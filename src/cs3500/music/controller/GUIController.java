package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
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
  private boolean playing;
  private int counter;

  @Override
  public void start(IMusicModel model, String[] args) {
    this.type = args[0];
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
    Timer timer = new Timer();
    long period = (long) (model.getTempo() / 30000.0);
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (playing && counter <= model.getNumBeats() * 30) {
          if (counter % 30 == 0) {
            view.sendNotes(counter / 30);
          }
          counter++;
          view.moveRedLine();
          view.reDrawNotes(viewModel);
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
      Note n = view.showAddPrompt();
      if (n != null) {
        model.add(n);
        MusicViewModel viewModel = new MusicViewModel(model);
        this.view.reDraw(viewModel);
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

  private void configureMouseListener() {
    MouseHandler mouse = new MouseHandler(this);
    this.view.setMouseListener(mouse);
  }

  @Override
  public void check(int x, int y) {
    try {
      if (x > model.getNumBeats() * 30 || y > model.getNoteRange().size() * 24) {
        return;
      }
      Note clicked = this.model.getNote(model.getNoteRange().size() - y / 22 - 1, x / 30);
      System.out.println(clicked);
      if (clicked != null) {
        MusicViewModel viewModel = new MusicViewModel(this.model);
        if (this.view.doRemove()) {
          model.remove(clicked);
          this.view.reDraw(viewModel);
        }
      }
    } catch (IllegalArgumentException e) {
      return;
    }
  }
}