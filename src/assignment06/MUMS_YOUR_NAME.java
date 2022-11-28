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
	public static void getMums(NaiveSuffixTree suffixTree, String string1){
		NaiveSuffixTree.Node node = suffixTree.getRoot();
		System.out.println(suffixTree.getRoot().getChildren().size());
		getMumsPastRoot(node, string1);

	}
	public static void getMumsPastRoot(NaiveSuffixTree.Node node, String string1){
		int watershed = string1.length();

		for (var child : node.getChildren()) {
			if (child.getChildren().size() == 2) {
				System.out.println(child.getLetters()+": This node has two children.");

			}


		}
	}
	/*public static void checkForTwoAndLeaves(NaiveSuffixTree suffixTree, NaiveSuffixTree.Node child, int watershed, int pos1, int pos2){

			if (suffixTree.getChildren(child).get(pos1).getSuffixPos() > watershed){
				if (getChildren(child).get(pos2).getSuffixPos() < watershed){
					System.out.println("MUM found.");
				}
			}


	}

	 */
}
