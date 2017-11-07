package imgAlg;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SouthPanel extends JPanel{
	
	private Model model;
	private CenterPanel cPan;
	
	public SouthPanel(Model model, CenterPanel cPan) {
		setLayout(new FlowLayout());
		this.model = model;
		this.cPan = cPan;
	}
	
	public void addComps() {
		for (int i = 0; i < model.getImages().size(); i++) {
			PreviewImageComponent pic = new PreviewImageComponent(model.getImages().get(i), cPan);
			pic.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					
					if(SwingUtilities.isRightMouseButton(e)) 
						pic.getImg().setSelectedForFade(!pic.getImg().isSelectedForFade());
					else {
						cPan.setCurrentImg(pic.getImg());
						pic.repaint();
						repaint();
					}
				}
			});
			add(pic);
		}
	}
}
