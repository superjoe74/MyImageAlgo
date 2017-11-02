package imgAlg;

import java.awt.Graphics;
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
		pixel = new int[1280*720];
		System.out.println(pixel.length);
		setSize(image.getWidth(), image.getHeight());
		pixel = image.getPixel();
		src = new MemoryImageSource(image.getWidth(), image.getHeight(), pixel, 0, image.getWidth());
		src.setAnimated(true);
		current = createImage(src);

	}
	
	public void setImage(int[] pixel) {
		for (int i = 0; i < pixel.length; i++) {
			this.pixel[i] = pixel[i];
		}
		src.newPixels();
		current = createImage(src);
		getGraphics().drawImage(current, 0, 0, this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
	}
	
	@Override
	public void update(Graphics g) {
	}
	
	@Override
	public void paint(Graphics g) {
	}
}
