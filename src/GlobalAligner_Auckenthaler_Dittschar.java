package assignment02;


import java.io.IOException;
import assignment01.FastA_Auckenthaler_Dittschar;
/**
 * GlobalAligner_Auckenthaler_Dittschar
 * Autors: Clarissa Auckenthaler & Marina Dittschar
 * Sequence Bioinformatics, WS 22/23
 */
public class GlobalAligner_Auckenthaler_Dittschar {
	public static void main(String[] args) throws IOException {
		if(args.length<1 || args.length>2)
			throw new IOException("Usage: GlobalAligner_Auckenthaler_Dittschar infile [quadraticSpace|linearSpace|noDP]");

		var list=FastA_Auckenthaler_Dittschar.read(args[0]);
		runNeedlemanWunschQuadraticSpace(list.get(0), list.get(1));
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
	public static void runNeedlemanWunschQuadraticSpace(FastA_Auckenthaler_Dittschar.Pair x, FastA_Auckenthaler_Dittschar.Pair y) {
		// todo: implement, Assignment 2.1
		// initialization
		char[] xchar = x.sequence().toCharArray();
		char[] ychar = y.sequence().toCharArray();
		int match = 1;
		int mismatch = -1;
		int gap = 1;


		Integer[][] nw_matrix = new Integer[xchar.length][ychar.length];
		for (int i=0; i<xchar.length; i++){
			nw_matrix[i][0] = -i;
		}
		for (int j=0; j<xchar.length; j++){
			nw_matrix[0][j] = -j;
		}
		int up = 0;
		int left = 0;
		int diagonal = 0;
		// matrix filling
		for (int i=1; i<xchar.length;i++){
			for (int j=1; j<ychar.length; j++){

				if (xchar[i] == ychar[j]){
					diagonal = match ;
				}
				else{
					diagonal = mismatch;
				}
				up = nw_matrix[i-1][j] - gap;
				left = nw_matrix[i][j-1] - gap;
				nw_matrix[i][j] = Math.max(up, Math.max(left, diagonal));
			}
		}
		for(Integer[] i : nw_matrix) {
			for(int j : i) {
				//print row
				System.out.print(j + "\t");
			}
			// new row
			System.out.println();
		}
	}

	/**
	 * computes the optimal global alignment score and an alignment, using linear space
	 * Prints out the optimal score and a corresponding alignment
	 * Also prints out the number of milliseconds required for the computation
	 * @param x
	 * @param y
	 */
	public static void runNeedlemanWunschLinearSpace(FastA_Auckenthaler_Dittschar.Pair x, FastA_Auckenthaler_Dittschar.Pair y) {
		// todo: implement, Assignment 2.2
	}

	/**
	 * computes the optimal global alignment score using a recursion and no table
	 * Prints out the optimal score
	 * Also prints out the number of milliseconds required for the computation
	 * @param x
	 * @param y
	 */
	public static void runNeedlemanWunschRecursively(FastA_Auckenthaler_Dittschar.Pair x, FastA_Auckenthaler_Dittschar.Pair y) {
		// todo: implement using recursive function computeF, , Assignment 2.3
	}

	public static int computeF(int i,int j) {
		// todo: implement
		return 0;
	}
}
