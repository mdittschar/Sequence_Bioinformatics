package assignment02;

import assignment01.FastA_YOUR_NAME;

import java.io.IOException;

/**
 * GlobalAligner_YOUR_NAME
 * Sequence Bioinformatics, WS 22/23
 */
public class GlobalAligner_YOUR_NAME {
	public static void main(String[] args) throws IOException {
		if(args.length<1 || args.length>2)
			throw new IOException("Usage: GlobalAligner_YOUR_NAME infile [quadraticSpace|linearSpace|noDP]");

		var list=FastA_YOUR_NAME.read(args[0]);

		if(list.size()!=2)
			throw new IOException("Wrong number of input sequences: "+list.size());

		var mode=(args.length==2?args[1]:"quadraticSpace");

		switch(mode) {
			case "quadraticSpace" -> runNeedlemanWunschQuadraticSpace(list.get(0),list.get(1));
			case "linearSpace" ->runNeedlemanWunschLinearSpace(list.get(0),list.get(1));
			case "noDP" ->runNeedlemanWunschQuadraticSpace(list.get(0),list.get(1));
			default -> throw new IOException("Unknown mode: "+mode);
		}
	}

	/**
	 * computes the optimal global alignment score and an alignment, using quadratic space.
	 * Prints out the optimal score and a corresponding alignment
	 * Also prints out the number of milliseconds required for the computation
	 * @param x
	 * @param y
	 */
	public static void runNeedlemanWunschQuadraticSpace(FastA_YOUR_NAME.Pair x, FastA_YOUR_NAME.Pair y) {
		// todo: implement, Assignment 2.1
	}

	/**
	 * computes the optimal global alignment score and an alignment, using linear space
	 * Prints out the optimal score and a corresponding alignment
	 * Also prints out the number of milliseconds required for the computation
	 * @param x
	 * @param y
	 */
	public static void runNeedlemanWunschLinearSpace(FastA_YOUR_NAME.Pair x, FastA_YOUR_NAME.Pair y) {
		// todo: implement, Assignment 2.2
	}

	/**
	 * computes the optimal global alignment score using a recursion and no table
	 * Prints out the optimal score
	 * Also prints out the number of milliseconds required for the computation
	 * @param x
	 * @param y
	 */
	public static void runNeedlemanWunschRecursively(FastA_YOUR_NAME.Pair x, FastA_YOUR_NAME.Pair y) {
		// todo: implement using recursive function computeF, , Assignment 2.3
	}

	public static int computeF(int i,int j) {
		// todo: implement
		return 0;
	}
}
