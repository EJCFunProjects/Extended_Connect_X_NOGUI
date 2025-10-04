package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your your {@link BoardPosition} class, {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4.
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class.
 *
 * @version 2.0
 */
public class ConnectXController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private ConnectXView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;
    
    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;

    /**
     * <p>
     * Boolean value used to keep track if someone one the game, or tied. Used to decied if a newGame starts.
     * </p>
     */
    private boolean winOrTie;

    /**
     * <p>
     * Keeps track of who the currentPlayer character is in the character array.
     * </p>
     */
    private int currentPlayer = 0;

    /**
     * <p>
     * Array that holds all possible players in the Game(Max Being Ten).
     * </p>
     */
    private char array[] = new char[10];

    /**
     * <p>
     * This array keeps track of the highest token placed in each column(by keeping track of the Row)
     * </p>
     */
    private int theRows[];

    /**
     * <p>
     * This creates a controller for running the Extended ConnectX game
     * </p>
     * 
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * @param np
     *      The number of players for this game.
     * 
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        this.numPlayers = np;

        winOrTie = false;
        currentPlayer = 0;


        theRows = new int[curGame.getNumColumns()];

        //Setting/Initializing the number of token found in each column to zero
        //This keeps track what the highest token is for the given column based on its row.
        for(int i = 0; i < curGame.getNumColumns(); ++i){
            theRows[i] = 0;
        }

        //Adding Player characters to array
        array[0] = 'X';
        array[1] = 0x265B;
        array[2] = 'V';
        array[3] = 'G';
        array[4] = '!';
        array[5] = '$';
        array[6] = 'J';
        array[7] = 'H';
        array[8] = '@';
        array[9] = 'P';


    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     * 
     * @param col 
     *      The column of the activated button
     * 
     * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int col) {

        //Will start a new Game if True (after a player clicks any Button)
        if(winOrTie == true){
            newGame();
        }

        //If the column is free, will add token to Board and do various checks.
        if(curGame.checkIfFree(col)){
            //Adds token to Board
            curGame.placeToken(array[currentPlayer],col);

            //Adds Token to Screen
            screen.setMarker(theRows[col],col,array[currentPlayer]);

            //If the column is not at the row limit, will increment position of row(for the column).
            if(theRows[col] < curGame.getNumRows()){
                theRows[col] += 1;
            }

            //Checks if the Current Player Won
            if(curGame.checkForWin(col) == true){
                screen.setMessage("Player " + array[currentPlayer] + " WINS! Click any Button For a New Game.");
                winOrTie = true;
            }
            //Check if the game ended in a Tie
            else if(curGame.checkTie() == true){
                screen.setMessage("The Game has now Resulted in a Tie. Click any Button For a New Game.");
                winOrTie = true;
            }

            //Changes to the next player of the Game.
            else {
                ++currentPlayer;

                if (currentPlayer > (numPlayers - 1)) {
                    currentPlayer = 0;
                }

                screen.setMessage("It is Now Player " + array[currentPlayer] + " Turn");
            }


        }
        //Prints this message if Column is full
        else{
            screen.setMessage("Column is Currently Full. Please enter Token into another Column.");
        }





    }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     * 
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}