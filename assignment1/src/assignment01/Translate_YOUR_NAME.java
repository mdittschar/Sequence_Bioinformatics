package assignment01;

import java.io.IOException;
import java.util.ArrayList;

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
