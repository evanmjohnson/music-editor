package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.MusicViewModel;
import cs3500.music.model.Note;
import cs3500.music.view.IMusicGUIView;
import cs3500.music.view.JFrameView;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a controller for the GUI view.
 */
public class GUIController extends MusicController {
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
    this.view.addActionListener(this);
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
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();

    //keyPresses.put(KeyEvent.VK_A, new MakeCaps());
    // keyReleases.put(KeyEvent.VK_C, new MakeOriginalCase());
    // Another possible syntax: instead of defining a new class, just to make a single instance,
    // you can create an "anonymous class" that implements a particular interface, by writing
    // "new Interfacename() { all the methods you need to implement }"
    // Note that "view" is in scope inside this Runnable!  But, also note that within the Runnable,
    // "this" refers to the Runnable and not to the Controller, so we don't say "this.view".
    keyTypes.put('a', new Runnable() {
      public void run() {
        Note n = view.showAddPrompt();
      }
    });

    // Another possible syntax:
    // Instead of an anonymous class, you can (as of Java 8) use "lambda syntax",
    // as follows: if the interface you want to implement has only one single method,
    // then write the parenthesized argument list, followed by an arrow, followed by
    // the body of the method.  Java will infer that you mean to implement the particular
    // single method of that interface, and translate the code for you to resemble the
    // anonymous Runnable example above.
    // Again note all the names that are in scope.
    keyTypes.put('r', () -> {
      // exchange the hotkeys C and U:
      // Take the event handlers from VK_C and VK_U
      Runnable oldCHandler = keyPresses.get(KeyEvent.VK_C);
      Runnable oldUHandler = keyPresses.get(KeyEvent.VK_U);
      // Update the C handler
      if (oldCHandler != null)
        keyPresses.put(KeyEvent.VK_U, oldCHandler);
      else
        keyPresses.remove(KeyEvent.VK_U);
      // Update the U handler
      if (oldUHandler != null)
        keyPresses.put(KeyEvent.VK_U, oldCHandler);
      else
        keyPresses.remove(KeyEvent.VK_U);
    });

    KeyboardListener kbd = new KeyboardListener();
    kbd.setKeyTypedMap(keyTypes);
    kbd.setKeyPressedMap(keyPresses);
    kbd.setKeyReleasedMap(keyReleases);

    //view.addKeyListener(kbd);

  }

  /*
  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    switch (e.getActionCommand()) {
      //read from the input textfield
      case "Echo Button":
        String text = view.getInputString();
        //send text to the model
        model.setString(text);

        //clear input textfield
        view.clearInputString();
        //finally echo the string in view
        text = model.getString();
        view.setEchoOutput(text);

        //set focus back to main frame so that keyboard events work
        view.resetFocus();

        break;
      case "Exit Button":
        System.exit(0);
        break;
    }
  }

  // THESE CLASSES ARE NESTED INSIDE THE CONTROLLER,
  // SO THAT THEY HAVE ACCESS TO THE VIEW
  class MakeCaps implements Runnable {
    public void run() {
      String text = model.getString();
      text = text.toUpperCase();
      view.setEchoOutput(text);
    }
  }

  class MakeOriginalCase implements Runnable {
    public void run() {
      String text = model.getString();
      view.setEchoOutput(text);
    }
  }
  */
}
