package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicGUIView;
import cs3500.music.view.JFrameView;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;


/**
 * Represents a controller for the GUI view.
 */
public class GUIController extends MusicController implements IMouseCallback {
  private IMusicModel model;
  private IMusicGUIView view;

  @Override
  public void start(IMusicModel model, String[] args) {
    this.model = model;
    this.view = new JFrameView();
    MusicViewModel viewModel = new MusicViewModel(model);
    view.create(viewModel);
    view.makeVisible();
    configureKeyBoardListener();
    configureMouseListener();
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
      System.out.println("RIGHT");
      this.view.scrollRight();
    });

    keyReleases.put(KeyEvent.VK_RIGHT, () -> {
      System.out.println("RIGHT");
      this.view.scrollRight();
    });

    keyTypes.put(KeyEvent.VK_L, () -> {
      this.view.scrollLeft();
      System.out.println("LEFT");
    });

    keyReleases.put(KeyEvent.VK_LEFT, () -> {
      this.view.scrollLeft();
      System.out.println("LEFT");
    });


    keyReleases.put(KeyEvent.VK_A, () -> {
      Note n = view.showAddPrompt();
      System.out.println(model.getNoteRange().size());
      model.add(n);
      System.out.println(model.getNoteRange().size());
      MusicViewModel viewModel = new MusicViewModel(model);
      this.view.reDraw(viewModel);
    });

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
    System.out.println(x/30 + "" + (model.getNoteRange().size() - y/22 + 1) + ", ");
    try {
      Note clicked = this.model.getNote(model.getNoteRange().size() - y / 22 - 1, x / 30);
      if (clicked != null) {
        clicked.makeSelected(true);
        MusicViewModel viewModel = new MusicViewModel(this.model);
        if (this.view.doRemove()) {
          System.out.println(clicked.toString());
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