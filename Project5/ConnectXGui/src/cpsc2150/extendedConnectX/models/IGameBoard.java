package cpsc2150.extendedConnectX.models;
//Name: Ethan Coffey
import cpsc2150.extendedConnectX.models.BoardPosition;
/**
 * IGameBoard
 *
 *<p> This Interface will represent all functions for the ConnectXGame</p>
 *
 * @author Ethan Coffey
 * @version 5.0
 * @invariant c {@Code>=} 0 AND c {@Code<=} getNumColumns AND pos is not outside the bounds of the Board
 * @correspondences maxRCT = 3 AND maxRC = 100 AND maxT = 25
 */
public interface IGameBoard {
     public static final int minRCT = 3;//Min of what Row Column and Winning Token can be
     public static final int maxRC = 100;//Max for Row and Column
     public static final int maxT = 25;
     public static final int BEGINNING = 0;

    /**
     *
     * Description: The Function checkIfFree will determine if a column can accept more tokens from a player
     *
     * @param c is the column being passed from GameBoard
     *
     * @return true if the column can accept more tokens
     *         false otherwise.
     * @pre
     * BEGINNING {@Code<=} c {@Code<} COLUMN
     *
     * @post
     * c = #c and Returns True if c (a column) can accept tokens
     *            Returns False otherwise
     */
    default boolean checkIfFree(int c){
        int i = getNumRows()-1;
        BoardPosition temp;
        temp = new BoardPosition(i,c);

        //Checks to see if the position is blank space, if not it will return false, otherwise true.
        if(!Character.isWhitespace(whatsAtPos(temp))){
            return false;
        }

        else{
            return true;
        }
    }

    /**
     *
     * Description: The function placeTokens places designated token into selected column
     *
     * @param p is the character token that goes in the column
     * @param c is the column where the token will be placed
     *
     * @pre
     * c {@Code >=} 0 and c{@Code <=} COLUMN and p {@Code !=} ' '
     * @post
     * p = #p and c = #c and board[][c] = p
     */
    public void placeToken(char p, int c);

    /**
     *
     * Description: This function checkForWin sees if the last token placed in c (column) results in a win
     *              this function can call other methods such as checkDiagWin,checkHorizWin,and checkVertWin
     *
     * @param c is the column where the last token was placed
     *
     * @return true if the column c resulted in the player winning the game.
     *         false otherwise
     * @pre
     * c {@Code >=}0 and c{@Code <=} COLUMN and c = [previously placed token location]
     * @post
     * c = #c and Returns True if last token adds with the other consecutive tokens equals
     * WINNINGTOKENS.
     * Returns False otherwise.
     */
     default boolean checkForWin(int c){
         int i = getNumRows()-1;
         BoardPosition temp;

         while(i >= 0) {
             temp = new BoardPosition(i, c);

             if ((whatsAtPos(temp) != ' ')) {
                 //When space is found will create a Board Position one before the space
                 //This is the most recent token placed and it will check if it is a winning token
                 temp = new BoardPosition(i, c);

                 char p = whatsAtPos(temp);

                 if (checkHorizWin(temp, p) == true) {
                     return true;
                 }
                 if (checkVertWin(temp, p) == true) {
                     return true;
                 }
                 if (checkDiagWin(temp, p) == true) {
                     return true;
                 }
                 return false;
             } else {
                 i--;//Do nothing if space is found
             }
         }

         return false;
     }

    /**
     *
     * Description: The Function checkTie will see if the game has resulted in a Tie.
     *
     * @return true if there are no free board positions at top of the board
     *         false otherwise
     * @pre c = [Does not contain a winning player/game]
     *
     * @post
     * Returns true if there are no other free position at the top of the game board.
     * Return false otherwise
     */
    default boolean checkTie(){
        int i = 0;
        int zero = 0;

        //Counting whiteSpace
        int counter = 0;

        //This is checking the top of each column to see if it is full, the counter will increment each time a
        //Column is free, if they are all full the counter will be zero and return true that there is a tie.
        for(i = 0; i < getNumColumns(); ++i){
            if(checkIfFree(i)){
                ++counter;
            }
        }

        if(counter > zero){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     *
     * Description: This function checkHorizWin sees if the last token placed at pos results in a win horizontally
     *
     * @param pos is the position where the last token was placed
     * @param p is the player who placed the token at pos
     *
     * @return true if the pos placed by player p has five matching token horizontally, winning the game
     *         false otherwise
     * @pre
     * BoardPosition pos is the last position of the board placed and p {@Code !=}' '
     * @post
     * pos = #pos and p = #p and Returns True if there are five tokens placed horizontally
     *                           Returns False otherwise
     */
    default boolean checkHorizWin(BoardPosition pos, char p){
    //This counter is used to loop through each position, while saveC will used in the actual position.
        int edge = 0;
        int right = 0;
        int left = 0;
        int horizToken = 0;
        int saveC = pos.getColumn();
        int saveR = pos.getRow();
        BoardPosition temp;

        //get amount of spaces to right of horizontal pos.
        while(saveC < getNumColumns()){
            ++right;
            ++saveC;
        }

        saveC = pos.getColumn();


        //get amount of spaces to left of horizontal pos.
        while(saveC >= edge){
            ++left;
            --saveC;
        }

        //Is the amount of spaces to the right equal five or more.
        if((right+left) >= getNumToWin()){
            saveC = 0;

            while(saveC < getNumColumns()){
                //Need to change position on board each time!
                temp = new BoardPosition(saveR,saveC);

                if(isPlayerAtPos(temp,p)){
                    horizToken += 1;
                    ++saveC;

                    if(horizToken >= getNumToWin()){
                        return true;
                    }
                }

                else{
                    horizToken = 0;
                   ++saveC;
                }

            }
        }


        return false;
    }

    /**
     *
     * Description: This function checkVertWin sees if the last token placed at pos results in a win vertically
     *
     * @param pos is the position where the last token was placed
     * @param p is the player who placed the token at pos
     *
     * @return true if the last pos placed by player p has five matching token vertically, winning the game
     *         false otherwise
     * @pre
     * BoardPosition pos is the last position of the board placed and p {@Code !=}' '
     * @post
     * pos = #pos and p = #p Returns True if there are five tokens placed horizontally
     *                       Returns False otherwise
     */
    default boolean checkVertWin(BoardPosition pos, char p){

        int edge = 0;
        int up = 0;
        int down = 0;
        int vertToken = 0;
        int saveC = pos.getColumn();
        int saveR = pos.getRow();
        BoardPosition temp;

        //Vertical checking how many rows down there is
        while(saveR < getNumRows()){
            ++down;
            ++saveR;
        }

        saveR = pos.getRow();
        //Vertical checking how many rows up there is
        while(saveR >= edge){
            ++up;
            --saveR;
        }

        //Is the amount of spaces down equal to getNumToWin() or more.
        if(down >= getNumToWin()){
            saveR = pos.getRow();

            while(saveR < getNumRows()){
                temp = new BoardPosition(saveR,saveC);

                if(isPlayerAtPos(temp,p) == true){
                    vertToken += 1;
                    ++saveR;

                    if(vertToken >= getNumToWin()){
                        return true;
                    }
                }

                else{
                    vertToken = 0;
                    ++saveR;
                }
            }
        }

        //Is the amount of spaces up equal to getNumToWin() or more.
        if(up >= getNumToWin()){
            saveR = pos.getRow();
            vertToken = 0;

            while(saveR >= edge){
                temp = new BoardPosition(saveR,saveC);

                if(isPlayerAtPos(temp,p) == true) {
                    vertToken += 1;
                    --saveR;

                    if(vertToken >= getNumToWin()){
                        return true;
                    }
                }

                else{
                    vertToken = 0;
                    --saveR;
                }
            }
        }

        return false;
    }

    /**
     *
     * Description: This function checkDiagWin sees if the last token placed at pos results in a win
     *
     * @param pos is the position  where the last token was placed
     * @param p is the player who placed the token at pos
     *
     * @return true if the pos placed by player p has five matching token diagonally, winning the game
     *         false otherwise
     * @pre
     * BoardPosition pos is the last position of the board placed and p {@Code !=}' '
     * @post
     * pos = #pos and p = #p and Returns True if there are five tokens placed diagonally
     *                           Returns False otherwise
     */
    default boolean checkDiagWin(BoardPosition pos, char p){
        int edge = 0;
        int diagToken = 0;
        int leftDiag = 0;
        int rightDiag = 0;
        int saveR = pos.getRow();
        int saveC = pos.getColumn();
        BoardPosition temp;

        //Upper Right Diagonal
        while(saveR < getNumRows() && saveC < getNumColumns()){
            ++rightDiag;
            ++saveC;
            ++saveR;
        }

        saveR = pos.getRow();
        saveC = pos.getColumn();

        //Lower Right Diagonal
        while(saveR >= edge && saveC >= edge){
            ++rightDiag;
            --saveR;
            --saveC;
        }

        --rightDiag;// Needed to Not overCount the Diagonal

        saveR = pos.getRow();
        saveC = pos.getColumn();

        //Upper Left Diagonal
        while(saveR < getNumRows() && saveC >= edge){
            ++leftDiag;
            ++saveR;
            --saveC;
        }
        saveR = pos.getRow();
        saveC = pos.getColumn();

        //Lower left Diagonal
        while(saveR >= edge && saveC < getNumColumns()){
            ++leftDiag;
            --saveR;
            ++saveC;
        }
         --leftDiag;// Needed to Not overCount the Diagonal

          saveR = pos.getRow();
          saveC = pos.getColumn();

        //Is the amount of spaces the right Diagonal equal to getNumToWin() or more.
        if(rightDiag >= getNumToWin()){

            //Goes to the upper right of the board.
            while(saveC < getNumColumns()-1 && saveR < getNumRows()-1){
                    ++saveC;
                    ++saveR;
            }
                //Algorithm Counting the Number of Tokens in a row that are found.
                while(saveR >= edge && saveC >= edge){
                    //Checking each boardpostions token along the diagonal.
                    temp = new BoardPosition(saveR,saveC);

                    if(isPlayerAtPos(temp,p) == true){
                        diagToken += 1;
                        --saveR;
                        --saveC;

                        if(diagToken >= getNumToWin()){
                            return true;
                        }
                    }

                    //If the there is a different token than the player found, resets diagToken count.
                    else{
                        diagToken = 0;
                        --saveR;
                        --saveC;
                    }
                }


        }

        //Is the amount of spaces the Left Diagonal equal to getNumToWin() or more.
        if (leftDiag >= getNumToWin()){
            saveR = pos.getRow();
            saveC = pos.getColumn();

            //Moves Pos to the top left of the board.
            while(saveR < getNumRows()-1 && saveC > edge) {
                --saveC;
                ++saveR;
            }

            diagToken = 0;

            while(saveR >= edge && saveC < getNumColumns()){
                temp = new BoardPosition(saveR,saveC);
                if(isPlayerAtPos(temp,p) == true){
                    ++diagToken;
                    --saveR;
                    ++saveC;

                    if(diagToken >= getNumToWin()){
                        return true;
                    }
                }

                else{
                    diagToken = 0;
                    ++saveC;
                    --saveR;
                }
            }



        }

        return false;
    }

    /**
     *
     * Description: This function whatsAtPos sees whats the current position in GameBoard
     *
     * @param pos is the position where GameBoard is currently
     *
     * @return char of the position at the current GameBoard
     *         if no marker returns blank space char
     * @pre
     * board.getRow {@Code >=} 0 and board.getColumn{@Code >=} 0 and
     * board.getRow {@Code <= } ROW and board.getColumn {@Code <=} COLUMN
     * @post
     * pos = #pos and board.getRow = #board.getRow and board.getColumn = #board.getColumn
     * and char = [is returned from pos]
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     *
     * Description: This function isPlayerAtPos returns true if player is a indicated position
     *
     * @param pos is the position where GameBoard is currently
     * @param player is the is the current user
     *
     * @return true if player is found pos in Board Position
     *         Returns false otherwise
     * @pre
     * board.getRow {@Code >=} 0 and board.getColumn {@Code >=} 0 and
     * board.getRow {@Code <= } ROW and board.getColumn {@Code <=} COLUMN
     * @post
     * pos = #pos and player = #player and board.getRow = #board.getRow and board.getColumn = #board.getColumn
     * #board = board and board = [returns true if player is at the position given]
     */
    default boolean isPlayerAtPos(BoardPosition pos, char player) {
       int i = pos.getRow();
       int j = pos.getColumn();

       BoardPosition temp;
       temp = new BoardPosition(i,j);

       //Sees from the position given if the player is at it.
       if(whatsAtPos(temp) == player){
           return true;
       }

       return false;

    }


    /**
     *
     * Description: The purpose of the getNumRows() is to return the number of ROW's for GameBoard
     *
     * @return
     * returns the number of ROWS to GameBoard
     * @pre
     * None
     * @post
     * getNumRows = ROWS and COLUMN = #COlUMN and ROW = #ROW and WINNINGTOKENS = #WINNINGTOKENS
     */
    public int getNumRows();

    /**
     *
     * Description: The purpose of the getNumColumns() is to return the number of COLUMN's for GameBoard
     *
     * @return
     * returns the number of COLUMNS to GameBoard
     * @pre
     * None
     * @post
     * getNumColumns = COLUMN and COLUMN = #COlUMN and ROW = #ROW and WINNINGTOKENS = #WINNINGTOKENS
     */
    public int getNumColumns();

    /**
     *
     * Description: The purpose of the getNumToWin() is to return the number of WINNINGTOKENS for GameBoard
     *
     * @return
     * returns the number of WINNINGTOKENS to GameBoard
     * @pre
     * None
     * @post
     * getNumToWin = WINNINGTOKENS and COLUMN = #COlUMN and ROW = #ROW and WINNINGTOKENS = #WINNINGTOKENS
     */
    public int getNumToWin();

}
