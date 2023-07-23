package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.fabricas.FabricaVale;
import grupo1.supershop.interfaces.controladores.IControladorVale;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/vale")
public class RestVale {

    private IControladorVale iControladorVale;

    public RestVale() {
        this.iControladorVale = FabricaVale.getIControladorVale();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{valeId}/{secreto}")
    public DtVale obtenerVale(@PathVariable("valeId") Long valeId, @PathVariable("secreto") String secreto) {
        return iControladorVale.obtenerVale(valeId, secreto);
    }

    @GetMapping("/listarValesDisponibles/{secreto}")
    public List<DtVale> listarValesDisponibles(@PathVariable("secreto") String secreto) {
        return iControladorVale.listarValesDisponibles(secreto);
    }

    @GetMapping("/listarVales/{compradorId}/{secreto}")
    public List<DtVale> listarVales(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorVale.listarVales(compradorId, secreto);
    }

    @GetMapping("/listarValesBorrados/{compradorId}/{secreto}")
    public List<DtVale> listarValesBorrados(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorVale.listarValesBorrados(compradorId, secreto);
    }

    @GetMapping("/listarActividadVale/{valeId}/{secreto}")
    public List<DtActividad> listarActividadVale(@PathVariable("valeId") Long valeId, @PathVariable("secreto") String secreto) {
        return iControladorVale.listarActividadVale(valeId, secreto);
    }

    @PostMapping("/crear/{compradorId}/{sucursalId}/{secreto}")
    public DtMensajeSistema crearVale(@RequestBody DtVale dtVale, @PathVariable("compradorId") Long compradorId, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorVale.crearVale(dtVale, compradorId, sucursalId, secreto);
    }

    @PostMapping("/modificar/{secreto}")
    public DtMensajeSistema modificarVale(@RequestBody DtVale dtVale, @PathVariable("secreto") String secreto) {
        return iControladorVale.modificarVale(dtVale, secreto);
    }

    @DeleteMapping("/borrar/{valeId}/{secreto}")
    public DtMensajeSistema borrarVale(@PathVariable("valeId") Long valeId, @PathVariable("secreto") String secreto) {
        return iControladorVale.borrarVale(valeId, secreto);
    }

    @PutMapping("/restaurar/{valeId}/{secreto}")
    public DtMensajeSistema restaurarVale(@PathVariable("valeId") Long valeId, @PathVariable("secreto") String secreto) {
        return iControladorVale.restaurarVale(valeId, secreto);
    }

}
