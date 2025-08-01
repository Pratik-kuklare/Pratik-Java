import dao.BookDAO;
import models.Book;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BookManagementPanel extends JPanel {
    private BookDAO bookDAO;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField titleField, authorField, isbnField, categoryField, copiesField;
    
    public BookManagementPanel() {
        bookDAO = new BookDAO();
        initializeComponents();
        loadBooks();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchBooks());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadBooks());
        searchPanel.add(refreshButton);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Title", "Author", "ISBN", "Category", "Total Copies", "Available"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add book panel
        JPanel addPanel = createAddBookPanel();
        add(addPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createAddBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add New Book"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(15);
        panel.add(titleField, gbc);
        
        // Author
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 3;
        authorField = new JTextField(15);
        panel.add(authorField, gbc);
        
        // ISBN
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        isbnField = new JTextField(15);
        panel.add(isbnField, gbc);
        
        // Category
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 3;
        categoryField = new JTextField(15);
        panel.add(categoryField, gbc);
        
        // Copies
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Copies:"), gbc);
        gbc.gridx = 1;
        copiesField = new JTextField(15);
        panel.add(copiesField, gbc);
        
        // Add button
        gbc.gridx = 2; gbc.gridy = 2;
        JButton addButton = new JButton("Add Book");
        addButton.addActionListener(this::addBook);
        panel.add(addButton, gbc);
        
        return panel;
    }
    
    private void loadBooks() {
        tableModel.setRowCount(0);
        try {
            List<Book> books = bookDAO.getAllBooks();
            
            for (Book book : books) {
                Object[] row = {
                    book.getBookId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getCategory(),
                    book.getTotalCopies(),
                    book.getAvailableCopies()
                };
                tableModel.addRow(row);
            }
        } catch (Exception e) {
            // Add a placeholder row showing database connection status
            Object[] row = {"N/A", "Database not connected", "Please setup MySQL connection", "", "", "", ""};
            tableModel.addRow(row);
        }
    }
    
    private void searchBooks() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadBooks();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.searchBooks(searchTerm);
        
        for (Book book : books) {
            Object[] row = {
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getCategory(),
                book.getTotalCopies(),
                book.getAvailableCopies()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addBook(ActionEvent e) {
        try {
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            String isbn = isbnField.getText().trim();
            String category = categoryField.getText().trim();
            int copies = Integer.parseInt(copiesField.getText().trim());
            
            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Author are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Book book = new Book(title, author, isbn, category, copies);
            
            try {
                if (bookDAO.addBook(book)) {
                    JOptionPane.showMessageDialog(this, "Book added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadBooks();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add book!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception dbEx) {
                JOptionPane.showMessageDialog(this, 
                    "Database connection error!\nPlease check:\n1. MySQL is running\n2. Database exists\n3. MySQL Connector JAR is in classpath\n\nError: " + dbEx.getMessage(), 
                    "Database Error", 
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for copies!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        titleField.setText("");
        authorField.setText("");
        isbnField.setText("");
        categoryField.setText("");
        copiesField.setText("");
    }
}