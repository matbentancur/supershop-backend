package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.CompraProducto;
import java.util.List;

public class ManejadorCompraProducto {

    private static ManejadorCompraProducto instance = null;

    private ManejadorCompraProducto() {
    }

    public static ManejadorCompraProducto getInstance() {
        if (instance == null) {
            instance = new ManejadorCompraProducto();
        }
        return instance;
    }

    public CompraProducto obtenerCompraProducto(Long compraId, Long productoId) {
        return (CompraProducto) ManejadorBase.getInstance().obtenerBean(new CompraProducto(), "compra.id", compraId, "producto.id", productoId);
    }
    
    public <T> List<CompraProducto> listarCompraProductos(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new CompraProducto(), parametro, valor, parametro, true);
    }
    
    public void crearCompraProducto(CompraProducto compraProducto) throws Exception {
        ManejadorBase.getInstance().crearBean(compraProducto);
    }

    public void modificarCompraProducto(CompraProducto compraProducto) throws Exception {
        ManejadorBase.getInstance().modificarBean(compraProducto);
    }

    public void borrarCompraProducto(CompraProducto compraProducto) throws Exception {
        ManejadorBase.getInstance().borrarBean(compraProducto);
    }

}
