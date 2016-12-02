package cs3500.music.provider;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cs3500.music.util.CompositionBuilder;

/**
 * This is the implementation of the Music Editor model; it represents
 * one piece of music.
 */
public class MusicEditorModel implements IMusicEditorModel {
  private HashMap<Integer, ArrayList<Note>> notes;
  private int tempo;
  private int finalBeat = -1;
  private Note upperNote = null;
  private Note lowerNote = null;

  /**
   * Constructs a model with a default tempo of 100 bpm.
   * This is the public default constructor.
   */
  public MusicEditorModel() {
    this.notes = new HashMap<>();
    this.tempo = 200000;
  }

  /**
   * Constructs a model with a given tempo.
   *
   * @param tempo the tempo that the model follows
   * @throws IllegalArgumentException if the tempo is not valid
   */
  public MusicEditorModel(int tempo) {
    if (tempo < 1) {
      throw new IllegalArgumentException("Invalid tempo; must be at least 1 bpm");
    } else {
      this.notes = new HashMap<>();
      this.tempo = tempo;
    }
  }

  /**
   * Constructs a model with a given tempo and series of notes.
   *
   * @param tempo the tempo that the model follows
   * @param notes the list of notes that the model has
   * @throws IllegalArgumentException if the tempo is not valid
   */
  public MusicEditorModel(int tempo, List<Note> notes) {
    if (tempo < 1) {
      throw new IllegalArgumentException("Invalid tempo; must be at least 1 bpm");
    } else {
      this.notes = new HashMap<>();

      for (Note n : notes) {
        addNote(n);
      }
      this.tempo = tempo;
    }
  }

  @Override
  public void addNote(Note note) throws IllegalArgumentException {
    try {
      Objects.requireNonNull(note);
    } catch (NullPointerException x) {
      throw new IllegalArgumentException("Note cannot be given as null");
    }

    int startTime = note.getStartTime();

    if (notes.isEmpty()) {
      for (int i = 0; i < startTime; i++) {
        ArrayList<Note> addNewBeat = new ArrayList<>();
        notes.put(i, addNewBeat);
      }
    }

    for (int i = startTime; i <= (note.getDuration() + startTime); i++) {
      if (notes.containsKey(i)) {
        notes.get(i).add(note);
      } else {
        ArrayList<Note> addNewBeat = new ArrayList<>();
        addNewBeat.add(note);
        notes.put(i, addNewBeat);
      }
    }
    this.updateRangeBoundaries(note);
    this.updateFinalBeat(note);
  }

  @Override
  public void removeNote(Note note) {
    try {
      Objects.requireNonNull(note);
    } catch (NullPointerException x) {
      throw new IllegalArgumentException("Note cannot be given as null");
    }

    int startTime = note.getStartTime();

    if (!notes.get(startTime).contains(note)) {
      throw new IllegalArgumentException("Note cannot be removed because it doesn't exist.");
    }

    for (int index = note.getStartTime(); index < note.getStartTime() + note.getDuration();
         index++) {
      this.notes.get(index).remove(note);
    }

    if (note.equals(upperNote)) {
      Note newUpper = null;
      for (ArrayList<Note> beat : this.notes.values()) {
        for (Note n : beat) {
          if (newUpper == null || newUpper.getNoteVal() < n.getNoteVal()) {
            newUpper = n;
          }
        }
        upperNote = newUpper;
      }
    }

    if (note.equals(lowerNote)) {
      Note newLower = null;
      for (ArrayList<Note> beat : this.notes.values()) {
        for (Note n : beat) {
          if (newLower == null || newLower.getNoteVal() > n.getNoteVal()) {
            newLower = n;
          }
        }
        lowerNote = newLower;
      }
    }

    if (note.getStartTime() + note.getDuration() - 1 == finalBeat) {
      int newFinalBeat = 0;
      for (ArrayList<Note> beat : this.notes.values()) {
        for (Note n : beat) {
          if (newFinalBeat < n.getStartTime() + n.getDuration() - 1) {
            newFinalBeat = n.getStartTime() + n.getDuration() - 1;
          }
        }
        finalBeat = newFinalBeat;
      }
    }

  }

  @Override
  public void editNote(Note oldNote, Note newNote)
          throws IllegalArgumentException {
    this.removeNote(oldNote);
    this.addNote(newNote);

    this.updateRangeBoundaries(newNote);
    this.updateFinalBeat(newNote);

  }

  /**
   * Updates the note range boundaries (lower and upper).
   */
  private void updateRangeBoundaries(Note note) {
    int noteVal = note.getNoteVal();

    if (lowerNote == null || upperNote == null) {
      lowerNote = note;
      upperNote = note;
    }
    if (noteVal < lowerNote.getNoteVal()) {
      lowerNote = note;
    }
    if (noteVal > upperNote.getNoteVal()) {
      upperNote = note;
    }
  }

  /**
   * Updates the final beat of the model.
   */
  private void updateFinalBeat(Note note) {
    int noteDuration = note.getDuration();
    int noteStartTime = note.getStartTime();

    if (noteDuration + noteStartTime - 1 > finalBeat) {
      finalBeat = noteDuration + noteStartTime - 1;
    }
  }

  @Override
  public void setTempo(int tempo) throws IllegalArgumentException {
    if (tempo < 1) {
      throw new IllegalArgumentException("Invalid tempo; must be at least 1 bpm");
    } else {
      this.tempo = tempo;
    }
  }

  @Override
  public int getTempo() {
    return this.tempo;
  }

  @Override
  public Note getLowerNote() {
    return this.lowerNote;
  }

  @Override
  public Note getUpperNote() {
    return this.upperNote;
  }

  @Override
  public int getFinalBeat() {
    return this.finalBeat;
  }

  @Override
  public List<Note> getAllNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number");
    }
    if (notes.containsKey(beat)) {
      return notes.get(beat);
    } else {
      return new ArrayList<Note>();
    }
  }

  @Override
  public List<Note> getStartingNotesAtBeat(int beat) throws IllegalArgumentException {
    if (beat < 0) {
      throw new IllegalArgumentException("Invalid beat number");
    }
    List<Note> startingNotes = new ArrayList<Note>();
    if (notes.containsKey(beat)) {
      for (int i = 0; i < notes.get(beat).size(); i++) {
        if (notes.get(beat).get(i).getStartTime() == beat) {
          startingNotes.add(notes.get(beat).get(i));
        }
      }
      return startingNotes;

    } else {
      return new ArrayList<Note>();
    }
  }

  @Override
  public int getTotalDuration() {
    return this.notes.values().size();
  }

  /**
   * Represents this model as a string.
   */
  @Override
  public String toString() {
    int modelLength = finalBeat + 1;
    String result = "";
    for (int index = 0; index < Integer.toString(this.getFinalBeat()).length() - 2; index++) {
      result += " ";
    }

    if (modelLength <= 0) {
      result = "";
    } else {
      for (int i = lowerNote.getNoteVal(); i <= upperNote.getNoteVal(); i = i + 1) {
        result = result + this.center(String.format("%" + "s", Pitch.noteValToString(i)));
      }
      for (int i = 0; i <= finalBeat; i = i + 1) {
        result = result + (String.format("\n%" + Integer.toString(this.getFinalBeat()).length()
                + "d", i)) + printNotes(i);
      }
    }
    return result;
  }

  /**
   * Centers the column indicators by adding spaces.
   *
   * @param str the string to center
   * @return the appended string
   */
  private String center(String str) {
    int buffer = 5 - str.length();
    for (int i = 0; i < (buffer / 2); i++) {
      str += " ";
    }
    while (str.length() < 5) {
      str = " " + str;
    }
    return str;
  }

  /**
   * Represents all of the {@node Note}s at a given beat as a String.
   *
   * @param beat the current beat
   * @return all of the notes at the current beat as a String
   */
  private StringBuilder printNotes(int beat) {
    List<Note> notesToPrint = this.getAllNotesAtBeat(beat);
    int b = (upperNote.getNoteVal() - lowerNote.getNoteVal()) * 5 + 2;
    StringBuilder result = new StringBuilder(String.format("%" + b + "s", ""));

    for (Note note : notesToPrint) {
      int i = (5 * (note.getNoteVal() - lowerNote.getNoteVal())) + 1;
      if (note.getStartTime() == beat) {
        result.setCharAt(i, 'X');
      }
      if (beat > note.getStartTime() && beat <= (note.getStartTime() + note.getDuration()) - 1) {
        result.setCharAt(i, '|');
      }
    }
    return result;
  }

  @Override
  public void merge(IMusicEditorModel model, int beatsShifted) throws IllegalArgumentException {
    try {
      Objects.requireNonNull(model);
    } catch (NullPointerException x) {
      throw new IllegalArgumentException("The model given cannot be null");
    }

    if (beatsShifted < 0) {
      throw new IllegalArgumentException("Negative shifts are not allowed");
    }

    List<Note> noteList = new ArrayList<>();
    for (int i = 0; i < model.getTotalDuration(); i++) {
      noteList.addAll(model.getStartingNotesAtBeat(i));
    }

    for (int i = 0; i < noteList.size(); i++) {
      noteList.get(i).updateStartTime(noteList.get(i).getStartTime() + beatsShifted);
    }

    for (int i = 0; i < noteList.size(); i++) {
      this.addNote(noteList.get(i));
    }

  }

  @Override
  public void append(IMusicEditorModel model) throws IllegalArgumentException {
    try {
      Objects.requireNonNull(model);
    } catch (NullPointerException x) {
      throw new IllegalArgumentException("You must enter a valid model to be able to append");
    }

    List<Note> noteList = new ArrayList<>();
    for (int i = 0; i < model.getTotalDuration(); i++) {
      noteList.addAll(model.getStartingNotesAtBeat(i));
    }

    int maxBeats = this.getTotalDuration();
    for (int i = 0; i < noteList.size(); i++) {
      noteList.get(i).updateStartTime(noteList.get(i).getStartTime() + maxBeats);
    }

    for (int i = 0; i < noteList.size(); i++) {
      this.addNote(noteList.get(i));
    }
  }

  public static final class Builder implements CompositionBuilder<IMusicEditorModel> {
    private ArrayList<Note> nList = new ArrayList<Note>();
    private int tempo = 200000;

    @Override
    public IMusicEditorModel build() {
      return new MusicEditorModel(tempo, nList);
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    @Override
    public CompositionBuilder<IMusicEditorModel> addNote(int start, int end, int instrument,
                                                         int pitch, int volume) {
      if (start != end) {
        this.nList.add(new Note(Pitch.noteValToPitch(pitch), Pitch.noteValToOctave(pitch),
                instrument, volume, end - start, start));
      }
      return this;
    }
  }
}