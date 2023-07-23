package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import java.util.Date;
import java.util.List;

public interface IControladorActividad {

    public DtActividad obtenerActividad(Long actividadId, String secreto);

    public List<DtActividad> listarActividadesTemporal(Date inicio, Date fin, String secreto);
    
    public List<DtActividad> listarActividadesUsuario(Long usuarioId, String secreto);
    
}
