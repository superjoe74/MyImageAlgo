package imgAlg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class CenterPanel extends JComponent {

	private MyImage currentImg;

	private int IMG_WIDTH;
	private int IMG_HEIGHT;

	private MemoryImageSource workSrc;
	private Image workImage;
	private int[] workPixel;

	private String mode;
	private int x0, y0, x1, y1;
	private int col_1 = 0xffff0000;
	private int col_2 = 0xff00ff00;

	public CenterPanel(Model model) {
		setBackground(new Color(0xffff0000));

		IMG_WIDTH = model.getIMG_WIDTH();
		IMG_HEIGHT = model.getIMG_HEIGHT();

		currentImg = model.getImages().get(0);

		workPixel = new int[IMG_WIDTH * IMG_HEIGHT];
		workSrc = new MemoryImageSource(IMG_WIDTH, IMG_HEIGHT, workPixel, 0, IMG_WIDTH);
		workSrc.setAnimated(true);
		workImage = createImage(workSrc);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				x0 = e.getX() - (getWidth() / 2 - 640);
				y0 = e.getY() - (getHeight() / 2 - 360);
				// scaling(0.9, 0.9);
				// rotation(15,true);

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clearPix();
				x1 = e.getX() - (getWidth() / 2 - 640);
				y1 = e.getY() - (getHeight() / 2 - 360);
				
				if (mode == "line") {
					drawLine(x0, y0, x1, y1);
				}else if (mode == "circle") {
					drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), false);
				}else if (mode == "filled circle") {
					drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), true);
				}else if (mode == "cut") {
					drawQuad(x0, y0, x1, y1);
				}
				
				currentImg.writePixel(workPixel);
				clearPix();
				workSrc.newPixels();
			}

		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				clearPix();
				x1 = e.getX() - (getWidth() / 2 - 640);
				y1 = e.getY() - (getHeight() / 2 - 360);
				
				if (mode == "line") {
					drawLine(x0, y0, x1, y1);
				}else if (mode == "circle") {
					drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), false);
				}else if (mode == "filled circle") {
					drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), true);
				}else if (mode == "cut") {
					drawQuad(x0, y0, x1, y1);
				}
				
				workSrc.newPixels();
			}
		});
	}

	private void clearPix() {
		for (int i = 0; i < workPixel.length; i++) {
			workPixel[i] = 0;
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(currentImg.getImage(), getWidth() / 2 - 640, getHeight() / 2 - 360, 1280, 720, this);
		g.drawImage(workImage, getWidth() / 2 - 640, getHeight() / 2 - 360, 1280, 720, this);
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
		MyMatrix dif = MyMatrix.getScaleMatrix(x, y);
		morph(dif);
	}

	public void morph(MyMatrix morphMatrix) {
		currentImg.setMorphMatrix(currentImg.getMorphMatrix().mult(morphMatrix));
		for (int i = 0; i < 1280; i++) {
			for (int j = 0; j < 720; j++) {
				MyGeoVector cords = new MyGeoVector(i, j, 1);
				MyGeoVector newCords = currentImg.getMorphMatrix().mult(cords);
				if ((newCords.getData()[0] >= 0) && (newCords.getData()[0] < 1280) && (newCords.getData()[1] >= 0)
						&& (newCords.getData()[1] < 720)) {
					workPixel[j * 1280 + i] = currentImg
							.getCurrentPixel()[(1280 * newCords.getData()[1] + newCords.getData()[0])];
				} else {
					workPixel[j * 1280 + i] = 0xffffffff;
				}
			}
		}
		workSrc.newPixels();
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

	public void drawCircle(int x0, int y0, int r, boolean fill) {
		int y = 0;
		int x = r;
		int F = -r;
		int dy = 1;
		int dyx = -2 * r + 3;
		while (y <= x) {
			if (fill) {
				setFilledCirclePixel(x0, y0, x, y);
			} else {
				setCirclePixel(x0, y0, x, y, r);
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

	public void drawQuad(int x0, int y0, int x1, int y1) {
		for (int i = 0; i < 1280; i++) {
			for (int j = 0; j < 720; j++) {
				if (i >= x0 && i <= x1 && j >= y0 && j <= y1) {
					workPixel[j * 1280 + i] = mixPixels(50, currentImg.getCurrentPixel()[j * 1280 + i], 0xaa2b569b);
				}
			}
		}
	}

	private void setFilledCirclePixel(int x0, int y0, int x, int y) {
		drawLine(x0 - x, y0 + y, x0 + x, y0 + y);
		drawLine(x0 - x, y0 - y, x0 + x, y0 - y);
		drawLine(x0 - y, y0 + x, x0 + y, y0 + x);
		drawLine(x0 - y, y0 - x, x0 + y, y0 - x);
	}

	private void setCirclePixel(int x0, int y0, int x, int y, int r) {
		setPixel(x0 + x, y0 + y, (int) (((x0 + r) + (x0 + x)) / (double) (x0 + r) * 50));
		setPixel(x0 - x, y0 + y, (int) (((x0 + r) + (x0 - x)) / (double) (x0 + r) * 100));
		setPixel(x0 + x, y0 - y, (int) (((x0 + r) + (x0 + x)) / (double) (x0 + r) * 100));
		setPixel(x0 - x, y0 - y, (int) (((x0 + r) + (x0 - x)) / (double) (x0 + r) * 100));
		setPixel(x0 + y, y0 + x, (int) (((x0 + r) + (x0 + x)) / (double) (x0 + r) * 100));
		setPixel(x0 - y, y0 + x, (int) (((x0 + r) + (x0 - x)) / (double) (x0 + r) * 100));
		setPixel(x0 + y, y0 - x, (int) (((x0 + r) + (x0 + x)) / (double) (x0 + r) * 100));
		setPixel(x0 - y, y0 - x, (int) (((x0 + r) + (x0 - x)) / (double) (x0 + r) * 100));
	}

	private void setPixel(int x, int y, int p) {
		if (x >= 0 && x < 1280 && y >= 0 && y < 720) {
			workPixel[y * 1280 + x] = mixPixels(p, col_1, col_2);
		}
	}

	private int mixPixels(int p, int col_1, int col_2) {
		int red = mixColors((col_1 >> 16) & 0xff, (col_2 >> 16) & 0xff, p);
		int green = mixColors((col_1 >> 8) & 0xff, (col_2 >> 8) & 0xff, p);
		int blue = mixColors(col_1 & 0xff, col_2 & 0xff, p);
		return 0xff000000 | (red << 16) | (green << 8) | blue;
	}

	private int mixColors(int i, int j, int p) {
		return i + (j - i) * p / 100;
	}

	public void setWorkPixel(int[] workPixel) {
		for (int i = 0; i < workPixel.length; i++) {
			this.workPixel[i] = workPixel[i];

		}
		workSrc.newPixels();
		repaint();
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

	public void setCurrentImg(MyImage img) {
		currentImg = img;
		clearPix();
		workSrc.newPixels();
		repaint();
	}

	public MyImage getCurrentImg() {
		return currentImg;
	}
}
