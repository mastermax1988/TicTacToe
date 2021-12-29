package toe.tic.tac.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;
import toe.tic.tac.model.Board;
import toe.tic.tac.model.Game;
import toe.tic.tac.model.GameState;

public class Shell implements PropertyChangeListener {

  private final String SHELL_OUT = "SP> ";
  private final String C_NEW_GAME = "NEWGAME";
  private final String C_QUIT = "QUIT";
  private final String TURN_HELP = "Enter your turn like this: A1 or C2";
  private Game game;

  public Shell() {
    System.out.println("Enter " + C_NEW_GAME + " at any time to start a new game or " + C_QUIT +
        " to quit.");
  }

  public void start() {
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      System.out.print(SHELL_OUT);
      String input;
      try {
        input = in.readLine().toUpperCase().trim();
      } catch (IOException e) {
        return;
      }
      switch (input) {
        case C_NEW_GAME -> {
          game = new Game();
          game.addListener(this);
          System.out.println("Game started. " + TURN_HELP);
          printBoard();
        }
        case C_QUIT -> {
          return;
        }
        default -> {
          if (game == null) {
            System.out.println("No game running!");
            continue;
          }
          Pattern pattern = Pattern.compile("[A-C][1-3]");
          if (input.length() != 2 || !pattern.matcher(input).matches()) {
            System.out.println(TURN_HELP);
            continue;
          }
          game.guess(input.charAt(0), Integer.parseInt(input.substring(1, 2)));
        }
      }
    }
  }

  @Override
  public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
    printBoard();
    if (propertyChangeEvent.getNewValue() instanceof GameState) {
      GameState gameState = (GameState) propertyChangeEvent.getNewValue();
      switch (gameState) {
        case winnerO, winnerX -> {
          System.out.println("Player " + (gameState == GameState.winnerX ? "X" : "0") + "has won!");
          game = null;
        }
        case tie -> {
          System.out.println("Board is full, no winner");
          game = null;
        }
      }
    }
  }

  private void printBoard() {
    Board board = game.getBoard();
    System.out.println("  A B C");
    for (int i = 1; i < 4; i++) {
      System.out.println(i + " " +
          board.getCell(1, i) + " " + board.getCell(2, i) + " " + board.getCell(3, i));
    }
    System.out.println("Next player: " + game.getCurrentPlayer());

  }
}
