import java.util.Scanner;

public class Bankers {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("How many rows will the matrix have?");
		int rows = s.nextInt();
		System.out.println("How many columns will the matrix have?");
		int cols = s.nextInt();
		
		System.out.println("Enter the elements of the allocation matrix");
		int[][] alloc = getInput(rows, cols, s);
		
		System.out.println("Enter the elements of the max matrix");
		int[][] max = getInput(rows, cols, s);
		
		System.out.println("Enter the " + cols + " values for available resources");
		int[] avail = new int[rows];
		for (int i=0; i<cols; i++) {
			avail[i] = s.nextInt();
		}
		s.close();
		
		int[][] need = CalcNeedMatrix(max, alloc, rows, cols);
		
		checkIfSafe(rows, cols, avail, max, need, alloc);
	}

	private static void checkIfSafe(int rows, int cols, int[] avail, int[][] max, int[][] need, int[][] alloc) {
		int count = 0;
		int[] safe = new int[rows];
		
		boolean[] visited = new boolean[rows];
		for (int i=0; i<rows; i++) {
			visited[i] = false;
		}
		
		int[] work = new int[cols];
		for(int i=0; i<cols; i++) {
			work[i] = avail[i];
		}
		
		while(count<rows) {
			boolean f = false;
			for(int i = 0; i<rows;i++) {
				if(visited[i] == false) {
					int j;
					for(j=0;j<cols;j++) {
						if(need[i][j] > work[j]) {
							break;
						}
					}
					if (j == cols) {
						safe[count++] = i;
						visited[i] = true;
						f = true;
						
						for(j = 0; j<cols; j++) {
							work[j] = work[j] + alloc[i][j];
						}
					}
				}
			}
			if (f == false) {
				break;
			}
		}
		if(count<rows) {
			System.out.println("Bro, this thing is unsafe");
		} else {
			System.out.println("Safe Sequence: ");
			for(int i=0; i<rows;i++) {
				System.out.print("P" + safe[i]);
				if (i != rows-1) {
					System.out.print(" -> ");
				}
			}
		}
	}

	private static int[][] CalcNeedMatrix(int[][] max, int[][] alloc, int rows, int cols) {
		int[][] need = new int[rows][cols];
		for (int i=0; i<rows; i++) {
			for (int j = 0; j<cols; j++) {
				need[i][j] = max[i][j]-alloc[i][j];
			}
		}
		return need;
	}

	private static int[][] getInput(int rows, int cols, Scanner s) {

		int[][] matrix = new int[rows][cols]; 
		
		
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				matrix[i][j] = s.nextInt();
			}
		}
		
//		System.out.println("Elements of the matrix are"); 
//        for (int i = 0; i < rows; i++) { 
//            for (int j = 0; j < cols; j++) 
//                System.out.print(matrix[i][j] + "  "); 
//            System.out.println(); 
//        } 
		
		return matrix;
	}

}
