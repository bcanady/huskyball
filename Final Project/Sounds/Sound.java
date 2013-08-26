import sun.audio.*;
import java.io.*;

public class Sound {

	public static void main(String[] args)
	{
		music();
	}
	
	public static void music()
	{
		AudioPlayer MGP = AudioPlayer.player;
		AudioStream BGM;
		AudioData MD;	
		ContinuousAudioDataStream loop = null;
		
		try{
		BGM = new AudioStream(new FileInputStream("music.mid"));
		MD = BGM.getData();
		loop = new ContinuousAudioDataStream(MD);
		}catch(IOException error){}
	
		MGP.start(loop);
	}
	
}
	
		