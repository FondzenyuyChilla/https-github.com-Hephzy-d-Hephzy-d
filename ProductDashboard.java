package admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProductDashboard extends JFrame {
    LocalDatabase local = new LocalDatabase();
    StorageHelper storage = new StorageHelper("./bin/local_db.sqlite");

    private JTextField nameField;
    private JTextField idField;
    private JTextField availableField;
    private JTextField unitPriceField;
    private JTextField restockField;
    private JTextField deleteNameField;
    private JTextField imageField;

    private JTable table;
    private DefaultTableModel tableModel;

    public ProductDashboard() {
        setTitle("Product Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.GRAY);

        // Dashboard panel
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridBagLayout());
        dashboardPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        dashboardPanel.setBackground(Color.GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Labels and text fields
        nameField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Name:", 0, nameField);

        idField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "ID:", 1, idField);

        availableField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Available:", 2, availableField);

        unitPriceField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Unit Price:", 3, unitPriceField);

        restockField = new JTextField(15);
        addLabelAndField(dashboardPanel, gbc, "Restock:", 4, restockField);

        // Image field and file picker button
        imageField = new JTextField(15);
        imageField.setEditable(false); // Prevent manual editing
        JButton chooseImageButton = new JButton("Choose Image");
        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseImage();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        dashboardPanel.add(new JLabel("Product Image:"), gbc);

        gbc.gridx = 1;
        dashboardPanel.add(imageField, gbc);

        gbc.gridx = 2;
        dashboardPanel.add(chooseImageButton, gbc);

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
        tableModel = new DefaultTableModel(new Object[] { "Name", "ID", "Available", "Unit Price", "Restock" }, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Delete section
        JPanel deletePanel = new JPanel();
        deletePanel.setBackground(Color.GRAY);
        deletePanel.add(new JLabel("Delete Name:"));
        deleteNameField = new JTextField(15);
        deletePanel.add(deleteNameField);
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
                addProduct();
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
                deleteProduct();
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

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false); // Disallow "All Files" option

        // Restrict to image files only (.png, .jpg, .jpeg)
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                }

                String extension = getExtension(f);
                if (extension != null) {
                    return extension.equals("png") || extension.equals("jpg") || extension.equals("jpeg");
                }

                return false;
            }

            public String getDescription() {
                return "Image Files (*.png, *.jpg, *.jpeg)";
            }

            private String getExtension(File f) {
                String ext = null;
                String s = f.getName();
                int i = s.lastIndexOf('.');

                if (i > 0 && i < s.length() - 1) {
                    ext = s.substring(i + 1).toLowerCase();
                }
                return ext;
            }
        });

        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filename = file.getPath();
            imageField.setText(filename);
        }
    }

    private void addProduct() {
        String name = nameField.getText();
        String id = idField.getText();
        String available = availableField.getText();
        String unitPrice = unitPriceField.getText();
        String restock = restockField.getText();
        String imageFilename = imageField.getText(); // This will contain the selected image filename

        //

        if (name.isEmpty() || id.isEmpty() || available.isEmpty() || unitPrice.isEmpty() || restock.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (imageFilename.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose a product image", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            tableModel.addRow(new Object[] { name, id, available, unitPrice, restock });
            local.createTable("products", "id INTEGER PRIMARY KEY, name TEXT, available TEXT, unitPrice TEXT");
            local.insert("products", "name, available, unitPrice",
                    "'" + name + "', ' " + available + "', '" + unitPrice + "'");
            storage.insertProduct(new Product(name, available, unitPrice, imageFilename));
            clearFields();
        }
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        availableField.setText("");
        unitPriceField.setText("");
        restockField.setText("");
        imageField.setText("");
    }

    private void deleteProduct() {
        String nameToDelete = deleteNameField.getText();
        if (nameToDelete.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(nameToDelete)) {
                tableModel.removeRow(i);
                deleteNameField.setText("");
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Name not found", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProductDashboard().setVisible(true);
            }
        });
    }
}
