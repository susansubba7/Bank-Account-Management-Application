import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.WindowConstants;


public class SignUpPage {
    private JButton signUpButton;
    private JPasswordField userPasswordInput;
    private JLabel statusLabel;
    private JFrame frame;
    private JTextField userIdInput, userSSNInput, userFirstNameInput, userLastNameInput, userBalanceInput, userDOBInput, userAddressInput, userPhoneNumberInput, userEmailInput, userQuestionOneAnswer, userQuestionTwoAnswer;
    private String questionOne, questionTwo;
    
    /**
     * Creates different JLabel and JButton the adds it to JFrame
     * Asks user for information to make a user account
     * JButtons have actionlistener added to the giving them different funcutionality 
     */
    public void createLayout(){
        frame = new JFrame("My Banking Portal");

        JLabel title = new JLabel("Sign Up");
        title.setBounds(50,0,400,100);
        title.setFont(new Font(Font.SANS_SERIF,Font.BOLD,50));
        frame.add(title);

        frame.add(makeLabel("First Name", 50,100,100,30));
        frame.add(userFirstNameInput = makeTextField(50, 130, 150, 30));

        frame.add(makeLabel("Last Name", 250,100,100,30));
        frame.add(userLastNameInput = makeTextField(250,130,150,30));

        frame.add(makeLabel("Address",450,100,100,30));
        frame.add(userAddressInput = makeTextField(450,130,150,30));

        frame.add(makeLabel("Phone Number",50,180,200,30));
        frame.add(userPhoneNumberInput = makeTextField(50,230,150,30));

        frame.add(makeLabel("Email",250,180,100,30));
        frame.add(userEmailInput = makeTextField(250,230,150,30));

        frame.add(makeLabel("Date of Birth",450,180,200,30));
        frame.add(userDOBInput = makeTextField(450,230,150,30));

        frame.add(makeLabel("Social Security",50,280,200,30));
        frame.add(userSSNInput = makeTextField(50,330,150,30));

        frame.add(makeLabel("User Name",250,280,200,30));
        frame.add(userIdInput = makeTextField(250,330,150,30));

        frame.add(makeLabel("Password",450,280,200,30));
        userPasswordInput = new JPasswordField();
        userPasswordInput.setBounds(450,330,150,30);
        frame.add(userPasswordInput);

        String questions[] = {"What city where you born in?", "What is the color of your first car?", "Where did your parents meet?", "What is the make of your first car?", "What city where you born in?",
        "What city was you mother born in?", "What is the color of your dad's eyes?"};
        JComboBox<String> securityQuestionOne = new JComboBox<>(questions);
        securityQuestionOne.setBounds(50,400,250,30);
        frame.add(securityQuestionOne);
        securityQuestionOne.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                questionOne = securityQuestionOne.getSelectedItem().toString();
            }
        });
        frame.add(userQuestionOneAnswer = makeTextField(50,450,250,30));
        
        JComboBox<String> securityQuestionTwo = new JComboBox<>(questions);
        securityQuestionTwo.setBounds(350,400,250,30);
        frame.add(securityQuestionTwo);
        securityQuestionTwo.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                questionTwo = securityQuestionTwo.getSelectedItem().toString();
            }
        });
        frame.add(userQuestionTwoAnswer = makeTextField(350,450,250,30));

        frame.add(makeLabel("Intial Deposit",50,500,200,30));
        frame.add(userBalanceInput = makeTextField(50, 550, 200, 30));

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(500,530,100,50);
        signUpButton.setBackground(Color.GREEN.darker());
        frame.add(signUpButton);
        signUpButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String passwordString = new String(userPasswordInput.getPassword());
                try {
                    if(userIdInput.getText().length() == 0 || userFirstNameInput.getText().length() == 0 || passwordString.length() == 0 || userLastNameInput.getText().length() == 0 || userSSNInput.getText().length() == 0 || userDOBInput.getText().length() == 0 || 
                    userAddressInput.getText().length() == 0 || userPhoneNumberInput.getText().length() == 0 || userEmailInput.getText().length() == 0 || userQuestionOneAnswer.getText().length() == 0 || userQuestionTwoAnswer.getText().length() == 0){
                        JLabel missingInformationLabel = new JLabel("<html>You are missing <br/> some information </html>");
                        missingInformationLabel.setBounds(280, 530, 200, 50);
                        missingInformationLabel.setForeground(Color.red);
                        frame.add(missingInformationLabel);
                        frame.repaint();
                    }
                    else if(questionOne.length() == 0 || questionTwo == null){
                        JLabel notSelected = new JLabel("Please select a security question");
                        notSelected.setBounds(280,530,200,50);
                        notSelected.setForeground(Color.red);
                        frame.add(notSelected);
                        frame.repaint();
                    }
                    else if(userQuestionOneAnswer.getText().length() == 0 || userQuestionTwoAnswer.getText().length() == 0){
                        JLabel noAnswerLabel = new JLabel("<html>Please enter an answer <br/> for security question</html>");
                        noAnswerLabel.setBounds(280,530,200,50);
                        noAnswerLabel.setForeground(Color.red);
                        frame.add(noAnswerLabel);
                        frame.repaint();
                    }
                    else{
                        frame.setVisible(false);
                        new UserAccountPage(userIdInput.getText(), new String(userPasswordInput.getPassword()), userFirstNameInput.getText(), userLastNameInput.getText(), userSSNInput.getText(), Double.parseDouble(userBalanceInput.getText()), userDOBInput.getText(), 
                        userAddressInput.getText(), userPhoneNumberInput.getText(), userEmailInput.getText(),userQuestionOneAnswer.getText(), userQuestionTwoAnswer.getText(), questionOne, questionTwo);
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                } 
            }
        });
        statusLabel = new JLabel();
        statusLabel.setBounds(150,300,100,30);
        statusLabel.setVisible(false);
        frame.add(statusLabel);

        frame.setSize(650,650);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
