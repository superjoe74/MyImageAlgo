package imgAlg;

import java.util.ArrayList;

public class Model {
	
	
	private ArrayList<MyImage> images;
	private int IMG_WIDTH;
	private int IMG_HEIGHT;
	
	private int standardTranslation = 10;
	private int standardRotation = 45;
	private double standardXShearing = 0.3;
	private double standardYShearing = 0.3;
	private double standardUpScaling = 1.1;
	private double standardDownScaling = 0.9;
	
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

	public int getStandardTranslation() {
		return standardTranslation;
	}

	public void setStandardTranslation(int standardTranslation) {
		this.standardTranslation = standardTranslation;
	}

	public int getStandardRotation() {
		return standardRotation;
	}

	public void setStandardRotation(int standardRotation) {
		this.standardRotation = standardRotation;
	}

	
	public double getStandardXShearing() {
		return standardXShearing;
	}

	public void setStandardXShearing(double standardXShearing) {
		this.standardXShearing = standardXShearing;
	}

	public double getStandardYShearing() {
		return standardYShearing;
	}

	public void setStandardYShearing(double standardYShearing) {
		this.standardYShearing = standardYShearing;
	}

	public double getStandardUpScaling() {
		return standardUpScaling;
	}

	public void setStandardUpScaling(double standardUpScaling) {
		this.standardUpScaling = standardUpScaling;
	}

	public double getStandardDownScaling() {
		return standardDownScaling;
	}

	public void setStandardDownScaling(double standardDownScaling) {
		this.standardDownScaling = standardDownScaling;
	}
}
