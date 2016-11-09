package cs3500.music.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

import java.util.List;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Represents the panel in the middle of the view that shows the grid of notes.
 */
public class NotesPanel extends JPanel {
  private List<Line> lines;
  private List<Rectangle> rects;
  private int numNotes;
  private final int RECTANGLE_WIDTH = 30;
  private final int RECTANGLE_HEIGHT = 24;

  public NotesPanel() {
    lines = new ArrayList<>();
    rects = new ArrayList<>();
  }

  /**
   * Sets the horizontal and vertical lines in this panel.
   *
   * @param numBeats the number of rows.
   * @param numNotes the number of columns.
   */

  public void setLines(int numBeats, int numNotes) {
    this.numNotes = numNotes;
    // draws vertical lines
    for (int i = 0; i <= numBeats; i += 4) {
      lines.add(new Line(i, 0, i, numNotes));
    }
    // draw horizontal lines
    for (int j = 0; j <= numNotes; j++) {
      lines.add(new Line(0, j, numBeats, j));
    }
  }

  /**
   * Sets the notes in this panel.
   *
   * @param start the notes that start at the beat.
   * @param cont  the notes that continue at the beat.
   * @param beat  the y position of the note.
   */
  public void setNotes(List<Integer> start, List<Integer> cont, int beat) {
    for (int i = 0; i <= numNotes; i++) {
      if (start.contains(i)) {
        rects.add(new Rectangle(beat, i, RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.black));
      } else if (cont.contains(i)) {
        rects.add(new Rectangle(beat, i, RECTANGLE_WIDTH, RECTANGLE_HEIGHT, Color.green));
      }
    }
  }


  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    for (Line l : lines) {
      g2d.drawLine(l.x0 * 30, l.y0 * 22, l.x1 * 30, l.y1 * 22);
    }

    for (Rectangle r : rects) {
      g2d.setColor(r.color);
      g2d.fillRect(r.x * 30, r.y * 22, r.width, r.height - 3);
    }
  }


}
