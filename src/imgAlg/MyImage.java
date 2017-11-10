package imgAlg;

import java.awt.Frame;
import java.awt.Image;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;

public class MyImage {

	private Image image;
	private int width, height;
	private int[] currentPixel;
	private int[] oldPixel;
	private MyMatrix morphMatrix;
	private boolean selectedForFade;
	private PixelGrabber grab;
	private MemoryImageSource src;

	public MyImage(Image big, int w, int h) {
		image = big.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		width = w;
		height = h;
		oldPixel = new int[w * h];
		currentPixel = new int[w * h];
		grab = new PixelGrabber(image, 0, 0, w, h, oldPixel, 0, w);
		try {
			grab.grabPixels();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < currentPixel.length; i++) {
			currentPixel[i] = oldPixel[i];
		}
		src = new MemoryImageSource(w, h, currentPixel, 0, w);
		src.setAnimated(true);
		image = new Frame().getToolkit().createImage(src);
		
		morphMatrix = MyMatrix.getNeutralMatrix();
	}

	public Image getImage() {
		return image;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	

	public int[] getCurrentPixel() {
		return currentPixel;
	}

	public boolean isSelectedForFade() {
		return selectedForFade;
	}

	public void setSelectedForFade(boolean selectedForFade) {
		this.selectedForFade = selectedForFade;
	}

	public MyMatrix getMorphMatrix() {
		return morphMatrix;
	}

	public void setMorphMatrix(MyMatrix morphMatrix) {
		for (int i = 0; i < morphMatrix.getData().length; i++) {
			for (int j = 0; j < morphMatrix.getData().length; j++) {
				this.morphMatrix.getData()[i][j] = morphMatrix.getData()[i][j];
			}		}
	}
	public void resetPixel() {
		for (int i = 0; i < currentPixel.length; i++) {
			currentPixel[i] = oldPixel[i];
		}
		src.newPixels();
	}

	public void setCurrentPixel(int i, int j) {
		currentPixel[i] = j;
		src.newPixels();
	}

	public void writePixel(int[] workPixel) {
		for (int i = 0; i < workPixel.length; i++) {
			if (workPixel[i] != 0) {
				currentPixel[i] = workPixel[i];
			}
		}
		src.newPixels();
	}
}