package su.lubieerror.rpgtool.Tools;

import java.util.Timer;
import java.util.TimerTask;

public class Timer2
{
	private int hPass;
	private int minPass;
	private int secPass;
	private Timer myTimer;
	private TimerTask secTask;
	
	public Timer2()
	{
		hPass = 0;
		minPass = 0;
		secPass = 0;
		myTimer = new Timer();
		secTask = new TimerTask()
		{
			public void run()
			{
				secPass++;
				if (secPass >= 60)
					updateTime();
			}
		};
	}
	//	public boolean running = true;

	//	TimerTask hTask = new TimerTask()
	//	{
	//		public void run()
	//		{
	//			if (hPass < 10)
	//				System.out.println("H: 0" + hPass);
	//			else System.out.println("H: " + hPass);
	//			hPass++;
	//			if (hPass >= 60)
	//				hPass = 0;
	//		}
	//	};
	//
	//	TimerTask minTask = new TimerTask()
	//	{
	//		public void run()
	//		{
	//			if (minPass < 10)
	//				System.out.println("H: 0" + minPass);
	//			else System.out.println("H: " + minPass);
	//			minPass++;
	//			if (minPass >= 60)
	//				minPass = 0;
	//		}
	//	};


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
		//		myTimer.scheduleAtFixedRate(hTask, 1000 * 60 * 60, 1000 * 60 * 60);
		//		myTimer.scheduleAtFixedRate(minTask, 1000 * 60, 1000 * 60);
		myTimer.scheduleAtFixedRate(secTask, 1000, 1000);
	}

	public void pause()
	{
		myTimer.cancel();
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
	
	private void setTime()
	{
	}

}
