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
import javax.swing.JOptionPane;
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
		System.out.println(model.getIMG_WIDTH());
		cPan = new CenterPanel(model);
		add(cPan, BorderLayout.CENTER);

		sPan = new SouthPanel(model, cPan);
		sPan.addComps();
		sPan.revalidate();
		
		JScrollPane scroll = new JScrollPane(sPan, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroll, BorderLayout.SOUTH);
		
		JMenuBar bar = new JMenuBar();
		JMenu fader = new JMenu("Fade");
		fade = new JMenuItem("start/stop fading");
		fade.setAccelerator(KeyStroke.getKeyStroke("control f"));
		setJMenuBar(bar);
		bar.add(fader);
		fader.add(fade);
		
		
		
		JMenu histo = new JMenu("Histogramm");
		JMenuItem create = new JMenuItem("create Histogramm");
		create.addActionListener(e -> {
			cPan.createHistogramm(cPan.getCurrentImg());
		});
		JMenuItem approx = new JMenuItem("approx");
		approx.addActionListener(e -> {
			cPan.name();
		});
		
		histo.add(create);
		histo.add(approx);
		
		JMenu settings = new JMenu("Settings");
		JMenuItem translation = new JMenuItem("Translation Value");
		JMenuItem upscaling = new JMenuItem("Upscaling Value");
		JMenuItem downscaling = new JMenuItem("Downscaling Value");		
		JMenuItem xshearing = new JMenuItem("x-Shearing Value");
		JMenuItem yshearing = new JMenuItem("y-Shearing Value");
		JMenuItem rotate = new JMenuItem("Rotation Value");
		
		translation.addActionListener(e -> {
			String s = JOptionPane.showInputDialog("x: ");
			try {
				model.setStandardTranslation(Integer.parseInt(s));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Nur Integer-Werte!");
			}
		});
		
		rotate.addActionListener(e -> {
			String s = JOptionPane.showInputDialog("Angle (degree): ");
			try {
				model.setStandardRotation(Integer.parseInt(s));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Nur Integer-Werte!");
			}
		});
		
		downscaling.addActionListener(e -> {
			String s = JOptionPane.showInputDialog("scalefactor: ");
			try {
				model.setStandardDownScaling(Double.parseDouble(s));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Nur Double-Werte!");
			}
		});
		
		upscaling.addActionListener(e -> {
			String s = JOptionPane.showInputDialog("scalefactor: ");
			try {
				model.setStandardUpScaling(Double.parseDouble(s));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Nur Double-Werte!");
			}
		});
		
		xshearing.addActionListener(e -> {
			String s = JOptionPane.showInputDialog("scalefactor: ");
			try {
				model.setStandardUpScaling(Double.parseDouble(s));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Nur Double-Werte!");
			}
		});
		
		yshearing.addActionListener(e -> {
			String s = JOptionPane.showInputDialog("scalefactor: ");
			try {
				model.setStandardUpScaling(Double.parseDouble(s));
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Nur Double-Werte!");
			}
		});
		
		bar.add(settings);
		settings.add(rotate);
		settings.add(yshearing);
		settings.add(xshearing);
		settings.add(downscaling);
		settings.add(upscaling);
		settings.add(translation);
		

		bar.add(histo);
		
		MenuPanel mP = new MenuPanel(cPan, model);
		add(mP, BorderLayout.WEST);
		
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
					model.addImage(new MyImage(img, model.getIMG_WIDTH(), model.getIMG_HEIGHT()));
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
