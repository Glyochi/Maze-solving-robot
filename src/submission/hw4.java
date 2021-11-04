package submission;

import java.io.IOException;
import submission.RobotPath;

public class hw4 {
	public static void main(String[] args) throws IOException{
		RobotPath rPath = new RobotPath();
		rPath.readInput(args[0]);

		System.out.println("\n planShortest:\n");
		rPath.planShortest();
		rPath.output();
		
		System.out.println("\n quickPlan:\n");
		rPath.quickPlan();
		rPath.output();
	}
}
