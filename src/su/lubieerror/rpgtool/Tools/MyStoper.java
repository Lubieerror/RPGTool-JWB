package su.lubieerror.rpgtool.Tools;

public class MyStoper implements Runnable
{
//	iTime.setText("00:00.00"); FORMAT!
	
	private int hour, min, sec;
	
	public MyStoper()
	{
	}

	@Override
	public void run()
	{
	}

	public synchronized void start()
	{
	}

	public synchronized void pause()
	{

	}

	public synchronized void stop()
	{

	}

	public void updateTime(int h, int m, int s)
	{
		hour = (h>= 0 && h < 24) ? h : 0;
		min = (m>= 0 && m < 60) ? m : 0;
		sec = (s>= 0 && s < 60) ? s : 0;
	}
}
