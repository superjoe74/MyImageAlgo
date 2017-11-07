package imgAlg;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.MemoryImageSource;

import javax.swing.JComponent;

public class CenterPanel extends JComponent{

	private MemoryImageSource src;
	private MyImage currentImg;
	private Image image;
	private int[] pixel;
	
	public CenterPanel() {
		pixel = new int[1280*720];
		src = new MemoryImageSource(1280, 720, pixel, 0, 1280);
		src.setAnimated(true);
		image = createImage(src);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				translation(20, 10);
//				rotation(15,true);
				
			}
		});
	}
	
	public void setImage(MyImage myImage) {
//		this.pixel = pixel;
		
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
		morph(dif);
			
	}
	
	public void rotation(double d, boolean b) {
		if (b) {
			MyMatrix dif = MyMatrix.getTranslationMatrix(-640, -360).mult(MyMatrix.getRotationMatrix(15)).mult(MyMatrix.getTranslationMatrix(640, 360));
			morph(dif);
		} else {
			MyMatrix dif = MyMatrix.getRotationMatrix(20); 
			morph(dif);
		}
	}
	
	public void morph(MyMatrix morphMatrix) {
		currentImg.setMorphMatrix(currentImg.getMorphMatrix().mult(morphMatrix));
//		currentImg.getMorphMatrix().printi();
		for (int i = 0; i < 1280; i++) {
			for (int j = 0; j < 720; j++) {
				MyGeoVector cords = new MyGeoVector(i, j, 1);				
				MyGeoVector newCords = currentImg.getMorphMatrix().mult(cords);
				if ((newCords.getData()[0] >= 0) && (newCords.getData()[0] < 1280) && (newCords.getData()[1] >= 0) && (newCords.getData()[1] < 720)) {
					//System.out.println((1280*newCords.getData()[1]+newCords.getData()[0]));
					pixel[j*1280+i] = currentImg.getPixel()[(1280*newCords.getData()[1]+newCords.getData()[0])];
				}
				else {
					pixel[j*1280+i] = 0xffffffff;
				}
			}
		}	
//		System.out.println("morphed");
		src.newPixels();
//		image = createImage(src);
		repaint();
	}

	public void setPixel(int[] pixel) {
		
		for (int i = 0; i < pixel.length; i++) {
			this.pixel[i] = pixel[i];
		}
		src.newPixels();
//		image = createImage(src);
		repaint();
	}

	public MyImage getCurrentImg() {
		return currentImg;
	}

	public void setCurrentImg(MyImage currentImg) {
		this.currentImg = currentImg;
		setPixel(currentImg.getPixel());
	}
}
