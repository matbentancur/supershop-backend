package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.fabricas.FabricaDomicilio;
import grupo1.supershop.interfaces.controladores.IControladorDomicilio;
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
@RequestMapping("/rest/domicilio")
public class RestDomicilio {

    private IControladorDomicilio iControladorDomicilio;

    public RestDomicilio() {
        this.iControladorDomicilio = FabricaDomicilio.getIControladorDomicilio();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{domicilioId}/{secreto}")
    public DtDomicilio obtenerDomicilio(@PathVariable("domicilioId") Long domicilioId, @PathVariable("secreto") String secreto) {
        return iControladorDomicilio.obtenerDomicilio(domicilioId, secreto);
    }

    @GetMapping("/listarDomiciliosComprador/{secreto}")
    public List<DtDomicilio> listarDomiciliosComprador(@PathVariable("secreto") String secreto) {
        return iControladorDomicilio.listarDomiciliosComprador(secreto);
    }

    @GetMapping("/listarDomiciliosCompradorBorrados/{secreto}")
    public List<DtDomicilio> listarDomiciliosCompradorBorrados(@PathVariable("secreto") String secreto) {
        return iControladorDomicilio.listarDomiciliosCompradorBorrados(secreto);
    }

    @PostMapping("/crear/{secreto}")
    public DtMensajeSistema crearDomicilio(@RequestBody DtDomicilio dtDomicilio, @PathVariable("secreto") String secreto) {
        return iControladorDomicilio.crearDomicilio(dtDomicilio, secreto);
    }

    @PostMapping("/modificar/{secreto}")
    public DtMensajeSistema modificarDomicilio(@RequestBody DtDomicilio dtDomicilio, @PathVariable("secreto") String secreto) {
        return iControladorDomicilio.modificarDomicilio(dtDomicilio, secreto);
    }

    @DeleteMapping("/borrar/{domicilioId}/{secreto}")
    public DtMensajeSistema borrarDomicilio(@PathVariable("domicilioId") Long domicilioId, @PathVariable("secreto") String secreto) {
        return iControladorDomicilio.borrarDomicilio(domicilioId, secreto);
    }

    @PutMapping("/restaurar/{domicilioId}/{secreto}")
    public DtMensajeSistema restaurarDomicilio(@PathVariable("domicilioId") Long domicilioId, @PathVariable("secreto") String secreto) {
        return iControladorDomicilio.restaurarDomicilio(domicilioId, secreto);
    }

}
