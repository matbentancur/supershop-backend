package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtVale;
import java.util.List;

public interface IControladorVale {

    public DtVale obtenerVale(Long valeId, String secreto);
       
    public List<DtVale> listarValesDisponibles(String secreto);
    
    public List<DtVale> listarVales(Long compradorId, String secreto);
    
    public List<DtVale> listarValesBorrados(Long compradorId, String secreto);
    
    public List<DtActividad> listarActividadVale(Long valeId, String secreto);
    
    public DtMensajeSistema crearVale(DtVale dtVale, Long compradorId, Long sucursalId, String secreto);
    
    public DtMensajeSistema modificarVale(DtVale dtVale, String secreto);
    
    public DtMensajeSistema borrarVale(Long valeId, String secreto);
    
    public DtMensajeSistema restaurarVale(Long valeId, String secreto);

}
