package imgAlg;

import java.util.ArrayList;

public class Model {
	
	
	private ArrayList<MyImage> images;
	private int IMG_WIDTH;
	private int IMG_HEIGHT;
	
	public Model(int width, int height) {
		images = new ArrayList<MyImage>(10);
		IMG_WIDTH = width;
		IMG_HEIGHT = height;
	}

	public void addImage(MyImage image) {
		images.add(image);
	}

	public ArrayList<MyImage> getImages() {
		return images;
	}

	public int getIMG_WIDTH() {
		return IMG_WIDTH;
	}

	public int getIMG_HEIGHT() {
		return IMG_HEIGHT;
	}
}
