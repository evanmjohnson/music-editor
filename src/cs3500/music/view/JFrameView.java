package cs3500.music.view;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a java Swing BoxLayout view for the music editor.
 */
public class JFrameView extends JFrame implements IMusicView {

  private int measureLength;
  private int numBeats;
  private String notes;
  private JLabel display;

  public JFrameView(int measureLength, int numBeats, String notes) {
    this.setMeasureLength(measureLength);
    setSize(500, 300);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel newPanel = new JPanel();
    this.setLayout(new BoxLayout(newPanel, BoxLayout.X_AXIS));
    this.numBeats = numBeats;
    this.notes = notes;
  }

  public void show() {
    setVisible(true);
  }

  public void setMeasureLength(int length) {
    this.measureLength = length;
  }
}