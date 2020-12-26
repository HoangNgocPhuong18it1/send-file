package tcp.remote;

import resources.Configuration;
import resources.ControlCommand;
import tcp.local.FileInfo;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileTransferRemote extends Thread{
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private boolean keepRunning = true;

    public FileTransferRemote() throws IOException {
        serverSocket = new ServerSocket(Configuration.FILE_TRANSFER_PORT);
    }

    @Override
    public void run(){
        try {
            socket = serverSocket.accept();
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos= new DataOutputStream(socket.getOutputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            while (keepRunning){
                int command = dis.readInt();
                switch (command){
                    case ControlCommand
                            .FILE_TRANSFER_OPEN_FOLDER :
                        String path = dis.readUTF();
                        if (path.equals("null")){
                            File folder = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
                            File[] listFile = folder.listFiles();
                            oos.writeObject(listFile);
                        }else {
                            File folder = new File(path);
                            File[] listFile = folder.listFiles();
                            oos.writeObject(listFile);
                        }
                        break;
                    case ControlCommand
                            .FILE_TRANSFER_BACK_FOLDER :
                        String pathBack = dis.readUTF();
//                        if (pathBack.equals("null")){
//                            File folder = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
//                            File[] listFile = folder.listFiles();
//                            oos.writeObject(listFile);
//                        }else {
                            File folder = new File(pathBack);
                            File folderBack = folder.getParentFile();
                            File[] listFile = folderBack.listFiles();
                            oos.writeObject(listFile);
//                        }
                        break;
                    case ControlCommand
                            .FILE_TRANSFER_GET_ICON :
                        String pathGetIcon = dis.readUTF();
                        if (pathGetIcon.equals("null")){
                            File folderGetIcon = new File(FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath());
                            File[] listFileGetIcon = folderGetIcon.listFiles();
                            int index = 0;
                            JLabel labels[] = new JLabel[listFileGetIcon.length];
                            for(File f : listFileGetIcon){
                                Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
                                JLabel label = new JLabel(f.getName());
                                label.setIcon(icon);
                                labels[index++] = label;
                            }
                            oos.writeObject(labels);
                        }else {

                            File folderGetIcon = new File(pathGetIcon);
                            File[] listFileGetIcon = folderGetIcon.listFiles();
                            int index = 0;
                            JLabel labels[] = new JLabel[listFileGetIcon.length];
                            for(File f : listFileGetIcon){
                                Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
                                JLabel label = new JLabel(f.getName());
                                label.setIcon(icon);
                                labels[index++] = label;
                            }
                            oos.writeObject(labels);
                        }
                        break;
                    case ControlCommand
                            .FILE_TRANSFER_GET_ICON_BACK :
                            String pathGetIconBack = dis.readUTF();
                            File folderIconBack = new File(pathGetIconBack);
                            File folderParentBack = folderIconBack.getParentFile();
                            File[] listFileGetIconBack = folderParentBack.listFiles();
                            int index = 0;
                            JLabel labels[] = new JLabel[listFileGetIconBack.length];
                            for(File f : listFileGetIconBack){
                                Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
                                JLabel label = new JLabel(f.getName());
                                label.setIcon(icon);
                                labels[index++] = label;
                            }
                            oos.writeObject(labels);

                        break;
                    case ControlCommand.FILE_TRANSFER_SEND_LOCAL_TO_REMOTE:
                        // receive file info
                        ois = new ObjectInputStream(socket.getInputStream());
                        FileInfo fileInfo = (FileInfo) ois.readObject();
                        if (fileInfo != null) {
                            createFile(fileInfo);
                        }

                        // confirm that file is received
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        fileInfo.setStatus("success");
                        fileInfo.setDataBytes(null);
                        oos.writeObject(fileInfo);
                        break;
                    case ControlCommand.FILE_TRANSFER_SEND_REMOTE_TO_LOCAL:

                        String pathSend = dis.readUTF();
                        String pathReceive = dis.readUTF();
                        // get file info
                        fileInfo = getFileInfo(pathSend, pathReceive);
                        // send file
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        oos.writeObject(fileInfo);
                        break;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
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
}
