package imgAlg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

public class SouthPanel extends JPanel{
	
	private Model model;
	
	public SouthPanel(Model model) {
		setLayout(new FlowLayout());
		this.model = model;
	}
	
	public void addComps() {
		System.out.println(model.getImages().size());
		for (int i = 0; i < model.getImages().size(); i++) {
			//System.out.println("added:" + model.getImages().get(i).getWidth());
			add(new PreviewImageComponent(model.getImages().get(i)));
		}
	} 
}
