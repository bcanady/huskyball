
import java.applet.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
public class Audioapp extends JApplet
{
    public class Sound // Holds one audio file

    {
  private AudioClip song; // Sound player
  private URL songPath; // Sound path
  Sound(String music)
  {
     try
     {
    songPath = new URL(getCodeBase(),music); // Get the Sound URL
    song = Applet.newAudioClip(songPath); // Load the Sound
     }
     catch(Exception e){} // Satisfy the catch
  }
  public void playSound()
  {
     song.loop(); // Play
  }
  public void stopSound()
  {
     song.stop(); // Stop
  }
  public void playSoundOnce()
  {
     song.play(); // Play only once
  }

    }
    public void init()
    {
  Sound testsong = new Sound("music.mid");
  testsong.playSound();

    }

}
