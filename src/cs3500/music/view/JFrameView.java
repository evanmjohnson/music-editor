package cs3500.music.view;

import cs3500.music.model.MusicViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicView {

  private int measureLength;
  private int numBeats;
  private JLabel display;

  public void show() {
    setVisible(true);
  }

  @Override
  public void draw(MusicViewModel model) {
    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    JPanel newPanel = new JPanelColumn(model.notesStartAtThisBeat(),
//        model.notesContinueAtThisBeat());
    JPanel panel = new JPanel();
    this.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    this.numBeats = model.getNumBeats();
    for(int i=0; i <= numBeats; i++) {
      panel.add(new JPanelColumn(i));
    }
    this.add(panel);
  }
}