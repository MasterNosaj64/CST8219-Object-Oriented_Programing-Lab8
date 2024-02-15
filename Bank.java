
/*
 * Name:Jason Waid
 * Student ID:040912687
 * Course & Section: CST8132 304
 * Assignment: Lab 8
 * Date: Nov 19th, 2018
 */
import java.util.Scanner;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.SecurityException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;

/**
 * This program simulates a Banking interface that an employee/user at a Bank
 * would use
 * 
 * @author Jason Waid
 *
 */
public class Bank {
	// Declare Variables
	private String bankName;
	private Scanner input;
	private Scanner fileInput;
	private static Formatter output;
	GenericArray<Account> tArray;
	String acctType; // C for Chequing, S for Savings
	String fName;
	String lName;
	long pNum;
	String email;

	/**
	 * The constructor collects the information for the customer and stores it in
	 * memory
	 * 
	 * @param fName
	 *            contains the first name of the user
	 * @param lName
	 *            contains the last name of the user
	 * @param pNum
	 *            contains the phone number of the user
	 * @param email
	 *            contains the email of the user
	 */
	public Bank() {
		// Initializing Scanner Object
		input = new Scanner(System.in);

		// Prompts user for name of Bank
		System.out.print("What is the name of this bank? ");
		bankName = input.next();

		System.out.print("How many accounts will be in the bank? ");
		int numOfAccounts = input.nextInt();
		tArray = new GenericArray<Account>(numOfAccounts);

		// For loops prompt user for info on each account
		for (int i = 0; i < tArray.capacity(); i++) {

			System.out.println("Enter the details for account " + (i + 1) + ":");
			System.out.println("-------------------------------");

			// Prompts user for account type
			System.out.println("C for Chequing | S for Savings");
			System.out.print("Enter the Account Type: ");
			acctType = input.next().toUpperCase();

			while (!acctType.equals("S") && !acctType.equals("C")) {
				System.out.println("I'm sorry, I didn't understand you.");
				acctType = input.next().toUpperCase();
			}

			// Prompt User for First Name
			System.out.print("Enter the client's first name: ");
			fName = input.next();

			// Prompt User for Last Name
			System.out.print("Enter the client's last name: ");
			lName = input.next();

			// Prompt User for Telephone Number
			System.out.print("Enter the client's telephone number: ");
			pNum = input.nextLong();

			// Prompt User for Email Address
			System.out.print("Enter the client's email address: ");
			email = input.next();

			Client client = new Client(fName, lName, pNum, email);

			System.out.print("Enter the opening balance of the account: ");
			double balance = input.nextDouble();

			// Populates the GenericArray with the provided account details
			if (acctType.equals("C")) {
				tArray.add(new ChequingAccount(client, balance));
			} else {
				tArray.add(new SavingsAccount(client, balance));
			}
		}
	}

	/**
	 * Prints the details of the accounts
	 * 
	 * @param i
	 *            signifies the index of the array
	 * @return account details
	 */
	public void printAccounts() {
		DecimalFormat df = new DecimalFormat("#,###.##");
		openOutputFile();
		for (int i = 0; i < tArray.capacity(); i++) {
			System.out.println(tArray.get(i).toString());

			output.format("%s %s %d %s %.2f%n",

					this.tArray.get(i).getType(), this.tArray.get(i).getName(), this.tArray.get(i).getClient().getPhoneNum(),
					this.tArray.get(i).getClient().getEmail(), this.tArray.get(i).getBalance());
		}
		closeOutputFile();
	}

	// open file bankinput.txt
	public void openInputFile() {
		try {
			fileInput = new Scanner(Paths.get("bankinput.txt"));
		} catch (IOException ioException) {
			System.err.println("Error opening file. Terminating. ");
			System.exit(1);
		}

	}

	// read record from file and enters them into the array
	public void readRecords() {
		
	tArray = new GenericArray<Account>(tArray.size()+4);
		try {
			while (fileInput.hasNext()) // while there is more to read
			{		
				acctType = fileInput.next();
				fName = fileInput.next();
				lName = fileInput.next();
				pNum = fileInput.nextLong();
				email = fileInput.next();
				Client client = new Client(fName, lName, pNum, email);
				double balance = fileInput.nextDouble();
			
				if (acctType.equals("C")) 
				{
					tArray.add(new ChequingAccount(client, balance));
				} 
				else 
				{
					tArray.add(new SavingsAccount(client, balance));
				}
			}
			
			// display record contents
			
			 
		} catch (

		NoSuchElementException elementException) {
			System.err.println("File improperly formed. Terminating. ");
		} catch (IllegalStateException stateException) {
			System.err.println("Error reading from file. Terminating. ");
		}
	}

	public void closeInputFile() {
		if (fileInput != null)
			fileInput.close();
	}

	public void openOutputFile() {
		try {
			output = new Formatter("bankoutput.txt"); // open the file
		} catch (SecurityException securityException) {
			System.err.println("Write permission denied. Terminating. ");
			System.exit(1);
		} catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file. Terminating. ");
			System.exit(1); // terminate the program
		}
	}

	public void closeOutputFile() {
		if (output != null)
			output.close();
	}

	/**
	 * Where the program executes, a menu is provided to the user to navigate the
	 * options such as withdrawal, deposits, printing account details, apply the
	 * monthly process and quitting
	 * 
	 */
	// Program executes from here
	public static void main(String[] args) {
		Bank myBank = new Bank();
		// Variable used to quit program
		boolean quitProgram = false;

		do {
			// Prompt User for input
			System.out.println("Please enter one of the following options:");
			System.out.println("P: print all accounts");
			System.out.println("D: deposit");
			System.out.println("W: withdraw");
			System.out.println("M: Run Monthly fee/interest");
			System.out.println("R: Read Records");
			System.out.println("Q: quit");

			char opt = myBank.input.next().toUpperCase().charAt(0);
			int acc;
			double amt;

			switch (opt) {
			case 'Q':
				quitProgram = true;
				break;
			case 'D':
				System.out.print("Enter the index of the account: ");
				acc = myBank.input.nextInt();
				System.out.print("Enter the amount to deposit: ");
				amt = myBank.input.nextDouble();

				((Account) myBank.tArray.get(acc)).deposit(amt);
				break;
			case 'W':
				System.out.print("Enter the index of the account: ");
				acc = myBank.input.nextInt();
				System.out.print("Enter the amount to withdraw: ");
				amt = myBank.input.nextDouble();

				if (!myBank.tArray.get(acc).withdraw(amt)) {
					DecimalFormat df = new DecimalFormat("#,###.##");
					System.out.println(
							"Insufficient funds! Balance is $" + df.format(myBank.tArray.get(acc).getBalance()));
				}
				break;
			case 'P':
				myBank.printAccounts();
				break;
			case 'M':
				System.out.println("Running monthly process: ");
				for (int i = 0; i < myBank.tArray.capacity(); i++) {
					myBank.tArray.get(i).monthlyProcess();
				}
				break;
			case 'R':
				myBank.openInputFile();
				myBank.readRecords();
				myBank.closeInputFile();
				break;
			default:
				System.out.println("I'm sorry, I didn't understand you.");
			}
			// quits program
		} while (quitProgram == false);

		System.out.println();
		System.out.println("Bye! Have a nice day!");

	}

}
