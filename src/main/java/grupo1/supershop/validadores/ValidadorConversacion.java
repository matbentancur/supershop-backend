package grupo1.supershop.validadores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Conversacion;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorConversacion {

    private static ValidadorConversacion instance = null;

    public static ValidadorConversacion getInstance() {
        if (instance == null) {
            instance = new ValidadorConversacion();
        }
        return instance;
    }

    private List<String> validarBean(Conversacion conversacion) {
        return ValidadorBase.getInstance().validarBean(conversacion);
    }

    public List<String> estaFinalizada(Conversacion conversacion) {
        List<String> listaErrores = new ArrayList<>();

        if (conversacion.isFinalizada()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Conversacion.conclude.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Conversacion conversacion) {
        List<String> listaErrores = new ArrayList<>();

        if (conversacion == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Conversacion.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> esPropietario(Conversacion conversacion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        if (usuario instanceof Comprador) {
            if (!conversacion.getComprador().equals(usuario)) {
                listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Conversacion.owner.error"));
            }
        }

        if (usuario instanceof UsuarioSucursal) {
            UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
            if (!conversacion.getSucursal().equals(usuarioSucursal.getSucursal())) {
                listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Conversacion.owner.error"));
            }
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Conversacion conversacion, Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(sucursal));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(sucursal));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarFinalizar(Conversacion conversacion, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esUsuarioSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, conversacion.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(conversacion.getSucursal()));
        listaErrores.addAll(this.existe(conversacion));
        listaErrores.addAll(this.estaFinalizada(conversacion));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
