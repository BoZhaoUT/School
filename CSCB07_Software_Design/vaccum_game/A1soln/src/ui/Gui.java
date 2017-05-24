package ui;

import game.VacuumGame;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;


/** A simple GUI for the game. */
@SuppressWarnings("serial")
public class Gui extends JFrame implements Ui {

  private VacuumGame game;
  private JLabel[][] tiles;

  /** Initializes a GUI for the given MazeGame.
   * @param game The MazeGame of this GUI 
   */
  public Gui(VacuumGame game) {
    this.game = game;
  }

  /** Returns the MazeGame of this GUI.
   * @return the MazeGame of this GUI
   */
  public VacuumGame getGame() {
    return game;
  }

  @Override
  public void launchGame() {
    int numRows = game.getNumRows();
    int numCols = game.getNumColumns();

    Container container = this.getContentPane();
    container.setLayout(new GridLayout(numRows, numCols));
    tiles = new JLabel[numRows][numCols];

    GuiListener listener = new GuiListener(this);
    addKeyListener(listener);

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        JLabel label = new JLabel();
        label.setText("" + game.getSprite(i, j).toString());                
        label.setFont(new Font(null, Font.BOLD, 18));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        container.add(label);
        tiles[i][j] = label;
      }
    }
    setVisible(true);
    pack();
  }

  @Override
  public void displayWinner() {

    if (!game.gameOver()) {
      return;
    }

    int won = game.getWinner();
    String message;

    if (won == 0) {
      message = "It's a tie!";
    } else if (won == 1) {
      message = "Congratulations Player 1! You won the game with a score of "
          + game.getVacuumOne().getScore() + ".";
    } else { 
      message = "Congratulations Player 2! You won the game with a score of "
          + game.getVacuumTwo().getScore() + ".";
    }
    JOptionPane.showMessageDialog(null, message);
    setVisible(false);
  }

  /** Update the grid display. */
  public void updateLabels() {
    int numRows = game.getNumRows();
    int numCols = game.getNumColumns();

    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numCols; j++) {
        this.tiles[i][j].setText(game.getSprite(i, j).toString());
      }
    }
    displayWinner();
  }
}
