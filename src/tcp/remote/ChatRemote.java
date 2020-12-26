package tcp.remote;

import gui.ChatGUI;
import resources.Configuration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ChatRemote extends Thread {
    private ChatGUI chatGUI;
    private boolean keepRunning = true;
    private Socket socket;
    private InputStream inputStream;
    public ChatRemote() throws IOException {
        this.chatGUI = new ChatGUI();
//        chatGUI.setVisible(true);
//        this.socket = new Socket();
//        this.socket.connect(new InetSocketAddress(StringResource.REMOTE_IP, Configuration.STREAM_PORT), Configuration.AUTHENTICATE_TIMEOUT);
        socket = new Socket("127.0.0.1", Configuration.CHAT_PORT);
        this.inputStream = socket.getInputStream();
    }
    @Override
    public void run(){
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while(keepRunning) {
                if (socket != null) {
                    String msg = "";
                    while ((msg = dis.readUTF()) != null) {
                        //Nếu có tin nhắn đến thì ghi vào lịch sử
                        chatGUI.txaShowMessage.append("\n Local: " + msg);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String message){
        if (message.isEmpty()) return;
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message);
            dos.flush();

            chatGUI.getMessage("Remote", message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
