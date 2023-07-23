package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.fabricas.FabricaSucursal;
import grupo1.supershop.interfaces.controladores.IControladorSucursal;
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
@RequestMapping("/rest/sucursal")
public class RestSucursal {

    private IControladorSucursal iControladorSucursal;

    public RestSucursal() {
        this.iControladorSucursal = FabricaSucursal.getIControladorSucursal();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{sucursalId}")
    public DtSucursal obtenerSucursal(@PathVariable("sucursalId") Long sucursalId) {
        return iControladorSucursal.obtenerSucursal(sucursalId);
    }

    @GetMapping("/obtenerSucursalNombre/{sucursalNombre}/{secreto}")
    public DtSucursal obtenerSucursalNombre(@PathVariable("sucursalNombre") String sucursalNombre, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.obtenerSucursalNombre(sucursalNombre, secreto);
    }

    @GetMapping("/listar")
    public List<DtSucursal> listarSucursales() {
        return iControladorSucursal.listarSucursales();
    }

    @GetMapping("/listarBorradas/{secreto}")
    public List<DtSucursal> listarSucursalesBorradas(String secreto) {
        return iControladorSucursal.listarSucursalesBorradas(secreto);
    }

    @GetMapping("/listarActividadSucursal/{sucursalId}/{secreto}")
    public List<DtActividad> listarActividadSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.listarActividadSucursal(sucursalId, secreto);
    }

    @PostMapping("/crear/{secreto}")
    public DtMensajeSistema crearSucursal(@RequestBody DtSucursal dtSucursal, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.crearSucursal(dtSucursal, secreto);
    }

    @PostMapping("/modificar/{secreto}")
    public DtMensajeSistema modificarSucursal(@RequestBody DtSucursal dtSucursal, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.modificarSucursal(dtSucursal, secreto);
    }

    @DeleteMapping("/borrar/{sucursalId}/{secreto}")
    public DtMensajeSistema borrarSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.borrarSucursal(sucursalId, secreto);
    }

    @PutMapping("/restaurar/{sucursalId}/{secreto}")
    public DtMensajeSistema restaurarSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.restaurarSucursal(sucursalId, secreto);
    }

    @PostMapping("/agregarImagen/{sucursalId}/{secreto}")
    public DtMensajeSistema agregarImagen(@PathVariable("sucursalId") Long sucursalId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.agregarImagen(sucursalId, imagen, secreto);
    }

    @PutMapping("/quitarImagen/{sucursalId}/{secreto}")
    public DtMensajeSistema quitarImagen(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.quitarImagen(sucursalId, secreto);
    }

    @PostMapping("/agregarImagenRemota/{sucursalId}/{secreto}")
    public DtMensajeSistema agregarImagenRemota(@RequestBody String url, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.agregarImagenRemota(sucursalId, url, secreto);
    }

    @PostMapping("/quitarImagenRemota/{sucursalId}/{secreto}")
    public DtMensajeSistema quitarImagenRemota(@RequestBody String url, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.quitarImagenRemota(sucursalId, url, secreto);
    }

    @PostMapping("/agregarImagenCloudinary/{sucursalId}/{secreto}")
    public DtMensajeSistema agregarImagenCloudinary(@PathVariable("sucursalId") Long sucursalId, @RequestParam("file") MultipartFile imagen, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.agregarImagenCloudinary(sucursalId, imagen, secreto);
    }

    @PutMapping("/quitarImagenCloudinary/{sucursalId}/{secreto}")
    public DtMensajeSistema quitarImagenCloudinary(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorSucursal.quitarImagenCloudinary(sucursalId, secreto);
    }

}
