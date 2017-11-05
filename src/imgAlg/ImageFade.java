package imgAlg;

import java.util.ArrayList;

public class ImageFade implements Runnable {

	private ArrayList<PreviewImageComponent> imageComponents;
	private boolean active;
	private int[] pix_1;
	private int[] pix_2;
	private CenterPanel cP;
	private SouthPanel sP;

	public ImageFade(ArrayList<PreviewImageComponent> imageComponents, CenterPanel c, SouthPanel s) {
		this.imageComponents = imageComponents;
		cP = c;
		sP = s;
		active = true;
		Thread th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		while (active) {
			for (int i = 0; i < imageComponents.size(); i++) {
				if (imageComponents.get(i).isSelected()) {
					if (pix_1 == null) {
						pix_1 = imageComponents.get(i).getImg().getPixel();
					}else if (pix_2 == null) {
						pix_2 = imageComponents.get(i).getImg().getPixel();
						fade();
						sP.repaint();
						pix_2 = null;
					}
					try {
						Thread.sleep(1111);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (pix_1 == pix_2) {
					active = false;
				}
			}
		}

	}

	private void fade() {
		int[] pix_3 = new int[pix_1.length];
		for (int i = 0; i < 101; i++) {
			for (int j = 0; j < pix_3.length; j++) {
				pix_3[j] = mixPixels(i, j);
			}
			cP.setImage(pix_3);
		}
		pix_1 = pix_2;
	}

	private int mixPixels(int p, int j) {
		int red = mixColors((pix_1[j] >> 16) & 0xff, (pix_2[j] >> 16) & 0xff, p);
		int green = mixColors((pix_1[j] >> 8) & 0xff, (pix_2[j] >> 8) & 0xff, p);
		int blue = mixColors(pix_1[j] & 0xff, pix_2[j] & 0xff, p);
		return 0xff000000 | (red << 16) | (green << 8) | blue;
	}

	private int mixColors(int i, int j, int p) {
		 return i+(j-i)*p/100;
	}

	public boolean isActive() {
		return active;
	}

}
