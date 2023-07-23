package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Sesion;
import grupo1.supershop.datatypes.DtSesion;

public class ManejadorSesion {

    private static ManejadorSesion instance = null;

    private ManejadorSesion() {
    }

    public static ManejadorSesion getInstance() {
        if (instance == null) {
            instance = new ManejadorSesion();
        }
        return instance;
    }

    public Sesion obtenerSesion(Long usuarioId) {
        return (Sesion) ManejadorBase.getInstance().obtenerBean(new Sesion(), "usuario.id", usuarioId);
    }

    public Sesion obtenerSesion(DtSesion dtSesion) {
        return this.obtenerSesion(dtSesion.getDtUsuario().getId());
    }

    public <T> Sesion obtenerSesion(String parametro, T valor) {
        return (Sesion) ManejadorBase.getInstance().obtenerBean(new Sesion(), parametro, valor);
    }

    public void crearSesion(Sesion sesion) throws Exception {
        ManejadorBase.getInstance().crearBean(sesion);
    }

    public void borrarSesion(Sesion sesion) throws Exception {
        ManejadorBase.getInstance().borrarBean(sesion);
    }

}
