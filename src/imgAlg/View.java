package imgAlg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View extends JFrame {

	private Model model;
	private CenterPanel cPan;
	private SouthPanel sPan;
	private JMenuBar bar;

	public View(Model model) {
		this.model = model;
		MyFileChooser fC = new MyFileChooser(this);
		setLayout(new BorderLayout());
		setSize(1600, 900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		sPan = new SouthPanel(model);
		add(sPan, BorderLayout.SOUTH);
		pack();
		setVisible(true);

		fC.selectImages();
		sPan.addComps();
		sPan.revalidate();
		sPan.repaint();
	}

	class MyFileChooser extends JFileChooser {
		private final Component owner;

		public MyFileChooser(Component owner) {
			this.owner = owner;
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png");
			setFileFilter(filter);
			setMultiSelectionEnabled(true);
			setCurrentDirectory(new File("C:\\Users\\Johann Hoffer\\OneDrive\\images"));
		}

		public void selectImages() {
			showOpenDialog(owner);
			File[] files = getSelectedFiles();
			MediaTracker mt = new MediaTracker(this);

			try {
				for (int i = 0; i < files.length; i++) {
					Image img = ImageIO.read(files[i]);
					mt.addImage(img, i);
					mt.waitForAll();
					System.out.println(img);
					model.addImage(new MyImage(img, model.getBigDim().width, model.getBigDim().height));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			revalidate();
		}
	}
}
