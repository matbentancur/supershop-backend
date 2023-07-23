package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.datatypes.DtMensajeSistema;
import java.util.List;

public interface IControladorDomicilio {

    public DtDomicilio obtenerDomicilio(Long domicilioId, String secreto);
       
    public List<DtDomicilio> listarDomiciliosComprador(String secreto);
    
    public List<DtDomicilio> listarDomiciliosCompradorBorrados(String secreto);
    
    public DtMensajeSistema crearDomicilio(DtDomicilio dtDomicilio, String secreto);
    
    public DtMensajeSistema modificarDomicilio(DtDomicilio dtDomicilio, String secreto);
    
    public DtMensajeSistema borrarDomicilio(Long domicilioId, String secreto);
    
    public DtMensajeSistema restaurarDomicilio(Long domicilioId, String secreto);

}
