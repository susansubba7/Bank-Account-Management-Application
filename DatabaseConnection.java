import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DatabaseConnection{
    private Connection connect;
    private Statement SQLStatment;
    
    /**
     * Constructor DatabaseConnection object and initalizes variables
     */
    public DatabaseConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "SQL server password");
            SQLStatment = connect.createStatement();
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    /**
     * Returns user's data from the database by using SQL SELECT statement
     * @param userName
     * @param userPassword
     * @return HashMap contaning user's information from the database
     * @throws Exception
     */
    public Map<String,String> getData(String userName, String userPassword) throws Exception{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "SQL server password");
            SQLStatment = connect.createStatement();
        }
        catch(Exception e){
            System.out.println(e);
        }
        Map<String, String> userData = new HashMap<>();
        ResultSet data = SQLStatment.executeQuery("SELECT * FROM CustomerAccount INNER JOIN CustomerInfo ON CustomerAccount.accountnum = CustomerInfo.accountnum WHERE username = '" + userName + "' && userpassword = '" + userPassword + "'");
        while(data.next()){
            userData.put(userName, data.getString("username"));
            userData.put(userPassword, data.getString("userpassword"));
            userData.put("accountnumber", data.getString("accountnum"));
            userData.put("ssn", data.getString("ssn"));
            userData.put("firstname", data.getString("firstname"));
            userData.put("lastname", data.getString("lastname"));
            userData.put("dob", data.getString("dob"));
            userData.put("balance", data.getString("balance"));
            userData.put("questionOne", data.getString("questionOne"));
            userData.put("AnswerOne", data.getString("AnswerOne"));
            userData.put("questionTwo", data.getString("questionTwo"));
            userData.put("AnswerTwo", data.getString("AnswerTwo"));
        }
        return userData;
    }

    /**
     * Returns user's bank statement from the database which includes date, amount, and description by using SQL SELECT statement
     * @param userName
     * @param Password
     * @return List of HashMap contaning user's bank activities
     * @throws Exception
     */
    public List<Map<String, String>> getStatement(String userName, String Password) throws Exception{
        Map<String, String> userData = getData(userName, Password);
        List<Map<String, String>> userStatement = new ArrayList<>();
        ResultSet data = SQLStatment.executeQuery("SELECT * FROM CustomerStatement WHERE accountnum = '" + userData.get("accountnumber") + "'");
        while(data.next()){
            Map<String, String> userStatementMap = new HashMap<>();
            userStatementMap.put("date", data.getString("ActivityDate"));
            userStatementMap.put("amount", data.getString("ActivityAmount"));
            userStatementMap.put("title", data.getString("ActivityTitle"));
            userStatement.add(userStatementMap);
        }
        connect.close();
        return userStatement;
    }

    /**
     * Inserts data into the database using SQL INSERT statements
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param ssn
     * @param amount
     * @param accountNum
     * @param dob
     * @param address
     * @param phoneNumber
     * @param email
     * @param asnwerOne
     * @param answerTwo
     * @param questionOne
     * @param questionTwo
     * @throws SQLException
     */
    public void setData(String username, String password, String firstName, String lastName, String ssn, double amount, String accountNum, String dob, String address, String phoneNumber, String email, String asnwerOne, String answerTwo, String questionOne, String questionTwo) throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "SQL server password");
            SQLStatment = connect.createStatement();
        }
        catch(Exception e){
            System.out.println(e);
        }
        SQLStatment.executeUpdate("INSERT INTO customerAccount(username, userpassword, accountnum, balance) VALUES ( '" + username +"', '" + password +"', '" + accountNum +"', " + amount +")");
        SQLStatment.executeUpdate("INSERT INTO CustomerInfo(AccountNum, FirstName, LastName, HomeAddress, SSN, DOB, PhoneNumber, Email, QuestionOne, AnswerOne, QuestionTwo, AnswerTwo) Values ( '" + accountNum + "', '" + firstName + "', '" + lastName + "', '" + address + "', '" + ssn + "', '" + dob + "', '" +  phoneNumber + "', '" + email + "', '" + questionOne + "', '" + asnwerOne + "', '" + questionTwo + "', '" + answerTwo + "')");
        SQLStatment.executeUpdate("INSERT INTO CustomerStatement(AccountNum, ActivityDate, ActivityAmount, ActivityTitle) VALUES ( '" +  accountNum + "', '" + java.time.LocalDate.now() + "', '" + amount + "', 'Deposit')");
        connect.close();
    }

    /**
     * Updates user's balance given the user's account number by using SQL UPDATE statement
     * @param amount
     * @param accountNumber
     * @throws SQLException
     */
    public void updateBalance(double amount, String accountNumber) throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "SQL server password");
            SQLStatment = connect.createStatement();
        }
        catch(Exception e){
            System.out.println(e);
        }
        SQLStatment.executeUpdate("UPDATE customerAccount SET balance = "+amount + " WHERE accountNum = '" + accountNumber + "'");
        connect.close();
    }

    /**
     * Returns user's balance given account number from the database by using SQL SELECT statement
     * @param accountNum
     * @return String representing user's balance
     * @throws SQLException
     */
    public String getBalance(String accountNum) throws SQLException{
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "SQL server password");
            SQLStatment = connect.createStatement();
        }
        catch(Exception e){
            System.out.println(e);
        }
        ResultSet balance =  SQLStatment.executeQuery("SELECT Balance FROM customerAccount WHERE accountNum = '" + accountNum + "'");
        balance.next();
        return balance.getString(1);
    }

    /**
     * Returns use's security questions and answers from the database using SQL SELECT statement
     * @param userName
     * @return HashMap of String retrived from the database
     */
    public Map<String, String> getSecurityQuestion(String userName){
        Map<String, String> securityQuestions = new HashMap<>();
        try {
            ResultSet data = SQLStatment.executeQuery("SELECT QuestionOne, AnswerOne, QuestionTwo, AnswerTwo, UserPassword FROM CustomerInfo INNER JOIN CustomerAccount ON CustomerInfo.accountnum = CustomerAccount.accountnum WHERE username = '" + userName + "'");
            while(data.next()){
                securityQuestions.put("questionOne", data.getString("questionOne"));
                securityQuestions.put("answerOne", data.getString("AnswerOne"));
                securityQuestions.put("questionTwo", data.getString("questionTwo"));
                securityQuestions.put("answerTwo", data.getString("AnswerTwo"));
                securityQuestions.put("password",data.getString("UserPassword"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return securityQuestions;
    }

    /**
     * Updates user's informations in the database by executing SQL UPDATE statements
     * @param userInfo
     * @param accountNumber
     */
    public void updateUserInfo(Map<String, String> userInfo, String accountNumber){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "SQL server password");
            SQLStatment = connect.createStatement();
        }
        catch(Exception e){
            System.out.println(e);
        }
        if(userInfo.containsKey("password")){
            try {
                SQLStatment.executeUpdate("UPDATE CustomerAccount SET UserPassword = '" + userInfo.get("password") + "' WHERE AccountNum = '" + accountNumber + "'");
                userInfo.remove("password");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for(String key : userInfo.keySet()){
            try {
                SQLStatment.executeUpdate("UPDATE CustomerInfo SET " + key + " = '" + userInfo.get(key) + "'  WHERE AccountNum = '" + accountNumber + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
