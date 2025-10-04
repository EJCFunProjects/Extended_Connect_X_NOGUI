package cpsc2150.extendedConnectX.models;
//Name: Ethan Coffey
import cpsc2150.extendedConnectX.models.IGameBoard;

public abstract class AbsGameBoard implements IGameBoard {
    /**
     *
     * Description: The Function of toString returns a string of the entire GameBoard
     *
     * @return one string that shows the entire GameBoard
     *
     * @post
     * toString will output the string of how the entire GameBoard
     * Its format will be a board getNumRows() X getNumColumns(), each column will have a number above it (ex. 0-6)
     */
    @Override
    public String toString(){
        String printBoard = "";
        int i = 0;
        int j = 0;
        int nine = 9;
        BoardPosition print;

        for (int l = 0; l < getNumColumns(); l++) {
            if(l <= nine){
                printBoard += ("| " + l);
            }
            else {
                printBoard += ("|" + l);
            }
        }
        printBoard += "|\n";

        for(i = getNumRows()-1; i >= 0; --i){

            for(j = 0; getNumColumns() > j; ++j){
                print = new BoardPosition(i,j);
                printBoard += ("|" + whatsAtPos(print) + " ");

            }

            printBoard += ("|\n");

        }

        return printBoard;
    }
}
