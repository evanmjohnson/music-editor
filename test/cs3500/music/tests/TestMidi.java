package cs3500.music.tests;

import cs3500.music.controller.MusicController;
import cs3500.music.model.*;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicBuilder;

import cs3500.music.view.MidiView;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.view.ConsoleView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests that correct inputs are provided to Midi so that it correctly plays.
 */
public class TestMidi {
  IMusicModel model = new MusicModel();
  CompositionBuilder<IMusicModel> compBuild = new MusicBuilder();
  StringBuffer out = new StringBuffer();
  ConsoleView console = new ConsoleView(out);
  MidiView midi = new MidiView("");
  MusicController controller = new MusicController();

  @Test
  public void testAddNotes() {
    Note c2 = new Note(PitchType.C, 1, 2, 2, 12, 15);
    compBuild.addNote(1, 3, 12, 12, 15);
    List<Note> exp = new ArrayList<>();
    exp.add(c2);
    assertEquals(exp, compBuild.build().noteListStartAt(1));
  }

  @Test
  public void testAddNote2() {
    Note c2 = new Note(PitchType.C, 1, 2, 2, 12, 15);
    compBuild.addNote(1, 3, 12, 13, 15);
    List<Note> exp = new ArrayList<>();
    exp.add(c2);
    assertNotEquals(exp, compBuild.build().noteListStartAt(1));
  }

  @Test
  public void testAddNote3() {
    Note c2 = new Note(PitchType.C, 1, 2, 2, 12, 15);
    compBuild.addNote(1, 3, 12, 12, 12);
    List<Note> exp = new ArrayList<>();
    exp.add(c2);
    assertNotEquals(exp, compBuild.build().noteListStartAt(1));
  }

  @Test
  public void testAddNote4() {
    Note f1 = new Note(PitchType.F, 1, 2, 1, 5, 5);
    Note c2 = new Note(PitchType.C, 1, 2, 2, 12, 15);
    compBuild.addNote(1, 3, 12, 12, 15);
    compBuild.addNote(1, 3, 5, 5, 5);
    List<Note> exp = new ArrayList<>();
    exp.add(f1);
    exp.add(c2);
    assertEquals(exp, compBuild.build().noteListContinueAt(2));
  }

  @Test
  public void testAddNote5() {
    Note c2 = new Note(PitchType.C, 1, 2, 2);
    compBuild.addNote(1, 3, 0, 12, 127);
    List<Note> exp = new ArrayList<>();
    exp.add(c2);
    assertEquals(exp, compBuild.build().noteListStartAt(1));
  }

  /**
   * Cannot add a note with a volume greater than 127.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddNoteException() {
    compBuild.addNote(1, 3, 0, 12, 128);
  }

  /**
   * Cannot add a note with a volume less than 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddNoteException2() {
    compBuild.addNote(1, 3, 0, 12, -1);
  }

  /**
   * Cannot add a note with a pitch less than 0.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddNoteException3() {
    compBuild.addNote(1, 3, 0, -1, 127);
  }

  /**
   * Cannot add a note with a pitch greater than 127.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testAddNoteException4() {
    compBuild.addNote(1, 3, 0, 130, 12);
  }

  @Test
  public void testMidiOneNote() {
    Note c2 = new Note(PitchType.C, 1, 2, 2);
    model.add(c2);
    model.setTempo(2);
    MusicViewModel vm = new MusicViewModel(model);
    midi.create(vm);
    String exp = "start 0 24 127\n" +
        "stop 0 24 127\n";
    assertEquals(exp, midi.messageString.toString());
  }

}
