package imgAlg;

import java.awt.Image;
import java.awt.image.PixelGrabber;

public class MyImage {

	private Image image;
	private int width, height;
	private int[] pixel;

	public MyImage(Image big, int w, int h) {
		image = big.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		width = w;
		height = h;
		pixel = new int[w * h];
		PixelGrabber grab = new PixelGrabber(big, 0, 0, w, h, pixel, 0, w);
		try {
			grab.grabPixels();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(image);

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
}