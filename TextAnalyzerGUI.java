import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextAnalyzerGUI {
    public static final String URL = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

    // GUI components
    public static JTextArea resultTextArea;
    public static JButton startButton;
    public static JFrame frame;

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

    //Listen for start button 
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

                    // Sort word frequencies in descending order
                    Map<String, Integer> sortedWords = wordFreq.entrySet().stream()
                            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                    (e1, e2) -> e1, LinkedHashMap::new));

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
                } catch (IOException ex) {
                    ex.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        resultTextArea.setText("An error occurred while fetching the webpage.");
                        startButton.setEnabled(true);
                    });
                }
            });
            thread.start();
        }
    }

    public static JFrame getFrame() {
        return frame;
    }
}





