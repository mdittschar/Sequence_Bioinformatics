package assignment06;

import assignment01.FastA_Auckenthaler_Dittschar;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.ArrayList;

/**
 * find occurrences of queries in a text
 * Sequence Bioinformatics, WS 22/23
 * FindQueries_Auckenthaler_Dittschar 11.22
 */
public class FindQueries_Auckenthaler_Dittschar {
	public static void main (String[] args) throws IOException {
		System.out.println(FindQueries_Auckenthaler_Dittschar.class.getSimpleName());

		if(args.length!=2)
			throw new IOException("Usage: FindQueries_Auckenthaler_Dittschar text queries");

		var textItems= FastA_Auckenthaler_Dittschar.read(args[0]);
		if(textItems.size()!=1)
			throw new IOException("text must contain 1 sequence, found: "+textItems.size());

		System.out.println("Text: "+textItems.get(0).sequence());

		var suffixTree=new NaiveSuffixTree(textItems.get(0).sequence());

		var queryItems=FastA_Auckenthaler_Dittschar.read(args[1]);
		suffixTree.printTree();
		for(var item:queryItems) {
			System.out.println("Query "+item.sequence());
			System.out.println("Contained: "+contains(suffixTree,item.sequence()));
			System.out.print("Occurrences:");
			for(var pos:find(suffixTree,item.sequence())) {
				System.out.print(" "+pos);
			}
			System.out.println();
		}
	}

	/**
	 * determines whether text contains query
	 * @param suffixTree the suffix tree representing the text
	 * @param query the query
	 * @return true, if query in text
	 */
	public static boolean contains(NaiveSuffixTree suffixTree, String query) {
		// todo: please implement this
		boolean isQueryInTree = getChildLetters(suffixTree.getRoot(), query);
		return isQueryInTree;
	}
	public static boolean getChildLetters(NaiveSuffixTree.Node node, String query) {
		boolean result = false;
		//System.out.println("Current query: "+ query);
		for (var child : node.getChildren()) {
			if (query.length() >= child.getLetters().length()){
				if (child.getLetters().equals(query.substring(0, child.getLetters().length()))){
					result = getChildLetters(child, query.substring(child.getLetters().length(), query.length()));
				}
			}
			if (child.getLetters().length() >= query.length()){
				if (query.equals(child.getLetters().substring(0, query.length()))) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * find and return all occurrences of query in text
	 * @param suffixTree the suffix tree representing the text
	 * @param query the query
	 * @return all positions in text at which query occurs
	 */
	public static Collection<Integer> find(NaiveSuffixTree suffixTree, String query) {
		// todo: please implement this
		return Collections.emptyList();
	}
}
