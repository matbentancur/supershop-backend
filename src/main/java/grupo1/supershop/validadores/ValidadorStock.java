package grupo1.supershop.validadores;

import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Stock;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioStock;
import java.util.ArrayList;
import java.util.List;

public class ValidadorStock {

    private static ValidadorStock instance = null;

    public static ValidadorStock getInstance() {
        if (instance == null) {
            instance = new ValidadorStock();
        }
        return instance;
    }

    private List<String> validarBean(Stock stock) {
        return ValidadorBase.getInstance().validarBean(stock);
    }

    public List<String> existe(Stock stock) {
        List<String> listaErrores = new ArrayList<>();

        if (stock == null) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Stock.notexists.error"));
        }

        return listaErrores;
    }

    public List<String> stockDisponible(Sucursal sucursal, Producto producto, Integer cantidad) {
        List<String> listaErrores = new ArrayList<>();

        boolean existeStock = ServicioStock.getInstance().existeStock(sucursal, producto, cantidad);
        if (!existeStock) {
            listaErrores.add(ServicioMensajeSistema.getInstance().getMensajeProperties("Carrito.noStock.error"));
        }

        return listaErrores;
    }

    public DtMensajeSistema validarAgregarStock(Stock stock, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, stock.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(stock.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(stock.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(stock.getProducto()));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(stock.getProducto()));
        listaErrores.addAll(this.existe(stock));
        listaErrores.addAll(this.validarBean(stock));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarQuitarStock(Stock stock, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, stock.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(stock.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(stock.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(stock.getProducto()));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(stock.getProducto()));
        listaErrores.addAll(this.existe(stock));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

    public DtMensajeSistema validarModificarStock(Stock stock, Usuario usuario) {
        List<String> listaErrores = new ArrayList<>();

        listaErrores.addAll(ValidadorUsuario.getInstance().existe(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().estaBorradoBloqueado(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().esAdministradorSucursal(usuario));
        listaErrores.addAll(ValidadorUsuario.getInstance().perteneceSusursal(usuario, stock.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().existe(stock.getSucursal()));
        listaErrores.addAll(ValidadorSucursal.getInstance().estaBorrada(stock.getSucursal()));
        listaErrores.addAll(ValidadorProducto.getInstance().existe(stock.getProducto()));
        listaErrores.addAll(ValidadorProducto.getInstance().estaBorrado(stock.getProducto()));
        listaErrores.addAll(this.existe(stock));

        if (listaErrores.isEmpty()) {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
        } else {
            return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR(listaErrores);
        }
    }

}
