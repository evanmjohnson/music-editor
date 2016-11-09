package cs3500.music.view;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
 * Created by Shravali on 11/8/2016.
 */
public class NotesPanel extends JPanel {
  private List<Line> lines;
  private List<Rectangle> rects;
  private int numNotes;
  private int numBeats;
  private final int RECTANGLE_WIDTH = 30;
  private final int RECTANGLE_HEIGHT = 24;

  public NotesPanel() {
    lines = new ArrayList<>();
    rects = new ArrayList<>();
  }

  public void setLines(int numBeats, int numNotes) {
    this.numBeats = numBeats;
    this.numNotes = numNotes;
    // draws vertical lines
    for(int i= 0; i<=numBeats; i+=4) {
        lines.add(new Line(i, 0, i, numNotes));
    }
    // draw horizontal lines
    for (int j = 0; j <= numNotes; j++) {
      lines.add(new Line(0, j, numBeats, j));
    }
  }

  public void setNotes(List<Integer> start, List<Integer> cont, int beat) {
    for (int i = 0; i <= numNotes; i++) {
      if (start.contains(i)) {
        rects.add(new Rectangle(beat, i, RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.black));
      }
      else if (cont.contains(i)) {
        rects.add(new Rectangle(beat, i, RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.green));
      }
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    //g2d.setColor(Color.BLACK);

    for (Line l : lines) {
      g2d.drawLine( l.x0 * 30, l.y0 * 22, l.x1 * 30, l.y1 * 22);
    }

    for (Rectangle r : rects) {
      g2d.setColor(r.color);
      g2d.fillRect(r.x * 30, r.y * 22, r.width, r.height - 3);
    }
  }


}
