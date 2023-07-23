package grupo1.supershop.servicios;

import grupo1.supershop.beans.Actividad;
import grupo1.supershop.beans.Modificable;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.persistencia.manejadores.ManejadorActividad;
import grupo1.supershop.persistencia.manejadores.ManejadorBase;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioActividad {

    private static ServicioActividad instance = null;

    private ServicioActividad() {
    }

    public static ServicioActividad getInstance() {
        if (instance == null) {
            instance = new ServicioActividad();
        }
        return instance;
    }

    public DtMensajeSistema crearActividad(String descripcion, Usuario usuario) {
        try {
            Actividad actividad = new Actividad(descripcion, usuario);
            ManejadorActividad.getInstance().crearActividad(actividad);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Actividad.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioActividad.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Actividad.create.error");
        }
    }

    public DtMensajeSistema crearActividad(Modificable modificable, String descripcion, Usuario usuario) {
        try {
            Actividad actividad = new Actividad(descripcion, usuario);
            ManejadorActividad.getInstance().crearActividad(actividad);
            modificable.agregarActividad(actividad);
            ManejadorBase.getInstance().modificarBean(modificable);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Actividad.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioActividad.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Actividad.create.error");
        }
    }

    public DtMensajeSistema registrarActvidadUsuario(Usuario usuario, String descripcion) {
        return this.crearActividad(usuario, descripcion, usuario);
    }

    public DtMensajeSistema registrarCreacion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha creado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarModificacion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha modificado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarEliminacion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha eliminado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarRestauracion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha restaurado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarBloqueo(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha bloqueado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarDesbloqueo(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha desbloqueado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarConfirmacion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha confirmado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarConclusion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha concluido " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarCancelacion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha cancelado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarFinalizacion(Modificable modificable, Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "Se ha iniciado " + modificable.getClass().getSimpleName() + " con el ID " + modificable.getId();
        return this.crearActividad(modificable, descripcion, usuario);
    }

    public DtMensajeSistema registrarInicioSesion(Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "El usuario ID: " + usuario.getId().toString() + " ha iniciado sesión";
        return this.crearActividad(usuario, descripcion, usuario);
    }

    public DtMensajeSistema registrarCerrarSesion(Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "El usuario ID: " + usuario.getId().toString() + " ha cerrado sesión";
        return this.crearActividad(usuario, descripcion, usuario);
    }

    public DtMensajeSistema registrarRecuperacionContraseña(Usuario usuario) {
        //CAMBIAR TEXTO POR PROPERTIES
        //TODO
        String descripcion = "El usuario ID: " + usuario.getId().toString() + " ha recuperado su contraseña";
        return this.crearActividad(usuario, descripcion, usuario);
    }

}
