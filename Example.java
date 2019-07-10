import java.lang.Math;
import java.util.*;


class Example {
		public static void main(String[] args){
			//Do *not* delete/alter the next line
			//Changing the next line will lead to loss of marks
		    long startT=System.currentTimeMillis();

		    //Edit this according to your name and login
			String name="Benjamin Roux";
			String login = "bsar3";

			GA1 ga1 = new GA1();
			ga1.start();
			GA1Candidate candidateSolution = ga1.getSolution();

			GA2 ga2 = new GA2();
			ga2.start();
			GA2Candidate candidateSolution2 = ga2.getSolution();

			Assess.checkIn(name, login, new double[]{candidateSolution.getMemberA(), candidateSolution.getMemberB()}, candidateSolution2.getMember());
      
			//Do not delete or alter the next 2 lines
			//Changing them will lead to loss of marks
			long endT= System.currentTimeMillis();
			System.out.println("Total execution time was: " +  ((endT - startT)/1000.0) + " seconds");

	  }



}
