package cs3500.music.controller;

import com.sun.glass.ui.View;
import com.sun.org.apache.xpath.internal.operations.Mod;
import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;
import cs3500.music.provider.CompositeView;
import cs3500.music.provider.GuiView;
import cs3500.music.provider.IGuiView;
import cs3500.music.provider.MidiView;
import cs3500.music.provider.ViewModel;
import cs3500.music.view.CombinedView;
import cs3500.music.view.IMusicGUIView;
import cs3500.music.view.JFrameView;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a controller for the GUI view.
 */
public class GUIController extends MusicController implements IMouseCallback {
  private IMusicModel model;
  //  private IMusicGUIView view;
  private IGuiView view;
  private String type;
  private boolean playing;
  private int counter;

  /**
   * Constructs a new GUIController with the given paramaters.
   *
   * @param model the model which to control
   * @param args  the command line arguments given to the program, the first of which is
   *              what type of view to use
   */
  public GUIController(IMusicModel model, String[] args) {
    this.model = model;
    this.type = args[0];
  }

  @Override
  public void start(IMusicModel model, String[] args) {
    if (type.equals("composite")) {
      this.startCombined(model);
    } else {
      this.model = model;
//      this.view = new JFrameView();
      this.view = new GuiView();
//      MusicViewModel viewModel = new MusicViewModel(model);
      ModelAdapter adapter = new ModelAdapter(model);
      ViewModel viewModel = new ViewModel(adapter);
      try {
        view.renderModel(viewModel);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
//      view.create(viewModel);
//      view.makeVisible();
      configureKeyBoardListener();
      configureMouseListener();
    }
  }

  /**
   * Starts the combined view.
   *
   * @param model The model of the work to display
   */
  private void startCombined(IMusicModel model) {
    this.playing = true;
//    MusicViewModel viewModel = new MusicViewModel(model);
//    this.view = new CombinedView();
    this.view = new CompositeView(new GuiView(), new MidiView());
    this.configureKeyBoardListener();
//    view.create(viewModel);
//    view.createRedLine();
    ModelAdapter adapter = new ModelAdapter(model);
    ViewModel viewModel = new ViewModel(adapter);
    try {
      view.renderModel(viewModel);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.configureMouseListener();
//    Timer timer = new Timer();
//    long period = (long) (model.getTempo() / 30000.0);
//    timer.scheduleAtFixedRate(new TimerTask() {
//      @Override
//      public void run() {
//        if (playing && counter <= model.getNumBeats() * 30) {
//          if (counter % 30 == 0) {
//            view.sendNotes(counter / 30);
//          }
//          counter++;
//          view.moveRedLine();
//          view.reDrawNotes(viewModel);
//        }
//      }
//    }, 0, period);
//    view.makeVisible();
  }

  /**
   * Configures the keyboard listener for the view.
   */
  private void configureKeyBoardListener() {
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyReleases.put(KeyEvent.VK_RIGHT, () -> {
      this.view.scrollRight();
    });

    keyReleases.put(KeyEvent.VK_LEFT, () -> {
      this.view.scrollLeft();
    });

    keyReleases.put(KeyEvent.VK_DOWN, () -> {
      this.view.scrollDown();
    });

    keyReleases.put(KeyEvent.VK_UP, () -> {
      this.view.scrollUp();
    });

    keyReleases.put(KeyEvent.VK_HOME, () -> {
      this.view.scrollBeginning();
    });

    keyReleases.put(KeyEvent.VK_END, () -> {
      this.view.scrollEnd();
    });

    keyReleases.put(KeyEvent.VK_SPACE, () -> {
      this.view.getThread1().suspend();
      this.view.getThread2().suspend();
    });

    keyReleases.put(KeyEvent.VK_P, () -> {
      this.view.getThread1().resume();
      this.view.getThread2().resume();
    });

//    keyReleases.put(KeyEvent.VK_A, () -> {
//      // because we want user input, we set the first four paramaters to null and the last to true.
//      this.addNote(null, 0, 0, 0, true);
//      Note n = view.showAddPrompt();
//      if (n != null) {
//        model.add(n);
//        MusicViewModel viewModel = new MusicViewModel(model);
//        this.view.reDraw(viewModel);
//      }
//    });

//    if (type.equals("combined")) {
//      keyReleases.put(KeyEvent.VK_SPACE, () -> {
//        this.playing = !playing;
//      });
//    }

    KeyboardHandler kbd = new KeyboardHandler();
    kbd.setKeyPressedMap(new HashMap<>());
    kbd.setKeyTypedMap(new HashMap<>());
    kbd.setKeyReleasedMap(keyReleases);
    view.addListener(kbd);
  }

  /**
   * Add a new Note to the model with the given paramaters. If the Note components are
   * {@code null} and {@code showOption} is true, then delegate to the view to show an
   * option pane requesting user input. If the Note components are not {@code null} and
   * {@code showOption} is false, add the Note from the given input.
   *
   * @param pitchType  a String representation of the {@link PitchType} of the new Note
   * @param start      the starting beat of the new Note
   * @param duration   the duration of the new Note
   * @param octave     the octave of the new Note
   * @param showOption whether or not to show an option pane requesting the user's
   *                   input on the fields of the new Note
   */
  /*
  public void addNote(String pitchType, int start, int duration, int octave,
                      boolean showOption) {
    if (showOption && pitchType == null && start == 0 && duration == 0 && octave == 0) {
      view.showAddPrompt();
    } else if (!showOption && pitchType != null) {
      PitchType type = PitchType.valueOf(pitchType);
      model.add(new Note(type, start, duration, octave));
    } else {
      throw new IllegalArgumentException("If showOption is true, the first four paramaters must" +
          "be null. If showOption is false, they must have value.");
    }
  }
  */

  /**
   * Configures the view's mouse listener to a new {@link MouseHandler} that calls back
   * to this controller.
   */
  private void configureMouseListener() {
    MouseHandler mouse = new MouseHandler(this);
//    this.view.setMouseListener(mouse);
    this.view.addMouse(mouse);
  }

  @Override
  public void check(int x, int y, boolean showOption) {
    try {
      if (x > model.getNumBeats() * 30 || y > model.getNoteRange().size() * 24) {
        return;
      }
      Note clicked = this.model.getNote(model.getNoteRange().size() - y / 22 - 1, x / 30);
      if (clicked != null) {
        MusicViewModel viewModel = new MusicViewModel(this.model);
        if (showOption) {
//          if (this.view.doRemove()) {
//            model.remove(clicked);
//            this.view.reDrawRemove(viewModel);
//          }
        } else {
          model.remove(clicked);
        }
      }
    } catch (IllegalArgumentException e) {
      return;
    }
  }
}