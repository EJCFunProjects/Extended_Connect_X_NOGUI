package cpsc2150.extendedConnectX.models;
/**
 * GameBoard
 *
 *<p> This class will represent the game board itself (Fast Implementation) </p>
 *
 * @author Ethan Coffey
 * @version 3.0
 * @invariant Pieces in board always falls to the lowest position in the column of boardPosition (with no gaps)
 *            board[0][0] = bottom left of board and board[getNumRows()-1][getNumColumns()-1] = top right of board
 *            and c {@Code>=} 0 and c {@Code<} COLUMNS and ROW {@Code<=} maxRC and ROW {@Code>=} minRCT
 *  *            and COLUMN {@Code<=} maxRC and COLUMN {@Code>=} minRCT and WINNINGTOKENS {@Code>=} minRCT and
 *  *            WINNINGTOKENS {@Code<=} maxT
 *
 * @correspondences getNumToWin = WINNINGTOKENS and getNumRows = ROW and getNumColumns = COLUMN
 * and self = [board[ROW-1][COLUMN-1]]
 */
//Look at check win, reference other wins, add to constructor,
public class GameBoard extends AbsGameBoard implements IGameBoard {

    private char board[][];
    private int ROW;
    private int COLUMN;
    private int WINNINGTOKENS;

    /**
     *
     * Description: This is a Default Constructor that initializes variable for GameBoard.
     *
     * @param nRow is the number of rows chosen for the board by the players.
     * @param nCol is the number of columns chosen for the board by the players.
     * @param wTok is the number of tokens needed to win chosen by the players.
     *
     * @post
     * board[ROW][COLUMN] = board[ROW][COLUMN] and board{ROW][COLUMN] = ' ' and ROW = ROW and COLUMN = COLUMN
     * and WINNINGTOKENS = WINNINGTOKENS
     */
    public GameBoard(int nRow, int nCol, int wTok)
    {
        ROW = nRow;
        COLUMN = nCol;
        WINNINGTOKENS = wTok;

        board = new char[getNumRows()][getNumColumns()];
        int i = 0;
        int j = 0;

        for(i = 0; i < getNumRows(); ++i ){

            for(j = 0; j < getNumColumns(); ++j){
                board[i][j] = ' ';
            }

        }

    }

    public void placeToken(char p, int c)
    {
        int zero = 0;
        int saveRow = 0;


        //Look at this issue for other places.
        while((board[saveRow][c]) != ' '){
            if(saveRow + 1 == getNumRows()){
                break;
            }
            else {
                ++saveRow;
            }
        }

        if(checkIfFree(c) && c < COLUMN && c >= zero){
            board[saveRow][c] = p;
        }

        //places the character p in column c. The token will be placed in
        //the lowest available row in column c.
    }


    public char whatsAtPos(BoardPosition pos)
    {
        int i = pos.getRow();
        int j = pos.getColumn();
        char temp;

        temp = board[i][j];

        return temp;
        //returns what is in the GameBoard at position pos
        //If no marker is there, it returns a blank space char.
    }

    public int getNumRows(){
        return ROW;
    }

    public int getNumColumns(){
        return COLUMN;
    }

    public int getNumToWin(){
        return WINNINGTOKENS;
    }


}
