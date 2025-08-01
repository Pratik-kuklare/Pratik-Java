import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryManagementSystem extends JFrame {
    private JTabbedPane tabbedPane;
    private BookManagementPanel bookPanel;
    private UserManagementPanel userPanel;
    private IssueReturnPanel issuePanel;
    
    public LibraryManagementSystem() {
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Initialize panels
        bookPanel = new BookManagementPanel();
        userPanel = new UserManagementPanel();
        issuePanel = new IssueReturnPanel();
        
        // Add tabs
        tabbedPane.addTab("Books", bookPanel);
        tabbedPane.addTab("Users", userPanel);
        tabbedPane.addTab("Issue/Return", issuePanel);
        
        add(tabbedPane);

    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryManagementSystem().setVisible(true);
        });
    }
}