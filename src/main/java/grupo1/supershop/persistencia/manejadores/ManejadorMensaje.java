package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Mensaje;
import java.util.List;

public class ManejadorMensaje {

    private static ManejadorMensaje instance = null;

    private ManejadorMensaje() {
    }

    public static ManejadorMensaje getInstance() {
        if (instance == null) {
            instance = new ManejadorMensaje();
        }
        return instance;
    }

    public Mensaje obtenerMensaje(Long id) {
        return (Mensaje) ManejadorBase.getInstance().obtenerBean(new Mensaje(), "id", id);
    }

    public <T> Mensaje obtenerMensaje(String parametro, T valor) {
        return (Mensaje) ManejadorBase.getInstance().obtenerBean(new Mensaje(), parametro, valor);
    }

    public <T> List<Mensaje> listarMensajes(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Mensaje(), parametro, valor, "id", true);
    }

    public <T, S> List<Mensaje> listarMensajes(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Mensaje(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public void crearMensaje(Mensaje mensaje) throws Exception {
        ManejadorBase.getInstance().crearBean(mensaje);
    }

    public void borrarMensaje(Mensaje mensaje) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(mensaje);
    }

}
