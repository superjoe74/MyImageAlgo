package imgAlg;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

public class PreviewImageComponent extends JComponent {
	
	MyImage img;

	public PreviewImageComponent(MyImage img) {
		this.img = img;
		setPreferredSize(new Dimension(192, 108));
		addMouseListener(new MouseAdapter() {
						@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("clicked");
			}
		});
		
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawLine(0, 0, 50, 50);
		g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), this);
		// g.drawImage(img.getImage(), 100, 100, this);
		System.out.println("painted smallpic");
		System.out.println(img.getImage().getWidth(this) + " " + getWidth());
	}
}
