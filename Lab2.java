
/**
* Lab 2
* creates a room with the size and obstacles determined when providing extra arguments while running program in terminal.
* CS107
* 10/22/19
* @author Marcus
*
*X = wall
*. = nothing
*O = object
*p = runner
*/
import java.util.Scanner;
import java.util.Random;

public class Lab2{
	public static Random random = new Random();
	public static Scanner scanner = new Scanner(System.in);
	public static void main(String[] args){
		boolean error = true;
		int width = 0;
		int length = 0;
		double percent = 0;

		try{
			//assigns values to variables according to the amount of arguments in command
			if(args.length == 1){
				width = Integer.parseInt(args[0]);
				length = Integer.parseInt(args[0]);
				percent = 0.2;
			}
			else if(args.length == 2){
				width = Integer.parseInt(args[0]);
				length = Integer.parseInt(args[1]);
				percent = 0.2;
			}
			else{
				width = 12;
				length = 12;
			}
			//checks if width and length are greater than 0					
			if(width == 0 || length == 0){
				width = 12;
				length = 12;
			}
			System.out.print("Enter the density for objects in room (enter a decimal value): ");
			percent = scanner.nextDouble();
		}
		
		catch(Exception e){
			//response to wrong input type
			System.out.println("Incorrect argument type, please enter an integer for first two arguments and a decimal for third argument.");
			return;
		}

			//initilizing room using 2d array
			char[][] room = new char[length][width];
			//creates room with everything in it
			createSpace(room);
			createWalls(room);
			placeObjects(room, percent);
			//outputs created room
			System.out.println("enter e to exit the program\n");
			System.out.println("A " + width + "x" + length + " room with " + percent + " obstacle density");
			error = true;
			
			//Runner extra credit
			int runnerX = findRunner('x', room);
			int runnerY = findRunner('y', room);
			while(true){
				//wasd controls and runner movement
				String input = scanner.nextLine();
				if(input.equalsIgnoreCase("w")){
					//makes sure next cell is open
					if(room[runnerY-1][runnerX] == '.'){
						//replaces old cell with empty space
						room[runnerY][runnerX] = '.';
						//replaces new cell with runner and increments it
						room[--runnerY][runnerX] = 'p';
					}
				}
				else if(input.equalsIgnoreCase("a")){
					if(room[runnerY][runnerX-1] == '.'){
						room[runnerY][runnerX] = '.';
						room[runnerY][--runnerX] = 'p';
					}
				}
				else if(input.equalsIgnoreCase("s")){
					if(room[runnerY+1][runnerX] == '.'){
						room[runnerY][runnerX] = '.';
						room[++runnerY][runnerX] = 'p';
					}
				}
				//checks that the move won't be greater than the length of the array before making the move
				else if(input.equalsIgnoreCase("d") && runnerX+1 != width){
					if(room[runnerY][runnerX+1] == '.'){
						room[runnerY][runnerX] = '.';
						room[runnerY][++runnerX] = 'p';
					}
				}
				//stops the game if e is entered
				if(input.equalsIgnoreCase("e")){
					break;
				}
				//prints room after user input has been entered  
				print2DArray(room);
				
				if(runnerX == 0){
					System.out.println("You Won!");
					break;
				}
					
			}
	}
	
	//method for printing 2d arrays
	public static void print2DArray(char[][] arr){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	//puts the empty spaces indicating characters in the room array
	public static void createSpace(char[][] arr){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				arr[i][j] = '.';
			}
		}
	}
	
	//method to make wall of room
	public static void createWalls(char[][] arr){
		int entrance = random.nextInt(arr.length - 3) + 1;
		int exit = random.nextInt(arr.length - 3) + 1;
		for(int i = 0; i < arr.length; i++){
			if(i == 0 || i == arr.length - 1){
				for(int j = 0; j < arr[i].length; j++){
					arr[i][j] = 'X';
				}
			}
			else{
				if(i == exit)
					arr[i][0] = '.';
				else
					arr[i][0] = 'X';
				
				if(i == entrance)
					arr[i][arr[i].length - 1] = 'p';
				else
					arr[i][arr[i].length - 1] = 'X';
			}
		}
	}
	
	//pre-condition: empty spaces and walls are added into the room array using creatWalls() and createSpace() methods
	//places objects in room array
	public static void placeObjects(char[][] arr, double percent){
		for(int i = 1; i < arr.length - 1; i++){
			for(int j = 1; j < arr[i].length - 1; j++){
				if((Math.random()) <= percent){
					arr[i][j] = 'O';
				}
			}
		}
	}
	
	//returns players coordinate depending on arguement entered where x returns x position and entering y returns y position
	public static int findRunner(char coord, char[][] arr){
		for(int i = 0; i < arr.length; i++){
			for(int j = 0; j < arr[i].length; j++){
				if(arr[i][j] == 'p' && coord == 'x')
					return j;
				if(arr[i][j] == 'p' && coord == 'y')
					return i;
					
			}
		}
		return -1;
	}
}