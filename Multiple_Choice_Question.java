import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Multiple_Choice_Question {
    public static Connection con;
    public static int queNo = 1;
    public static String ans = " ";
    public static int marks = 0;
    public static int total = 0;
    public static String OriAns = " ";
    public static  JFrame fr = new JFrame("MULTIPLE CHOICE QUESTIONS");
    public static JLabel label = new JLabel();
    public static JRadioButton A = new JRadioButton();
    public static JRadioButton B = new JRadioButton();
    public static JRadioButton C = new JRadioButton();
    public static JRadioButton D = new JRadioButton();

    public static void main(String[] args) throws Exception {

        // database connection
        String dbURL = "jdbc:mysql://localhost:3306/gopaldb";
        String dbroot = "root";
        String dbPassword = "jgkrishna@96";
        con = DriverManager.getConnection(dbURL, dbroot, dbPassword);

        // frame
        fr.setSize(1000, 600);
        fr.setResizable(false);
        fr.setLayout(null);

        JLabel labeltitle = new JLabel("                                 MCQ's Test");
        labeltitle.setFont(new Font("Arial", Font.BOLD, 40));  // Applying changes here
        labeltitle.setBounds(0, 30, 1000, 60);
        labeltitle.setForeground(Color.WHITE);
        labeltitle.setBackground(new Color(0, 102, 204));
        labeltitle.setOpaque(true);
        fr.add(labeltitle);

        // label
        label.setBounds(40, 130, 1000, 40);
        label.setFont(new Font("Arial", Font.BOLD, 25));  // Applying changes here
        label.setOpaque(true);
        fr.add(label);

        // options
        A.setBounds(60, 200, 600, 30);
        A.setFont(new Font("Arial", Font.PLAIN, 15));  // Applying changes here
         A.setBackground(new Color(255, 255, 255));
        A.setBorderPainted(true);
        A.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ans = "A";
            }
        });
        fr.add(A);

        B.setBounds(60, 250, 600, 30);
        B.setFont(new Font("Arial", Font.PLAIN, 15));  // Applying changes here
         B.setBackground(new Color(255, 255, 255));
        B.setBorderPainted(true);
        B.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ans = "B";
            }
        });
        fr.add(B);

        C.setBounds(60, 300, 600, 30);
        C.setFont(new Font("Arial", Font.PLAIN, 15));  // Applying changes here
        C.setBorderPainted(true);
        C.setBackground(new Color(255, 255, 255));
        C.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ans = "C";
            }
        });
        fr.add(C);

        D.setBounds(60, 350, 600, 30);
        D.setFont(new Font("Arial", Font.PLAIN, 15));  // Applying changes here
        D.setBackground(new Color(255, 255, 255));
        D.setBorderPainted(true);
        D.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ans = "D";
            }
        });
        fr.add(D);

        // Create a ButtonGroup to group the radio buttons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(A);
        buttonGroup.add(B);
        buttonGroup.add(C);
        buttonGroup.add(D);

        // for question number
        gettingMCQS(String.valueOf(queNo));

        // button
        JButton button2 = new JButton("SUBMIT");
        button2.setBounds(750, 450, 150, 60);
        button2.setFocusable(false);
        button2.setFont(new Font("Arial", Font.BOLD, 18));  // Applying changes here
        button2.setForeground(Color.WHITE);
        button2.setBackground(new Color(0, 102, 204));
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ans.equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Please Select any Option", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    buttonGroup.clearSelection();
                    // checking
                    if (ans.equals(OriAns))
                        marks++;
                    ans = " ";
                    // for question number
                    queNo++;
                    gettingMCQS(String.valueOf(queNo));
                }

            }
        });
        fr.add(button2);

        fr.setVisible(true);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void gettingMCQS(String quesNo){
        try{
            String sqlSDA="SELECT * FROM MCQ_TABLE WHERE QID="+quesNo;
            Statement pstSDA = con.createStatement();
            ResultSet resl = pstSDA.executeQuery(sqlSDA);
            int suc=0;
            if(resl.next()){
                label.setText(quesNo+")"+resl.getString("QUESTIONS"));
                A.setText(resl.getString("A"));
                B.setText(resl.getString("B"));
                C.setText(resl.getString("C"));
                D.setText(resl.getString("D"));
                OriAns=resl.getString("ANS");
                total++;
                suc=1;
            }
            if(suc==0){
                JOptionPane.showMessageDialog(null, "You Have Scored " + marks+"/"+total, "RESULT", 1);
                fr.dispose();
            }
        }
        catch(Exception e) {
            System.out.println("Failed Show QUESTIONS");
        }
    }
}