//Name : Dastagiri Reddy Malikireddy
//Wisc username : malikireddy
//Homework : 5
//Class section : Prof Jerry Zhu
//Submission date : 25th October 2016
//Late days : 0
import java.util.*;

class State {
	public char[] val = new char[12];
	public int gtval = -10; //assume that value initially is -10 (initialization)
	public int alpha = -10;
	public int beta = -10;
	public char turn;
	public List<State> children;

	public State(char[] args, char turn) {
		for (int i=0; i<12; i++)
			val[i] = args[i];
		this.turn = turn;
		this.children = new ArrayList<State>();
	}

	public void printState() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<4; j++)
				System.out.print(val[4*i+j] + " ");
			System.out.print("\n");
		}
	} 
}

public class TicTacToe {

//Static method to check if the game is over or not (i.e. finding the leaf nodes)
public static boolean checkIfLeafNode (State s) {

	//Horizontal checks for 3 continuous occurrences of X's or O's 
	if (s.val[0] == s.val[1] && s.val[1] == s.val[2] && s.val[0] != '_' && s.val[0] != s.val[3]) {
		if (s.val[0] == 'O')
			s.gtval = 1;
		else if (s.val[0] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[1] == s.val[2] && s.val[2] == s.val[3] && s.val[1] != '_' && s.val[0] != s.val[1]) {
		if (s.val[1] == 'O')
			s.gtval = 1;
		else if (s.val[1] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[5] == s.val[6] && s.val[6] == s.val[7] && s.val[5] != '_') {
		if (s.val[5] == 'O')
			s.gtval = 1;
		else if (s.val[5] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[8] == s.val[9] && s.val[9] == s.val[10] && s.val[8] != '_' && s.val[10] != s.val[11]) {
		if (s.val[8] == 'O')
			s.gtval = 1;
		else if (s.val[8] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[9] == s.val[10] && s.val[10] == s.val[11] && s.val[9] != '_' && s.val[8] != s.val[9]) {
		if (s.val[9] == 'O')
			s.gtval = 1;
		else if (s.val[9] == 'X')
			s.gtval = -1;
		return true;
	}

	//Vertical checks
	if (s.val[1] == s.val[5] && s.val[5] == s.val[9] && s.val[1] != '_') {
		if (s.val[1] == 'O')
			s.gtval = 1;
		else if (s.val[1] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[2] == s.val[6] && s.val[6] == s.val[10] && s.val[2] != '_') {
		if (s.val[5] == 'O')
			s.gtval = 1;
		else if (s.val[5] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[3] == s.val[7] && s.val[7] == s.val[11] && s.val[3] != '_') {
		if (s.val[3] == 'O')
			s.gtval = 1;
		else if (s.val[3] == 'X')
			s.gtval = -1;
		return true;
	}

	//Diagonal checks
	if (s.val[0] == s.val[5] && s.val[5] == s.val[10] && s.val[0] != '_') {
		if (s.val[5] == 'O')
			s.gtval = 1;
		else if (s.val[5] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[2] == s.val[5] && s.val[5] == s.val[8] && s.val[2] != '_') {
		if (s.val[5] == 'O')
			s.gtval = 1;
		else if (s.val[5] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[1] == s.val[6] && s.val[6] == s.val[11] && s.val[1] != '_') {
		if (s.val[6] == 'O')
			s.gtval = 1;
		else if (s.val[6] == 'X')
			s.gtval = -1;
		return true;
	}
	if (s.val[3] == s.val[6] && s.val[6] == s.val[9] && s.val[3] != '_') {
		if (s.val[6] == 'O')
			s.gtval = 1;
		else if (s.val[6] == 'X')
			s.gtval = -1;
		return true;
	}
	
	//For leaf nodes wherein all entries are filled and none is the winner
	int flag = 1;
	for(int i=0; i<12; i++)
		if (s.val[i] == '_')
			flag = 0;
	if (flag == 1) {
		s.gtval = 0;
		return false;
	}
	return false;	
}

//Static method to create the game search tree
public static void createGameSearchTree (State s) {
	//s.printState();
	if (checkIfLeafNode(s))
		return;

	List<Integer> empty = new ArrayList<Integer>();
	for (int i=0; i<12; i++) {
		if (s.val[i] == '_') 
			empty.add(i);
	}

	State t = null;
	for (int i=0; i<empty.size(); i++) {
		char[] val = new char[12];
		for (int j=0; j<12; j++)
			val[j] = s.val[j];
		
		if (s.turn == 'X') {
			val[empty.get(i)] = 'X';
			t = new State(val, 'O');
		}
		else {
			val[empty.get(i)] = 'O';
			t = new State(val, 'X');
		}
		s.children.add(t);
		createGameSearchTree(t);
	}
	
		
}

//Static method to traverse the tree and print the alpha-beta trace for the states
public static void traverse(State s) {
	if (s.children.size() == 0) {
		if(!(s.alpha == -10 && s.beta == -10)) {
			s.printState();
			System.out.println("Alpha : "+Integer.toString(s.alpha)+" Beta: "+Integer.toString(s.beta));
		}
		return;
	}
	
	for (int i=0; i<s.children.size(); i++) 
		traverse(s.children.get(i));

	if(!(s.alpha == -10 && s.beta == -10)) {
		s.printState();
		System.out.println("Alpha : "+Integer.toString(s.alpha)+" Beta: "+Integer.toString(s.beta));
	}
	return;
}

//Static methods to compute the alpha-beta values for each node RIGHT BEFORE RETURNING
public static int computeMinValue(State s, int alpha, int beta) { 
	s.alpha = alpha;
	s.beta = beta;
	if (s.children.size() == 0) 
		return s.gtval;

	for (int i=0; i<s.children.size(); i++) {
		int val = computeMaxValue(s.children.get(i), s.alpha, s.beta);
		s.beta = s.beta > val ? val : s.beta;
		if (s.alpha >= s.beta)
		return s.alpha;
	}
	return s.beta;
}

public static int computeMaxValue(State s, int alpha, int beta) {
	s.alpha = alpha;
	s.beta = beta;
	if (s.children.size() == 0) 
		return s.gtval;

	for (int i=0; i<s.children.size(); i++) {
		int val = computeMinValue(s.children.get(i), s.alpha, s.beta);
		s.alpha = s.alpha < val ? val : s.alpha;
		if (s.alpha >= s.beta)
		return s.beta;
	}
	return s.alpha;
}

//Static method to compute the game-theoretic values for all the nodes
public static void computeMiniMax(State s) {

	List<Integer> vals = new ArrayList<Integer>();

	for (int i=0; i<s.children.size(); i++) {
		if (s.children.get(i).gtval == -10)
			computeMiniMax(s.children.get(i));
		vals.add(s.children.get(i).gtval);
	}
	if (s.turn == 'X')
		s.gtval =  Collections.min(vals);
	else
		s.gtval =  Collections.max(vals);
	return;
}

public static void main (String[] args) {

	if (args.length != 13) {
		System.out.println("Incorrect number of arguments. Please enter correctly");
		return;
	}
	
	char[] vals = new char[12];
	int countO = 0;
	int countX = 0;
	int countu = 0;

	for (int i=0; i<12; i++) {
		vals[i] = args[i].charAt(0);
		
		if (vals[i] == 'X')
			countX += 1;
		else if (vals[i] == 'O')
			countO += 1;
		else if (vals[i] == '_')
			countu += 1;
	}
	if (countO < countX) {
		System.out.println("Given that the human (player O) always start first, this is not a valid state.Note it must be X's turn");
		return;
	} else {
		if ( countu!=0 && countO - countX != 1) {
			System.out.println("Something just doesn't seem right. Please enter a valid state. Note it must be X's turn");
			return;
		}
	}
	State init = new State(vals, 'X');   //it is X's turn initially
 
	createGameSearchTree(init);
	if (init.children.size() == 0) {
		System.out.println("You entered :");
		init.printState();
		if (init.gtval == 1)
			System.out.println(" Game Over : Player X (computer) has already lost the game! X does not need to make a move.");
		else if (init.gtval == -1)
			System.out.println(" Game Over : Player X (computer) has already won the game! X does not need to make a move.");
		else
			System.out.println(" Game Over : It is a draw! X does not need to make a move.");
		return;
	}

	if (args[12].charAt(0) == 'Y') {
	//Print the alpha-beta trace with the states (Post order traversal of the game search tree)
		computeMinValue(init, -2, 2); //initially alpha = -2, beta = 2
		traverse(init);
	}

	computeMiniMax(init);
	
	//Print the solution
	System.out.println("SOLUTION");
	if (init.gtval == -1) {
		
		int check = 0;
		for (int i=0; i<init.children.size(); i++) { 
			if (init.children.get(i).gtval == -1) {
				init.children.get(i).printState();
				check = 1;
			}
			if (check == 1)
				break;
		}
	}
	else if (init.gtval == 0) {
		
		int check = 0;
		for (int i=0; i<init.children.size(); i++) { 
			if (init.children.get(i).gtval == 0) {
				init.children.get(i).printState();
				check = 1;
			}
			if (check == 1)
				break;
		}
	}
	else {
		System.out.println("cool" + init.gtval);
		init.children.get(0).printState();
	}

	return;
	
}
}
