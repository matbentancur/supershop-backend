package grupo1.supershop.servicios;

import grupo1.supershop.beans.Sesion;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.persistencia.manejadores.ManejadorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;

public class ServicioSesion {

    private static ServicioSesion instance = null;

    private ServicioSesion() {
    }

    public static ServicioSesion getInstance() {
        if (instance == null) {
            instance = new ServicioSesion();
        }
        return instance;
    }

    public Sesion obtenerSesion(String email, String password) {
        try {
            DtMensajeSistema dtMensajeSistema = ServicioAutenticacion.getInstance().autenticarUsuario(email, password);
            if (!dtMensajeSistema.isExitoso()) {
                return null;
            }

            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", email);
            if (usuario == null) {
                return null;
            }

            Sesion sesion = ManejadorSesion.getInstance().obtenerSesion(usuario.getId());
            if (sesion == null) {
                return null;
            }

            return sesion;
        } catch (Exception ex) {
            Logger.getLogger(ServicioSesion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Usuario obtenerUsuario(String secreto) {
        try {
            Sesion sesion = ManejadorSesion.getInstance().obtenerSesion("secreto", secreto);
            if (sesion == null) {
                return null;
            }
            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario(sesion.getUsuario().getId());
            if (usuario == null) {
                return null;
            }
            if (!this.verificarValidezSesion(sesion)){
                return null;
            }
            return usuario;
        } catch (Exception ex) {
            Logger.getLogger(ServicioSesion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public DtMensajeSistema verificarSesion(String secreto) {
        try {
            Sesion sesion = ManejadorSesion.getInstance().obtenerSesion("secreto", secreto);
            if (sesion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }
            if (!this.verificarValidezSesion(sesion)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.expired.error");
            }
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sesion.exists.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioSesion.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
        }
    }

    private boolean verificarValidezSesion(Sesion sesion) {
        Date fechaActual = new Date();
        if (fechaActual.before(sesion.getValidez())) {
            return true;
        } else {
            return false;
        }
    }

    public String getSecret() {
        return RandomStringUtils.randomAlphanumeric(128);
    }

    public Date getValidez() {
        Date fechaActual = new Date();
        return DateUtils.addDays(fechaActual, 1);
    }

}
