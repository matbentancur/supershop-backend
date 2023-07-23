package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Stock;
import java.util.List;

public class ManejadorStock {

    private static ManejadorStock instance = null;

    private ManejadorStock() {
    }

    public static ManejadorStock getInstance() {
        if (instance == null) {
            instance = new ManejadorStock();
        }
        return instance;
    }

    public Stock obtenerStock(Long sucursalId, Long productoId) {
        return (Stock) ManejadorBase.getInstance().obtenerBean(new Stock(), "sucursal.id", sucursalId, "producto.id", productoId);
    }
    
    public <T> List<Stock> listarStock(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBeanNotOrdered(new Stock(), parametro, valor);
    }
    
    public <T,S> List<Stock> listarStock(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Stock(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public void crearStock(Stock stock) throws Exception {
        ManejadorBase.getInstance().crearBean(stock);
    }

    public void modificarStock(Stock stock) throws Exception {
        ManejadorBase.getInstance().modificarBean(stock);
    }

}
