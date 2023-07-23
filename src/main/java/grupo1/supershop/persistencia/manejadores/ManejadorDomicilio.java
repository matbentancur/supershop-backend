package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Domicilio;
import java.util.List;

public class ManejadorDomicilio {

    private static ManejadorDomicilio instance = null;

    private ManejadorDomicilio() {
    }

    public static ManejadorDomicilio getInstance() {
        if (instance == null) {
            instance = new ManejadorDomicilio();
        }
        return instance;
    }

    public Domicilio obtenerDomicilio(Long id) {
        return (Domicilio) ManejadorBase.getInstance().obtenerBean(new Domicilio(), "id", id);
    }

    public <T> Domicilio obtenerDomicilio(String parametro, T valor) {
        return (Domicilio) ManejadorBase.getInstance().obtenerBean(new Domicilio(), parametro, valor);
    }

    public <T> List<Domicilio> listarDomicilios(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Domicilio(), parametro, valor, parametro, true);
    }

    public <T, S> List<Domicilio> listarDomicilios(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Domicilio(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public void crearDomicilio(Domicilio domicilio) throws Exception {
        ManejadorBase.getInstance().crearBean(domicilio);
    }

    public void modificarDomicilio(Domicilio domicilio) throws Exception {
        ManejadorBase.getInstance().modificarBean(domicilio);
    }

    public void borrarDomicilio(Domicilio domicilio) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(domicilio);
    }

    public void restaurarDomicilio(Domicilio domicilio) throws Exception {
        ManejadorBase.getInstance().restaurarBean(domicilio);
    }

}
