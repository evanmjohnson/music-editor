package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;
import cs3500.music.model.Repeat;
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
  private IMusicGUIView view;
  private String type;
  private boolean playing;
  private int counter;
  private int repeatStart;

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
    if (type.equals("combined")) {
      this.startCombined(model);
    } else {
      this.model = model;
      this.view = new JFrameView();
      MusicViewModel viewModel = new MusicViewModel(model);
      view.create(viewModel);
      view.makeVisible();
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
    this.playing = false;
    MusicViewModel viewModel = new MusicViewModel(model);
    this.view = new CombinedView();
    this.configureKeyBoardListener();
    view.create(viewModel);
    view.createRedLine();
    this.configureMouseListener();
    Timer timer = new Timer();
    long period = (long) (model.getTempo() / 30000.0);
    repeatStart = 0;
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
         if (playing && counter<= model.getNumBeats() * 30) {
          if (counter % 30 == 0) {
            view.sendNotes(counter / 30);
          }
          counter++;
          view.moveRedLine(counter);
          view.reDrawNotes(viewModel);
          // check if there is a repeat here
          if (counter % 30 == 0 && model.repeatEndsHere(counter / 30)) {
            // if this repeat has endings, go to the next one
            if (model.getRepeat(counter / 30).hasEndings()) {
              counter = model.getNextEnding(counter / 30) * 30;
            }
            // if this repeat is an ending, go back to the beginning of the parent repeat
            // or continue going on if this is the last ending
            else if (model.getRepeat(counter / 30).hasParent()) {
              counter = model.getBeginningofRepeat(counter / 30) * 30;
            }
            else {
              counter =  model.getBeginningofRepeat(counter / 30) * 30;
            }
          }
        }
      }
    }, 0, period);
    view.makeVisible();
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

    keyReleases.put(KeyEvent.VK_A, () -> {
      // because we want user input, we set the first four paramaters to null and the last to true.
      Note n = view.showAddPrompt();
      if (n != null) {
        model.add(n);
        MusicViewModel viewModel = new MusicViewModel(model);
        this.view.reDraw(viewModel);
      }
    });

    keyReleases.put(KeyEvent.VK_R, () -> {
      MusicViewModel viewModel = new MusicViewModel(model);
      Repeat toAdd = view.showRepeatPrompt(viewModel);
      boolean validAddition = true;
      if (toAdd != null) {
        for (Repeat r : model.getRepeats()) {
          // if the new repeat is between
          if (toAdd.getStartBeat() > r.getStartBeat() && toAdd.getStartBeat() < r.getEndBeat()) {
            view.showInvalidRepeat();
            validAddition = false;
          }
          else if (toAdd.getEndBeat() > r.getStartBeat()
              && toAdd.getEndBeat() < r.getEndBeat()) {
            view.showInvalidRepeat();
            validAddition = false;
          }
        }
        if (validAddition) {
          if (toAdd.hasParent()) {
            model.addEnding(toAdd);
          }
          else {
            model.addRepeat(toAdd);
          }
          this.view.reDraw(viewModel);
        }
      }
    });

    if (type.equals("combined")) {
      keyReleases.put(KeyEvent.VK_SPACE, () -> {
        this.playing = !playing;
      });
    }

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

  /**
   * Configures the view's mouse listener to a new {@link MouseHandler} that calls back
   * to this controller.
   */
  private void configureMouseListener() {
    MouseHandler mouse = new MouseHandler(this);
    this.view.setMouseListener(mouse);
  }

  /**
   * Adds a new {@link Repeat} to this piece with the given start and end beats. This method is
   * used for testing and bypasses the user input portion of adding a repeat to the piece.
   * @param start the start beat of the repeat
   * @param end the end beat of the repeat
   */
  public void addRepeat(int start, int end) {
    model.addRepeat(new Repeat(start, end));
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
          if (this.view.doRemove()) {
            model.remove(clicked);
            this.view.reDrawRemove(viewModel);
          }
        } else {
          model.remove(clicked);
        }
      }
    } catch (IllegalArgumentException e) {
      return;
    }
  }
}