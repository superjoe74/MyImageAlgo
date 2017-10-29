package imgAlg;

import java.awt.Dimension;
import java.util.ArrayList;

public class Model {
	
	private final Dimension initSize;
	private ArrayList<MyImage> images;
	private final Dimension bigDim;
	private final Dimension littleDim;
	
	public Model(int width, int height) {
		initSize = new Dimension(width, height);
		images = new ArrayList<MyImage>(10);
		bigDim = new Dimension(1280, 720);
		littleDim = new Dimension(128, 72);
	}

	public Dimension getInitSize() {
		return initSize;
	}

	public void addImage(MyImage image) {
		images.add(image);
	}

	public Dimension getBigDim() {
		return bigDim;
	}

	public Dimension getLittleDim() {
		return littleDim;
	}

	public ArrayList<MyImage> getImages() {
		return images;
	}
}
