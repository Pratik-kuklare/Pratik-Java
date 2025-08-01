import dao.UserDAO;
import models.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class UserManagementPanel extends JPanel {
    private UserDAO userDAO;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;
    private JTextField nameField, emailField, phoneField, addressField;
    private JComboBox<User.UserType> userTypeCombo;
    
    public UserManagementPanel() {
        userDAO = new UserDAO();
        initializeComponents();
        loadUsers();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchUsers());
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> loadUsers());
        searchPanel.add(refreshButton);
        
        add(searchPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Name", "Email", "Phone", "User Type"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // Add user panel
        JPanel addPanel = createAddUserPanel();
        add(addPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createAddUserPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add New User"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        panel.add(nameField, gbc);
        
        // Email
        gbc.gridx = 2; gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3;
        emailField = new JTextField(15);
        panel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        panel.add(phoneField, gbc);
        
        // User Type
        gbc.gridx = 2; gbc.gridy = 1;
        panel.add(new JLabel("User Type:"), gbc);
        gbc.gridx = 3;
        userTypeCombo = new JComboBox<>(User.UserType.values());
        panel.add(userTypeCombo, gbc);
        
        // Address
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        addressField = new JTextField(30);
        panel.add(addressField, gbc);
        
        // Add button
        gbc.gridx = 3; gbc.gridy = 2; gbc.gridwidth = 1;
        JButton addButton = new JButton("Add User");
        addButton.addActionListener(this::addUser);
        panel.add(addButton, gbc);
        
        return panel;
    }
    
    private void loadUsers() {
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        
        for (User user : users) {
            Object[] row = {
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getUserType()
            };
            tableModel.addRow(row);
        }
    }
    
    private void searchUsers() {
        String searchTerm = searchField.getText().trim();
        if (searchTerm.isEmpty()) {
            loadUsers();
            return;
        }
        
        tableModel.setRowCount(0);
        List<User> users = userDAO.searchUsers(searchTerm);
        
        for (User user : users) {
            Object[] row = {
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getUserType()
            };
            tableModel.addRow(row);
        }
    }
    
    private void addUser(ActionEvent e) {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String address = addressField.getText().trim();
        User.UserType userType = (User.UserType) userTypeCombo.getSelectedItem();
        
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        User user = new User(name, email, phone, address, userType);
        
        if (userDAO.addUser(user)) {
            JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add user!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
        userTypeCombo.setSelectedIndex(0);
    }
}