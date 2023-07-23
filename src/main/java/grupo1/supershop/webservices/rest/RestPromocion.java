package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtPromocion;
import grupo1.supershop.fabricas.FabricaPromocion;
import grupo1.supershop.interfaces.controladores.IControladorPromocion;
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
@RequestMapping("/rest/promocion")
public class RestPromocion {

    private IControladorPromocion iControladorPromocion;

    public RestPromocion() {
        this.iControladorPromocion = FabricaPromocion.getIControladorPromocion();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{promocionId}/{secreto}")
    public DtPromocion obtenerPromocion(@PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.obtenerPromocion(promocionId, secreto);
    }

    @GetMapping("/obtenerPromocionNombre/{promocionNombre}/{secreto}")
    public DtPromocion obtenerPromocionNombre(@PathVariable("promocionNombre") String promocionNombre, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.obtenerPromocionNombre(promocionNombre, secreto);
    }

    @GetMapping("/listarPromociones/{sucursalId}/{secreto}")
    public List<DtPromocion> listarPromociones(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.listarPromociones(sucursalId, secreto);
    }

    @GetMapping("/listarPromocionesBorradas/{sucursalId}/{secreto}")
    public List<DtPromocion> listarPromocionesBorradas(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.listarPromocionesBorradas(sucursalId, secreto);
    }

    @GetMapping("/listarPromocionesVigentes/{sucursalId}")
    public List<DtPromocion> listarPromocionesVigentes(@PathVariable("sucursalId") Long sucursalId) {
        return iControladorPromocion.listarPromocionesVigentes(sucursalId);
    }

    @GetMapping("/listarPromocionesExpiradas/{sucursalId}/{secreto}")
    public List<DtPromocion> listarPromocionesExpiradas(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.listarPromocionesExpiradas(sucursalId, secreto);
    }

    @GetMapping("/listarPromocionesProximas/{sucursalId}")
    public List<DtPromocion> listarPromocionesProximas(@PathVariable("sucursalId") Long sucursalId) {
        return iControladorPromocion.listarPromocionesProximas(sucursalId);
    }

    @GetMapping("/listarActividadPromocion/{promocionId}/{secreto}")
    public List<DtActividad> listarActividadPromocion(@PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.listarActividadPromocion(promocionId, secreto);
    }

    @PostMapping("/crear/{productoId}/{sucursalId}/{secreto}")
    public DtMensajeSistema crearPromocion(@RequestBody DtPromocion dtPromocion, @PathVariable("productoId") Long productoId, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.crearPromocion(dtPromocion, productoId, sucursalId, secreto);
    }

    @PostMapping("/modificar/{secreto}")
    public DtMensajeSistema modificarPromocion(@RequestBody DtPromocion dtPromocion, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.modificarPromocion(dtPromocion, secreto);
    }

    @DeleteMapping("/borrar/{promocionId}/{secreto}")
    public DtMensajeSistema borrarPromocion(@PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.borrarPromocion(promocionId, secreto);
    }

    @PutMapping("/restaurar/{promocionId}/{secreto}")
    public DtMensajeSistema restaurarPromocion(@PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.restaurarPromocion(promocionId, secreto);
    }

    @PostMapping("/agregarImagen/{promocionId}/{secreto}")
    public DtMensajeSistema agregarImagen(@PathVariable("promocionId") Long promocionId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.agregarImagen(promocionId, imagen, secreto);
    }

    @PutMapping("/quitarImagen/{promocionId}/{secreto}")
    public DtMensajeSistema quitarImagen(@PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.quitarImagen(promocionId, secreto);
    }

    @PostMapping("/agregarImagenRemota/{promocionId}/{secreto}")
    public DtMensajeSistema agregarImagenRemota(@RequestBody String url, @PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.agregarImagenRemota(promocionId, url, secreto);
    }

    @PostMapping("/quitarImagenRemota/{promocionId}/{secreto}")
    public DtMensajeSistema quitarImagenRemota(@RequestBody String url, @PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.quitarImagenRemota(promocionId, url, secreto);
    }

    @PostMapping("/agregarImagenCloudinary/{promocionId}/{secreto}")
    public DtMensajeSistema agregarImagenCloudinary(@PathVariable("promocionId") Long promocionId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.agregarImagenCloudinary(promocionId, imagen, secreto);
    }

    @PutMapping("/quitarImagenCloudinary/{promocionId}/{secreto}")
    public DtMensajeSistema quitarImagenCloudinary(@PathVariable("promocionId") Long promocionId, @PathVariable("secreto") String secreto) {
        return iControladorPromocion.quitarImagenCloudinary(promocionId, secreto);
    }

}
