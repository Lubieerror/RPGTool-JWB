package su.lubieerror.rpgtool.screen;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import su.lubieerror.rpgtool.ProgramVersion;
import su.lubieerror.rpgtool.Tools.Dice;
import su.lubieerror.rpgtool.Tools.StopWatch;

public class MainWindow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 9144683399426863535L;
	private JPanel contentPane;
	private JSpinner tFrom, tTo;
	private JButton bThrow, bStart, bStop, bPause, bClear;
	private JLabel sTime, sHistory, sProgram, sType, sThrow, sFrom, sTo, iThrow;
	private JRadioButton rOne, rTwo, rThree, rFour;
	JList<String> lHistory;
	private Dice mDice = new Dice(MainWindow.class.getResource("/res/mp3/dice.wav"),
			MainWindow.class.getResource("/res/mp3/swing.wav"));
	private String currentThrow;
	private int sys_x;
	private int sys_y;
	private StopWatch myStoper;
	
	private static JLabel iTime;

	/* TODO:
	 * -Fix linux fonts
	 * https://stackoverflow.com/questions/12998604/adding-fonts-to-swing-application-and-include-in-package#12998649
	 * 
	 * -better randoms using
	 *  +Mersenne Twister
	 *  +Mersenne Twister Light
	 *  +Xirshift
	 *  +Java TRNG 
	 * -Implement better sound system
	 * -reduce volume
	 * -add menubar (reduce volume, random system(?), look)
	 * -right side
	 */

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow()
	{
		setTitle("RPG Tool by lubieerror v. " + ProgramVersion.VERSION + "." + ProgramVersion.UNDERVERSION + "_"
				+ ProgramVersion.BUILD + " (J,SWB)[" + ProgramVersion.TYPEVERSION + "]");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/res/dice_ico.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 200, 795, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bStart = new JButton("Start");
		bStart.setBounds(10, 115, 80, 40);
		contentPane.add(bStart);
		bStart.addActionListener(this);

		bPause = new JButton("Pause");
		bPause.setEnabled(false);
		bPause.setBounds(93, 115, 80, 40);
		contentPane.add(bPause);
		bPause.addActionListener(this);

		bStop = new JButton("Stop");
		bStop.setEnabled(false);
		bStop.setBounds(175, 115, 80, 40);
		contentPane.add(bStop);
		bStop.addActionListener(this);

		bThrow = new JButton("Throw!");
		bThrow.setBounds(123, 325, 110, 70);
		bThrow.setFont(new Font(null, Font.PLAIN, 20));
		contentPane.add(bThrow);
		bThrow.addActionListener(this);

		bClear = new JButton("Clear!");
		bClear.setEnabled(false);
		bClear.setBounds(265, 345, 110, 50);
		contentPane.add(bClear);
		bClear.addActionListener(this);

		sTime = new JLabel("Game time:");
		sTime.setHorizontalAlignment(SwingConstants.CENTER);
		sTime.setBounds(10, 15, 235, 29);
		sTime.setFont(new Font(null, Font.PLAIN, 21));
		contentPane.add(sTime);

		iTime = new JLabel("CLICK_TO_START");
		iTime.setHorizontalAlignment(SwingConstants.CENTER);
		iTime.setBounds(10, 55, 235, 40);
		iTime.setFont(new Font("Monospace", Font.BOLD, 23));
		contentPane.add(iTime);

		sHistory = new JLabel("<html><center>Roll history:</center></html>");
		sHistory.setHorizontalAlignment(SwingConstants.CENTER);
		sHistory.setBounds(265, 0, 110, 52);
		sHistory.setFont(new Font("Serif", Font.BOLD, 17));
		contentPane.add(sHistory);

		sProgram = new JLabel("Throw dice generator");
		sProgram.setHorizontalAlignment(SwingConstants.CENTER);
		sProgram.setBounds(10, 166, 245, 40);
		sProgram.setFont(new Font("Dialog", Font.PLAIN, 22));
		contentPane.add(sProgram);

		sType = new JLabel("Dice type:");
		sType.setBounds(20, 205, 80, 25);
		sType.setFont(new Font(null, Font.PLAIN, 14));
		contentPane.add(sType);

		sThrow = new JLabel("<html><center>Thrown:</center></html>");
		sThrow.setHorizontalAlignment(SwingConstants.CENTER);
		sThrow.setBounds(123, 205, 105, 40);
		sThrow.setFont(new Font(null, Font.BOLD, 20));
		contentPane.add(sThrow);

		sFrom = new JLabel("Min: ");
		sFrom.setHorizontalAlignment(SwingConstants.RIGHT);
		sFrom.setBounds(0, 340, 45, 12);
		contentPane.add(sFrom);

		sTo = new JLabel("Max: ");
		sTo.setHorizontalAlignment(SwingConstants.RIGHT);
		sTo.setBounds(0, 375, 45, 12);
		contentPane.add(sTo);

		iThrow = new JLabel("null");
		iThrow.setHorizontalAlignment(SwingConstants.CENTER);
		iThrow.setBounds(123, 230, 110, 84);
		iThrow.setFont(new Font("Dialog", Font.PLAIN, 49));
		contentPane.add(iThrow);

		rOne = new JRadioButton("1-6");
		rOne.setFont(UIManager.getFont("Button.font"));
		rOne.setBounds(10, 230, 88, 23);
		contentPane.add(rOne);
		rOne.addActionListener(this);

		rTwo = new JRadioButton("1-10");
		rTwo.setFont(UIManager.getFont("Button.font"));
		rTwo.setBounds(10, 255, 88, 23);
		contentPane.add(rTwo);
		rTwo.addActionListener(this);

		rThree = new JRadioButton("1-100");
		rThree.setFont(UIManager.getFont("Button.font"));
		rThree.setSelected(true);
		sys_x = 1;
		sys_y = 100;
		rThree.setBounds(10, 280, 88, 23);
		contentPane.add(rThree);
		rThree.addActionListener(this);

		rFour = new JRadioButton("Custom");
		rFour.setFont(UIManager.getFont("Button.font"));
		rFour.setHorizontalAlignment(SwingConstants.LEFT);
		rFour.setBounds(10, 305, 88, 23);
		contentPane.add(rFour);
		rFour.addActionListener(this);

		tFrom = new JSpinner();
		tFrom.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		tFrom.setEnabled(false);
		tFrom.setBounds(45, 335, 53, 25);
		contentPane.add(tFrom);
		// tFrom.setColumns(10);

		tTo = new JSpinner();
		tTo.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		tTo.setEnabled(false);
		tTo.setBounds(45, 370, 53, 25);
		contentPane.add(tTo);
		// tTo.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(265, 55, 110, 279);
		contentPane.add(scrollPane);

		lHistory = new JList<>(mDice.getHistory());
		lHistory.setValueIsAdjusting(true);
		scrollPane.setViewportView(lHistory);
		lHistory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lHistory.setFont(new Font(null, Font.PLAIN, 35));

		JLabel lblToDo = new JLabel("TO DO!");
		lblToDo.setEnabled(false);
		lblToDo.setToolTipText(
				"This part is in planes. We'll bring it to live soon... Don't worry. We'll done it before HL3 will be released!");
		lblToDo.setBackground(new Color(0, 120, 215));
		lblToDo.setHorizontalAlignment(SwingConstants.CENTER);
		lblToDo.setFont(new Font("Monospace", Font.BOLD, 90));
		lblToDo.setBounds(385, 15, 394, 380);
		contentPane.add(lblToDo);

		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("ToolTip.background"));
		panel.setBounds(385, 15, 394, 385);
		contentPane.add(panel);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) lHistory.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.RIGHT);
		
		myStoper = new StopWatch(iTime);
	}

	public void actionPerformed(ActionEvent e)
	{
		Object my_source = e.getSource();
		if (my_source == bThrow)
		{
			if (rFour.isSelected())
				if ((int) tTo.getValue() > (int) tFrom.getValue())
				{
					sys_x = (int) tFrom.getValue();
					sys_y = (int) tTo.getValue();
				}
				else
				{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(this,
							"<html><center><b>Invalid values!<b><br>Check if your first number isn't bigger or equal second!</center></html>",
							"Invalid values!", JOptionPane.ERROR_MESSAGE);
					return;
				}
			if (currentThrow != null)
				mDice.addStringToHistory(currentThrow);
			currentThrow = mDice.completeDiceRoll(sys_x, sys_y, 1);
			iThrow.setText(currentThrow);
			bClear.setEnabled(true);
		}
		else if (my_source == bClear)
		{
			bClear.setEnabled(false);
			mDice.completeClear();
			currentThrow = null;
			iThrow.setText("null");
		}
		else if (my_source == bStart)
		{
			if(!bStop.isEnabled())
				iTime.setText("00:00.00");
			iTime.setFont(new Font(null, Font.PLAIN, 40));
			myStoper.start();
			bStart.setEnabled(false);
			bPause.setEnabled(true);
			bStop.setEnabled(true);
			iTime.setHorizontalAlignment(SwingConstants.CENTER);
		}
		else if (my_source == bPause)
		{
			myStoper.pause();
			bStart.setEnabled(true);
			bPause.setEnabled(false);
			bStop.setEnabled(true);
		}
		else if (my_source == bStop)
		{
			myStoper.stop();
			bStart.setEnabled(true);
			bPause.setEnabled(false);
			bStop.setEnabled(false);
			iTime.setText("CLICK_TO_START");
			iTime.setFont(new Font("Monospace", Font.BOLD, 23));
			iTime.setHorizontalAlignment(SwingConstants.LEFT);
		}
		else if (my_source == rOne)
		{
			sys_x = 1;
			sys_y = 6;
			rOne.setSelected(true);
			rTwo.setSelected(false);
			rThree.setSelected(false);
			rFour.setSelected(false);
			tFrom.setEnabled(false);
			tTo.setEnabled(false);
		}
		else if (my_source == rTwo)
		{
			sys_x = 1;
			sys_y = 10;
			rOne.setSelected(false);
			rTwo.setSelected(true);
			rThree.setSelected(false);
			rFour.setSelected(false);
			tFrom.setEnabled(false);
			tTo.setEnabled(false);
		}
		else if (my_source == rThree)
		{
			sys_x = 1;
			sys_y = 100;
			rOne.setSelected(false);
			rTwo.setSelected(false);
			rThree.setSelected(true);
			rFour.setSelected(false);
			tFrom.setEnabled(false);
			tTo.setEnabled(false);
		}
		else if (my_source == rFour)
		{
			rOne.setSelected(false);
			rTwo.setSelected(false);
			rThree.setSelected(false);
			rFour.setSelected(true);
			tFrom.setEnabled(true);
			tTo.setEnabled(true);
		}
	}
}
