import java.util.ArrayList;

public class Knight extends ChessPieces{
  private String color;
  private String enemyColor;
  private int rowPos;
  private String colPos;
  private boolean hasMoved;
  private Board board;

  public Knight(String color, String col, int row){
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
    validMoves.addAll(getDownLeftMoves(myBoard));
    validMoves.addAll(getRightUpMoves(myBoard));
    validMoves.addAll(getRightDownMoves(myBoard));
    validMoves.addAll(getLeftUpMoves(myBoard));
    validMoves.addAll(getLeftDownMoves(myBoard));
    return validMoves;
  }

  //--------------------------
  //--------------------------
  //2nd level valid move lists
  //--------------------------
  //--------------------------

  public ArrayList<String> getUpRightMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> upRightMoves = new ArrayList<String>();
    int currentRow = rowPos + 2;
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

  public ArrayList<String> getUpLeftMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> upLeftMoves = new ArrayList<String>();
    int currentRow = rowPos + 2;
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

  public ArrayList<String> getDownRightMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> downRightMoves = new ArrayList<String>();
    int currentRow = rowPos - 2;
    String currentCol = board.nextCol(colPos,"up");
    if(currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      downRightMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return downRightMoves;
  }

  public ArrayList<String> getDownLeftMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> downLeftMoves = new ArrayList<String>();
    int currentRow = rowPos - 2;
    String currentCol = board.nextCol(colPos,"down");
    if(currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      downLeftMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return downLeftMoves;
  }

  public ArrayList<String> getRightUpMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> rightUpMoves = new ArrayList<String>();
    int currentRow = rowPos + 1;
    String currentCol = board.nextCol(board.nextCol(colPos,"up"),"up"); //moves column up twice
    if(currentRow < 9 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      rightUpMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return rightUpMoves;
  }

  public ArrayList<String> getRightDownMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> rightDownMoves = new ArrayList<String>();
    int currentRow = rowPos - 1;
    String currentCol = board.nextCol(board.nextCol(colPos,"up"),"up"); //moves column up twice
    if(currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      rightDownMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return rightDownMoves;
  }

  public ArrayList<String> getLeftUpMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> leftUpMoves = new ArrayList<String>();
    int currentRow = rowPos + 1;
    String currentCol = board.nextCol(board.nextCol(colPos,"down"),"down"); //moves column up twice
    if(currentRow < 9 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      leftUpMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return leftUpMoves;
  }

  public ArrayList<String> getLeftDownMoves(Board myBoard){
    board = myBoard;
    ArrayList<String> leftDownMoves = new ArrayList<String>();
    int currentRow = rowPos - 1;
    String currentCol = board.nextCol(board.nextCol(colPos,"down"),"down"); //moves column up twice
    if(currentRow > 0 && !currentCol.equals("outOfBounds")){
      if(board.checkIfPiece(currentCol + currentRow, color)){
      //no position added if friendly piece is there
      } else{
      leftDownMoves.add(currentCol + currentRow); //if no piece or enemy piece at position it is a valid move
      }
    }
    return leftDownMoves;
  }




  public String toString(){
    return color + "N";
  }
}
