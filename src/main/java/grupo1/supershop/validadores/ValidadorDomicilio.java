package grupo1.supershop.validadores;

import grupo1.supershop.beans.Domicilio;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorDomicilio {

    private static ValidadorDomicilio instance = null;

    public static ValidadorDomicilio getInstance() {
        if (instance == null) {
            instance = new ValidadorDomicilio();
        }
        return instance;
    }

    private List<String> validarBean(Domicilio domicilio) {
        return ValidadorBase.getInstance().validarBean(domicilio);
    }

    public List<String> estaBorrado(Domicilio domicilio) {
        List<String> listaErrores = new ArrayList<>();

        if (domicilio.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Domicilio.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Domicilio domicilio) {
        List<String> listaErrores = new ArrayList<>();

        if (domicilio == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Domicilio.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> esPropietario(Domicilio domicilio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        Comprador comprador = (Comprador) usuario;

        if (!domicilio.getComprador().equals(comprador)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Domicilio.owner.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Domicilio domicilio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.validarBean(domicilio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(Domicilio domicilio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(domicilio));
        listaErrores.addAll(this.estaBorrado(domicilio));
        listaErrores.addAll(this.esPropietario(domicilio, usuario));
        listaErrores.addAll(this.validarBean(domicilio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Domicilio domicilio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(domicilio));
        listaErrores.addAll(this.estaBorrado(domicilio));
        listaErrores.addAll(this.esPropietario(domicilio, usuario));
        listaErrores.addAll(this.validarBean(domicilio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(Domicilio domicilio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(domicilio));
        listaErrores.addAll(this.esPropietario(domicilio, usuario));
        listaErrores.addAll(this.validarBean(domicilio));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
