package Hw4;

public class EndBlock extends Block{

	public EndBlock(int r, int c) {
		super(r, c);
		this.value[0] = "D";
	}
	
	@Override
	public void reset() {
		super.reset();
		this.value[0] = "D";
	}
	
	@Override
	public boolean isDest() {
		return true;
	}

}
