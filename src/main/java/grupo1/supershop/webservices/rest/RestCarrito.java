package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtCarrito;
import grupo1.supershop.datatypes.DtCarritoProducto;
import grupo1.supershop.fabricas.FabricaCarrito;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/carrito")
public class RestCarrito {

    private IControladorCarrito iControladorCarrito;

    public RestCarrito() {
        this.iControladorCarrito = FabricaCarrito.getIControladorCarrito();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{secreto}")
    public DtCarrito obtenerCarrito(@PathVariable("secreto") String secreto) {
        return iControladorCarrito.obtenerCarrito(secreto);
    }

    @GetMapping("/listarCarritoProductos/{secreto}")
    public List<DtCarritoProducto> listarCarritoProductos(@PathVariable("secreto") String secreto) {
        return iControladorCarrito.listarCarritoProductos(secreto);
    }

    @PutMapping("/crear/{sucursalId}/{productoId}/{cantidad}/{secreto}")
    public DtMensajeSistema crearCarrito(
            @PathVariable("sucursalId") Long sucursalId,
            @PathVariable("productoId") Long productoId,
            @PathVariable("cantidad") Integer cantidad,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorCarrito.crearCarrito(sucursalId, productoId, cantidad, secreto);
    }

    @DeleteMapping("/borrar/{secreto}")
    public DtMensajeSistema borrarCarrito(@PathVariable("secreto") String secreto) {
        return iControladorCarrito.borrarCarrito(secreto);
    }

    @PutMapping("/agregar/{productoId}/{cantidad}/{secreto}")
    public DtMensajeSistema agregarProducto(
            @PathVariable("productoId") Long productoId,
            @PathVariable("cantidad") Integer cantidad,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorCarrito.agregarProducto(productoId, cantidad, secreto);
    }

    @PutMapping("/quitar/{productoId}/{cantidad}/{secreto}")
    public DtMensajeSistema quitarProducto(
            @PathVariable("productoId") Long productoId,
            @PathVariable("cantidad") Integer cantidad,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorCarrito.quitarProducto(productoId, cantidad, secreto);
    }

    @PutMapping("/modificar/{productoId}/{cantidad}/{secreto}")
    public DtMensajeSistema modificarCantidadProducto(
            @PathVariable("productoId") Long productoId,
            @PathVariable("cantidad") Integer cantidad,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorCarrito.modificarCantidadProducto(productoId, cantidad, secreto);
    }

    @PutMapping("/borrarCarritoProducto/{productoId}/{secreto}")
    public DtMensajeSistema borrarCarritoProducto(
            @PathVariable("productoId") Long productoId,
            @PathVariable("secreto") String secreto
    ) {
        return iControladorCarrito.borrarCarritoProducto(productoId, secreto);
    }

}
