package cpsc2150.extendedConnectX.models;

import java.security.PublicKey;
import java.util.*;

/**
 * GameBoardMem
 *
 * <p> This class will represent the game board itself (Memory Implementation) </p>
 *
 * @author Ethan Coffey
 * @version 1.0
 * @invariant Pieces in the board are filled into the map as a token is placed(the map starts empty).
 *            Each player token(character) acts as a key to the value (list of board positions for the key) in the map.
 *            c {@Code>=} 0 and c {@Code<} COLUMN and ROW {@Code<=} maxRC and ROW {@Code>=} minRCT
 *            and COLUMN {@Code<=} maxRC and COLUMN {@Code>=} minRCT and WINNINGTOKENS {@Code>=} minRCT and
 *            WINNINGTOKENS {@Code<=} maxT and theGame = [Starts as a blank map] and
 *            theGame does not contain any duplicate values.
 *
 * @correspondences getNumToWin = WINNINGTOKENS and getNumRows = ROW and getNumColumns = COLUMN and
 *  self = the
 */

public class GameBoardMem extends AbsGameBoard implements IGameBoard{
    private Map<Character, ArrayList<BoardPosition>> theGame;
    private ArrayList<BoardPosition> gameList;

    private int ROW;
    private int COLUMN;
    private int WINNINGTOKENS;

    /**
     *
     * Description: This is a Default Constructor that initializes variable for GameBoardMem.
     *
     * @param nRow is the number of rows chosen for the board by the players.
     * @param nCol is the number of columns chosen for the board by the players.
     * @param wTok is the number of tokens needed to win chosen by the players.
     *
     * @post
     * ROW = ROW and COLUMN = COLUMN and WINNINGTOKENS = WINNINGTOKENS and theGame = theGame
     */

    public GameBoardMem(int nRow, int nCol, int wTok){
        ROW = nRow;
        COLUMN = nCol;
        WINNINGTOKENS = wTok;
        theGame = new HashMap<Character,ArrayList<BoardPosition>>();
    }

    @Override
    public void placeToken(char p, int c) {

        int zero = 0;
        int saveR = 0;
        BoardPosition temp;

        //Used to exit loop when token is pla

        //Idea, loop with a new board position until checkiffree is true, reference CheckforWin
        temp = new BoardPosition(saveR,c);

        while(whatsAtPos(temp) != ' '){

            if(saveR + 1 == getNumRows()) {
                break;
            }

            else {
               ++saveR;
            }

            temp = new BoardPosition(saveR,c);

        }
            temp = new BoardPosition(saveR,c);

                Character ch = p;
                if(!theGame.containsKey(ch)){
                    //Create a New List For this Player and add it to the map
                    gameList = new ArrayList<BoardPosition>();
                    gameList.add(temp);

                    theGame.put(ch,gameList);

                }

                else{
                    //add to the current list for that player.
                    theGame.get(ch).add(temp);

                }


        }

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        Character ch = player;
        //Use whatsatpos place token
        //boolean ans = theGame.get(ch).contains(pos);

        //Need to use player
        if(theGame.get(ch).contains(pos)){
           return true;
        }

        else {
            return false;
        }
    }

    @Override
    public char whatsAtPos(BoardPosition pos) {
        char blankSpace = ' ';

        for(Map.Entry<Character,ArrayList<BoardPosition>> m: theGame.entrySet()){
            //use m.getKey and m.getValue, see if something is at that BoardPostion passed in.
            //If there is return the key, else return blank space.

            if((m.getValue().contains(pos))){
                return m.getKey();
            }

        }

        return blankSpace;
    }



    @Override
    public int getNumRows() {
        return ROW;
    }

    @Override
    public int getNumColumns() {
        return COLUMN;
    }

    @Override
    public int getNumToWin() {
        return WINNINGTOKENS;
    }
}
