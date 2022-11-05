package assignment03;

import java.io.IOException;
import java.util.Arrays;


/**
 * count number of edges between nucleotides in different sequences and the number of mixed cycles
 * Autors: Clarissa Auckenthaler und Marina Dittschar
 * Sequence Bioinformatics, WS22/23
 */

public class CountEdgesSimpleMixedCycles_Auckenthaler_Dittschar {
	public static void main(String[] args) throws IOException {
		if (args.length != 3)
			throw new IOException("Usage: CountEdgesSimpleMixedCycles__Auckenthaler_Dittschar aLength bLength cLength");

		var length = new int[]{Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2])};

		System.out.println(CountEdgesSimpleMixedCycles_Auckenthaler_Dittschar.class.getSimpleName());

		System.out.printf("Sequence lengths: %d %d %d%n", length[0], length[1], length[2]);

		// todo: report the number of edges between nucleotides in different sequences

		var numEdgesBetweenDifferenteSequences = countAllEdges(length);

		System.out.printf("Edges between different sequences: %d%n", numEdgesBetweenDifferenteSequences);

		var numSimpleMixedCycles2 = 0;

		// todo: implement counting of number of simple mixed cycles
		// first compute the number of simple mixed cycles that use two cycles
		for (int no_seq = 0; no_seq < length.length; no_seq++) {
			int sequence1 = length[2];
			int sequence2 = length[0];
			if (no_seq!= length.length - 1){
				sequence1 = length[no_seq];
				sequence2 = length[no_seq + 1];
			}
			for (int i = 0; i < sequence1; i++) {
				for (int j = 0; j < sequence1; j++) {
					for (int k = 0; k < sequence2; k++) {
						for (int l = 0; l < sequence2; l++) {
							if (k !=l || i != j) {
								numSimpleMixedCycles2 += 1;
							}
						}
					}
				}
			}
		}
		System.out.println(numSimpleMixedCycles2);
		// then compute and add the number of simple mixed cycles that use three cycles
		int numSimpleMixedCycles3 = 0;
		numSimpleMixedCycles3= numSimpleMixedCycles2;
		for (int i = 0; i < length[0]; i++) {
			for (int j = 0; j < length[0]; j++) {
				for (int k = 0; k < length[1]; k++) {
					for (int l = 0; l < length[1]; l++) {
						for (int m = 0; m < length[2]; m++) {
							for (int n = 0; n < length[2]; n++) {
								if ((i != j) || (k != l) || (m!=n)) {
									numSimpleMixedCycles3 += 2;
								}
							}
						}
					}
				}
			}
		}
		System.out.printf("Total simple mixed cycles: %d%n", numSimpleMixedCycles3);
	}

	public static int countAllEdges(int[] lengths) {
		return lengths[0] * lengths[1] + lengths[2] * lengths[1] + lengths[0] * lengths[2];
	}
}
