package imgAlg;

import java.awt.Image;
import java.awt.image.PixelGrabber;

public class MyImage {

	private Image bigImage, littleImage;
	private int bigWidth, littleWidth, bigHeight, littleHeight;
	private int[] pixel;
	
	public MyImage(Image big, Image little, int w1, int h1, int w2, int h2) {
		bigImage = big;
		littleImage = little;
		bigWidth = w1;
		littleWidth = w2;
		bigHeight = h1;
		littleHeight = h2;
		pixel = new int[w1 * h1];
		PixelGrabber grab = new PixelGrabber(big, 0, 0, w1, h1, pixel, 0, w1);
		try {
			grab.grabPixels();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Image getBigImage() {
		return bigImage;
	}

	public Image getLittleImage() {
		return littleImage;
	}

	public int getBigWidth() {
		return bigWidth;
	}

	public int getLittleWidth() {
		return littleWidth;
	}

	public int getBigHeight() {
		return bigHeight;
	}

	public int getLittleHeight() {
		return littleHeight;
	}

	public int[] getPixel() {
		return pixel;
	}
}