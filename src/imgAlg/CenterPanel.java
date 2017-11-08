package imgAlg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class CenterPanel extends JComponent {

	private MemoryImageSource src;
	private MyImage currentImg;
	private Image image;
	private int[] pixel;
	private String mode;
	private int x0,y0,x1,y1;
	private boolean fill = true;
	private int col_1 = 0xffff0000;
	private int col_2= 0xff00ff00;

	public CenterPanel() {
		setBackground(new Color(0xffff0000));
		pixel = new int[1280 * 720];
		src = new MemoryImageSource(1280, 720, pixel, 0, 1280);
		src.setAnimated(true);
		image = createImage(src);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				x0 = e.getX() - (getWidth() / 2 - 640);
				y0 = e.getY() - (getHeight() / 2 - 360);
				// translation(20, 10);
				// rotation(15,true);
				
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				clearPix();
				x1 = e.getX() - (getWidth() / 2 - 640);
				y1 = e.getY() - (getHeight() / 2 - 360);
				drawCircle(x0, y0, x1 - x0);
				src.newPixels();
				repaint();
			}
			
		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				clearPix();
				x1 = e.getX() - (getWidth() / 2 - 640);
				y1 = e.getY() - (getHeight() / 2 - 360);
				drawCircle(x0, y0, x1 - x0);
				src.newPixels();
				repaint();
			}
		});
	}
	private void clearPix() {
		for (int i = 0; i < pixel.length; i++) {
			pixel[i] = 0;
		}
	}
	public void setImage(MyImage myImage) {
		// this.pixel = pixel;

	}

	@Override
	public void paintComponent(Graphics g) {

		if (mode == "line") {
			if (currentImg.getImage() != null) {
				g.drawImage(currentImg.getImage(), getWidth() / 2 - 640, getHeight() / 2 - 360, 1280, 720, this);
				System.out.println("gg");
			}
			g.drawImage(image, getWidth() / 2 - 640, getHeight() / 2 - 360, 1280, 720, this);
		} else {

			g.drawImage(image, getWidth() / 2 - 640, getHeight() / 2 - 360, 1280, 720, this);
		}

	}

	public int[] getPixel() {
		return pixel;
	}

	@Override
	public void update(Graphics g) {
		paintComponent(g);
	}

	public void translation(int x, int y) {
		MyMatrix dif = MyMatrix.getTranslationMatrix(x, y);
		morph(dif);

	}

	public void rotation(double d, boolean b) {
		if (b) {
			MyMatrix dif = MyMatrix.getTranslationMatrix(-640, -360).mult(MyMatrix.getRotationMatrix(15))
					.mult(MyMatrix.getTranslationMatrix(640, 360));
			morph(dif);
		} else {
			MyMatrix dif = MyMatrix.getRotationMatrix(20);
			morph(dif);
		}
	}

	
	public void yShearing(double y) {
		MyMatrix dif = MyMatrix.yShearMatrix(y);
		morph(dif);
	}
	
	public void xShearing(double x) {
		MyMatrix dif = MyMatrix.xShearMatrix(x);
		morph(dif);
	}
	
	public void scaling(double x, double y) {
		MyMatrix dif = MyMatrix.getScaleMatrix(x,y);
		morph(dif);
	}
	



	public void morph(MyMatrix morphMatrix) {
		currentImg.setMorphMatrix(currentImg.getMorphMatrix().mult(morphMatrix));
		// currentImg.getMorphMatrix().printi();
		for (int i = 0; i < 1280; i++) {
			for (int j = 0; j < 720; j++) {
				MyGeoVector cords = new MyGeoVector(i, j, 1);
				MyGeoVector newCords = currentImg.getMorphMatrix().mult(cords);
				if ((newCords.getData()[0] >= 0) && (newCords.getData()[0] < 1280) && (newCords.getData()[1] >= 0)
						&& (newCords.getData()[1] < 720)) {
					// System.out.println((1280*newCords.getData()[1]+newCords.getData()[0]));
					pixel[j * 1280 + i] = currentImg.getPixel()[(1280 * newCords.getData()[1] + newCords.getData()[0])];
				} else {
					pixel[j * 1280 + i] = 0xffffffff;
				}
			}
		}
		// System.out.println("morphed");
		src.newPixels();
		// image = createImage(src);
		repaint();
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		final int dx = Math.abs(x0 - x1);
		final int dy = Math.abs(y0 - y1);
		final int sgnDx = x0 < x1 ? 1 : -1;
		final int sgnDy = y0 < y1 ? 1 : -1;
		int shortD, longD, incXshort, incXlong, incYshort, incYlong;
		if (dx > dy) {
			shortD = dy;
			longD = dx;
			incXlong = sgnDx;
			incXshort = 0;
			incYlong = 0;
			incYshort = sgnDy;
		} else {
			shortD = dx;
			longD = dy;
			incXlong = 0;
			incXshort = sgnDx;
			incYlong = sgnDy;
			incYshort = 0;
		}
		int d = longD / 2, x = x0, y = y0;
		for (int i = 0; i <= longD; ++i) {
			int p = (int) ((Math.abs(x - x0) / (double) dx) * 100);
			setPixel(x, y, p);
			x += incXlong;
			y += incYlong;
			d += shortD;
			if (d >= longD) {
				d -= longD;
				x += incXshort;
				y += incYshort;
			}
		}
	}
	
	public void drawCircle(int x0, int y0, int r) {
		int y = 0;
		int x = r;
		int F = -r;
		int dy = 1;
		int dyx = -2*r+3;
		while(y<=x) {
			if (fill) {
				setFilledCirclePixel(x0, y0, x, y);	
			}else {
				setCirclePixel(x0, y0, x, y);
			}
		++y;
		dy += 2;
		dyx += 2;
		if (F > 0) {
		F += dyx;
		--x;
		dyx += 2;
		} else {
		F += dy;
		}
		}
	}
	
	private void setFilledCirclePixel(int x0, int y0, int x, int y) {
		drawLine(x0 - x, y0 + y, x0 + x, y0 + y);
		drawLine(x0 - x, y0 - y, x0 + x, y0 - y);
		drawLine(x0 - y, y0 + x, x0 + y, y0 + x);
		drawLine(x0 - y, y0 - x, x0 + y, y0 - x);
	}
	private void setCirclePixel(int x0, int y0, int x, int y) {
		setPixel(x0 + x, y0 + y);
		setPixel(x0 - x, y0 + y);
		setPixel(x0 + x, y0 - y);
		setPixel(x0 - x, y0 - y);
		setPixel(x0 + y, y0 + x);
		setPixel(x0 - y, y0 + x);
		setPixel(x0 + y, y0 - x);
		setPixel(x0 - y, y0 - x);
	}
	
	private void setPixel(int x, int y, int p) {
		pixel[y*1280 +x] = mixPixels(p);
	}
	
	private int mixPixels(int p) {
		int red = mixColors((col_1 >> 16) & 0xff, (col_2 >> 16) & 0xff, p);
		int green = mixColors((col_1 >> 8) & 0xff, (col_2 >> 8) & 0xff, p);
		int blue = mixColors(col_1 & 0xff, col_2 & 0xff, p);
		return 0xff000000 | (red << 16) | (green << 8) | blue;
	}

	private int mixColors(int i, int j, int p) {
		return i + (j - i) * p / 100;
	}

	public void setPixel(int[] pixel) {

		for (int i = 0; i < pixel.length; i++) {
			this.pixel[i] = pixel[i];
		}
		src.newPixels();
		// image = createImage(src);
		repaint();
	}

	public MyImage getCurrentImg() {
		return currentImg;
	}

	public void setCurrentImg(MyImage currentImg) {
		this.currentImg = currentImg;
		setPixel(currentImg.getPixel());
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
	public void setCol_1(int col_1) {
		this.col_1 = col_1;
	}
	public void setCol_2(int col_2) {
		this.col_2 = col_2;
	}
}
