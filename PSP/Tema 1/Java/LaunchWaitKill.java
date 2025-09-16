import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LaunchWaitKill {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Configurem l'aplicacio a llançar
        ArrayList<String> app=new ArrayList<String>();

        app.add("xclock");
        app.add("-geometry");
        app.add("500x500");

        // Creem la instancia de ProcessBuilder
        ProcessBuilder pb = new ProcessBuilder(app);

        // Definim i instanciem el procés
        Process p = pb.start();

        // Esperem 3 segons

        // El mètode waitFor espera el temps indicat, i
        // retorna un valor lògic, indicant si el procés
        // continua viu al cap del temps indicat.
        // Atés que aquest mètode pot generar una excepció
        // de tipus InterruptedException, afegim aquesta
        // a la clàusula catch
        boolean isProcessDead = p.waitFor(3, TimeUnit.SECONDS);

        // Si el procés segueix viu al cap de 3 segons
        // li enviem la senyal SIGTERM (p.destroy)

        if (!isProcessDead) {
            System.out.println("Destruint l'aplicació");
            p.destroy(); // Suggeriment: Pots usar destroyForcibily i comprovar el resultat.
            }

            // Com que la destrucció del procés no és immediata,
            // esperarem que aquest finalitze completament. Per a això
            // utilitzem el mètode isAlive, que retorna si el procés
            // continua viu o no.
            // Si el procés continua viu, esperarem un mil·lisegon, i
            // mostrarem un missatge.

            while (p.isAlive()) {
                System.out.println("El procés continua viu. Espere un mil·lisegon.");
                p.waitFor(1, TimeUnit.MILLISECONDS);
            };

            // Finalitzat el proces mostrem l'eixida d'aquest
            System.out.println("El procés ha finalitzat amb l'eixida: " + p.exitValue());
        }
}
