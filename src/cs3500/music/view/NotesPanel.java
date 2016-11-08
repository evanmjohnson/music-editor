package cs3500.music.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
 * Created by Shravali on 11/8/2016.
 */
public class NotesPanel extends JPanel {
  private java.util.List<Line> lines;

  public NotesPanel() {
    lines = new ArrayList<Line>();
  }

  public void setLines(int numBeats, int numNotes) {
    this.lines = new ArrayList<Line>();
    // draws vertical lines
    for(int i=0; i<=numBeats; i+=4) {
      //for(int j=0; j<=numNotes; j++) {
        lines.add(new Line(i, numBeats));
      }
    }



  @Override
  protected void paintComponent(Graphics g) {
    System.out.println("draw");
    //never forget to call super.paintComponent!
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    //g2d.setColor(Color.BLACK);

    for (Line l : lines) {
      g2d.drawLine( l.start, 0, l.start, l.end + 500);
    }
  }


}
