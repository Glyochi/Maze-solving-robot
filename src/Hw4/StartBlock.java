package Hw4;

public class StartBlock extends Block{
	public StartBlock(int r, int c) {
		super(r, c);
		this.value[0] = "S";
		this.layer = 0;
	}
	
	@Override
	public void reset() {
		super.reset();
		this.value[0] = "S";
		this.layer = 0;
	}
	

	@Override
	public boolean isStart() {
		return true;
	}

//	@Override
//	public boolean canAddRelatives() {
//		return false;
//	}
	
	public boolean discoveredQuickPlan() {
		return true;
	}
}
