import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class Client extends JFrame {
    private JLabel inputLabel;
    private JTextField numberField;
    private JTextArea resultArea;

    public Client() {
        setTitle("Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new FlowLayout());

        inputLabel = new JLabel("Enter a number to check if it is prime:");
        add(inputLabel);

        numberField = new JTextField(10);
        add(numberField);

        JButton checkButton = new JButton("Check Prime");
        add(checkButton);

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int number = Integer.parseInt(numberField.getText());
                try {
                    Socket socket = new Socket("localhost", 5000);

                    // Send number to the server
                    PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                    writer.println(number);

                    // Receive result from server
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String result = reader.readLine();

                    // Display the result in resultArea
                    resultArea.append("The number is " + number + "\n");
                    resultArea.append(result + "\n");

                    // Close socket
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        resultArea = new JTextArea(8, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Client().setVisible(true));
    }
}
