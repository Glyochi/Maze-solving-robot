package Hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class RobotPath {
	Block[][] maze;
	StartBlock start;
	EndBlock end;
	FIFOStack stack;

	public RobotPath() {
	};

	public void readInput(String FileName) throws IOException {
		File file;
		Scanner scanner;
		try {
			file = new File(FileName);
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("File not found");
		}

		int row = 0, col = 0;

		String temp = scanner.next();
		if ("nrows".equals(temp)) {
			row = scanner.nextInt();
			temp = scanner.next();
		} else {
			scanner.close();
			return;
		}

		if ("ncols".equals(temp)) {
			col = scanner.nextInt();
			temp = scanner.next();
		} else {
			scanner.close();
			return;
		}

		maze = new Block[row][col];

		if ("start".equals(temp)) {
			row = scanner.nextInt();
			col = scanner.nextInt();
			start = new StartBlock(row, col);
			stack = new FIFOStack(start);
			temp = scanner.next();
		} else {
			scanner.close();
			return;
		}

		if ("dest".equals(temp)) {
			row = scanner.nextInt();
			col = scanner.nextInt();
			end = new EndBlock(row, col);
			temp = scanner.next();
		} else {
			scanner.close();
			return;
		}

		if ("obstacles".equals(temp)) {
			for (int r = 0; r < maze.length; r++) {
				for (int c = 0; c < maze[0].length; c++) {
					maze[r][c] = new NormBlock(r, c);
				}
			}

			maze[start.row][start.col] = start;
			maze[end.row][end.col] = end;

			while (scanner.hasNextInt()) {
				row = scanner.nextInt();
				col = scanner.nextInt();
				maze[row][col] = new ObstacleBlock(row, col);
			}

			scanner.close();

		} else {
			scanner.close();
			return;
		}

	};
	
	/* Relatives in quick plan represent parent blocks
	 * 
	 */
	public void planShortest() {
		resetMaze();
		stack.clear();
		stack.add(start);

		

		int blocksBetween = -1;
		Block child, parent;

		while (!stack.isEmpty()) {
			parent = stack.pop();
			
			//If Destination block has been found, then all blocks' layers must be less than Destination's layer
			if (blocksBetween != -1 && parent.layer > blocksBetween) {
				stack.clear();
				break;
			}

			// Checking adjacent blocks
			for (int a = 1, b = 0; b > -2; --a, --b) {
				for (int i = 0; i < 2; i++) {

					// check for indices out of bounds
					if (parent.row + a >= 0 && parent.row + a < maze.length && parent.col + b >= 0
							&& parent.col + b < maze[0].length) {

						child = maze[parent.row + a][parent.col + b];

						if (child.canAddRelatives()) {

							if (child.isDest()) {

								// If undiscovered then set blocksBetween
								if (blocksBetween == -1) {
									blocksBetween = parent.layer;
								}
								child.addRelatives(parent);

							} else {

								// Undiscovered child block
								if (child.layer == -1) {
									stack.add(child);
									child.layer = parent.layer + 1;
								}

								// Add parent connection with child block if there wasnt any other more
								// efficient path
								if (child.layer == parent.layer + 1) {
									child.addRelatives(parent);
								}

							}

						}

					}

					int temp = a;
					a = b;
					b = temp;
				}
			}
		}

		if (end.relativesNum > 0) {
			for (int i = 0; i < end.relativesNum; i++) {
				stack.add(end.relatives[i]);
				end.relatives[i].layer = -1;
				if (!end.relatives[i].isStart())
					modifyParentBlockValue(end, end.relatives[i]);
			}
		}

		blocksBetween *= -1;

		while (!stack.isEmpty()) {
			child = stack.pop();

			if (child.layer >= blocksBetween) {
				for (int i = 0; i < child.relativesNum; i++) {
					if (!child.relatives[i].isStart()) {
						modifyParentBlockValue(child, child.relatives[i]);
						child.relatives[i].layer = child.layer - 1;
					}
					if (!child.relatives[i].inValModStack) {
						stack.add(child.relatives[i]);
						child.relatives[i].inValModStack = true;
					}
				}
			}
		}
	};

	/* Relatives in quick plan represent children blocks
	 * 
	 */
	public void quickPlan() {
		resetMaze();
		stack.clear();
		stack.add(start);


		Block parent, child, lastBlock = new NormBlock(-1, -1);
		;
		int minR, minC;
		int dist;
		boolean reachedDest = false;

		while (!reachedDest) {
			// search forward
			while (!stack.isEmpty()) {
				parent = stack.pop();
				lastBlock = parent;
				minR = -1;
				minC = -1;
				dist = -1;

				for (int a = 1, b = 0; b > -2; --a, --b) {
					for (int i = 0; i < 2; i++) {

						int tempR = parent.row + a;
						int tempC = parent.col + b;
						if (tempR < 0 || tempC < 0 || tempR >= maze.length || tempC >= maze[tempR].length) {
							// DO NOTHING - OUT OF BOUNDS
						} else {
							child = maze[tempR][tempC];

							if (!child.discoveredQuickPlan()) {

								int tempDist = end.distance(child);

								// Using the first block
								if (minR == -1) {

									dist = tempDist;
									minR = tempR;
									minC = tempC;

								} else {

									// Add all the adjacent blocks, excluding the one that's going to be add in the
									// stack (minR, minC)

									if (dist > tempDist) {
										parent.addRelatives(maze[minR][minC]);
										dist = tempDist;
										minR = tempR;
										minC = tempC;

									} else if (dist == tempDist) {

										if (tempR == minR) {
											if (tempC < minC) {
												parent.addRelatives(maze[minR][minC]);
												minC = tempC;
												minR = tempR;

											} else {
												parent.addRelatives(maze[tempR][tempC]);
											}
										} else {
											if (tempR < minR) {
												parent.addRelatives(maze[minR][minC]);
												minR = tempR;
												minC = tempC;
											} else {
												parent.addRelatives(maze[tempR][tempC]);
											}
										}

									} else {
										parent.addRelatives(maze[tempR][tempC]);

									}

								}

							}
						}

						int temp = a;
						a = b;
						b = temp;
					}

				}
				// If found an unexplored block that is the closest to Destination, add it to
				// the stack and link it to its parent
				if (minR != -1) {
					child = maze[minR][minC];
					child.quickPlanLink = parent;
					if (!child.isDest()) {
						stack.add(child);
					} else {
						reachedDest = true;
					}
				}
			}

			// backtracking if reached a dead end
			if (!reachedDest) {
				while (lastBlock.relativesNum == 0) {
					lastBlock = lastBlock.quickPlanLink; // Back tracking
					if (lastBlock == null) { // if backtracked all the way back until there's no more blocks to go => no
												// available path
						return;
					}
				}
				Block temp = lastBlock.popRelative();
				temp.quickPlanLink = lastBlock;
				stack.add(temp);
			}

		}

		if (end.quickPlanLink != null) {
			stack.add(end);
			while (!stack.isEmpty()) {
				child = stack.pop();
				parent = child.quickPlanLink;

				if (!parent.isStart()) {
					modifyParentBlockValue(child, parent);
					stack.add(parent);
				}
			}
		}
	};

	/*
	 * First: Row, Second: Col s e w n
	 */
	public void modifyParentBlockValue(Block child, Block parent) {
		int deltaCol, deltaRow, direction;
		deltaRow = child.row - parent.row;
		deltaCol = child.col - parent.col;
		direction = deltaRow + 2 * deltaCol;
		// South = 1, Ease = 2, West = -2, North = -1
		if (direction == -1) {
			parent.value[1] = "n";
		} else if (direction == -2) {
			parent.value[2] = "w";
		} else if (direction == 1) {
			parent.value[3] = "s";
		} else {
			parent.value[4] = "e";
		}

		parent.value[0] = " ";

		return;

	}

	public void resetMaze() {
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[r].length; c++) {
				maze[r][c].reset();
			}
		}
		return;
	}

	public void output() {
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[0].length; c++) {
				String[] blockVal = maze[r][c].value;
				String msg = "";
				if (blockVal[0] == "0" || blockVal[0] == "S" || blockVal[0] == "D" || blockVal[0] == "*") {
					msg = blockVal[0];
				} else {
					for (int i = 0; i < 5; i++) {
						if (blockVal[i] != " ") {
							msg += blockVal[i];
						}
					}
				}
				System.out.printf("%5s", msg);
			}
			System.out.println();
		}
	};

	public String outputJunit() {
		String path = "";
		for (int r = 0; r < maze.length; r++) {
			for (int c = 0; c < maze[r].length; c++) {
				String[] blockVal = maze[r][c].value;
				String msg = "";
				if (blockVal[0] == "0" || blockVal[0] == "S" || blockVal[0] == "D" || blockVal[0] == "*") {
					msg = blockVal[0];
				} else {
					for (int i = 0; i < 5; i++) {
						if (blockVal[i] != " ") {
							msg += blockVal[i];
						}
					}
				}
				path += String.format("%5s", msg);
			}
			path += "\n";
		}
		return path;
	}
}
