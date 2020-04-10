import java.util.Scanner;

public class Bankers {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("How many processes will the matrix have?");
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
		
		int[][] need = CalcNeedMatrix(max, alloc, rows, cols);
		
		System.out.println("Do you want to make a request? Please type 'y' or 'n'.");
		String request = s.next();
		if(request.equals("y")) {
			System.out.println("What process is this request for? Options are from 0 to " + rows);
			int process = s.nextInt();
			int[] req = checkRequest(need, avail, cols, s, process);
			//request is granted, remove the request from the avail and add to alloc
			for(int i=0; i<cols;i++) {
				avail[i] = avail[i] - req[i];
				alloc[process][i] = alloc[process][i] + req[i];
			}
		} else if (request.equals("n")) {
			System.out.println("No request made, checking if there is a safe sequence...");
		} else {
			System.out.println("Invalid Input. Assuming no request.");
		}
		
		checkIfSafe(rows, cols, avail, max, need, alloc);
		s.close();
	}

	private static int[] checkRequest(int[][] need, int[] avail, int cols, Scanner s, int process) {
		int[] request = new int[cols];
		System.out.println("Please enter the "+cols+" numbers for the request");
		for(int i=0; i<cols; i++) {
			request[i] = s.nextInt();
		}
		//check if request is greater than available
		for(int i=0; i<cols; i++) {
			if(request[i] > avail[i]) {
				System.out.println("Request is greater than what's available. Denied");
				System.exit(0);
			}
		}
		//check if request is greater than need
		for(int i=0; i<cols; i++) {
			if(request[i] > need[process][i]) {
				System.out.println("Request is greater than the need. Denied");
				System.exit(0);
			}
		}
		System.out.println("Request Granted. Removing request from available and adding to P"+process+"'s allocation");
		return request;
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
		return matrix;
	}

}
