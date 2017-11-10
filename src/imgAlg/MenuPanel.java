package imgAlg;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	
	private CenterPanel cPan;
	
	private JButton col_1;
	private JButton col_2;
	private JButton reset;
	
	private MenuPanelButton drawLine;
	private MenuPanelButton drawCircle;
	private MenuPanelButton drawFilledCircle;
	private MenuPanelButton cut;
	
	private MenuPanelButton current;
	
	public MenuPanel(CenterPanel cPan) {
		setLayout(new FlowLayout());
		setBackground(Color.DARK_GRAY);
		this.cPan = cPan;
		
		col_1 = new JButton();
		col_1.setBackground(new Color(0xffff0000));
		col_1.setPreferredSize(new Dimension(30, 30));
		add(col_1);
		
		col_2 = new JButton();
		col_2.setBackground(new Color(0xff00ff00));
		col_2.setPreferredSize(new Dimension(30, 30));
		add(col_2);
		
		drawLine = new MenuPanelButton("icons/line_white.png", "icons/line_green.png");
		add(drawLine);
		
		drawCircle = new MenuPanelButton("icons/circle_white.png", "icons/circle_green.png");
		add(drawCircle);
		
		drawFilledCircle = new MenuPanelButton("icons/filled_circle_white.png", "icons/filled_circle_green.png");
		add(drawFilledCircle);
		
		cut = new MenuPanelButton("icons/cut_white.png", "icons/cut_green.png");
		add(cut);
		
		reset = new JButton();
		reset.setPreferredSize(new Dimension(30, 30));
		reset.setIcon(new ImageIcon("icons/reset.png"));
		add(reset);
		
		current = drawLine;
		addListeners();
		setPreferredSize(new Dimension(40, 200));
	}
	private void addListeners() {
		col_1.addActionListener(e -> {
			Color color = JColorChooser.showDialog(this, "Choose color", col_1.getBackground());
			col_1.setBackground(color);
			col_2.setBackground(color);
			cPan.setCol_1(color.getRGB());
			cPan.setCol_2(color.getRGB());
		});
		
		col_2.addActionListener(e -> {
			Color color = JColorChooser.showDialog(this, "Choose color", col_2.getBackground());
			col_2.setBackground(color);
			cPan.setCol_2(color.getRGB());
		});
		
		drawLine.addActionListener(e -> {
			current.deactivate();
			cPan.setMode("line");
			drawLine.activate();
			current = drawLine;
		});
		
		drawCircle.addActionListener(e -> {
			current.deactivate();
			cPan.setMode("circle");
			drawCircle.activate();
			current = drawCircle;
		});
		
		drawFilledCircle.addActionListener(e -> {
			current.deactivate();
			cPan.setMode("filled circle");
			drawFilledCircle.activate();
			current = drawFilledCircle;
		});
		
		cut.addActionListener(e -> {
			current.deactivate();
			cPan.setMode("cut");
			cut.activate();
			current = cut;
		});
		
		reset.addActionListener(e -> {
			cPan.getCurrentImg().resetPixel();
			cPan.repaint();
		});
	}
}
