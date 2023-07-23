package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.CarritoProducto;
import java.util.List;

public class ManejadorCarritoProducto {

    private static ManejadorCarritoProducto instance = null;

    private ManejadorCarritoProducto() {
    }

    public static ManejadorCarritoProducto getInstance() {
        if (instance == null) {
            instance = new ManejadorCarritoProducto();
        }
        return instance;
    }

    public CarritoProducto obtenerCarritoProducto(Long carritoId, Long productoId) {
        return (CarritoProducto) ManejadorBase.getInstance().obtenerBean(new CarritoProducto(), "carrito.comprador.id", carritoId, "producto.id", productoId);
    }

    public <T> List<CarritoProducto> listarCarritoProductos(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new CarritoProducto(), parametro, valor, parametro, true);
    }

    public void crearCarritoProducto(CarritoProducto carritoProducto) throws Exception {
        ManejadorBase.getInstance().crearBean(carritoProducto);
    }

    public void modificarCarritoProducto(CarritoProducto carritoProducto) throws Exception {
        ManejadorBase.getInstance().modificarBean(carritoProducto);
    }

    public void borrarCarritoProducto(CarritoProducto carritoProducto) throws Exception {
        ManejadorBase.getInstance().borrarBean(carritoProducto);
    }

}
