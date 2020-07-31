import java.util.*;
import java.util.Scanner;

class Main {
  private static Board board = new Board(); //sets up the board without pieces
  private static Scanner scan = new Scanner(System.in);
  private static String turn = "w"; //white goes first
  private static boolean surrender = false;

  public static void main(String[] args) {
      System.out.println("Welcome to Jackson's Chess Game. A visual of the board will be shown below. For every move, enter the position of the piece you want to move first, then enter the position you want the piece to move to. Positions should be entered in proper Chess positions notation (ie. a1, b5, or f7).");
      setUpGame();
      boolean checkMated = false;
      boolean staleMated = false;
      while(!checkMated || !staleMated || !surrender){
        takeTurn();
        checkMated = isCheckMated(turn);
        staleMated = isStaleMated(turn);
      }
      if(surrender){
        System.out.println(getTurnNotation(turn) + " player surrenders! Game is over.");
      } else if(checkMated){
        System.out.println("CheckMate! " + getTurnNotation(turn) + " player loses");
      } else if(staleMated){
        System.out.println("Stalemate! Game is Over");
      }
  }


  //---------------------
  //---------------------
  //1st Abstraction Level
  //---------------------
  //---------------------


  public static void setUpGame(){
    welcomeMessage();
    System.out.println("    POSITION BOARD");
    System.out.println(board.toString()); //shows all positions on board
    board.setPieces("w"); //w stands for white
    board.setPieces("b"); //assigns positions to all the pieces
    //System.out.println(board.getPositions("w"));
    //System.out.println(board.getPositions("b"));
    System.out.println("        GAME BOARD");
    System.out.println(board.boardVisual());
  }

  public static void takeTurn(){
    System.out.println("It is " + getTurnNotation(turn) + "'s turn!");
    String posAt = "";
    String posTo = "";
    boolean kingChecked = true; //must be proven not checked
    while(kingChecked){
      boolean validMove = false; //must be proven valid
      while(!validMove){ //runs until valid move is made
        posAt = getPosAt();
        posTo = getPosTo();
        validMove = checkValidityOfMove(posAt,posTo); //returns true if valid move; false otherwise
        if(!validMove){
          System.out.println("Invalid move. Try Again"); //informs user
        }
      }
      conquer(posTo);
      makeMove(posAt,posTo);
      kingChecked = checkIfKingChecked(); //returns false if King is not checked
      if(kingChecked){ //if King checked, must reverse move
        System.out.println("Invalid Move. King is Checked. Try Again.");
        makeMove(posTo,posAt);
        undoConquer(posTo);
      }
    }
    finalizeMove();
    turn = getEnemyColor(turn); //returns the opposite color
  }

  public static boolean isCheckMated(String color){ //checks if last turn resulted in a check mate
    ChessPieces king = findKingPiece(color);
    String kingCol = king.getPosition().substring(0,1);
    int kingRow = Integer.parseInt(king.getPosition().substring(1));

    String currentPos = kingCol + kingRow;
    if(!isKingChecked(currentPos,color)){
      return false;
    }

    if(canAttackCheck(currentPos,color)){ //if one of your pieces can conquer the piece putting the check on the King then it is not CheckMate
      return false;
    }

    String upPos = kingCol + (kingRow + 1);
    if(!isKingChecked(upPos,color)){
      return false;
    }
    String downPos = kingCol + (kingRow - 1);
    if(!isKingChecked(downPos,color)){
      return false;
    }
    String rightPos = board.nextCol(kingCol,"up") + kingRow;
    if(!isKingChecked(rightPos,color)){
      return false;
    }
    String leftPos = board.nextCol(kingCol,"down") + kingRow;
    if(!isKingChecked(leftPos,color)){
      return false;
    }
    String upRightPos = board.nextCol(kingCol,"up") + (kingRow + 1);
    if(!isKingChecked(upRightPos,color)){
      return false;
    }
    String upLeftPos = board.nextCol(kingCol,"down") + (kingRow + 1);
    if(!isKingChecked(upLeftPos,color)){
      return false;
    }
    String downRightPos = board.nextCol(kingCol,"up") + (kingRow - 1);
    if(!isKingChecked(downRightPos,color)){
      return false;
    }
    String downLeftPos = board.nextCol(kingCol,"down") + (kingRow - 1);
    if(!isKingChecked(downLeftPos,color)){
      return false;
    }

    if(canBlockCheck(currentPos,color)){ //returns true if check can be blocked
      return false;
    }

    return true; //returns true if all possible move positions are checked and check cannot be removed

  }

  public static boolean isStaleMated(String color){ //returns true if StaleMated (King is last piece, not checked, but cannot move)
    ArrayList<ChessPieces> friendlyPieces = board.getPiecesList(color);
    if(friendlyPieces.size() != 1){ //can only be checkMate if King is last piece
      return false;
    }
    ChessPieces king = friendlyPieces.get(0);
    String kingPosition = king.getPosition();
    String kingCol = kingPosition.substring(0,1);
    int kingRow = Integer.parseInt(kingPosition.substring(1));
    if(isKingChecked(kingPosition,color)){ //is not a stalemate if King is checked
      return false;
    }

    //Check for StaleMate
    String upPos = kingCol + (kingRow + 1);
    if(!isKingChecked(upPos,color)){
      return false;
    }
    String downPos = kingCol + (kingRow - 1);
    if(!isKingChecked(downPos,color)){
      return false;
    }
    String rightPos = board.nextCol(kingCol,"up") + kingRow;
    if(!isKingChecked(rightPos,color)){
      return false;
    }
    String leftPos = board.nextCol(kingCol,"down") + kingRow;
    if(!isKingChecked(leftPos,color)){
      return false;
    }
    String upRightPos = board.nextCol(kingCol,"up") + (kingRow + 1);
    if(!isKingChecked(upRightPos,color)){
      return false;
    }
    String upLeftPos = board.nextCol(kingCol,"down") + (kingRow + 1);
    if(!isKingChecked(upLeftPos,color)){
      return false;
    }
    String downRightPos = board.nextCol(kingCol,"up") + (kingRow - 1);
    if(!isKingChecked(downRightPos,color)){
      return false;
    }
    String downLeftPos = board.nextCol(kingCol,"down") + (kingRow - 1);
    if(!isKingChecked(downLeftPos,color)){
      return false;
    }

    return true;
  }



  //---------------------
  //---------------------
  //2nd Abstraction Level
  //---------------------
  //---------------------


  public static void welcomeMessage(){
    System.out.println("\n");
    //also explain the position board (which will be below)
  }

  public static String getPosAt(){ //returns posAt, checks to make sure posAt is on the board and has holds one of your pieces
    String posAt = "";
    boolean valid = false;
    while(!valid){ //runs until valid positions are entered
      System.out.print("Select Piece: ");
      posAt = scan.next();
      if(posAt.equals("surrender")){ //this will end the game after this move is executed
        surrender = true;
        System.out.println("You have surrendered. Make a valid move to end the game.");
        System.out.println("Select Piece: ");
        posAt = scan.next();
      }
      valid = board.checkIfPosition(posAt); //returns true if String is a position on the board
      valid = board.checkIfPiece(posAt,turn); //returns true if one of your pieces is at posAt
      if(!valid){
        System.out.println("Invalid position notation. Enter position in correct notation (ie. \"a3\" or \"f7\", but do not include quotation marks).");
      }
    }
    return posAt;
  }

  public static String getPosTo(){ //returns posTo, checks to make sure posTo is a position on the board
    String posTo = "";
    boolean valid = false;
    while(!valid){ //runs until valid positions are entered
      System.out.print("Select Position to Move to: ");
      posTo = scan.next();
      valid = board.checkIfPosition(posTo); //returns true if String is a position on the board
      if(!valid){
        System.out.println("Invalid position notation. Enter position in correct notation (ie. \"a3\" or \"f7\", but do not include quotation marks).");
      }
    }
    return posTo;
  }

  public static boolean checkValidityOfMove(String posAt,String posTo){
    ChessPieces myPiece = board.getPiece(posAt,turn); //gets the object of the piece at current position
    if(myPiece.isValidMove(posTo, board)){
      return true;
    }
    return false;
  }

 public static boolean checkIfKingChecked(){ //return true if King is checked after move
      ChessPieces myPiece = findKingPiece(turn);
      String kingPosition = myPiece.getPosition();
      boolean checked = isKingChecked(kingPosition,turn);
      return checked;
  }

  public static String conquer(String posTo){ //if enemy piece is in moveTo position, adds it to outList and removes it from pieces in play list; returns the piece notation of removed piece
    String enemyColor = getEnemyColor(turn);
    String pieceNotation = board.getPieceNotation(posTo); //save piece notation to be returned later; is "none" if no enemy piece in position
    if(board.checkIfPiece(posTo,enemyColor)){ //if enemy piece is in moveTo position
      board.addToOutList(board.getPiece(posTo,enemyColor),enemyColor);
    }
    return pieceNotation; //returns none if no enemy piece removed
  }

  public static void makeMove(String posAt, String posTo){ //moves piece at posAt position to posTo
    ChessPieces myPiece = board.getPiece(posAt,turn);
    myPiece.setPosition(posTo.substring(0,1),Integer.parseInt(posTo.substring(1)));
  }

  public static void undoConquer(String position){ //adds enemy piece back to pieces in play list and removes it from the outList
    ChessPieces myPiece = new ChessPieces();
    String enemyColor = getEnemyColor(turn);
    ArrayList<ChessPieces> outList = board.getOutList(enemyColor); //out list for enemy pieces
    for(int i=0;i<outList.size();i++){
      if(outList.get(i).getPosition().equals(position)){
        myPiece = outList.get(i); //sets myPiece to the correct piece in the outList
        board.removeFromOutList(myPiece,enemyColor);
        board.addToList(myPiece,enemyColor);
      }
    }
  }

  public static void finalizeMove(){
    System.out.println("\n");
    System.out.println("Captured Enemies: " + board.getOutList(getEnemyColor(turn)));
    //System.out.println("\n");
    System.out.println("        GAME BOARD");
    System.out.println(board.boardVisual());
  }

  public static String getEnemyColor(String color){
    String enemyColor;
    if(color.equals("w")){
      enemyColor = "b";
    } else {
      enemyColor = "w";
    }
    return enemyColor;
  }

  public static boolean canAttackCheck(String kingPosition, String color){ //returns true if the check can be removed from the King by killing the piece that is putting the check on
    ArrayList<ChessPieces> checkingPieces = getCheckingPieces(kingPosition,color); //gets the pieces that are checking the King
    if(checkingPieces.size() != 1){ //if more than two pieces are checking the King, both cannot be attacked
      return false;
    }
    ArrayList<ChessPieces> friendlyPieces = board.getPiecesList(color);
    String enemyPiecePos = checkingPieces.get(0).getPosition();
    for(int i=0;i<friendlyPieces.size();i++){
      if(friendlyPieces.get(i).isValidMove(enemyPiecePos, board)){
        return true;
      }
    }
    return false;
  }

  public static boolean canBlockCheck(String kingPosition, String color){ //returns true if check can be blocked
    ArrayList<ChessPieces> checkingPieces = getCheckingPieces(kingPosition, color);
    if(checkingPieces.size()!= 1){ //if more than one checking piece, they cannot both be blocked
      return false;
    }
    ChessPieces enemyPiece = checkingPieces.get(0);
    if(enemyPiece.toString().substring(1).equals("N") || enemyPiece.toString().substring(1).equals("P")){ //if enemyPiece is a Knight or a Pawn, their check cannot be blocked
      return false;
    }
    ArrayList<String> blockPositions = getPositionsBetween(kingPosition,enemyPiece.getPosition());
    ArrayList<ChessPieces> friendlyPieces = board.getPiecesList(color); //friendly pieces list
    for(int i=0;i<blockPositions.size();i++){ //loops through every block position
      for(int j=0;j<friendlyPieces.size();j++){ //loops through all friendly pieces
        if(friendlyPieces.get(j).isValidMove(blockPositions.get(i),board)){ //if any friendly piece can move to a block position, then method returns true
          return true;
        }
      }
    }
    return false; //if no enemy pieces can move to a block position
  }


  //---------------------
  //---------------------
  //3rd Abstraction Level
  //---------------------
  //---------------------


  public static ChessPieces findKingPiece(String color){ //finds the King piece of the specified color
    ChessPieces myPiece = new ChessPieces();
    ArrayList<ChessPieces> friendlyPieces = board.getPiecesList(color); //list of friendly pieces
    for(int i=0;i<friendlyPieces.size();i++){ //loops through list
      if(friendlyPieces.get(i).toString().substring(1).equals("K")){
        myPiece = friendlyPieces.get(i); //sets myPiece to object in list that is the King
      }
    }
    return myPiece;
  }

  public static boolean isKingChecked(String position, String color){ //checks if enemy can move to given position
    if(board.checkIfPosition(position)){ //runs if position is on the board
      String enemyColor = getEnemyColor(color);
      ArrayList<ChessPieces> enemyPieces = board.getPiecesList(enemyColor); //creates array list of all enemy pieces
      for(int i=0;i<enemyPieces.size();i++){
        if(enemyPieces.get(i).isValidMove(position, board)){ //checks if position of King is a valid movTo position for any enemy pieces
          return true;
        }
      }
      return false;
    } else {
      return true; //returns true if position is not on the board, because the King cannot move there
    }
  }

  public static ArrayList<ChessPieces> getCheckingPieces(String kingPosition, String kingColor){ //gets list of pieces that are checking the King
    ArrayList<ChessPieces> checkingPieces = new ArrayList<ChessPieces>();
    ArrayList<ChessPieces> enemyPieces = board.getPiecesList(getEnemyColor(kingColor));
    for(int i=0;i<enemyPieces.size();i++){ //loops through enemy pieces
      if(enemyPieces.get(i).isValidMove(kingPosition, board)){ //any piece that has a valid move to the friendly King (is checking the King), is added to the list
        checkingPieces.add(enemyPieces.get(i));
      }
    }
    return checkingPieces;
  }

  public static ArrayList<String> getPositionsBetween(String firstPos, String secondPos){ //gets all the positions inbetween the two positions
    ArrayList<String> betweenPositions = new ArrayList<String>();
    String firstCol = firstPos.substring(0,1);
    int firstRow = Integer.parseInt(firstPos.substring(1));
    String secondCol = secondPos.substring(0,1);
    int secondRow = Integer.parseInt(secondPos.substring(1));
    String currentCol = firstCol;
    int currentRow = firstRow;
    if(firstCol.equals(secondCol)){ //above or below
      if(firstRow<secondRow){ //above
        currentRow++; //start one above firstRow
        while(currentRow != secondRow){ //once we reach the secondPosition, we stop adding positions
          betweenPositions.add(currentCol + currentRow); //add position to list
          currentRow++; //increment
        }
      }else { //below
        currentRow--;
        while(currentRow != secondRow){
          betweenPositions.add(currentCol + currentRow);
          currentRow--;
        }
      }
    } else if(firstRow == secondRow){ //left or right
      if(isCol2Less(firstCol,secondCol)){ //left
        currentCol = board.nextCol(currentCol,"down");
        while(!currentCol.equals(secondCol)){
          betweenPositions.add(currentCol + currentRow);
          currentCol = board.nextCol(currentCol,"down");
        }
      } else { //right
        currentCol = board.nextCol(currentCol,"up");
        while(!currentCol.equals(secondCol)){
          betweenPositions.add(currentCol + currentRow);
          currentCol = board.nextCol(currentCol,"up");
        }
      }
    } else if(isCol2Less(firstCol,secondCol)){ //leftUp or leftBelow
      if(firstRow<secondRow){ //leftUp
        currentCol = board.nextCol(currentCol,"down");
        currentRow++;
        while(currentRow != secondRow){ //only need to check one of the conditions
          betweenPositions.add(currentCol + currentRow);
          currentCol = board.nextCol(currentCol,"down");
          currentRow++;
        }
      } else{ //left down
        currentCol = board.nextCol(currentCol,"down");
        currentRow--;
        while(currentRow != secondRow){ //only need to check one of the conditions
          betweenPositions.add(currentCol + currentRow);
          currentCol = board.nextCol(currentCol,"down");
          currentRow--;
        }
      }
    } else { //rightUp or rightDown
      if(firstRow<secondRow){ //rightUp
        currentCol = board.nextCol(currentCol,"up");
        currentRow++;
        while(currentRow != secondRow){ //only need to check one of the conditions
          betweenPositions.add(currentCol + currentRow);
          currentCol = board.nextCol(currentCol,"up");
          currentRow++;
        }
      }else{ //rightDown
        currentCol = board.nextCol(currentCol,"up");
        currentRow--;
        while(currentRow != secondRow){ //only need to check one of the conditions
          betweenPositions.add(currentCol + currentRow);
          currentCol = board.nextCol(currentCol,"up");
          currentRow--;
        }
      }
    }
    return betweenPositions;
  }

  //---------------------
  //---------------------
  //4th Abstraction Level
  //---------------------
  //---------------------

  public static boolean isCol2Less(String col, String col2){
    ArrayList<String> below = new ArrayList<String>();
    String currentCol = board.nextCol(col,"down"); //starts one below col
    while(!currentCol.equals("outOfBounds")){ //runs until currentCol is out of Bounds
      below.add(currentCol); //adds all values below col to below list
      currentCol = board.nextCol(currentCol,"down"); //decreases col
    }

    for(int i=0;i<below.size();i++){ //loops through all columns below "col"
      if(below.get(i).equals(col2)){ //if col2 is in the below list, then col2 is below col
        return true;
      }
    }
    return false;
  }

  public static String getTurnNotation(String turn){
    if(turn.equals("w")){
      return "white";
    } else {
      return "brown";
    }
  }



}

/*To do:
-function to surrender
-interface for game
*/
