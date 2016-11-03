package cs3500.music.view;

import javax.swing.*;

import cs3500.music.model.MusicViewModel;

/**
 * Represents column panels of every beat in the visual music editor GUI.
 */
public class JPanelColumn extends JPanel {
  int beat;

  public JPanelColumn(int beat) {
    this.beat = beat;
  }

  public void draw(MusicViewModel model) {
    JPanel panel = new JPanel();
    this.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
  }
}
