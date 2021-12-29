package toe.tic.tac.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Game {

  private final Board board;
  private Marker player;
  private GameState gameState;
  private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public Game() {
    board = new Board();
    player = Marker.x;
    gameState = GameState.running;
  }

  public void addListener(PropertyChangeListener listener) {
    pcs.addPropertyChangeListener(listener);
  }

  public Marker getCurrentPlayer() {
    return player;
  }

  public Board getBoard() {
    return board;
  }

  private void nextPlayer() {
    if (player == Marker.x) {
      player = Marker.o;
    } else {
      player = Marker.x;
    }
  }


  public void guess(char x, int y) {
    if (gameState != GameState.running) {
      return;
    }

    if (board.setCell(x - 'A' + 1, y, player)) {
      Marker winner = board.getWinner();
      if (winner == Marker.x) {
        gameState = GameState.winnerX;
      } else if (winner == Marker.o) {
        gameState = GameState.winnerO;
      } else if (board.boardFull()) {
        gameState = GameState.tie;
      } else {
        nextPlayer();
      }
    }
    pcs.firePropertyChange("", null, gameState);
  }
}
