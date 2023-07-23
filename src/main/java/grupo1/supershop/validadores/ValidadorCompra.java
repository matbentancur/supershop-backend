package grupo1.supershop.validadores;

import grupo1.supershop.beans.Carrito;
import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.CompraDomicilio;
import grupo1.supershop.beans.CompraSucursal;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Domicilio;
import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.enums.EstadoCompra;
import grupo1.supershop.enums.MetodoPago;
import grupo1.supershop.servicios.ServicioCompra;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidadorCompra {

    private static ValidadorCompra instance = null;

    public static ValidadorCompra getInstance() {
        if (instance == null) {
            instance = new ValidadorCompra();
        }
        return instance;
    }

    private List<String> validarBean(Compra compra) {
        return ValidadorBase.getInstance().validarBean(compra);
    }

    public List<String> esCompraDomicilio(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (!(compra instanceof CompraDomicilio)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.home.error"));
        }

        return listaErrores;
    }

    public List<String> esCompraSucursal(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (!(compra instanceof CompraSucursal)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.branch.error"));
        }

        return listaErrores;
    }

    public List<String> estaPendiente(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getEstado().equals(EstadoCompra.PENDIENTE)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.pending.error"));
        }

        return listaErrores;
    }

    public List<String> estaConfirmada(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getEstado().equals(EstadoCompra.CONFIRMADA)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.confirmed.error"));
        }

        return listaErrores;
    }

    public List<String> estaConcluida(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getEstado().equals(EstadoCompra.CONCLUIDA)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.concluded.error"));
        }

        return listaErrores;
    }

    public List<String> estaCancelada(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getEstado().equals(EstadoCompra.CANCELADA)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.canceled.error"));
        }

        return listaErrores;
    }

    public List<String> estaDevuelta(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getEstado().equals(EstadoCompra.DEVUELTA)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.returned.error"));
        }

        return listaErrores;
    }

    public List<String> estaProceso(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getEstado().equals(EstadoCompra.PROCESO)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.inProgress.error"));
        }

        return listaErrores;
    }

    public List<String> existe(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> esPropietario(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        Comprador comprador = (Comprador) usuario;

        if (!compra.getComprador().equals(comprador)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.owner.error"));
        }

        return listaErrores;
    }

    public List<String> estaPronta(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getMetodoPago() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.paymentMethod.error"));
        }

        if (compra instanceof CompraDomicilio) {
            CompraDomicilio compraDomicilio = (CompraDomicilio) compra;
            if (compraDomicilio.getDomicilio() == null) {
                listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.address.error"));
            }
            if (compraDomicilio.getMetodoEnvio() == null) {
                listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.shipping.error"));
            }
            if (!compra.isPago()) {
                listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.payment.error"));
            }
        }

        return listaErrores;
    }

    public List<String> estaPaga(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (!compra.isPago()) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.paid.error"));
        }

        return listaErrores;
    }

    public List<String> tieneMetodoPago(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getMetodoPago() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.paymentMethod.error"));
        }

        return listaErrores;
    }

    public List<String> tieneMetodoPagoPayPal(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getMetodoPago().equals(MetodoPago.PAYPAL)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.paymentMethod.paypal.error"));
        }

        return listaErrores;
    }

    public List<String> tieneMetodoPagoEfectivo(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getMetodoPago().equals(MetodoPago.EFECTIVO)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.paymentMethod.cash.error"));
        }

        return listaErrores;
    }

    public List<String> tieneOrdenPayPalId(Compra compra) {
        List<String> listaErrores = new ArrayList<>();

        if (compra.getPaypalOrdenId() == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.paypal.order.error"));
        }

        return listaErrores;
    }

    public List<String> cumplePesoMaximo(Compra compra, MetodoEnvio metodoEnvio) {
        List<String> listaErrores = new ArrayList<>();

        if (!ServicioCompra.getInstance().verificarPesoCompra(compra, metodoEnvio)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.weight.error"));
        }

        return listaErrores;
    }

    public List<String> cumpleCantidadProductosMaximo(Compra compra, MetodoEnvio metodoEnvio) {
        List<String> listaErrores = new ArrayList<>();

        if (!ServicioCompra.getInstance().verificarCantidadProductos(compra, metodoEnvio)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.quantity.error"));
        }

        return listaErrores;
    }

    public List<String> cumpleMinimoCompra(Carrito carrito) {
        List<String> listaErrores = new ArrayList<>();

        if (!ServicioCompra.getInstance().verificarMinimoCompra(carrito)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.minimumAmount.error"));
        }

        return listaErrores;
    }

    public List<String> cumpleMontoValesCompra(Compra compra, Set<Vale> conjuntoVales) {
        List<String> listaErrores = new ArrayList<>();

        if (!ServicioCompra.getInstance().verificarMontoValesCompra(compra, conjuntoVales)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.voucherAmount.error"));
        }

        return listaErrores;
    }

    public List<String> cumpleComprasPendientes(Comprador comprador) {
        List<String> listaErrores = new ArrayList<>();

        if (!ServicioCompra.getInstance().verificarComprasPendientes(comprador)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Compra.pendingPurchases.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarIniciarCompra(Carrito carrito, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(carrito.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(carrito.getSucursal()));
        listaErrores.addAll(ValidadorCarrito.getInstance().existe(carrito));
        listaErrores.addAll(ValidadorCarrito.getInstance().esPropietario(carrito, usuario));
        listaErrores.addAll(this.cumpleMinimoCompra(carrito));
        listaErrores.addAll(this.cumpleComprasPendientes((Comprador) usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarConfirmar(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, compra.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(compra.getSucursal()));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaProceso(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarConcluir(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, compra.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(compra.getSucursal()));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.estaProceso(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarCancelar(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarDevolver(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, compra.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(compra.getSucursal()));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaProceso(compra));
        listaErrores.addAll(this.estaPaga(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAplicarMetodoPago(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAplicarMetodoEnvio(Compra compra, MetodoEnvio metodoEnvio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorMetodoEnvio.getInstance().existe(metodoEnvio));
        listaErrores.addAll(ValidadorMetodoEnvio.getInstance().estaBorrado(metodoEnvio));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.esCompraDomicilio(compra));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.cumplePesoMaximo(compra, metodoEnvio));
        listaErrores.addAll(this.cumpleCantidadProductosMaximo(compra, metodoEnvio));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarDomicilio(Compra compra, Domicilio domicilio, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorDomicilio.getInstance().existe(domicilio));
        listaErrores.addAll(ValidadorDomicilio.getInstance().estaBorrado(domicilio));
        listaErrores.addAll(ValidadorDomicilio.getInstance().esPropietario(domicilio, usuario));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.esCompraDomicilio(compra));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAplicarObservacionesComprador(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarObservacionesSucursal(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, compra.getSucursal()));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaProceso(compra));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAplicarVales(Compra compra, Set<Vale> conjuntoVales, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));

        for (Vale vale : conjuntoVales) {
            listaErrores.addAll(ValidadorVale.getInstance().existe(vale));
            listaErrores.addAll(ValidadorVale.getInstance().esPropietario(vale, usuario));
            listaErrores.addAll(ValidadorVale.getInstance().perteneceSusursal(vale, compra.getSucursal()));
            listaErrores.addAll(ValidadorVale.getInstance().estaBorrado(vale));
            listaErrores.addAll(ValidadorVale.getInstance().estaPendiente(vale));
            listaErrores.addAll(ValidadorVale.getInstance().estaUsado(vale));
            listaErrores.addAll(ValidadorVale.getInstance().estaExpirado(vale));
        }

        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.cumpleMontoValesCompra(compra, conjuntoVales));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarFinalizar(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.estaPronta(compra));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarCrearOrdenPayPal(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.tieneMetodoPago(compra));
        listaErrores.addAll(this.tieneMetodoPagoEfectivo(compra));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarCapturarPagoPayPal(Compra compra, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(this.existe(compra));
        listaErrores.addAll(this.esPropietario(compra, usuario));
        listaErrores.addAll(this.estaCancelada(compra));
        listaErrores.addAll(this.estaConcluida(compra));
        listaErrores.addAll(this.estaConfirmada(compra));
        listaErrores.addAll(this.estaDevuelta(compra));
        listaErrores.addAll(this.estaPendiente(compra));
        listaErrores.addAll(this.tieneMetodoPago(compra));
        listaErrores.addAll(this.tieneMetodoPagoEfectivo(compra));
        listaErrores.addAll(this.tieneOrdenPayPalId(compra));
        listaErrores.addAll(this.validarBean(compra));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
