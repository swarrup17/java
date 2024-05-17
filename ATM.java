import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private Map<Integer, Account> accounts;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    // Method to initialize sample accounts (for testing)
    private void initializeAccounts() {
        accounts.put(1234, new Account(1234, "John Doe", 500.0));
        accounts.put(5678, new Account(5678, "Jane Smith", 1000.0));
    }

    public void displayMenu() {
        System.out.println("Welcome to the ATM!");
        System.out.println("1. Login");
        System.out.println("2. Create Account");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    public void login() {
        System.out.print("Enter your account number: ");
        int accountNumber = scanner.nextInt();

        System.out.print("Enter your PIN: ");
        int pin = scanner.nextInt();

        Account account = accounts.get(accountNumber);

        if (account != null && account.getPin() == pin) {
            System.out.println("Login successful. Welcome, " + account.getOwner() + "!");
            showAccountMenu(account);
        } else {
            System.out.println("Invalid account number or PIN. Please try again.");
        }
    }

    public void createAccount() {
        System.out.print("Enter your name: ");
        scanner.nextLine(); // Consume newline
        String name = scanner.nextLine();

        System.out.print("Enter a PIN for your account: ");
        int pin = scanner.nextInt();
        int number = generateAccountNumber();
        Account newAccount = new Account(number, name, 0.0);
        newAccount.setPin(pin);
        accounts.put(newAccount.getAccountNumber(), newAccount);

        System.out.println("Account number = "+number);
        System.out.println("Account created successfully. Your account number is: " + newAccount.getAccountNumber());
    }

    private void showAccountMenu(Account account) {
        int choice;
        do {
            System.out.println("\nAccount Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transaction History");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    account.checkBalance();
                    break;
                case 2:
                    account.deposit(scanner);
                    break;
                case 3:
                    account.withdraw(scanner);
                    break;
                case 4:
                    account.displayTransactionHistory();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private int generateAccountNumber() {
        // Simple method to generate unique account numbers
        return (int) (Math.random() * 9000) + 1000;
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.initializeAccounts(); // For testing with sample accounts

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            atm.displayMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.login();
                    break;
                case 2:
                    atm.createAccount();
                    break;
                case 3:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);

        scanner.close();
    }
}

class Account {
    private int accountNumber;
    private String owner;
    private int pin;
    private double balance;
    private TransactionHistory transactionHistory;

    public Account(int accountNumber, String owner, double balance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = balance;
        this.transactionHistory = new TransactionHistory();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getOwner() {
        return owner;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void checkBalance() {
        System.out.println("Your balance is: $" + balance);
    }

    public void deposit(Scanner scanner) {
        System.out.print("Enter deposit amount: $");
        double amount = scanner.nextDouble();
        balance += amount;
        transactionHistory.addTransaction("Deposit", amount);
        System.out.println("Deposit successful.");
    }

    public void withdraw(Scanner scanner) {
        System.out.print("Enter withdrawal amount: $");
        double amount = scanner.nextDouble();
        if (amount > balance) {
            System.out.println("Insufficient funds!");
        } else {
            balance -= amount;
            transactionHistory.addTransaction("Withdrawal", -amount);
            System.out.println("Withdrawal successful.");
        }
    }

    public void displayTransactionHistory() {
        transactionHistory.printHistory();
    }
}

class TransactionHistory {
    private StringBuilder history;

    public TransactionHistory() {
        history = new StringBuilder();
    }

    public void addTransaction(String type, double amount) {
        history.append(type).append(": $").append(amount).append("\n");
    }

    public void printHistory() {
        System.out.println("Transaction History:");
        System.out.println(history.toString());
    }
}
