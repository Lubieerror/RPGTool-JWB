package su.lubieerror.rpgtool.Tools;

import java.net.URL;
import java.util.Random;

import javax.swing.DefaultListModel;

import su.lubieerror.rpgtool.RandomSystems.MersenneTwister;
//import su.lubieerror.rpgtool.RandomSystems.MersenneTwister2;
import su.lubieerror.rpgtool.RandomSystems.MersenneTwisterFast;
import su.lubieerror.rpgtool.Sound.SoundPlayer;

public class Dice
{
	private Random rand;
	private MersenneTwister mt1;
//	private MersenneTwister2 mt2;
	private MersenneTwisterFast mtf;
	private SoundPlayer dicePlayer, clearPlayer;
	private DefaultListModel<String> history = new DefaultListModel<>();
	private String sR;
	private int iR;

	private URL soundPath, clearPath;

	public Dice(URL url, URL url2)
	{
		rand = new Random();
		mt1 = new MersenneTwister(rand.nextLong());
//		mt2 = new MersenneTwister2(rand.nextLong());
		mtf = new MersenneTwisterFast(rand.nextLong());
		soundPath = url;
		clearPath = url2;
		dicePlayer = new SoundPlayer(soundPath);
		clearPlayer = new SoundPlayer(clearPath);
	}

	private int myRandomNextInt(int min, int max, int ver)
	{
		max++;
		int range = max - min;
		rand.nextInt();
		rand.nextInt();
		rand.nextInt();
		if (ver == 0)
			return rand.nextInt(range) + min;
		else if (ver == 1)
			return mt1.nextInt(range) + min;
		else if (ver == 2)
			System.out.println("UNIMPLEMENTED! MTv2");
		else if (ver == 3)
			return mtf.nextInt(range) + min;
		else if (ver == 4)
			System.out.println("UNIMPLEMENTED! Xirshift");
		else if (ver == 5)
			System.out.println("UNIMPLEMENTED! TRNG");
		else System.out.println("ERROR! Bad version :(");
		rand.nextInt();
		rand.nextInt();
		rand.nextInt();
		return 0;
	}

	public void Roll(int x, int y, int ver)
	{
		iR = myRandomNextInt(x, y, ver);
		sR = Integer.toString(iR);
	}

	public String getResult()
	{
		return sR;
	}

	public void clear()
	{
		history.clear();
	}

	public void playDiceSound()
	{
		dicePlayer.stop();
		dicePlayer.play();
	}

	public void playClearSound()
	{
		clearPlayer.stop();
		clearPlayer.play();
	}

	public String completeDiceRoll(int x, int y, int ver)
	{
		playDiceSound();
		Roll(x, y, ver);
		return getResult();
	}

	public void completeClear()
	{
		playClearSound();
		clear();
	}

	public DefaultListModel<String> getHistory()
	{
		return history;
	}

	public void addToHistory(int i)
	{
		history.add(0, Integer.toString(i) + "  ");
	}

	public void addStringToHistory(String s)
	{
		history.add(0, s);
	}
}
