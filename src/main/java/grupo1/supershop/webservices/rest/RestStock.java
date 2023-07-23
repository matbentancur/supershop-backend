package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtStock;
import grupo1.supershop.fabricas.FabricaStock;
import grupo1.supershop.interfaces.controladores.IControladorStock;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/stock")
public class RestStock {

    private IControladorStock iControladorStock;

    public RestStock() {
        this.iControladorStock = FabricaStock.getIControladorStock();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{sucursalId}/{productoId}/{secreto}")
    public DtStock obtenerStock(@PathVariable("sucursalId") Long sucursalId, @PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorStock.obtenerStock(sucursalId, productoId, secreto);
    }

    @GetMapping("/listarStockSucursal/{sucursalId}/{secreto}")
    public List<DtStock> listarStockSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorStock.listarStockSucursal(sucursalId, secreto);
    }

    @GetMapping("/listarStockProducto/{productoId}/{secreto}")
    public List<DtStock> listarStockProducto(@PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorStock.listarStockProducto(productoId, secreto);
    }

    @GetMapping("/listarSinStock/{secreto}")
    public List<DtStock> listarSinStock(@PathVariable("secreto") String secreto) {
        return iControladorStock.listarSinStock(secreto);
    }

    @GetMapping("/listarSinStockSucursal/{sucursalId}/{secreto}")
    public List<DtStock> listarSinStockSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorStock.listarSinStockSucursal(sucursalId, secreto);
    }

    @GetMapping("/listarSinStockProducto/{productoId}/{secreto}")
    public List<DtStock> listarSinStockProducto(@PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorStock.listarSinStockProducto(productoId, secreto);
    }

    @PutMapping("/agregar/{sucursalId}/{productoId}/{cantidad}/{secreto}")
    public DtMensajeSistema agregarStock(
            @PathVariable("sucursalId") Long sucursalId,
            @PathVariable("productoId") Long productoId,
            @PathVariable("cantidad") Integer cantidad,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorStock.agregarStock(sucursalId, productoId, cantidad, secreto);
    }

    @PutMapping("/quitar/{sucursalId}/{productoId}/{cantidad}/{secreto}")
    public DtMensajeSistema quitarStock(
            @PathVariable("sucursalId") Long sucursalId,
            @PathVariable("productoId") Long productoId,
            @PathVariable("cantidad") Integer cantidad,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorStock.quitarStock(sucursalId, productoId, cantidad, secreto);
    }

    @PutMapping("/modificar/{sucursalId}/{productoId}/{cantidad}/{secreto}")
    public DtMensajeSistema modificarStock(
            @PathVariable("sucursalId") Long sucursalId,
            @PathVariable("productoId") Long productoId,
            @PathVariable("cantidad") Integer cantidad,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorStock.modificarStock(sucursalId, productoId, cantidad, secreto);
    }

}
