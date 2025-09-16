import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class Ping {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("ping", "-c", "4", "ieseljust.com");
        
        try {
            Process p = pb.start();
            
            // Read the output from the process
            try (BufferedReader reader = new BufferedReader(
                 new InputStreamReader(p.getInputStream()))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {
            System.err.println("Error d'entrada/sortida: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
