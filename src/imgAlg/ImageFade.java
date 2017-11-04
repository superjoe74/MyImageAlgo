package imgAlg;

import java.util.ArrayList;

public class ImageFade implements Runnable {

	private ArrayList<PreviewImageComponent> imageComponents;
	private CenterPanel centerPanel;
	private boolean active;
	private Thread th;

	public ImageFade(ArrayList<PreviewImageComponent> imageComponents, CenterPanel c) {
		this.imageComponents = imageComponents;
		centerPanel = c;
		active = true;
		th = new Thread(this);
		th.start();
	}

	@Override
	public void run() {
		while (active) {
//			int count = 0;
			for (int i = 0; i < imageComponents.size(); i++) {
				if (imageComponents.get(i).isSelected()) {
					centerPanel.setImage(imageComponents.get(i).getImg().getPixel());
//					++count;
					try {
						Thread.sleep(222);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
//				if(i == imageComponents.size() - 1 && count < 2)
//					active = false;
			}
		}

	}

	public boolean isActive() {
		return active;
	}

}
