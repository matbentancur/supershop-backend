package grupo1.supershop.servicios;

import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.CompraProducto;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Stock;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorCompraProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorStock;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioStock {

    private static ServicioStock instance = null;

    private ServicioStock() {
    }

    public static ServicioStock getInstance() {
        if (instance == null) {
            instance = new ServicioStock();
        }
        return instance;
    }

    public void descontarStock(Compra compra) {
        try {
            List<CompraProducto> listaCompraProdcuto = ManejadorCompraProducto.getInstance().listarCompraProductos("compra.id", compra.getId());
            for (CompraProducto compraProducto : listaCompraProdcuto) {
                Stock stock = ManejadorStock.getInstance().obtenerStock(compra.getSucursal().getId(), compraProducto.getProducto().getId());
                if (stock != null) {
                    stock.quitarCantidad(compraProducto.getCantidad());
                    ManejadorStock.getInstance().modificarStock(stock);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reponerStock(Compra compra) {
        try {
            List<CompraProducto> listaCompraProdcuto = ManejadorCompraProducto.getInstance().listarCompraProductos("compra.id", compra.getId());
            for (CompraProducto compraProducto : listaCompraProdcuto) {
                Stock stock = ManejadorStock.getInstance().obtenerStock(compra.getSucursal().getId(), compraProducto.getProducto().getId());
                if (stock != null) {
                    stock.agregarCantidad(compraProducto.getCantidad());
                    ManejadorStock.getInstance().modificarStock(stock);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioStock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean existeStock(Sucursal sucursal, Producto producto, Integer cantidad) {
        Stock stock = ManejadorStock.getInstance().obtenerStock(sucursal.getId(), producto.getId());
        if (stock != null) {
            if (stock.getCantidad() >= cantidad) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
