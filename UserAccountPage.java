import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;


public class UserAccountPage {
    private JFrame frame;
    private  User user;
    private JLabel userBalanceLabel, successLabel;
    private JTextField userFirstNameInput, userLastNameInput, userAddressInput, userPhoneNumberInput, userEmailInput;
    private JPasswordField userPasswordInput;

    /**
     * Constructor for UserAccountPage
     * @param userName
     * @param password
     * @throws Exception
     */
    public UserAccountPage(String userName, String password) throws Exception{
        user = new User(userName, password);
        frame = new JFrame("customer");
        greeting();
        showUserInfo();
        transfer();
        logout();
        updateAccount();
        frame.setSize(650,650);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    /**
     * Second constructor for UserAccountPage
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
    public UserAccountPage(String username, String password, String firstName, String lastName, String ssn, double amount, String dob, String address, String phoneNumber, String email, String asnwerOne, String answerTwo, String questionOne, String questionTwo) throws Exception{
        user = new User(username, password, firstName, lastName, ssn, amount, dob, address, phoneNumber, email, asnwerOne, answerTwo, questionOne, questionTwo);
        frame = new JFrame("customer");
        greeting();
        showUserInfo();
        transfer();
        logout();
        updateAccount();
        frame.setSize(650,650);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    private void greeting(){
        JLabel greeting = new JLabel("Hello, " + user.getFirstName());
        greeting.setBounds(10,10,400,50);
        greeting.setFont(new Font("MONOSPACED",Font.BOLD,40));
        frame.add(greeting);
    }

    private void showUserInfo(){
        JLabel accountNumberLabel = new JLabel("Account Number: " + user.getAccountNumber());
        accountNumberLabel.setBounds(10, 60, 400,50);
        accountNumberLabel.setFont(new Font("MONOSPACED",Font.BOLD,20));
        userBalanceLabel = new JLabel("Current Balance: $" + user.getBalance());
        userBalanceLabel.setBounds(10,90,400,50);
        userBalanceLabel.setFont(new Font("MONOSPACED",Font.BOLD,20));
        JLabel bankStamentLabel = new JLabel("Bank Statement");
        bankStamentLabel.setFont(new Font("MONOSPACED",Font.BOLD,20));
        bankStamentLabel.setBounds(10,130,400,50);
        String[] columnNames = {"Date", "Description", "Amount"};
        JTable table = new JTable(parseUserStatement(),columnNames);
        table.getColumnModel().getColumn(0).setPreferredWidth(5);
        table.setBackground(Color.GREEN.darker());
        table.setForeground(Color.WHITE);
        table.getTableHeader().setBackground(Color.BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,180,610,300);
        scrollPane.getViewport().setBackground(Color.LIGHT_GRAY);
        frame.add(scrollPane);
        frame.add(userBalanceLabel);
        frame.add(accountNumberLabel);
        frame.add(bankStamentLabel);
    }

    private String[][] parseUserStatement(){
        List<Map<String, String>> userStatement = user.getUserStatement();
        String[][] userData = new String[userStatement.size()][3];
        int i = 0;
        int j = 0;
        while(!userStatement.isEmpty()){
            Map<String, String> userDataMap = userStatement.remove(i);
            userData[j][0] = userDataMap.get("date");
            userData[j][1] = userDataMap.get("title");
            userData[j][2] = userDataMap.get("amount");
            j++;
        }
        return userData;
    }

    private void logout(){
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBounds(110, 530, 100, 40);
        logoutButton.setBackground(Color.green.darker());
        logoutButton.setForeground(Color.white);
        logoutButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginPage loginPage = new LoginPage();
                loginPage.createLayout(); 
                frame.setVisible(false);
            }
        });
        frame.add(logoutButton);
    }

    private void transfer(){
        JButton transferButton = new JButton("<html>Transfer<br />Fund</html>");
        transferButton.setBounds(260, 530, 100, 40);
        transferButton.setBackground(Color.green.darker());
        transferButton.setForeground(Color.white);
        transferButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFrame frame2 = new JFrame("Transfer Funds");
                    JLabel amountLabel = new JLabel("Enter amount: ");
                    amountLabel.setBounds(10, 0, 100, 30);
                    JTextField amountInput = new JTextField();
                    amountInput.setBounds(150, 0, 100, 30);
                    JLabel accountNumLabel = new JLabel("Enter reciver's account number: ");
                    accountNumLabel.setBounds(10, 50, 200, 30);
                    JTextField accountNumInput = new JTextField();
                    accountNumInput.setBounds(200,50,100,30);
                    JButton send = new JButton("Send");
                    send.setBounds(130,120,80,40);
                    send.setBackground(Color.GREEN.darker());
                    send.setForeground(Color.WHITE);
                    send.addActionListener(new ActionListener(){
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                user.transferFunds(Double.parseDouble(amountInput.getText()), accountNumInput.getText());
                                userBalanceLabel.setText("Balance: $" + user.getBalance());
                            }
                            catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                        
                    });
                    frame2.add(amountInput);
                    frame2.add(amountLabel);
                    frame2.add(accountNumLabel);
                    frame2.add(accountNumInput);
                    frame2.add(send);
                    frame2.setSize(400,250);
                    frame2.setLayout(null);
                    frame2.setVisible(true);
                    frame2.setResizable(false);
                    frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
        });
        frame.add(transferButton);
    }

    private void updateAccount(){
        JButton updateAccount = new JButton("<html>Update <br/> Account</html>");
        updateAccount.setBounds(410,530,100,40);
        updateAccount.setBackground(Color.green.darker());
        updateAccount.setForeground(Color.white);
        updateAccount.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame2 = new JFrame("Update Account");

                frame2.add(makeLabel("First Name", 10,0,100,30));
                frame2.add(userFirstNameInput = makeTextField(10, 30, 150, 30));
        
                frame2.add(makeLabel("Last Name", 200,0,100,30));
                frame2.add(userLastNameInput = makeTextField(200,30,150,30));
        
                frame2.add(makeLabel("Address",10,170,100,30));
                frame2.add(userAddressInput = makeTextField(10,200,150,30));
        
                frame2.add(makeLabel("Phone Number",10,80,200,30));
                frame2.add(userPhoneNumberInput = makeTextField(10,120,150,30));
        
                frame2.add(makeLabel("Email",200,80,100,30));
                frame2.add(userEmailInput = makeTextField(200,120,150,30));

                frame2.add(makeLabel("Password",200,170,200,30));
                userPasswordInput = new JPasswordField();
                userPasswordInput.setBounds(200,200,200,30);
                frame2.add(userPasswordInput);

                JButton updateAccount2 = new JButton("<html>Update <br/> Account</html>");
                updateAccount2.setBounds(130,250,100,40);
                updateAccount2.setBackground(Color.green.darker());
                updateAccount2.setForeground(Color.white);
                updateAccount2.addActionListener(new ActionListener(){
                    Map<String, String> userInfo = new HashMap<>();
                    String passwordString = new String(userPasswordInput.getPassword());
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(userFirstNameInput.getText().length() > 0){
                            userInfo.put("FirstName", userFirstNameInput.getText());
                        }
                        if(userLastNameInput.getText().length() > 0){
                            userInfo.put("LastName", userLastNameInput.getText());
                        }
                        if(userAddressInput.getText().length() > 0){
                            userInfo.put("HomeAddress", userAddressInput.getText());
                        }
                        if(userPhoneNumberInput.getText().length() > 0){
                            userInfo.put("PhoneNumber", userPhoneNumberInput.getText());
                        }
                        if(userEmailInput.getText().length() > 0){
                            userInfo.put("Email", userEmailInput.getText());
                        }
                        if(passwordString.length() > 0){
                            userInfo.put("password", passwordString);
                        }
                        user.updateAccount(userInfo, user.getAccountNumber());
                        successLabel = new JLabel("Account updated successfully!");
                        successLabel.setBounds(130,300,200,30);
                        successLabel.setForeground(Color.GREEN.darker());
                        frame2.add(successLabel);
                        frame2.repaint();
                    }
                });
                frame2.add(updateAccount2);
                frame2.setSize(450,400);
                frame2.setLayout(null);
                frame2.setVisible(true);
                frame2.setResizable(false);
                frame2.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            }
            
        });
        frame.add(updateAccount);
    }

    private JLabel makeLabel(String name, int x, int y, int width, int height){
        JLabel label = new JLabel(name);
        label.setBounds(x,y,width,height);
        label.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        return label;
    }

    private JTextField makeTextField(int x, int y, int width, int height){
        JTextField textField = new JTextField();
        textField.setBounds(x,y,width,height);
        return textField;
    }
}
