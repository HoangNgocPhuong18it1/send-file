package gui;

import tcp.local.ChatLocal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChatGUILocal extends JFrame {

    private JPanel contentPane;
    private JTextField txtMessage;
    public JTextArea txaShowMessageLocal;
    private static ChatLocal chatLocal;

    /**
     * Launch the application.
     */
    public static void main(String[] args) throws IOException {
        chatLocal= new ChatLocal();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChatGUILocal frame = new ChatGUILocal();
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
    public ChatGUILocal() {
        setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\wallpaper\\logo\\app_icon.png"));
        setTitle("Chat local");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 524, 431);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 488, 301);
        contentPane.add(scrollPane);

        txaShowMessageLocal = new JTextArea();
        txaShowMessageLocal.setEditable(false);
        txaShowMessageLocal.setBorder(null);
        scrollPane.setViewportView(txaShowMessageLocal);

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

                chatLocal.sendMessage(txtMessage.getText());
                txaShowMessageLocal.append("\n Local: " + txtMessage.getText());

            }
        });
    }
    public void getMessage(String sender, String message){
        txaShowMessageLocal.append("\n" + sender + ": " + message);
    }
}

