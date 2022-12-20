package assignment08;

import assignment01.FastA_Auckenthaler_Dittschar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

/**
 * proof-of-concept implementation for the basic minimap algorithm
 * Sequence Bioinformatics, WS 22/23
 * Minimap_YOUR_NAME, 12.22
 */
public class Minimap_Auckenthaler_Dittschar{
	/**
	 * run the basic minimap algorithm
	 * @param args commandline arguments
	 * @throws IOException if arguments are incorrect or sequences not found
	 */
	public static void main(String[] args) throws IOException {
		int w;
		int k;
		ArrayList<FastA_Auckenthaler_Dittschar.Pair> targets;
		ArrayList<FastA_Auckenthaler_Dittschar.Pair> queries;
		if(args.length==2) {
			w=10;
			k=15;
			targets=FastA_Auckenthaler_Dittschar.read(args[0]);
			queries=FastA_Auckenthaler_Dittschar.read(args[1]);
		}
		else if(args.length==4) {
			 w=Integer.parseInt(args[0]);
			 k=Integer.parseInt(args[1]);
			 targets= FastA_Auckenthaler_Dittschar.read(args[2]);
			 queries=FastA_Auckenthaler_Dittschar.read(args[3]);
		}
		else
			throw new IOException("Usage: Minimap_Auckenthaler_Dittschar [w k] targets-file queries-file");


		if(true) { // this tests whether hashing and reverse-complement methods work as expected
			var testSequence= "CACGGTAGA";
			var hash=h(sk(testSequence, 1, 5, 0));
			if (hash != 107)
				throw new RuntimeException("Hashing broken, expected 107, got: "+hash);
			var hashReverseComplement=h(sk(testSequence, 1, 5, 1));
			if (hashReverseComplement != 91)
				throw new RuntimeException("Hashing reverse-complement broken, expected 91, got: "+hashReverseComplement);
		}

		System.err.printf(Minimap_Auckenthaler_Dittschar.class.getSimpleName() + " w=%d k=%d targets=%d queries=%d%n", w,k,targets.size(),queries.size());

		var targetIndex=computeTargetIndex(targets,w,k);

		for(var record:queries) {
			var query= record.sequence();
			System.err.println("\nQuery: "+record.header());
			var matches=mapQuerySequence(targetIndex,query,w,k,500);
			System.out.println("Matches: "+matches.size());
			for(var match:matches) {
				System.err.println(("Target: %d, query: %d - %d, target: %d - %d, reverse: %d"
						.formatted(match.t()+1,match.qMin()+ 1, match.qMax()+ 1,match.tMin()+ 1, match.tMax()+ 1,match.r())));
				System.err.println(query.substring(match.qMin(), match.qMax()));
				var target=targets.get(match.t()).sequence();
				System.err.println(sk(target,match.tMin(),match.tMax()-match.tMin(), match.r()));
			}
		}
	}

	/**
	 * extracts the k-mer at given position  (as described in script)
	 * @param sequence the DNA sequence
	 * @param pos the position
	 * @param k the k-mer size
	 * @param r 0 for forward and 1 for reverse complement
	 * @return k-mer at given position or its reverse complement
	 */
	public static String sk(String sequence,int pos,int k,int r) {
		if(r==0)
			return sequence.substring(pos,pos+k);
		else {
			var buf=new StringBuilder();
			// todo: implement reverse-complement of k-mer here
			for (int i=pos-1; i<pos+k-1; i++) {
				if (sequence.charAt(pos+k-1-i) == 'A') {
					buf.append("T");
				} else if (sequence.charAt(pos+k-1-i) == 'T') {
					buf.append("A");
				} else if (sequence.charAt(pos+k-1-i) == 'G') {
					buf.append("C");
				} else if (sequence.charAt(pos+k-1-i) == 'C') {
					buf.append("G");
				}
				else if (sequence.charAt(pos+k-1-i) == 'N') {
				buf.append("N");
			}
			}
			return buf.toString();
		}
	}

	/**
	 * computes the h value for a k-mer (as described in script)
	 * @param s DNA string of length k
	 * @return h value
	 */
	public static int h(String s) {
		var value = 0;
		// todo: implement hashing as described in script
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("A", 0);
		map.put("C", 1);
		map.put("G", 2);
		map.put("T", 3);
		map.put("N", 0); // no indication given on how to deal with N
		for (int i=0; i<s.length(); i++){
			int c = map.get(s.substring(i,i+1));
			int add = (int) Math.pow(4, s.length()-1-i);
			value = value + c*add;
		}
		return value;
	}

	/**
	 * computes a minimizer sketch for a given sequence and parameter k (algorithm 1 in the script)
	 * @param s the DNA sequence
	 * @param w the word size
	 * @param k the k-mer size
	 * @return sorted set of all minimizers
	 */
	public static Set<Minimizer> minimizerSketch(String s, int w, int k) {
		var sketch=new HashSet<Minimizer>();

		// todo: implement computation of minimizer sketch as described in script (algorithm 1)
		for (int i=0; i<s.length()-w-k+1;i++){
			//System.out.println("The length we consider is: "+len);
			int m = Integer.MAX_VALUE;
			for(int j=0; j<=w-1;j++){
				ArrayList<Integer> uv = new ArrayList<Integer>();
				int h1 = h(sk(s, i+j, k,0));
				int h2 = h(sk(s, i+j, k,1));
				uv.add(h1);
				uv.add(h2);
				//System.out.println("List 0: "+uv.get(0)+" List 1: "+uv.get(1));
				if (uv.get(0) != uv.get(1)){
					m = Math.min(m, Math.min(uv.get(0), uv.get(1)));
				}
			}
			for (int j=0; j <= w-1; j++){
				ArrayList<Integer> uv = new ArrayList<Integer>();
				int h1 = h(sk(s, i+j, k,0));
				int h2 = h(sk(s, i+j, k,1));
				if (h1 < h2 && h1 == m){
					sketch.add(new Minimizer(m, i+j, 0));
				}
				else if ( h1 > h2 && h2 == m){
					sketch.add(new Minimizer(m, i+j, 1));
				}
			}

		}

		return sketch;
	}

	/**
	 * Compute a hash map of h-values to minimizer locations in the target sequences (algorithm 3 in the script)
	 * @param targets the target sequences
	 * @param w the word size
	 * @param k the k-mer size
	 * @return the
	 */
	public static HashMap<Integer,Set<Location>> computeTargetIndex(ArrayList<FastA_Auckenthaler_Dittschar.Pair> targets, int w, int k) {
		var targetIndex= new HashMap<Integer,Set<Location>>();

		// todo: implement computation of target index as described in script (algorithm 1)
		for (int t=0; t< targets.size(); t++){
			Set<Minimizer> minimizerSet = minimizerSketch(targets.get(t).sequence(), w, k);
			Iterator<Minimizer> itr = minimizerSet.iterator();

			while(itr.hasNext()){
				Minimizer curmin = itr.next();

				Location loc = new Location(t, curmin.pos, curmin.r);

				if (targetIndex.containsKey(curmin.h)){
					Set<Location> set = targetIndex.get(curmin.h);
					set.add(loc);
					targetIndex.put(curmin.h, set);
				}
				else{

					Set<Location> set = new HashSet<Location>();
					set.add(loc);
					targetIndex.put(curmin.h,set);
				}

			}

		}

		return targetIndex;
	}

	/**
	 * compute all matches of query to any of the target sequences (algorithm 4 in script)
	 * @param targetIndex the target index computed using #computeTargetIndex()
	 * @param query the query DNA sequences
	 * @param w the word size
	 * @param k the k-mer size
	 * @param epsilon the dialog shift allowed between two chained dialogs
	 */
	public static ArrayList<Match> mapQuerySequence(HashMap<Integer,Set<Location>> targetIndex, String query, int w, int k, int epsilon) {

		// compute array of k-mer hits:
		var A=new ArrayList<KMerHit>();
		var M = minimizerSketch(query, w, k);
		Iterator<Minimizer> itr = M.iterator();
		//System.out.println("Minimizer set size: "+M);
		//System.out.println("Targetindex set size: "+targetIndex);

		while(itr.hasNext()){
			Minimizer curmin = itr.next();
			for (HashMap.Entry<Integer, Set<Location>> entry : targetIndex.entrySet()){
				if (entry.getKey() == curmin.h){
					Iterator<Location> locationIterator = entry.getValue().iterator();
					while (locationIterator.hasNext()){
						Location curloc = locationIterator.next();
						if (curloc.r == curmin.r){
							KMerHit kmerHit = new KMerHit(curloc.t, 0, curmin.pos - curloc.pos, curloc.pos);
							A.add(kmerHit);

						}
						else {
							KMerHit kmerHit = new KMerHit(curloc.t, 1, curmin.pos + curloc.pos, curloc.pos);
							A.add(kmerHit);

						}

					}

				}
				//System.out.println(entry.getKey() + "/" + entry.getValue());


			}
		}

		A.sort(KMerHit::compareTo);

		// chain k-mer hits into matches and return the matches
		var result=new ArrayList<Match>();
		//???
		//System.out.println(A);
		var b=0;
		for(var e=0;e<A.size();e++) {
			//Match match = new Match(A.get(e).t, A.get(e).r, A.get(e).pos  + A.get(e).c, A.get(e).pos + k + A.get(e).c, A.get(e).pos, A.get(e).pos + k);
			// todo: compute matches or ``clusters'' (as described in script, algorithm 4, part;s 2 and 3
			if (e== A.size()-1 || A.get(e+1).t !=  A.get(e).t || A.get(e+1).r != A.get(e).r || A.get(e+1).c - A.get(e).c >= epsilon){

				Match match = new Match(A.get(e).t, A.get(e).r, A.get(b).pos + A.get(b).c, A.get(e).pos + k + A.get(e).c, A.get(b).pos, A.get(e).pos + k);
				Match match2 = new Match(A.get(e).t, A.get(e).r, A.get(b).c - A.get(b).pos, A.get(e).c - A.get(e).pos + k, A.get(b).pos, A.get(e).pos + k);

				if (A.get(e).r == 1){
					result.add(match2);
				}
				else{
					result.add(match);
				}
				b = e + 1;

			}



		}
		return result;
	}


	// PLEASE DO NOT CHANGE ANYTHING BELOW HERE:

	/**
	 * a minimizer
	 * @param h hash value
	 * @param pos position in sequence
	 * @param r 0 for forward and 1 for reverse strand
	 */
	public static record Minimizer(int h,int pos, int r) {
	}

	/**
	 * a minimizer location
	 * @param t index of target sequence 0...T-1, with T the number of target sequences
	 * @param pos position in sequence t
	 * @param r 0 for forward and 1 for reverse strand
	 */
	public static record Location(int t,int pos,int r) {}

	/**
	 * a k-mer hit
	 * @param t is the number of the target sequence
	 * @param r is the *relative* strand (0 if both query and target minimizers on same strand, otherwise 1)
	 * @param c the dialog number
	 * @param pos the position in the target sequence
	 */
	public static record KMerHit(int t, int r, int c, int pos) implements Comparable<KMerHit>{
		@Override
		public int compareTo(KMerHit other) {
			if(t<other.t)
				return -1;
			else if(t>other.t)
				return 1;
			else if(r<other.r)
				return -1;
			else if(r>other.r)
				return 1;
			else if(c<other.c)
				return -1;
			else if(c>other.c)
				return 1;
			else return Integer.compare(pos, other.pos);
		}
	}

	/**
	 * a query-target sequence match
	 * @param t the target index
	 * @param r is the *relative* strand (0 for matches on same strand, 1 for on different strands)
	 * @param qMin minimum position of match in query
	 * @param qMax maximum position of match in query
	 * @param tMin minimum position of match in target
	 * @param tMax maximum position of match in target

	 */
	public static record Match(int t, int r, int qMin, int qMax, int tMin, int tMax) {
	}
}

