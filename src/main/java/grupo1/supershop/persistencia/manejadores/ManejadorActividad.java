package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Actividad;
import grupo1.supershop.persistencia.conexiones.ConexionDataBase;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ManejadorActividad {

    private static ManejadorActividad instance = null;

    private ManejadorActividad() {
    }

    public static ManejadorActividad getInstance() {
        if (instance == null) {
            instance = new ManejadorActividad();
        }
        return instance;
    }

    public Actividad obtenerActividad(Long id) {
        return (Actividad) ManejadorBase.getInstance().obtenerBean(new Actividad(), "id", id);
    }

    public <T> List<Actividad> listarActividades(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Actividad(), parametro, valor, parametro, true);
    }

    public void crearActividad(Actividad actividad) throws Exception {
        ManejadorBase.getInstance().crearBean(actividad);
    }

    public List<Actividad> listarActividadesTemporal(Date inicia, Date finaliza) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "FROM Actividad AS a "
                + "WHERE "
                + "momento >= :inicia "
                + "AND "
                + "momento <= :finaliza "
                + "ORDER BY a.momento ASC",
                Actividad.class
        );
        query.setParameter("inicia", inicia);
        query.setParameter("finaliza", finaliza);
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

}
