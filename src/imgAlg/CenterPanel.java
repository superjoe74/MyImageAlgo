package imgAlg;

import java.awt.Image;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class CenterPanel extends JComponent{
	
	private MyImage image;
	private MemoryImageSource src;
	private Image current;
	private int[] pixel;
	
	public CenterPanel(MyImage image) {
		this.image = image;
		setSize(image.getWidth(), image.getHeight());
		pixel = image.getPixel();
		src = new MemoryImageSource(image.getWidth(), image.getHeight(), pixel, 0, image.getWidth());
		src.setAnimated(true);
		current = createImage(src);
		getGraphics().drawImage(current, 0, 0, this);
	}
	
	public void setImage(int[] pixel) {
		this.pixel = pixel;
		src.newPixels();
		current = createImage(src);
		getGraphics().drawImage(current, 0, 0, this);
	}
}
