import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

public class DesiredGPAView extends JFrame implements ActionListener, ItemListener
{
	private static final long serialVersionUID = -2899769176308655521L;

	/*
	 * constants that are in the GUI
	 */
	private DesiredGPAController controller;
	private final JLabel lDesiredGPA, lGPALimit, lCurrentGPA, lTotalCred, lClass1, lClass1Cred;
	private final JTextField tDesiredGPA, tGPALimit, tClass1, tCurrentGPA, tTotalCred;
	private final JTextArea aOutcomes;
	private final JButton bAdd, bRem, bCalc;
	// private final JCheckBox chGradeKnown;
	private final JComboBox<String> coCredits;
	private final JPanel pDesiredGPA, pGPALimit, pCurrentGPA, pOutcomes, pClasses, pClass1, pCalc, pButtons;
	private final JScrollPane spOutcomes, spClasses;
	private final String[] creditNums = { "", "1", "2", "3", "4", "5" };
	private final String[] letterGrades = { "", "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "E" };
	private ArrayList<JPanel> classes = new ArrayList<JPanel>();
	private ArrayList<JTextField> classNameArray = new ArrayList<JTextField>();

	/*
	 * following are needed for calculations
	 */
	private ArrayList<JComboBox<String>> classCredits = new ArrayList<JComboBox<String>>();
	private ArrayList<JComboBox<String>> ubGrades = new ArrayList<JComboBox<String>>();
	private ArrayList<JComboBox<String>> lbGrades = new ArrayList<JComboBox<String>>();
	private int[] upperBounds;
	private int[] lowerBounds;

	public ArrayList<JComboBox<String>> getClassCredits()
	{
		return classCredits;
	}

	public ArrayList<JTextField> getClassNameArray()
	{
		return classNameArray;
	}

	public JTextField gettGPALimit()
	{
		return tGPALimit;
	}

	public int[] getUpperBounds()
	{
		return upperBounds;
	}

	public int[] getLowerBounds()
	{
		return lowerBounds;
	}

	public JTextField gettDesiredGPA()
	{
		return tDesiredGPA;
	}

	public JTextField gettCurrentGPA()
	{
		return tCurrentGPA;
	}

	public JTextField gettTotalCred()
	{
		return tTotalCred;
	}

	public DesiredGPAView()
	{
		// titles the window
		super("Desired GPA");

		/*
		 * adds Desired GPA label and a text field to the first panel that will
		 * be added to the JFrame
		 */
		pDesiredGPA = new JPanel(new FlowLayout());
		lDesiredGPA = new JLabel("Desired GPA: ");
		tDesiredGPA = new JTextField("", 5);
		pDesiredGPA.add(lDesiredGPA);
		pDesiredGPA.add(tDesiredGPA);

		pGPALimit = new JPanel(new FlowLayout());
		lGPALimit = new JLabel("Limit on Possible GPAs: ");
		tGPALimit = new JTextField("", 5);
		pGPALimit.add(lGPALimit);
		pGPALimit.add(tGPALimit);

		/*
		 * will be the second panel added to the JFrame adds current cumulative
		 * and total credits labels and their respected text fields.
		 */
		pCurrentGPA = new JPanel(new FlowLayout());
		lCurrentGPA = new JLabel("Current Cumulative GPA: ");
		lTotalCred = new JLabel("Total Credits Taken: ");
		tCurrentGPA = new JTextField("", 5);
		tTotalCred = new JTextField("", 5);
		pCurrentGPA.add(lCurrentGPA);
		pCurrentGPA.add(tCurrentGPA);
		pCurrentGPA.add(lTotalCred);
		pCurrentGPA.add(tTotalCred);

		/*
		 * third panel added creates a scrollable text field
		 */
		pOutcomes = new JPanel(new GridLayout());
		aOutcomes = new JTextArea(20, 9);
		aOutcomes.setEditable(false);
		aOutcomes.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret) aOutcomes.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		pOutcomes.add(aOutcomes);
		spOutcomes = new JScrollPane(aOutcomes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		/*
		 * fourth panel that will be added to the JFrame creates a scrollable
		 * panel that will update when the add class button is hit
		 */
		pClasses = new JPanel();
		pClasses.setBorder(BorderFactory.createLineBorder(Color.black));
		pClasses.setLayout(new BoxLayout(pClasses, BoxLayout.PAGE_AXIS));
		spClasses = new JScrollPane(pClasses, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		/*
		 * GUI will always at least have one class so it is a constant.
		 * following adds class name label, credits, Grade Known check box,
		 * upper bound, lower bound and their respective text fields ands combo
		 * boxes.
		 */
		pClass1 = new JPanel();
		pClass1.setLayout(new BoxLayout(pClass1, BoxLayout.PAGE_AXIS));
		pClass1.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel pClass1Top = new JPanel(new FlowLayout());
		lClass1 = new JLabel("Class 1 Name: ");
		tClass1 = new JTextField("", 7);
		classNameArray.add(tClass1);
		lClass1Cred = new JLabel("Credits:");
		coCredits = new JComboBox<String>(creditNums);
		// adds class one credit combo box to the array.
		classCredits.add(coCredits);
		coCredits.addItemListener(this);

		// chGradeKnown = new JCheckBox("Grade Known");
		JPanel bounds = new JPanel();
		bounds.setLayout(new BoxLayout(bounds, BoxLayout.PAGE_AXIS));
		JPanel boundsUB = new JPanel(new FlowLayout());
		JLabel lUpper = new JLabel("Upper Bound (inclusive): ");
		JComboBox<String> coUpper = new JComboBox<String>(letterGrades);
		// adds class one credit combo box to the array.
		ubGrades.add(coUpper);
		coUpper.addItemListener(this);
		boundsUB.add(lUpper);
		boundsUB.add(coUpper);
		JPanel boundsLB = new JPanel(new FlowLayout());
		JLabel lLower = new JLabel("Lower Bound (inclusive): ");
		JComboBox<String> coLower = new JComboBox<String>(letterGrades);
		// adds class one credit combo box to the array.
		lbGrades.add(coLower);
		coLower.addItemListener(this);
		boundsLB.add(lLower);
		boundsLB.add(coLower);
		bounds.add(boundsUB);
		bounds.add(boundsLB);

		/*
		 * puts the panel together and is added to the scrollable class panel.
		 */
		pClass1Top.add(lClass1);
		pClass1Top.add(tClass1);
		pClass1Top.add(lClass1Cred);
		pClass1Top.add(coCredits);
		// pClass1Top.add(chGradeKnown);
		pClass1.add(pClass1Top);
		pClass1.add(bounds);
		pClasses.add(pClass1);
		classes.add(pClass1);

		/*
		 * fifth panel that will be added to the Jframe creates the "add class"
		 * and "remove class" buttons
		 */
		pButtons = new JPanel(new FlowLayout());
		bAdd = new JButton("Add Class");
		bRem = new JButton("Remove Class");
		bRem.setEnabled(false);
		pButtons.add(bAdd);
		pButtons.add(bRem);

		/*
		 * last constant panel to be added creates the calculate button
		 */
		pCalc = new JPanel(new GridLayout());
		bCalc = new JButton("Calculate");
		bCalc.setEnabled(false);
		pCalc.add(bCalc);

		/*
		 * creates action listeners for all buttons
		 */
		this.bAdd.addActionListener(this);
		this.bRem.addActionListener(this);
		this.bCalc.addActionListener(this);

		/*
		 * puts together all constant panels created prior and gives properties
		 * to the window.
		 */
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		this.add(pDesiredGPA);
		this.add(pGPALimit);
		this.add(pCurrentGPA);
		this.add(spOutcomes);
		this.add(spClasses);
		this.add(pButtons);
		this.add(pCalc);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
	}

	public void registerObserver(DesiredGPAController controller)
	{
		this.controller = controller;
	}

	public void updateAddClass()
	{
		JPanel classNum = new JPanel();
		classNum.setLayout(new BoxLayout(classNum, BoxLayout.PAGE_AXIS));
		classNum.setBorder(BorderFactory.createLineBorder(Color.black));
		JPanel pClassNumTop = new JPanel(new FlowLayout());
		JLabel lClassNum = new JLabel("Class " + (classes.size() + 1) + " Name: ");
		JTextField tClassNum = new JTextField("", 7);
		classNameArray.add(tClassNum);
		JLabel lClassNumCred = new JLabel("Credits:");
		JComboBox<String> coCredits = new JComboBox<String>(creditNums);
		// adds the new classes credits to the combobox array.
		classCredits.add(coCredits);
		coCredits.addItemListener(this);
		JPanel bounds = new JPanel();
		bounds.setLayout(new BoxLayout(bounds, BoxLayout.PAGE_AXIS));
		JPanel boundsUB = new JPanel(new FlowLayout());
		JLabel lUpper = new JLabel("Upper Bound (inclusive): ");
		JComboBox<String> coUpper = new JComboBox<String>(letterGrades);
		ubGrades.add(coUpper);
		coUpper.addItemListener(this);
		boundsUB.add(lUpper);
		boundsUB.add(coUpper);
		JPanel boundsLB = new JPanel(new FlowLayout());
		JLabel lLower = new JLabel("Lower Bound (inclusive): ");
		JComboBox<String> coLower = new JComboBox<String>(letterGrades);
		lbGrades.add(coLower);
		coLower.addItemListener(this);
		boundsLB.add(lLower);
		boundsLB.add(coLower);
		bounds.add(boundsUB);
		bounds.add(boundsLB);
		pClassNumTop.add(lClassNum);
		pClassNumTop.add(tClassNum);
		pClassNumTop.add(lClassNumCred);
		pClassNumTop.add(coCredits);
		classNum.add(pClassNumTop);
		classNum.add(bounds);
		pClasses.add(classNum);
		classes.add(classNum);
		makeBoundArray();

		/*
		 * following makes it so user can't enter "" for credits and hit
		 * calculate
		 */
		int i = 0;
		for (JComboBox<String> classCredits : classCredits)
		{
			if (!classCredits.getSelectedItem().equals(""))
			{
				i++;
			}
		}

		if (i == classCredits.size())
		{
			bCalc.setEnabled(true);
		} else
		{
			bCalc.setEnabled(false);
		}

		/*
		 * disables remove class button if there is only one class
		 */
		if (pClasses.getComponentCount() > 1)
		{
			bRem.setEnabled(true);
		}

		if (pClasses.getComponentCount() >= 5)
		{
			bAdd.setEnabled(false);
		}

		/*
		 * resizes windows up to 3 classes then stops afterwards.
		 */
		if (pClasses.getComponentCount() <= 3)
		{
			this.setVisible(true);
		}
	}

	public void updateRemClass()
	{
		pClasses.remove(pClasses.getComponentCount() - 1);

		if (pClasses.getComponentCount() <= 1)
		{
			bRem.setEnabled(false);
		}

		pClasses.validate();
		pClasses.repaint();

		if (pClasses.getComponentCount() <= 3)
		{
			this.setVisible(true);
		}

		classes.remove(classes.size() - 1);
		ubGrades.remove(ubGrades.size() - 1);
		lbGrades.remove(lbGrades.size() - 1);
		makeBoundArray();
		// fixes the bug where if you remove a class that has a credit selected
		// the calculate button won't be disabled.
		classCredits.remove(classCredits.size() - 1);
		classNameArray.remove(classNameArray.size() - 1);

		int i = 0;
		for (JComboBox<String> classCredits : classCredits)
		{
			if (!classCredits.getSelectedItem().equals(""))
			{
				i++;
			}
		}

		if (i != 0)
		{
			bCalc.setEnabled(true);
		} else
		{
			bCalc.setEnabled(false);
		}

		if (pClasses.getComponentCount() < 5)
		{
			bAdd.setEnabled(true);
		}
	}

	public void updateCalc(StringBuilder s)
	{
		aOutcomes.setText(s.toString());
	}

	public void updateCalc(String s)
	{
		aOutcomes.setText(s.toString());
	}

	public void makeBoundArray()
	{
		upperBounds = new int[ubGrades.size()];
		for (JComboBox<String> upper : ubGrades)
		{
			if (upper.getSelectedItem().toString() == "A" || upper.getSelectedItem().toString() == "")
			{
				upperBounds[ubGrades.indexOf(upper)] = 0;
			} else if (upper.getSelectedItem().toString() == "A-")
			{
				upperBounds[ubGrades.indexOf(upper)] = 1;
			} else if (upper.getSelectedItem().toString() == "B+")
			{
				upperBounds[ubGrades.indexOf(upper)] = 2;
			} else if (upper.getSelectedItem().toString() == "B")
			{
				upperBounds[ubGrades.indexOf(upper)] = 3;
			} else if (upper.getSelectedItem().toString() == "B-")
			{
				upperBounds[ubGrades.indexOf(upper)] = 4;
			} else if (upper.getSelectedItem().toString() == "C+")
			{
				upperBounds[ubGrades.indexOf(upper)] = 5;
			} else if (upper.getSelectedItem().toString() == "C")
			{
				upperBounds[ubGrades.indexOf(upper)] = 6;
			} else if (upper.getSelectedItem().toString() == "C-")
			{
				upperBounds[ubGrades.indexOf(upper)] = 7;
			} else if (upper.getSelectedItem().toString() == "D+")
			{
				upperBounds[ubGrades.indexOf(upper)] = 8;
			} else if (upper.getSelectedItem().toString() == "D")
			{
				upperBounds[ubGrades.indexOf(upper)] = 9;
			} else
			{
				upperBounds[ubGrades.indexOf(upper)] = 10;
			}
		}

		lowerBounds = new int[lbGrades.size()];
		for (JComboBox<String> lower : lbGrades)
		{
			if (lower.getSelectedItem().toString() == "A")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 0;
			} else if (lower.getSelectedItem().toString() == "A-")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 1;
			} else if (lower.getSelectedItem().toString() == "B+")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 2;
			} else if (lower.getSelectedItem().toString() == "B")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 3;
			} else if (lower.getSelectedItem().toString() == "B-")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 4;
			} else if (lower.getSelectedItem().toString() == "C+")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 5;
			} else if (lower.getSelectedItem().toString() == "C")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 6;
			} else if (lower.getSelectedItem().toString() == "C-")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 7;
			} else if (lower.getSelectedItem().toString() == "D+")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 8;
			} else if (lower.getSelectedItem().toString() == "D")
			{
				lowerBounds[lbGrades.indexOf(lower)] = 9;
			} else
			{
				lowerBounds[lbGrades.indexOf(lower)] = 10;
			}
		}

		int checkBounds = 0;
		for (int i = 0; i < lowerBounds.length; i++)
		{
			if (lowerBounds[i] - upperBounds[i] >= 0)
			{
				checkBounds++;
			}
		}

		if (checkBounds < lowerBounds.length)
		{
			bCalc.setEnabled(false);
			updateCalc("error in bounds");
		} else
		{
			bCalc.setEnabled(true);
			updateCalc("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		if (source == this.bAdd)
		{
			this.controller.processAddClass();
		} else if (source == this.bRem)
		{
			this.controller.processRemClass();
		} else if (source == this.bCalc)
		{
			makeBoundArray();
			this.controller.processCalculate();
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		Object source = e.getSource();
		if (classCredits.contains(source))
		{
			int i = 0;
			for (JComboBox<String> classCredits : classCredits)
			{
				if (!classCredits.getSelectedItem().equals(""))
				{
					i++;
				}
			}

			if (i == classCredits.size())
			{
				bCalc.setEnabled(true);
			} else
			{
				bCalc.setEnabled(false);
			}
		} else if (ubGrades.contains(source) || lbGrades.contains(source))
		{
			makeBoundArray();
		}
	}
}
