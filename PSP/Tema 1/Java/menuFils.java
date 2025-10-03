import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class menuFils {

    public static void main(String[] args) {
        System.out.println("ElJust Eat Pizzeria. Menú.");
        System.out.println("=".repeat(60));

        int pageSize = 4;
        int tamañoPool = 5; // Número de hilos en el pool
        ArrayList<String> todasLasPizzas = new ArrayList<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        AtomicInteger paginaActual = new AtomicInteger(1);

        Runnable tarea = new Runnable() {
            @Override
            public void run() {
                int pagina = paginaActual.getAndIncrement();
                ProcesadorPagina procesador = new ProcesadorPagina(pagina, pageSize, todasLasPizzas);
                procesador.run();

                // Si la página no es la última y el pool sigue abierto, lanza otra tarea
                if (!procesador.esUltimaPagina() &&
                    !executor.isShutdown() &&
                    !ProcesadorPagina.ultimaPaginaDetectada) {
                    try {
                        executor.execute(this);
                    } catch (java.util.concurrent.RejectedExecutionException e) {
                    }
                } else if (procesador.esUltimaPagina()) {
                    executor.shutdown();
                }
            }
        };

        for (int i = 0; i < tamañoPool; i++) {
            executor.execute(tarea);
        }

        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        if (!todasLasPizzas.isEmpty()) {
            for (String pizza : todasLasPizzas) {
                System.out.println(pizza);
            }
        } else {
            System.out.println("❌ No se obtuvieron pizzas");
        }
    }
}