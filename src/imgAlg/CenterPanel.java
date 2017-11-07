package imgAlg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class CenterPanel extends JComponent{

	private MemoryImageSource src;
	private Image image;
	private int[] pixel;
	private int[] newPixel;
	
	public CenterPanel() {
		pixel = new int[1280*720];
		newPixel = new int[1280*720];
		src = new MemoryImageSource(1280, 720, pixel, 0, 1280);
		src.setAnimated(true);
		image = createImage(src);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//			translation(128, 72);
				rotation(0.3);
			}
		});
	}
	
	public void setImage(MyImage myImage) {
//		this.pixel = pixel;
		for (int i = 0; i < myImage.getPixel().length; i++) {
			this.pixel[i] = myImage.getPixel()[i];
		}
		src.newPixels();
//		image = createImage(src);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, getWidth()/2 - 640, getHeight()/2 - 360, 1280, 720, this);
	}

	public int[] getPixel() {
		return pixel;
	}
	@Override
	public void update(Graphics g) {
		paintComponent(g);
	}
	
	public void translation(int x, int y) {
		MyMatrix dif = MyMatrix.getTranslationMatrix(x,y); 
			for (int i = 0; i < 1280; i++) {
				for (int j = 0; j < 720; j++) {
					int[] cords = {i,j,1};
					int[] newCords = MyMatrix.mult(dif, cords);
					if ((newCords[0] >= 0) && (newCords[0] <= 1280) && (newCords[1] >= 0) && (newCords[1] <= 720)) {
						newPixel[j*1280+i] = pixel[(1280*newCords[1]+newCords[0])];
					}
					else {
						newPixel[j*1280+i] = 0xffffffff;
					}
				}
			}	
		setImage(newPixel);
		repaint();
	}
	
	public void rotation(double d) {
		double[][] dif = {{Math.cos(-d),-Math.sin(-d),0},{Math.sin(-d),Math.cos(-d),0},{0,0,1}}; 
			for (int i = 0; i < 1280; i++) {
				for (int j = 0; j < 720; j++) {
					int[] cords = {i,j,1};
					int[] newCords = MyMatrix.mult(dif, cords);
					if ((newCords[0] >= 0) && (newCords[0] <= 1280) && (newCords[1] >= 0) && (newCords[1] <= 720)) {
						newPixel[j*1280+i] = pixel[(1280*newCords[1]+newCords[0])];
					}
					else {
						newPixel[j*1280+i] = 0xffffffff;
					}
				}
			}	
		setImage(newPixel);
		repaint();
	}
}
