import java.io.IOException;

public class Launcher {
    public static void main(String[] args) {
        ProcessBuilder pb = new ProcessBuilder();
        pb.command("google-chrome", "https://ieseljust.com");
        try {
            Process p1=pb.start();
            Process p2=pb.start();
            System.out.println("PID del procés 1:"+p1.pid());
            System.out.println("PID del procés 2:"+p2.pid());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
