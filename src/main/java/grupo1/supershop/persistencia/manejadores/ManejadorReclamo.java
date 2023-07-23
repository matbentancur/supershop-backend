package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Reclamo;
import java.util.List;

public class ManejadorReclamo {

    private static ManejadorReclamo instance = null;

    private ManejadorReclamo() {
    }

    public static ManejadorReclamo getInstance() {
        if (instance == null) {
            instance = new ManejadorReclamo();
        }
        return instance;
    }

    public Reclamo obtenerReclamo(Long id) {
        return (Reclamo) ManejadorBase.getInstance().obtenerBean(new Reclamo(), "id", id);
    }

    public <T> Reclamo obtenerReclamo(String parametro, T valor) {
        return (Reclamo) ManejadorBase.getInstance().obtenerBean(new Reclamo(), parametro, valor);
    }
    
    public List<Reclamo> listarReclamos() {
        return ManejadorBase.getInstance().listarBean(new Reclamo(), "id", true);
    }
    
    public <T> List<Reclamo> listarReclamos(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Reclamo(), parametro, valor, parametro, true);
    }
    
    public <T, S> List<Reclamo> listarReclamos(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Reclamo(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public void crearReclamo(Reclamo reclamo) throws Exception {
        ManejadorBase.getInstance().crearBean(reclamo);
    }

    public void modificarReclamo(Reclamo reclamo) throws Exception {
        ManejadorBase.getInstance().modificarBean(reclamo);
    }

}
