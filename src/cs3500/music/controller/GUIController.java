package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.model.PitchType;
import cs3500.music.provider.CompositeView;
import cs3500.music.provider.GuiView;
import cs3500.music.provider.IGuiView;
import cs3500.music.provider.MidiView;
import cs3500.music.provider.ViewModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a controller for the GUI view.
 */
public class GUIController extends MusicController implements ActionListener {
  private IGuiView view;
  private IMusicModel model;
  private String type;
  private boolean playing;

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
  public void start(String[] args) {
    if (type.equals("composite")) {
      this.startCombined(model);
    } else {
      this.view = new GuiView();
      ModelAdapter adapter = new ModelAdapter(model);
      ViewModel viewModel = new ViewModel(adapter);
      try {
        view.renderModel(viewModel);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
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
    this.view = new CompositeView(new GuiView(), new MidiView());
    this.configureKeyBoardListener();
    ModelAdapter adapter = new ModelAdapter(model);
    ViewModel viewModel = new ViewModel(adapter);
    try {
      view.renderModel(viewModel);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    this.configureMouseListener();
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
      if (playing) {
        this.view.getThread1().suspend();
        this.view.getThread2().suspend();
      }
      else {
        this.view.getThread1().resume();
        this.view.getThread2().resume();
      }
      playing = !playing;
    });

    KeyboardHandler kbd = new KeyboardHandler();
    kbd.setKeyPressedMap(new HashMap<>());
    kbd.setKeyTypedMap(new HashMap<>());
    kbd.setKeyReleasedMap(keyReleases);
    view.addListener(kbd);
  }

  /**
   * Configures the view's mouse listener to a new {@link MouseHandler} that calls back
   * to this controller.
   */
  private void configureMouseListener() {
//    MouseHandler mouse = new MouseHandler(this);
//    this.view.setMouseListener(mouse);
//    this.view.addMouse(mouse);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // // TODO: 12/4/16 WRITE AN ACTION LISTENER 
    System.out.println("action performed");
    this.view.action();
  }
}