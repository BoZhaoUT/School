package game;

import ui.Gui;
import ui.TextUi;
import ui.Ui;

import java.io.IOException;

public class Play {

  /**
   * Plays the game.
   * @param args the usual
   * @throws IOException if the input file cannot be opened
   */
  public static void main(String[] args) throws IOException {

    VacuumGame game = new VacuumGame(Constants.FILENAME);

    Ui gameUi;

    if (Constants.UI_TYPE.equals("text")) {
      gameUi = new TextUi(game);
    } else {
      gameUi = new Gui(game);
    }

    gameUi.launchGame(); 
    gameUi.displayWinner();
  }
}
