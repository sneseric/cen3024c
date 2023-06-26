import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import static org.junit.Assert.*;

public class TextAnalyzerGUITest {

    public TextAnalyzerGUI textAnalyzerGUI;

    @Before
    public void setUp() {
        textAnalyzerGUI = new TextAnalyzerGUI();
        SwingUtilities.invokeLater(() -> textAnalyzerGUI.createAndShowGUI());
    }

    @Test
    public void testGUIInitialization() {
        JFrame frame = TextAnalyzerGUI.getFrame();
        assertNotNull(frame);
        assertEquals("The Raven: Word Occurrences", frame.getTitle());

        java.awt.Component[] components = frame.getContentPane().getComponents();
        assertEquals(2, components.length);
        assertTrue(components[0] instanceof JPanel);
        assertTrue(components[1] instanceof JScrollPane);

        JPanel panel = (JPanel) components[0];
        java.awt.Component[] panelComponents = panel.getComponents();
        assertEquals(1, panelComponents.length);
        assertTrue(panelComponents[0] instanceof JButton);

        JButton startButton = (JButton) panelComponents[0];
        assertEquals("Start Program", startButton.getText());

        JScrollPane scrollPane = (JScrollPane) components[1];
        java.awt.Component viewport = scrollPane.getViewport().getView();
        assertTrue(viewport instanceof JTextArea);

        JTextArea resultTextArea = (JTextArea) viewport;
        assertFalse(resultTextArea.isEditable());

        System.out.println("GUI Initialization Test Passed");
    }

    @Test
    public void testStartButtonAction() {
        JFrame frame = TextAnalyzerGUI.getFrame();
        JPanel panel = (JPanel) frame.getContentPane().getComponents()[0];
        JButton startButton = (JButton) panel.getComponents()[0];
        JTextArea resultTextArea = (JTextArea) ((JScrollPane) frame.getContentPane().getComponents()[1]).getViewport().getView();

        assertTrue(startButton.isEnabled());
        assertTrue(resultTextArea.getText().isEmpty());

        startButton.doClick();

        assertFalse(startButton.isEnabled());
        assertFalse(resultTextArea.getText().isEmpty());

        System.out.println("Start Button Action Test Passed");
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main("TextAnalyzerGUITest");
    }
}
