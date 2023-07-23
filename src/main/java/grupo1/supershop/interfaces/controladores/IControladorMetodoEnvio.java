package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import java.util.List;

public interface IControladorMetodoEnvio {

    public DtMetodoEnvio obtenerMetodoEnvio(Long metodoEnvioId, String secreto);
       
    public List<DtMetodoEnvio> listarMetodosEnvio();
    
    public List<DtMetodoEnvio> listarMetodosEnvioBorrados(String secreto);
    
    public List<DtActividad> listarActividadMetodoEnvio(Long metodoEnvioId, String secreto);
    
    public DtMensajeSistema crearMetodoEnvio(DtMetodoEnvio dtMetodoEnvio, String secreto);
    
    public DtMensajeSistema modificarMetodoEnvio(DtMetodoEnvio dtMetodoEnvio, String secreto);
    
    public DtMensajeSistema borrarMetodoEnvio(Long metodoEnvioId, String secreto);
    
    public DtMensajeSistema restaurarMetodoEnvio(Long metodoEnvioId, String secreto);

}
