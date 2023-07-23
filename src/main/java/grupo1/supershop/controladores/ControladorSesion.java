package grupo1.supershop.controladores;

import grupo1.supershop.beans.Sesion;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtInicioSesion;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioAutenticacion;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioSesion;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorSesion implements IControladorSesion {

    @Override
    public DtSesion obtenerSesion(String email, String password) {
        Sesion sesion = ServicioSesion.getInstance().obtenerSesion(email, password);
        if (sesion == null) {
            return null;
        }
        return (DtSesion) sesion.getDataType();
    }

    @Override
    public DtSesion obtenerSesionSeguro(DtInicioSesion dtInicioSesion) {
        Sesion sesion = ServicioSesion.getInstance().obtenerSesion(dtInicioSesion.getEmail(), dtInicioSesion.getPassword());
        if (sesion == null) {
            return null;
        }
        return (DtSesion) sesion.getDataType();
    }

    @Override
    public DtMensajeSistema iniciarSesion(String email, String password) {
        try {
            DtMensajeSistema dtMensajeSistema = ServicioAutenticacion.getInstance().autenticarUsuario(email, password);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", email);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            Sesion sesion = ManejadorSesion.getInstance().obtenerSesion(usuario.getId());
            if (sesion != null) {
                ManejadorSesion.getInstance().borrarSesion(sesion);
            }

            sesion = new Sesion(usuario);
            ManejadorSesion.getInstance().crearSesion(sesion);

            ServicioActividad.getInstance().registrarInicioSesion(usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sesion.login.success");

        } catch (Exception ex) {
            Logger.getLogger(ControladorSesion.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.autentication.error");
        }
    }

    @Override
    public DtMensajeSistema iniciarSesionSeguro(DtInicioSesion dtInicioSesion) {
        try {
            DtMensajeSistema dtMensajeSistema = ServicioAutenticacion.getInstance().autenticarUsuario(dtInicioSesion.getEmail(), dtInicioSesion.getPassword());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", dtInicioSesion.getEmail());
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            Sesion sesion = ManejadorSesion.getInstance().obtenerSesion(usuario.getId());
            if (sesion != null) {
                ManejadorSesion.getInstance().borrarSesion(sesion);
            }

            sesion = new Sesion(usuario);
            ManejadorSesion.getInstance().crearSesion(sesion);

            ServicioActividad.getInstance().registrarInicioSesion(usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sesion.login.success");

        } catch (Exception ex) {
            Logger.getLogger(ControladorSesion.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.autentication.error");
        }
    }

    @Override
    public DtMensajeSistema cerrarSesion(String secret) {
        try {
            //EL USUARIO NO EXISTE O NO HAY SESION
            Sesion sesion = ManejadorSesion.getInstance().obtenerSesion("secreto", secret);
            if (sesion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.logout.code.error");
            }

            ManejadorSesion.getInstance().borrarSesion(sesion);
            ServicioActividad.getInstance().registrarCerrarSesion(sesion.getUsuario());

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sesion.logout.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.logout.error");
        }

    }

    @Override
    public DtMensajeSistema validarSesion(String secret) {
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secret);
        if (usuario == null) {
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.secret.error");
        } else {
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Sesion.secret.success");
        }
    }

}
