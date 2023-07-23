package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtInicioSesion;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSesion;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
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
@RequestMapping("/rest/sesion")
public class RestSesion {

    private IControladorSesion iControladorSesion;

    public RestSesion() {
        this.iControladorSesion = FabricaSesion.getIControladorSesion();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{email}/{password}")
    public DtSesion obtenerSesion(@PathVariable("email") String email, @PathVariable("password") String password) {
        return iControladorSesion.obtenerSesion(email, password);
    }

    @PostMapping("/obtenerSesionSeguro")
    public DtSesion obtenerSesionSeguro(@RequestBody DtInicioSesion dtInicioSesion) {
        return iControladorSesion.obtenerSesionSeguro(dtInicioSesion);
    }

    @PutMapping("/iniciarSesion/{email}/{password}")
    public DtMensajeSistema iniciarSesion(@PathVariable("email") String email, @PathVariable("password") String password) {
        return iControladorSesion.iniciarSesion(email, password);
    }
    
    @PostMapping("/iniciarSesionSeguro")
    public DtMensajeSistema iniciarSesionSeguro(@RequestBody DtInicioSesion dtInicioSesion) {
        return iControladorSesion.iniciarSesionSeguro(dtInicioSesion);
    }

    @DeleteMapping("/cerrarSesion/{secreto}")
    public DtMensajeSistema cerrarSesion(@PathVariable("secreto") String secreto) {
        return iControladorSesion.cerrarSesion(secreto);
    }

    @GetMapping("/validarSesion/{secreto}")
    public DtMensajeSistema validarSesion(@PathVariable("secreto") String secreto) {
        return iControladorSesion.validarSesion(secreto);
    }

}
