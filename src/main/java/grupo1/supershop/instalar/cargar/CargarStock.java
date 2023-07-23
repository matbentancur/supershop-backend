package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Stock;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorStock;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import lombok.NoArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

@NoArgsConstructor
public class CargarStock {

    public void cargarStock() throws Exception {
        List<Producto> productos = ManejadorProducto.getInstance().listarProductos();
        List<Sucursal> sucursales = ManejadorSucursal.getInstance().listarSucursales();

        for (Sucursal sucursal : sucursales) {
            for (Producto producto : productos) {
                    Stock stock = new Stock();
                    stock.setProducto(producto);
                    stock.setSucursal(sucursal);
                    stock.setCantidad(2000);

                    ManejadorStock.getInstance().crearStock(stock);
            }
        }
    }

    private int generarNumeroAleatorio() throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        return random.nextInt(101) + 200;
    }
}
