import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class TextAnalyzerGUI {
    public static final String URL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

    // GUI components
    public static JTextArea resultTextArea;
    public static JButton startButton;
    public static JFrame frame;
    public static Connection connection;

    // MySQL connection details
    public static final String DB_URL = "jdbc:mysql://localhost:3306/word_occurences";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "password";

    // Run GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TextAnalyzerGUI::createAndShowGUI);
    }

    public static void createAndShowGUI() {
        frame = new JFrame("The Raven: Word Occurrences");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));

        JPanel panel = new JPanel(new BorderLayout());
        startButton = new JButton("Start Program");
        startButton.addActionListener(new StartButtonListener());
        panel.add(startButton, BorderLayout.CENTER);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        frame.getContentPane().add(panel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    // Listen for start button
    public static class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startButton.setEnabled(false);

            // Fetch webpage using JSoup
            Thread thread = new Thread(() -> {
                try {
                    Document doc = Jsoup.connect(URL).get();
                    Elements poemElements = doc.select("div.chapter");

                    StringBuilder poemText = new StringBuilder();
                    for (Element element : poemElements) {
                        poemText.append(element.text()).append(" ");
                    }

                    Map<String, Integer> wordFreq = new HashMap<>();
                    String[] tokens = poemText.toString().toLowerCase().split("\\s+");
                    Pattern pattern = Pattern.compile("\\W+");

                    // Remove non-word characters and count word occurrences
                    for (String token : tokens) {
                        String word = pattern.matcher(token).replaceAll("");
                        if (!word.isEmpty()) {
                            wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                        }
                    }

                    // Store word frequencies in the database
                    storeWordFrequencies(wordFreq);

                    // Retrieve word frequencies from the database
                    Map<String, Integer> sortedWords = retrieveWordFrequencies();

                    StringBuilder resultBuilder = new StringBuilder();
                    for (Map.Entry<String, Integer> entry : sortedWords.entrySet()) {
                        String word = entry.getKey();
                        int frequency = entry.getValue();
                        resultBuilder.append(word).append(": ").append(frequency).append("\n");
                    }

                    // Update GUI with results
                    SwingUtilities.invokeLater(() -> {
                        resultTextArea.setText(resultBuilder.toString());
                        startButton.setEnabled(true);
                    });
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        resultTextArea.setText("An error occurred while processing the text.");
                        startButton.setEnabled(true);
                    });
                }
            });
            thread.start();
        }
    }

    public static void storeWordFrequencies(Map<String, Integer> wordFreq) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String insertQuery = "REPLACE INTO word (word, occurrences) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (Map.Entry<String, Integer> entry : wordFreq.entrySet()) {
                    String word = entry.getKey();
                    int occurrences = entry.getValue();
                    preparedStatement.setString(1, word);
                    preparedStatement.setInt(2, occurrences);
                    preparedStatement.executeUpdate();
                }
            }
        }
    }
    
    public static Map<String, Integer> retrieveWordFrequencies() throws SQLException {
        Map<String, Integer> wordFreq = new LinkedHashMap<>();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD)) {
            String selectQuery = "SELECT word, occurrences FROM word";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    String word = resultSet.getString("word");
                    int occurrences = resultSet.getInt("occurrences");
                    wordFreq.put(word, occurrences);
                }
            }
        }
        return wordFreq;
    }
    
    
    
    public static JFrame getFrame() {
        return frame;
    }
}
