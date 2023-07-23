package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Conversacion;
import java.util.List;

public class ManejadorConversacion {

    private static ManejadorConversacion instance = null;

    private ManejadorConversacion() {
    }

    public static ManejadorConversacion getInstance() {
        if (instance == null) {
            instance = new ManejadorConversacion();
        }
        return instance;
    }

    public Conversacion obtenerConversacion(Long id) {
        return (Conversacion) ManejadorBase.getInstance().obtenerBean(new Conversacion(), "id", id);
    }

    public <T> Conversacion obtenerConversacion(String parametro, T valor) {
        return (Conversacion) ManejadorBase.getInstance().obtenerBean(new Conversacion(), parametro, valor);
    }

    public <T> List<Conversacion> listarConversaciones(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Conversacion(), parametro, valor, parametro, true);
    }

    public <T, S> List<Conversacion> listarConversaciones(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Conversacion(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public void crearConversacion(Conversacion conversacion) throws Exception {
        ManejadorBase.getInstance().crearBean(conversacion);
    }

    public void modificarConversacion(Conversacion conversacion) throws Exception {
        ManejadorBase.getInstance().modificarBean(conversacion);
    }

}
