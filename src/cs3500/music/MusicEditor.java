package cs3500.music;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.view.JFrameView;
import cs3500.music.view.MidiView;

import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;

/**
 * Runs the music editor program.
 */
public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    JFrameView view = new JFrameView();
    MidiView midiView = new MidiView();
    IMusicModel model = new MusicModel();

  }
}
