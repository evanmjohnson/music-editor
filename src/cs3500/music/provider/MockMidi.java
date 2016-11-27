package cs3500.music.provider;

import java.util.List;

import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.VoiceStatus;
import javax.sound.midi.Soundbank;
import javax.sound.midi.Instrument;
import javax.sound.midi.Patch;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 * This class represents a mock for a Midi Device.
 */
public class MockMidi implements Synthesizer {

  @Override
  public int getMaxPolyphony() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public long getLatency() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public MidiChannel[] getChannels() {
    MidiChannel[] result = null;
    try {
      result = MidiSystem.getSynthesizer().getChannels();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public VoiceStatus[] getVoiceStatus() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public boolean isSoundbankSupported(Soundbank soundbank) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public boolean loadInstrument(Instrument instrument) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public void unloadInstrument(Instrument instrument) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public boolean remapInstrument(Instrument from, Instrument to)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public Soundbank getDefaultSoundbank() {
    Soundbank result = null;
    try {
      result = MidiSystem.getSynthesizer().getDefaultSoundbank();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public Instrument[] getAvailableInstruments() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public Instrument[] getLoadedInstruments() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public boolean loadAllInstruments(Soundbank soundbank) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public void unloadAllInstruments(Soundbank soundbank) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public boolean loadInstruments(Soundbank soundbank, Patch[] patchList)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public void unloadInstruments(Soundbank soundbank, Patch[] patchList)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public Info getDeviceInfo() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public void open() throws MidiUnavailableException {
    MidiView.RESULT.append("Receiver Opened\n");
  }

  @Override
  public void close() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public boolean isOpen() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public long getMicrosecondPosition() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public int getMaxReceivers() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public int getMaxTransmitters() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new MockMidiReceiver();
  }

  @Override
  public List<Receiver> getReceivers() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Not allowed");
  }
}
