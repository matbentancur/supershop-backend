package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtCategoria;
import grupo1.supershop.fabricas.FabricaCategoria;
import grupo1.supershop.interfaces.controladores.IControladorCategoria;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/rest/categoria")
public class RestCategoria {

    private IControladorCategoria iControladorCategoria;

    public RestCategoria() {
        this.iControladorCategoria = FabricaCategoria.getIControladorCategoria();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{categoriaId}/{secreto}")
    public DtCategoria obtenerCategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.obtenerCategoria(categoriaId, secreto);
    }

    @GetMapping("/obtenerCategoriaNombre/{categoriaNombre}/{secreto}")
    public DtCategoria obtenerCategoriaNombre(@PathVariable("categoriaNombre") String categoriaNombre, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.obtenerCategoriaNombre(categoriaNombre, secreto);
    }

    @GetMapping("/listar")
    public List<DtCategoria> listarCategorias() {
        return iControladorCategoria.listarCategorias();
    }

    @GetMapping("/listarBorradas/{secreto}")
    public List<DtCategoria> listarCategoriasBorradas(@PathVariable("secreto") String secreto) {
        return iControladorCategoria.listarCategoriasBorradas(secreto);
    }

    @GetMapping("/listarActividadCategoria/{categoriaId}/{secreto}")
    public List<DtActividad> listarActividadCategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.listarActividadCategoria(categoriaId, secreto);
    }

    @PostMapping("/crear/{secreto}")
    public DtMensajeSistema crearCategoria(@RequestBody DtCategoria dtCategoria, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.crearCategoria(dtCategoria, secreto);
    }

    @PostMapping("/modificar/{secreto}")
    public DtMensajeSistema modificarCategoria(@RequestBody DtCategoria dtCategoria, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.modificarCategoria(dtCategoria, secreto);
    }

    @DeleteMapping("/borrar/{categoriaId}/{secreto}")
    public DtMensajeSistema borrarCategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.borrarCategoria(categoriaId, secreto);
    }

    @PutMapping("/restaurar/{categoriaId}/{secreto}")
    public DtMensajeSistema restaurarCategoria(@PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.restaurarCategoria(categoriaId, secreto);
    }

    @PostMapping("/agregarImagen/{categoriaId}/{secreto}")
    public DtMensajeSistema agregarImagen(@PathVariable("categoriaId") Long categoriaId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.agregarImagen(categoriaId, imagen, secreto);
    }

    @PutMapping("/quitarImagen/{categoriaId}/{secreto}")
    public DtMensajeSistema quitarImagen(@PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.quitarImagen(categoriaId, secreto);
    }

    @PostMapping("/agregarImagenRemota/{categoriaId}/{secreto}")
    public DtMensajeSistema agregarImagenRemota(@RequestBody String url, @PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.agregarImagenRemota(categoriaId, url, secreto);
    }

    @PostMapping("/quitarImagenRemota/{categoriaId}/{secreto}")
    public DtMensajeSistema quitarImagenRemota(@RequestBody String url, @PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.quitarImagenRemota(categoriaId, url, secreto);
    }

    @PostMapping("/agregarImagenCloudinary/{categoriaId}/{secreto}")
    public DtMensajeSistema agregarImagenCloudinary(@PathVariable("categoriaId") Long categoriaId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.agregarImagenCloudinary(categoriaId, imagen, secreto);
    }

    @PutMapping("/quitarImagenCloudinary/{categoriaId}/{secreto}")
    public DtMensajeSistema quitarImagenCloudinary(@PathVariable("categoriaId") Long categoriaId, @PathVariable("secreto") String secreto) {
        return iControladorCategoria.quitarImagenCloudinary(categoriaId, secreto);
    }

}
