package chatting.application;

// Packages imported in CHAT APPLICATION PROJECT
// For GUI , TIME MANAGEMENT, INPUT_OUTPUTand FILE HANDLING

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

// CLASSS SERVER 
public class Server extends JFrame implements ActionListener {

    // P1 is the Panel creater for HEADER SECTION which contains DP,Active status, other images
    // T1 is the TEXT_FIELD used to create the MESSAGE_TYPING text area
    // B1 Button is created to send message
    // A1 Panel is created To create a area wher messages will Appear
    JPanel P1;
    JTextField T1;
    JButton B1;
    static JPanel A1;
    static Box vertical = Box.createVerticalBox();
    //used for socket programming
    static ServerSocket skt;
    static Socket s;
    // used for data transfer
    static DataInputStream din;
    static DataOutputStream dout;
    // TYPING variable used to check whether the current person is typing or not
    Boolean typing;
    static JFrame F1 = new JFrame();

    // all coding part of Jframe is done in the constructor
    Server() {
        
        F1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        // HEADER SECTION
        // creating a panel P1 to show the the back button DP ,videocall, image, and settinfs buttons and options       
        P1 = new JPanel();
        P1.setLayout(null);
        P1.setBackground(new Color(18, 140, 126));
        P1.setBounds(0, 0, 450, 70);
        F1.add(P1);

        // BACK BUTTON IMAGE AND FUNCTIONALITY
        // getSystemResources -> function to take resources like images and files from system
        ImageIcon I1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3.png"));
        Image I2 = I1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon I3 = new ImageIcon(I2);
        JLabel L1 = new JLabel(I3);
        L1.setBounds(5, 15, 30, 30);
        P1.add(L1);
        // adding FUNCTIONALITY to BACK BUTTON IMAGE
        L1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                //if clicked then exit thesystem SYSTEM_CALL
                System.exit(0);
            }
        });

        //adding DP - PROFILE IMAGE
        // setting the DP image of VIRAT_KOHLI
        ImageIcon I4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/virat-modified.png"));
        Image I5 = I4.getImage().getScaledInstance(65, 65, Image.SCALE_DEFAULT);
        ImageIcon I6 = new ImageIcon(I5);
        JLabel L2 = new JLabel(I6);
        L2.setBounds(42, 4, 65, 65);
        P1.add(L2);

        //writing VIRAT KHOLI NAME on the panel
        Label L3 = new Label("Virat Kohli");
        L3.setFont(new Font("SAN_SERIF", Font.BOLD, 17));
        L3.setBounds(115, 20, 120, 20);
        L3.setForeground(Color.WHITE);
        P1.add(L3);

        // adding ACTIVE_NOW STATUS
        Label L4 = new Label("Active Now");
        L4.setForeground(Color.WHITE);
        L4.setFont(new Font("SAN_SERIF", Font.PLAIN, 12));
        L4.setBounds(115, 40, 120, 20);
        P1.add(L4);
        //using timer function for changing typing status
        Timer t = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (!typing) {
                    L4.setText("Active Now");
                }
            }
        });
        t.setInitialDelay(3000);
        // VIDEO_CALL SECTION
        ImageIcon I7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
        Image I8 = I7.getImage().getScaledInstance(30, 40, Image.SCALE_DEFAULT);
        ImageIcon I9 = new ImageIcon(I8);
        JLabel L5 = new JLabel(I9);
        L5.setBounds(290, 15, 30, 40);
        P1.add(L5);

        // VOICE_CALL SECTION
        ImageIcon I10 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
        Image I11 = I10.getImage().getScaledInstance(30, 40, Image.SCALE_DEFAULT);
        ImageIcon I12 = new ImageIcon(I11);
        JLabel L6 = new JLabel(I12);
        L6.setBounds(350, 15, 30, 40);
        P1.add(L6);

        // SETTING SECTION IMAGE
        ImageIcon I13 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3icon.png"));
        Image I14 = I13.getImage().getScaledInstance(10, 30, Image.SCALE_DEFAULT);
        ImageIcon I15 = new ImageIcon(I14);
        JLabel L7 = new JLabel(I15);
        L7.setBounds(410, 20, 10, 30);
        P1.add(L7);

        // making SCROLLABLE PANEL to read messages
        A1 = new JPanel();
        A1.setBackground(new Color(220, 248, 198));
        A1.setFont(new Font("SAN_SERIF", Font.PLAIN, 17));
        // Adding Scrollable feature       
        JScrollPane JS=new JScrollPane(A1);
        JS.setBounds(5, 75, 440, 560);
        JS.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        JS.setBorder(BorderFactory.createEmptyBorder());
        F1.add(JS);
        
        // MESSAGE WRITING AREA
        T1 = new JTextField();
        T1.setBounds(10, 645, 355, 40);
        T1.setFont(new Font("SAN_SERIF", Font.PLAIN, 15));
        F1.add(T1);
        
        //Typing Status
        // IIf the keys are pressed while typing then change status to typing
        // KEY LISTENER FUNCTIONALITY
        T1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                L4.setText("Typing......");
                t.stop();
                typing = true;
            }

            public void keyReleased(KeyEvent ke) {
                L4.setText("Active Now");
                typing = false;
                if (!t.isRunning()) {
                    t.start();
                }

            }
        });
        // SEND BUTTON
        B1 = new JButton("Send");
        B1.setBounds(365, 645, 80, 40);
        B1.setBackground(new Color(18, 140, 126));
        B1.setForeground(new Color(220, 248, 198));
        B1.setFont(new Font("SAN_SERIF", Font.BOLD, 15));
        // addind functionlity to button SEND
        B1.addActionListener(this);
        F1.add(B1);

        // since we will be designing our own layout
        // default layout provided is BORDER-LAYOUT
        F1.setLayout(null);
        F1.setUndecorated(true);
        F1.setSize(450, 700);
        F1.setLocation(300, 100);
        // GIVING BORDER TO FRAME
        F1.getRootPane().setBorder(
        BorderFactory.createMatteBorder(7,7,7,6, new Color(67, 90, 100))
);
        F1.setVisible(true);
        
    }
    
    // WHAT ACTIONS WILL BE PERFORMED WHEN SEND BUTTON IS PRESSED
    // Message ttransfer from one user to other
    // append that message in area panel A1
    public void actionPerformed(ActionEvent ae) {
        //overriding ACTION_LISTENER package function
        try {
            
            // Take Text from TEXTAREA and send it to other user
            String out = T1.getText();
            SendTextToFile(out,"Virat Kohli");
            dout.writeUTF(out);
            
            // make that message to append in AREA PANEL A1
            JPanel p = formatLabel(out, (int) 0);
            // The panelp containd message sent along with its TIME on the right side 
            A1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(new Color(220, 248, 198));
            right.add(p, BorderLayout.LINE_END);
            vertical.add(right);
            A1.add(vertical, BorderLayout.PAGE_START);
            F1.validate();
            
            T1.setText("");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // NOTE TO HAVE A DATABASE WE MUST MAKE A FILE
    // ADDING CHAT DATA TO THE CHAT FILE FOR BACK_UP PURPOSE
     public void SendTextToFile(String message,String name) throws FileNotFoundException
     {
         try(PrintWriter pw = new PrintWriter(new FileOutputStream(new File("Chat.txt"), true)); )
         {
             pw.println(name+" :- "+message);
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
     }
    // FOR SPEECH BUBBLES
    // Styling the message boxarea
    public static JPanel formatLabel(String out, int i) {
        JPanel k = new JPanel();
        k.setLayout(new BoxLayout(k, BoxLayout.Y_AXIS));
        // label created to display text
        // SETTING THE MAXIMUM WIDTH OF THE MESSAGE BOX
        JLabel L = new JLabel("<html><p style = \"width: 150px\">" + out + "</p></html>");
        
        if (i == 1)
        {
            // that means we are sending message
            L.setBackground(new Color(52, 183, 241));
        } else 
        {
            // we are reading the message
            L.setBackground(new Color(236, 229, 221));
        }
        L.setOpaque(true);
        L.setFont(new Font("Helvetica", Font.PLAIN, 17));
        L.setBorder(new EmptyBorder(10, 10, 10, 40));
        k.add(L);

        // label created to add time with the message
        // TIME WILL BE DISPLAYED ALONG WITH THE MESSAGE SENT
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH::mm");
        JLabel L2 = new JLabel();
        L2.setBackground(new Color(220, 248, 198));
        L2.setText(sdf.format(cal.getTime()));

        k.add(L2);
        return k;
    }

    // main method
    public static void main(String[] args) {
        new Server().F1.setVisible(true);

        // SOCKET PROGRAMMING or  NETWORK PROGRAMMING  PART
        String MessageInput = "";
        try {
            skt = new ServerSocket(6000);

            while (true) {
                // waiting for the conection to establish
                s = skt.accept();
                // connection established
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    MessageInput = din.readUTF();
                    // showing the input message
                     A1.setLayout(new BorderLayout());
                    JPanel p2 = formatLabel(MessageInput, (int) 1);
                    JPanel left = new JPanel(new BorderLayout());
                    left.setBackground(new Color(220, 248, 198));
                    left.add(p2, BorderLayout.LINE_START);
                    vertical.add(left);
                    
                    A1.add(vertical,BorderLayout.PAGE_START);
                    F1.validate();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
