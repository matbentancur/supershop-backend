package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtProducto;
import grupo1.supershop.fabricas.FabricaProducto;
import grupo1.supershop.interfaces.controladores.IControladorProducto;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/rest/producto")
public class RestProducto {

    private IControladorProducto iControladorProducto;

    public RestProducto() {
        this.iControladorProducto = FabricaProducto.getIControladorProducto();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{productoId}")
    public DtProducto obtenerProducto(@PathVariable("productoId") Long productoId) {
        return iControladorProducto.obtenerProducto(productoId);
    }

    @GetMapping("/obtenerProductoNombre/{productoId}/{secreto}")
    public DtProducto obtenerProductoNombre(@PathVariable("productoId") String productoNombre, @PathVariable("secreto") String secreto) {
        return iControladorProducto.obtenerProductoNombre(productoNombre, secreto);
    }

    @GetMapping("/obtenerProductoBarcode/{productoBarcode}")
    public DtProducto obtenerProductoBarcode(@PathVariable("productoBarcode") String productoBarcode) {
        return iControladorProducto.obtenerProductoBarcode(productoBarcode);
    }

    @GetMapping("/listarProductosDisponibles/{sucursalId}")
    public List<DtProducto> listarProductosDisponibles(@PathVariable("sucursalId") Long sucursalId) {
        return iControladorProducto.listarProductosDisponibles(sucursalId);
    }

    @GetMapping("/listar")
    public List<DtProducto> listarProductos() {
        return iControladorProducto.listarProductos();
    }

    @GetMapping("/listarBorradas/{secreto}")
    public List<DtProducto> listarProductosBorrados(@PathVariable("secreto") String secreto) {
        return iControladorProducto.listarProductosBorrados(secreto);
    }

    @GetMapping("/listarProductosCategoria/{categoriaId}")
    public List<DtProducto> listarProductosCategoria(@PathVariable("categoriaId") Long categoriaId) {
        return iControladorProducto.listarProductosCategoria(categoriaId);
    }

    @GetMapping("/listarActividadProducto/{productoId}/{secreto}")
    public List<DtActividad> listarActividadProducto(@PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorProducto.listarActividadProducto(productoId, secreto);
    }

    @GetMapping("/buscarProducto/{texto}")
    public List<DtProducto> buscarProducto(@PathVariable("texto") String texto) {
        return iControladorProducto.buscarProducto(texto);
    }

    @PostMapping("/crear/{secreto}")
    public DtMensajeSistema crearProducto(@RequestBody DtProducto dtProducto, @PathVariable("secreto") String secreto) {
        return iControladorProducto.crearProducto(dtProducto, secreto);
    }

    @PostMapping("/modificar/{secreto}")
    public DtMensajeSistema modificarProducto(@RequestBody DtProducto dtProducto, @PathVariable("secreto") String secreto) {
        return iControladorProducto.modificarProducto(dtProducto, secreto);
    }

    @DeleteMapping("/borrar/{productoId}/{secreto}")
    public DtMensajeSistema borrarProducto(@PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorProducto.borrarProducto(productoId, secreto);
    }

    @PutMapping("/restaurar/{productoId}/{secreto}")
    public DtMensajeSistema restaurarProducto(@PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorProducto.restaurarProducto(productoId, secreto);
    }

    @PostMapping("/agregarImagen/{productoId}/{secreto}")
    public DtMensajeSistema agregarImagen(@PathVariable("productoId") Long productoId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorProducto.agregarImagen(productoId, imagen, secreto);
    }

    @PutMapping("/quitarImagen/{productoId}/{archivoId}/{secreto}")
    public DtMensajeSistema quitarImagen(@PathVariable("productoId") Long productoId, @PathVariable("archivoId") Long archivoId, @PathVariable("secreto") String secreto) {
        return iControladorProducto.quitarImagen(productoId, archivoId, secreto);
    }

    @PostMapping("/agregarImagenRemota/{productoId}/{secreto}")
    public DtMensajeSistema agregarImagenRemota(@RequestBody String url, @PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorProducto.agregarImagenRemota(productoId, url, secreto);
    }

    @PostMapping("/quitarImagenRemota/{productoId}/{secreto}")
    public DtMensajeSistema quitarImagenRemota(@RequestBody String url, @PathVariable("productoId") Long productoId, @PathVariable("secreto") String secreto) {
        return iControladorProducto.quitarImagenRemota(productoId, url, secreto);
    }

    @PostMapping("/agregarImagenCloudinary/{productoId}/{secreto}")
    public DtMensajeSistema agregarImagenCloudinary(@PathVariable("productoId") Long productoId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorProducto.agregarImagenCloudinary(productoId, imagen, secreto);
    }

    @PutMapping("/quitarImagenCloudinary/{productoId}/{archivoId}/{secreto}")
    public DtMensajeSistema quitarImagenCloudinary(@PathVariable("productoId") Long productoId, @PathVariable("archivoId") Long archivoId, @PathVariable("secreto") String secreto) {
        return iControladorProducto.quitarImagenCloudinary(productoId, archivoId, secreto);
    }

}
