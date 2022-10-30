package assignment02;


import java.io.IOException;
import assignment01.FastA_Auckenthaler_Dittschar;
import java.util.Arrays;
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

		runNeedlemanWunschRecursively(list.get(0),list.get(1));


		if(list.size()!=2)
			throw new IOException("Wrong number of input sequences: "+list.size());


		var mode=(args.length==2?args[1]:"linearSpace");

		switch(mode) {
			case "quadraticSpace" -> runNeedlemanWunschQuadraticSpace(list.get(1),list.get(0));
			case "linearSpace" ->runNeedlemanWunschLinearSpace(list.get(1),list.get(0));
			case "noDP" ->runNeedlemanWunschQuadraticSpace(list.get(1),list.get(0));
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

		Integer[][] nw_matrix = new Integer[xchar.length+1][ychar.length+1];
		Integer[][] traceback_matrix = new Integer[xchar.length+1][ychar.length+1];

		String alignedseq1 = "";
		String mid = "";
		String alignedseq2 = "";

		nw_matrix[0][0]= 0;

		for (int i=1; i<xchar.length+1; i++){
			nw_matrix[i][0] = nw_matrix[i-1][0]-gap;
		}
		for (int j=1; j<ychar.length+1; j++){
			nw_matrix[0][j] = nw_matrix[0][j-1]-gap;
		}
		int up = 0;
		int left = 0;
		int diagonal = 0;
		// matrix filling
		for (int i=1; i<xchar.length+1;i++){
			for (int j=1; j<ychar.length+1; j++){

				if (xchar[i-1] == ychar[j-1]){
					diagonal = nw_matrix[i-1][j-1]+ match;
				}
				else{
					diagonal = nw_matrix[i-1][j-1] +mismatch;
				}
				up = nw_matrix[i-1][j] - gap;
				left = nw_matrix[i][j-1] - gap;
				nw_matrix[i][j] = Math.max(up, Math.max(left, diagonal));
				//creating tracebackmatrix
				Integer[] traceback_arr = {up, left, diagonal};
				int tracer = argmax(traceback_arr);
				traceback_matrix[i][j]= tracer;

			}
		}

		prettyPrint(nw_matrix);

		//generating alignment sequence
		int i = traceback_matrix.length-1;
		int j = traceback_matrix[0].length-1;

		while (i!= 0 && j!= 0){
			if (traceback_matrix[i][j]==2){
				alignedseq1 = xchar[i-1]+ alignedseq1;
				alignedseq2 = ychar[j-1] + alignedseq2;
				i -= 1;
				j -= 1;
			}
			else if(traceback_matrix[i][j]==1){
				alignedseq1 =  "-" + alignedseq1;
				alignedseq2 = ychar[j-1]+ alignedseq2;

				j -= 1;

			}
			else{
				alignedseq1 =  xchar[i-1] + alignedseq1;
				alignedseq2 = "-" + alignedseq2;
				i -= 1;
			}
		}
		System.out.println("The alignment score is " + nw_matrix[nw_matrix.length-1][nw_matrix[0].length-1]);
		System.out.println(alignedseq1);
		System.out.println(alignedseq2);

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
		char[] xchar = x.sequence().toCharArray();
		char[] ychar = y.sequence().toCharArray();
		int match = 1;
		int mismatch = -1;
		int gap = 1;
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
		char[] xchar = x.sequence().toCharArray();
		char[] ychar = y.sequence().toCharArray();
		int match = 1;
		int mismatch = -1;
		int gap = 1;
		int matchscore= -1;

		int xlength = xchar.length;
		int ylength = ychar.length;
		System.out.println(xlength);
		System.out.println(ylength);
		int[][] maxAlign = new int[xlength+1][ylength+1];

		for(int i = 0; i < xlength+1; i++) {
			for( int j= 0; j< ylength+1; j++){
				if(i == 0 && j== 0){
					maxAlign[i][j]= 0;
				}
				else if (i== 0 && j!=0)
					maxAlign[i][j]= -j * gap;
				else if (i!= 0 && j==0)
					maxAlign[j][i] = -i* gap;

				else{
					if (xchar[i-1] == ychar[j-1]){
						matchscore =  match;
					}
					else{
						matchscore = mismatch;
					}

					int top_left= maxAlign[i-1][j-1]+ matchscore;
					int top = maxAlign[i][j-1]+gap;
					int left = maxAlign[i-1][j]+gap;

					maxAlign[i][j]= Math.max(top_left, Math.max(top, left));
				}
			}
		}
		System.out.println("runNeedlemanWunschRecursively alignment score: "+maxAlign[xlength][ylength]);
	}


	public static void prettyPrint(Integer[][] nw_matrix){
		for(Integer[] i : nw_matrix) {
			for(int j : i) {
				//print row
				System.out.print(j + "\t");
			}
			// new row
			System.out.println();
		}
	}
	public static Integer argmax(Integer[] array) {
		int re = Integer.MIN_VALUE;
		int arg = -1;
		for (int i = 0; i < array.length; i++) {
			if (array[i] > re) {
				re = array[i];
				arg = i;
			}
		}
		return arg;
	}

	public static int computeF(int i,int j) {
		// todo: implement
		return 0;
	}
}
