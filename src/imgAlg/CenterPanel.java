package imgAlg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class CenterPanel extends JComponent{

	private MemoryImageSource src;
	private Image image;
	private int[] pixel;
	
	public CenterPanel() {
		pixel = new int[1280*720];
		src = new MemoryImageSource(1280, 720, pixel, 0, 1280);
		src.setAnimated(true);
	}
	
	public void setImage(int[] pixel) {
		for (int i = 0; i < pixel.length; i++) {
			this.pixel[i] = pixel[i];
		}
		src.newPixels();
		image = createImage(src);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, getWidth()/2 - 640, getHeight()/2 - 360, 1280, 720, this);
	}
	
}
