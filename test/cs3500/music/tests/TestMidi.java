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
    Note c0 = new Note(PitchType.C, 1, 2, 0, 12, 15);
    compBuild.addNote(1, 3, 12, 12, 15);
    List<Note> exp = new ArrayList<>();
    exp.add(c0);
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
    Note c4 = new Note(PitchType.C, 1, 3, 4, 15, 15);
    Note c0 = new Note(PitchType.C, 1, 2, 0, 12, 12);
    compBuild.addNote(1, 3, 12, 12, 12);
    compBuild.addNote(1, 4, 15, 60, 15);
    List<Note> exp = new ArrayList<>();
    exp.add(c0);
    exp.add(c4);
    assertEquals(exp, compBuild.build().noteListContinueAt(2));
  }

  @Test
  public void testAddNote5() {
    Note c0 = new Note(PitchType.C, 1, 2, 0);
    compBuild.addNote(1, 3, 0, 12, 127);
    List<Note> exp = new ArrayList<>();
    exp.add(c0);
    assertEquals(exp, compBuild.build().noteListStartAt(1));
  }

  /**
   * Cannot add a note with a volume greater than 127.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteException() {
    compBuild.addNote(1, 3, 0, 12, 128);
  }

  /**
   * Cannot add a note with a volume less than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteException2() {
    compBuild.addNote(1, 3, 0, 12, -1);
  }

  /**
   * Cannot add a note with a pitch less than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteException3() {
    compBuild.addNote(1, 3, 0, -1, 127);
  }


  /**
   * Cannot add a note with a pitch greater than 127.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteException4() {
    compBuild.addNote(1, 3, 0, 130, 12);
  }

  /**
   * Cannot add a note with an instrument greater than 127.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteException5() {
    compBuild.addNote(1, 3, 130, 0, 12);
  }

  /**
   * Cannot add a note with an instrument less than 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddNoteException6() {

    compBuild.addNote(1, 3, -1, 15, 127);
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

  @Test
  public void testMidiTwoNotes() {
    model.setTempo(2);
    Note a4 = new Note(PitchType.A, 0, 2, 4, 1, 120);
    Note e5 = new Note(PitchType.E, 3, 4, 5, 6, 12);
    model.add(a4);
    model.add(e5);
    MusicViewModel vm = new MusicViewModel(model);
    midi.create(vm);
    String exp = "start 0 57 120\n" +
            "stop 0 57 120\n" +
            "start 0 64 12\n" +
            "stop 0 64 12\n";
    assertEquals(exp, midi.messageString.toString());
  }

  /**
   * Cannot create a MIDI view if the given model is empty.
   */
  @Test (expected = IllegalArgumentException.class)
  public void testMidiEmpty() {
    model.setTempo(2);
    MusicViewModel vm = new MusicViewModel(model);
    midi.create(vm);
  }
}
