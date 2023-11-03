import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import java.util.LinkedList;

public class AdminPanel extends JFrame {

    private JPanel contentPane;
    private JTextField titleField, authorField;
    private JTextArea synopsisField;
    private JButton addButton, editButton, clearButton, backButton, deleteButton;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> genreComboBox;
    private LinkedList<Book> bookList = new LinkedList<>();

    public static void main(String[] args) {
        LinkedList<Book> bookList = new LinkedList<>();
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminPanel frame = new AdminPanel(bookList);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public AdminPanel(LinkedList<Book> bookList) {
        this.bookList = bookList;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1020, 639);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
     // Pass the bookList to the UserPanel
        UserPanel userPanel = new UserPanel(bookList);
        userPanel.setVisible(false);

        // Book input components with labels
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(167, 149, 40, 25);
        contentPane.add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(223, 149, 183, 25);
        contentPane.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(166, 185, 46, 25);
        contentPane.add(authorLabel);

        authorField = new JTextField();
        authorField.setBounds(223, 185, 183, 25);
        contentPane.add(authorField);

        JLabel synopsisLabel = new JLabel("Synopsis:");
        synopsisLabel.setBounds(161, 221, 72, 25);
        contentPane.add(synopsisLabel);
        JScrollPane synopsisScrollPane = new JScrollPane();
        synopsisScrollPane.setBounds(223, 221, 183, 47);
        contentPane.add(synopsisScrollPane);
        
                synopsisField = new JTextArea();
                synopsisScrollPane.setViewportView(synopsisField);

        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setBounds(167, 279, 40, 25);
        contentPane.add(genreLabel);

        genreComboBox = new JComboBox<>(new String[]{"Mystery", "Science Fiction", "Fantasy", "Historical Fiction", "Romance", "Non-Fiction"});
        genreComboBox.setModel(new DefaultComboBoxModel(new String[] {"Mystery", "Science Fiction", "Fantasy", "Historical Fiction", "Romance", "Non-Fiction"}));
        genreComboBox.setBounds(223, 279, 183, 25);
        contentPane.add(genreComboBox);

        addButton = new JButton("Add Book");
        addButton.setBounds(167, 378, 100, 30);
        contentPane.add(addButton);
       

        editButton = new JButton("Edit Book");
        editButton.setBounds(167, 443, 100, 30);
        contentPane.add(editButton);
        

        clearButton = new JButton("Clear");
        clearButton.setBounds(306, 378, 100, 30);
        contentPane.add(clearButton);
        
        backButton = new JButton("Back");
        backButton.setBounds(306, 443, 100, 30);
        contentPane.add(backButton);
        
        deleteButton = new JButton("Delete Book");
        deleteButton.setBounds(306, 443, 100, 30);
        contentPane.add(deleteButton);

        // Initialize the book table
        initBookTable();
        
        deleteButton = new JButton("delete");
        deleteButton.setBounds(237, 510, 100, 30);
        contentPane.add(deleteButton);
        JScrollPane tableScrollPane = new JScrollPane(bookTable);
        tableScrollPane.setBounds(453, 123, 476, 438);
        contentPane.add(tableScrollPane);
                        
                                JLabel backgroundLabel = new JLabel("");
                                backgroundLabel.setIcon(new ImageIcon("C:\\Users\\Administrator\\Downloads\\BookWhispers.png"));
                                backgroundLabel.setBounds(0, 0, 1010, 606);
                                contentPane.add(backgroundLabel);

                             // Add action listener for the "Add Book" button
                                addButton.addActionListener(e -> {
                                    String title = titleField.getText();
                                    String author = authorField.getText();
                                    String synopsis = synopsisField.getText();
                                    String genre = (String) genreComboBox.getSelectedItem();

                                    // Create a new Book instance and add it to the linked list
                                    Book book = new Book(title, author, genre, synopsis);
                                    bookList.add(book);
                                    saveDataToFile();

                                    // Refresh the table
                                    refreshTable();

                                    // Clear the input fields
                                    clearInputFields();
                                });

                                // Add action listener for the "Edit Book" button
                                editButton.addActionListener(e -> {
                                    int selectedRow = bookTable.getSelectedRow();
                                    if (selectedRow != -1) {
                                        String title = titleField.getText();
                                        String author = authorField.getText();
                                        String synopsis = synopsisField.getText();
                                        String genre = (String) genreComboBox.getSelectedItem();

                                        // Update the selected book in the linked list
                                        Book book = bookList.get(selectedRow);
                                        book.setTitle(title);
                                        book.setAuthor(author);
                                        book.setGenre(genre);
                                        book.setSynopsis(synopsis);

                                        // Refresh the table
                                        refreshTable();

                                        // Clear the input fields
                                        clearInputFields();
                                    }
                                });

                                // Add action listener for the "Delete Book" button
                                deleteButton.addActionListener(e -> {
                                    int selectedRow = bookTable.getSelectedRow();
                                    if (selectedRow != -1) {
                                        // Remove the selected book from the linked list
                                        bookList.remove(selectedRow);

                                        // Refresh the table
                                        refreshTable();
                                    }
                                });
                            }

    private void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : bookList) {
                writer.write(book.title);
                writer.write(",");
                writer.write(book.author);
                writer.write(",");
                writer.write(book.synopsis);
                writer.write(",");
                writer.write(",");
                writer.write(book.genre);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving data to file");
        }
    }

							private void initBookTable() {
                                String[] columnNames = {"Title", "Author", "Genre", "Synopsis"};
                                tableModel = new DefaultTableModel(columnNames, 0);
                                bookTable = new JTable(tableModel);
                            }

                            private void refreshTable() {
                                // Clear the table
                                tableModel.setRowCount(0);

                                // Populate the table from the linked list
                                for (Book book : bookList) {
                                    tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getGenre(), book.getSynopsis()});
                                }
                            }

                            private void clearInputFields() {
                                titleField.setText("");
                                authorField.setText("");
                                synopsisField.setText("");
                                genreComboBox.setSelectedIndex(0);
                            }

                            // Custom class to represent a book
                            public static class Book {
                                 String title;
                                 String author;
                                 String genre;
                                 String synopsis;

                                public Book(String title, String author, String genre, String synopsis) {
                                    this.title = title;
                                    this.author = author;
                                    this.genre = genre;
                                    this.synopsis = synopsis;
                                }

                                public String getTitle() {
                                    return title;
                                }

                                public void setTitle(String title) {
                                    this.title = title;
                                }

                                public String getAuthor() {
                                    return author;
                                }

                                public void setAuthor(String author) {
                                    this.author = author;
                                }

                                public String getGenre() {
                                    return genre;
                                }

                                public void setGenre(String genre) {
                                    this.genre = genre;
                                }

                                public String getSynopsis() {
                                    return synopsis;
                                }

                                public void setSynopsis(String synopsis) {
                                    this.synopsis = synopsis;
                                }
                            }
                        }
