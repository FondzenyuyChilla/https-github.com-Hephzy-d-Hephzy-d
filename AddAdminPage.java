package admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.LocalDatabase;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAdminPage extends JFrame {
    LocalDatabase localdatabase = new LocalDatabase();

    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JTable table;
    private DefaultTableModel tableModel;

    public AddAdminPage() {
        setTitle("Add Admin");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        getContentPane().setBackground(Color.GRAY);

        // Dashboard panel
        JPanel dashboardPanel = new JPanel(new GridBagLayout());
        dashboardPanel
                .setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.WHITE), "Add Admin"));
        dashboardPanel.setBackground(Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels and text fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        dashboardPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(20);
        dashboardPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        dashboardPanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        dashboardPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        dashboardPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        dashboardPanel.add(passwordField, gbc);

        // Add and clear buttons
        gbc.gridx = 0;
        gbc.gridy = 3;
        JButton addButton = new JButton("Add Admin");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAdmin();
            }
        });
        dashboardPanel.add(addButton, gbc);

        gbc.gridx = 1;
        JButton clearButton = new JButton("Clear Fields");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        dashboardPanel.add(clearButton, gbc);

        add(dashboardPanel);

        // Table panel (placeholder for displaying added admins)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[] { "Name", "Email" }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel);

        // Adjusting layout and visibility
        pack();
        setLocationRelativeTo(null);
    }

    private void addAdmin() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            // Assuming the admin details are added to a table for display
            tableModel.addRow(new Object[] { name, email });
            localdatabase.createTable("Admin", "id INTEGER PRIMARY KEY, name TEXT, email TEXT, password TEXT");
            localdatabase.insert("Admin", "name, email, password",
                    "'" + name + "', '" + email + "', '" + password + "'");
            localdatabase.disconnect();
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddAdminPage().setVisible(true);
            }
        });
    }
}
