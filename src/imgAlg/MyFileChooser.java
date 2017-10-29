package imgAlg;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFileChooser extends JFileChooser{
	public MyFileChooser(JFrame owner, Model model) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png");
		setFileFilter(filter);
		setMultiSelectionEnabled(true);
		showOpenDialog(owner);
		File[] files = getSelectedFiles();
		try {
			for (int i = 0; i < files.length; i++) {
				Image img = ImageIO.read(files[i]);
				model.addImage(new MyImage(img.getScaledInstance(model., height, hints));
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
