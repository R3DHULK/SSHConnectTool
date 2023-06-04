import com.jcraft.jsch.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SSHConnectTool extends JFrame {

    private JTextField hostTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton connectButton;

    public SSHConnectTool() {
        setTitle("SSH Connect Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Host:"));
        hostTextField = new JTextField();
        panel.add(hostTextField);

        panel.add(new JLabel("Username:"));
        usernameTextField = new JTextField();
        panel.add(usernameTextField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String host = hostTextField.getText();
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());

                connectToSSH(host, username, password);
            }
        });
        panel.add(connectButton);

        add(panel);
        setVisible(true);
    }

    private void connectToSSH(String host, String username, String password) {
        JSch jSch = new JSch();
        Session session = null;

        try {
            session = jSch.getSession(username, host, 22);
            session.setPassword(password);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Connection successful
            JOptionPane.showMessageDialog(this, "Connected to SSH on port 22!",
                    "Connection Successful", JOptionPane.INFORMATION_MESSAGE);

        } catch (JSchException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to SSH server: " + e.getMessage(),
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SSHConnectTool();
            }
        });
    }
}
