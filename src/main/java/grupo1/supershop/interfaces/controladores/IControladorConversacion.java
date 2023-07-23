package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtConversacion;
import java.util.List;

public interface IControladorConversacion {

    public DtConversacion obtenerConversacion(Long conversacionId, String secreto);

    public List<DtConversacion> listarConversacionesComprador(String secreto);

    public List<DtConversacion> listarConversacionesCompradorFinalizadas(String secreto);

    public List<DtConversacion> listarConversacionesSucursal(Long sucursalId, String secreto);
    
    public List<DtConversacion> listarConversacionesSucursalFinalizadas(Long sucursalId, String secreto);
    
    public List<DtActividad> listarActividadConversacion(Long conversacionId, String secreto);

    public DtMensajeSistema crearConversacion(Long sucursalId, String secreto);

    public DtMensajeSistema finalizarConversacion(Long conversacionId, String secreto);

}
