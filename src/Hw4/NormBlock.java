package Hw4;

public class NormBlock extends Block{
	public NormBlock(int r, int c) {
		super(r, c);
		this.value[0] = "0";
	}

	public void reset() {
		super.reset();
		this.value[0] = "0";
	}
}
