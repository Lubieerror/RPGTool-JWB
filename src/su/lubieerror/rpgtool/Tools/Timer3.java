package su.lubieerror.rpgtool.Tools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

public class Timer3 implements ActionListener, Runnable
{
	private long startTime;
	private final static java.text.SimpleDateFormat timerFormat = new java.text.SimpleDateFormat("mm : ss.SSS");
	private Thread updater;
	private boolean isRunning = false;
	private final Runnable displayUpdater = new Runnable()
	{
		public void run()
		{
			displayElapsedTime(System.currentTimeMillis() - Timer3.this.startTime);
		}
	};

	public void actionPerformed(ActionEvent ae)
	{
		if (isRunning)
		{
			long elapsed = System.currentTimeMillis() - startTime;
			isRunning = false;
			try
			{
				updater.join();
			} catch (InterruptedException ie)
			{
			}
			displayElapsedTime(elapsed);
		}
		else
		{
			startTime = System.currentTimeMillis();
			isRunning = true;
			updater = new Thread(this);
			updater.start();
		}
	}

	private String displayElapsedTime(long elapsedTime)
	{
		return timerFormat.format(new java.util.Date(elapsedTime));
	}

	public void run()
	{
		try
		{
			while (isRunning)
			{
				SwingUtilities.invokeAndWait(displayUpdater);
				Thread.sleep(50);
			}
		} catch (java.lang.reflect.InvocationTargetException ite)
		{
			ite.printStackTrace(System.err);
			// Should never happen!
		} catch (InterruptedException ie)
		{
		}
		// Ignore and return!
	}
}