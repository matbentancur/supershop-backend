package grupo1.supershop.validadores;

import grupo1.supershop.beans.Carrito;
import grupo1.supershop.beans.CarritoProducto;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import java.util.ArrayList;
import java.util.List;

public class ValidadorCarrito {

    private static ValidadorCarrito instance = null;

    public static ValidadorCarrito getInstance() {
        if (instance == null) {
            instance = new ValidadorCarrito();
        }
        return instance;
    }

    private List<String> validarBean(Carrito carrito) {
        return ValidadorBase.getInstance().validarBean(carrito);
    }

    public List<String> existe(Carrito carrito) {
        List<String> listaErrores = new ArrayList<>();

        if (carrito == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Carrito.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> esPropietario(Carrito carrito, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        Comprador comprador = (Comprador) usuario;

        if (!carrito.getComprador().equals(comprador)) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Carrito.owner.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarCrear(Carrito carrito, Producto producto, Integer cantidad, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(carrito.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(carrito.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(producto));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(producto));
        listaErrores.addAll(this.validarBean(carrito));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarAgregarProducto(Carrito carrito, Producto producto, Integer cantidad, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(carrito.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(carrito.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(producto));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(producto));
        listaErrores.addAll(this.existe(carrito));
        listaErrores.addAll(this.esPropietario(carrito, usuario));
        listaErrores.addAll(this.validarBean(carrito));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarProducto(Carrito carrito, Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(carrito.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(carrito.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(producto));
        listaErrores.addAll(this.existe(carrito));
        listaErrores.addAll(this.esPropietario(carrito, usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarCantidadProducto(Carrito carrito, Producto producto, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esComprador(usuario));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(carrito.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(carrito.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(producto));
        listaErrores.addAll(this.existe(carrito));
        listaErrores.addAll(this.esPropietario(carrito, usuario));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarCarritoProducto(CarritoProducto carritoProducto) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorStock.getInstance().stockDisponible(carritoProducto.getCarrito().getSucursal(), carritoProducto.getProducto(), carritoProducto.getCantidad()));
        listaErrores.addAll(ValidadorBase.getInstance().validarBean(carritoProducto));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
