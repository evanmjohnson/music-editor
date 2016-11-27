package cs3500.music.provider;

import cs3500.music.model.Note;

import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * This class represents the model as printed out and displayed as a GUI, while also
 * being played as midi music in tandem.
 */
public class CompositeView implements IGuiView {
  private GuiView guiView;
  private MidiView midiView;
  static int currentBeat;
  private Thread thread1;
  private Thread thread2;

  /**
   * Constructs a composite view consisting of a {@link GuiView} and a {@link MidiView}.
   *
   * @param g the GuiView component of the composite view
   * @param m the MidiView component of the composite view
   */
  public CompositeView(GuiView g, MidiView m) {
    this.guiView = g;
    this.midiView = m;
    currentBeat = midiView.getCurrentBeat();
  }

  @Override
  public void renderModel(ViewModel viewmodel) throws InterruptedException {
    thread2 = new Thread() {
      public void run() {
        guiView.renderModel(viewmodel);
        for (; currentBeat <= viewmodel.getFinalBeat() + 9; currentBeat++) {
          try {
            Thread.sleep(viewmodel.getTempo() / 1000);
            guiView.getDisplayPanel().repaint();
            guiView.scrollToRedLine();

          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
    thread1 = new Thread() {
      public void run() {
        try {
          midiView.renderModel(viewmodel);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    };
    thread2.start();
    Thread.sleep(1070);
    thread1.start();
  }

  @Override
  public void scrollToRedLine() {
    this.guiView.scrollToRedLine();
  }

  @Override
  public void scrollBeginning() {
    this.guiView.scrollBeginning();
  }

  @Override
  public void scrollEnd() {
    this.guiView.scrollEnd();
  }

  @Override
  public void scrollUp() {
    this.guiView.scrollUp();
  }

  @Override
  public void scrollDown() {
    this.guiView.scrollDown();
  }

  @Override
  public void scrollLeft() {
    this.guiView.scrollLeft();
  }

  @Override
  public void scrollRight() {
    this.guiView.scrollRight();
  }


  @Override
  public void addListener(KeyListener e) {
    this.guiView.addListener(e);
  }

  @Override
  public void addMouse(MouseListener e) {
    this.guiView.addMouse(e);
  }

  @Override
  public Note getNoteAtPoint(Point p) {
    return this.guiView.getNoteAtPoint(p);
  }

  @Override
  public ViewPanel getDisplayPanel() {
    return this.guiView.getDisplayPanel();
  }

  @Override
  public JScrollPane getScrollPane() {
    return this.guiView.getScrollPane();
  }

  @Override
  public Thread getThread1() {
    return thread1;
  }

  @Override
  public Thread getThread2() {
    return this.thread2;
  }

  @Override
  public JButton getButton() {
    return this.guiView.getButton();
  }

  @Override
  public void action() {
    this.guiView.action();
    this.midiView.addNote(this.getAddedNote());
  }

  @Override
  public Note getAddedNote() {
    return this.guiView.getAddedNote();
  }


}
