package imgAlg;

public class Controller {
	
	private Model model;
	private View view;
	
	public Controller() {
		model = new Model(1600, 900);
		view = new View(model);
	}
	
}
