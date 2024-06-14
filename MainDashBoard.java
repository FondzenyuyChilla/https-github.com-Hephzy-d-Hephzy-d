package admin;

import javax.swing.*;
import java.awt.*;

public class MainDashBoard extends JFrame {

    public MainDashBoard() {
        setTitle("Main Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add OrderDashboard tab
        OrderDashboard orderDashboard = new OrderDashboard();
        tabbedPane.addTab("Order Dashboard", orderDashboard.getContentPane());

        // Add AddAdminPage tab
        AddAdminPage addAdminPage = new AddAdminPage();
        tabbedPane.addTab("Add Admin", addAdminPage.getContentPane());

        // Add ProductDashboard tab
        ProductDashboard productDashboard = new ProductDashboard();
        tabbedPane.addTab("Product Dashboard", productDashboard.getContentPane());

        // Add the tabbed pane to the main frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainDashBoard().setVisible(true);
            }
        });
    }
}
