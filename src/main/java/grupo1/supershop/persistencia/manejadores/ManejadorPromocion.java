package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Promocion;
import grupo1.supershop.persistencia.conexiones.ConexionDataBase;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ManejadorPromocion {

    private static ManejadorPromocion instance = null;

    private ManejadorPromocion() {
    }

    public static ManejadorPromocion getInstance() {
        if (instance == null) {
            instance = new ManejadorPromocion();
        }
        return instance;
    }

    public Promocion obtenerPromocion(Long id) {
        return (Promocion) ManejadorBase.getInstance().obtenerBean(new Promocion(), "id", id);
    }

    public <T> Promocion obtenerPromocion(String parametro, T valor) {
        return (Promocion) ManejadorBase.getInstance().obtenerBean(new Promocion(), parametro, valor);
    }

    public <T> List<Promocion> listarPromociones() {
        return ManejadorBase.getInstance().listarBean(new Promocion(), "borrado", false, "id", true);
    }

    public <T> List<Promocion> listarPromociones(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Promocion(), parametro, valor, parametro, true);
    }

    public <T, S> List<Promocion> listarPromociones(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Promocion(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public void crearPromocion(Promocion promocion) throws Exception {
        ManejadorBase.getInstance().crearBean(promocion);
    }

    public void modificarPromocion(Promocion promocion) throws Exception {
        ManejadorBase.getInstance().modificarBean(promocion);
    }

    public void borrarPromocion(Promocion promocion) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(promocion);
    }

    public void restaurarPromocion(Promocion promocion) throws Exception {
        ManejadorBase.getInstance().restaurarBean(promocion);
    }

    public List<Promocion> listarPromocionesVigentesProducto(Long sucursalId, Long productoId) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "FROM Promocion AS p "
                + "WHERE "
                + "p.borrado = :borrado "
                + "AND "
                + "p.inicia <= CURRENT_TIMESTAMP "
                + "AND "
                + "p.finaliza >= CURRENT_TIMESTAMP "
                + "AND "
                + "sucursal.id = :sucursalId "
                + "AND "
                + "producto.id = :productoId "
                + "ORDER BY p.inicia ASC",
                Promocion.class
        );
        query.setParameter("borrado", false);
        query.setParameter("sucursalId", sucursalId);
        query.setParameter("productoId", productoId);
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

    public List<Promocion> listarPromocionesVigentes(Long sucursalId) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "FROM Promocion AS p "
                + "WHERE "
                + "p.borrado = :borrado "
                + "AND "
                + "p.inicia <= CURRENT_TIMESTAMP "
                + "AND "
                + "p.finaliza >= CURRENT_TIMESTAMP "
                + "AND "
                + "sucursal.id = :sucursalId "
                + "ORDER BY p.inicia ASC",
                Promocion.class
        );
        query.setParameter("borrado", false);
        query.setParameter("sucursalId", sucursalId);
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

    public List<Promocion> listarPromocionesExpiradas(Long sucursalId) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "FROM Promocion AS p "
                + "WHERE "
                + "p.borrado = :borrado "
                + "AND "
                + "p.finaliza <= CURRENT_TIMESTAMP "
                + "AND "
                + "sucursal.id = :sucursalId "
                + "ORDER BY p.inicia ASC",
                Promocion.class
        );
        query.setParameter("borrado", false);
        query.setParameter("sucursalId", sucursalId);
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

    public List<Promocion> listarPromocionesProximas(Long sucursalId) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "FROM Promocion AS p "
                + "WHERE "
                + "p.borrado = :borrado "
                + "AND "
                + "p.inicia >= CURRENT_TIMESTAMP "
                + "AND "
                + "sucursal.id = :sucursalId "
                + "ORDER BY p.inicia ASC",
                Promocion.class
        );
        query.setParameter("borrado", false);
        query.setParameter("sucursalId", sucursalId);
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

}
