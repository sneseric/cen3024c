import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server extends JFrame {
    private JLabel statusLabel;
    private JTextArea logArea;

    public Server() {
        setTitle("Server");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        statusLabel = new JLabel("Server started at " + getCurrentDateTime());
        add(statusLabel, BorderLayout.NORTH);

        logArea = new JTextArea(10, 30);
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
        startServer();
    }

    private void startServer() {
        try {
            // Start the server on separate thread to listen for incoming connections
            ServerSocket serverSocket = new ServerSocket(5000);

            while (true) {
                // Wait for client connection
                Socket socket = serverSocket.accept();

                // Handle client request in separate thread
                new Thread(() -> handleClientRequest(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            String numberString = reader.readLine();
            int number = Integer.parseInt(numberString);

            // Display the received number and time in the logArea
            logMessage("Number received from client to check if prime is: " + number);
            logMessage("Time: " + getCurrentDateTime());

            // Check if number is prime
            boolean isPrime = isPrime(number);

            // Send result back to the client
            writer.println(isPrime ? number + " is prime." : number + " is not prime.");

            // Close socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1)
            return false;

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0)
                return false;
        }

        return true;
    }

    private void logMessage(String message) {
        logArea.append(message + "\n");
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        new Server();
    }
}
