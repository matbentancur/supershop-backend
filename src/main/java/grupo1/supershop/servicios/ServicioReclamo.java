package grupo1.supershop.servicios;

import grupo1.supershop.beans.Reclamo;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.enums.EstadoReclamo;
import grupo1.supershop.persistencia.manejadores.ManejadorReclamo;
import java.util.List;

public class ServicioReclamo {

    private static ServicioReclamo instance = null;

    private ServicioReclamo() {
    }

    public static ServicioReclamo getInstance() {
        if (instance == null) {
            instance = new ServicioReclamo();
        }
        return instance;
    }

    public boolean verificarReclamosPendientes(Usuario usuario) {
        List<Reclamo> listaReclamos = ManejadorReclamo.getInstance().listarReclamos("comprador.id", usuario.getId(), "estado", EstadoReclamo.PENDIENTE);

        if (listaReclamos.size() < 3) {
            return true;
        } else {
            return false;
        }
    }

}
