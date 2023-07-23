package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtConversacion;
import grupo1.supershop.fabricas.FabricaConversacion;
import grupo1.supershop.interfaces.controladores.IControladorConversacion;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/conversacion")
public class RestConversacion {

    private IControladorConversacion iControladorConversacion;

    public RestConversacion() {
        this.iControladorConversacion = FabricaConversacion.getIControladorConversacion();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{conversacionId}/{secreto}")
    public DtConversacion obtenerConversacion(@PathVariable("conversacionId") Long conversacionId, @PathVariable("secreto") String secreto) {
        return iControladorConversacion.obtenerConversacion(conversacionId, secreto);
    }

    @GetMapping("/listarConversacionesComprador/{secreto}")
    public List<DtConversacion> listarConversacionesComprador(@PathVariable("secreto") String secreto) {
        return iControladorConversacion.listarConversacionesComprador(secreto);
    }

    @GetMapping("/listarConversacionesCompradorFinalizadas/{secreto}")
    public List<DtConversacion> listarConversacionesCompradorFinalizadas(@PathVariable("secreto") String secreto) {
        return iControladorConversacion.listarConversacionesCompradorFinalizadas(secreto);
    }

    @GetMapping("/listarConversacionesSucursal/{sucursalId}/{secreto}")
    public List<DtConversacion> listarConversacionesSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorConversacion.listarConversacionesSucursal(sucursalId, secreto);
    }

    @GetMapping("/listarConversacionesSucursalFinalizadas/{sucursalId}/{secreto}")
    public List<DtConversacion> listarConversacionesSucursalFinalizadas(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorConversacion.listarConversacionesSucursalFinalizadas(sucursalId, secreto);
    }

    @GetMapping("/listarActividadConversacion/{conversacionId}/{secreto}")
    public List<DtActividad> listarActividadConversacion(@PathVariable("conversacionId") Long conversacionId, @PathVariable("secreto") String secreto) {
        return iControladorConversacion.listarActividadConversacion(conversacionId, secreto);
    }

    @PutMapping("/crear/{sucursalId}/{secreto}")
    public DtMensajeSistema crearConversacion(Long compradorId, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorConversacion.crearConversacion(sucursalId, secreto);
    }

    @PutMapping("/finalizar/{conversacionId}/{secreto}")
    public DtMensajeSistema finalizarConversacion(@PathVariable("conversacionId") Long conversacionId, @PathVariable("secreto") String secreto) {
        return iControladorConversacion.finalizarConversacion(conversacionId, secreto);
    }

}
