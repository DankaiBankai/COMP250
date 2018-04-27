package A2;

/* Daniel Baker 
 * 260805345

 */

import java.util.Stack; 
import java.util.ArrayList;

public class Expression  {
	private ArrayList<String> tokenList;

	//  Constructor    
	/**
	 * The constructor takes in an expression as a string
	 * and tokenizes it (breaks it up into meaningful units)
	 * These tokens are then stored in an array list 'tokenList'.
	 */

	Expression(String expressionString) throws IllegalArgumentException{
		tokenList = new ArrayList<String>();
		StringBuilder token = new StringBuilder();

		//ADD YOUR CODE BELOW HERE
		for (int i = 0; i < expressionString.length(); i++) {
			char c = expressionString.charAt(i);
			if (c != ' ') { // removes spaces from input
				token.append(c); // appends each valid char to token
			}
		}
		for (int i = 0; i < token.length(); i++) {
			if (isInteger(String.valueOf(token.charAt(i)))) {
				int countInt = 0;
				int k = i;
				while ((k+1) < token.length() && isInteger(String.valueOf(token.charAt(k+1)))) { // finds number of consecutive integer digits
					countInt++;
					k++;
				}
				tokenList.add(token.substring(i, i+countInt+1)); // adds consecutive integer digits to token
				i= i + countInt; // updates i so we dont add duplicates
			}
			else if(((String.valueOf(token.charAt(i)).equals("+")) && (String.valueOf(token.charAt(i+1)).equals("+"))) 
					|| (String.valueOf(token.charAt(i)).equals("-")) && (String.valueOf(token.charAt(i+1)).equals("-"))) { // handles the increment and decrement operators
				tokenList.add(token.substring(i, i + 2));
				i = i + 1;
			}
			 else {
					tokenList.add(String.valueOf(token.charAt(i))); // handles the rest
			 	}
			}
	

			
		

		
		
		
		
		
		//ADD YOUR CODE ABOVE HERE
	}
	
	

	/**
	 * This method evaluates the expression and returns the value of the expression
	 * Evaluation is done using 2 stack ADTs, operatorStack to store operators
	 * and valueStack to store values and intermediate results.
	 * - You must fill in code to evaluate an expression using 2 stacks
	 */
	public Integer eval(){ 
		Stack<String> operatorStack = new Stack<String>();
		Stack<Integer> valueStack = new Stack<Integer>();
		
		//ADD YOUR CODE BELOW HERE
		String brackets= "()[]";
		String operators = "+-/*++--";
		
		
		int count1 = 0;
		for(int k = 0; k < tokenList.size() || valueStack.size() > 1; k =count1) { // builds up the 2 stacks
			
			while(count1 < tokenList.size() && (!((tokenList.get(count1)).equals(")")))) {
				if (brackets.contains(tokenList.get(count1)) || operators.contains(tokenList.get(count1))) { //push to stack when encouters first bracket or operator
					operatorStack.push(tokenList.get(count1));
				}
				if(isInteger((tokenList.get(count1)))) {
					valueStack.push(Integer.valueOf(tokenList.get(count1)));
				}
				count1++;
			}

			while(!operatorStack.empty() && !operatorStack.peek().equals("(")) { // handles the different operators and brackets using previous stacks
				
				if (!operatorStack.empty() && operatorStack.peek().equals("]")) {
					operatorStack.pop();
				}
				if(!operatorStack.empty() && operatorStack.peek().equals("++")) {
					int m = Integer.valueOf(valueStack.pop());
					valueStack.push(m+1);
					operatorStack.pop();
				}
				if(!operatorStack.empty() && operatorStack.peek().equals("--")) {
					int m = Integer.valueOf(valueStack.pop());
					valueStack.push(m-1);
					operatorStack.pop();
				}
				if (!operatorStack.empty() && operatorStack.peek().equals("[")) {
					int m = Integer.valueOf(valueStack.pop());
					valueStack.push(Math.abs(m));
					operatorStack.pop();
				}
				if (!operatorStack.empty() && operatorStack.peek().equals("+")){
					int m = Integer.valueOf(valueStack.pop());
					int n = Integer.valueOf(valueStack.pop());
					valueStack.push(m+n);
					operatorStack.pop();
				}
				if (!operatorStack.empty() && operatorStack.peek().equals("-")){
					int m = Integer.valueOf(valueStack.pop());
					int n = Integer.valueOf(valueStack.pop());
					valueStack.push(n-m);
					operatorStack.pop();
				}
				if (!operatorStack.empty() && operatorStack.peek().equals("*")){
					int m = Integer.valueOf(valueStack.pop());
					int n = Integer.valueOf(valueStack.pop());
					valueStack.push(m*n);
					operatorStack.pop();
				}
				if (!operatorStack.empty() && operatorStack.peek().equals("/")){
					int m = Integer.valueOf(valueStack.pop());
					int n = Integer.valueOf(valueStack.pop());
					valueStack.push(n/m);
					operatorStack.pop();
				}
			}
			if (!operatorStack.empty()) {
				operatorStack.pop();
			}
			count1 = count1 + 1;		
		}
		return Integer.valueOf(valueStack.peek());
		
		
		//ADD YOUR CODE ABOVE HERE

		
	}

	//Helper methods
	/**
	 * Helper method to test if a string is an integer
	 * Returns true for strings of integers like "456"
	 * and false for string of non-integers like "+"
	 * - DO NOT EDIT THIS METHOD
	 */
	private boolean isInteger(String element){
		try{
			Integer.valueOf(element);
		}catch(NumberFormatException e){
			return false;
		}
		return true;
	}

	/**
	 * Method to help print out the expression stored as a list in tokenList.
	 * - DO NOT EDIT THIS METHOD    
	 */

	@Override
	public String toString(){	
		String s = new String(); 
		for (String t : tokenList )
			s = s + "~"+  t;
		return s;		
	}

}



