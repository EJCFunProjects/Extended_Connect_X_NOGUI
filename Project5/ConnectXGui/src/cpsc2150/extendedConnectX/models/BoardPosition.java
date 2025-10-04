package cpsc2150.extendedConnectX.models;
//Name: Ethan Coffey
/**
 * Board Position
 *
 *<p> This class will be used to keep track of an individual cell for a board </p>
 *
 * @author Ethan Coffey
 * @version 1.0
 * @invariant Row {@Code >=} 0 and Column {@Code >=} 0
 */

public class BoardPosition {
       private int Row;
       private int Column;

       /**
        *
        * Description: This Constructor takes in two integers and sets Positions for Rows and Columns
        *
        * @param r is the row being passed into and set in BoardPosition
        * @param c is the column being passed into and set in BoardPosition
        *
        * @pre
        * Row {@Code >=} 0 and Column {@Code >=} 0
        * @post
        * Row = Row and Column = Column
        */
        public BoardPosition(int r,int c){
            Row = r;
            Column = c;
        }

       /**
        *
        * Description: The purpose of the getRow() function is to return this board positions row.
        *
        * @return the Row position of the current GameBoard.
        *
        * @post
        * getRow = Row and Column = #Column and Row = #Row
        *
        */
        public int getRow() {
            return Row;
        }

       /**
        *
        * Description: The purpose of the getColumn() function is to return this board positions column.
        *
        * @return the Column position of the current GameBoard.
        *
        * @post
        * getColumn = Column and Row = #Row and Column = #Column
        *
        */
        public int getColumn(){
            return Column;
        }

        /**
         *
         * Description: The Function of toString returns a string pair of the Row and Column for BoardPosition
         *
         * @return the String pair of Row and Column for BoardPosition
         *
         * @post
         * toString will output the pair of Row and Column in BoardPosition
         * Its format is <Row>,<Column>, Ex. "3,5"
         */
        @Override
        public String toString(){
            String temp = "";

            temp += (Row + "," + Column);

            return temp;
        }

        /**
         *
         * Description: The function equals will return true if Two Board Positions contain the same Row and Column
         *              False otherwise.
         *
         * @param obj takes in any type of Object.
         *
         * @return true if Two Board Positions contain the same Row and Column
         *         false if otherwise.
         *
         * @post
         * Returns true if the param is a BoardPosition object and Row = Obj.getRow() and Column = Obj.getColumn().
         * Returns False with any other Object.
         */
        @Override
        public boolean equals(Object obj){

            if(!(obj instanceof BoardPosition)){
                return false;
            }

            BoardPosition bp = (BoardPosition) obj;

            if(this.getRow() == (bp.getRow()) && this.getColumn() == (bp.getColumn())){
                return true;
            }

            return false;
        }
}
