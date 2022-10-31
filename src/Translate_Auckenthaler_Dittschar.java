package assignment01;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Translate_Auckenthaler_Dittschar
 * Author(s): Clarissa Auckenthaler & Marina Dittschar
 * Sequence Bioinformatics, WS 22/23
 */
public class Translate_Auckenthaler_Dittschar {
	public static void main(String[] args) throws IOException {
		if (args.length < 1 || args.length > 2)
			throw new IOException("Usage: Translate_Auckenthaler_Dittschar infile [outFile]");

		var translated=new ArrayList<assignment01.FastA_Auckenthaler_Dittschar.Pair>();
		// todo: read in FastA pairs
		var list = assignment01.FastA_Auckenthaler_Dittschar.read(args[0]);
		for (int i =0; i < list.size(); i++){
			String sequence = list.get(i).sequence();
			sequence = sequence.replaceAll("\\s+","");
			translated.add(new FastA_Auckenthaler_Dittschar.Pair(list.get(i).header(), translate(sequence)));
		}

		// todo: compute translated sequences using translate(sequence) method defined below


		// todo: write translated sequences
	}

	public static String translate(String sequence) {
		//Source for bacterial genetic code:
		// https://en.wikipedia.org/wiki/DNA_and_RNA_codon_tables#Standard_DNA_codon_table
		var buf=new StringBuilder();
		for (int i =0; i < sequence.length()/3; i++){
			String codon = new String(sequence.substring(i*3, (i+1)*3));
			if (codon.equals("GGT") || codon.equals("GGC") || codon.equals("GGA") || codon.equals("GGG")){
				buf.append("G");
			}

			else if (codon.equals("TTT") || codon.equals("TTC")){
				buf.append("F");
			}
			else if (codon.equals("TTA") || codon.equals("TTG")|| codon.equals("CTT") || codon.equals("CTC")|| codon.equals("CTA") || codon.equals("CTG")){
				buf.append("L");
			}
			else if (codon.equals("ATT") || codon.equals("ATC")|| codon.equals("ATA")){
				buf.append("I");
			}
			else if (codon.equals("ATG")){
				buf.append("M");
			}
			else if (codon.equals("GTT") || codon.equals("GTC")|| codon.equals("GTA")|| codon.equals("GTG")){
				buf.append("V");
			}
			else if (codon.equals("TCT") || codon.equals("TCC")|| codon.equals("TCA")|| codon.equals("TCG")){
				buf.append("S");
			}
			else if (codon.equals("CCT") || codon.equals("CCC")|| codon.equals("CCA")|| codon.equals("CCG")){
				buf.append("P");
			}
			else if (codon.equals("ACT") || codon.equals("ACC")|| codon.equals("ACA")|| codon.equals("ACG")){
				buf.append("T");
			}
			else if (codon.equals("GCT") || codon.equals("GCC")|| codon.equals("GCA")|| codon.equals("GCG")){
				buf.append("A");
			}
			else if (codon.equals("TAT") || codon.equals("TAC")){
				buf.append("Y");
			}
			else if (codon.equals("TAA") || codon.equals("TAG")|| codon.equals("TGA")){
				buf.append("Stop");
			}
			else if (codon.equals("CAT") || codon.equals("CAC")){
				buf.append("H");
			}
			else if (codon.equals("CAA") || codon.equals("CAG")){
				buf.append("Q");
			}
			else if (codon.equals("AAT") || codon.equals("AAC")){
				buf.append("N");
			}
			else if (codon.equals("AAA") || codon.equals("AAG")){
				buf.append("K");
			}
			else if (codon.equals("GAT") || codon.equals("GAC")){
				buf.append("D");
			}
			else if (codon.equals("GAA") || codon.equals("GAG")){
				buf.append("E");
			}
			else if (codon.equals("TGT") || codon.equals("TGC")){
				buf.append("C");
			}
			else if (codon.equals("TGG")){
				buf.append("W");
			}
			else if (codon.equals("AGA") || codon.equals("AGC"){
				buf.append("R");
			}
			else if (codon.equals("AGT") || codon.equals("AGC")){
				buf.append("S");
			}



		}
		// todo: implement translation of sequence
		System.out.println(buf.toString());
		return buf.toString();
	}
}
