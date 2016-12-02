package cs3500.music.controller;

import cs3500.music.model.IMusicModel;
import cs3500.music.model.MusicModel;
import cs3500.music.model.PitchType;
import cs3500.music.provider.IMusicEditorModel;
import cs3500.music.provider.Note;
import cs3500.music.provider.Pitch;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapts our view to the provider's view interface.
 */
public class ModelAdapter implements IMusicEditorModel {

  private IMusicModel model;

  public ModelAdapter() {
    this.model = new MusicModel();
  }

  public ModelAdapter(IMusicModel model) {
    this.model = model;
  }

  /**
   * Converts a provider's Note to our Note.
   * @param note a {@link cs3500.music.provider.Note} to convert
   * @return a {@link cs3500.music.model.Note}
   */
  private cs3500.music.model.Note convertNoteFromProvider(cs3500.music.provider.Note note) {
    String noteString = note.getPitch().toString();
    PitchType pitch = PitchType.A;
    switch (noteString) {
      case "A" :
        pitch = PitchType.A;
        break;
      case "A#" :
        pitch = PitchType.ASharp;
        break;
      case "B" :
        pitch = PitchType.B;
        break;
      case "C" :
        pitch = PitchType.C;
        break;
      case "C#" :
        pitch = PitchType.CSharp;
        break;
      case "D" :
        pitch = PitchType.D;
        break;
      case "D#" :
        pitch = PitchType.DSharp;
        break;
      case "E" :
        pitch = PitchType.E;
        break;
      case "F" :
        pitch = PitchType.F;
        break;
      case "F#" :
        pitch = PitchType.FSharp;
        break;
      case "G" :
        pitch = PitchType.G;
        break;
      case "G#" :
        pitch = PitchType.GSharp;
        break;
    }
    int startBeat = note.getStartTime();
    int duration = note.getDuration();
    int octave = note.getOctave();
    int instrument = note.getInstrument();
    int volume = note.getVolume();
    return new cs3500.music.model.Note(pitch, startBeat, duration, octave, instrument, volume);
  }

  /**
   * Converts a our Note to a provider's Note.
   * @param note a {@link cs3500.music.model.Note} to convert
   * @return a {@link cs3500.music.provider.Note} with the same fields as the given note
   */
  private cs3500.music.provider.Note convertNoteToProvider(cs3500.music.model.Note note) {
    String noteString = note.getPitch().toString();
    Pitch pitch = Pitch.C;
    switch (noteString) {
      case "A" :
        pitch = Pitch.A;
        break;
      case "A#" :
        pitch = Pitch.ASHARP;
        break;
      case "B" :
        pitch = Pitch.B;
        break;
      case "C" :
        pitch = Pitch.C;
        break;
      case "C#" :
        pitch = Pitch.CSHARP;
        break;
      case "D" :
        pitch = Pitch.D;
        break;
      case "D#" :
        pitch = Pitch.DSHARP;
        break;
      case "E" :
        pitch = Pitch.E;
        break;
      case "F" :
        pitch = Pitch.F;
        break;
      case "F#" :
        pitch = Pitch.FSHARP;
        break;
      case "G" :
        pitch = Pitch.G;
        break;
      case "G#" :
        pitch = Pitch.GSHARP;
        break;
    }
    int octave = note.getOctave();
    int instrument = note.getInstrument();
    int volume = note.getVolume();
    int duration = note.getDuration();
    int startTime = note.getStartBeat();
    return new cs3500.music.provider.Note(pitch, octave, instrument, volume, duration, startTime);
  }

  @Override
  public void addNote(Note note) {
    this.model.add(this.convertNoteFromProvider(note));
  }

  @Override
  public void removeNote(Note note) {
    this.model.remove(this.convertNoteFromProvider(note));
  }

  @Override
  public void editNote(Note note1, Note note2) {
    this.removeNote(note1);
    this.addNote(note2);
  }

  @Override
  public void setTempo(int tempo) {
    this.model.setTempo(tempo);
  }

  @Override
  public int getTempo() {
    return this.model.getTempo();
  }

  @Override
  public Note getLowerNote() {
    return this.convertNoteToProvider(this.model.lowestNote());
  }

  @Override
  public Note getUpperNote() {
    return this.convertNoteToProvider(this.model.highestNote());
  }

  @Override
  public int getFinalBeat() {
    return this.model.getNumBeats();
  }

  @Override
  public List<Note> getAllNotesAtBeat(int beat) {
    List<cs3500.music.model.Note> cont = this.model.noteListContinueAt(beat);
    List<cs3500.music.model.Note> start = this.model.noteListStartAt(beat);
    cont.addAll(start);
    List<cs3500.music.provider.Note> result = new ArrayList<>();
    for (cs3500.music.model.Note n : cont) {
      result.add(this.convertNoteToProvider(n));
    }
    return result;
  }

  @Override
  public List<Note> getStartingNotesAtBeat(int beat) {
    List<cs3500.music.model.Note> start = this.model.noteListStartAt(beat);
    List<cs3500.music.provider.Note> result = new ArrayList<>();
    for (cs3500.music.model.Note n : start) {
      result.add(this.convertNoteToProvider(n));
    }
    return result;
  }

  @Override
  public int getTotalDuration() {
    return model.getNumBeats();
  }

  @Override
  public void merge(IMusicEditorModel model, int beatsShifted) throws IllegalArgumentException {
    // this method was never used in the code that we received
  }

  @Override
  public void append(IMusicEditorModel model) throws IllegalArgumentException {
    // this method was never used in the code that we received
  }
}
