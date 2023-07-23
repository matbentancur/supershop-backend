package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtMensaje;
import java.util.List;

public interface IControladorMensaje {

    public DtMensaje obtenerMensaje(Long mensajeId, String secreto);

    public List<DtMensaje> listarMensajes(Long conversacionId, String secreto);

    public DtMensajeSistema enviarMensaje(DtMensaje dtMensaje, Long conversacionId, String secreto);

    public DtMensajeSistema borrarMensaje(Long mensajeId, String secreto);

}
