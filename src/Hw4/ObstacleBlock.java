package Hw4;

public class ObstacleBlock extends Block {
	public ObstacleBlock(int r, int c) {
		super(r, c);
		this.value[0] = "*";
	}
	
	@Override
	public void reset() {
		super.reset();
		this.value[0] = "*";
	}
	

	
	@Override
	public boolean canAddRelatives() {
		return false;
	}
	
	public boolean discoveredQuickPlan() {
		return true;
	}
}
