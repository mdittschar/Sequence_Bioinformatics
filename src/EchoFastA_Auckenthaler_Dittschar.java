package assignment01;

import java.io.IOException;
import java.util.ArrayList;

import static assignment01.FastA_Auckenthaler_Dittschar.read;

/**
 * EchoFastA_Auckenthaler_Dittschar
 * Author(s): YOUR_NAME
 * Sequence Bioinformatics, WS 22/23
 */
public class EchoFastA_Auckenthaler_Dittschar {
	public static void main(String[] args) throws IOException {
		if(args.length<1 || args.length>2)
			throw new IOException("Usage: EchoFastA_Auckenthaler_Dittschar infile [outFile]");
		else{
			var list =  assignment01.FastA_Auckenthaler_Dittschar.read(args[0]);
			ArrayList<FastA_Auckenthaler_Dittschar.Pair> example_list = new ArrayList<FastA_Auckenthaler_Dittschar.Pair>();
			example_list = read("dna.fasta");
			assignment01.FastA_Auckenthaler_Dittschar.write(example_list, "");
		}
		// todo: read FastA records from infile and echo to outfile or stdout (console)

	}
}
