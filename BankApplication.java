package Bank_Management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Account{
    private String name;
    private double balance = 0;
    private String Password;
    private String upiID;
    private int AccNo;

    Account(){}
    Account(String AccHolderName, String Password, String upiId, int AccNo){
        this.name = AccHolderName;
        this.Password = Password;
        this.upiID = upiId;
        this.AccNo = AccNo;
    }

    public void Write(double amt){
        List<String> list = new ArrayList<>();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/Bank_Management/AccountData.txt"));
            String line;
            while ((line = reader.readLine())!=null){
                String[] arr = line.split(",");
                if(Integer.valueOf(arr[3]) == AccNo){
                    arr[4] = String.valueOf(amt);
                }

                list.add(String.join(",",arr));
            }
            reader.close();
            FileWriter writer = new FileWriter("src/Bank_Management/AccountData.txt");
            for(String l : list){
                writer.write(l+"\n");
            }
            writer.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
    public void setBalance(double amt){
        this.balance = amt;
    }
    public void getBalance(){
        System.out.println("Balance : "+balance);
    }

    public void Withdraw(double amt){
        if(amt > 0 && amt <= balance){
            balance -= amt;
            Write(balance);
            System.out.println("Withdrawal successful. New Balance: " + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public void Deposit(double amt){
        if(amt > 0){
            balance += amt;
            Write(balance);
        }
        System.out.println("Deposited successful. New Balance: " + balance);
    }

    public String getName(){
        return name;
    }
    public void receiveTransfer(double amt){
        balance += amt;
        Write(balance);
    }
    public void Transfer(int AccNo, double amt){
        if(amt > 0 && amt <= balance){
            if(CreateAccount.hm.containsKey(AccNo)){
                CreateAccount.hm.get(AccNo).receiveTransfer(amt);
                balance -= amt;
                Write(balance);
                System.out.println("Transferred successful. New Balance: " + balance);
            }
        }
    }

}

class CreateAccount extends Account{
    static HashMap<Integer, Account> hm = new HashMap<>();;
    CreateAccount(String AccHolderName, String Password, String upiId, int AccNo){
        super();
        Account account = new Account(AccHolderName,Password,upiId,AccNo);
        boolean flag = false;
        try {
            FileWriter writer = new FileWriter("src/Bank_Management/AccountData.txt", true);
            if(!hm.containsKey(AccNo)){
                hm.put(AccNo, account);
                writer.write(AccHolderName+","+Password+","+upiId+","+AccNo +","+0.0+"\n");
                System.out.println("Inserted into hashMap");
                System.out.println("Created Successfully");
                flag = true;
            }else{
                System.out.println("Account already exist!");
            }
            writer.close();
        }
        catch (IOException error){
            System.out.println(error);
        }
        if(flag){
            AccountVerification.accountVerification(AccNo,Password);
        }
    }
}
class MainPage{
    private int AccNo;
    MainPage(int AccNo){
        this.AccNo = AccNo;
        main();
    }
    private void main(){
        Account arr = CreateAccount.hm.get(AccNo);
        System.out.println("Account HolderName : "+" "+arr.getName()+"\n");
        int stop =0;
        while(stop != 5) {
            System.out.println("1. Withdraw");
            System.out.println("2. Deposit");
            System.out.println("3. Check Balance");
            System.out.println("4. Transfer");
            System.out.println("5. Logout");

            int choice = InputUtil.scanner.nextInt();
            stop = choice;
            if (choice == 1) {
                System.out.println("Enter the Amount");
                double amt = InputUtil.scanner.nextDouble();
                arr.Withdraw(amt);

            } else if (choice == 2) {
                System.out.println("Enter the Amount");
                double amt = InputUtil.scanner.nextDouble();
                arr.Deposit(amt);
            }
            else if (choice ==3){
                arr.getBalance();
            } else if (choice == 4) {
                System.out.println("Enter the AccNo and Amount");
                int AccNo = InputUtil.scanner.nextInt();
                double amt = InputUtil.scanner.nextDouble();
                arr.Transfer(AccNo, amt);
            }
        }
    }
}

class AccountVerification {
    public static boolean accountVerification(int AccNo, String Password){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/Bank_Management/AccountData.txt"));
            String line;
            while((line = reader.readLine())!= null){
                String[] Data = line.split(",");
                if(CreateAccount.hm.containsKey(AccNo) && Password.equals(Data[1]) && AccNo == Integer.parseInt(Data[3])){
                    System.out.println("Successfully Verified");
                    new MainPage(AccNo);
                    return true;
                }
            }
            reader.close();
        }
        catch (IOException error){
            System.out.println(error);
        }
        System.out.println("Invalid Credientials");
        return false;
    }
}
class InputUtil{
    public static Scanner scanner = new Scanner(System.in);
}
public class BankApplication {
    public static void AccVerification(){
        boolean flag = false;
        while(!flag){
            System.out.println("Enter the AccNo and Password");
            int AccNo = InputUtil.scanner.nextInt();
            InputUtil.scanner.nextLine();
            String Password = InputUtil.scanner.nextLine();
            flag = AccountVerification.accountVerification(AccNo,Password);
        }
    }

    public static void NewAccount(){
        System.out.println("Enter the AccountHolderName, Password, UPI ID and AccNo");
        InputUtil.scanner.nextLine();
        String AccHolderName = InputUtil.scanner.nextLine();
        String Password = InputUtil.scanner.nextLine();
        String UPIID = InputUtil.scanner.nextLine();
        int AccNo = InputUtil.scanner.nextInt();
        CreateAccount createAccount = new CreateAccount(AccHolderName,Password, UPIID, AccNo);
    }

    public static void LoadAccounts(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/Bank_Management/AccountData.txt"));
            String line;
            while ((line = reader.readLine())!=null){
                String[] arr = line.split(",");
                Account account = new Account(arr[0],arr[1],arr[2],Integer.valueOf(arr[3]));
                account.setBalance(Double.valueOf(arr[4]));
                CreateAccount.hm.put(Integer.valueOf(arr[3]), account);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        LoadAccounts();
        int flag = 0;
        while(flag!=3) {
            System.out.println("Welcome to ABC Bank");
            System.out.println("Sign In");
            System.out.println("1. Already have an Account");
            System.out.println("2. Create an Account");
            System.out.println("3. Exit");

            int choice = InputUtil.scanner.nextInt();
            flag = choice;
            if (choice == 1) {
                AccVerification();
            } else if (choice == 2){
                NewAccount();
            }
        }
    }
}