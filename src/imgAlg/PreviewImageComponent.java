package imgAlg;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class PreviewImageComponent extends JComponent {
	
	private MyImage img;
	private boolean selected;
	private CenterPanel cP;

	public PreviewImageComponent(MyImage img, CenterPanel cP) {
		this.img = img;
		this.cP = cP;
		setPreferredSize(new Dimension(192, 108));
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (img.getPixel()[500000] == cP.getPixel()[500000]) {
			g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
		}else {
			g.drawImage(img.getImage(), 19, 11, getWidth() - 38, getHeight() - 22, this);
		}
	}

	public MyImage getImg() {
		return img;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
}
