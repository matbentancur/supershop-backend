package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtMensajeSistema;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IControladorArchivo {
  
    public DtArchivo obtenerArchivo(Long archivoId);
    
    public DtMensajeSistema crearArchivo(MultipartFile archivoSubir, String archivoNombre, String secreto);
    
    public Resource descargarArchivo(Long archivoId);

}
