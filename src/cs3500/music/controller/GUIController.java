package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
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
  private Timer timer;
  private boolean playing;
  private int counter;

  @Override
  public void start(IMusicModel model, String[] args) {
    this.type = args[0];
    if (type.equals("combined")) {
      this.startCombined(model);
    }
    else {
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
   * @param model The model of the work to display
   */
  private void startCombined(IMusicModel model) {
    this.playing = true;
    MusicViewModel viewModel = new MusicViewModel(model);
    this.view = new CombinedView();
    this.configureKeyBoardListener();
    view.create(viewModel);
    view.makeVisible();
    view.createRedLine();
    timer = new Timer();
    counter = 0;
    long period = (long)(model.getTempo()/30000.0);
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (playing && counter <= model.getNumBeats() * 30) {
          counter++;
          view.sendNotes(counter);
          view.moveRedLine();
          view.reDrawNotes(viewModel);
        }
      }
    }, 0, period);
  }

  private void resumeCombined(IMusicModel model, CombinedView view) {
    MusicViewModel viewModel = new MusicViewModel(model);
    long period = (long)(model.getTempo()/30000.0);
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        view.moveRedLine();
        view.reDrawNotes(viewModel);
      }
    }, 0, period);
  }
  /**
   * Creates and sets a keyboard listener for the view. In effect it creates snippets of code as
   * Runnable object, one for each time a key is typed, pressed and released, only for those that
   * the program needs.
   *
   * In this example, we need to toggle color when user TYPES 'd', make the message all caps when
   * the user PRESSES 'c' and reverts to the original string when the user RELEASES 'c'. Thus we
   * create three snippets of code (ToggleColor,MakeCaps and MakeOriginalCase) and put them in the
   * appropriate map.
   *
   * Last we create our KeyboardListener object, set all its maps and then give it to the view.
   */
  private void configureKeyBoardListener() {
    Map<Integer, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    keyTypes.put(KeyEvent.VK_R, () -> {
      this.view.scrollRight();
    });

    keyReleases.put(KeyEvent.VK_RIGHT, () -> {
      this.view.scrollRight();
    });

    keyTypes.put(KeyEvent.VK_L, () -> {
      this.view.scrollLeft();
    });

    keyReleases.put(KeyEvent.VK_LEFT, () -> {
      this.view.scrollLeft();
    });


    keyReleases.put(KeyEvent.VK_A, () -> {
      Note n = view.showAddPrompt();
      model.add(n);
      MusicViewModel viewModel = new MusicViewModel(model);
      this.view.reDraw(viewModel);
    });

    if (type.equals("combined")) {
      keyReleases.put(KeyEvent.VK_SPACE, () -> {
        this.playing = false;
      });
      keyReleases.put(KeyEvent.VK_K, () -> {
        this.playing = true;
      });
    }


    KeyboardHandler kbd = new KeyboardHandler();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);
    view.addListener(kbd);
  }

  private void configureMouseListener() {
    MouseHandler mouse = new MouseHandler(this);
    this.view.setMouseListener(mouse);
  }

  @Override
  public void check(int x, int y) {
    try {
      if (x > model.getNumBeats() || y > model.getNoteRange().size()) {
        return;
      }
      Note clicked = this.model.getNote(model.getNoteRange().size() - y / 22 - 1, x / 30);
      if (clicked != null) {
        clicked.makeSelected(true);
        MusicViewModel viewModel = new MusicViewModel(this.model);
        if (this.view.doRemove()) {
          model.remove(clicked);
          this.view.reDrawNotes(viewModel);
        }
      }
    }
    catch (IllegalArgumentException e) {
      return;
    }
  }
}