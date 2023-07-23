package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Actividad;
import grupo1.supershop.beans.Sucursal;
import java.util.List;
import java.util.Set;

public class ManejadorSucursal {

    private static ManejadorSucursal instance = null;

    private ManejadorSucursal() {
    }

    public static ManejadorSucursal getInstance() {
        if (instance == null) {
            instance = new ManejadorSucursal();
        }
        return instance;
    }

    public Sucursal obtenerSucursal(Long id) {
        return (Sucursal) ManejadorBase.getInstance().obtenerBean(new Sucursal(), "id", id);
    }

    public <T> Sucursal obtenerSucursal(String parametro, T valor) {
        return (Sucursal) ManejadorBase.getInstance().obtenerBean(new Sucursal(), parametro, valor);
    }

    public List<Sucursal> listarSucursales() {
        return ManejadorBase.getInstance().listarBean(new Sucursal(), "id", true);
    }

    public <T> List<Sucursal> listarSucursales(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Sucursal(), parametro, valor, parametro, true);
    }

    public void crearSucursal(Sucursal sucursal) throws Exception {
        ManejadorBase.getInstance().crearBean(sucursal);
    }

    public void modificarSucursal(Sucursal sucursal) throws Exception {
        ManejadorBase.getInstance().modificarBean(sucursal);
    }

    public void borrarSucursal(Sucursal sucursal) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(sucursal);
    }

    public void restaurarSucursal(Sucursal sucursal) throws Exception {
        ManejadorBase.getInstance().restaurarBean(sucursal);
    }
    
    public Set<Actividad> listarActividadSucursal(Long id) {
        Sucursal sucursal = this.obtenerSucursal(id);
        return sucursal.getActividades();
    }

}
