package assignment01;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;

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
        var list = assignment01.FastA_YOUR_NAME.read(args[0]);
        // todo: check that all input sequences have the same length, otherwise throw a new IOException("Different lengths");
        int check_size = list.get(0).sequence().length();
        for (int i=1; i< list.size();i++){
            if (list.get(i).sequence().length() != check_size)
                throw new IOException("Different lengths");
        }
        Integer[][] edit_matrix = new Integer[list.size()][list.size()];
        for (int i=0; i<list.size();i++){
            for (int j=0; j<list.size();j++) {
                edit_matrix[i][j] = computeEditDistance(list.get(i).sequence(), list.get(j).sequence());
            }
        }
        System.out.println("Edit Distance Matrix: ");
        for(Integer[] i : edit_matrix) {
           for(int j : i) {
               //print row
               System.out.print(j + "\t");
           }
           // new row
           System.out.println();
        }
        //System.out.println(Arrays.deepToString(edit_matrix));
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
        int dist = 0;
        char[] xchar = x.toCharArray();
        char[] ychar = y.toCharArray();
        for (int i=0; i<xchar.length; i++){
            if (xchar[i] != ychar[i]){
                dist = dist + 1;
            }
        }
       // System.out.print(dist + " ");
        return dist;
    }
}
