package tcp.local;

import resources.Configuration;
import resources.ControlCommand;
import resources.StringResource;

import javax.swing.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FileTransferLocal {

    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private DataOutputStream dos;
    private DataInputStream dis;


    public FileTransferLocal() throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(StringResource.REMOTE_IP, Configuration.FILE_TRANSFER_PORT), Configuration.AUTHENTICATE_TIMEOUT);

        dis = new DataInputStream(socket.getInputStream());
        dos= new DataOutputStream(socket.getOutputStream());
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());


    }

    public File[] openFolder(String path){
        File[] result = null;
        try {
            dos.writeInt(ControlCommand.FILE_TRANSFER_OPEN_FOLDER);
            dos.writeUTF(path);
            result = (File[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public File[] backFolder(String path){
        File[] result = null;
        try {
            dos.writeInt(ControlCommand.FILE_TRANSFER_BACK_FOLDER);
            dos.writeUTF(path);
            result = (File[]) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public JLabel[] getIcon(String path){
        JLabel[] result = null;
        try {
            dos.writeInt(ControlCommand.FILE_TRANSFER_GET_ICON);
            dos.writeUTF(path);
            result = (JLabel[])  ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    public JLabel[] getIconBack(String path){
        JLabel[] result = null;
        try {
            dos.writeInt(ControlCommand.FILE_TRANSFER_GET_ICON_BACK);
            dos.writeUTF(path);
            result = (JLabel[])  ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String sendFile(String pathSend, String pathReceive){
        String result = null;
        try {
            dos.writeInt(ControlCommand.FILE_TRANSFER_SEND_LOCAL_TO_REMOTE);
            // get file info
            FileInfo fileInfo = getFileInfo(pathSend, pathReceive);

            // send file
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(fileInfo);

            // get confirmation
            ois = new ObjectInputStream(socket.getInputStream());
            fileInfo = (FileInfo) ois.readObject();
            if (fileInfo != null) {
                result = fileInfo.getStatus();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;

    }
    public String getFile(String pathSend, String pathReceive){
        String result = null;
        try {
            dos.writeInt(ControlCommand.FILE_TRANSFER_SEND_REMOTE_TO_LOCAL);
            dos.writeUTF(pathSend);
            dos.writeUTF(pathReceive);
            ois = new ObjectInputStream(socket.getInputStream());
            FileInfo fileInfo = (FileInfo) ois.readObject();
            if (fileInfo != null) {
                createFile(fileInfo);
                result = "success";
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    private FileInfo getFileInfo(String sourceFilePath, String destinationDir) {
        FileInfo fileInfo = null;
        BufferedInputStream bis = null;
        try {
            File sourceFile = new File(sourceFilePath);
            bis = new BufferedInputStream(new FileInputStream(sourceFile));
            fileInfo = new FileInfo();
            byte[] fileBytes = new byte[(int) sourceFile.length()];
            // get file info
            bis.read(fileBytes, 0, fileBytes.length);
            fileInfo.setFilename(sourceFile.getName());
            fileInfo.setDataBytes(fileBytes);
            fileInfo.setDestinationDirectory(destinationDir);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fileInfo;
    }
    private boolean createFile(FileInfo fileInfo) {
        BufferedOutputStream bos = null;

        try {
            if (fileInfo != null) {
                File fileReceive = new File(fileInfo.getDestinationDirectory()
                        + fileInfo.getFilename());
                bos = new BufferedOutputStream(
                        new FileOutputStream(fileReceive));
                // write file content
                bos.write(fileInfo.getDataBytes());
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
