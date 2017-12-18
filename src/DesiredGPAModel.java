import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextField;

public class DesiredGPAModel
{

	private final String[] letterGrades = { "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "E" };
	private ArrayList<ArrayList<String>> includeArray = new ArrayList<ArrayList<String>>();
	private ArrayList<Double> cumaGPAArray = new ArrayList<Double>();
	private ArrayList<Double> semGPAArray = new ArrayList<Double>();
	private ArrayList<String> classNames = new ArrayList<String>();
	private StringBuilder outcomes;

	public DesiredGPAModel()
	{
		this.outcomes = new StringBuilder();
	}

	private static BigDecimal truncateDecimal(double x, int numberofDecimals)
	{
		if (x > 0)
		{
			return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
		}
		else
		{
			return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
		}
	}

	public StringBuilder outcomes()
	{
		return this.outcomes;
	}

	boolean isDouble(String str)
	{
		try
		{
			Double.parseDouble(str);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	boolean isInteger(String str)
	{
		try
		{
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	public ArrayList<String> boundArray(int upper, int lower)
	{
		ArrayList<String> boundedArray = new ArrayList<String>();
		for (int i = upper; i <= lower; i++)
		{
			boundedArray.add(letterGrades[i]);
		}
		return boundedArray;
	}

	public void seeLetterArray(
			int[] upper,
			int[] lower,
			ArrayList<JTextField> classNameArray,
			ArrayList<JComboBox<String>> Credits,
			JTextField limitGPA,
			JTextField desiredGPA,
			JTextField currentCuma,
			JTextField credTaken)
	{
		includeArray.clear();
		cumaGPAArray.clear();
		semGPAArray.clear();
		classNames.clear();
		double cDesiredGPA;
		double cCurrentCuma;
		int cCredTaken;
		double cLimitGPA;
		this.outcomes = new StringBuilder();

		for (int z = 0; z < classNameArray.size(); z++)
		{
			String className = classNameArray.get(z).getText();
			if (className.equals(""))
			{
				className = "Class " + (z + 1) + ": ";
			}
			else
			{
				className = classNameArray.get(z).getText() + ": ";
			}
			classNames.add(className);
		}

		if (desiredGPA.getText().equals(""))
		{
			cDesiredGPA = 0.0;
		}
		else
		{
			cDesiredGPA = Double.parseDouble(desiredGPA.getText());
		}

		if (limitGPA.getText().equals(""))
		{
			cLimitGPA = 4.0;
		}
		else
		{
			cLimitGPA = Double.parseDouble(limitGPA.getText());
		}

		if (currentCuma.getText().equals(""))
		{
			cCurrentCuma = 0.0;
		}
		else
		{
			cCurrentCuma = Double.parseDouble(currentCuma.getText());
		}

		if (credTaken.getText().equals(""))
		{
			cCredTaken = 0;
		}
		else
		{
			cCredTaken = Integer.parseInt(credTaken.getText());
		}

		ArrayList<ArrayList<String>> boundLetterArray = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < lower.length; i++)
		{
			boundLetterArray.add(boundArray(upper[i], lower[i]));
		}
		combinations(
				boundLetterArray,
				boundLetterArray.size(),
				Credits,
				cLimitGPA,
				cDesiredGPA,
				cCurrentCuma,
				cCredTaken);

		if (includeArray.isEmpty())
		{
			this.outcomes.append("Sorry not possible!");
		}
		else
		{
			this.outcomes.append(
					"THERE ARE " + includeArray.size() + " POSSIBILITIES!"
							+ "\n______________________________________________________________\n");
			for (int i = 0; i < includeArray.size(); i++)
			{
				for (int j = 0; j < classNames.size(); j++)
				{
					this.outcomes.append(classNames.get(j) + includeArray.get(i).get(j) + "\n");
				}
				double hypoCuma = cumaGPAArray.get(i);
				double hypoSem = semGPAArray.get(i);
				this.outcomes.append("\nHypothetical Semester GPA: " + truncateDecimal(hypoSem, 3) + "\n");
				this.outcomes.append(
						"Hypothetical Cumalative GPA: " + truncateDecimal(hypoCuma, 3)
								+ "\n______________________________________________________________\n");
			}
		}
	}

	public void combinations(
			ArrayList<ArrayList<String>> boundedArrays,
			int size,
			ArrayList<JComboBox<String>> Credits,
			double limitGPA,
			double desiredGPA,
			double currentCuma,
			int credTaken)
	{
		if (size == 1)
		{
			for (ArrayList<String> gradeArray : boundedArrays)
			{
				for (String grade : gradeArray)
				{
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(grade);
					calculateGPA(temp, Credits, limitGPA, desiredGPA, currentCuma, credTaken);
				}
			}
		}
		else if (size == 2)
		{
			for (String grade0 : boundedArrays.get(0))
			{
				for (String grade1 : boundedArrays.get(1))
				{
					ArrayList<String> temp = new ArrayList<String>();
					temp.add(grade0);
					temp.add(grade1);
					calculateGPA(temp, Credits, limitGPA, desiredGPA, currentCuma, credTaken);
				}
			}
		}
		else if (size == 3)
		{
			for (String grade0 : boundedArrays.get(0))
			{
				for (String grade1 : boundedArrays.get(1))
				{
					for (String grade2 : boundedArrays.get(2))
					{
						ArrayList<String> temp = new ArrayList<String>();
						temp.add(grade0);
						temp.add(grade1);
						temp.add(grade2);
						calculateGPA(temp, Credits, limitGPA, desiredGPA, currentCuma, credTaken);
					}
				}
			}
		}
		else if (size == 4)
		{
			for (String grade0 : boundedArrays.get(0))
			{
				for (String grade1 : boundedArrays.get(1))
				{
					for (String grade2 : boundedArrays.get(2))
					{
						for (String grade3 : boundedArrays.get(3))
						{
							ArrayList<String> temp = new ArrayList<String>();
							temp.add(grade0);
							temp.add(grade1);
							temp.add(grade2);
							temp.add(grade3);
							calculateGPA(temp, Credits, limitGPA, desiredGPA, currentCuma, credTaken);
						}
					}
				}
			}
		}
		else if (size == 5)
		{
			for (String grade0 : boundedArrays.get(0))
			{
				for (String grade1 : boundedArrays.get(1))
				{
					for (String grade2 : boundedArrays.get(2))
					{
						for (String grade3 : boundedArrays.get(3))
						{
							for (String grade4 : boundedArrays.get(4))
							{
								ArrayList<String> temp = new ArrayList<String>();
								temp.add(grade0);
								temp.add(grade1);
								temp.add(grade2);
								temp.add(grade3);
								temp.add(grade4);
								calculateGPA(temp, Credits, limitGPA, desiredGPA, currentCuma, credTaken);
							}
						}
					}
				}
			}
		}
	}

	private double calculateGPA(
			ArrayList<String> temp,
			ArrayList<JComboBox<String>> Credits,
			double limitGPA,
			double cDesiredCuma,
			double cCurrentCuma,
			int cTotalCred)
	{
		ArrayList<Double> actualCredArray = new ArrayList<Double>();
		double cumaGPA;
		double semCredTot = 0;
		ArrayList<Integer> creditHours = new ArrayList<Integer>();
		for (JComboBox<String> cred : Credits)
		{
			Integer credNum = 0;
			String credString = (String) cred.getSelectedItem();
			if (credString.equals(""))
			{
				credNum = 0;
				creditHours.add(credNum);
			}
			else if (isDouble(credString))
			{
				credNum = Integer.parseInt(credString);
				semCredTot += credNum;
				creditHours.add(credNum);
			}
		}
		for (String grade : temp)
		{
			double letterAsDouble;
			if (grade.equals(letterGrades[0]))
			{
				letterAsDouble = 4.0;
			}
			else if (grade.equals(letterGrades[1]))
			{
				letterAsDouble = 3.7;
			}
			else if (grade.equals(letterGrades[2]))
			{
				letterAsDouble = 3.3;
			}
			else if (grade.equals(letterGrades[3]))
			{
				letterAsDouble = 3.0;
			}
			else if (grade.equals(letterGrades[4]))
			{
				letterAsDouble = 2.7;
			}
			else if (grade.equals(letterGrades[5]))
			{
				letterAsDouble = 2.3;
			}
			else if (grade.equals(letterGrades[6]))
			{
				letterAsDouble = 2.0;
			}
			else if (grade.equals(letterGrades[7]))
			{
				letterAsDouble = 1.7;
			}
			else if (grade.equals(letterGrades[8]))
			{
				letterAsDouble = 1.3;
			}
			else if (grade.equals(letterGrades[9]))
			{
				letterAsDouble = 1.0;
			}
			else
			{
				letterAsDouble = 0.0;
			}
			actualCredArray.add(letterAsDouble);
		}

		double semGPA;
		double acquiredCredits = 0;
		for (int i = 0; i < temp.size(); i++)
		{
			acquiredCredits += creditHours.get(i) * actualCredArray.get(i);
		}

		double semCredWeight = semCredTot / (semCredTot + cTotalCred);
		double pastCredWeight = cTotalCred / (semCredTot + cTotalCred);
		semGPA = acquiredCredits / semCredTot;
		cumaGPA = (semCredWeight * semGPA) + (pastCredWeight * cCurrentCuma);
		if (cumaGPA >= cDesiredCuma && cumaGPA <= limitGPA)
		{
			cumaGPAArray.add(cumaGPA);
			semGPAArray.add(semGPA);
			includeArray.add(temp);
		}
		return cumaGPA;
	}
}
