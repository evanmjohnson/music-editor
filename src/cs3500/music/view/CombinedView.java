package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.IllegalFormatException;

/**
 * Represents a view that combines the window of the visual view and the sound of the MIDI view.
 */
public class CombinedView implements IMusicGUIView {
  private int currentBeat;

  @Override
  public void create(MusicViewModel model) {

  }

  @Override
  public void makeVisible() {

  }

  @Override
  public Note showAddPrompt() throws IllegalFormatException {
    return null;
  }

  @Override
  public void resetFocus() {

  }

  @Override
  public void addListener(KeyListener kbd) {

  }

  @Override
  public void scrollRight() {

  }

  @Override
  public void scrollLeft() {

  }

  @Override
  public void reDraw(MusicViewModel model) {

  }

  @Override
  public void reDrawNotes(MusicViewModel model) {

  }

  @Override
  public boolean doRemove() {
    return false;
  }

  @Override
  public void setMouseListener(MouseListener mouse) {

  }
}