package grupo1.supershop.controladores;

import grupo1.supershop.beans.Conversacion;
import grupo1.supershop.beans.Mensaje;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtMensaje;
import grupo1.supershop.interfaces.controladores.IControladorMensaje;
import grupo1.supershop.persistencia.manejadores.ManejadorConversacion;
import grupo1.supershop.persistencia.manejadores.ManejadorMensaje;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.servicios.ServicioUsuario;
import grupo1.supershop.validadores.ValidadorMensaje;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorMensaje implements IControladorMensaje {

    @Override
    public DtMensaje obtenerMensaje(Long mensajeId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        Mensaje mensaje = ManejadorMensaje.getInstance().obtenerMensaje(mensajeId);
        if (mensaje == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        if (!ServicioUsuario.getInstance().esPropietarioConversacion(usuario, mensaje.getConversacion())) {
            return null;
        }

        return (DtMensaje) mensaje.getDataType();
    }

    @Override
    public List<DtMensaje> listarMensajes(Long conversacionId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //OBTIENE CONVERSACION
        Conversacion conversacion = ManejadorConversacion.getInstance().obtenerConversacion(conversacionId);
        if (conversacion == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        if (!ServicioUsuario.getInstance().esPropietarioConversacion(usuario, conversacion)) {
            return null;
        }

        List<Mensaje> lista = ManejadorMensaje.getInstance().listarMensajes("conversacion.id", conversacionId, "borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public DtMensajeSistema enviarMensaje(DtMensaje dtMensaje, Long conversacionId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //OBTIENE CONVERSACION
            Conversacion conversacion = ManejadorConversacion.getInstance().obtenerConversacion(conversacionId);
            if (conversacion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Conversacion.notexists.error");
            }

            //OBTIENE MENSAJE
            Mensaje mensaje = new Mensaje(dtMensaje);
            mensaje.setConversacion(conversacion);
            mensaje.setUsuario(usuario);

            //VALIDA MENSAJE
            DtMensajeSistema dtMensajeSistema = ValidadorMensaje.getInstance().validarEnviar(mensaje, conversacion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA MENSAJE
            ManejadorMensaje.getInstance().crearMensaje(mensaje);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(mensaje, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Mensaje.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Mensaje.send.error");
    }

    @Override
    public DtMensajeSistema borrarMensaje(Long mensajeId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //OBTIENE MENSAJE
            Mensaje mensaje = ManejadorMensaje.getInstance().obtenerMensaje(mensajeId);
            if (mensaje == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Mensaje.notexists.error");
            }

            //VALIDA MENSAJE
            DtMensajeSistema dtMensajeSistema = ValidadorMensaje.getInstance().validarBorrar(mensaje, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA MENSAJE
            ManejadorMensaje.getInstance().borrarMensaje(mensaje);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(mensaje, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Mensaje.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMensaje.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Mensaje.delete.error");
    }

}
