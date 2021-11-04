package Hw4;

import java.util.Arrays;

abstract class Block {
	public int row, col;
	public String[] value;
	public Block stackLink, quickPlanLink;
	public int layer;
	public Block[] relatives;
	public int relativesNum;
	public boolean inValModStack;

	/* First: Row, Second: Col */
	public Block(int r, int c) {
		this.row = r;
		this.col = c;
		this.layer = -1;
		this.relatives = new Block[4];
		this.relativesNum = 0;
		this.quickPlanLink = null;
		this.value = new String[5];
		this.inValModStack = false;
		Arrays.fill(this.value, " ");
	}

	public boolean canAddRelatives() {
		if (relativesNum != 4) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean discoveredQuickPlan() {
		if (quickPlanLink == null) {
			return false;
		} else {
			return true;
		}
	}

	public void addRelatives(Block block) {
		if (canAddRelatives()) {
			if (relativesNum < 4) {
				relatives[relativesNum] = (Block) block;
				relativesNum++;
			} else {
				System.out.println("TOO MANY Relatives!!");
			}
		}
		return;
	}
	
	public Block popRelative() {
		if(relativesNum == 0) return null;
		
		Block temp = relatives[0];
		for(int i = 0; i < relativesNum-1; i++) {
			relatives[i] = relatives[i+1];
		}
		relativesNum--;
		return temp;
	}

	public void reset() {
		this.layer = -1;
		this.relatives = new Block[4];
		this.relativesNum = 0;
		this.quickPlanLink = null;
		this.value = new String[5];
		this.inValModStack = false;
		Arrays.fill(this.value, " ");
	}

	public boolean isStart() {
		return false;
	}

	public boolean isDest() {
		return false;
	}

	public int distance(Block b) {
		int temp = (row - b.row)*(row - b.row) + (col - b.col)*(col - b.col);
		return temp;
	}
}
