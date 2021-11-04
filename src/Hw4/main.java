package Hw4;

import java.io.IOException;


import Hw4.RobotPath;

public class main {
	public static void main(String[] args) throws IOException {
//		FIFOStack s = new FIFOStack(new StartBlock(0,0));
//		System.out.println("1: " + s.length); 
//		
//		s.add(new NormBlock(1,1));
//		System.out.println("2: " + s.length);
//		
//		Block b = s.pop();
//		System.out.println("0 0: "+ b.row + " " + b.col);
//		System.out.println("1: " + s.length);
//		
//		b = s.pop();
//		System.out.println("1 1: "+ b.row + " " + b.col);
//		System.out.println("0: " + s.length);
//		
//		b = s.pop();
//		System.out.println("true: " + (b == null));
//		System.out.println("0: " + s.length);
//		
		
		
		RobotPath rPath = new RobotPath();
		rPath.readInput("maze7.txt");

//		System.out.println("\n planShortest:\n");
//		rPath.planShortest();
//		rPath.output();
		
		System.out.println("\n quickPlan:\n");
		rPath.quickPlan();
		rPath.output();
		
	}
}
