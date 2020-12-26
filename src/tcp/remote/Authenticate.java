package tcp.remote;

import resources.Configuration;
import resources.StringResource;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Authenticate {
    private Socket socket;
    private String ip;
    private DataInputStream dis;
    private DataOutputStream dos;
    private static Authenticate auth;

    public static Authenticate getInstance(String ip) throws IOException {
        if(auth == null)
            auth = new Authenticate(ip);
        return auth;
    }

    public Authenticate(String ip) throws IOException {
        this.ip = ip;
        this.socket = new Socket();
        socket.connect(
                new InetSocketAddress(this.ip, Configuration.AUTHENTICATE_PORT),
                Configuration.AUTHENTICATE_TIMEOUT);
        dis = new DataInputStream(socket.getInputStream());
        dos = new DataOutputStream(socket.getOutputStream());
    }

    public boolean auth(String password){
        try {
            dos.writeUTF(password);
            String result = dis.readUTF();
            if (result.equals(StringResource.AUTH_SUCCESS)) {
                StringResource.REMOTE_IP = ip;
                dis.close();
                dos.close();
                socket.close();
                return true;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return false;
    }

}
