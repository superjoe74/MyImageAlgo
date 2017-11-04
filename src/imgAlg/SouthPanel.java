package imgAlg;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class SouthPanel extends JPanel{
	
	private Model model;
	private CenterPanel cPan;
	private ArrayList<PreviewImageComponent> imageComponents;
	private ImageFade fade;
	
	public SouthPanel(Model model, CenterPanel cPan) {
		setLayout(new FlowLayout());
		this.model = model;
		this.cPan = cPan;
		imageComponents = new ArrayList<PreviewImageComponent>(10);
		fade = new ImageFade(imageComponents, cPan);
	}
	
	public void addComps() {
		for (int i = 0; i < model.getImages().size(); i++) {
			PreviewImageComponent pic = new PreviewImageComponent(model.getImages().get(i), cPan);
			pic.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					cPan.setImage(pic.getImg().getPixel());
					if(SwingUtilities.isRightMouseButton(e))
						pic.setSelected(!pic.isSelected());
//						if(pic.isSelected() == true && (fade == null || fade.isActive() == false))
//							fade = new ImageFade(imageComponents, cPan);
				}
			});
			add(pic);
			imageComponents.add(pic);
		}
	}

	public ArrayList<PreviewImageComponent> getImageComponents() {
		return imageComponents;
	} 
}
