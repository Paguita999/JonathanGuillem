import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.util.concurrent.TimeUnit;

public class Ping2 {
	public static void main(String[] args) {
		ProcessBuilder pb = new ProcessBuilder();
		pb.command("ping", "-c", "4", "ieseljust.com");

		try {
			Process p = pb.start();

			// Read the output from the process
			try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(p.getInputStream()))) {

				try (BufferedWriter writer = new BufferedWriter(
					new FileWriter("foo.txt"))) {

					String line;
					while ((line = reader.readLine()) != null) {
					writer.write(line);
					}
				}
			}

		} catch (IOException e) {
			System.err.println("Error d'entrada/sortida: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
