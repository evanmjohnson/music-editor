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
  private IMusicGUIView gui;
  private IMusicView midi;

  public CombinedView() {
    this.gui = new JFrameView();
    this.midi = new MidiView();
  }

  @Override
  public void create(MusicViewModel model) {
    midi.create(model);
    gui.create(model);
    this.createRedLine();
    gui.makeVisible();
  }

  @Override
  public void makeVisible() {
    gui.makeVisible();
  }

  @Override
  public Note showAddPrompt() throws IllegalFormatException {
    return null;
  }

  @Override
  public void addListener(KeyListener kbd) {
    gui.addListener(kbd);
  }

  @Override
  public void scrollRight() {
    gui.scrollRight();
  }

  @Override
  public void scrollLeft() {
    gui.scrollLeft();
  }

  @Override
  public void scrollUp() {
    gui.scrollUp();
  }

  @Override
  public void scrollDown() {
    gui.scrollDown();
  }

  @Override
  public void reDraw(MusicViewModel model) {
    gui.reDraw(model);
  }

  @Override
  public void reDrawNotes(MusicViewModel model) {
    gui.reDrawNotes(model);
  }

  @Override
  public boolean doRemove() {
    return false;
  }

  @Override
  public void setMouseListener(MouseListener mouse) {
    gui.setMouseListener(mouse);
  }

  @Override
  public void moveRedLine() {
    gui.moveRedLine();
  }

  @Override
  public void createRedLine() {
    gui.createRedLine();
  }

  @Override
  public void sendNotes(int counter) {
    midi.sendNotes(counter);
  }

  @Override
  public int getCurrentPosition() {
    return gui.getCurrentPosition();
  }
}