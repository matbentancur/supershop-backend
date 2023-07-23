package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Categoria;
import java.util.List;

public class ManejadorCategoria {

    private static ManejadorCategoria instance = null;

    private ManejadorCategoria() {
    }

    public static ManejadorCategoria getInstance() {
        if (instance == null) {
            instance = new ManejadorCategoria();
        }
        return instance;
    }

    public Categoria obtenerCategoria(Long id) {
        return (Categoria) ManejadorBase.getInstance().obtenerBean(new Categoria(), "id", id);
    }

    public <T> Categoria obtenerCategoria(String parametro, T valor) {
        return (Categoria) ManejadorBase.getInstance().obtenerBean(new Categoria(), parametro, valor);
    }
    
    public List<Categoria> listarCategorias() {
        return ManejadorBase.getInstance().listarBean(new Categoria(), "id", true);
    }
    
    public <T> List<Categoria> listarCategorias(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Categoria(), parametro, valor, parametro, true);
    }

    public void crearCategoria(Categoria categoria) throws Exception {
        ManejadorBase.getInstance().crearBean(categoria);
    }

    public void modificarCategoria(Categoria categoria) throws Exception {
        ManejadorBase.getInstance().modificarBean(categoria);
    }

    public void borrarCategoria(Categoria categoria) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(categoria);
    }

    public void restaurarCategoria(Categoria categoria) throws Exception {
        ManejadorBase.getInstance().restaurarBean(categoria);
    }

}
