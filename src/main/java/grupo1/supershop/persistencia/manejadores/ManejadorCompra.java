package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.CompraProducto;
import java.util.List;

public class ManejadorCompra {

    private static ManejadorCompra instance = null;

    private ManejadorCompra() {
    }

    public static ManejadorCompra getInstance() {
        if (instance == null) {
            instance = new ManejadorCompra();
        }
        return instance;
    }

    public Compra obtenerCompra(Long id) {
        return (Compra) ManejadorBase.getInstance().obtenerBean(new Compra(), "id", id);
    }

    public <T> Compra obtenerCompra(String parametro, T valor) {
        return (Compra) ManejadorBase.getInstance().obtenerBean(new Compra(), parametro, valor);
    }

    public <T, S> Compra obtenerCompra(String parametro1, T valor1, String parametro2, S valor2) {
        return (Compra) ManejadorBase.getInstance().obtenerBean(new Compra(), parametro1, valor1, parametro2, valor2);
    }

    public <T> List<Compra> listarCompras(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Compra(), parametro, valor, parametro, true);
    }

    public <T, S> List<Compra> listarCompras(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Compra(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public void crearCompra(Compra compra) throws Exception {
        ManejadorBase.getInstance().crearBean(compra);
    }

    public void modificarCompra(Compra compra) throws Exception {
        ManejadorBase.getInstance().modificarBean(compra);
    }

    public void borrarCompra(Compra compra) throws Exception {
        ManejadorBase.getInstance().borrarBean(compra);
    }
    
    public void vaciarCompra(Compra compra) throws Exception {
        List<CompraProducto> listaCompraProducto = ManejadorCompraProducto.getInstance().listarCompraProductos("compra.id", compra.getId());
        for (CompraProducto compraProducto : listaCompraProducto) {
            ManejadorBase.getInstance().borrarBean(compraProducto);
        }
    }

}
