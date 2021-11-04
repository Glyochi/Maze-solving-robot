package Hw4;

public class FIFOStack {
	int length;
	Block head;
	Block tail;

	public FIFOStack(Block start) {
		length = 1;
		head = start;
		tail = start;
	}

	public boolean isEmpty() {
		if (length == 0)
			return true;
		return false;
	}

	public Block pop() {
		if (length == 0)
			return null;

		Block temp = head;
		head = head.stackLink;
		
		length--;
		return temp;
	}

	public void add(Block block) {
		if (block == null)
			return;
		if (length == 0) {
			head = block;
			tail = block;
		} else if(length == 1){
			tail = block;
			head.stackLink = block;
		}else {
			tail.stackLink = block;
			tail = block;
		}
		length++;
		return;
	}
	
	public void clear() {
		length = 0;
	}
}
