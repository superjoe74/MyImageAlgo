package imgAlg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	
	private JButton col_1;
	private JButton col_2;
	private JButton drawLine;
	private JButton drawCircle;
	private JButton drawFilledCircle;
	private JButton selectArea;
	
	public MenuPanel() {
		setLayout(new FlowLayout());
		setBackground(Color.DARK_GRAY);
		col_1 = new JButton();
		col_1.setBackground(new Color(0xffff0000));
		col_1.setPreferredSize(new Dimension(30, 30));
		add(col_1);
		
		col_2 = new JButton();
		col_2.setBackground(new Color(0xff00ff00));
		col_2.setPreferredSize(new Dimension(30, 30));
		add(col_2);
		
		drawLine = new JButton();
		drawLine.setPreferredSize(new Dimension(30, 30));
		try {
			drawLine.setIcon(new ImageIcon(ImageIO.read(new File("icons/line_white.png"))));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(drawLine);
		
		setPreferredSize(new Dimension(40, 200));
	}

	public JButton getCol_1() {
		return col_1;
	}

	public JButton getCol_2() {
		return col_2;
	}

	public JButton getDrawLine() {
		return drawLine;
	}

	public JButton getDrawCircle() {
		return drawCircle;
	}

	public JButton getDrawFilledCircle() {
		return drawFilledCircle;
	}

	public JButton getSelectArea() {
		return selectArea;
	}
}
