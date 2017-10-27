package imgAlg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyFileChooser extends JFileChooser{

	public MyFileChooser(View view, ArrayList<BufferedImage> images) {
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "png");
		setFileFilter(filter);
		setMultiSelectionEnabled(true);
		showOpenDialog(view);
		File[] files = getSelectedFiles();
		
		for (File file : files) {
			try {
				images.add(ImageIO.read(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
