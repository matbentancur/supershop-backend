package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.fabricas.FabricaArchivo;
import grupo1.supershop.interfaces.controladores.IControladorArchivo;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/rest/archivo")
public class RestArchivo {

    private IControladorArchivo iControladorArchivo;

    public RestArchivo() {
        this.iControladorArchivo = FabricaArchivo.getIControladorArchivo();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtener/{archivoId}")
    public DtArchivo obtenerArchivo(@PathVariable("archivoId") Long archivoId) {
        return iControladorArchivo.obtenerArchivo(archivoId);
    }

    @PostMapping("/crear/{archivoNombre}/{secreto}")
    public DtMensajeSistema crearArchivo(@RequestParam("file") MultipartFile archivoSubir, @PathVariable("archivoNombre") String archivoNombre, @PathVariable("secreto") String secreto) {
        return iControladorArchivo.crearArchivo(archivoSubir, archivoNombre, secreto);
    }

    @GetMapping("/descargar/{archivoId}")
    public ResponseEntity<Resource> descargarArchivo(@PathVariable Long archivoId) {
        Resource archivo = iControladorArchivo.descargarArchivo(archivoId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + archivo.getFilename() + "\"").body(archivo);
    }

}
