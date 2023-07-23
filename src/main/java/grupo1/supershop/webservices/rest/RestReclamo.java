package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtReclamo;
import grupo1.supershop.enums.EstadoReclamo;
import grupo1.supershop.fabricas.FabricaReclamo;
import grupo1.supershop.interfaces.controladores.IControladorReclamo;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/reclamo")
public class RestReclamo {

    private IControladorReclamo iControladorReclamo;

    public RestReclamo() {
        this.iControladorReclamo = FabricaReclamo.getIControladorReclamo();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{reclamoId}/{secreto}")
    public DtReclamo obtenerReclamo(@PathVariable("reclamoId") Long reclamoId, @PathVariable("secreto") String secreto) {
        return iControladorReclamo.obtenerReclamo(reclamoId, secreto);
    }

    @GetMapping("/listarReclamos/{secreto}")
    public List<DtReclamo> listarReclamos(@PathVariable("secreto") String secreto) {
        return iControladorReclamo.listarReclamos(secreto);
    }

    @GetMapping("/listarReclamosComprador/{estadoReclamo}/{secreto}")
    public List<DtReclamo> listarReclamosComprador(@PathVariable("estadoReclamo") EstadoReclamo estadoReclamo, @PathVariable("secreto") String secreto) {
        return iControladorReclamo.listarReclamosComprador(estadoReclamo, secreto);
    }

    @GetMapping("/listarReclamosCompradorTodos/{secreto}")
    public List<DtReclamo> listarReclamosCompradorTodos(@PathVariable("secreto") String secreto) {
        return iControladorReclamo.listarReclamosCompradorTodos(secreto);
    }

    @GetMapping("/listarReclamosSucursal/{estadoReclamo}/{secreto}")
    public List<DtReclamo> listarReclamosSucursal(@PathVariable("estadoReclamo") EstadoReclamo estadoReclamo, @PathVariable("secreto") String secreto) {
        return iControladorReclamo.listarReclamosSucursal(estadoReclamo, secreto);
    }

    @GetMapping("/listarActividadReclamo/{reclamoId}/{secreto}")
    public List<DtActividad> listarActividadReclamo(@PathVariable("reclamoId") Long reclamoId, @PathVariable("secreto") String secreto) {
        return iControladorReclamo.listarActividadReclamo(reclamoId, secreto);
    }

    @PostMapping("/crear/{sucursalId}/{secreto}")
    public DtMensajeSistema crearReclamo(@RequestBody DtReclamo dtReclamo, @PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorReclamo.crearReclamo(dtReclamo, sucursalId, secreto);
    }

    @PutMapping("/confirmar/{reclamoId}/{secreto}")
    public DtMensajeSistema confirmarReclamo(@PathVariable("reclamoId") Long reclamoId, @PathVariable("secreto") String secreto) {
        return iControladorReclamo.confirmarReclamo(reclamoId, secreto);
    }

    @PostMapping("/concluir/{secreto}")
    public DtMensajeSistema concluirReclamo(@RequestBody DtReclamo dtReclamo, @PathVariable("secreto") String secreto) {
        return iControladorReclamo.concluirReclamo(dtReclamo, secreto);
    }

}
