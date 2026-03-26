import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
class FindAccount
{
    public static Account findAccount(String accountNumber)
    {
        List<Account> accounts=Bank.accounts;
        ListIterator<Account> itr=accounts.listIterator();
        while(itr.hasNext())
        {
            Account acc=(Account)itr.next();
            if(acc.getAccountNumber().equals(accountNumber))
            {
                return acc;
                
            }
        }
        throw new AccountNotFound();

    }
}
class PasswordInvalidException extends RuntimeException
{
    PasswordInvalidException()
    {
        super("Invalid Password");
    }
}
class PasswordDoesntMatch extends RuntimeException
{
    PasswordDoesntMatch()
    {
        super("Both passwords not matched");
    }
}
class PasswordExitsException extends RuntimeException
{
    PasswordExitsException()
    {
        super("Password already exits");
    }
}
class PasswordValidationException extends RuntimeException
{
    PasswordValidationException()
    {
        super("Password is not valid");
    }
}
class PasswordValidator
{
    final static String passwordvalidation="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
    public static boolean passwordChecker(String password)
    {
        Pattern patt=Pattern.compile(passwordvalidation);
        Matcher match=patt.matcher(password);
        return match.matches();
    }
    public static boolean passwordExists(String password)
    {
        List<Account> accounts=Bank.accounts;
        ListIterator<Account> itr=accounts.listIterator();
        while(itr.hasNext())
        {
            Account acc=(Account)itr.next();
            if(acc.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }
}
class AccountNotFound extends RuntimeException
{
    AccountNotFound()
    {
        super("Account Not Found");
    }
}
class InsufficientFunds extends RuntimeException
{
    InsufficientFunds()
    {
        super("Balance is low");
    }
}
class Account
{
    ArrayList<String> transactions=new ArrayList<>();
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String password;

    Account(String accountNumber,String accountHolderName,String password)
    {
        this.accountHolderName=accountHolderName;
        this.accountNumber=accountNumber;
        this.password=password;
    }
    public void saveTransactions()throws IOException
    {
        ListIterator itr=transactions.listIterator();
        PrintWriter pw=new PrintWriter("Transactions.txt");
        pw.println("Account Number : Transaction Type : Transaction Date : Amount ");
        while(itr.hasNext())
        {
            pw.println(itr.next());
        }
        pw.close();
        System.out.println("TRANSACTION HISTORY STORED IN Transactions.txt FILE");
    }
    public void showTransaction()
    {
        ListIterator itr=transactions.listIterator();
        System.out.println("Account Number : Transaction Type : Transaction Date : Transaction Amount");
        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
    public  void transaction(String transaction_accountNumber,String transaction_type,String transaction_date,double transaction_amount)
    {
        transactions.add(transaction_accountNumber+" : "+transaction_type+" : "+transaction_date+" : "+transaction_amount);
    }
    public String getHolderName()
    {
        return accountHolderName;
    }
    public String getAccountNumber()
    {
        return accountNumber;
    }
    public String getPassword()
    {
        return password;
    }
    public synchronized void deposit(double amount)
    {
        this.balance+=amount;
    }
    public double balance()
    {
        return balance;
    }
    public synchronized void withdraw(double amount)
    {
        if(amount>balance())
        {
            throw new InsufficientFunds();
        }
        else
        {
            balance-=amount;
        }
    }
    @Override
    public  boolean equals(Object obj)
    {
        if(obj==null)return false;
        if(obj instanceof Account)
        {
            Account a2=(Account)obj;
            String accountNumber2=(String)a2.accountNumber;
            String accountHolderName2=(String)a2.accountHolderName;
            if(this.accountHolderName.equals(accountHolderName2)&&accountNumber.equals(accountNumber2))
            {
                return true;
            }
            return false;
        }
        return false;

    }
    @Override
    public int hashCode()
    {
        return accountNumber.hashCode()+accountHolderName.hashCode();
    }
    public synchronized void transfer(String accountNumber,double amount)
    {
        if(balance<amount)
        {
            throw new InsufficientFunds();
        }
        else
        {
            Account acc=FindAccount.findAccount(accountNumber);
            acc.deposit(amount);
            this.withdraw(amount);
        }

    }
    public String toString()
    {
        return this.accountNumber+" : "+this.accountHolderName;
    }
    

}

class Display
{
    public static void listAccounts()
    {
        List<Account> accounts=Bank.accounts;
        ListIterator itr=accounts.listIterator();
        System.out.println("Account Number : Account Holder Name");
        while(itr.hasNext())
        {
            System.out.println(itr.next());
        }
    }
    public static void saveToFile()throws IOException
    {
        PrintWriter pw=new PrintWriter("AccountsInfo.txt");
        List<Account> accounts=Bank.accounts;
        ListIterator itr=accounts.listIterator();
        pw.println("ACCOUNT NO : HOLDER NAME");
        while(itr.hasNext())
        {
            pw.println(itr.next());
        }
        pw.close();
        System.out.println("DATA SAVED TO AccountsInfo.txt FILE");
    }
}
public class Bank
{
    static List<Account> accounts=new ArrayList<>();
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("****WELCOME TO ONLINE BANKING****");
        int i=101;
        while(true)
        {
            System.out.println("1.CREATE ACCOUNT \n2.DEPOSIT \n3.WITHDRAW \n4.TRANSFER \n5.CHECK BALANCE \n6.DISPLAY ALL ACCOUNTS \n7.SAVE ACCOUNTS INFO TO FILE \n8.SHOW TRANSACTIONS \n9.SAVE TRANSACTIONS \n10.EXIT \n");
            System.out.print("ENTER YOUR CHOICE: ");
            int ch=sc.nextInt();
            switch(ch)
            {
                case 1:
                    System.out.println("\nENTER ACCOUNT HOLDER NAME");
                    String name=sc.next();
                    System.out.println("\nENTER PASSWORD: ");
                    String password=sc.next();
                    
                   
                    if(!PasswordValidator.passwordChecker(password))
                    {
                        throw new PasswordValidationException();
                    }
                    else if(PasswordValidator.passwordExists(password))
                    {
                        throw new PasswordExitsException();
                    }
                    else
                    {
                        System.out.println("\nCONFIRM PASSWORD: ");
                        String confirmPassword=sc.next();
                        if(password.equals(confirmPassword))
                        {
                            String customerNumber=String.valueOf(i);
                            Account acc=new Account(customerNumber, name, confirmPassword);
                            accounts.add(acc);
                            i++;
                            System.out.println("\nACCOUNT CREATED SUCCESSFULLY");
                            System.out.println("\nNAME: "+acc.getHolderName()+" ACCOUNT NUMBER: "+acc.getAccountNumber());
                        }
                        else
                        {
                            throw new PasswordDoesntMatch();
                        }
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("\nENTER THE ACCOUNT NUMBER: ");
                    String accountNumber=sc.next();
                    Account acc=FindAccount.findAccount(accountNumber);
                    System.out.println("\nENTER THE AMOUNT TO DEPOSIT");
                    double amount=sc.nextDouble();
                    acc.deposit(amount);
                    System.out.println("\nAMOUNT CREDITED SUCCESSFULLY");
                    LocalDateTime currentDateTime=LocalDateTime.now();
                    acc.transaction(accountNumber,"Deposit" , String.valueOf(currentDateTime),amount);
                    break;
                case 3:
                    System.out.println("\nENTER THE ACCOUNT NUMBER: ");
                    accountNumber=sc.next();
                    acc=FindAccount.findAccount(accountNumber);
                    System.out.println("ENTER THE PASSWORD: ");
                    password=sc.next();
                    if(acc.getPassword().equals(password))
                    {
                        System.out.println("\nENTER THE AMOUNT TO WITHDRAW: ");
                        amount=sc.nextDouble();
                        acc.withdraw(amount);
                        System.out.println("\nAMOUNT DEBITED SUCCESSFULLY");
                        currentDateTime=LocalDateTime.now();
                        acc.transaction(accountNumber,"WithDraw" , String.valueOf(currentDateTime),amount);
                    }
                    else
                    {
                        throw new PasswordInvalidException();
                    }
                    System.out.println();
                    break;
                case 5:
                    System.out.println("\nENTER THE ACCOUNT NUMBER");
                    accountNumber=sc.next();
                    acc=FindAccount.findAccount(accountNumber);
                    System.out.println("\nBALANCE: "+acc.balance());
                    System.out.println();
                    break;
                case 4:
                    System.out.println("\nENTER THE SENDER ACCOUNT NUMBER: ");
                    String accountNumber1=sc.next();
                    Account acc1=FindAccount.findAccount(accountNumber1);
                    System.out.println("\nENTER THE PASSWORD: ");
                    password=sc.next();
                    if(acc1.getPassword().equals(password))
                    {
                        System.out.println("\nENTER THE RECEIVER ACCOUNT NUMBER");
                        String accountNumber2=sc.next();
                        System.out.println("\nENTER THE AMOUNT: ");
                        amount=sc.nextDouble();
                        acc1.transfer(accountNumber2, amount);
                        System.out.println("\nMONEY TRANSFERED SUCCESSFULLY");
                        currentDateTime=LocalDateTime.now();
                        acc1.transaction(accountNumber1,"Withdraw" , String.valueOf(currentDateTime),amount);
                        Account acc2=FindAccount.findAccount(accountNumber2);
                        acc2.transaction(accountNumber1,"Deposit" , String.valueOf(currentDateTime),amount);

                    }
                    else
                    {
                        throw new PasswordInvalidException();

                    }
                    System.out.println();
                    break;
                    
                default:
                System.out.println("\nInvalid Choice , Enter valid");
                System.out.println();
                break;
                case 6:
                    
                    Display.listAccounts();
                    System.out.println();
                    break;
                case 7:
                    try{
                        Display.saveToFile();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    System.out.println("\nENTER THE ACCOUNT NUMBER: ");
                    accountNumber=sc.next();
                    acc=FindAccount.findAccount(accountNumber);
                    acc.showTransaction();
                    System.out.println();
                    break;
                case 9:
                    System.out.println("\nENTER THE ACCOUNT NUMBER");
                    accountNumber=sc.next();
                    acc=FindAccount.findAccount(accountNumber);
                    try{
                        acc.saveTransactions();
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    System.out.println();
                    break;
                case 10:
                    System.out.println("THANK YOU");
                    sc.close();
                    System.exit(0);
            }
        }
    }
}