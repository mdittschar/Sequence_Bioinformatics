package assignment06;

import assignment01.FastA_Auckenthaler_Dittschar;

import java.io.IOException;
import java.util.Stack;

/**
 * computes all MUMs in a text
 * Sequence Bioinformatics, WS 22/23
 * MUMS_YOUR_NAME, 11.22
 */
public class MUMS_YOUR_NAME {

	public static void main (String[] args) throws IOException {
		System.out.println(MUMS_YOUR_NAME.class.getSimpleName());

		if (args.length != 1)
			throw new IOException("Usage: MUMS_YOUR_NAME fasta-file");

		var textItems = FastA_Auckenthaler_Dittschar.read(args[0]);
		if (textItems.size() != 2)
			throw new IOException("fasta-file must contain 2 sequence, found: " + textItems.size());

		// todo: please implement

		// 1. setup suffix tree for appropriately concatenated sequences
		String string1 = textItems.get(0).sequence();
		String string2 = textItems.get(1).sequence();
		NaiveSuffixTree suffixTree = buildMUMtree(string1, string2);
		suffixTree.printTree();
		// 2. implement algorithm to report all MUMs (of any size)
		getMums(suffixTree, string1);

		// output should be:
		// MUM "GC" at 2 and 2 (1-based)
		// for input AGCT and GGCC
	}
	public static NaiveSuffixTree buildMUMtree(String string1, String string2){
		String concatstring = string1+"%"+string2;
		var suffixTree=new NaiveSuffixTree(concatstring);
		return suffixTree;
	}
	public static void getMums(NaiveSuffixTree suffixTree, String firstEntry){
		NaiveSuffixTree.Node node = suffixTree.getRoot();
		System.out.println(suffixTree.getRoot().getChildren().size());
		getMumsPastRoot(node, "", firstEntry);

	}
	public static void getMumsPastRoot(NaiveSuffixTree.Node node, String string, String firstEntry){

		for (var child : node.getChildren()) {
			int percent = 0;

			string = string + child.getLetters();

			if (child.getChildren().size() == 2) {
				//string = child.getLetters();
				System.out.println(child.getLetters()+": This node has two children.");
				System.out.println(child.getChildren());

				int[] pos = new int[2];
				for (var grandchild : child.getChildren()){
					if (grandchild.getChildren().size() == 0){
						if (grandchild.getLetters().contains("%")){
							percent = percent + 1;
							pos[0] = grandchild.getSuffixPos()+1;
						}
						else{
							pos[1] = grandchild.getSuffixPos()-firstEntry.length();
						}
					}
				}
				if (percent == 1){
					System.out.println("MUM "+string+" at "+pos[0]+" and "+ pos[1]);
				}

			}
			if (child.getChildren().size() == 0 && percent != 1){
				string = "";
			}

		getMumsPastRoot(child, string, firstEntry);
		}

	}
}
