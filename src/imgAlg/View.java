package imgAlg;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class View extends JFrame{

	private Model model;
	private BigPicComp centerComp;
	private PreviewPanel previewPan;
	
	
	public View(Model model) {
		this.model = model;
		setSize(model.getInitSize());
		setLayout(new BorderLayout());
		
		centerComp = new BigPicComp();
		add(centerComp, BorderLayout.CENTER);
		
		
		setVisible(true);
	}
}
