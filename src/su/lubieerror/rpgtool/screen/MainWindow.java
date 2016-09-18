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

import su.lubieerror.rpgtool.Tools.Dice;

public class MainWindow extends JFrame implements ActionListener
{
	private static final String typeVersion = "Alpha";
	private static final long serialVersionUID = 3;
	private static final int underVersion = 0;
	private static final int build = 17;
	private JPanel contentPane;
	private JSpinner tFrom, tTo;
	private JButton bThrow, bStart, bStop, bPause, bClear;
	private JLabel sTime, iTime, sHistory, sProgram, sType, sThrow, sFrom, sTo, iThrow;
	private JRadioButton rOne, rTwo, rThree, rFour;
	JList<String> lHistory;
	private Dice mDice = new Dice(MainWindow.class.getResource("/res/mp3/dice.wav"),
			MainWindow.class.getResource("/res/mp3/swing.wav"));
	private String currentThrow;
	private int sys_x;
	private int sys_y;
	
	/* TODO:
	 * -right side
	 * -stoper/timer
	 * -better randoms using
	 *  +Mersenne Twister
	 *  +Mersenne Twister Light
	 *  +Xirshift
	 *  +Java TRNG 
	 * -Implement better sound system
	 * -reduce volume
	 * -add menubar (reduce volume, random system(?), look)
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
		setTitle("RPG Tool by lubieerror v. <<" + typeVersion + ">> " + serialVersionUID + "." + underVersion + "."
				+ build + " (J,SWB)");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/res/dice_ico.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 200, 795, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		bStart = new JButton("Start");
		bStart.setBounds(27, 115, 75, 40);
		contentPane.add(bStart);
		bStart.addActionListener(this);

		bPause = new JButton("Pauza");
		bPause.setEnabled(false);
		bPause.setBounds(107, 115, 75, 40);
		contentPane.add(bPause);
		bPause.addActionListener(this);

		bStop = new JButton("Stop");
		bStop.setEnabled(false);
		bStop.setBounds(187, 115, 75, 40);
		contentPane.add(bStop);
		bStop.addActionListener(this);

		int xpStart = bStart.getX();

		bThrow = new JButton("Losuj!");
		// bThrow.setBounds((this.getHeight() / 2) - 75, 325, 110, 70);
		bThrow.setBounds((this.getHeight() / 2) - 75, 325, 110, 70);
		bThrow.setFont(new Font(null, Font.PLAIN, 20));
		contentPane.add(bThrow);
		bThrow.addActionListener(this);

		bClear = new JButton("Wyczyœæ!");
		bClear.setEnabled(false);
		bClear.setBounds((this.getHeight() / 2) + 70, 345, 110, 50);
		contentPane.add(bClear);
		bClear.addActionListener(this);

		sTime = new JLabel("Czas gry:");
		sTime.setBounds(67, 15, 110, 25);
		sTime.setFont(new Font(null, Font.PLAIN, 21));
		contentPane.add(sTime);

		iTime = new JLabel("CLICK_TO_START");
		iTime.setBounds(xpStart + 10, 55, 210, 40);
		iTime.setFont(new Font(null, Font.BOLD, 23));
		contentPane.add(iTime);

		sHistory = new JLabel("<html><center>Historia rzutów:</center></html>");
		sHistory.setHorizontalAlignment(SwingConstants.CENTER);
		sHistory.setBounds(295, 19, 105, 40);
		sHistory.setFont(new Font(null, Font.BOLD, 16));
		contentPane.add(sHistory);

		sProgram = new JLabel("Generator rzutu koœci¹");
		sProgram.setBounds(xpStart + 15, 175, 200, 25);
		sProgram.setFont(new Font(null, Font.PLAIN, 18));
		contentPane.add(sProgram);

		sType = new JLabel("Typ koœci:");
		sType.setBounds(27, 200, 75, 25);
		sType.setFont(new Font(null, Font.PLAIN, 14));
		contentPane.add(sType);

		sThrow = new JLabel("Wyrzucono:");
		sThrow.setBounds(150, 200, 105, 25);
		sThrow.setFont(new Font(null, Font.PLAIN, 18));
		contentPane.add(sThrow);

		sFrom = new JLabel("Od: ");
		sFrom.setBounds(15, 340, 26, 12);
		contentPane.add(sFrom);

		sTo = new JLabel("Do: ");
		sTo.setBounds(15, 375, 26, 12);
		contentPane.add(sTo);

		iThrow = new JLabel("null");
		iThrow.setHorizontalAlignment(SwingConstants.CENTER);
		iThrow.setBounds(145, 236, 110, 78);
		iThrow.setFont(new Font("Dialog", Font.PLAIN, 45));
		contentPane.add(iThrow);

		rOne = new JRadioButton("1-6");
		rOne.setBounds(27, 230, 75, 23);
		contentPane.add(rOne);
		rOne.addActionListener(this);

		rTwo = new JRadioButton("1-10");
		rTwo.setBounds(27, 255, 75, 23);
		contentPane.add(rTwo);
		rTwo.addActionListener(this);

		rThree = new JRadioButton("1-100");
		rThree.setSelected(true);
		sys_x = 1;
		sys_y = 100;
		rThree.setBounds(27, 280, 75, 23);
		contentPane.add(rThree);
		rThree.addActionListener(this);

		rFour = new JRadioButton("Custom");
		rFour.setBounds(27, 305, 75, 23);
		contentPane.add(rFour);
		rFour.addActionListener(this);

		tFrom = new JSpinner();
		tFrom.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		tFrom.setEnabled(false);
		tFrom.setBounds(37, 335, 65, 25);
		contentPane.add(tFrom);
		// tFrom.setColumns(10);

		tTo = new JSpinner();
		tTo.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		tTo.setEnabled(false);
		tTo.setBounds(37, 370, 65, 25);
		contentPane.add(tTo);
		// tTo.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(295, 74, 105, 260);
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
		lblToDo.setFont(new Font("Tahoma", Font.BOLD, 99));
		lblToDo.setBounds(410, 15, 369, 380);
		contentPane.add(lblToDo);

		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("ToolTip.background"));
		panel.setBounds(410, 15, 369, 385);
		contentPane.add(panel);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) lHistory.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.RIGHT);
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
							"<html><center>Invalid values!<br>Check if your first number isn't bigger than second!</center></html>",
							"Invalid values!", JOptionPane.ERROR_MESSAGE);
					return;
				}
			if (currentThrow != null)
				mDice.addStringToHistory(currentThrow);
			currentThrow = mDice.completeDiceRoll(sys_x, sys_y);
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
			bStart.setEnabled(false);
			bPause.setEnabled(true);
			bStop.setEnabled(true);
			iTime.setText("00:00.00");
			iTime.setFont(new Font(null, Font.PLAIN, 40));
			iTime.setHorizontalAlignment(SwingConstants.CENTER);
		}
		else if (my_source == bPause)
		{
			bStart.setEnabled(true);
			bPause.setEnabled(false);
			bStop.setEnabled(true);
		}
		else if (my_source == bStop)
		{
			bStart.setEnabled(true);
			bPause.setEnabled(false);
			bStop.setEnabled(false);
			iTime.setText("CLICK_TO_START");
			iTime.setFont(new Font(null, Font.BOLD, 23));
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
