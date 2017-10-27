package imgAlg;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Model {
	
	private Dimension initSize;
	private ArrayList<BufferedImage> images;

	public Model(int width, int height) {
		initSize = new Dimension(width, height);
		images = new ArrayList<BufferedImage>(10);
	}

	public Dimension getInitSize() {
		return initSize;
	}

	public ArrayList<BufferedImage> getImages() {
		return images;
	}

}
