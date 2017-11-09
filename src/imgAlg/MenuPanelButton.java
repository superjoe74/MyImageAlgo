package imgAlg;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MenuPanelButton extends JButton{
	
	private ImageIcon activated;
	private ImageIcon deactivated;
	
	public MenuPanelButton(String d, String a) {
		deactivated = new ImageIcon(d);
		activated = new ImageIcon(a);
		setIcon(deactivated);
		setPreferredSize(new Dimension(30, 30));
	}
	
	public void deactivate() {
		setIcon(deactivated);
	}
	
	public void activate() {
		setIcon(activated);
	}
} 