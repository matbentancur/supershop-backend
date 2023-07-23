package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Producto;
import grupo1.supershop.persistencia.conexiones.ConexionDataBase;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ManejadorProducto {

    private static ManejadorProducto instance = null;

    private ManejadorProducto() {
    }

    public static ManejadorProducto getInstance() {
        if (instance == null) {
            instance = new ManejadorProducto();
        }
        return instance;
    }

    public Producto obtenerProducto(Long id) {
        return (Producto) ManejadorBase.getInstance().obtenerBean(new Producto(), "id", id);
    }

    public <T> Producto obtenerProducto(String parametro, T valor) {
        return (Producto) ManejadorBase.getInstance().obtenerBean(new Producto(), parametro, valor);
    }

    public List<Producto> listarProductos() {
        return ManejadorBase.getInstance().listarBean(new Producto(), "id", true);
    }

    public <T> List<Producto> listarProductos(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Producto(), parametro, valor, parametro, true);
    }

    public void crearProducto(Producto producto) throws Exception {
        ManejadorBase.getInstance().crearBean(producto);
    }

    public void modificarProducto(Producto producto) throws Exception {
        ManejadorBase.getInstance().modificarBean(producto);
    }

    public void borrarProducto(Producto producto) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(producto);
    }

    public void restaurarProducto(Producto producto) throws Exception {
        ManejadorBase.getInstance().restaurarBean(producto);
    }

    public List<Producto> listarProductosDisponibles(Long sucursalId) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "SELECT p "
                + "FROM Stock AS st "
                + "JOIN st.producto AS p "
                + "WHERE "
                + "st.sucursal.id = :sucursalId "
                + "AND "
                + "p.borrado = :borrado "
                + "AND "
                + "st.cantidad > :cantidad ",
                Producto.class
        );
        query.setParameter("sucursalId", sucursalId);
        query.setParameter("borrado", false);
        query.setParameter("cantidad", 0);
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

    public List<Producto> buscarProducto(String texto) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "FROM Producto AS p "
                + "WHERE "
                + "LOWER(p.nombre) LIKE LOWER(:texto) "
                + "OR "
                + "LOWER(p.descripcion) LIKE LOWER(:texto)",
                Producto.class
        );
        query.setParameter("texto", "%" + texto + "%");
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

}
