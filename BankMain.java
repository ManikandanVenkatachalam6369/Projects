package BankManagement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class BankMain {

	    private static String acc_status;

		public static void main(String[] args) throws SQLException {
	        Scanner sc = new Scanner(System.in);
	        bank b = new bank();
	        try {
	            System.out.println("*** Bank Management System ***");
	            System.out.println("Login as \n1. Admin \n2. User");
	            int ch = sc.nextInt();

	            switch (ch) {
	            
	                case 1:
	                    System.out.println("You are Logged in as Admin");
	                    System.out.println("Choose Any Operation to Perform\n1. CREATE ACCOUNT \n2. UPDATE ACCOUNT \n3. DELETE ACCOUNT \n4. VIEW ACCOUNT \n5. VIEW ALL ACCOUNTS \n6. FREEZE ACCOUNTS \n7. ACTIVATE ACCOUNTS ");
	                    int c = sc.nextInt();

	                    if (c == 1) {
	                    	
	                    	//CREATE ACCOUNT
	                        try {
	                        	
	                            //ACCOUNT NUMBER
	                            long acc_num;
	                            while (true) {
	                                try {
	                                    System.out.println("Enter Account Number (9–11 DIGITS): ");
	                                    acc_num = sc.nextLong();
	                                    String accStr = String.valueOf(acc_num);

	                                    if (accStr.length() >= 9 && accStr.length() <= 11) {
	                                        break;
	                                    } else {
	                                        System.out.println("Invalid! Must be 9–11 digits.");
	                                    }
	                                } catch (Exception e) {
	                                    System.out.println("Invalid Input! Only digits allowed.");
	                                    sc.nextLine();
	                                }
	                            }

	                            //ACCOUNT NAME
	                            System.out.println("Enter Account Name: ");
	                            String acc_name = sc.next();

	                            //DOB
	                            Date acc_DOB;
	                            while (true) {
	                                System.out.println("Enter DOB (yyyy-MM-dd): ");
	                                String dob = sc.next();
	                                try {
	                                    acc_DOB = Date.valueOf(dob);
	                                    break;
	                                } catch (IllegalArgumentException e) {
	                                    System.out.println("Invalid Date format! Use yyyy-MM-dd.");
	                                }
	                            }

	                            //MOBILE NUMBER
	                            String acc_mob;
	                            while (true) {
	                                System.out.println("Enter Mobile Number (10 digits, starts with 6–9): ");
	                                acc_mob = sc.next();
	                                if (acc_mob.matches("^[6-9][0-9]{9}$")) {
	                                    break;
	                                } else {
	                                    System.out.println("Invalid Mobile Number! Must be 10 digits and start with 6–9.");
	                                }
	                            }

	                            //GMAIL
	                            String acc_gmail;
	                            while (true) {
	                                System.out.println("Enter Gmail (example@gmail.com): ");
	                                acc_gmail = sc.next();
	                                if (acc_gmail.matches("^[A-Za-z0-9+_.-]+@gmail.com$")) {
	                                    break;
	                                } else {
	                                    System.out.println("Invalid Gmail! Must end with @gmail.com");
	                                }
	                            }

	                            //ADDRESS
	                            System.out.println("Enter Address: (Door no,Street name,Village name,District)");
	                            sc.nextLine(); 
	                            String acc_addr = sc.nextLine();

	                            //ACCOUNT TYPE
	                            String acc_type;
	                            while (true) {
	                                System.out.println("Enter Account Type (Savings/Current): ");
	                                acc_type = sc.next();
	                                if (acc_type.equalsIgnoreCase("Savings") ||
	                                        acc_type.equalsIgnoreCase("Current")) {
	                                    break;
	                                } else {
	                                    System.out.println("Invalid Account Type! Must be Savings or Current.");
	                                }
	                            }

	                            //INITIAL DEPOSIT
	                            double acc_bal = 1000;
	                            System.out.println("Initial Deposit fixed at ₹1000.");

	                            //INSERT INTO DB
	                            b.createaccount((int) acc_num, acc_name, acc_DOB, acc_mob,
	                                    acc_gmail, acc_addr, acc_type, acc_bal,acc_status);

	                        } catch (Exception e) {
	                            System.out.println("Unexpected Error: " + e.getMessage());
	                        }
	                    }
	                    
	                    //UPDATE ACCOUNT
	                    else if (c == 2) {
	                        System.out.println("Enter Account Number to Update: ");
	                        int acc_num = sc.nextInt();
	                        System.out.println("Enter New Mobile Number: ");
	                        String acc_mob = sc.next();
	                        System.out.println("Enter New Gmail: ");
	                        String acc_gmail = sc.next();
	                        System.out.println("Enter New Address: ");
	                        sc.nextLine(); 
	                        String acc_addr = sc.nextLine();

	                        b.updateAccount(acc_num, acc_mob, acc_gmail, acc_addr);
	                    }
	                    
	                    //DELETE ACCOUNT
	                    else if (c == 3) {	                        
	                        System.out.println("Enter Account Number to Delete: ");
	                        int acc_num = sc.nextInt();
	                        b.deleteAccount(acc_num);
	                    }
	                    
	                    //VIEW ACCOUNT BY NUMBER AND NAME
	                    else if (c == 4) {	                      
	                        System.out.println("Enter Account Number: ");
	                        int acc_num = sc.nextInt();
	                        System.out.println("Enter Account Name: ");
	                        String acc_name = sc.next();
	                        b.viewAccount(acc_num, acc_name);
	                    }
	                    
	                    //VIEW ALL ACCOUNTS
	                    else if (c == 5) {	                       
	                        b.viewAllAccounts();

	                    }
	                    
	                    //FREEZE ACCOUNT
	                    else if (c == 6) {	                      
	                        System.out.println("Enter Account Number to Freeze: ");
	                        int acc_num = sc.nextInt();
	                        b.freezeAccount(acc_num);

	                    }
	                    
	                    //ACTIVATE ACCOUNT
	                    else if(c == 7) {
	                    	System.out.println("Enter Account Number to Activate: ");
	                        int activateAcc = sc.nextInt();
	                        b.activateAccount(activateAcc);
	                        break;
	                    }
	                   
	                    break;

	                case 2:
	                    // -------- User Menu --------
	                    System.out.println("You are Logged in as User");

	                    // LOGIN FIRST
	                    System.out.println("Enter Account Number: ");
	                    int uacc = sc.nextInt();
	                    System.out.println("Enter Account Name: ");
	                    String uname = sc.next();

	                    String status = b.getAccountStatus(uacc);

	                    if (status == null) {
	                        System.out.println("Account not found!");
	                        break;
	                    } else if (status.equalsIgnoreCase("Frozen")) {
	                        System.out.println("Your account is frozen. Please contact Admin.");
	                        break;
	                    } else {
	                        if (b.login(uacc, uname)) {
	                            System.out.println("Login Successful!");

	                            // Keep showing user menu until logout
	                            while (true) {
	                                System.out.println("\nChoose Operation:");
	                                System.out.println("1. View Account Details");
	                                System.out.println("2. Check Balance");
	                                System.out.println("3. Deposit");
	                                System.out.println("4. Withdraw");
	                                System.out.println("5. Update User Contact");
	                                System.out.println("6. Transfer Money to Another Account");
	                                System.out.println("7. Logout");

	                                int choice = sc.nextInt();
	                                
	                                //VIEW DETAILS
	                                if (choice == 1) {
	                                    b.viewDetails(uacc);
	                                } 
	                                
	                                //CHECK BALANCE
	                                else if (choice == 2) {
	                                    b.checkBalance(uacc);
	                                } 
	                                
	                                //DEPOSIT AMOUNT
	                                else if (choice == 3) {
	                                    System.out.println("Enter Deposit Amount: ");
	                                    double damt = sc.nextDouble();
	                                    b.deposit(uacc, damt);
	                                } 
	                                
	                                //WITHDRAW AMOUNT
	                                else if (choice == 4) {
	                                    System.out.println("Enter Withdraw Amount: ");
	                                    double wamt = sc.nextDouble();
	                                    b.withdraw(uacc, wamt);
	                                }
	                                
	                                //UPDATE USER CONTACT
	                                else if (choice == 5) {
	                                    System.out.println("Enter New Mobile: ");
	                                    String newMob = sc.next();
	                                    System.out.println("Enter New Gmail: ");
	                                    String newGmail = sc.next();
	                                    b.updateUserContact(uacc, newMob, newGmail);
	                                } 
	                                
	                                //TRANSFER MONEY
	                                else if (choice == 6) {
	                                    System.out.println("Enter Receiver Account No: ");
	                                    int recv = sc.nextInt();
	                                    System.out.println("Enter Transfer Amount: ");
	                                    double amt = sc.nextDouble();
	                                    b.transferMoney(uacc, recv, amt);
	                                } 
	                                
	                                //LOG OUT
	                                else if (choice == 7) {
	                                    System.out.println("Logged out successfully!");
	                                    break;
	                                } 
	                                else {
	                                    System.out.println("Invalid Choice!");
	                                }
	                            }
	                        } 
	                        else {
	                            System.out.println("Invalid Account Number or Name!");
	                        }
	                    }
	                    break;

	                default:
	                    System.out.println("Invalid Main Choice!");
	            }
	        } 
	        catch (Exception e) {
	            System.out.println("Error: " + e.getMessage());
	        }
	    }
	}

