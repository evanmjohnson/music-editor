package cs3500.music.view;

import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.IllegalFormatException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a view that combines the window of the visual view and the sound of the MIDI view.
 */
public class CombinedView implements IMusicGUIView {
  private IMusicGUIView gui;
  private IMusicView midi;
  private boolean playing;

  public CombinedView() {
    this.gui = new JFrameView();
    this.midi = new MidiView();
  }

  @Override
  public void create(MusicViewModel model) {
    gui.create(model);
    this.createRedLine();
    gui.makeVisible();
    while (playing) {
      //midi.create;
    }
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
  public void resetFocus() {

  }

  @Override
  public void addListener(KeyListener kbd) {
    gui.addListener(kbd);
  }

  @Override
  public void scrollRight() {

  }

  @Override
  public void scrollLeft() {

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
  public void pause() {
    this.playing = false;
  }

  @Override
  public void resume() {

  }

  @Override
  public int getCurrentPosition() {
    return gui.getCurrentPosition();
  }
}