import java.util.ArrayList;

public class Rook extends ChessPieces{
  private String color;
  private String enemyColor;
  private int rowPos;
  private String colPos;
  private boolean hasMoved;
  private Board board;

  public Rook(String color, String col, int row){
    this.color = color;
    enemyColor = Main.getEnemyColor(color);
    rowPos = row;
    colPos = col;
    hasMoved = false;
  }

  public String getPosition(){
    return colPos + rowPos;
  }

  public void setPosition(String col, int row){
    rowPos = row;
    colPos = col;
  }

  public boolean isValidMove(String posTo, Board myBoard){ //checks array of valid moves to see if given move is valid
    ArrayList<String> validMoves = getValidMoves(myBoard);
    int arrayLength = validMoves.size();
    for(int i = 0; i < arrayLength; i++){
      if(validMoves.get(i).equals(posTo)){
        return true;
      }
    }
    return false;
  }

  public ArrayList<String> getValidMoves(Board myBoard){
    ArrayList<String> validMoves = new ArrayList<String>();
    validMoves.addAll(getUpMoves(myBoard));
    validMoves.addAll(getDownMoves(myBoard));
    validMoves.addAll(getRightMoves(myBoard));
    validMoves.addAll(getLeftMoves(myBoard));
    return validMoves;
  }

  //--------------------------
  //--------------------------
  //2nd level valid move lists
  //--------------------------
  //--------------------------

  public ArrayList<String> getUpMoves(Board myBoard){ //gets the moves for the moving the piece up the board (up row numbers). Therefore this will work for either color (for brown in will be towards the player)
    board = myBoard;
    ArrayList<String> upMoves = new ArrayList<String>();
    int currentRow = rowPos + 1; //will start checking valid moves one row above where piece is currently
    boolean continueOn = true;
    while(continueOn && currentRow < 9){
      if(board.checkIfPiece(colPos + currentRow, enemyColor)){ //if enemy piece is at location
        upMoves.add(colPos + currentRow); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(colPos + currentRow, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        upMoves.add(colPos + currentRow); //if no piece at position it is a valid move position
        currentRow++;
      }
    }
    return upMoves;
  }

  public ArrayList<String> getDownMoves(Board myBoard){ //gets the moves for the moving the piece down the board (down row numbers)
    board = myBoard;
    ArrayList<String> downMoves = new ArrayList<String>();
    int currentRow = rowPos - 1; //will start checking valid moves one row below where piece is currently
    boolean continueOn = true;
    while(continueOn && currentRow > 0){
      if(board.checkIfPiece(colPos + currentRow, enemyColor)){ //if enemy piece is at location
        downMoves.add(colPos + currentRow); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(colPos + currentRow, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        downMoves.add(colPos + currentRow); //if no piece at position it is a valid move position
        currentRow--;
      }
    }
    return downMoves;
  }

  public ArrayList<String> getRightMoves(Board myBoard){ //gets the moves for the moving the piece right on board (from white player's perspective)
    board = myBoard;
    ArrayList<String> rightMoves = new ArrayList<String>();
    String currentCol = board.nextCol(colPos, "up"); //will start checking valid moves one column to the right of where piece is currently
    boolean continueOn = true;
    while(continueOn && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + rowPos, enemyColor)){ //if enemy piece is at location
        rightMoves.add(currentCol + rowPos); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(currentCol + rowPos, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        rightMoves.add(currentCol + rowPos); //if no piece at position it is a valid move position
        currentCol = board.nextCol(currentCol, "up");
      }
    }
    return rightMoves;
  }

  public ArrayList<String> getLeftMoves(Board myBoard){ //gets the moves for the moving the piece left on board (from white player's perspective)
    board = myBoard;
    ArrayList<String> leftMoves = new ArrayList<String>();
    String currentCol = board.nextCol(colPos, "down"); //will start checking valid moves one column to the left of where piece is currently
    boolean continueOn = true;
    while(continueOn && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + rowPos, enemyColor)){ //if enemy piece is at location
        leftMoves.add(currentCol + rowPos); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(currentCol + rowPos, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        leftMoves.add(currentCol + rowPos); //if no piece at position it is a valid move position
        currentCol = board.nextCol(currentCol, "down");
      }
    }
    return leftMoves;
  }


  public String toString(){
    return color + "R";
  }
}
