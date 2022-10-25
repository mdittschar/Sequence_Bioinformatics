package assignment01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Translate_YOUR_NAME
 * Author(s): YOUR_NAME
 * Sequence Bioinformatics, WS 22/23
 */
public class Translate_YOUR_NAME {
	public static void main(String[] args) throws IOException {
		if (args.length < 1 || args.length > 2)
			throw new IOException("Usage: Translate_YOUR_NAME infile [outFile]");


		// todo: read in FastA pairs
		var list = assignment01.FastA_YOUR_NAME.read(args[0]);

		String sequence = list.get(0).sequence();
		sequence = sequence.replaceAll("\\s+","");
		String[] peptides = new String[sequence.length()];
		for (int i =0; i < sequence.length()/3; i++){
			String codon = new String(sequence.substring(i*3, (i+1)*3));
			System.out.println(codon);
			peptides[i] = codon;
			if (codon.equals("CCG") || codon.equals("CCA") || codon.equals("CCT") || codon.equals("CCC")){
				peptides[i] = "G";
				System.out.println("G!");
			}

			else if (codon.equals("AAA") || codon.equals("AAG")){
				peptides[i] = "F";
				System.out.println("F!");
			}
			else if (codon.equals("AAT") || codon.equals("AAC")|| codon.equals("GAA") || codon.equals("GAG")|| codon.equals("GAT") || codon.equals("GAC")){
				peptides[i] = "L";
				System.out.println("L!");
			}
			else if (codon.equals("TAA") || codon.equals("TAG")|| codon.equals("TAT")){
				peptides[i] = "I";
				System.out.println("I!");
			}
		}
		// todo: compute translated sequences using translate(sequence) method defined below
		var translated=new ArrayList<assignment01.FastA_YOUR_NAME.Pair>();

		// todo: write translated sequences
	}

	public static String translate(String sequence) {
		var buf=new StringBuilder();

		// todo: implement translation of sequence

		return buf.toString();
	}
}
