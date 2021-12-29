package toe.tic.tac.model;

public class Board {


  private Marker[][] board;

  Board() {
    board = new Marker[3][3];
    for(int i=0;i<3;i++){
      for(int j=0;j<3;j++){
        board[i][j] = Marker.e;
      }
    }
  }



  /**
   * Return the value of the cell.
   *
   * @param x x-coordinate (1-3)
   * @param y y-coordinate (1-3)
   * @return the cell value; returns empty, if coordinates are not in bounds
   */
  public Marker getCell(int x, int y) {
    if (x < 1 || x > 3 || y < 1 || y > 3) {
      return Marker.e;
    }
    return board[x - 1][y - 1];
  }

  /**
   * Sets cell, if the cell is empty and the coordinates are in bounds.
   *
   * @param x x-coordinate (1-3)
   * @param y y-coordinate (1-3)
   * @param m the Marker
   * @return true, if insertion was possible, else false
   */
  public boolean setCell(int x, int y, Marker m) {
    if (x < 1 || x > 3 || y < 1 || y > 3) {
      return false;
    }
    if (board[x - 1][y - 1] != Marker.e) {
      return false;
    }
    board[x - 1][y - 1] = m;
    return true;
  }

  /**
   * Checks if a player has won and returns its marker.
   *
   * @return The Marker of the winner, if one has won. Returns Marker.empty, when noone has won;
   */
  public Marker getWinner() {
    //Rows and columns:
    for (int i = 0; i < 3; i++) {
      if (board[i][0] != Marker.e && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
        return board[i][0];
      }
      if (board[0][i] != Marker.e && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
        return board[i][0];
      }
    }

    //Diagonals
    if (board[0][0] != Marker.e && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
      return board[0][0];
    }
    if (board[2][0] != Marker.e && board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
      return board[2][0];
    }
    return Marker.e;
  }

  /**
   * Checks if the board is full.
   *
   * @return false, if at least one cell is empty and true, if all cells are not empty
   */
  public boolean boardFull(){
   for (int i=0;i<3;i++) {
      for (int j=0;j<3;j++) {
        if(board[i][j] == Marker.e){
          return false;
        }
      }
    }
   return true;
  }
}
