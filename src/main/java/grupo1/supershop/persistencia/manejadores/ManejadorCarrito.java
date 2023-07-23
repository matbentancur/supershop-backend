package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Carrito;
import grupo1.supershop.beans.CarritoProducto;
import java.util.List;

public class ManejadorCarrito {

    private static ManejadorCarrito instance = null;

    private ManejadorCarrito() {
    }

    public static ManejadorCarrito getInstance() {
        if (instance == null) {
            instance = new ManejadorCarrito();
        }
        return instance;
    }

    public Carrito obtenerCarrito(Long compradorId) {
        return (Carrito) ManejadorBase.getInstance().obtenerBean(new Carrito(), "comprador.id", compradorId);
    }

    public void crearCarrito(Carrito carrito) throws Exception {
        ManejadorBase.getInstance().crearBean(carrito);
    }

    public void modificarCarrito(Carrito carrito) throws Exception {
        ManejadorBase.getInstance().modificarBean(carrito);
    }

    public void borrarCarrito(Carrito carrito) throws Exception {
        ManejadorBase.getInstance().borrarBean(carrito);
    }

    public void borrarProductosCarrito(List<CarritoProducto> listaCarritoProductos) throws Exception {
        for (CarritoProducto carritoProducto : listaCarritoProductos) {
            ManejadorBase.getInstance().borrarBean(carritoProducto);
        }
    }

    public void vaciarCarrito(Carrito carrito) throws Exception {
        List<CarritoProducto> listaCarritoProducto = ManejadorCarritoProducto.getInstance().listarCarritoProductos("carrito.comprador.id", carrito.getComprador().getId());
        for (CarritoProducto carritoProducto : listaCarritoProducto) {
            ManejadorBase.getInstance().borrarBean(carritoProducto);
        }
    }

}
