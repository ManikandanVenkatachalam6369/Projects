package BankManagement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class bank {
	Connection con;
	public bank() {
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","Mani@1234");
		System.out.println("DB connected");
	    } 
	catch (ClassNotFoundException | SQLException e) {
		e.printStackTrace();
    	}
}
	
	//CREATE ACCOUNT
	public void createaccount(int acc_num,String acc_name,Date acc_DOB,String acc_mob,String acc_gmail,String acc_addr,String acc_type,double acc_bal,String acc_status) throws SQLException {
		String s="insert into bank values(?,?,?,?,?,?,?,?,NULL)";
		PreparedStatement pst=con.prepareStatement(s);
		pst.setInt(1, acc_num);
		pst.setString(2,acc_name);
		pst.setDate(3,acc_DOB);
		pst.setString(4,acc_mob);
		pst.setString(5,acc_gmail);
		pst.setString(6,acc_addr);
		pst.setString(7,acc_type);
		pst.setDouble(8, acc_bal);
		int n=pst.executeUpdate();
		String r=(n>0)?"Inserted":"Not Inserted";
		System.out.println(r);
	}
	
	//UPDATE ACCOUNT
	public void updateAccount(int acc_num, String acc_mob, String acc_gmail, String acc_addr) throws SQLException {
	    String q = "UPDATE bank SET acc_mob=?, acc_gmail=?, acc_addr=? WHERE acc_num=?";
	    PreparedStatement pst = con.prepareStatement(q);
	    pst.setString(1, acc_mob);
	    pst.setString(2, acc_gmail);
	    pst.setString(3, acc_addr);
	    pst.setInt(4, acc_num);
	    int n = pst.executeUpdate();
	    String r=((n > 0) ? "Account Updated Successfully" : "Account Not Found!");
	    System.out.println(r);
	}
	
	//DELETE ACCOUNT
	public void deleteAccount(int acc_num) throws SQLException {
	       String q = "DELETE FROM bank WHERE acc_num=?";
	       PreparedStatement pst = con.prepareStatement(q);
	       pst.setInt(1, acc_num);
	       int n = pst.executeUpdate();
	       System.out.println((n > 0) ? "Account Deleted Successfully" : "Account Not Found!");
	   }
	
	//VIEW SINGLE ACCOUNT
	public void viewAccount(int acc_num, String acc_name) throws SQLException {
	    String q = "SELECT * FROM bank WHERE acc_num=? AND acc_name=?";
	    PreparedStatement pst = con.prepareStatement(q);
	    pst.setInt(1, acc_num);
	    pst.setString(2, acc_name);
	    ResultSet rs = pst.executeQuery();

	    if (rs.next()) {
	        System.out.println("\n--- Account Details ---");
	        System.out.println("Account No   : " + rs.getInt("acc_num"));
	        System.out.println("Name         : " + rs.getString("acc_name"));
	        System.out.println("DOB          : " + rs.getDate("acc_DOB"));
	        System.out.println("Mobile       : " + rs.getString("acc_mob"));
	        System.out.println("Gmail        : " + rs.getString("acc_gmail"));
	        System.out.println("Address      : " + rs.getString("acc_addr"));
	        System.out.println("Account Type : " + rs.getString("acc_type"));
	        System.out.println("Balance      : " + rs.getDouble("acc_bal"));
	        System.out.println("Status     : " + rs.getString("acc_status"));
	    } else {
	        System.out.println("No account found with this Number & Name!");
	    }
	}

	
	 //VIEW ALL ACCOUNTS
	 public void viewAllAccounts() throws SQLException {
		    String sql = "SELECT * FROM bank";
		    PreparedStatement pst = con.prepareStatement(sql);
		    ResultSet rs = pst.executeQuery();

		    System.out.println("\n All Accounts:");
		    System.out.println("-------------------------------------------------------------");
		    while (rs.next()) {
		        System.out.println("Account No : " + rs.getInt("acc_num"));
		        System.out.println("Name       : " + rs.getString("acc_name"));
		        System.out.println("DOB        : " + rs.getDate("acc_DOB"));
		        System.out.println("Mobile     : " + rs.getString("acc_mob"));
		        System.out.println("Gmail      : " + rs.getString("acc_gmail"));
		        System.out.println("Address    : " + rs.getString("acc_addr"));
		        System.out.println("Type       : " + rs.getString("acc_type"));
		        System.out.println("Balance    : ₹" + rs.getDouble("acc_bal"));
		        System.out.println("Status     : " + rs.getString("acc_status"));
		        System.out.println("-------------------------------------------------------------");
		    }
		}
	 
	 public String getAccountStatus(int acc_num) throws SQLException {
		    String sql = "SELECT acc_status FROM bank WHERE acc_num = ?";
		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setInt(1, acc_num);
		    ResultSet rs = ps.executeQuery();
		    if (rs.next()) {
		        return rs.getString("acc_status");
		    }
		    return null; // Account not found
		}
	 
	 //FREEZE ACCOUNT
	 public void freezeAccount(int acc_num) throws SQLException {
		    String sql = "UPDATE bank SET acc_status = 'Frozen' WHERE acc_num = ?";
		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setInt(1, acc_num);
		    int rows=ps.executeUpdate();
		    if (rows > 0) {
		        System.out.println("Account " + acc_num + " has been frozen.");
		    } else {
		        System.out.println("Account not found!");
		    }
		}
	 
	 //ACTIVATE ACCOUNT
	 public void activateAccount(int acc_num) throws SQLException {
		    // First check current status
		    String checkSql = "SELECT acc_status FROM bank WHERE acc_num = ?";
		    PreparedStatement checkPst = con.prepareStatement(checkSql);
		    checkPst.setInt(1, acc_num);
		    ResultSet rs = checkPst.executeQuery();

		    if (rs.next()) {
		        String status = rs.getString("acc_status");
		       

		        if ("Frozen".equalsIgnoreCase(status)) {
		            // Update only if Frozen
		            String sql = "UPDATE bank SET acc_status = 'Active' WHERE acc_num = ?";
		            PreparedStatement pst = con.prepareStatement(sql);
		            pst.setInt(1, acc_num);
		            int rows = pst.executeUpdate();

		            if (rows > 0) {
		                System.out.println("Account " + acc_num + " has been Activated Successfully!");
		            } else {
		                System.out.println("Activation failed. Try again.");
		            }
		        } else if ("Active".equalsIgnoreCase(status)) {
		            System.out.println("Account is already Active!");
		        } else {
		            System.out.println("Account status is: " + status + ". Cannot activate.");
		        }
		    } else {
		        System.out.println("Account not found!");
		    }
		}
	 
	 //LOGIN USER
	 public boolean login(int acc_num, String acc_name) throws SQLException {
	        String sql = "SELECT * FROM bank WHERE acc_num=? AND acc_name=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, acc_num);
	        pst.setString(2, acc_name);
	        ResultSet rs = pst.executeQuery();
	        return rs.next();
	    }


	 //VIEW DETAILS
	 public void viewDetails(int acc_num) throws SQLException {
		    String sql = "SELECT * FROM bank WHERE acc_num = ?";
		    PreparedStatement pst = con.prepareStatement(sql);
		    pst.setInt(1, acc_num);
		    ResultSet rs = pst.executeQuery();

		    if (rs.next()) {
		        System.out.println("\n--- Account Details ---");
		        System.out.println("Acc No     : " + rs.getInt("acc_num"));
		        System.out.println("Name       : " + rs.getString("acc_name"));
		        System.out.println("DOB        : " + rs.getDate("acc_DOB"));
		        System.out.println("Mobile     : " + rs.getString("acc_mob"));
		        System.out.println("Gmail      : " + rs.getString("acc_gmail"));
		        System.out.println("Address    : " + rs.getString("acc_addr"));
		        System.out.println("Type       : " + rs.getString("acc_type"));
		        System.out.println("Balance    : ₹" + rs.getDouble("acc_bal"));
		        System.out.println("Status     : " + rs.getString("acc_status"));
		    } else {
		        System.out.println("Account not found!");
		    }
		}

	    //CHECK BALANCE
	    public void checkBalance(int acc_num) throws SQLException {
	        String sql = "SELECT acc_bal FROM bank WHERE acc_num=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, acc_num);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            System.out.println("Current Balance: ₹" + rs.getDouble("acc_bal"));
	        }
	    }

	    //DEPOSIT
	    public void deposit(int acc_num, double amount) throws SQLException {
	        String sql = "UPDATE bank SET acc_bal = acc_bal + ? WHERE acc_num=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setDouble(1, amount);
	        pst.setInt(2, acc_num);
	        int rows = pst.executeUpdate();
	        if (rows > 0) {
	            System.out.println("Amount Deposited Successfully!");
	            checkBalance(acc_num);
	        }
	    }

	    //WITHDRAW
	    public void withdraw(int acc_num, double amount) throws SQLException {
	        String sql = "SELECT acc_bal FROM bank WHERE acc_num=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setInt(1, acc_num);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            double bal = rs.getDouble("acc_bal");
	            if (bal >= amount) {
	                sql = "UPDATE bank SET acc_bal = acc_bal - ? WHERE acc_num=?";
	                pst = con.prepareStatement(sql);
	                pst.setDouble(1, amount);
	                pst.setInt(2, acc_num);
	                pst.executeUpdate();
	                System.out.println("Amount Withdrawn Successfully!");
	                checkBalance(acc_num);
	            } else {
	                System.out.println("Insufficient Balance!");
	            }
	        }
	    }
	    
	    //UPDATE USER INFO
	    public void updateUserContact(int acc_num, String newMob, String newGmail) throws SQLException {
	        String sql = "UPDATE bank SET acc_mob=?, acc_gmail=? WHERE acc_num=?";
	        PreparedStatement pst = con.prepareStatement(sql);
	        pst.setString(1, newMob);
	        pst.setString(2, newGmail);
	        pst.setInt(3, acc_num);
	        int rows = pst.executeUpdate();
	        System.out.println(rows > 0 ? "Contact Updated" : "Account Not Found");
	    }
	    
	    //TRANSFER MONEY
	    public void transferMoney(int fromAcc, int toAcc, double amt) throws SQLException {
	        con.setAutoCommit(false); // Transaction
	        try {
	        	
	            // Withdraw from sender
	            PreparedStatement pst1 = con.prepareStatement("UPDATE bank SET acc_bal=acc_bal-? WHERE acc_num=?");
	            pst1.setDouble(1, amt);
	            pst1.setInt(2, fromAcc);
	            int r1 = pst1.executeUpdate();

	            // Deposit to receiver
	            PreparedStatement pst2 = con.prepareStatement("UPDATE bank SET acc_bal=acc_bal+? WHERE acc_num=?");
	            pst2.setDouble(1, amt);
	            pst2.setInt(2, toAcc);
	            int r2 = pst2.executeUpdate();

	            if (r1 > 0 && r2 > 0) {
	                con.commit();
	                System.out.println("Transfer Successful!");
	            } else {
	                con.rollback();
	                System.out.println("Transfer Failed! Check Accounts.");
	            }
	        } catch (Exception e) {
	            con.rollback();
	            System.out.println("Error: " + e.getMessage());
	        } finally {
	            con.setAutoCommit(true);
	        }
	    }
	
}

