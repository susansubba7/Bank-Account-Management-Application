/**
This class deals with user login interface
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginPage{
    private JButton loginButton;
    private JButton signUpButton;
    private JTextField userIdInput;
    private JPasswordField userPasswordInput;
    private JLabel statusLabel;
    private JFrame frame;

    /**
     * Creates a user interface with differet buttons
     * Buttons include login, signup, and forgot password
     */
    public void createLayout(){
        frame = new JFrame("My Banking Portal");

        JLabel title = new JLabel("User Login");
        title.setBounds(50,0,400,100);
        title.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        frame.add(title);

        JLabel userIDLabel = new JLabel("User ID");
        userIDLabel.setBounds(50,100,100,30);
        frame.add(userIDLabel);

        userIdInput = new JTextField();
        userIdInput.setBounds(150,100,150,30);
        frame.add(userIdInput);


        JLabel userPasswordLabel = new JLabel("Password");
        userPasswordLabel.setBounds(50,150,100,30);
        frame.add(userPasswordLabel);

        userPasswordInput = new JPasswordField();
        userPasswordInput.setBounds(150,150,150,30);
        frame.add(userPasswordInput);

        loginButton = new JButton("Login");
        loginButton.setBounds(50,200,100,30);
        loginButton.setBackground(Color.green.darker());
        loginButton.setForeground(Color.white);
        frame.add(loginButton);
        loginButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setVisible(false);
                    new UserAccountPage(userIdInput.getText(), new String(userPasswordInput.getPassword()));
                } 
                catch (Exception x){
                    createLayout();
                    JLabel fail = new JLabel("Incorrect username or password\n, please try again");
                    fail.setBounds(20,300,300,30);
                    fail.setForeground(Color.red);
                    frame.add(fail);
                }
                
            }
        });

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(250,200,100,30);
        signUpButton.setBackground(Color.blue.brighter());
        signUpButton.setForeground(Color.white);
        frame.add(signUpButton);
        signUpButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    frame.setVisible(false);
                    SignUpPage signUpPage = new SignUpPage();
                    signUpPage.createLayout();
                } 
                catch (Exception x){
                    x.printStackTrace();
                }
            }
        });

        JButton forgotPasswordButton = new JButton("Forgot Password");
        forgotPasswordButton.setBounds(125,250,150,30);
        forgotPasswordButton.setBackground(Color.lightGray);

        forgotPasswordButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                    frame.setVisible(false);
                    new PasswordRetrivalPage();

            }
        });
        frame.add(forgotPasswordButton);

        statusLabel = new JLabel();
        statusLabel.setBounds(150,300,100,30);
        statusLabel.setVisible(false);
        frame.add(statusLabel);

        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}