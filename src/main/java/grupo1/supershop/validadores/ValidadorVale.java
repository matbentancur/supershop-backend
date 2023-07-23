package grupo1.supershop.validadores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.time.DateUtils;

public class ValidadorVale {

    private static ValidadorVale instance = null;

    public static ValidadorVale getInstance() {
        if (instance == null) {
            instance = new ValidadorVale();
        }
        return instance;
    }

    private List<String> validarBean(Vale vale) {
        return ValidadorBase.getInstance().validarBean(vale);
    }

    public List<String> estaBorrado(Vale vale) {
        List<String> listaErrores = new ArrayList<>();

        if (vale.isBorrado()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.deleted.error"));
        }

        return listaErrores;
    }

    public List<String> estaPendiente(Vale vale) {
        List<String> listaErrores = new ArrayList<>();

        if (vale.getEstado().equals(EstadoVale.PENDIENTE)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.pending.error"));
        }

        return listaErrores;
    }

    public List<String> estaUsado(Vale vale) {
        List<String> listaErrores = new ArrayList<>();

        if (vale.getEstado().equals(EstadoVale.USADO)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.used.error"));
        }

        return listaErrores;
    }

    public List<String> estaExpirado(Vale vale) {
        List<String> listaErrores = new ArrayList<>();
        
        Date fechaActual = new Date();
        if (!fechaActual.before(vale.getExpira())) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.expired.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Vale vale) {
        List<String> listaErrores = new ArrayList<>();

        if (vale == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> esPropietario(Vale vale, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        Comprador comprador = (Comprador) usuario;

        if (!vale.getComprador().equals(comprador)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.owner.error"));
        }

        return listaErrores;
    }

    public List<String> perteneceSusursal(Vale vale, Sucursal sucursal) {
        List<String> listaErrores = new ArrayList<>();

        if (!sucursal.equals(vale.getSucursal())) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.brach.error"));
        }

        return listaErrores;
    }
    
    public List<String> cumpleValidezMaxima(Vale vale) {
        List<String> listaErrores = new ArrayList<>();

        Date fechaActual = new Date();
        Date fechaValidezMaxima = DateUtils.addDays(fechaActual, 60);
        if (!vale.getExpira().before(fechaValidezMaxima)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Vale.maximumValidity.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Vale vale, Comprador comprador, Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esUsuarioSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, sucursal));
        listaErrores.addAll(ValidadorUsuario.getInstance().existe(comprador));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(comprador));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(sucursal));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(sucursal));
        listaErrores.addAll(this.cumpleValidezMaxima(vale));
        listaErrores.addAll(this.validarBean(vale));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificar(Vale vale, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esUsuarioSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, vale.getSucursal()));
        listaErrores.addAll(ValidadorUsuario.getInstance().existe(vale.getComprador()));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(vale.getComprador()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(vale.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(vale.getSucursal()));
        listaErrores.addAll(this.existe(vale));
        listaErrores.addAll(this.estaBorrado(vale));
        listaErrores.addAll(this.estaPendiente(vale));
        listaErrores.addAll(this.estaUsado(vale));
        listaErrores.addAll(this.cumpleValidezMaxima(vale));
        listaErrores.addAll(this.validarBean(vale));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarBorrar(Vale vale, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esUsuarioSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, vale.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(vale.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(vale.getSucursal()));
        listaErrores.addAll(this.existe(vale));
        listaErrores.addAll(this.estaBorrado(vale));
        listaErrores.addAll(this.estaPendiente(vale));
        listaErrores.addAll(this.estaUsado(vale));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarRestaurar(Vale vale, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esUsuarioSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, vale.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(vale.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(vale.getSucursal()));
        listaErrores.addAll(this.existe(vale));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
