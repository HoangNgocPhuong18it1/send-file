package gui;

import tcp.remote.ChatRemote;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatGUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtMessage;
    public JTextArea txaShowMessage;
    private ChatRemote chatRemote;

    /**
     * Launch the application.
     */
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatGUI frame = new ChatGUI();
                    frame.setVisible(true);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Create the frame.
     */
    public ChatGUI() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\wallpaper\\logo\\app_icon.png"));
        setTitle("Chat");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 524, 431);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 488, 301);
        contentPane.add(scrollPane);

        txaShowMessage = new JTextArea();
        txaShowMessage.setEditable(false);
        txaShowMessage.setBorder(null);
        scrollPane.setViewportView(txaShowMessage);

        txtMessage = new JTextField();
        txtMessage.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.GRAY));
        txtMessage.setBounds(10, 324, 427, 44);
        contentPane.add(txtMessage);
        txtMessage.setColumns(10);

        JButton btnSendMessage = new JButton("");
        btnSendMessage.setFocusable(false);
        btnSendMessage.setBorder(null);
        btnSendMessage.setBackground(new Color(30, 144, 255));
        btnSendMessage.setIcon(new ImageIcon("D:\\wallpaper\\logo\\sendMs.png"));
        btnSendMessage.setBounds(453, 323, 45, 45);
        contentPane.add(btnSendMessage);
        btnSendMessage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    chatRemote = new ChatRemote();
                    chatRemote.sendMessage(txtMessage.getText());
                    txaShowMessage.append("\n Remote: " +txtMessage.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });
    }
    public void getMessage(String sender, String message){
        txaShowMessage.append("\n" + sender + ": " + message);
    }
}

