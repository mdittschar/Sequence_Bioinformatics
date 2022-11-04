package assignment03;

import java.io.IOException;

/**
 * count number of edges between nucleotides in different sequences and the number of mixed cycles
 * Autors: Clarissa Auckenthaler und Marina Dittschar
 * Sequence Bioinformatics, WS22/23
 */

public class CountEdgesSimpleMixedCycles_Auckenthaler_Dittschar {
	public static void main(String[] args) throws IOException {
		if(args.length!=3)
			throw new IOException("Usage: CountEdgesSimpleMixedCycles_YOUR_Name aLength bLength cLength");

		var length=new int[]{Integer.parseInt(args[0]),Integer.parseInt(args[1]),Integer.parseInt(args[2])};

		System.out.println(CountEdgesSimpleMixedCycles_Auckenthaler_Dittschar.class.getSimpleName());

		System.out.printf("Sequence lengths: %d %d %d%n", length[0],length[1],length[2]);

		// todo: report the number of edges between nucleotides in different sequences

		var numEdgesBetweenDifferenteSequences= countAllEdges(length);


		System.out.printf("Edges between different sequences: %d%n", numEdgesBetweenDifferenteSequences);


		var numSimpleMixedCycles=0;

		// todo: implement counting of number of simple mixed cycles

		// first compute the number of simple mixed cycles that use two cycles

		// then compute and add the number of simple mixed cycles that use three cycles
		//numSimpleMixedCycles = countSimpleMixedCycles(length);
		countSimpleMixedCycles(length);
		System.out.printf("Total simple mixed cycles: %d%n", numSimpleMixedCycles);
	}

	public static int countAllEdges(int[] lengths){
		return lengths[0]*lengths[1]+lengths[2]*lengths[1]+lengths[0]*lengths[2];
	}
	public static void countSimpleMixedCycles(int[] lengths){

		binomialCoefficient(lengths[0]*lengths[1],2);
		binomialCoefficient(lengths[1]*lengths[2], 2);
		binomialCoefficient(lengths[2]*lengths[0], 2);
		//return c;
	}
	public static long factorial(long f){
		if(f <= 1){
			return 1;
		}
		return f * factorial(f-1);

	}
	public static void binomialCoefficient(long n, long c){
		System.out.println("binomial coefficient of "+n+" "+factorial(n)/(factorial(n-c)*factorial(c)));

		//return factorial(n)/(factorial(n)*factorial(c));
	}
}

