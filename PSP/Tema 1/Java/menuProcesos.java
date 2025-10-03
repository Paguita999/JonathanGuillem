import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class menuProcesos {
    
    public static void main(String[] args) {
        System.out.println("ElJust Eat Pizzeria. Menú.");
        System.out.println();
        
        int nucleos = Runtime.getRuntime().availableProcessors();
        
        ArrayList<String> todasLasPizzas = new ArrayList<>();
        int pageNumber = 1;
        int pageSize = 4;
        boolean hayMasPaginas = true;
        
        // Procesar por bloques según número de núcleos
        while (hayMasPaginas) {
            List<ProcessInfo> bloqueProcesos = new ArrayList<>();
            
            // Lanzar un bloque de procesos (tantos como núcleos)
            for (int i = 0; i < nucleos && hayMasPaginas; i++) {
                try {
                    ProcessBuilder pb = new ProcessBuilder();
                    String url = "https://pizza-rest-server-production.up.railway.app/api/pizzeria/pizzes?pageNumber=" + pageNumber + "&pageSize=" + pageSize;
                    pb.command("curl", "-H", "Accept: application/json", url);
                    
                    Process proceso = pb.start();
                    bloqueProcesos.add(new ProcessInfo(proceso, pageNumber));
                    
                    pageNumber++;
                    
                } catch (IOException e) {
                    System.err.println("Error lanzando proceso página " + pageNumber + ": " + e.getMessage());
                }
            }
            
            // Procesar resultados del bloque actual
            for (ProcessInfo info : bloqueProcesos) {
                try {
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(info.proceso.getInputStream()))) {
                        
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }
                    
                    // Esperar a que el proceso termine
                    info.proceso.waitFor();
                    
                    String jsonResponse = response.toString();
                    
                    // Verificar si es la última página
                    int totalCount = Integer.parseInt(extractTotalCount(jsonResponse));
                    int perPage = Integer.parseInt(extractPerPage(jsonResponse));
                    
                    if (totalCount <= perPage) {
                        hayMasPaginas = false;
                    }
                    
                    // Procesar pizzas de esta página
                    ArrayList<String> pizzasPagina = procesarJsonPagina(jsonResponse, info.pagina);
                    todasLasPizzas.addAll(pizzasPagina);
                    
                    
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error procesando página " + info.pagina + ": " + e.getMessage());
                }
            }
            
            // Pequeña pausa entre bloques para no saturar
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        for (String pizza : todasLasPizzas) {
            System.out.println(pizza);
        }
    }
    
    private static ArrayList<String> procesarJsonPagina(String jsonResponse, int pagina) {
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
            System.err.println("Error procesando JSON página " + pagina);
        }
        
        return pizzas;
    }
    
    private static String extractValue(String text, String startKey, String endChars) {
        try {
            int startIndex = text.indexOf(startKey);
            if (startIndex == -1) return "N/A";
            
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
    
    private static String extractTotalCount(String json) {
        try {
            int startIndex = json.indexOf("\"total_count\":");
            if (startIndex == -1) return "0";
            
            int valueStart = startIndex + 14;
            int endIndex = valueStart;
            while (endIndex < json.length()) {
                char c = json.charAt(endIndex);
                if (c == ',' || c == '}' || c == ' ') {
                    break;
                }
                endIndex++;
            }
            
            return json.substring(valueStart, endIndex).trim();
        } catch (Exception e) {
            return "0";
        }
    }
    
    private static String extractPerPage(String json) {
        try {
            int startIndex = json.indexOf("\"per_page\":");
            if (startIndex == -1) return "0";
            
            int valueStart = startIndex + 11;
            int endIndex = valueStart;
            while (endIndex < json.length()) {
                char c = json.charAt(endIndex);
                if (c == ',' || c == '}' || c == ' ') {
                    break;
                }
                endIndex++;
            }
            
            return json.substring(valueStart, endIndex).trim();
        } catch (Exception e) {
            return "0";
        }
    }
    
    // Clase auxiliar para almacenar información del proceso
    static class ProcessInfo {
        Process proceso;
        int pagina;
        
        ProcessInfo(Process proceso, int pagina) {
            this.proceso = proceso;
            this.pagina = pagina;
        }
    }


}