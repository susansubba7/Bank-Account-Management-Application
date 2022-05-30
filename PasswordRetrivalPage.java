import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class PasswordRetrivalPage {
    private JFrame frame;
    private DatabaseConnection connection;
    private Map<String, String> securityQuestions;

    /**
     * Constructor for PasswordRetrivalPage which initalizes variables
     */
    public PasswordRetrivalPage(){
        connection = new DatabaseConnection();
        frame = new JFrame();
        createLayout();
    }

    private void createLayout(){
        JLabel label1 = new JLabel("Forgot your password?");
        label1.setBounds(130,0,300,50);
        label1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
        frame.add(label1);

        JButton backButton = new JButton("Back");
        backButton.setBounds(20,20,80,20);
        backButton.setForeground(Color.white);
        backButton.setBackground(Color.gray);
        backButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new LoginPage().createLayout();;
            }
            
        });
        frame.add(backButton);
        getUserName();
        frame.setSize(500,500);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private boolean showSecurityQuestions(String userName){
        securityQuestions = connection.getSecurityQuestion(userName);
        if(securityQuestions.size() == 0){
            return false;
        }
        String questionOne = securityQuestions.get("questionOne");
        String answerOne = securityQuestions.get("answerOne");
        String questionTwo = securityQuestions.get("questionTwo");
        String asnwerTwo = securityQuestions.get("answerTwo");
        
        JLabel label2 = new JLabel("Please answer these security questions");
        label2.setBounds(130,30,300,30);

        JLabel questionOneLabel = new JLabel();
        questionOneLabel.setText(questionOne);
        questionOneLabel.setBounds(80,80,300,30);
        JTextField questionOneInput = new JTextField();
        questionOneInput.setBounds(80,120,300,30);

        
        JLabel questionTwoLabel = new JLabel();
        questionTwoLabel.setText(questionTwo);
        questionTwoLabel.setBounds(80,150,300,30);
        JTextField questionTwoInput = new JTextField();
        questionTwoInput.setBounds(80,180,300,30);

        JLabel incorredtAnswer = new JLabel("Incorrect answer!");
        incorredtAnswer.setBounds(130,250,200,30);
        incorredtAnswer.setForeground(Color.red);
        
        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(130,230,200,20);
        submitButton.setBackground(Color.GREEN.darker());
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(questionOneInput.getText().equals(answerOne) && questionTwoInput.getText().equals(asnwerTwo)){
                    JLabel userPassowrd = new JLabel("Your password: " + securityQuestions.get("password"));
                    userPassowrd.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
                    userPassowrd.setForeground(Color.BLUE.darker());
                    userPassowrd.setBounds(130,300,300,20);
                    frame.add(userPassowrd);
                }
                frame.repaint();
            } 
        });

        
        frame.add(submitButton);
        frame.add(questionOneLabel);
        frame.add(label2);
        frame.add(questionOneInput);
        frame.add(questionTwoInput);
        frame.add(questionTwoLabel);
        return true;
    }
    
    private void getUserName(){
        JLabel userNameLabel = new JLabel("Please enter your username");
        userNameLabel.setBounds(130,100,200,50);
        frame.add(userNameLabel);

        JTextField userNameInput = new JTextField();
        userNameInput.setBounds(130,150,200,20);
        frame.add(userNameInput);

        JLabel message = new JLabel("Invalid username!");
        message.setBounds(13,250,200,30);
        message.setForeground(Color.red);

        JButton resetPasswordButton = new JButton("Retrive Password");
        resetPasswordButton.setBounds(130,200,200,30);
        resetPasswordButton.setBackground(Color.green.darker());
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameInput.getText();
                if(showSecurityQuestions(userName)){
                    resetPasswordButton.setVisible(false);
                    userNameInput.setVisible(false);
                    userNameLabel.setVisible(false);
                    message.setVisible(false);
                }
                else{
                    frame.add(message);
                }
                frame.repaint();
            } 
        });
        frame.add(resetPasswordButton);
    }
}
