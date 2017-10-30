package imgAlg;

public class Controller {
	
	private Model model;
	private View view;
	
	public Controller(int w, int h) {
		model = new Model(w, h);
		view = new View(model);
		
	}
}
