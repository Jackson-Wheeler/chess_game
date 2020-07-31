import java.util.ArrayList;

public class ChessPieces{
  private String position;

  public ChessPieces(){
  }

  public void setPosition(String col, int row){
  }

  public ArrayList<String> canMoveTo(){
    ArrayList<String> chessboard = new ArrayList<String>();
    String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h"};
    for (int i = 0; i < 8; i++){
      for (int j = 1; j < 9; j++){
        String temp = letters[i] + j;
        chessboard.add(temp);
      }
    }
    return chessboard;
  }

  public String getPosition(){ //for the compiler
    return "";
  }

  public boolean isValidMove(String parameter, Board parameter2){  //for compiler
    System.out.println("in ChessPieces class");
    return false;
  }



}
