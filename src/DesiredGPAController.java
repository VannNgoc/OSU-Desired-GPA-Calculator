public class DesiredGPAController {

private final DesiredGPAView view;
private final DesiredGPAModel model;

public DesiredGPAController(DesiredGPAView view, DesiredGPAModel model) {
	this.view = view;
	this.model = model;
}


public void processAddClass() {
	this.view.updateAddClass();
	
}
public void processRemClass() {
	this.view.updateRemClass();	
}


public void processCalculate() {
	
	this.model.seeLetterArray(this.view.getUpperBounds(), this.view.getLowerBounds(),this.view.getClassNameArray(),this.view.getClassCredits(),this.view.gettGPALimit(), this.view.gettDesiredGPA(),this.view.gettCurrentGPA(),this.view.gettTotalCred());
	this.view.updateCalc(this.model.outcomes());
}

}
