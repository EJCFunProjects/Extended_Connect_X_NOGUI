package cpsc2150.extendedConnectX;

import cpsc2150.extendedConnectX.models.GameBoard;
import cpsc2150.extendedConnectX.models.GameBoardMem;
import cpsc2150.extendedConnectX.models.IGameBoard;
import java.util.Scanner;

public class GameScreen{
	public static void main(String args[]){

		Scanner scn = new Scanner(System.in);
		char current = ' ';
		int zero = 0;


		boolean hasWon = false;
		boolean checkValid = false;
		boolean tryAgain = false;
		IGameBoard theBoard;

		//The While loop decides weather the game continues or quits after a tie or win.
        while(tryAgain != true) {
        	//Default Board created for each new game.
			theBoard = new GameBoard(zero,zero,zero);
			int c = 0;//Column Choice
			int players = 0;

			String userInput;
			hasWon = false;
			tryAgain = false;

			String gameInput;//Used to input rows,columns and winning tokens amount
			boolean accept = false;
			int upperBound = 10;
			int lowerBound = 2;

			while(accept == false){

				System.out.println("How many players?");
				gameInput = scn.nextLine();
				players = Integer.parseInt(gameInput);

				if(players > upperBound){
					System.out.println("Must be " + upperBound + " or fewer");
				}
				else if(players < lowerBound){
					System.out.println("Must be at least " + lowerBound + " players");
				}
				else{
					accept = true;
				}
			}//End of The Amount of Players

			//Store Players of the game.
			char gotPlayers[] = new char[players];
			char iChar;
			String insert;
			int i = 0;
			int saveI = 0;

			//Used to enter player characters
				while(i < players) {
					System.out.println("Enter the character to represent player " + (i + 1));
					insert = scn.nextLine();

					if(insert.length() > 1){
						System.out.println("Character can not exceeded length of one ");
					}

					else{
						iChar = Character.toUpperCase(insert.charAt(0));

						if(i < lowerBound-1){
							gotPlayers[i] = iChar;
						}
						else {
							//Check to see if Player is already entered
							saveI = i;
							for (int j = 0; j < i; ++j) {
								if (gotPlayers[j] == iChar) {
									System.out.println(iChar + " is already taken as a player token!");
									--i;//Make Sure it doesn't move on to the next player.
								}
							}

							//If I is any different from the previous, will add player into i.
							if(saveI == i){
								gotPlayers[i] = iChar;
							}


						}
						++i;

					}
			    }//End of Player Char While Loop


			//Need to ask for Column Rows and Winning Tokens
			    String newRow;
				String newCol;
				String winTok;
				int nRow = 0;
				int nCol = 0;
				int wTok = 0;
				//int minRCT = 3;//Min of what Row Column and Winning Token can be
				//int maxRC = 100;//Max for Row and Column
				//int maxT = 25; //Max for Token

				boolean validNum = false;

				while(validNum == false){
				  System.out.println("How many rows should be on the board?");
				  newRow = scn.nextLine();
				  nRow = Integer.parseInt(newRow);

				   if(nRow < IGameBoard.minRCT){
				 	  System.out.println("Row inputted needs to be greater then " + IGameBoard.minRCT);
				   }
				   else if(nRow > IGameBoard.maxRC){
				 	  System.out.println("Row inputted needs to be less then " + IGameBoard.maxRC);
				   }
				   else{
				 	  validNum = true;
				   }
				}
				//Doing the same for Col and for winning TOKEN.
				validNum = false;

			    while(validNum == false){
				  System.out.println("How many columns should be on the board?");
				  newCol = scn.nextLine();
				  nCol = Integer.parseInt(newCol);

				  if(nCol < IGameBoard.minRCT){
					  System.out.println("Column inputted needs to be greater then " + IGameBoard.minRCT);
				  }
				  else if(nCol > IGameBoard.maxRC){
					  System.out.println("Column inputted needs to be less then " + IGameBoard.maxRC);
				  }
				  else{
					  validNum = true;
				  }
			    }

			    validNum = false;

			    while(validNum == false){
			    	System.out.println("How many in a row to win?");
			    	winTok = scn.nextLine();
			    	wTok = Integer.parseInt(winTok);

			    	if(wTok > nRow || wTok > nCol){
			    		System.out.println("Cannot input tokens greater than number of row or columns.");
					}

					else if (wTok < IGameBoard.minRCT) {
						System.out.println("Tokens inputted needs to be greater then " + IGameBoard.minRCT);
					}

					else if(wTok > IGameBoard.maxT){
						System.out.println("Tokens inputted needs to be greater then " + IGameBoard.maxT);
					}

					else{
						validNum = true;
					}
				}

			//Asks what implementation the game will use(Fast or Memory).
			boolean implementation = false;
			String playerChoice;

			while(implementation == false) {
				System.out.println("Would you like a Fast Game (F/f) or a Memory Efficient Game(M/m)?");
				playerChoice = scn.nextLine();
				playerChoice = (playerChoice.toLowerCase());

				if(playerChoice.equals("f")){
					theBoard = new GameBoard(nRow,nCol,wTok);
					implementation = true;
				}

				else if(playerChoice.equals("m")){
					theBoard = new GameBoardMem(nRow,nCol,wTok);
					implementation = true;
				}

				else{
					implementation = false;
				}
			}

		  //This while loop is where the game takes place.
			int currentPlayer = 0;//Used for the current player in the gotPlayers array.
		  while (hasWon != true) {

		  	  current = gotPlayers[currentPlayer];
		  	  System.out.print(theBoard.toString());
			  checkValid = false;
			  c = 0;


			  //Will Check to see if input given is a valid choice.
			   while (checkValid == false){

			   	System.out.print("Player " + current + " what column do you want to place your marker in?\n");
			   	   userInput = scn.nextLine();
			   	   //The Pre Condition Should be we do not need to check for any other value than int
				   c = Integer.parseInt(userInput);

			   	  if(c > theBoard.getNumColumns()-1) {
					   System.out.println("Column cannot be greater than " + (theBoard.getNumColumns()-1));
				   }
				  else if (c < zero) {
					  System.out.print("Column cannot be less than 0\n");
				  }
				  else if (theBoard.checkIfFree(c) == false) {
					  System.out.print("Column is full\n");
				  }
				  else if(c < theBoard.getNumColumns() && theBoard.checkIfFree(c) && c >= zero) {
					  checkValid = true;
				  }
			   }

			   //Places Token in Valid Position
			  theBoard.placeToken(current, c);

			  if (theBoard.checkTie() == true){
				  System.out.print("The Game has resulted in a tie!\n");
				  hasWon = true;

			  }

			  //Switch Order

			  if (theBoard.checkForWin(c) == true) {
				  System.out.print(theBoard.toString());
				  System.out.print("Player " + current + " Won!\n");
				  hasWon = true;
			  }

			   ++currentPlayer;
			    //Decides the next players turn.
			  	//Current position of player vs the total amount of players, if Equal,will go back to the first player.
			    if(currentPlayer == players){
			    	currentPlayer = 0;
				}


		  }//End of game

		  String Choice;
		  boolean end = false;
		  System.out.print("Would you like to play again?Y/N\n");
		  Choice = scn.nextLine();
		  Choice = (Choice.toLowerCase());

		  //Will decide if the game continues, depends on valid userInput.
		  while (end == false){
		  	if(Choice.equals("y")){
		  		end = true;
			}

         	else if(Choice.equals("n")){
         		tryAgain = true;
         		end = true;
			}

         	else{
				System.out.print("Would you like to play again?Y/N\n");
				Choice = scn.nextLine();
				Choice = (Choice.toLowerCase());
			}
		  }

	  }//End of try again

	}

}

