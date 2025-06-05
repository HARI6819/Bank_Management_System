💳 Bank Management System (Java) 

A simple console-based Bank Management System built using Java, 
simulating core banking operations such as account creation, login verification, 
deposits, withdrawals, transfers, and balance checks. All user data is securely stored 
and managed in a flat .txt file, enabling persistent session tracking between application restarts.

📌 Features

 🔐 Create New Account
 
•	Stores user details (Name, Password, UPI ID, Account Number)

•	Saves data in AccountData.txt

•	Automatically loads into in-memory HashMap for quick access

 🔐 Account Login & Verification
 
•	Securely verifies credentials using Account Number and Password

•	Grants access to banking operations after validation

💰 Deposit & Withdraw Money

•	Handles deposits and withdrawals with balance checks

•	Updates changes in both memory and AccountData.txt

🔁 Fund Transfer

•	Allows users to transfer money between accounts

•	Validates target account and updates balances in both

📄 Persistent Data Storage

•	Reads and writes data using file I/O

•	Maintains consistent account balance across sessions

📋 Main Dashboard

Console-based UI to perform:

•	Deposit

•	Withdraw

•	Transfer

•	Check Balance

•	Logout

🚀 How to Run

1.	Clone the repository:
   
bash

Copy code
git clone https://github.com/yourusername/BankManagementSystem.git

2.	Open the project in an IDE (like IntelliJ IDEA or Eclipse)
  
3.	Make sure the file path src/Bank_Management/AccountData.txt exists
   
4.	Run BankApplication.java as a Java Application
   
📌 To Do / Enhancements

•	Password hashing for enhanced security

•	Use of a database (e.g., SQLite or MySQL) instead of file storage

•	Transaction history logs

