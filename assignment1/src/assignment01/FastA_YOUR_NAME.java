package assignment01;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * FastA_YOUR_NAME
 * Author(s): YOUR_NAME
 * Sequence Bioinformatics, WS 22/23
 */
public class FastA_YOUR_NAME {

	public static void write(Collection<Pair> list, String fileName) throws IOException {
		try(var w=(fileName!=null?new FileWriter(fileName):new OutputStreamWriter(System.out))) {
			// todo: write out pairs in FastA format here
		}
	}

	public static ArrayList<Pair> read(String fileName) throws IOException {
		var list=new ArrayList<Pair>();
		try(var r=new BufferedReader(new FileReader(fileName))) {
			// todo: read in pairs in FastA format here
			StringBuilder content = new StringBuilder();
			String line;
			while ((line = r.readLine()) != null) {
				content.append(line);
				content.append(System.lineSeparator());
			}
			System.out.println(content);
		}

		return list;
	}

	/**
	 * a FastA record consisting of a pair of header and sequence
	 */
	public static record Pair(String header, String sequence) {
	}
}
