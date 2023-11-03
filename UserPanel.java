import finals.AdminPanel.Book;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.awt.event.ActionEvent;

public class UserPanel extends JFrame {

    private JPanel contentPane;
    private DefaultTableModel tableModel;
    private JComboBox<String> genreComboBox;
    private LinkedList<AdminPanel.Book> bookList;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LinkedList<AdminPanel.Book> bookList = loadDataFromTextFile("books.txt");
                    UserPanel frame = new UserPanel(bookList);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserPanel(LinkedList<AdminPanel.Book> bookList) {
        this.bookList = bookList;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1021, 639);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        genreComboBox = new JComboBox<>();
        genreComboBox.setModel(new DefaultComboBoxModel<>(new String[]{"All Genres", "Mystery", "Science Fiction", "Fantasy", "Historical Fiction", "Romance", "Non-Fiction"}));
        genreComboBox.setBounds(442, 133, 150, 25);
        contentPane.add(genreComboBox);

        JScrollPane tableScrollPane = new JScrollPane();
        tableScrollPane.setBounds(87, 169, 837, 388);
        contentPane.add(tableScrollPane);

        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("C:\\Users\\Administrator\\Downloads\\BookWhispers (1).png"));
        background.setBounds(0, 0, 1010, 606);
        contentPane.add(background);

        initBookTable();
        refreshTable();

        genreComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
    }

    // Load data from the "books.txt" file and populate the bookList
    public static LinkedList<AdminPanel.Book> loadDataFromTextFile(String filePath) {
        LinkedList<AdminPanel.Book> bookList = new LinkedList<>();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length >= 4) {
                        String title = data[0];
                        String author = data[1];
                        String synopsis = data[2];
                        String genre = data[3];
                        AdminPanel.Book book = new AdminPanel.Book (title, author, genre, synopsis);
                        bookList.add(book);
                    
                    }
                }
                br.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookList;
    }


    private void initBookTable() {
        String[] columnNames = {"Title", "Author", "Genre", "Synopsis"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable bookTable = new JTable(tableModel);  // Initialize bookTable here
        JScrollPane tableScrollPane = new JScrollPane(bookTable);  // Initialize tableScrollPane with bookTable
        tableScrollPane.setBounds(87, 169, 837, 388);
        contentPane.add(tableScrollPane);  // Add tableScrollPane to the contentPane
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        String selectedGenre = (String) genreComboBox.getSelectedItem();

        for (AdminPanel.Book book : bookList) {
            if ("All Genres".equals(selectedGenre) || selectedGenre.equals(book.getGenre())) {
                tableModel.addRow(new Object[]{book.getTitle(), book.getAuthor(), book.getGenre(), book.getSynopsis()});
            }
        }
    }
	
}
