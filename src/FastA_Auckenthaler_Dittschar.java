package assignment01;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * FastA_Auckenthaler_Dittschar
 * Author(s): YOUR_NAME
 * Sequence Bioinformatics, WS 22/23
 */


public class FastA_Auckenthaler_Dittschar {

	//Collection
	public static void write(ArrayList<Pair> list, String fileName) throws IOException {
		try(var w=(fileName!=null?new FileWriter(fileName):new OutputStreamWriter(System.out))) {
			// todo: write out pairs in FastA format here
			//while ((line = r.readLine()) != null) {
			for (int i = 0; i < list.size(); i++){
				w.write(">");
				w.write(list.get(i).header);
				w.write(String.format("%n"));
				w.write(list.get(i).sequence);
				w.write(String.format("%n"));
			}
		}
		catch(IOException ex) {
			for (int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).header);
				System.out.println(list.get(i).sequence);
			}
		}


	}

	public static ArrayList<Pair> read(String fileName) throws IOException {
		var list=new ArrayList<Pair>();
		try(var r=new BufferedReader(new FileReader(fileName))) {
			// todo: read in pairs in FastA format here
			String line;
			while ((line = r.readLine()) != null) {
				String header = "";
				String sequence = "";
				if (line.startsWith(">")){
					header = line.substring(1);
					sequence = r.readLine();
				}
				Pair pair = new Pair(header, sequence);
				list.add(pair);

			}
		}

		return list;
	}

	/**
	 * a FastA record consisting of a pair of header and sequence
	 */
	public static record Pair(String header, String sequence) {
	}


}


