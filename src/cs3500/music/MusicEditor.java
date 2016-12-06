package cs3500.music;

import cs3500.music.controller.IMusicController;
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
  /**
   * Runs the music editor for this program.
   * @param args the command line prompts for this program.
   * @throws IOException throws if the file can't be found.
   * @throws InvalidMidiDataException throws if the file has invalid midi data.
   */
  public static void main(String[] args) throws IOException, InvalidMidiDataException {
    IMusicModel viewModel;
    viewModel = MusicReader.parseFile(new FileReader(args[1]), new MusicBuilder());
    IMusicController controller = new MusicController(viewModel, args);
    controller.start(args);
  }
}