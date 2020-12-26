package tcp.local;

import gui.ChatGUILocal;
import resources.Configuration;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatLocal extends Thread {
    private ChatGUILocal chatGUILocal;
    private boolean keepRunning = true;
    private ServerSocket serverSocket;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    public ChatLocal() throws IOException {
        serverSocket = new ServerSocket(Configuration.CHAT_PORT);
        System.out.println("Server is started");
        this.chatGUILocal = new ChatGUILocal();
    }

    @Override
    public void run(){
        try {
            socket = serverSocket.accept();
            while(keepRunning) {
                if (socket != null) {
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    String msg = "";
                    while ((msg = dis.readUTF()) != null) {
                        //Nếu có tin nhắn đến thì ghi vào lịch sử
                        chatGUILocal.txaShowMessageLocal.append("\n Remote: " + msg);
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

            chatGUILocal.getMessage("Local", message);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
