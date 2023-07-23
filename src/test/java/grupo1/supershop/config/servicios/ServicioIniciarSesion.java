package grupo1.supershop.config.servicios;

import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;

public class ServicioIniciarSesion {
    private static ServicioIniciarSesion instance = null;

    private ServicioIniciarSesion() {
    }

    public static ServicioIniciarSesion getInstance() {
        if (instance == null) {
            instance = new ServicioIniciarSesion();
        }
        return instance;
    }

    public String iniciarSesion(String email, String passw) {
        IControladorSesion icSesion = FabricaSesion.getIControladorSesion();
        icSesion.iniciarSesion(email, passw);
        return icSesion.obtenerSesion(email, passw).getSecreto();
    }

    public void cerrarSesion(String secreto) {
        IControladorSesion icSesion = FabricaSesion.getIControladorSesion();
        icSesion.cerrarSesion(secreto);
    }
}
