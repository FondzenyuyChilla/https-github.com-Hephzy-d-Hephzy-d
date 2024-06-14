package admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderDashboard extends JFrame {

    private JTextField customerField;
    private JTextField contactField;
    private JTextField addressField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField deleteCustomerField;

    private JTable table;
    private DefaultTableModel tableModel;

    public OrderDashboard() {
        setTitle("Order Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.GRAY);

        // Dashboard panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridBagLayout());
        dashboardPanel.setBorder(BorderFactory.createTitledBorder("Dashboard"));
        dashboardPanel.setBackground(Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Title label
        JLabel label = new JLabel("Orders", SwingConstants.CENTER);
        Font font = new Font("Serif", Font.BOLD, 24);
        label.setFont(font);
        label.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        dashboardPanel.add(label, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;

        // Labels and text fields
        customerField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Customer:", 1, customerField);

        contactField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Contact:", 2, contactField);

        addressField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Address:", 3, addressField);

        quantityField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Quantity:", 4, quantityField);

        priceField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Price:", 5, priceField);

        // Add and clear buttons
        JButton addButton = new JButton("Add");
        gbc.gridx = 0;
        gbc.gridy = 6;
        dashboardPanel.add(addButton, gbc);

        JButton clearButton = new JButton("Clear");
        gbc.gridx = 1;
        dashboardPanel.add(clearButton, gbc);

        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        mainGbc.weightx = 0.5;
        mainGbc.fill = GridBagConstraints.BOTH;
        add(dashboardPanel, mainGbc);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[] { "Customer", "Contact", "Address", "Quantity", "Price" }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Delete section
        JPanel deletePanel = new JPanel();
        deletePanel.setBackground(Color.GRAY);
        deletePanel.add(new JLabel("Delete Customer:"));
        deleteCustomerField = new JTextField(15);
        deletePanel.add(deleteCustomerField);
        JButton deleteButton = new JButton("Delete");
        deletePanel.add(deleteButton);
        tablePanel.add(deletePanel, BorderLayout.SOUTH);

        mainGbc.gridx = 1;
        mainGbc.gridy = 0;
        mainGbc.weightx = 0.5;
        add(tablePanel, mainGbc);

        // Button actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOrder();
            }
        });
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, int yPos,
            JTextField textField) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private void addOrder() {
        String customer = customerField.getText();
        String contact = contactField.getText();
        String address = addressField.getText();
        String quantity = quantityField.getText();
        String price = priceField.getText();

        if (customer.isEmpty() || contact.isEmpty() || address.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            tableModel.addRow(new Object[] { customer, contact, address, quantity, price });
            clearFields();
        }
    }

    private void clearFields() {
        customerField.setText("");
        contactField.setText("");
        addressField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    private void deleteOrder() {
        String customerToDelete = deleteCustomerField.getText();
        if (customerToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a customer to delete", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(customerToDelete)) {
                tableModel.removeRow(i);
                deleteCustomerField.setText("");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Customer not found", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OrderDashboard().setVisible(true);
            }
        });
    }
}
