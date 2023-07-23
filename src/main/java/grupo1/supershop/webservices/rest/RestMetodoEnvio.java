package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import grupo1.supershop.fabricas.FabricaMetodoEnvio;
import grupo1.supershop.interfaces.controladores.IControladorMetodoEnvio;
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
@RequestMapping("/rest/metodoEnvio")
public class RestMetodoEnvio {

    private IControladorMetodoEnvio iControladorMetodoEnvio;

    public RestMetodoEnvio() {
        this.iControladorMetodoEnvio = FabricaMetodoEnvio.getIControladorMetodoEnvio();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{metodoEnvioId}/{secreto}")
    public DtMetodoEnvio obtenerMetodoEnvio(@PathVariable("metodoEnvioId") Long metodoEnvioId, @PathVariable("secreto") String secreto) {
        return iControladorMetodoEnvio.obtenerMetodoEnvio(metodoEnvioId, secreto);
    }

    @GetMapping("/listar")
    public List<DtMetodoEnvio> listarMetodosEnvio() {
        return iControladorMetodoEnvio.listarMetodosEnvio();
    }

    @GetMapping("/listarBorradas/{secreto}")
    public List<DtMetodoEnvio> listarMetodosEnvioBorrados(@PathVariable("secreto") String secreto) {
        return iControladorMetodoEnvio.listarMetodosEnvioBorrados(secreto);
    }

    @GetMapping("/listarActividadMetodoEnvio/{metodoEnvioId}/{secreto}")
    public List<DtActividad> listarActividadMetodoEnvio(@PathVariable("metodoEnvioId") Long metodoEnvioId, @PathVariable("secreto") String secreto) {
        return iControladorMetodoEnvio.listarActividadMetodoEnvio(metodoEnvioId, secreto);
    }

    @PostMapping("/crear/{secreto}")
    public DtMensajeSistema crearMetodoEnvio(@RequestBody DtMetodoEnvio dtMetodoEnvio, @PathVariable("secreto") String secreto) {
        return iControladorMetodoEnvio.crearMetodoEnvio(dtMetodoEnvio, secreto);
    }

    @PostMapping("/modificar/{secreto}")
    public DtMensajeSistema modificarMetodoEnvio(@RequestBody DtMetodoEnvio dtMetodoEnvio, @PathVariable("secreto") String secreto) {
        return iControladorMetodoEnvio.modificarMetodoEnvio(dtMetodoEnvio, secreto);
    }

    @DeleteMapping("/borrar/{metodoEnvioId}/{secreto}")
    public DtMensajeSistema borrarMetodoEnvio(@PathVariable("metodoEnvioId") Long metodoEnvioId, @PathVariable("secreto") String secreto) {
        return iControladorMetodoEnvio.borrarMetodoEnvio(metodoEnvioId, secreto);
    }

    @PutMapping("/restaurar/{metodoEnvioId}/{secreto}")
    public DtMensajeSistema restaurarMetodoEnvio(@PathVariable("metodoEnvioId") Long metodoEnvioId, @PathVariable("secreto") String secreto) {
        return iControladorMetodoEnvio.restaurarMetodoEnvio(metodoEnvioId, secreto);
    }

}
