package su.lubieerror.rpgtool.Tools;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class StopWatch
{
	private int hPass;
	private int minPass;
	private int secPass;
	private boolean running, firstTime;
	private Timer myTimer;
	private TimerTask secTask;
	private JLabel cpLabel;
	
	public StopWatch(JLabel source)
	{
		cpLabel = source;
		running = false;
		firstTime = true;
		hPass = 0;
		minPass = 0;
		secPass = 0;
		myTimer = new Timer();
		secTask = new TimerTask()
		{
			public void run()
			{
				if(running)
				{
					secPass++;
					if (secPass >= 60)
						updateTime();
					setTime();
				}
			}
		};
	}

	private void updateTime()
	{
		if(secPass >= 60)
		{
			minPass++;
			secPass -= 60;
		}
		
		if (minPass >= 60)
		{
			hPass++;
			minPass -= 60;
		}
	}

	public void start()
	{
		running = true;
		if(firstTime)
		{
			myTimer.scheduleAtFixedRate(secTask, 1000, 1000);
			firstTime = false;
		}
	}

	public void pause()
	{
		running = false;
	}

	public void stop()
	{
		pause();
		hPass = 0;
		minPass = 0;
		secPass = 0;
	}
	
	private String getTime()
	{
		String time = null;
		if(hPass < 10)
			time = "0";
		time += hPass + ":";
		if(minPass < 10)
			time += "0";
		time += minPass + ".";
		if(secPass < 10)
			time += "0";
		time += secPass;
		return time;		
	}
	
	public void setTime()
	{
		cpLabel.setText(getTime());
	}
}
