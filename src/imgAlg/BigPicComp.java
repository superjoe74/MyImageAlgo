package imgAlg;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class BigPicComp extends JComponent{
	
	private BufferedImage imageToPaint;
	private double compRatio;
	private double imgRatio;
	
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		if (imgRatio > compRatio)
			g.drawImage(imageToPaint, 0, 0, getWidth(), (int) (getWidth() / imgRatio), this);
		else
			g.drawImage(imageToPaint, 0, 0, (int) (getHeight() * imgRatio), getHeight(), this);
	}

	public void setImageToPaint(BufferedImage imageToPaint) {
		this.imageToPaint = imageToPaint;
		compRatio = getWidth() / (double) getHeight();
		imgRatio = imageToPaint.getWidth() / (double) imageToPaint.getHeight();
	}

}
