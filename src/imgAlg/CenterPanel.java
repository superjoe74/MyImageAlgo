package imgAlg;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;

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

	private int[] selectedPixel;
	private MyMatrix selectedHistory;
	
	private boolean unsavedContent;
	
	private int[][] histogramm;

	public CenterPanel(Model model) {
		setBackground(new Color(0xffff0000));

		IMG_WIDTH = model.getIMG_WIDTH();
		IMG_HEIGHT = model.getIMG_HEIGHT();

		currentImg = model.getImages().get(0);

		workPixel = new int[IMG_WIDTH * IMG_HEIGHT];
		workSrc = new MemoryImageSource(IMG_WIDTH, IMG_HEIGHT, workPixel, 0, IMG_WIDTH);
		workSrc.setAnimated(true);
		workImage = createImage(workSrc);
		
		selectedPixel = new int[IMG_WIDTH * IMG_HEIGHT];
		selectedHistory = MyMatrix.getNeutralMatrix();

		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				x0 = e.getX() - (getWidth() / 2 - 640);
				y0 = e.getY() - (getHeight() / 2 - 360);

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				clearPix();
				x1 = e.getX() - (getWidth() / 2 - 640);
				y1 = e.getY() - (getHeight() / 2 - 360);

				if (mode != "cut") {
					
					if (mode == "line") {
						drawLine(x0, y0, x1, y1);
					} else if (mode == "circle") {
						drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), false);
					} else if (mode == "filled circle") {
						drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), true);
					}
					
					currentImg.writePixel(workPixel);
					clearPix();
					workSrc.newPixels();
				
				} else {
					clearPix();
					getSelectedPixel(x0, y0, x1, y1);
//					clearPix();
//					scaling(2, 2);
					workSrc.newPixels();
					setUnsavedContent(true);
				}
			}

			private void getSelectedPixel(int x0, int y0, int x1, int y1) {
				for (int i = 0; i < 1280; i++) {
					for (int j = 0; j < 720; j++) {
						if (i >= x0 && i <= x1 && j >= y0 && j <= y1) {
							selectedPixel[j * 1280 + i] = currentImg.getCurrentPixel()[j * 1280 + i];
							workPixel[j * 1280 + i] = currentImg.getCurrentPixel()[j * 1280 + i];
							currentImg.getCurrentPixel()[j * 1280 + i] = 0;
						}
					}
				}
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
				} else if (mode == "circle") {
					drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), false);
				} else if (mode == "filled circle") {
					drawCircle(x0, y0, (int) Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1)), true);
				} else if (mode == "cut") {
					drawQuad(x0, y0, x1, y1);
				}

				workSrc.newPixels();
			}
		});
	}

	public void clearPix() {
		for (int i = 0; i < workPixel.length; i++) {
			workPixel[i] = 0;
			selectedPixel[i] = 0;
		}
		workSrc.newPixels();
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
		if (unsavedContent) {
			morph(dif, selectedHistory, selectedPixel, workPixel);
		}else {
			morph(dif, currentImg.getMorphMatrix(), currentImg.getOriginalPixel(), currentImg.getCurrentPixel());
		}
	}

	public void rotation(int d, boolean b) {
		if (b) {
			
			if (unsavedContent) {
				MyMatrix dif = MyMatrix.getTranslationMatrix(-(x0 + (x1 - x0)/2), -(y0 + (y1 - y0)/2)).mult(MyMatrix.getRotationMatrix(d))
						.mult(MyMatrix.getTranslationMatrix(x0 + (x1 - x0)/2, y0 + (y1 - y0)/2));
				morph(dif, selectedHistory, selectedPixel, workPixel);
			}else {
				MyMatrix dif = MyMatrix.getTranslationMatrix(-IMG_WIDTH/2, -IMG_HEIGHT/2).mult(MyMatrix.getRotationMatrix(d))
						.mult(MyMatrix.getTranslationMatrix(IMG_WIDTH/2, IMG_HEIGHT/2));
				morph(dif, currentImg.getMorphMatrix(), currentImg.getOriginalPixel(), currentImg.getCurrentPixel());
			}
		} else {
			MyMatrix dif = MyMatrix.getRotationMatrix(d);
			if (unsavedContent) {
				morph(dif, selectedHistory, selectedPixel, workPixel);
			}else {
				morph(dif, currentImg.getMorphMatrix(), currentImg.getOriginalPixel(), currentImg.getCurrentPixel());
			}
		}
	}

	public void yShearing(double y) {
		MyMatrix dif = MyMatrix.yShearMatrix(y);
		if (unsavedContent) {
			morph(dif, selectedHistory, selectedPixel, workPixel);
		}else {
			morph(dif, currentImg.getMorphMatrix(), currentImg.getOriginalPixel(), currentImg.getCurrentPixel());
		}
	}

	public void xShearing(double x) {
		MyMatrix dif = MyMatrix.xShearMatrix(x);
		if (unsavedContent) {
			morph(dif, selectedHistory, selectedPixel, workPixel);
		}else {
			morph(dif, currentImg.getMorphMatrix(), currentImg.getOriginalPixel(), currentImg.getCurrentPixel());
		}
	}

	public void scaling(double x, double y) {
		MyMatrix dif = MyMatrix.getScaleMatrix(x, y);
		if (unsavedContent) {
			morph(dif, selectedHistory, selectedPixel, workPixel);
		}else {
			morph(dif, currentImg.getMorphMatrix(), currentImg.getOriginalPixel(), currentImg.getCurrentPixel());
		}
	}

	public void morph(MyMatrix morphMatrix, MyMatrix historyMatrix, int[] source, int[] goal) {
		if (unsavedContent) {
			setHistoryMatrix(historyMatrix.mult(morphMatrix));
		} else {
			currentImg.setMorphMatrix(historyMatrix.mult(morphMatrix));
		}
		
		for (int i = 0; i < 1280; i++) {
			for (int j = 0; j < 720; j++) {
				if (mode == "xShear") {
					
				}
				MyGeoVector cords = new MyGeoVector(i, j, 1);
				MyGeoVector newCords = historyMatrix.mult(cords);
				if ((newCords.getData()[0] >= 0) && (newCords.getData()[0] < 1280) && (newCords.getData()[1] >= 0)
						&& (newCords.getData()[1] < 720)) {
					goal[j * 1280 + i] = source[(1280 * newCords.getData()[1] + newCords.getData()[0])];
				} else {
					goal[j * 1280 + i] = 0x00000000;
				}
			}
		}
		workSrc.newPixels();
		currentImg.refresh();
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
		System.out.println(((x0+x - this.x0+r)/(double)(2*r)));
		setPixel(x0 + x, y0 + y, 0);
		setPixel(x0 - x, y0 + y, 0);
		setPixel(x0 + x, y0 - y, 0);
		setPixel(x0 - x, y0 - y, 0);
		setPixel(x0 + y, y0 + x, 0);
		setPixel(x0 - y, y0 + x, 0);
		setPixel(x0 + y, y0 - x, 0);
		setPixel(x0 - y, y0 - x, 0);
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

	public int[] getWorkPixel() {
		return workPixel;
	}
	
	public void setHistoryMatrix(MyMatrix morphMatrix) {
		for (int i = 0; i < morphMatrix.getData().length; i++) {
			for (int j = 0; j < morphMatrix.getData().length; j++) {
				selectedHistory.getData()[i][j] = morphMatrix.getData()[i][j];
			}		}
	}
	
	public void resetHistoryMatrix(){
		selectedHistory = MyMatrix.getNeutralMatrix();
		currentImg.setMorphMatrix(MyMatrix.getNeutralMatrix());
	}
	
	public int[][] createHistogramm(MyImage img) {
//		PrintWriter writer;
//		try {
//			writer = new PrintWriter("Histogramm.txt", "UTF-8");
			TreeMap<Integer, Integer> colorMap = new TreeMap<Integer, Integer>();
	
			int[] pix = currentImg.getCurrentPixel();
			for (int i = 0; i < pix.length; ++i) {
				if (!colorMap.containsKey(pix[i])) {
					colorMap.put(pix[i], 1);
				} else {
					colorMap.put(pix[i], (int) colorMap.get(pix[i]) + 1);
				}
			}

			int[][] histo = new int[2][colorMap.size()];
			//Map<Integer,Integer> = new TreeMap(colorMap);
			int i = 0;
			for (Integer color : colorMap.keySet()) {
				histo[0][i] = color;
				histo[1][i++] = colorMap.get(color);
//				writer.println(Integer.toHexString(color) + ": " + (Integer) colorMap.get(color));
			}
//			for (int j = 0; j < histo[0].length; j++) {
//				System.out.println(Integer.toHexString(histo[0][j]) + ": " + histo[1][j]);
//			}
			histo = quicksort(histo);
//			for (int j = 0; j < 100; j++) {
//				System.out.println(Integer.toHexString(histo[0][j]) + ": " + histo[1][j]);
//			}
//			System.out.println(histo[0].length);
			return histo;
	
//			writer.close();
//			return true;
//		} catch (FileNotFoundException | UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
	}
	private int[][] quicksort(int[][] array) {
		return quicksort_helper(array, 0, array[0].length-1);
	}

	private int[][] quicksort_helper(int[][] array, int left, int right) {
			int mid = array[1][(left + right) / 2];
			int l = left;
			int r = right;
			
			while(l < r) {
				while(array[1][l] > mid) { ++l; }
				while(array[1][r] < mid) { --r; }
				if(l <= r)
					swap(array, l++, r--);
			}
			if (left < r)
				quicksort_helper(array, left, r );
			if (right > l)
				quicksort_helper(array, l, right);
			return array;
	}

	private void swap(int[][] array, int i, int j) {
		int tmp0 = array[0][i];
		int tmp1 = array[1][i];
		array[0][i] = array[0][j];
		array[1][i] = array[1][j];
		array[0][j] = tmp0;
		array[1][j] = tmp1;
	}
	
	public void setMode(String mode) {
		this.mode = mode;
	}

	public void setCol_2(int col_2) {
		this.col_2 = col_2;
	}

	public void setCurrentImg(MyImage img) {
			currentImg = img;
	//		clearPix();
	//		workSrc.newPixels();
			repaint();
		}

	public void setSelectedHistory(MyMatrix selectedHistory) {
		this.selectedHistory = selectedHistory;
	}

	public void setCol_1(int col_1) {
		this.col_1 = col_1;
	}

	public MyImage getCurrentImg() {
		return currentImg;
	}

	public boolean isUnsavedContent() {
		return unsavedContent;
	}

	public void setUnsavedContent(boolean unsavedContent) {
		this.unsavedContent = unsavedContent;
	}
}
