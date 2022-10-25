package assignment01;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * EditDistance_YOUR_NAME
 * Author(s): YOUR_NAME
 * Sequence Bioinformatics, WS 22/23
 */
public class EditDistance_YOUR_NAME {

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args.length > 2)
            throw new IOException("Usage: EditDistance_YOUR_NAME infile [outFile]");

        // todo: implement input of FastA records

        // todo: check that all input sequences have the same length, otherwise throw a new IOException("Different lengths");

         try (Writer w = (args.length == 2 ? new FileWriter(args[1]) : new OutputStreamWriter(System.out))) {
             // todo: compute distance between any two sequences, using method computeEditDistance(x,y) defined below
             // todo: write distance matrix

         }
        // example of format:
        // a 0 1 2 3
        // b 1 0 4 5
        // c 2 4 0 6
        // d 3 5 6 0
    }

    private static int computeEditDistance(String x, String y) {
        // todo: implement computation of edit distance
        return 0;
    }
}
