package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtMensaje;
import grupo1.supershop.fabricas.FabricaMensaje;
import grupo1.supershop.interfaces.controladores.IControladorMensaje;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/mensaje")
public class RestMensaje {

    private IControladorMensaje iControladorMensaje;

    public RestMensaje() {
        this.iControladorMensaje = FabricaMensaje.getIControladorMensaje();;
    }
    
    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{mensajeId}/{secreto}")
    public DtMensaje obtenerMensaje(@PathVariable("mensajeId") Long mensajeId, @PathVariable("secreto") String secreto) {
        return iControladorMensaje.obtenerMensaje(mensajeId, secreto);
    }

    @GetMapping("/listarMensajes/{conversacionId}/{secreto}")
    public List<DtMensaje> listarMensajes(@PathVariable("conversacionId") Long conversacionId, @PathVariable("secreto") String secreto) {
        return iControladorMensaje.listarMensajes(conversacionId, secreto);
    }

    @PostMapping("/enviar/{conversacionId}/{secreto}")
    public DtMensajeSistema enviarMensaje(@RequestBody DtMensaje dtMensaje, @PathVariable("conversacionId") Long conversacionId, @PathVariable("secreto") String secreto) {
        return iControladorMensaje.enviarMensaje(dtMensaje, conversacionId, secreto);
    }

    @DeleteMapping("/borrar/{mensajeId}/{secreto}")
    public DtMensajeSistema borrarMensaje(@PathVariable("mensajeId") Long mensajeId, @PathVariable("secreto") String secreto) {
        return iControladorMensaje.borrarMensaje(mensajeId, secreto);
    }

}
