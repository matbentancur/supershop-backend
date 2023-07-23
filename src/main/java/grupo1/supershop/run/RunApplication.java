package grupo1.supershop.run;

import grupo1.supershop.instalar.InstalarSistema;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "grupo1.supershop")
public class RunApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);

        //INSTALAR SISTEMA CON DATOS INICIALES
        InstalarSistema instalarSistema = new InstalarSistema();

        try {
            instalarSistema.instalar();
            System.out.println("APLICACION INICIADA");
        } catch (Exception ex) {
            System.out.println("ERROR AL INSTALAR");
            Logger.getLogger(RunApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
