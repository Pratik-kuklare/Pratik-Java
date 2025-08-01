import dao.*;
import models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IssueReturnPanel extends JPanel {
    private BookDAO bookDAO;
    private UserDAO userDAO;
    private BookIssueDAO issueDAO;
    private JTable issueTable;
    private DefaultTableModel tableModel;
    private JComboBox<Book> bookCombo;
    private JComboBox<User> userCombo;
    private JSpinner dueDaysSpinner;
    
    public IssueReturnPanel() {
        bookDAO = new BookDAO();
        userDAO = new UserDAO();
        issueDAO = new BookIssueDAO();
        initializeComponents();
        loadData();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Issue book panel
        JPanel issuePanel = createIssuePanel();
        add(issuePanel, BorderLayout.NORTH);
        
        // Table for active issues
        createIssueTable();
        JScrollPane scrollPane = new JScrollPane(issueTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Active Issues"));
        add(scrollPane, BorderLayout.CENTER);
        
        // Control buttons
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createIssuePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Issue Book"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Book selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Book:"), gbc);
        gbc.gridx = 1;
        bookCombo = new JComboBox<>();
        bookCombo.setPreferredSize(new Dimension(200, 25));
        panel.add(bookCombo, gbc);
        
        // User selection
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("User:"), gbc);
        gbc.gridx = 3;
        userCombo = new JComboBox<>();
        userCombo.setPreferredSize(new Dimension(200, 25));
        panel.add(userCombo, gbc);
        
        // Due days
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Due Days:"), gbc);
        gbc.gridx = 1;
        dueDaysSpinner = new JSpinner(new SpinnerNumberModel(14, 1, 90, 1));
        panel.add(dueDaysSpinner, gbc);
        
        // Issue button
        gbc.gridx = 2; gbc.gridy = 1;
        JButton issueButton = new JButton("Issue Book");
        issueButton.addActionListener(this::issueBook);
        panel.add(issueButton, gbc);
        
        return panel;
    }
    
    private void createIssueTable() {
        String[] columns = {"Issue ID", "Book", "User", "Issue Date", "Due Date", "Status", "Late Fee"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        issueTable = new JTable(tableModel);
        issueTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        
        JButton returnButton = new JButton("Return Selected Book");
        returnButton.addActionListener(this::returnBook);
        panel.add(returnButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadIssues());
        panel.add(refreshButton);
        
        JButton overdueButton = new JButton("Show Overdue");
        overdueButton.addActionListener(e -> showOverdueBooks());
        panel.add(overdueButton);
        
        JButton allIssuesButton = new JButton("Show All Issues");
        allIssuesButton.addActionListener(e -> showAllIssues());
        panel.add(allIssuesButton);
        
        return panel;
    }
    
    private void loadData() {
        loadBooks();
        loadUsers();
        loadIssues();
    }
    
    private void loadBooks() {
        bookCombo.removeAllItems();
        List<Book> books = bookDAO.getAllBooks();
        for (Book book : books) {
            if (book.getAvailableCopies() > 0) {
                bookCombo.addItem(book);
            }
        }
    }
    
    private void loadUsers() {
        userCombo.removeAllItems();
        List<User> users = userDAO.getAllUsers();
        for (User user : users) {
            userCombo.addItem(user);
        }
    }
    
    private void loadIssues() {
        tableModel.setRowCount(0);
        List<BookIssue> issues = issueDAO.getActiveIssues();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (BookIssue issue : issues) {
            Object[] row = {
                issue.getIssueId(),
                issue.getBookTitle(),
                issue.getUserName(),
                issue.getIssueDate().format(formatter),
                issue.getDueDate().format(formatter),
                issue.getStatus(),
                String.format("$%.2f", issue.getLateFee())
            };
            tableModel.addRow(row);
        }
    }
    
    private void showOverdueBooks() {
        tableModel.setRowCount(0);
        List<BookIssue> issues = issueDAO.getOverdueIssues();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (BookIssue issue : issues) {
            Object[] row = {
                issue.getIssueId(),
                issue.getBookTitle(),
                issue.getUserName(),
                issue.getIssueDate().format(formatter),
                issue.getDueDate().format(formatter),
                issue.getStatus(),
                String.format("$%.2f", issue.getLateFee())
            };
            tableModel.addRow(row);
        }
    }
    
    private void showAllIssues() {
        tableModel.setRowCount(0);
        List<BookIssue> issues = issueDAO.getAllIssues();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for (BookIssue issue : issues) {
            Object[] row = {
                issue.getIssueId(),
                issue.getBookTitle(),
                issue.getUserName(),
                issue.getIssueDate().format(formatter),
                issue.getDueDate().format(formatter),
                issue.getStatus(),
                String.format("$%.2f", issue.getLateFee())
            };
            tableModel.addRow(row);
        }
    }
    
    private void issueBook(ActionEvent e) {
        Book selectedBook = (Book) bookCombo.getSelectedItem();
        User selectedUser = (User) userCombo.getSelectedItem();
        
        if (selectedBook == null || selectedUser == null) {
            JOptionPane.showMessageDialog(this, "Please select both book and user!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (selectedBook.getAvailableCopies() <= 0) {
            JOptionPane.showMessageDialog(this, "No copies available for this book!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int dueDays = (Integer) dueDaysSpinner.getValue();
        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = issueDate.plusDays(dueDays);
        
        BookIssue issue = new BookIssue(selectedBook.getBookId(), selectedUser.getUserId(), issueDate, dueDate);
        
        if (issueDAO.issueBook(issue)) {
            // Update available copies
            int newAvailableCount = selectedBook.getAvailableCopies() - 1;
            bookDAO.updateAvailableCopies(selectedBook.getBookId(), newAvailableCount);
            
            JOptionPane.showMessageDialog(this, "Book issued successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadData(); // Refresh all data
        } else {
            JOptionPane.showMessageDialog(this, "Failed to issue book!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void returnBook(ActionEvent e) {
        int selectedRow = issueTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an issue to return!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int issueId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String bookTitle = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Return book: " + bookTitle + "?", 
            "Confirm Return", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (issueDAO.returnBook(issueId)) {
                // Find the book and update available copies
                List<BookIssue> allIssues = issueDAO.getAllIssues();
                for (BookIssue issue : allIssues) {
                    if (issue.getIssueId() == issueId) {
                        Book book = bookDAO.getBookById(issue.getBookId());
                        if (book != null) {
                            int newAvailableCount = book.getAvailableCopies() + 1;
                            bookDAO.updateAvailableCopies(book.getBookId(), newAvailableCount);
                        }
                        break;
                    }
                }
                
                JOptionPane.showMessageDialog(this, "Book returned successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadData(); // Refresh all data
            } else {
                JOptionPane.showMessageDialog(this, "Failed to return book!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}