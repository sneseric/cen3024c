import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Thread serverThread = new Thread(() -> runServer());
        serverThread.start();

        try {
            Thread.sleep(3000); // Delay for 3 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread clientThread = new Thread(() -> runClient());
        clientThread.start();
    }

    private static void runServer() {
        try {
            ProcessBuilder serverProcessBuilder = new ProcessBuilder("java", "-jar", "server-cli.jar");
            serverProcessBuilder.inheritIO();
            Process serverProcess = serverProcessBuilder.start();
            serverProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runClient() {
        try {
            ProcessBuilder clientProcessBuilder = new ProcessBuilder("java", "-jar", "client-cli.jar");
            clientProcessBuilder.inheritIO();
            Process clientProcess = clientProcessBuilder.start();
            clientProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
