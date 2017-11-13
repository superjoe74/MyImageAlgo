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
	private Model model;
	
	private JButton col_1;
	private JButton col_2;
	private JButton reset;
	private JButton move_up;
	private JButton move_right;
	private JButton move_down;
	private JButton move_left;
	private JButton x_shear;
	private JButton y_shear;
	private JButton rotate;
	private JButton scale_up;
	private JButton scale_down;
	private JButton save;
	
	private MenuPanelButton drawLine;
	private MenuPanelButton drawCircle;
	private MenuPanelButton drawFilledCircle;
	private MenuPanelButton cut;
	
	private MenuPanelButton current;
	
	public MenuPanel(CenterPanel cPan, Model model) {
		setLayout(new FlowLayout());
		setBackground(Color.DARK_GRAY);
		this.cPan = cPan;
		this.model = model;
		
		col_1 = new JButton();
		col_1.setBackground(new Color(0xffff0000));
		col_1.setPreferredSize(new Dimension(30, 30));
		add(col_1);
		
		col_2 = new JButton();
		col_2.setBackground(new Color(0xff00ff00));
		col_2.setPreferredSize(new Dimension(30, 30));
		add(col_2);
		
		drawLine = new MenuPanelButton("src/icons/line_white.png", "src/icons/line_green.png");
		add(drawLine);
		
		drawCircle = new MenuPanelButton("src/icons/circle_white.png", "src/icons/circle_green.png");
		add(drawCircle);
		
		drawFilledCircle = new MenuPanelButton("src/icons/filled_circle_white.png", "src/icons/filled_circle_green.png");
		add(drawFilledCircle);
		
		cut = new MenuPanelButton("src/icons/cut_white.png", "src/icons/cut_green.png");
		add(cut);
		
		move_up = new JButton();
		move_up.setPreferredSize(new Dimension(30, 30));
		move_up.setIcon(new ImageIcon("src/icons/move_up.png"));
		add(move_up);
		
		move_right = new JButton();
		move_right.setPreferredSize(new Dimension(30, 30));
		move_right.setIcon(new ImageIcon("src/icons/move_right.png"));
		add(move_right);
		
		move_down = new JButton();
		move_down.setPreferredSize(new Dimension(30, 30));
		move_down.setIcon(new ImageIcon("src/icons/move_down.png"));
		add(move_down);
		
		move_left = new JButton();
		move_left.setPreferredSize(new Dimension(30, 30));
		move_left.setIcon(new ImageIcon("src/icons/move_left.png"));
		add(move_left);
		
		x_shear = new JButton();
		x_shear.setPreferredSize(new Dimension(30, 30));
		x_shear.setIcon(new ImageIcon("src/icons/x_shear.png"));
		add(x_shear);
		
		y_shear = new JButton();
		y_shear.setPreferredSize(new Dimension(30, 30));
		y_shear.setIcon(new ImageIcon("src/icons/y_shear.png"));
		add(y_shear);
		
		rotate = new JButton();
		rotate.setPreferredSize(new Dimension(30, 30));
		rotate.setIcon(new ImageIcon("src/icons/rotate.png"));
		add(rotate);
		
		scale_up = new JButton();
		scale_up.setPreferredSize(new Dimension(30, 30));
		scale_up.setIcon(new ImageIcon("src/icons/scale_up.png"));
		add(scale_up);
		
		scale_down = new JButton();
		scale_down.setPreferredSize(new Dimension(30, 30));
		scale_down.setIcon(new ImageIcon("src/icons/scale_down.png"));
		add(scale_down);
		
		save = new JButton();
		save.setPreferredSize(new Dimension(30, 30));
		save.setIcon(new ImageIcon("src/icons/save.png"));
		add(save);
		
		reset = new JButton();
		reset.setPreferredSize(new Dimension(30, 30));
		reset.setIcon(new ImageIcon("src/icons/reset.png"));
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
			cPan.resetHistoryMatrix();
			cPan.clearPix();
			cPan.repaint();
		});
		
		save.addActionListener(e -> {
			cPan.getCurrentImg().writePixel(cPan.getWorkPixel());
			cPan.clearPix();
			cPan.setUnsavedContent(false);
		});
		
		move_up.addActionListener(e -> {
			cPan.translation(0, -model.getStandardTranslation());
		});
		
		move_right.addActionListener(e -> {
			cPan.translation(model.getStandardTranslation(), 0);
		});
		
		move_down.addActionListener(e -> {
			cPan.translation(0, model.getStandardTranslation());
		});
		
		move_left.addActionListener(e -> {
			cPan.translation(-model.getStandardTranslation(), 0);
		});
		
		x_shear.addActionListener(e -> {
			cPan.xShearing(model.getStandardXShearing());
		});
		
		y_shear.addActionListener(e -> {
			cPan.yShearing(model.getStandardYShearing());
		});
		
		rotate.addActionListener(e -> {
			cPan.rotation(model.getStandardRotation(),true);
		});
		
		scale_up.addActionListener(e -> {
			cPan.scaling(model.getStandardUpScaling(), model.getStandardUpScaling());
		});
		
		scale_down.addActionListener(e -> {
			cPan.scaling(model.getStandardDownScaling(), model.getStandardDownScaling());
		});
	}
}
