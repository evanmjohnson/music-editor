package cs3500.music;

import cs3500.music.controller.MusicController;
import cs3500.music.model.IMusicModel;
import cs3500.music.util.MusicBuilder;
import cs3500.music.util.MusicReader;

import java.io.FileReader;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;

/**
 * Runs the music editor program.
 */
public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicModel viewModel;
    viewModel = MusicReader.parseFile(new FileReader(args[0]), new MusicBuilder());
    MusicController controller = new MusicController();
    controller.start(viewModel, args);
  }
}
