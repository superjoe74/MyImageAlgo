package imgAlg;

import java.awt.Image;
import java.awt.image.PixelGrabber;

public class MyImage {

	private Image image;
	private int width, height;
	private int[] pixel;
	private MyMatrix morphMatrix;
	private boolean selectedForFade;
	private PixelGrabber grab;

	public MyImage(Image big, int w, int h) {
		image = big.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		width = w;
		height = h;
		pixel = new int[w * h];
		grab = new PixelGrabber(image, 0, 0, w, h, pixel, 0, w);
		try {
			grab.grabPixels();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public int[] getPixel() {
		return pixel;
	}

	public void setPixel(int[] pixel) {
//		for (int i = 0; i < pixel.length; i++) {
			this.pixel = pixel;
//		}
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
		try {
			grab.grabPixels();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}