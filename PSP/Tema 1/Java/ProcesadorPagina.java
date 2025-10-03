import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProcesadorPagina implements Runnable {
    private int numeroPagina;
    private int tamañoPagina;
    private ArrayList<String> pizzasResultado;
    private boolean esUltimaPagina;
    public static volatile boolean ultimaPaginaDetectada = false; // bandera global

    public ProcesadorPagina(int numeroPagina, int tamañoPagina, ArrayList<String> pizzasResultado) {
        this.numeroPagina = numeroPagina;
        this.tamañoPagina = tamañoPagina;
        this.pizzasResultado = pizzasResultado;
        this.esUltimaPagina = false;
    }

    public boolean esUltimaPagina() {
        return esUltimaPagina;
    }

    @Override
    public void run() {
        String nombreHilo = Thread.currentThread().getName();

        try {
            ProcessBuilder pb = new ProcessBuilder();
            String url = "https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber="
                    + numeroPagina + "&pageSize=" + tamañoPagina;
            pb.command("curl", "-H", "Accept: application/json", url);

            Process proceso = pb.start();

            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(proceso.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            proceso.waitFor();

            String jsonResponse = response.toString();

            // Extraer total_count y per_page de ESTA página específica
            int totalCount = extraerTotalCount(jsonResponse);
            int perPage = extraerPerPage(jsonResponse);

            // Verificar si es la última página
            if (totalCount < perPage) {
                this.esUltimaPagina = true;
                ultimaPaginaDetectada = true; // avisar a todos los hilos
            }

            ArrayList<String> pizzasPagina = procesarJsonPagina(jsonResponse);

            synchronized (pizzasResultado) {
                pizzasResultado.addAll(pizzasPagina);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println(nombreHilo + " - Error en página " + numeroPagina + ": " + e.getMessage());
        }
    }

    private int extraerTotalCount(String json) {
        try {
            int startIndex = json.indexOf("\"total_count\":");
            if (startIndex == -1)
                return 0;

            int valueStart = startIndex + 14;
            int endIndex = valueStart;
            while (endIndex < json.length()) {
                char c = json.charAt(endIndex);
                if (c == ',' || c == '}' || c == ' ') {
                    break;
                }
                endIndex++;
            }

            String totalCountStr = json.substring(valueStart, endIndex).trim();
            return Integer.parseInt(totalCountStr);
        } catch (Exception e) {
            return 0;
        }
    }

    private int extraerPerPage(String json) {
        try {
            int startIndex = json.indexOf("\"per_page\":");
            if (startIndex == -1)
                return 0;

            int valueStart = startIndex + 11;
            int endIndex = valueStart;
            while (endIndex < json.length()) {
                char c = json.charAt(endIndex);
                if (c == ',' || c == '}' || c == ' ') {
                    break;
                }
                endIndex++;
            }

            String perPageStr = json.substring(valueStart, endIndex).trim();
            return Integer.parseInt(perPageStr);
        } catch (Exception e) {
            return 0;
        }
    }

    private ArrayList<String> procesarJsonPagina(String jsonResponse) {
        ArrayList<String> pizzas = new ArrayList<>();

        try {
            String[] pizzasArray = jsonResponse.split("\"id\":\"");

            for (int i = 1; i < pizzasArray.length; i++) {
                String pizza = pizzasArray[i];
                String nombre = extractValue(pizza, "\"nom\":\"", "\"");
                String precio = extractValue(pizza, "\"preu\":", ",}");
                pizzas.add(String.format("%-25s %6s€", nombre, precio));
            }

        } catch (Exception e) {
            System.err.println("Error procesando JSON página " + numeroPagina);
        }

        return pizzas;
    }

    private String extractValue(String text, String startKey, String endChars) {
        try {
            int startIndex = text.indexOf(startKey);
            if (startIndex == -1)
                return "N/A";

            int start = startIndex + startKey.length();
            int end = text.length();

            for (char c : endChars.toCharArray()) {
                int pos = text.indexOf(c, start);
                if (pos != -1 && pos < end) {
                    end = pos;
                }
            }

            return text.substring(start, end);
        } catch (Exception e) {
            return "N/A";
        }
    }
}