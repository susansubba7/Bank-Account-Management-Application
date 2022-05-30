import java.util.List;
import java.util.Map;
import java.util.Random;

public class User {
    private String userName;
    private String password;
    private DatabaseConnection connection = new DatabaseConnection();
    private Map<String, String> userInfo;
    private List<Map<String, String>> userStatement;
    private double balance;
    private Random randomGen = new Random();
    private String accountNumber;

    /**
     * Constructor for User
     * @param userName
     * @param password
     * @throws Exception
     */
    public User(String userName, String password) throws Exception{
        this.userName = userName;
        this.password = password;
        userInfo = connection.getData(userName, password);
        userStatement = connection.getStatement(userName, password);
    }

    /**
     * Second constructor for User
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param ssn
     * @param amount
     * @param dob
     * @param address
     * @param phoneNumber
     * @param email
     * @param asnwerOne
     * @param answerTwo
     * @param questionOne
     * @param questionTwo
     * @throws Exception
     */
    public User(String username, String password, String firstName, String lastName, String ssn, double amount, String dob, String address, String phoneNumber, String email, String asnwerOne, String answerTwo, String questionOne, String questionTwo) throws Exception{
        this.userName = username;
        this.password = password;
        accountNumber = "";
        for(int i = 0; i < 8; i++){
            accountNumber += randomGen.nextInt(10);
        }
        connection.setData(username, password, firstName, lastName, ssn, amount, accountNumber, dob, address, phoneNumber, email, asnwerOne, answerTwo, questionOne, questionTwo);
        userInfo = connection.getData(username, password);
        userStatement = connection.getStatement(username, password);
    }

    /**
     * Returns user's firstname
     * @return String representing user's first name
     */
    public String getFirstName(){
        return userInfo.get("firstname");
    }

    /**
     * Returns user's lastname
     * @return String representing user's lastname
     */
    public String getLastName(){
        return userInfo.get("lastname");
    }

    /**
     * Returns user's account number
     * @return String representing user's account number
     */
    public String getAccountNumber(){
        return userInfo.get("accountnumber");
    }

    /**
     * Returns user's balance
     * @return double representing user's balance
     */
    public double getBalance(){
        balance = Double.parseDouble(userInfo.get("balance"));
        return balance;
    }

    /**
     * Returns feedback based on how much user's balance will change
     * @param amount
     * @param accountNum
     * @return String representing feedback for user's execution
     * @throws Exception
     */
    public String transferFunds(double amount, String accountNum) throws Exception{
        if(balance - amount < 0){
            return "insufficient funds";
        }
        balance -= amount;
        connection.updateBalance(balance, this.getAccountNumber());
        userInfo = connection.getData(userName, password);
        double receverInfo = Double.parseDouble(connection.getBalance(accountNum));
        receverInfo += amount;
        connection.updateBalance(receverInfo, accountNum);
        return null;
    }

    /**
     * Returns user's bank statement
     * @return List of HashMap contaning String
     */
    public List<Map<String, String>> getUserStatement(){
        return userStatement;
    }

    /**
     * Returns user's security questions
     * @return HashMap of String
     */
    public Map<String, String> getUserSecurityQuestions(){
        return connection.getSecurityQuestion(userName);
    }

    /**
     * Updates user's information in the database
     */
    public void updateAccount(Map<String, String> userInfo, String accountNumber){
        connection.updateUserInfo(userInfo, accountNumber);
    }
}
