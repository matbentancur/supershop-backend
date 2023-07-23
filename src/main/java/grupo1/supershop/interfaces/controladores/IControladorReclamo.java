package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtReclamo;
import grupo1.supershop.enums.EstadoReclamo;
import java.util.List;

public interface IControladorReclamo {

    public DtReclamo obtenerReclamo(Long reclamoId, String secreto);

    public List<DtReclamo> listarReclamos(String secreto);
    
    public List<DtReclamo> listarReclamosComprador(EstadoReclamo estadoReclamo, String secreto);
    
    public List<DtReclamo> listarReclamosCompradorTodos(String secreto);

    public List<DtReclamo> listarReclamosSucursal(EstadoReclamo estadoReclamo, String secreto);
    
    public List<DtActividad> listarActividadReclamo(Long reclamoId, String secreto);

    public DtMensajeSistema crearReclamo(DtReclamo dtReclamo, Long sucursalId, String secreto);

    public DtMensajeSistema confirmarReclamo(Long reclamoId, String secreto);

    public DtMensajeSistema concluirReclamo(DtReclamo dtReclamo, String secreto);

}
