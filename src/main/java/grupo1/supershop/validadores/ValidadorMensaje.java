package grupo1.supershop.validadores;

import grupo1.supershop.beans.Conversacion;
import grupo1.supershop.beans.Mensaje;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorMensaje {

    private static ValidadorMensaje instance = null;

    public static ValidadorMensaje getInstance() {
        if (instance == null) {
            instance = new ValidadorMensaje();
        }
        return instance;
    }

    private List<String> validarBean(Mensaje mensaje) {
        return ValidadorBase.getInstance().validarBean(mensaje);
    }

    public List<String> estaBorrado(Mensaje mensaje) {
        List<String> listaErrores = new ArrayList<>();

        if (mensaje.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Mensaje.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Mensaje mensaje) {
        List<String> listaErrores = new ArrayList<>();

        if (mensaje == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Mensaje.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> esPropietario(Mensaje mensaje, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (!mensaje.getUsuario().equals(usuario)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Mensaje.owner.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarEnviar(Mensaje mensaje, Conversacion conversacion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(conversacion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(conversacion.getSucursal()));
        listaErrores.addAll(ValidadorConversacion.getInstance().existe(conversacion));
        listaErrores.addAll(ValidadorConversacion.getInstance().esPropietario(conversacion, usuario));
        listaErrores.addAll(ValidadorConversacion.getInstance().estaFinalizada(conversacion));
        listaErrores.addAll(this.validarBean(mensaje));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Mensaje mensaje, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(mensaje.getConversacion().getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(mensaje.getConversacion().getSucursal()));
        listaErrores.addAll(ValidadorConversacion.getInstance().existe(mensaje.getConversacion()));
        listaErrores.addAll(ValidadorConversacion.getInstance().esPropietario(mensaje.getConversacion(), usuario));
        listaErrores.addAll(ValidadorConversacion.getInstance().estaFinalizada(mensaje.getConversacion()));
        listaErrores.addAll(this.existe(mensaje));
        listaErrores.addAll(this.esPropietario(mensaje, usuario));
        listaErrores.addAll(this.estaBorrado(mensaje));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
