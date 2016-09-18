package su.lubieerror.rpgtool.Sound;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer
{
	URL soundFile;
	AudioClip player;

	public SoundPlayer(URL soundPath)
	{
		// System.out.println("soundFile Created");
		soundFile = soundPath;
		player = Applet.newAudioClip(soundFile);
		// System.out.println("player created");
	}

	public void play()
	{
		player.play();
	}

	public void stop()
	{
		try
		{
			player.stop();
			// System.out.println("player.stop()");
		} catch (Exception e)
		{
			System.out.println("ERROR! in player.stop()");
			e.printStackTrace();
		}
	}

	public void playLoop()
	{
		new Thread()
		{
			public void run()
			{
				while (true)
					try
					{
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(soundFile));
						clip.start();
						System.out.print(clip.getMicrosecondLength());
						Thread.sleep(clip.getMicrosecondLength() / 1000);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
			}
		}.start();
	}
}
