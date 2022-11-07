package assignment03;

import assignment01.FastA_Auckenthaler_Dittschar;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedHashSet;
import java.util.Arrays;
import java.util.ArrayList;



/**
 * setup ILP to solve multiple sequence alignment for three sequences
 * Autors:Clarissa Auckenthaler, Marina Dittschar
 * Sequence Bioinformatics, WS22/23
 */
public class AlignmentILP_Auckenthaler_Dittschar {
	public static void main(String[] args) throws IOException {
		if (args.length != 1 && args.length!=2) {
			throw new IOException("Usage: AlignmentILP_Auckenthaler_Dittschar input [output]");
		}

		var list = assignment01.FastA_Auckenthaler_Dittschar.read(args[0]);

		if (list.size() != 3) {
			throw new IOException("Input file must contain 3 sequences, found: " + list.size());
		}

		char[] sequence1 = list.get(0).sequence().toCharArray();
		char[] sequence2 = list.get(1).sequence().toCharArray();
		char[] sequence3 = list.get(2).sequence().toCharArray();
		String plus = "";

		// todo: setup and write out ILP based on extended alignment graph, with match score =  4 and mismatch score = 1
		int match = 4;
		int mismatch = 1;

		ArrayList<String> objectiveFunction = new ArrayList<String>();
		ArrayList<String> simpleMixedCyles2 = new ArrayList<String>();
		ArrayList<String> simpleMixedCyles3 = new ArrayList<String>();


		try(var w=(args.length ==2?new FileWriter(args[1]):new OutputStreamWriter(System.out))) {
			w.write("max: ");
			// 1. write the objective function: loop over all pairs of sequences and all pairs of letters
			for (int no_seq = 0; no_seq < list.size(); no_seq++) {
				char[] si = list.get(0).sequence().toCharArray();
				int i= 0;
				char[] sp = list.get(2).sequence().toCharArray();
				int p= 2;
				if (no_seq!= list.size() - 1){
					si = list.get(no_seq).sequence().toCharArray();
					i= no_seq;
					sp = list.get(no_seq+1).sequence().toCharArray();
					p= (no_seq+1);
				}
				for (int j= 0; j < si.length; j++) {
					for (int q=0; q<sp.length;q++){

						if (si[j]== sp[q]){
							w.write(plus+String.valueOf(match)+"*X"+String.valueOf(i)+String.valueOf(j)+"_"+String.valueOf(p)+String.valueOf(q));
						}
						else{
							w.write(plus+String.valueOf(mismatch)+"*X"+String.valueOf(i)+String.valueOf(j)+"_"+String.valueOf(p)+String.valueOf(q));
						}
						plus = "+";
					}
				}
			}
			w.write(";");
			w.write('\n');

			//2. write out all the simple mixed cycle constraints between any two sequences
			for (int no_seq = 0; no_seq < list.size(); no_seq++) {
				char[] si = list.get(0).sequence().toCharArray();
				int i = 0;
				char[] sp = list.get(2).sequence().toCharArray();
				int p = 2;
				if (no_seq != list.size() - 1) {
					si = list.get(no_seq).sequence().toCharArray();
					i = no_seq;
					sp = list.get(no_seq + 1).sequence().toCharArray();
					p = (no_seq + 1);
				}
				for (int ii = 0; ii < si.length; ii++) {
					for (int j = ii; j < si.length; j++) {
						for (int k = 0; k < sp.length; k++) {
							for (int l = k; l < sp.length; l++) {
								if (k !=l || ii != j) {
									w.write("X"+String.valueOf(i)+String.valueOf(ii)+"_"+String.valueOf(p)+String.valueOf(k)+" + "+"X"+String.valueOf(i)+String.valueOf(j)+"_"+String.valueOf(p)+String.valueOf(l)+"<1;\n");
									//simpleMixedCyles2.add("X"+String.valueOf(i)+String.valueOf(ii)+"_"+String.valueOf(p)+String.valueOf(k)+"+ "+"X"+String.valueOf(i)+String.valueOf(j)+"_"+String.valueOf(p)+String.valueOf(l)+"<1;");

								}
							}
						}
					}
				}
			}
			// 3. write out all the simple mixed cycle constraints between any three sequences
			for (int ii = 0; ii < sequence1.length; ii++) {
				for (int j  = ii ; j < sequence1.length; j++) {
					for (int k = 0; k < sequence2.length; k++) {
						for (int l = k; l < sequence2.length; l++) {
							for (int m= 0; m<sequence3.length;m++){
								for (int n= m; n<sequence3.length;n++){
									if ((k !=l) || (ii != j)||(m!=n)) {
										w.write("X"+"0"+ii+"_"+"1"+k+"_"+"2"+m+" + "+"X"+"0"+j+"_"+"1"+l+"_"+"2"+n+ "<2;\n");
										//simpleMixedCyles3.add("X"+"0"+String.valueOf(ii)+"_"+"1"+String.valueOf(k)+"_"+"2"+String.valueOf(m)+"+"+"X"+"0"+String.valueOf(j)+"_"+"1"+String.valueOf(l)+"_"+"2"+String.valueOf(n)+ "<2;");
										w.write("X"+"0"+ii+"_"+"2"+k+"_"+"1"+m+" + "+"X"+"0"+j+"_"+"2"+l+"_"+"1"+n+ "<2;\n");
										//simpleMixedCyles3.add("X"+"0"+String.valueOf(ii)+"_"+"2"+String.valueOf(k)+"_"+"1"+String.valueOf(m)+"+"+"X"+"0"+String.valueOf(j)+"_"+"2"+String.valueOf(l)+"_"+"1"+String.valueOf(n)+ "<2;");

									}
								}
							}
						}
					}
				}
			}

			// 4. write out the binary variable constraints
			for (int no_seq = 0; no_seq < list.size(); no_seq++) {
				char[] si = list.get(0).sequence().toCharArray();
				int i= 0;
				char[] sp = list.get(2).sequence().toCharArray();
				int p= 2;
				if (no_seq!= list.size() - 1){
					si = list.get(no_seq).sequence().toCharArray();
					i= no_seq;
					sp = list.get(no_seq+1).sequence().toCharArray();
					p= (no_seq+1);
				}
				for (int j= 0; j < si.length; j++) {
					for (int q=0; q<sp.length;q++){
						w.write("X"+(i)+(j)+"_"+(p)+(q)+"<1;");
						w.write('\n');
					}
				}
			}

			// 5. specify all variables as integers
			w.write("int ");
			for (int no_seq = 0; no_seq < list.size(); no_seq++) {
				char[] si = list.get(0).sequence().toCharArray();
				int i= 0;
				char[] sp = list.get(2).sequence().toCharArray();
				int p= 2;
				if (no_seq!= list.size() - 1){
					si = list.get(no_seq).sequence().toCharArray();
					i= no_seq;
					sp = list.get(no_seq+1).sequence().toCharArray();
					p= (no_seq+1);
				}

				for (int j= 0; j < si.length; j++) {
					for (int q=0; q<sp.length;q++){
							if ((j == si.length-1) && (q==sp.length-1)&& (p==2)&&(i==0)) {
								w.write("X"+(i)+(j)+"_"+(p)+(q));
								w.write(";");
							}
							else{
								w.write("X"+(i)+(j)+"_"+(p)+(q)+", ");
							}
					}
				}
			}
			//System.out.println(simpleMixedCyles3.size());
			//System.out.println(simpleMixedCyles2.size());

		}
	}

}
