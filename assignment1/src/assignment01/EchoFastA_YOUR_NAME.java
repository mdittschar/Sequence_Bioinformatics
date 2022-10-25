package assignment01;

import java.io.IOException;

import static assignment01.FastA_YOUR_NAME.read;

/**
 * EchoFastA_YOUR_NAME
 * Author(s): YOUR_NAME
 * Sequence Bioinformatics, WS 22/23
 */
public class EchoFastA_YOUR_NAME {
	public static void main(String[] args) throws IOException {
		if(args.length<1 || args.length>2)
			throw new IOException("Usage: EchoFastA_YOUR_NAME infile [outFile]");
		else{

		read("dna.fasta");}
		// todo: read FastA records from infile and echo to outfile or stdout (console)

	}
}
