package imgAlg;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View extends JFrame {

	private Model model;
	private CenterPanel cPan;
	private SouthPanel sPan;
	private JMenuItem fade;

	public View(Model model) {
		this.model = model;
		MyFileChooser fC = new MyFileChooser(this);
		setLayout(new BorderLayout());
		setSize(1600, 900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		fC.selectImages();

		cPan = new CenterPanel();
		add(cPan, BorderLayout.CENTER);

		sPan = new SouthPanel(model, cPan);
		sPan.addComps();
		sPan.revalidate();
		
		JScrollPane scroll = new JScrollPane(sPan, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroll, BorderLayout.SOUTH);
		
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		fade = new JMenuItem("start/stop fading");
		fade.setAccelerator(KeyStroke.getKeyStroke("control f"));
		setJMenuBar(bar);
		bar.add(file);
		file.add(fade);
		
		setVisible(true);
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
					model.addImage(new MyImage(img, model.getBigDim().width, model.getBigDim().height));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			revalidate();
		}
	}

	public SouthPanel getsPan() {
		return sPan;
	}

	public CenterPanel getcPan() {
		return cPan;
	}
	
	@Override
	public void update(Graphics g) {
		paintComponents(g);
	}

	public JMenuItem getFade() {
		return fade;
	}
}
