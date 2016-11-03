package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.IMusicView;

/**
 * Created by evan on 11/1/16.
 */
public class ControllerRunner {
  public static void main(String[] args) {
    MusicController controller = new MusicController();
    IMusicModel model = new MusicModel();
    Note middleC = new Note(PitchType.C, 0, 17, 4);
    model.add(middleC);
    Note e5 = new Note(PitchType.E,  3, 1, 5);
    model.add(e5);
    controller.start(model, "visual");
  }
}
