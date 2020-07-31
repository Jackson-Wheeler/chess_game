import java.util.ArrayList;

public class Bishop extends ChessPieces{
  private String color;
  private String enemyColor;
  private int rowPos;
  private String colPos;
  private boolean hasMoved;
  private Board board;

  public Bishop(String color, String col, int row){
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

  public ArrayList<String> getUpRightMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> upRightMoves = new ArrayList<String>();
    int currentRow = rowPos + 1;
    String currentCol = board.nextCol(colPos,"up");
    boolean continueOn = true;
    while(continueOn && currentRow < 9 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, enemyColor)){ //if enemy piece is at location
        upRightMoves.add(currentCol + currentRow); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(currentCol + currentRow, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        upRightMoves.add(currentCol + currentRow); //if no piece at position it is a valid move position
        currentRow++;
        currentCol = board.nextCol(currentCol,"up");

      }
    }
    return upRightMoves;
  }

  public ArrayList<String> getUpLeftMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> upLeftMoves = new ArrayList<String>();
    int currentRow = rowPos + 1;
    String currentCol = board.nextCol(colPos,"down");
    boolean continueOn = true;
    while(continueOn && currentRow < 9 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, enemyColor)){ //if enemy piece is at location
        upLeftMoves.add(currentCol + currentRow); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(currentCol + currentRow, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        upLeftMoves.add(currentCol + currentRow); //if no piece at position it is a valid move position
        currentRow++;
        currentCol = board.nextCol(currentCol,"down");
      }
    }
    return upLeftMoves;
  }

  public ArrayList<String> getDownRightMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> downRightMoves = new ArrayList<String>();
    int currentRow = rowPos - 1; //moves up
    String currentCol = board.nextCol(colPos,"up"); //moves right
    boolean continueOn = true;
    while(continueOn && currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, enemyColor)){ //if enemy piece is at location
        downRightMoves.add(currentCol + currentRow); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(currentCol + currentRow, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        downRightMoves.add(currentCol + currentRow); //if no piece at position it is a valid move position
        currentRow--;
        currentCol = board.nextCol(currentCol,"up");
      }
    }
    return downRightMoves;
  }

  public ArrayList<String> getDownLeftMoves(Board myBoard){ //from white player's perspective
    board = myBoard;
    ArrayList<String> downLeftMoves = new ArrayList<String>();
    int currentRow = rowPos - 1; //moves up
    String currentCol = board.nextCol(colPos,"down"); //moves right
    boolean continueOn = true;
    while(continueOn && currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, enemyColor)){ //if enemy piece is at location
        downLeftMoves.add(currentCol + currentRow); //adds position to valid moves
        continueOn = false; //cannot move past an enemy piece
      } else if(board.checkIfPiece(currentCol + currentRow, color)){
        continueOn = false; //cannot move to or past a friendly piece
      } else{
        downLeftMoves.add(currentCol + currentRow); //if no piece at position it is a valid move position
        currentRow--;
        currentCol = board.nextCol(currentCol,"down");
      }
    }
    return downLeftMoves;
  }




  public String toString(){
    return color + "B";
  }

}
