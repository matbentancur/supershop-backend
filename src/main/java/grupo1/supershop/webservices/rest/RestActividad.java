package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.fabricas.FabricaActividad;
import grupo1.supershop.interfaces.controladores.IControladorActividad;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/actividad")
public class RestActividad {

    private IControladorActividad iControladorActividad;

    public RestActividad() {
        this.iControladorActividad = FabricaActividad.getIControladorActividad();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{actividadId}/{secreto}")
    public DtActividad obtenerActividad(@PathVariable("actividadId") Long actividadId, @PathVariable("secreto") String secreto) {
        return iControladorActividad.obtenerActividad(actividadId, secreto);
    }

    @GetMapping("/listarActividadesTemporal/{inicia}/{finaliza}/{secreto}")
    public List<DtActividad> listarActividadesTemporal(
            @PathVariable("inicia")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date inicia,
            @PathVariable("finaliza")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date finaliza,
            @PathVariable("secreto") String secreto) {
        return iControladorActividad.listarActividadesTemporal(inicia, finaliza, secreto);
    }

    @GetMapping("/listarActividadesUsuario/{usuarioId}/{secreto}")
    public List<DtActividad> listarActividadesUsuario(@PathVariable("usuarioId") Long usuarioId, @PathVariable("secreto") String secreto) {
        return iControladorActividad.listarActividadesUsuario(usuarioId, secreto);
    }

}
