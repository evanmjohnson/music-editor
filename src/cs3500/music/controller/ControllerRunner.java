package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.IMusicView;
import cs3500.music.view.JFrameView;

/**
 * Created by evan on 11/1/16.
 */
public class ControllerRunner {
  public static void main(String[] args) {
    MusicController controller = new MusicController();
    IMusicModel model = new MusicModel();
    Note middleC = new Note(PitchType.C, 0, 5, 4);
    model.add(middleC);
    Note e5 = new Note(PitchType.E,  5, 10, 5);
    model.add(e5);
    Note d4 = new Note(PitchType.D,  30, 7, 4);
    model.add(d4);


//    JFrameView frame = new JFrameView();
//    MusicViewModel view = new MusicViewModel(model);
//    frame.draw(view);

    controller.start(model, args);
  }
}
