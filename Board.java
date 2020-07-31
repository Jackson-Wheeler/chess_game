import java.util.*;

public class Board{
  private String[][] board = new String[8][8];
  private String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
  private int[] rows = {8, 7, 6, 5, 4, 3, 2, 1};
  private ArrayList<ChessPieces> white = new ArrayList<ChessPieces>();
  private ArrayList<ChessPieces> brown = new ArrayList<ChessPieces>();
  private ArrayList<ChessPieces> whiteOut = new ArrayList<ChessPieces>();
  private ArrayList<ChessPieces> brownOut = new ArrayList<ChessPieces>();


  public Board(){ //sets up the board at beginning of game
    for (int row = 0; row < 8; row++){
      for (int col =  0; col < 8; col++){
        board[row][col] = columns[col] + rows[row]; //applies correct numbering system to chessboard
      }
    }
  }

  public String nextCol(String currentCol, String direction){ //returns next column (up or down the list)
    if(direction.equals("up")){
      for(int i = 0; i < columns.length-1; i++){
        if(columns[i].equals(currentCol)){
          return columns[i+1];
        }
      }
      return "outOfBounds"; //if currentCol = "h" and request up
    } else{
      for(int i = 1; i < columns.length; i++){
        if(columns[i].equals(currentCol)){
          return columns[i-1];
        }
      }
      return "outOfBounds"; //if currentCol = "a" and request down
    }
  }


  //---------------------
  //---------------------
  //-Pieces in Play List-
  //---------------------
  //---------------------


  public String getPositions(String color){
    String positions = "";
    if(color.equals("w")){
      for(int i = 0; i < white.size(); i++){
        positions += white.get(i) + ": " + white.get(i).getPosition() + ", ";
      }
    } else {
      for(int i = 0; i < brown.size(); i++){
        positions += brown.get(i) + ": " + brown.get(i).getPosition() + ", ";
      }
    }
    return positions;
  }


  public void addToList(ChessPieces piece, String color){ //adds piece to out list
    if(color.equals("w")){
      white.add(piece);
    } else{
      brown.add(piece);
    }
  }


  public void removeFromList(ChessPieces piece, String color){
    String position = piece.getPosition();
    if(color.equals("w")){
      for(int i = 0; i < white.size(); i++){
        if(white.get(i).getPosition().equals(position)){
          white.remove(i);
        }
      }
    } else{
      for(int i = 0; i < brown.size(); i++){
        if(brown.get(i).getPosition().equals(position)){
          brown.remove(i);
        }
      }
    }
  }


  public ArrayList<ChessPieces> getPiecesList(String color){
    if(color.equals("w")){
      return white;
    } else{
      return brown;
    }
  }


  //---------------------
  //---------------------
  //Pieces outOfPlay List
  //---------------------
  //---------------------


  public ArrayList<ChessPieces> getOutList(String color){
    if(color.equals("w")){
      return whiteOut;
    } else{
      return brownOut;
    }
  }


  public void addToOutList(ChessPieces piece, String color){ //adds piece to out list
    if(color.equals("w")){
      whiteOut.add(piece);
      removeFromList(piece,color);
    } else{
      brownOut.add(piece);
      removeFromList(piece,color);
    }
  }


  public void removeFromOutList(ChessPieces piece, String color){
    String position = piece.getPosition();
    if(color.equals("w")){
      for(int i = 0; i < whiteOut.size(); i++){
        if(whiteOut.get(i).getPosition().equals(position)){
          whiteOut.remove(i);
        }
      }
    } else{
      for(int i = 0; i < brownOut.size(); i++){
        if(brownOut.get(i).getPosition().equals(position)){
          brownOut.remove(i);
        }
      }
    }
  }




  //---------------------
  //---------------------
  //-----SET UP----------
  //---------------------
  //---------------------


  public void setPieces(String color){ //sets pieces in game start positions
    if(color.equals("w")){ //initialize white pieces and brown pieces differently because they have different positions
      //initial set up for white pieces
      ArrayList<ChessPieces> whiteSetUp = new ArrayList<ChessPieces>(Arrays.asList(new Pawn("w","a",2), new Pawn("w","b",2),  new Pawn("w","c",2), new Pawn("w","d",2), new Pawn("w","e",2), new Pawn("w","f",2), new Pawn("w","g",2), new Pawn("w","h",2), new Rook("w","a",1), new Knight("w","b",1), new Bishop("w","c",1), new King("w","d",1), new Queen("w","e",1), new Bishop("w","f",1), new Knight("w","g",1), new Rook("w","h",1))); //The setup array does NOT change as the game progresses
      white.clear(); //for if game is reseting
      white = whiteSetUp; //puts values into new array that will change as game progresses

    } else{ //for "brown" parameter
      //initial set up for brown pieces
      ArrayList<ChessPieces> brownSetUp = new ArrayList<ChessPieces>(Arrays.asList(new Pawn("b","a",7), new Pawn("b","b",7), new Pawn("b","c",7), new Pawn("b","d",7), new Pawn("b","e",7), new Pawn("b","f",7), new Pawn("b","g",7), new Pawn("b","h",7), new Rook("b","a",8), new Knight("b","b",8), new Bishop("b","c",8), new King("b","d",8), new Queen("b","e",8), new Bishop("b","f",8), new Knight("b","g",8), new Rook("b","h",8))); //The setup array does NOT change as the game progresses
      brown.clear(); //for if game is reseting
      brown = brownSetUp; //puts values into new array that will change as game progresses
    }
  }


  //-----------------------
  //-----------------------
  //-----Board Methods-----
  //-----------------------
  //-----------------------


  public ChessPieces getPiece(String position, String turn){ //gets the piece object at given position
    if(turn.equals("w")){
      int lengthWhite = white.size(); //improved efficiency of code
      for(int i = 0; i < lengthWhite; i++){
        if(white.get(i).getPosition().equals(position)){
          return white.get(i);
        }
      }
    } else {
      int lengthBrown = brown.size(); //improved efficiency of code
      for(int i = 0; i < lengthBrown; i++){
        if(brown.get(i).getPosition().equals(position)){
          return brown.get(i);
        }
      }
    }
    return brown.get(20); //back-up return statement
  }

  public String getPieceNotation(String position){ //gets piece notation of piece at given position. If there is no piece there it returns "none";
    int lengthWhite = white.size(); //to make code more efficient
    for(int i = 0; i < lengthWhite; i++){ //loops through all white pieces to see if any are at the given position
      if(white.get(i).getPosition().equals(position)){
        return white.get(i).toString(); //returns color and what type of piece it is
      }
    }
    int lengthBrown = brown.size();
    for(int i = 0; i < lengthBrown; i++){ //now for the brown pieces
      if(brown.get(i).getPosition().equals(position)){
        return brown.get(i).toString();
      }
    }
    return "none";
  }

  public String boardVisual(){ //returns a visual of the board with pieces
    String boardString = "";
    boardString += "  "; //for correct spacing for top row of column letters
    for(int i=0; i<columns.length; i++){
      boardString += columns[i] + "  "; //Adds the letter of each column on top of the game board
    }
    boardString += "\n";
    for (int row = 0; row < 8; row++){
      boardString += rows[row] + " "; //Adds the number of each row along the left side of the game board
      for (int col =  0; col < 8; col++){
        String piece = getPieceNotation(board[row][col]); //color and type of piece at current position
        if(piece.equals("none")){ //if no piece at current position the position notation is printed to the board
          boardString += "[] ";
        } else { //for positions with a piece the color and type are printed
          boardString += piece + " ";
        }

      }
      boardString += rows[row]; //Adds the number of each row along the right side of the game board
      boardString += "\n";
    }
    boardString += "  "; //for correct spacing for bottom row of column letters
    for(int i=0; i<columns.length; i++){
      boardString += columns[i] + "  "; //Adds the letter of each column below the game board
    }
    boardString += "\n";
    return boardString;
  }

  public String toString(){ //shows the position notation for the board
    String boardString = "";
    for (int row = 0; row < 8; row++){
      for (int col =  0; col < 8; col++){
          boardString += board[row][col] + " ";
      }
      boardString += "\n";
    }
    boardString += "\n";
    return boardString;
  }

  public boolean checkIfPosition(String position){ //checks if given position is on the board
    for (int row = 0; row < 8; row++){
      for (int col =  0; col < 8; col++){
          if(position.equals(board[row][col])){ //position is on the board
            return true;
          }
      }

    }
    return false;
  }

  public boolean checkIfPiece(String position, String color){ //returns true if given position holds a piece of given color
    if(color.equals("w")){
      for(int i = 0; i < white.size(); i++){
        if(white.get(i).getPosition().equals(position)){
          return true;
        }
      }
      return false;
    } else {
      for(int i = 0; i < brown.size(); i++){
        if(brown.get(i).getPosition().equals(position)){
          return true;
        }
      }
      return false;
    }
  }



}
