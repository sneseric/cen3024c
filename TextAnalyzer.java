import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextAnalyzer {
    public static void main(String[] args) {
        String url = "https://www.gutenberg.org/files/1065/1065-h/1065-h.htm";

        try {
            // Get webpage using Jsoup
            Document doc = Jsoup.connect(url).get();

            // Locate the specific HTML element containing the poem
            Elements poemElements = doc.select("div.chapter");

            // Extract the text of the poem from div.chapter
            StringBuilder poemText = new StringBuilder();
            for (Element element : poemElements) {
                poemText.append(element.text()).append(" ");
            }

            // Tokenize and count word frequencies
            Map<String, Integer> wordFreq = new HashMap<>();
            String[] tokens = poemText.toString().toLowerCase().split("\\s+");

            // Define a regex pattern to match non-word characters
            Pattern pattern = Pattern.compile("\\W+");

            for (String token : tokens) {
                // Remove non-word characters from the token
                String word = pattern.matcher(token).replaceAll("");
                if (!word.isEmpty()) {
                    wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                }
            }

            // Sort words by frequency in descending order
            Map<String, Integer> sortedWords = wordFreq.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            // Print the sorted words and their frequencies in pairs (word: frequency)
            for (Map.Entry<String, Integer> entry : sortedWords.entrySet()) {
                String word = entry.getKey();
                int frequency = entry.getValue();
                System.out.println(word + ": " + frequency);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

