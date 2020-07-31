import java.util.ArrayList;

public class Pawn extends ChessPieces{
  private String color;
  private String enemyColor;
  private int rowPos;
  private String colPos;
  private boolean hasMoved;
  private Board board;

  public Pawn(String color, String col, int row){
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
    board = myBoard;
    ArrayList<String> validMoves = new ArrayList<String>();
    //In Front
    String inFront = inFront();
    if(!(inFront.equals("notOnBoard")) && !board.checkIfPiece(inFront,color) && !board.checkIfPiece(inFront,enemyColor)){ //Can move forward if forward position is on the board and no other piece of either color is there
      validMoves.add(inFront);
      //Two in Front
      String twoInFront = twoInFront();
      if(!hasMoved && !board.checkIfPiece(twoInFront,color) && !board.checkIfPiece(twoInFront,enemyColor)){ //if pawn hasn't moved yet it can go two spaces
        validMoves.add(twoInFront);
      }
    }
    //Right diagnol
    String diagRight = diagRight();
    if(!(diagRight.equals("notOnBoard")) && board.checkIfPiece(diagRight,enemyColor)){ //can only move diagnolly if enemy piece there
      validMoves.add(diagRight);
    }
    //Left diagnol
    String diagLeft = diagLeft();
    if(!(diagLeft.equals("notOnBoard")) && board.checkIfPiece(diagLeft,enemyColor)){ //can only move diagnolly if enemy piece there
      validMoves.add(diagLeft);
    }
    return validMoves;

  }

  //-----------------
  //-----------------
  //Position Changers
  //-----------------
  //-----------------


  public String inFront(){ //gives position in front of current position
    int row = rowPos;
    String col = colPos;
    if(color.equals("w")){
      if(row == 8){return "notOnBoard";} //new position would not be on board
      else {row++;}
    } else {
      if(row == 1){return "notOnBoard";}
      else {row--;}
    }
    return col + row;
  }

  public String twoInFront(){
    int row = rowPos;
    String col = colPos;
    if(color.equals("w")){
      if(row == 8){return "notOnBoard";} //new position would not be on board
      else {row += 2;}
    } else {
      if(row == 1){return "notOnBoard";}
      else {row -= 2;}
    }
    return col + row;
  }

  public String diagRight(){
    int row = rowPos;
    String col = colPos;
    if(color.equals("w")){
      if(row == 8 || col.equals("h")){return "notOnBoard";} //new position would not be on board
      else {
        col = board.nextCol(col,"up");
        row++;
      }
    } else {
      if(row == 1 || col.equals("a")){return "notOnBoard";}
      else {
        col = board.nextCol(col,"down");
        row--;
      }
    }
    return col + row;
  }

  public String diagLeft(){
    int row = rowPos;
    String col = colPos;
    if(color.equals("w")){
      if(row == 8 || col.equals("a")){return "notOnBoard";} //new position would not be on board
      else {
        col = board.nextCol(col,"down");
        row++;
      }
    } else {
      if(row == 1 || col.equals("h")){return "notOnBoard";}
      else {
        col = board.nextCol(col,"up");
        row--;
      }
    }
    return col + row;
  }


  //-----------------
  //-----------------
  //-----------------
  //-----------------


  public String toString(){
    return color + "P";
  }

}
