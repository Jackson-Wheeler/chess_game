import java.util.ArrayList;

public class King extends ChessPieces{
  private String color;
  private String enemyColor;
  private int rowPos;
  private String colPos;
  private boolean hasMoved;
  private Board board;

  public King(String color, String col, int row){
    this.color = color;
    enemyColor = Main.getEnemyColor(color);
    rowPos = row;
    colPos = col;
    hasMoved = false;
  }

  public String getPosition(){
    return colPos + rowPos;
  }

  public String getCol(){
    return colPos;
  }

  public int getRow(){
    return rowPos;
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
    validMoves.addAll(getUpRightMoves(myBoard));
    validMoves.addAll(getUpLeftMoves(myBoard));
    validMoves.addAll(getDownRightMoves(myBoard));
    validMoves.addAll(getDownLeftMoves(myBoard));//all these from the perspective of the white player
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
    if(currentRow < 9){
      if(board.checkIfPiece(colPos + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      upMoves.add(colPos + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return upMoves;
  }

  public ArrayList<String> getDownMoves(Board myBoard){ //gets the moves for the moving the piece down the board (down row numbers)
    board = myBoard;
    ArrayList<String> downMoves = new ArrayList<String>();
    int currentRow = rowPos - 1; //will start checking valid moves one row below where piece is currently
    if(currentRow > 0){
      if(board.checkIfPiece(colPos + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      downMoves.add(colPos + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return downMoves;
  }

  public ArrayList<String> getRightMoves(Board myBoard){ //gets the moves for the moving the piece right on board (from white player's perspective)
    board = myBoard;
    ArrayList<String> rightMoves = new ArrayList<String>();
    String currentCol = board.nextCol(colPos, "up"); //will start checking valid moves one column to the right of where piece is currently
    if(!currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + rowPos, color)){
      //no position added if friendly piece is there
      } else{
      rightMoves.add(currentCol + rowPos); //if no piece or enemy piece at position it is a valid move
      }
    }
    return rightMoves;
  }

  public ArrayList<String> getLeftMoves(Board myBoard){ //gets the moves for the moving the piece left on board (from white player's perspective)
    board = myBoard;
    ArrayList<String> leftMoves = new ArrayList<String>();
    String currentCol = board.nextCol(colPos, "down"); //will start checking valid moves one column to the left of where piece is currently
    if(!currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + colPos, color)){
      //no position added if friendly piece is there
      } else{
      leftMoves.add(currentCol + colPos); //if no piece or enemy piece at position it is a valid move
      }
    }
    return leftMoves;
  }


  public ArrayList<String> getUpRightMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> upRightMoves = new ArrayList<String>();
    int currentRow = rowPos + 1;
    String currentCol = board.nextCol(colPos,"up");
    if(currentRow < 9 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      upRightMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return upRightMoves;
  }

  public ArrayList<String> getUpLeftMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> upLeftMoves = new ArrayList<String>();
    int currentRow = rowPos + 1;
    String currentCol = board.nextCol(colPos,"down");
    if(currentRow < 9 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      upLeftMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return upLeftMoves;
  }

  public ArrayList<String> getDownRightMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> downRightMoves = new ArrayList<String>();
    int currentRow = rowPos - 1; //moves up
    String currentCol = board.nextCol(colPos,"up"); //moves right
    if(currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      downRightMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return downRightMoves;
  }

  public ArrayList<String> getDownLeftMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> downLeftMoves = new ArrayList<String>();
    int currentRow = rowPos - 1; //moves up
    String currentCol = board.nextCol(colPos,"down"); //moves right
    if(currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      downLeftMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return downLeftMoves;
  }



  public String toString(){
    return color + "K";
  }

}
