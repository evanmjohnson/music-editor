package cs3500.music.provider;

import cs3500.music.model.Note;
import cs3500.music.model.Pitch;

import javax.swing.JPanel;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a ViewPanel.
 */
public class ViewPanel extends JPanel {
  private List<Line2D> lines;
  private List<Note> notes;
  private Note upperNote;
  private Note lowerNote;
  private int rLinePos;

  /**
   * The main constructor for the ViewPanel class; it initializes the variables {@code lines},
   * {@code notes}, {@code upperNote}, and {@code lowerNote}.
   */
  public ViewPanel() {
    super();
    lines = new ArrayList<Line2D>();
    notes = new ArrayList<Note>();
    upperNote = null;
    lowerNote = null;
    this.setBackground(Color.WHITE);
    this.rLinePos = 50;
  }

  /**
   * Makes the lines that comprise the grid.
   */
  public void setLines() {
    for (int index = 0; index < notes.size(); index++) {
      int noteVal = notes.get(index).getNoteVal();

      if (this.lowerNote == null || this.upperNote == null) {
        this.lowerNote = notes.get(index);
        this.upperNote = notes.get(index);
      }
      if (noteVal < lowerNote.getNoteVal()) {
        this.lowerNote = notes.get(index);
      }
      if (noteVal > upperNote.getNoteVal()) {
        this.upperNote = notes.get(index);
      }
    }

    int tempSum = 0;
    for (int index = 0; index < this.notes.size(); index++) {
      if (this.notes.get(index).getStartTime() + this.notes.get(index).getDuration() > tempSum) {
        tempSum = this.notes.get(index).getStartTime() + this.notes.get(index).getDuration();
      }
    }

    for (int index = 0; index < Note.distanceBetweenNotes(this.lowerNote, this.upperNote) + 2;
         index++) {
      this.lines.add(new Line2D.Double(50.0, index * 16.0 + 120.0,
              ((Math.ceil(tempSum / 4) + 1) * 100) + 50.0, index * 16.0 + 120.0));
    }

    for (int index = 0; index < (((int) (Math.ceil(tempSum / 4)) + 2) * 100); index += 100) {
      this.lines.add(new Line2D.Double(50.0 + index, 120.0, 50.0 + index,
              ((Note.distanceBetweenNotes(this.lowerNote, this.upperNote) + 1) * 16) + 120.0));
    }

  }

  /**
   * Returns a tempSum.
   */
  public int tempSum() {
    int tempSum = 0;
    for (int index = 0; index < this.notes.size(); index++) {
      if (this.notes.get(index).getStartTime() + this.notes.get(index).getDuration() > tempSum) {
        tempSum = this.notes.get(index).getStartTime() + this.notes.get(index).getDuration();
      }
    }
    return tempSum;
  }

  /**
   * Attempts to retrieve what note has been clicked on at a certain point.
   *
   * @param p the point clicked on by the mouse
   * @return the note at the point
   */
  public Note getNoteAtPoint(Point p) {
    double x = (p.getX() - 50) / 25;
    double y = (p.getY() - 21) / 16;
    Note n = null;

    for (int index = 0; index < this.notes.size(); index++) {
      if (this.notes.get(index).getStartTime() < (int) x + 1 &&
              this.notes.get(index).getStartTime() >= (int) x &&
              (Note.distanceBetweenNotes(upperNote, this.notes.get(index))) < (int) y + 1 &&
              (Note.distanceBetweenNotes(upperNote, this.notes.get(index))) >= (int) y) {
        n = this.notes.get(index);
        break;
      }
    }
    return n;
  }

  /**
   * Returns the current {@code lowerNote}.
   *
   * @return the lowermost note
   */
  public Note getLowerNote() {
    return this.lowerNote;
  }

  /**
   * Returns the current {@code upperNote}.
   *
   * @return the uppermost note
   */
  public Note getUpperNote() {
    return this.upperNote;
  }

  /**
   * Returns all of the notes.
   *
   * @return the notes as a list
   */
  public List<Note> getNotes() {
    return this.notes;
  }

  /**
   * Sets the notes to be rendered.
   */
  public void setNotes(List<Note> notes) {
    this.notes.addAll(notes);
  }

  /**
   * Gets the preferred {@link Dimension}.
   */
  public Dimension getPreferredSize() {
    int width = 0;
    int height = 0;

    for (int index = 0; index < this.notes.size(); index++) {
      if (this.notes.get(index).getStartTime() + this.notes.get(index).getDuration() > width) {
        width = this.notes.get(index).getStartTime() + this.notes.get(index).getDuration();
      }
    }

    width = (width * 25) + 150;
    height = (Note.distanceBetweenNotes(this.lowerNote, this.upperNote) * 16) + 180;

    return new Dimension(width, height);

  }

  /**
   * Draws the red line at the current beat.
   *
   * @param currentBeat the current beat
   */
  public void paintRedLine(Graphics musicG, int currentBeat, int tempSum) {
    musicG.drawLine(rLinePos, 120, rLinePos,
            (int) (((Note.distanceBetweenNotes(this.lowerNote, this.upperNote) + 1) * 16) + 120.0));
    rLinePos = (currentBeat - 2) * 25;
  }

  /**
   * Returns the current position of the red line.
   *
   * @return the position of the red line as an int
   */
  public int getrLinePos() {
    return rLinePos;
  }

  /**
   * Draws the grid and {@link Note}s.
   */
  public void paintComponent(Graphics g) {
    this.setFocusable(true);
    super.paintComponent(g);

    Graphics2D musicG = (Graphics2D) g;

    int tempSum = 0;
    for (int index = 0; index < this.notes.size(); index++) {
      if (this.notes.get(index).getStartTime() + this.notes.get(index).getDuration() > tempSum) {
        tempSum = this.notes.get(index).getStartTime() + this.notes.get(index).getDuration();
      }
    }

    musicG.setColor(Color.BLACK);

    for (Note n : this.notes) {
      musicG.draw(new Rectangle2D.Double((double) n.getStartTime() * 25 + 50.0,
              (Note.distanceBetweenNotes(upperNote, n) * 16.0) + 121.0, 24.5, 14.0));
      musicG.fill(new Rectangle2D.Double((double) n.getStartTime() * 25 + 50.0,
              (Note.distanceBetweenNotes(upperNote, n) * 16.0) + 121.0, 24.5, 14.0));
    }

    musicG.setColor(Color.GREEN);
    for (Note n : this.notes) {
      musicG.draw(new Rectangle2D.Double((double) n.getStartTime() * 25 + 75,
              (Note.distanceBetweenNotes(upperNote, n) * 16.0) + 121.0, 25 *
              (n.getDuration() - 1), 14.0));
      musicG.fill(new Rectangle2D.Double((double) n.getStartTime() * 25 + 75,
              (Note.distanceBetweenNotes(upperNote, n) * 16.0) + 121.0, 25 *
              (n.getDuration() - 1), 14.0));
    }

    musicG.setColor(Color.BLACK);

    int ctr = 0;
    for (int i = lowerNote.getNoteVal(); i <= upperNote.getNoteVal(); i++) {
      musicG.drawString(Pitch.noteValToString(i), 0,
              (((Note.distanceBetweenNotes(this.lowerNote, this.upperNote) + 1)
                      * 16) + 116) - 16 * ctr);
      ctr++;
    }

    for (int index = 0; index < ((int) (Math.ceil(tempSum / 4)) * 100); index += 400) {
      musicG.drawString(Integer.toString((index / 400) * 16), 50 + index, 110);

    }

    musicG.drawString("Enter Instrument:", 340, 60);

    musicG.drawString("Enter Octave:", 150, 60);

    musicG.drawString("Enter Starting Beat:", 550, 60);
    musicG.drawString("Enter Duration:", 780, 60);
    musicG.drawString("Enter Volume:", 980, 60);

    for (Line2D l : this.lines) {
      musicG.drawLine((int) l.getX1(), (int) l.getY1(),
              (int) l.getX2(), (int) l.getY2());
    }

    musicG.setColor(Color.RED);

    this.paintRedLine(musicG, CompositeView.currentBeat, tempSum);
  }
}

