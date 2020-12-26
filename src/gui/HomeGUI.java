package gui;

import lib.Lib;
import resources.Configuration;
import resources.StringResource;
import tcp.remote.Authenticate;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class HomeGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = Configuration.SERIAL_VERSION_UID;
	private static final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789"
			+ "abcdefghijklmnopqrstuvxyz";
	private static final String FONT = "SansSerif";

	private JPanel contentPane;
	private JTextField txtRemotePassword;
	private JTextField txtRemoteID;
	private JPanel panelLocal;
	private JTextField vLine1;
	private JTextField vLine2;
	private JTextField txtLocalPassword;
	private JButton btnConnect;

	private String password;

	private String randomString(int n) {
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}

	public HomeGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 759, 473);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(250, 250, 250));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panelLocal = new JPanel();
		panelLocal.setBorder(new MatteBorder(0, 0, 0, 1, new Color(228, 228, 228)));
		panelLocal.setBackground(new Color(250, 250, 250));
		panelLocal.setBounds(0, 0, 371, 434);
		contentPane.add(panelLocal);

		JLabel lblLocalTitle = new JLabel("Allow Remote Control");
		lblLocalTitle.setBounds(34, 87, 305, 41);
		lblLocalTitle.setForeground(new Color(80, 80, 80));
		lblLocalTitle.setFont(new Font(FONT, Font.PLAIN, 25));

		vLine2 = new JTextField();
		vLine2.setBounds(34, 228, 2, 50);
		vLine2.setEnabled(false);
		vLine2.setEditable(false);
		vLine2.setColumns(10);
		vLine2.setBorder(null);
		vLine2.setBackground(new Color(3, 143, 244));
		vLine2.setAlignmentX(0.0f);

		txtLocalPassword = new JTextField();
		txtLocalPassword.setBounds(65, 247, 250, 31);
		txtLocalPassword.setEditable(false);
		txtLocalPassword.setForeground(Color.DARK_GRAY);
		txtLocalPassword.setFont(new Font(FONT, Font.PLAIN, 25));
		txtLocalPassword.setColumns(10);
		txtLocalPassword.setBorder(null);
		txtLocalPassword.setBackground(new Color(250, 250, 250));
		panelLocal.setLayout(null);
		panelLocal.add(lblLocalTitle);

		vLine1 = new JTextField();
		vLine1.setBounds(34, 149, 2, 50);
		vLine1.setEditable(false);
		vLine1.setEnabled(false);
		vLine1.setBackground(new Color(3, 143, 244));
		vLine1.setAlignmentX(Component.LEFT_ALIGNMENT);
		vLine1.setBorder(null);
		vLine1.setColumns(10);
		panelLocal.add(vLine1);

		JLabel lblLocalIdTitle = new JLabel("Your ID");
		lblLocalIdTitle.setBounds(48, 146, 145, 18);
		lblLocalIdTitle.setForeground(new Color(80, 80, 80));
		lblLocalIdTitle.setFont(new Font(FONT, Font.BOLD, 13));
		panelLocal.add(lblLocalIdTitle);

		JTextField txtLocalID = new JTextField();
		txtLocalID.setBounds(65, 168, 250, 31);
		txtLocalID.setEditable(false);
		txtLocalID.setFont(new Font(FONT, Font.PLAIN, 25));
		txtLocalID.setBorder(null);
		txtLocalID.setForeground(Color.DARK_GRAY);
		txtLocalID.setColumns(10);
		txtLocalID.setBackground(new Color(250, 250, 250));
		panelLocal.add(txtLocalID);
		panelLocal.add(vLine2);

		JLabel lblLocalPasswordTitle = new JLabel("Password");
		lblLocalPasswordTitle.setBounds(48, 225, 145, 18);
		lblLocalPasswordTitle.setForeground(new Color(80, 80, 80));
		lblLocalPasswordTitle.setFont(new Font(FONT, Font.BOLD, 12));
		panelLocal.add(lblLocalPasswordTitle);
		panelLocal.add(txtLocalPassword);

		JPanel panelRemote = new JPanel();
		panelRemote.setBackground(new Color(250, 250, 250));
		panelRemote.setBounds(372, 0, 371, 434);
		contentPane.add(panelRemote);

		JLabel lblRemotePasswordTitle = new JLabel("Password");
		lblRemotePasswordTitle.setBounds(36, 161, 145, 18);
		lblRemotePasswordTitle.setForeground(new Color(5, 0, 2));
		lblRemotePasswordTitle.setFont(new Font(FONT, Font.BOLD, 12));

		txtRemotePassword = new JTextField();
		txtRemotePassword.setBounds(46, 183, 295, 31);
		txtRemotePassword.setFont(new Font(FONT, Font.PLAIN, 25));
		txtRemotePassword.setForeground(Color.DARK_GRAY);
		txtRemotePassword.setColumns(10);
		txtRemotePassword.setBorder(new MatteBorder(0, 0, 1, 0, new Color(156, 156, 156)));
		txtRemotePassword.setBackground(new Color(250, 250, 250));

		btnConnect = new JButton("Connect");
		btnConnect.setOpaque(true);
		btnConnect.setBorder(null);
		btnConnect.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConnect.setFont(new Font(FONT, Font.BOLD, 18));
		btnConnect.setBounds(110, 276, 168, 44);
		btnConnect.addActionListener(this);
		btnConnect.setForeground(Color.WHITE);
		btnConnect.setRequestFocusEnabled(false);
		btnConnect.setRolloverEnabled(false);
		btnConnect.setBackground(new Color(30, 70, 220));

		panelRemote.setLayout(null);

		JLabel lblControlRemoteComputer = new JLabel("Control Remote Computer");
		lblControlRemoteComputer.setBounds(36, 29, 305, 41);
		lblControlRemoteComputer.setForeground(new Color(80, 80, 80));
		lblControlRemoteComputer.setFont(new Font(FONT, Font.PLAIN, 23));
		panelRemote.add(lblControlRemoteComputer);

		JLabel lblRemoteIdTitle = new JLabel("Partner ID");
		lblRemoteIdTitle.setBounds(36, 88, 145, 18);
		lblRemoteIdTitle.setForeground(new Color(80, 80, 80));
		lblRemoteIdTitle.setFont(new Font(FONT, Font.BOLD, 12));
		panelRemote.add(lblRemoteIdTitle);

		txtRemoteID = new JTextField();
		txtRemoteID.setBounds(46, 110, 295, 31);
		txtRemoteID.setFont(new Font(FONT, Font.PLAIN, 25));
		txtRemoteID.setForeground(Color.DARK_GRAY);
		txtRemoteID.setColumns(10);
		txtRemoteID.setBorder(new MatteBorder(0, 0, 1, 0, new Color(156, 156, 156)));
		txtRemoteID.setBackground(new Color(250, 250, 250));
		panelRemote.add(txtRemoteID);
		panelRemote.add(lblRemotePasswordTitle);
		panelRemote.add(txtRemotePassword);
		panelRemote.add(btnConnect);

		setResizable(false);
		setVisible(true);

		password = "123456";//randomString(6);
		txtLocalPassword.setText(password);

		try {
			Enumeration<NetworkInterface> nics = NetworkInterface
					.getNetworkInterfaces();
			while (nics.hasMoreElements()) {
				NetworkInterface nic = nics.nextElement();
				Enumeration<InetAddress> addrs = nic.getInetAddresses();
				while (addrs.hasMoreElements()) {
					InetAddress addr = addrs.nextElement();
					String ip = addr.getHostAddress();
					if(Lib.checkNonLocalIP(nic.getDisplayName(), ip)){
						txtLocalID.setText(ip);
						break;
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		try {
			new tcp.local.Authenticate(this, password).startListen();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource() == btnConnect){
			String remoteId = txtRemoteID.getText().trim();
			String remotePassword = txtRemotePassword.getText().trim();
			Authenticate auth = null;
			try {
				auth = Authenticate.getInstance(remoteId);
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(
						HomeGUI.this,
						StringResource.EMPTY_ID_PASSWORD,
						StringResource.MESSAGE_ERROR,
						JOptionPane.ERROR_MESSAGE);
			}

			if(remoteId.equals("") || remotePassword.equals(""))
				JOptionPane.showMessageDialog(
						HomeGUI.this,
						StringResource.EMPTY_ID_PASSWORD,
						StringResource.MESSAGE_ERROR,
						JOptionPane.ERROR_MESSAGE);
			else if(auth != null && !auth.auth(remotePassword))
					JOptionPane.showMessageDialog(
							HomeGUI.this,
							StringResource.WRONG_ID_PASSWORD,
							StringResource.MESSAGE_ERROR,
							JOptionPane.ERROR_MESSAGE);
			else{
				StringResource.REMOTE_IP = remoteId;

				this.dispose();
				try {
					FileTransferGUI frame = new FileTransferGUI();
					frame.setVisible(true);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}


		}
	}

	public String getPassword() {
		return password;
	}
}
