package gui;

import lib.Lib;
import tcp.local.FileTransferLocal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileTransferGUI extends JFrame implements ActionListener , MouseListener{


    private JPanel contentPane;
    private JTable tableFileLocal;
    private JTextField txtFileChooseLocal;
    private JTextField txtFileChooseRemote;
    private JTable tableFileRemote;
    private JTextField txtPathLocal;
    private JTextField txtPathRemote;

    private static String pathHome = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
    //    private static String pathHome = "C:\\";
    private String path;
    private String pathRemote;
    private DefaultTableModel defaultTableModel;
    private JButton btnSendLocal;
    private JButton btnSendRemote;
    private JButton btnHomeLocal;
    private JButton btnHomeRemote;
    private JButton btnBackLocal;
    private JButton btnBackRemote;
    private File[] listFileRemote;
    private FileTransferLocal fileTransferLocal;
    private JLabel labels[];
    /**
     * Launch the application.
     */
//    public static void main(String[] args) throws IOException {
//        new FileTransferRemote().start();
//        FileTransferGUI frame = new FileTransferGUI();
//        frame.setVisible(true);
//
//
//    }


    /**
     * Create the frame.
     */
    public FileTransferGUI() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 761, 528);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel LocalTitle = new JLabel("Local Computer");
        LocalTitle.setFont(new Font("SansSerif", Font.PLAIN, 25));
        LocalTitle.setBounds(64, 27, 220, 45);
        contentPane.add(LocalTitle);

        JButton LogoLocal = new JButton("");
        LogoLocal.setRequestFocusEnabled(false);
        LogoLocal.setContentAreaFilled(false);
        LogoLocal.setBorderPainted(false);
        LogoLocal.setDefaultCapable(false);
        LogoLocal.setFocusPainted(false);
        LogoLocal.setFocusTraversalKeysEnabled(false);
        LogoLocal.setFocusable(false);
        LogoLocal.setBackground(Color.WHITE);
        LogoLocal.setBorder(null);
        LogoLocal.setIcon(new ImageIcon("img/avatarLocal.png"));
        LogoLocal.setBounds(23, 27, 40, 45);
        contentPane.add(LogoLocal);

        JSeparator Hline1 = new JSeparator();
        Hline1.setBounds(23, 80, 702, 2);
        contentPane.add(Hline1);

        JSeparator Hline2 = new JSeparator();
        Hline2.setBounds(23, 114, 702, 2);
        contentPane.add(Hline2);

        JScrollPane scrollPaneLocal = new JScrollPane();
        scrollPaneLocal.setBorder(null);
        scrollPaneLocal.setBackground(Color.WHITE);
        scrollPaneLocal.setBounds(23, 125, 340, 287);
        contentPane.add(scrollPaneLocal);

        tableFileLocal = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        tableFileLocal.setBorder(null);
        tableFileLocal.setBackground(Color.WHITE);
        scrollPaneLocal.setViewportView(tableFileLocal);
        tableFileLocal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        defaultTableModel = (DefaultTableModel) tableFileLocal.getModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{
                "Name","Size","Type","Date modified","Path"
        });
        tableFileLocal.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableFileLocal.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableFileLocal.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableFileLocal.getColumnModel().getColumn(3).setPreferredWidth(90);
        tableFileLocal.getColumnModel().getColumn(4).setPreferredWidth(90);

        labels = loadFolderToTable(Lib.getListChildOfFile(pathHome),tableFileLocal);
        tableFileLocal.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
        tableFileLocal.addMouseListener(this);

        txtFileChooseLocal = new JTextField();
        txtFileChooseLocal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
        txtFileChooseLocal.setBackground(Color.WHITE);
        txtFileChooseLocal.setBounds(23, 436, 293, 37);
        contentPane.add(txtFileChooseLocal);
        txtFileChooseLocal.setColumns(10);

        btnSendLocal = new JButton("");
        btnSendLocal.setFocusable(false);
        btnSendLocal.setBorder(null);
        btnSendLocal.setBackground(new Color(30, 144, 255));
        btnSendLocal.setIcon(new ImageIcon("img/sendLocal.png"));
        btnSendLocal.setBounds(326, 436, 37, 37);
        contentPane.add(btnSendLocal);
        btnSendLocal.addActionListener(this);

        JSeparator Vline3 = new JSeparator();
        Vline3.setOrientation(SwingConstants.VERTICAL);
        Vline3.setBounds(373, 126, 2, 287);
        contentPane.add(Vline3);

        JScrollPane scrollPaneRemote = new JScrollPane();
        scrollPaneRemote.setBorder(null);
        scrollPaneRemote.setBackground(Color.WHITE);
        scrollPaneRemote.setBounds(385, 125, 340, 287);
        contentPane.add(scrollPaneRemote);

        tableFileRemote = new JTable(){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        tableFileRemote.setBackground(Color.WHITE);
        tableFileRemote.setBorder(null);
        scrollPaneRemote.setViewportView(tableFileRemote);

        defaultTableModel = (DefaultTableModel) tableFileRemote.getModel();
        defaultTableModel.setColumnIdentifiers(new Object[]{
                "Name","Size","Type","Date modified","Path"
        });
        tableFileRemote.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableFileRemote.getColumnModel().getColumn(0).setPreferredWidth(150);
        tableFileRemote.getColumnModel().getColumn(1).setPreferredWidth(100);
        tableFileRemote.getColumnModel().getColumn(2).setPreferredWidth(100);
        tableFileRemote.getColumnModel().getColumn(3).setPreferredWidth(90);
        tableFileRemote.getColumnModel().getColumn(4).setPreferredWidth(90);

        tableFileRemote.addMouseListener(this);

        txtFileChooseRemote = new JTextField();
        txtFileChooseRemote.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
        txtFileChooseRemote.setColumns(10);
        txtFileChooseRemote.setBackground(Color.WHITE);
        txtFileChooseRemote.setBounds(432, 436, 293, 37);
        contentPane.add(txtFileChooseRemote);

        btnSendRemote = new JButton("");
        btnSendRemote.setFocusable(false);
        btnSendRemote.setIcon(new ImageIcon("img/sendRemote.png"));
        btnSendRemote.setBorder(null);
        btnSendRemote.setBackground(new Color(30, 144, 255));
        btnSendRemote.setBounds(385, 436, 37, 37);
        contentPane.add(btnSendRemote);
        btnSendRemote.addActionListener(this);

        JSeparator Vline2 = new JSeparator();
        Vline2.setOrientation(SwingConstants.VERTICAL);
        Vline2.setBounds(373, 85, 2, 25);
        contentPane.add(Vline2);

        JSeparator Vline1 = new JSeparator();
        Vline1.setOrientation(SwingConstants.VERTICAL);
        Vline1.setBounds(373, 27, 2, 45);
        contentPane.add(Vline1);

        JSeparator Hline3 = new JSeparator();
        Hline3.setBounds(23, 423, 702, 2);
        contentPane.add(Hline3);

        JSeparator Vline4 = new JSeparator();
        Vline4.setOrientation(SwingConstants.VERTICAL);
        Vline4.setBounds(373, 431, 2, 45);
        contentPane.add(Vline4);

        JLabel RemoteTitle = new JLabel("Remote Computer");
        RemoteTitle.setFont(new Font("SansSerif", Font.PLAIN, 25));
        RemoteTitle.setBounds(432, 27, 220, 45);
        contentPane.add(RemoteTitle);

        JButton LogoRemote = new JButton("");
        LogoRemote.setContentAreaFilled(false);
        LogoRemote.setBorderPainted(false);
        LogoRemote.setDefaultCapable(false);
        LogoRemote.setFocusTraversalKeysEnabled(false);
        LogoRemote.setFocusPainted(false);
        LogoRemote.setRequestFocusEnabled(false);
        LogoRemote.setFocusable(false);
        LogoRemote.setIcon(new ImageIcon("img/avatarRemote.png"));
        LogoRemote.setBorder(null);
        LogoRemote.setBackground(Color.WHITE);
        LogoRemote.setBounds(385, 27, 40, 45);
        contentPane.add(LogoRemote);

        btnHomeLocal = new JButton("");
        btnHomeLocal.setFocusable(false);
        btnHomeLocal.setDefaultCapable(false);
        btnHomeLocal.setIcon(new ImageIcon("img/HomeLocal.png"));
        btnHomeLocal.setBackground(Color.WHITE);
        btnHomeLocal.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(192, 192, 192)));
        btnHomeLocal.setBounds(23, 82, 30, 30);
        contentPane.add(btnHomeLocal);
        btnHomeLocal.addActionListener(this);

        btnBackLocal = new JButton("");
        btnBackLocal.setFocusable(false);
        btnBackLocal.setDefaultCapable(false);
        btnBackLocal.setIcon(new ImageIcon("img/BackLocal.png"));
        btnBackLocal.setBorder(null);
        btnBackLocal.setBackground(Color.WHITE);
        btnBackLocal.setBounds(56, 82, 30, 30);
        contentPane.add(btnBackLocal);
        btnBackLocal.addActionListener(this);

        txtPathLocal = new JTextField();
        txtPathLocal.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
        txtPathLocal.setBounds(89, 85, 274, 25);
        contentPane.add(txtPathLocal);
        txtPathLocal.setColumns(10);
        txtPathLocal.setText(pathHome);


        txtPathRemote = new JTextField();
        txtPathRemote.setColumns(10);
        txtPathRemote.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
        txtPathRemote.setBounds(451, 85, 274, 25);
        contentPane.add(txtPathRemote);

        btnBackRemote = new JButton("");
        btnBackRemote.setIcon(new ImageIcon("img/BackLocal.png"));
        btnBackRemote.setFocusable(false);
        btnBackRemote.setDefaultCapable(false);
        btnBackRemote.setBorder(null);
        btnBackRemote.setBackground(Color.WHITE);
        btnBackRemote.setBounds(418, 82, 30, 30);
        contentPane.add(btnBackRemote);
        btnBackRemote.addActionListener(this);

        btnHomeRemote = new JButton("");
        btnHomeRemote.setIcon(new ImageIcon("img/HomeLocal.png"));
        btnHomeRemote.setFocusable(false);
        btnHomeRemote.setDefaultCapable(false);
        btnHomeRemote.setBorder(null);
        btnHomeRemote.setBackground(Color.WHITE);
        btnHomeRemote.setBounds(385, 82, 30, 30);
        contentPane.add(btnHomeRemote);
        btnHomeRemote.addActionListener(this);

        fileTransferLocal = new FileTransferLocal();
        File[] f = fileTransferLocal.openFolder("null");
        loadFolderToTable(f, tableFileRemote);
        labels =  fileTransferLocal.getIcon("null");
        tableFileRemote.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
    }

    public JLabel[] loadFolderToTable(File[] listFile, JTable jTable){
        DefaultTableModel defaultTableModel= (DefaultTableModel) jTable.getModel();
        JLabel labels[] = new JLabel[listFile.length];

        int index = 0;
        for (File f : listFile){

            String extension = "";

            int i = f.getName().lastIndexOf('.');
            if (i > 0) {
                extension = f.getName().substring(i+1);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
            JLabel label = new JLabel(f.getName());
            label.setIcon(icon);
            labels[index++] = label;

            if (extension.equals("")){
                String path = f.getPath();
                defaultTableModel.addRow(new Object[]{

                        f.getName(),f.length(), "Directory", sdf.format(f.lastModified()), path
                });
            }else{
                String path = f.getPath();
                defaultTableModel.addRow(new Object[]{
                        f.getName(), f.length(), extension, sdf.format(f.lastModified()), path
                });
            }

        }
        return labels;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnHomeLocal ){
            DefaultTableModel model = (DefaultTableModel) tableFileLocal.getModel();
            model.setRowCount(0);
            JLabel labels[] = loadFolderToTable(Lib.getListChildOfFile(pathHome ),tableFileLocal);
            tableFileLocal.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
            txtPathLocal.setText("");
        }
        else if (e.getSource()== btnBackLocal){
            File folderLocal = new File(path);
            String pathParent = folderLocal.getParent();
            DefaultTableModel model = (DefaultTableModel) tableFileLocal.getModel();
            model.setRowCount(0);
            if (pathParent.equals(pathHome)){
                model = (DefaultTableModel) tableFileLocal.getModel();
                model.setRowCount(0);
                JLabel labels[] = loadFolderToTable(Lib.getListChildOfFile(pathHome),tableFileLocal);
                tableFileLocal.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
                txtPathLocal.setText(pathParent);
            }else {
                JLabel labels[] = loadFolderToTable(Lib.getListChildOfFile(pathParent),tableFileLocal);
                tableFileLocal.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
                txtPathLocal.setText(pathParent);
                path = pathParent;
            }
            txtFileChooseLocal.setText("");
        }
        else if (e.getSource()== btnSendLocal){
            String message = fileTransferLocal.sendFile(txtPathLocal.getText(),txtPathRemote.getText()+ File.separator);
            JOptionPane.showMessageDialog(null,"Send file: "+ message);
        }
        else if (e.getSource()== btnHomeRemote){
            DefaultTableModel model = (DefaultTableModel) tableFileRemote.getModel();
            model.setRowCount(0);
            File[] f = fileTransferLocal.openFolder("null");
            loadFolderToTable(f, tableFileRemote);
            JLabel labels[] =  fileTransferLocal.getIcon("null");
            tableFileRemote.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
            txtPathRemote.setText("");
        }
        else if (e.getSource()==btnBackRemote){
            File folderRemote = new File(pathRemote);
            String pathParentRemote = folderRemote.getParent();
            DefaultTableModel model = (DefaultTableModel) tableFileRemote.getModel();
            model.setRowCount(0);
//            if (pathParent.equals(pathHome)){
//                model = (DefaultTableModel) tableFileLocal.getModel();
//                model.setRowCount(0);
//                loadFolderToTable(Lib.getListChildOfFile(pathHome),tableFileLocal);
//                txtPathLocal.setText(pathParent);
//            }else {
            File[] f = fileTransferLocal.backFolder(pathRemote + File.separator);
            loadFolderToTable(f, tableFileRemote);
            JLabel labels[] =  fileTransferLocal.getIconBack(pathRemote + File.separator);
            tableFileRemote.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
            txtPathRemote.setText(pathParentRemote);
            pathRemote = pathParentRemote;
//            }

        }
        else if (e.getSource()==btnSendRemote){
            String message = fileTransferLocal.getFile(txtPathRemote.getText(),txtPathLocal.getText()+ File.separator);
            JOptionPane.showMessageDialog(null,"Send file: "+ message);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource()==tableFileLocal){
            int row = tableFileLocal.getSelectedRow();
            String value = tableFileLocal.getModel().getValueAt(row,0).toString();
            String type = tableFileLocal.getModel().getValueAt(row, 2).toString();
            path = tableFileLocal.getModel().getValueAt(row, 4).toString();
            if (!type.equals("Directory")){
                txtFileChooseLocal.setText(value);
            }else  txtFileChooseLocal.setText("");
            if (e.getClickCount() == 2) {
                if (type.equals("Directory")){
                    DefaultTableModel model = (DefaultTableModel) tableFileLocal.getModel();
                    model.setRowCount(0);
                    JLabel labels[] = loadFolderToTable(Lib.getListChildOfFile(path+ File.separator),tableFileLocal);
                    tableFileLocal.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));

                }else  txtFileChooseLocal.setText("");
            }
            txtPathLocal.setText(path);
        }else if (e.getSource()==tableFileRemote){
            int row = tableFileRemote.getSelectedRow();
            String value = tableFileRemote.getModel().getValueAt(row,0).toString();
            String type = tableFileRemote.getModel().getValueAt(row, 2).toString();
            pathRemote = tableFileRemote.getModel().getValueAt(row, 4).toString();
            if (!type.equals("Directory")){
                txtFileChooseRemote.setText(value);
            }else  txtFileChooseRemote.setText("");
            if (e.getClickCount() == 2) {
                if (type.equals("Directory")){
                    DefaultTableModel model = (DefaultTableModel) tableFileRemote.getModel();
                    model.setRowCount(0);
                    File[] f = fileTransferLocal.openFolder(pathRemote);
                    loadFolderToTable(f, tableFileRemote);
                    JLabel labels[] =  fileTransferLocal.getIcon(pathRemote);
                    tableFileRemote.getColumnModel().getColumn(0).setCellRenderer(getRenderer(labels));
                }else  txtFileChooseRemote.setText("");
            }
            txtPathRemote.setText(pathRemote);


        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private TableCellRenderer getRenderer(JLabel labels[]) {
        return new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {

                return labels[row];
            }
        };
    }
}
