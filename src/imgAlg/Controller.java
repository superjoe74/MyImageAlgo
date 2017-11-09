package imgAlg;

public class Controller {
	
	private Model model;
	private View view;
	private ImageFade imageFader;
	
	public Controller(int w, int h) {
		model = new Model(w, h);
		view = new View(model);
		view.getFade().addActionListener(e -> {
			if (imageFader == null) {
				imageFader = new ImageFade(model.getImages(), view.getcPan(), view.getsPan());
			} else {
				imageFader.setActive(false);
				imageFader = null;
			}
		});
	}
}
