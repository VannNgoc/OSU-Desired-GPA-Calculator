
public class DesiredGPA
{
	public static void main(String[] args)
	{
		DesiredGPAView view = new DesiredGPAView();
		DesiredGPAModel model = new DesiredGPAModel();
		DesiredGPAController controller = new DesiredGPAController(view, model);

		view.registerObserver(controller);
	}
}
