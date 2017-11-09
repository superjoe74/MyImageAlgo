package imgAlg;

import java.util.ArrayList;

public class ImageFade implements Runnable {

	private ArrayList<MyImage> images;
	private volatile boolean active;
	private MyImage img_1;
	private MyImage img_2;
	private CenterPanel cP;
	private SouthPanel sP;

	public ImageFade(ArrayList<MyImage> images, CenterPanel c, SouthPanel s) {
		this.images = images;
		cP = c;
		sP = s;
		active = true;
		Thread th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		while (active) {
			for (int i = 0; i < images.size(); i++) {
				if (images.get(i).isSelectedForFade()) {
					if (img_1 == null) {
						img_1 = cP.getCurrentImg();
					} else if (img_2 == null) {
						img_2 = images.get(i);
						fade();
						img_2 = null;
						try {

							Thread.sleep(1111);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}

	}

	private void fade() {

		int[] pix_3 = new int[img_1.getCurrentPixel().length];
		for (int i = 0; i < 101; i++) {
			for (int j = 0; j < pix_3.length; j++) {
				pix_3[j] = mixPixels(i, j);
			}
			cP.setWorkPixel(pix_3);
		}
		cP.setCurrentImg(img_2);
		sP.repaint();
		img_1 = img_2;
	}

	private int mixPixels(int p, int j) {
		int red = mixColors((img_1.getCurrentPixel()[j] >> 16) & 0xff, (img_2.getCurrentPixel()[j] >> 16) & 0xff, p);
		int green = mixColors((img_1.getCurrentPixel()[j] >> 8) & 0xff, (img_2.getCurrentPixel()[j] >> 8) & 0xff, p);
		int blue = mixColors(img_1.getCurrentPixel()[j] & 0xff, img_2.getCurrentPixel()[j] & 0xff, p);
		return 0xff000000 | (red << 16) | (green << 8) | blue;
	}

	private int mixColors(int i, int j, int p) {
		return i + (j - i) * p / 100;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
