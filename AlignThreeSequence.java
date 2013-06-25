import java.text.DecimalFormat;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

public class AlignThreeSequence {
	public static int lineWidth = 80;
    // tranpose a 2D matrix
	private static int[][] tranpose(int[][] M) {
		int nrows = M.length;
		int ncols = M[0].length;
		int[][] N = new int[ncols][nrows];
		for (int i = 0; i < nrows; ++i) {
			for (int j = 0; j < ncols; ++j) {
				N[j][i] = M[i][j];
			}
		}
		return N;
	}
    
    //rotate a 2D matrix clockwise 90 degree
	private static int[][] ClockWiseRotate90Degree(int[][] M) {
		int nrows = M.length;
		int ncols = M[0].length;
		int[][] N = new int[ncols][nrows];
		for (int i = 0; i < nrows; ++i) {
			for (int j = 0; j < ncols; ++j) {
				N[j][N[0].length - 1 - i] = M[i][j];
			}
		}
		return N;
	}
  
    // join an integer array with specified delimiter
	private static String join(int[] L, String delimiter) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < L.length - 1; ++i) {
			result.append(L[i]);
			result.append(delimiter);
		}
		result.append(L[L.length - 1]);
		return result.toString();
	}

    // wrapper function to print a 2D matrix
	private static String print2DMatrix(int[][] M) {
		int nrows = M.length;
		int ncols = M[0].length;
		String result = "";
		for (int i = 0; i < M.length; ++i) {
			result += join(M[i], "\t") + "\n";
		}
		return result;
	}

    // wrapper function to print a 3D matrix
	private static String print3DMatrix(int[][][] M) {
		int nrows = M.length;
		int ncols = M[0].length;
		String result = "";
		for (int i = 0; i < M.length; ++i) {
			result += "#" + i + ":\n";
			for (int j = 0; j < M[0].length; ++j) {
				result += join(M[i][j], "\t") + "\n";
			}
			result += "\n";
		}
		return result;
	}
    
	private static void PrintHash(HashMap m) {
		Set set = m.entrySet();
		Iterator i = set.iterator();
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.println(me.getKey() + " => " + me.getValue());
		}
	}

    // generated the ScoreMatrix Hash Table
	public static HashMap ScoreMatrixTable() {
		int mismatch = -4;
		int match = 5;
		int indel = -8;
		HashMap ScoreTwoChar = new HashMap();
		HashMap ScoreThreeChar = new HashMap();

		String[] alphabet = { "A", "C", "G", "T", "-" };
		for (String a : alphabet) {
			for (String b : alphabet) {
				if (a == b)
					if (a == "-")
						ScoreTwoChar.put(a + b, 0);
					else
						ScoreTwoChar.put(a + b, match);
				else if (a == "-" || b == "-")
					ScoreTwoChar.put(a + b, indel);
				else
					ScoreTwoChar.put(a + b, mismatch);
			}
		}

		for (String a : alphabet) {
			for (String b : alphabet) {
				for (String c : alphabet) {
					if (a == b && b == c && c == "-")
						continue;
					else {
						int sum = (Integer) ScoreTwoChar.get(a + b)
								+ (Integer) ScoreTwoChar.get(a + c)
								+ (Integer) ScoreTwoChar.get(b + c);
						ScoreThreeChar.put(a + b + c, sum);
					}

				}
			}
		}
		return ScoreThreeChar;
	}

	private static void testScoreTwoSeq(StringBuilder A, StringBuilder B) {
		NamedObject AB = ScoreAlignTwoSeq(A, B);
		System.out.println("Score Matrix\n" + AB.Score);
		System.out.println(AB.Aln[0]);
		System.out.println(AB.Aln[1]);
	}

	private static NamedObject testAlignThreeSeq(StringBuilder A, StringBuilder B, StringBuilder C) {

		NamedObject result = alignThreeSequence(A, B, C);
		System.out.format("%s\n%s\n%s\n", result.Aln[0],
				result.Aln[1], result.Aln[2]);
		return result;
	}

	private static NamedObject testRecursiveCall(StringBuilder A, StringBuilder B, StringBuilder C){
		NamedObject result = recursive_call(A, B, C);
		return result;
	}

	private static void test() {
		int[][] test = new int[3][4];
		int k = 0;
		// System.out.println("dimension of Array = (" + test.length + ", " +
		// test[0].length + ")");
		for (int i = 0; i < test.length; ++i) {
			for (int j = 0; j < test[0].length; ++j) {
				test[i][j] = k++;
			}
		}
		PrintHash(ScoreMatrixTable());
		testRecursiveCall(new StringBuilder("ATC"), new StringBuilder("ATC"), new StringBuilder("ATC"));
	}

	private static Pair<String,Integer> getMatchString(NamedObject result){
		String res = "";
		String A = result.Aln[0].toString();
		String B = result.Aln[1].toString();
		String C = result.Aln[2].toString();
		int strLen = A.length();
		int identical = 0;
		for (int i = 0; i < strLen; ++i){
			if (A.charAt(i) == B.charAt(i) && B.charAt(i) == C.charAt(i)) {
				res +=  "*" ;
				identical++;
			}
			else
				res += " ";
		}
		return new Pair<String, Integer>(res, identical);

	}

	private static class Pair<X, Y>{
		public final X first;
		public final Y second;
		public Pair(X a, Y b){
			this.first = a;
			this.second = b;
		}
	}

    // write NamedObject result into a writer 
	public static void writeResult(NamedObject result, PrintWriter writer){
		String A = result.Aln[0].toString();
		String B = result.Aln[1].toString();
		String C = result.Aln[2].toString();
		Pair<String, Integer> statPair = getMatchString(result);
		String M = statPair.first;
		int identicalMatches = statPair.second;
		int score = result.Score;
		int strLen = A.length();
		writer.format("score: %d\tlength:%d\tperfect match nts: %d\n\n", 
				score, strLen, identicalMatches);

		for (int i = 0; i < strLen; i += lineWidth){
			int end = (i + lineWidth < strLen) ? i + lineWidth : strLen;
			writer.format("A: %s\nB: %s\nC: %s\nM: %s\n\n", 
					A.substring(i, end), B.substring(i,  end),
					C.substring(i, end), M.substring(i,  end));
		}
	}
	
	private static class NamedObject2D {
		public final int[][] Score;
		public final int [][][] Path;
		public NamedObject2D(int[][] s, int [][][] p) {
			this.Score = s;
			this.Path = p;
		}
	}
	
	private static class NamedObject {
		// encapsulate class
		public final int Score;
		public final StringBuilder [] Aln;

		public NamedObject(int s, StringBuilder [] aln) {
			this.Score = s;
			this.Aln = aln;
		}
	}


	private static int[][] scoreThreeSeq(StringBuilder A, StringBuilder B, StringBuilder C) {
		HashMap score = ScoreMatrixTable();
		int new_indel = (Integer) score.get("A" + "-" + "-");
		int new_match = (Integer) score.get("A" + "A" + "-");
		int new_mismatch = (Integer) score.get("A" + "C" + "-");
		int identical = (Integer) score.get("A" + "A" + "A");
		int[][] prev = scoreTwoSeq(B, C);
		int [][] current = new int[B.length() + 1][C.length() + 1];
		long startTime = System.currentTimeMillis();
//		System.out.println(print2DMatrix(current));
		for (int a = 1; a < A.length() + 1; ++a) {
			current = new int[B.length() + 1][C.length() + 1];
			current[0][0] = prev[0][0] + new_indel;
			// fill dummy row when C == 0
//			if (a % 10 == 0){
//				System.err.format("%d/%d in scoreThreeSeq\n", a, A.length());
//				long endTime   = System.currentTimeMillis();
//				long totalTime = endTime - startTime;
//				startTime = endTime;
//				System.err.format("cost: " + new DecimalFormat("#.##").format(totalTime/1000.0) + " s\n");
//			}
				
			for (int b = 1; b < B.length() + 1; ++b) {
				int diagonal = prev[b - 1][0] + new_match;
				if (B.charAt(b - 1) != A.charAt(a - 1))
					diagonal = prev[b - 1][0] + new_mismatch;
				current[b][0] = Math.max(diagonal, Math.max(current[b - 1][0] + new_indel, prev[b][0] + new_indel));
			}
//			System.out.println(print2DMatrix(current));
			// fill dummy row when B == 0
			for (int c = 1; c < C.length() + 1; ++c) {
				int diagonal = prev[0][c - 1] + new_match;
				if (C.charAt(c - 1) != A.charAt(a - 1))
					diagonal = prev[0][c - 1] + new_mismatch;
				current[0][c] = Math.max(diagonal, Math.max(current[0][c - 1] + new_indel, prev[0][c] + new_indel));
			}
			
			int one, two, three, four, five, six, seven;
			for (int b = 1; b < B.length() + 1; ++b) {
				for (int c = 1; c < C.length() + 1; ++c) {
					seven = prev[b - 1][c - 1]
							+ (Integer) score.get(String.valueOf(A
									.charAt(a - 1))
									+ String.valueOf(B.charAt(b - 1))
									+ String.valueOf(C.charAt(c - 1)));
					if (A.charAt(a - 1) == B.charAt(b - 1))
						six = prev[b - 1][c] + new_match;
					else
						six = prev[b - 1][c] + new_mismatch;

					if (A.charAt(a - 1) == C.charAt(c - 1))
						five = prev[b][c - 1] + new_match;
					else
						five = prev[b][c - 1] + new_mismatch;				

					three = prev[b][c] + new_indel;
					if (B.charAt(b - 1) == C.charAt(c - 1))
						four = current[b - 1][c - 1] + new_match;
					else
						four = current[b - 1][c - 1] + new_mismatch;								 
					two = current[b - 1][c] + new_indel;
					one = current[b][c - 1] + new_indel;
					current[b][c] = Math.max(one, Math.max(two, Math.max(three, 
							Math.max(four, Math.max(five, Math.max(six, seven))))));
				}
			}
			//			System.out.println(print2DMatrix(current));
			prev = current;
		}
		
//		System.out.println(print2DMatrix(current));
		return current;
	}

	private static int [][] scoreTwoSeq(StringBuilder B, StringBuilder C) {
		int[][] M = new int[B.length() + 1][C.length() + 1];

		HashMap score = ScoreMatrixTable();
		int new_indel = (Integer) score.get("A" + "-" + "-");
		int new_match = (Integer) score.get("A" + "A" + "-");
		int new_mismatch = (Integer) score.get("A" + "C" + "-");
		for (int i = 1; i < C.length() + 1; ++i) {
			M[0][i] =  i * new_indel;
		}
		// initialize the dummy column
		for (int i = 1; i < B.length() + 1; ++i) {
			M[i][0] = i * new_indel;
		}

		for (int i = 1; i < B.length() + 1; ++i) {
			for (int j = 1; j < C.length() + 1; ++j) {
				int diagonal = M[i - 1][j - 1] + new_match;
				if (B.charAt(i - 1) != C.charAt(j - 1)) {
					// update diagonal with mismatch value
					diagonal = M[i - 1][j - 1] + new_mismatch;
				}
				M[i][j] = Math.max(diagonal, Math.max(M[i - 1][j] + new_indel, M[i][j - 1] + new_indel));
			}
		}
		return M;
	}
		
	private static NamedObject2D scoreTwoSeqBC(StringBuilder B, StringBuilder C) {
		int[][] M = new int[B.length() + 1][C.length() + 1];
		int[][][] P = new int[B.length() + 1][C.length() + 1][3];
		P[0][0][0] = -1;
		P[0][0][1] = -1;
		P[0][0][2] = -1;

		HashMap score = ScoreMatrixTable();
		int new_indel = (Integer) score.get("A" + "-" + "-");
		int new_match = (Integer) score.get("A" + "A" + "-");
		int new_mismatch = (Integer) score.get("A" + "C" + "-");
		for (int i = 1; i < C.length() + 1; ++i) {
			M[0][i] = i * new_indel;
			P[0][i][0] = 0;
			P[0][i][1] = 0;
			P[0][i][2] = i - 1;
		}
		// initialize the dummy column
		for (int i = 1; i < B.length() + 1; ++i) {
			M[i][0] = i * new_indel;
			P[i][0][0] = 0;
			P[i][0][1] = i - 1;
			P[i][0][2] = 0;
		}

		for (int i = 1; i < B.length() + 1; ++i) {
			for (int j = 1; j < C.length() + 1; ++j) {
				int diagonal = M[i - 1][j - 1] + new_match;
				int upper = M[i - 1][j] + new_indel;
				int left = M[i][j - 1] + new_indel;
				if (B.charAt(i - 1) != C.charAt(j - 1)) {
					// update diagonal with mismatch value
					diagonal = M[i - 1][j - 1] + new_mismatch;
				}
				M[i][j] = Math.max(diagonal, Math.max(upper, left));
				int first, second;
				if (M[i][j] == M[i - 1][j - 1] + new_match || M[i][j] == M[i - 1][j - 1] + new_mismatch){
					first = i - 1;
					second = j - 1;
				} else if ( M[i][j] == upper){
					first = i - 1;
					second = j;
				}else {
					first = i;
					second = j - 1;
				}
				P[i][j][0] = 0;
				P[i][j][1] = first;
				P[i][j][2] = second;
			}
		}
		return new NamedObject2D(M, P);

	}

	private static NamedObject2D scoreTwoSeqAB(StringBuilder A, StringBuilder B) {
		int[][] M = new int[A.length() + 1][B.length() + 1];
		// P is used to store the 3D coordinate for previous position, where the
		// alignment
		// comes from
		int[][][] P = new int[A.length() + 1][B.length() + 1][3];
		P[0][0][0] = -1;
		P[0][0][1] = -1;
		P[0][0][2] = -1;
		HashMap score = ScoreMatrixTable();
		int new_indel = (Integer) score.get("A" + "-" + "-");
		int new_match = (Integer) score.get("A" + "A" + "-");
		int new_mismatch = (Integer) score.get("A" + "C" + "-");

		// initialize the dummy row
		for (int i = 1; i < B.length() + 1; ++i) {
			M[0][i] = i * new_indel;
			P[0][i][0] = 0;
			P[0][i][1] = i - 1;
			P[0][i][2] = 0;
		}
		// initialize the dummy column
		for (int i = 1; i < A.length() + 1; ++i) {
			M[i][0] = i * new_indel;
			P[i][0][0] = i - 1;
			P[i][0][1] = 0;
			P[i][0][2] = 0;
		}

		for (int i = 1; i < A.length() + 1; ++i) {
			for (int j = 1; j < B.length() + 1; ++j) {
				int diagonal = M[i - 1][j - 1] + new_match;
				int upper = M[i - 1][j] + new_indel;
				int left = M[i][j - 1] + new_indel;
				if (A.charAt(i - 1) != B.charAt(j - 1)) {
					// update diagonal with mismatch value
					diagonal = M[i - 1][j - 1] + new_mismatch;
				}
				M[i][j] = Math.max(diagonal, Math.max(upper, left));
				
				int first, second;
				if (M[i][j] == M[i - 1][j - 1] + new_match || M[i][j] == M[i - 1][j - 1] + new_mismatch){
					first = i - 1;
					second = j - 1;
				} else if ( M[i][j] == upper){
					first = i - 1;
					second = j;
				}else {
					first = i;
					second = j - 1;
				}
				P[i][j][0] = first;
				P[i][j][1] = second;
				P[i][j][2] = 0;
			}
		}
		return new NamedObject2D(M, P);
	}

	private static NamedObject2D scoreTwoSeqAC(StringBuilder A, StringBuilder C) {
		int[][] M = new int[A.length() + 1][C.length() + 1];
		// P is used to store the 3D coordinate for previous position, where the
		// alignment
		// comes from
		int[][][] P = new int[A.length() + 1][C.length() + 1][3];
		P[0][0][0] = -1;
		P[0][0][1] = -1;
		P[0][0][2] = -1;
		HashMap score = ScoreMatrixTable();
		int new_indel = (Integer) score.get("A" + "-" + "-");
		int new_match = (Integer) score.get("A" + "A" + "-");
		int new_mismatch = (Integer) score.get("A" + "C" + "-");

		// initialize the dummy row
		for (int i = 1; i < C.length() + 1; ++i) {
			M[0][i] = i * new_indel;
			P[0][i][0] = 0;
			P[0][i][1] = 0;
			P[0][i][2] = i - 1;
		}
		// initialize the dummy column
		for (int i = 1; i < A.length() + 1; ++i) {
			M[i][0] = i * new_indel;
			P[i][0][0] = i - 1;
			P[i][0][1] = 0;
			P[i][0][2] = 0;
		}

		for (int i = 1; i < A.length() + 1; ++i) {
			for (int j = 1; j < C.length() + 1; ++j) {
				int diagonal = M[i - 1][j - 1] + new_match;
				int upper = M[i - 1][j] + new_indel;
				int left = M[i][j - 1] + new_indel;
				if (A.charAt(i - 1) == C.charAt(j - 1)) {
					diagonal = M[i - 1][j - 1] + new_mismatch;
				}
				M[i][j] = Math.max(diagonal, Math.max(upper, left));
				
				int first, second;
				if (M[i][j] == M[i - 1][j - 1] + new_match || M[i][j] == M[i - 1][j - 1] + new_mismatch){
					first = i - 1;
					second = j - 1;
				} else if ( M[i][j] == upper){
					first = i - 1;
					second = j;
				}else {
					first = i;
					second = j - 1;
				}
				P[i][j][0] = first;
				P[i][j][1] = 0;
				P[i][j][2] = second;
			}
		}
		return new NamedObject2D(M, P);
	}



	public static NamedObject ScoreAlignTwoSeq(StringBuilder B, StringBuilder C) {
		int[][] M = new int[B.length() + 1][C.length() + 1];
		int[][] P = new int[B.length() + 1][C.length() + 1];
		// if P[i][j] == 9, means need to look back left (i, j-1)
		// if P[i][j] == 10, means need to look diagonal (i-1, j-1)
		// if P[i][j] == 11, means need to look upper    (i-1, j)
		HashMap score = ScoreMatrixTable();
		int new_indel = (Integer) score.get("A" + "-" + "-");
		int new_match = (Integer) score.get("A" + "A" + "-");
		int new_mismatch = (Integer) score.get("A" + "C" + "-");
		StringBuilder [] T = new StringBuilder [] {new StringBuilder(), new StringBuilder()};
		StringBuilder B_aln = T[0];
		StringBuilder C_aln = T[1];
		for (int i = 1; i < C.length() + 1; ++i) {
			M[0][i] = M[0][i - 1] + new_indel;
			P[0][i] = 9;
		}
		// initialize the dummy column
		for (int i = 1; i < B.length() + 1; ++i) {
			M[i][0] = M[i - 1][0] + new_indel;
			P[i][0] = 11;
		}

		for (int i = 1; i < B.length() + 1; ++i) {
			for (int j = 1; j < C.length() + 1; ++j) {
				int diagonal = M[i - 1][j - 1] + new_match;
				int upper = M[i - 1][j] + new_indel;
				int left = M[i][j - 1] + new_indel;
				if (B.charAt(i - 1) != C.charAt(j - 1)) {
					// update diagonal with mismatch value
					diagonal = M[i - 1][j - 1] + new_mismatch;
				}
				M[i][j] = Math.max(diagonal, Math.max(upper, left));
				if (M[i][j] == diagonal){
					P[i][j] = 10;
				}
				else if (M[i][j] == upper){
					P[i][j] = 11;
				}
				else{
					P[i][j] = 9;
				}
			}
		}
		
		int i = B.length();
		int j = C.length();
		int max_score = M[i][j];
		while (P[i][j] != 0){
			switch(P[i][j]){
			case 10:
				B_aln.append(B.charAt(i-1));
				C_aln.append(C.charAt(j-1));
				i--; j--;
				break;
			case 9:
				B_aln.append('-');
				C_aln.append(C.charAt(j-1));
				j--;
				break;
			case 11:
				B_aln.append(B.charAt(i-1));
				C_aln.append('-');			
				i--;
				break;
			}
		}
		B_aln = B_aln.reverse();
		C_aln = C_aln.reverse();
		return new NamedObject(max_score, T);
	}
	
	private static NamedObject alignThreeSequence(StringBuilder A, StringBuilder B, StringBuilder C) {
		HashMap score = ScoreMatrixTable();
		int[][][] M = new int[A.length() + 1][B.length() + 1][C.length() + 1];
		int[][][][] P = new int[A.length() + 1][B.length() + 1][C.length() + 1][3];

		// convert 2D tuple to 3D tuple dummy surface A
		NamedObject2D result = scoreTwoSeqBC(B, C);
		M[0] = result.Score;
		P[0] = result.Path;
		
		int new_indel = (Integer) score.get("A" + "-" + "-");
		int new_match = (Integer) score.get("A" + "A" + "-");
		int new_mismatch = (Integer) score.get("A" + "C" + "-");
		int identical = (Integer) score.get("A" + "A" + "A");
		// convert 2D tuple to 3D tuple dummy surface B
		NamedObject2D AC = scoreTwoSeqAC(A, C);
		for (int i = 0; i < A.length() + 1; ++i) {
			for (int j = 0; j < C.length() + 1; ++j) {
				M[i][0][j] = AC.Score[i][j];
				P[i][0][j] = AC.Path[i][j];
			}
		}
		// convert 2D tuple to 3D tuple dummy surface C
		NamedObject2D AB = scoreTwoSeqAB(A, B);
		for (int i = 0; i < A.length() + 1; ++i) {
			for (int j = 0; j < B.length() + 1; ++j) {
				M[i][j][0] = AB.Score[i][j];
				P[i][j][0] = AB.Path[i][j];
			}
		}
		

		int one, two, three,four, five, six, seven;
		for (int a = 1; a < A.length() + 1; ++a) {
			for (int b = 1; b < B.length() + 1; ++b) {
				for (int c = 1; c < C.length() + 1; ++c) {
					seven = M[a - 1][b - 1][c - 1]
							+ (Integer) score.get(String.valueOf(A
									.charAt(a - 1))
									+ String.valueOf(B.charAt(b - 1))
									+ String.valueOf(C.charAt(c - 1)));
					if (A.charAt(a - 1) == B.charAt(b - 1))
						six = M[a - 1][b - 1][c] + new_match;
					else
						six = M[a - 1][b - 1][c]+ new_mismatch;

					if (A.charAt(a - 1) == C.charAt(c - 1))
						five = M[a - 1][b][c - 1] + new_match;
					else
						five = M[a - 1][b][c - 1] + new_mismatch;	
				
					three = M[a - 1][b][c] + new_indel;
					if (B.charAt(b - 1) == C.charAt(c - 1))
						four = M[a][b - 1][c - 1] + new_match;
					else
						four = M[a][b - 1][c - 1] + new_mismatch;
					two = M[a][b - 1][c] + new_indel;
					one = M[a][b][c - 1] + new_indel;

					M[a][b][c] = Math.max(one, Math.max(two, Math.max(three, 
							Math.max(four, Math.max(five, Math.max(six, seven))))));
					int first, second, third;
					if (M[a][b][c] == seven) {
						first = a - 1;
						second = b - 1;
						third = c - 1;
					} else if (M[a][b][c] == six) {
						first = a - 1;
						second = b - 1;
						third = c;
					} else if (M[a][b][c] == four) {
						first = a;
						second = b - 1;
						third = c - 1;
					} else if (M[a][b][c] == five) {
						first = a - 1;
						second = b;
						third = c - 1;
					} else if (M[a][b][c] == one) {
						first = a;
						second = b;
						third = c - 1;
					} else if (M[a][b][c] == two) {
						first = a;
						second = b - 1;
						third = c;
					} else if (M[a][b][c] == three){ // three
						first = a - 1;
						second = b;
						third = c;
					}else {
						throw new EmptyStackException();
					}
					P[a][b][c][0] = first;
					P[a][b][c][1] = second;
					P[a][b][c][2] = third;
				}
			}
		}

		int a = A.length();
		int b = B.length();
		int c = C.length();

		int max_score = M[a][b][c];
		StringBuilder A_aln = new StringBuilder();
		StringBuilder B_aln = new StringBuilder();
		StringBuilder C_aln = new StringBuilder();
		
		while (!(P[a][b][c][0] == -1 && P[a][b][c][1] == -1 && P[a][b][c][2] == -1)) {

			int new_a = P[a][b][c][0];
			int new_b = P[a][b][c][1];
			int new_c = P[a][b][c][2];
			A_aln.append(a - new_a > 0 ? A.charAt(a - 1) : '-');
			B_aln.append(b - new_b > 0 ? B.charAt(b - 1) : '-');
			C_aln.append(c - new_c > 0 ? C.charAt(c - 1) : '-');
			a = new_a;
			b = new_b;
			c = new_c;
		}
		
		A_aln = A_aln.reverse();
		B_aln = B_aln.reverse();
		C_aln = C_aln.reverse();
		
		return new NamedObject(max_score, new StringBuilder[] {A_aln, B_aln, C_aln});
	}

	private static StringBuilder reverseStr(StringBuilder A) {
		
		return new StringBuilder(A.toString()).reverse();
	}
    
    // find the 2D coordinates that maximizes the sum of two matrices
    // store 2D coordinates into result
	private static int[] partionBC(int[][] upper, int[][] down) {
		int[][] rotated180 = ClockWiseRotate90Degree(ClockWiseRotate90Degree(down));
		int[] result = new int[2];
		int max = -1000000;
		for (int i = 0; i < upper.length; ++i) {
			for (int j = 0; j < upper[0].length; ++j) {
				if (upper[i][j] + rotated180[i][j] > max) {
					max = upper[i][j] + rotated180[i][j];
					result[0] = i;
					result[1] = j;
				}
			}
		}
		return result;
	}

	private static StringBuilder repeatStr(char str, int times){
		StringBuilder n = new StringBuilder();
		for (int i = 0; i < times; ++i){
			n.append(str);
		}
		return n;
	}

	public static NamedObject recursive_call(StringBuilder A, StringBuilder B, StringBuilder C) {
//		System.err.format("len(A)=%d, len(B)=%d, len(C)=%d\n", A.length(), B.length(), C.length());
//		System.err.format("A:%s\nB:%s\nC:%s\n", A, B, C);
		if (A.length() == 0 || B.length() == 0 || C.length() == 0) {
			StringBuilder [] n  = new StringBuilder [3];
			int score;
			if (A.length() == 0){
				NamedObject BC = ScoreAlignTwoSeq(B, C);
				n[0] = repeatStr('-', BC.Aln[0].length());
				n[1] = BC.Aln[0];
				n[2] = BC.Aln[1];
				score = BC.Score;
			}else if (B.length() == 0){
				NamedObject AC = ScoreAlignTwoSeq(A, C);
				n[1] = repeatStr('-', AC.Aln[0].length());
				n[0] = AC.Aln[0];
				n[2] = AC.Aln[1];
				score = AC.Score;
			} else{
				NamedObject AB = ScoreAlignTwoSeq(A, B);
				n[2] = repeatStr('-', AB.Aln[0].length());
				n[0] = AB.Aln[0];
				n[1] = AB.Aln[1];
				score = AB.Score;
			}
			return new NamedObject(score, n);	
		}
		if (A.length() <= 1 || B.length() <= 1 || C.length() <= 1) {
			return alignThreeSequence(A, B, C);
		} else {
			int xmid = A.length() / 2;
			StringBuilder firstHalf = new StringBuilder(A.substring(0, xmid));
			StringBuilder secondHalf = new StringBuilder(A.substring(xmid));
			int[][] upper = scoreThreeSeq(firstHalf, B, C);
			int[][] down = scoreThreeSeq(
					reverseStr(secondHalf), reverseStr(B),
					reverseStr(C));
			int[] coordinates = partionBC(upper, down);
			int b = coordinates[0];
			int c = coordinates[1];
//			System.err.format("xmid=%d, b=%d, c=%d\n", xmid, b, c);
//			System.err.format("A: %s\nB:%s\nC:%s\n", A, B, C);
			NamedObject r_upper = recursive_call(
					firstHalf, new StringBuilder(B.substring(0, b)),
					new StringBuilder(C.substring(0, c)));
			NamedObject r_down = recursive_call(secondHalf,
					new StringBuilder(B.substring(b)), 
					new StringBuilder(C.substring(c)));
			StringBuilder [] T = new StringBuilder[] {
					r_upper.Aln[0].append(r_down.Aln[0]),
					r_upper.Aln[1].append(r_down.Aln[1]),
					r_upper.Aln[2].append(r_down.Aln[2])};
			return new NamedObject(r_upper.Score + r_down.Score, T);
		}
	}

	public static Pair<String, String> fastaReader(String fileName) throws IOException{
		StringBuilder result = new StringBuilder();
		String head = "";
		try {  
			BufferedReader br = new BufferedReader(new FileReader(fileName));  
			String br1 ;  

			boolean flag = false;
			while ( (br1 = br.readLine()) != null  ){  
				if (br1.startsWith(">"))
					if (!flag){
						head = br1;
						flag = true;
					}else{
						System.out.println("Error in parsing: " + fileName + 
								" Note: only one fasta record is allowed in input file");
						System.exit(1);
					}			
				else 
					result.append(br1);
			}  
			br.close();  
		}catch ( FileNotFoundException e ){  
			e.printStackTrace();  
		}catch ( IOException e){  
			e.printStackTrace();  
		}
		return new Pair<String, String> (head, result.toString());
	}

    // associate a write object with a filename	
	public static PrintWriter getWriter(String fileName){
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(fileName, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return writer;
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 3){
			System.out.println("number of files has to be three!");
			System.exit(1);
		}
		long startTime = System.currentTimeMillis();
		String A = fastaReader(args[0]).second;
		String B = fastaReader(args[1]).second;
		String C = fastaReader(args[2]).second;
		String outFileName = String.format("%s_%s_%s", args[0], args[1], args[2]);
		PrintWriter writer = getWriter(outFileName);
		NamedObject result = recursive_call(new StringBuilder(A), new StringBuilder(B), new StringBuilder(C));
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		writeResult(result, writer);
		writer.println("Running_time: " + new DecimalFormat("#.##").format(totalTime/1000.0) + " s");
		MemoryUsage heapMemoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
		writer.println("Allocated memory for JVM :"+heapMemoryUsage.getCommitted()/(1024*1024) + " Megabytes");
		writer.println("Initially requested memory :"+heapMemoryUsage.getInit()/(1024*1024) + " Megabytes");
		writer.println("Maximum memory can be used :"+heapMemoryUsage.getMax()/(1024*1024) + " Megabytes");
		writer.println("Memory used by the JVM :"+heapMemoryUsage.getUsed()/(1024*1024) + " Megabytes");
		writer.close();
	}
}



