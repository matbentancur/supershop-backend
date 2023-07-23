package grupo1.supershop.validadores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Reclamo;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.enums.EstadoReclamo;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioReclamo;
import java.util.ArrayList;
import java.util.List;

public class ValidadorReclamo {

    private static ValidadorReclamo instance = null;

    public static ValidadorReclamo getInstance() {
        if (instance == null) {
            instance = new ValidadorReclamo();
        }
        return instance;
    }

    private List<String> validarBean(Reclamo reclamo) {
        return ValidadorBase.getInstance().validarBean(reclamo);
    }

    public List<String> estaPendiente(Reclamo reclamo) {
        List<String> listaErrores = new ArrayList<>();

        if (reclamo.getEstado().equals(EstadoReclamo.PENDIENTE)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Reclamo.pending.error"));
        }

        return listaErrores;
    }

    public List<String> estaConfirmado(Reclamo reclamo) {
        List<String> listaErrores = new ArrayList<>();

        if (reclamo.getEstado().equals(EstadoReclamo.CONFIRMADO)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Reclamo.confirmed.error"));
        }

        return listaErrores;
    }

    public List<String> estaConcluido(Reclamo reclamo) {
        List<String> listaErrores = new ArrayList<>();

        if (reclamo.getEstado().equals(EstadoReclamo.CONCLUIDO)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Reclamo.concluded.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Reclamo reclamo) {
        List<String> listaErrores = new ArrayList<>();

        if (reclamo == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Reclamo.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> cumpleReclamosPendientes(Comprador comprador) {
        List<String> listaErrores = new ArrayList<>();

        if (!ServicioReclamo.getInstance().verificarReclamosPendientes(comprador)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Reclamo.pendingClaims.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Reclamo reclamo, Sucursal sucursal, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(sucursal));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(sucursal));
        listaErrores.addAll(this.cumpleReclamosPendientes((Comprador) usuario));
        listaErrores.addAll(this.validarBean(reclamo));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarConfirmar(Reclamo reclamo, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esUsuarioSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, reclamo.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(reclamo.getSucursal()));
        listaErrores.addAll(this.existe(reclamo));
        listaErrores.addAll(this.estaConfirmado(reclamo));
        listaErrores.addAll(this.estaConcluido(reclamo));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarConcluir(Reclamo reclamo, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esUsuarioSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, reclamo.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(reclamo.getSucursal()));
        listaErrores.addAll(this.existe(reclamo));
        listaErrores.addAll(this.estaPendiente(reclamo));
        listaErrores.addAll(this.estaConcluido(reclamo));
        listaErrores.addAll(this.validarBean(reclamo));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
