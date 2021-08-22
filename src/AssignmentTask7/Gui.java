package AssignmentTask7;

import static java.nio.file.StandardOpenOption.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Gui extends JFrame implements ActionListener, Cards {
	ArrayList<String> ColleageStudent = new ArrayList<>();
	int numWonRound = 0;
	int numLostRound = 0;
	int roundCount = 0;
	String textOutput = " ";
	String cardOneNumber;
	String cardTwoNumber;
	String cardThreeNumber;
	int cardOneNumberInt;
	int cardTwoNumberInt;
	int cardThreeNumberInt;
	int max, min;

	private double BalanceHere = 0;
	double NewBalance;
	String[][] allCards = {
			{ "1 spade", "2 spade", "3 spade", "4 spade", "5 spade", "6 spade", "7 spade", "8 spade", "9 spade",
					"10 spade", "11 spade", "12 spade", "13 spade" },
			{ "1 club", "2 club", "3 club", "4 club", "5 club", "6 club", "7 club", "8 club", "9 club", "10 club",
					"11 club", "12 club", "13 club" },
			{ "1 heart", "2 heart", "3 heart", "4 heart", "5 heart", "6 heart", "7 heart", "8 heart", "9 heart",
					"10 heart", "11 heart", "12 heart", "13 heart" },
			{ "1 diamand", "2 diamand", "3 diamand", "4 diamand", "5 diamand", "6 diamand", "7 diamand", "8 diamand",
					"9 diamand", "10 diamand", "11 diamand", "12 diamand", "13 diamand" } };

	String cardOne;// = gennumbForArrayOne(allCards);
	String cardTwo;// = gennumbForArrayTwo(allCards);
	String cardThree;

	JLabel enterBalance = new JLabel("Enter your balance FIRST and click save");
	JLabel enteredBalance = new JLabel();
	JTextField balanceField = new JTextField(10);

	JLabel labelOne = new JLabel("************Game start***************");
	JLabel labelTwo;
	JLabel labelThree;
	JLabel labelFour;
	JLabel labelFive = new JLabel();
	// JLabel labelSix = new JLabel("______________________________");
	JLabel winOrLose = new JLabel();
	JButton savebutton = new JButton("Save");
	JButton saveAndExit = new JButton("Save result & exit");
	JTextField textField = new JTextField(3);
	JButton myButton = new JButton("Generate");
	// JButton nextRound = new JButton("NextRound");

	public Gui() {

		super("Card game here");
		setSize(650, 350);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout(FlowLayout.CENTER));
		labelOne.setFont(new Font("Arial", Font.BOLD, 30));
		labelTwo = new JLabel(
				"**The first two cards will be displayed here after you enter 1 for the new card and press generate**");
		labelThree = new JLabel("Enter 0 to fold or 1 to generate a new card (if first round enter 1)");
		enteredBalance.setFont(new Font("Arial", Font.ITALIC, 8));

		gameStart(BalanceHere);

	}

	public void gameStart(double bal) {

		BalanceHere = bal;
		add(labelOne);
		add(enterBalance);
		add(balanceField);
		add(savebutton);
		add(enteredBalance);
		add(labelTwo);
		add(labelThree);
		add(textField);

		add(myButton);
		// add(labelSix);
		add(labelFive);
		add(winOrLose);
		add(saveAndExit);
		savebutton.addActionListener(this);
		myButton.addActionListener(this);
		saveAndExit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		try {

			String textFieldEntNum = textField.getText();
			String textFieldBalance = balanceField.getText();
			BalanceHere = Double.parseDouble(textFieldBalance);

			Object source = e.getSource();
			try {
				if (source == savebutton) {
					NewBalance = BalanceHere;
					System.out.println("Entered balance is " + NewBalance);
					enteredBalance.setText("Entered balance is " + NewBalance);
				}

				if (source == myButton) {
					roundCount += 1;
					winOrLose.setVisible(true);
					// every time when the generate button is clicked the round count gets +1
					if (textFieldEntNum.equals("1")) {
						cardOne = gennumbForArrayOne(allCards);
						cardTwo = gennumbForArrayTwo(allCards);
						textOutput = "The generated cards are " + cardOne + " " + cardTwo
								+ "*                          ";
						labelTwo.setText(textOutput);

						cardThree = gennumbForArrayThree(allCards);
						textOutput = "The new card was generated " + cardThree;
						System.out.println(textOutput);
						labelFive.setText(textOutput);

						cardOneNumber = cardOne.substring(0, 2);
						cardTwoNumber = cardTwo.substring(0, 2);
						cardThreeNumber = cardThree.substring(0, 2);

						cardOneNumberInt = Integer.parseInt(cardOneNumber.trim());
						cardTwoNumberInt = Integer.parseInt(cardTwoNumber.trim());
						cardThreeNumberInt = Integer.parseInt(cardThreeNumber.trim());

						System.out.println("The number card one: " + cardOneNumberInt + "\nThe number card two: "
								+ cardTwoNumberInt + "\nThe number card three: " + cardThreeNumberInt);

						roundWinner(cardOneNumberInt, cardTwoNumberInt, cardThreeNumberInt);
					}

					else if (textFieldEntNum.equals("0")) {
						winOrLose.setVisible(false);
						cardOne = gennumbForArrayOne(allCards);
						cardTwo = gennumbForArrayTwo(allCards);
						textOutput = "The generated cards are " + cardOne + " " + cardTwo
								+ "*                          ";
						labelTwo.setText(textOutput);

						// winOrLose.setVisible(false);
						textOutput = "You got fold. Your balance is the same";

						System.out.println(textOutput);
						labelFive.setText(textOutput);

					}

					else {
						textOutput = "Invalid input, enter 1 or 0 (1 if the first round)";
						labelFive.setText(textOutput);
					}

				}

				if (source == saveAndExit) {
					saveResultOfGame();
					System.exit(0);

				}

			}

			catch (Exception ex) {
				System.out.println("Error " + ex);
			}

		} catch (Exception exception) {
			System.out.println("Error" + exception);
			System.out.println("Enter balance first!");
			enterBalance.setFont(new Font("Arial", Font.BOLD, 20));
			enterBalance.setText("Enter your balance first!");
		}

	}

	public String gennumbForArrayOne(String[][] arrayhere) {
		int a, b;
		Random randGnd = new Random();
		a = randGnd.nextInt(SYMNUM);
		Random randGnd1 = new Random();
		b = randGnd1.nextInt(CARDNUM);

		return arrayhere[a][b];
	}

	public String gennumbForArrayTwo(String[][] arrayhere) {
		int a, b;
		Random randGnd = new Random();
		a = randGnd.nextInt(SYMNUM);
		Random randGnd1 = new Random();
		b = randGnd1.nextInt(CARDNUM);

		return arrayhere[a][b];
	}

	public String gennumbForArrayThree(String[][] arrayhere) {
		int a, b;
		Random randGnd = new Random();
		a = randGnd.nextInt(SYMNUM);
		Random randGnd1 = new Random();
		b = randGnd1.nextInt(CARDNUM);

		return arrayhere[a][b];
	}

	public void roundWinner(int cardOneNumberInt, int cardTwoNumberInt, int cardThreeNumberInt) {

		if (cardOneNumberInt > cardTwoNumberInt) {
			max = cardOneNumberInt;
			min = cardTwoNumberInt;
		} else {
			max = cardTwoNumberInt;
			min = cardOneNumberInt;
		}
		if (cardThreeNumberInt > min && cardThreeNumberInt < max) {
			NewBalance += 1;
			numWonRound += 1;
			textOutput = "You won and your balance is " + NewBalance;
			winOrLose.setText(textOutput);
			System.out.println("You won and your balance is " + NewBalance);

		} else {
			NewBalance -= 1;
			numLostRound += 1;
			textOutput = "You lost, your balance is " + NewBalance;
			winOrLose.setText(textOutput);
			System.out.println("You lost and your balance is " + NewBalance);
		}

	}

	public void saveResultOfGame() {
		Path filetoWrite = Paths.get("C:\\MY DOC\\java 2175\\lecture 11\\Assignment7Last\\gameResult.txt");
		String datatosave = "Number or round that was played: " + roundCount + "\nNumber of lost rounds: "
				+ numLostRound + "\nNumber of won rounds: " + numWonRound + "\nYour balance: " + NewBalance;
		byte[] data = datatosave.getBytes();
		OutputStream output = null;
		try {
			output = new BufferedOutputStream(Files.newOutputStream(filetoWrite, CREATE));
			output.write(data);
			output.close();
		}

		catch (Exception ex) {
			System.out.println("Error " + ex);
		}
	}

}
