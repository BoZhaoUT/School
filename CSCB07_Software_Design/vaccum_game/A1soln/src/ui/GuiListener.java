package ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/** A listener for the GUI window. */
public class GuiListener extends KeyAdapter {

  /** A GUI for the vacuum game. */
  private Gui window;

  /**
   * Creates a listener for the GUI window.
   * @param window the GUI to listen to
   */
  public GuiListener(Gui window) {
    this.window = window;
  }

  @Override
  public void keyPressed(KeyEvent event) {
  }

  @Override
  public void keyTyped(KeyEvent event) {
    char nextMove = event.getKeyChar();
    this.window.getGame().move(nextMove);
    this.window.updateLabels();
  }

  @Override
  public void keyReleased(KeyEvent event) {
  } 
}
