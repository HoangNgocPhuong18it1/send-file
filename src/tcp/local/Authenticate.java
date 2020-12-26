package tcp.local;

import gui.HomeGUI;
import resources.Configuration;
import resources.StringResource;
import tcp.remote.FileTransferRemote;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Authenticate {
    private ServerSocket serverSocket;
    private Socket socket;
    private boolean keepRunning = true;
    private Thread thread;
    private String password;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Toolkit toolkit;
    private HomeGUI homeGUI;

    public Authenticate(HomeGUI homeGUI, String password) throws IOException {
        serverSocket = new ServerSocket(Configuration.AUTHENTICATE_PORT);
        this.password = password;
        toolkit = Toolkit.getDefaultToolkit();
        this.homeGUI = homeGUI;
    }

    public void stop() throws IOException {
        serverSocket.close();
        keepRunning = false;
        thread = null;
    }

    public void startListen(){
        if(thread != null)
            keepRunning = false;
        keepRunning = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        while(keepRunning) {
                            socket = serverSocket.accept();
                            dis = new DataInputStream(socket.getInputStream());
                            dos = new DataOutputStream(socket.getOutputStream());
                            checkPassword();
                        }
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        });
        thread.start();
    }

    private void checkPassword() throws IOException, InterruptedException {
        String receivePassword;
        receivePassword = dis.readUTF();
        if(receivePassword.equals(this.password)) {
            dos.writeUTF(StringResource.AUTH_SUCCESS);


            new FileTransferRemote().start();
            dis.close();
            dos.close();
            homeGUI.dispose();
            stop();
        }
        else {
            dos.writeUTF(StringResource.AUTH_FAILED);
            checkPassword();
        }
    }
}
