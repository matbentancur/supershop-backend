package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.MetodoEnvio;
import java.util.List;

public class ManejadorMetodoEnvio {

    private static ManejadorMetodoEnvio instance = null;

    private ManejadorMetodoEnvio() {
    }

    public static ManejadorMetodoEnvio getInstance() {
        if (instance == null) {
            instance = new ManejadorMetodoEnvio();
        }
        return instance;
    }

    public MetodoEnvio obtenerMetodoEnvio(Long id) {
        return (MetodoEnvio) ManejadorBase.getInstance().obtenerBean(new MetodoEnvio(), "id", id);
    }

    public <T> MetodoEnvio obtenerMetodoEnvio(String parametro, T valor) {
        return (MetodoEnvio) ManejadorBase.getInstance().obtenerBean(new MetodoEnvio(), parametro, valor);
    }
    
    public List<MetodoEnvio> listarMetodosEnvio() {
        return ManejadorBase.getInstance().listarBean(new MetodoEnvio(),"id", true);
    }

    public <T> List<MetodoEnvio> listarMetodosEnvio(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new MetodoEnvio(), parametro, valor, parametro, true);
    }

    public void crearMetodoEnvio(MetodoEnvio metodoEnvio) throws Exception {
        ManejadorBase.getInstance().crearBean(metodoEnvio);
    }

    public void modificarMetodoEnvio(MetodoEnvio metodoEnvio) throws Exception {
        ManejadorBase.getInstance().modificarBean(metodoEnvio);
    }

    public void borrarMetodoEnvio(MetodoEnvio metodoEnvio) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(metodoEnvio);
    }

    public void restaurarMetodoEnvio(MetodoEnvio metodoEnvio) throws Exception {
        ManejadorBase.getInstance().restaurarBean(metodoEnvio);
    }

}
