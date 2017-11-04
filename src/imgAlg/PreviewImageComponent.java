package imgAlg;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class PreviewImageComponent extends JComponent {
	
	private MyImage img;
	private boolean selected;

	public PreviewImageComponent(MyImage img, CenterPanel cPan) {
		this.img = img;
		setPreferredSize(new Dimension(192, 108));
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
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
