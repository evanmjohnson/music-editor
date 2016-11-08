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
    args = new String[2];
    args[0] = "midi";
    //args[1] = "/Users/evan/Downloads/code 4/mary-little-lamb.txt";
    //args[1] = "/Users/evan/Downloads/code 4/lnl.txt";
    args[1] = "/Users/Shravali/Desktop/OOD/starteractual/mary-little-lamb.txt";
    viewModel = MusicReader.parseFile(new FileReader(args[1]), new MusicBuilder());
    MusicController controller = new MusicController();
    //controller.start(viewModel, args);
    args[0] = "visual";
    MusicController controller1 = new MusicController();
    controller1.start(viewModel, args);
  }
}