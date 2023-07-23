package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Vale;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.persistencia.conexiones.ConexionDataBase;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ManejadorVale {

    private static ManejadorVale instance = null;

    private ManejadorVale() {
    }

    public static ManejadorVale getInstance() {
        if (instance == null) {
            instance = new ManejadorVale();
        }
        return instance;
    }

    public Vale obtenerVale(Long id) {
        return (Vale) ManejadorBase.getInstance().obtenerBean(new Vale(), "id", id);
    }

    public <T> Vale obtenerVale(String parametro, T valor) {
        return (Vale) ManejadorBase.getInstance().obtenerBean(new Vale(), parametro, valor);
    }

    public Set<Vale> obtenerListaVales(List<DtVale> listaDtVales) {
        Set<Vale> conjuntoVales = new HashSet<Vale>();
        for (DtVale dtVale : listaDtVales) {
            Vale vale = this.obtenerVale(dtVale.getId());
            if (vale != null) {
                conjuntoVales.add(vale);

            } else {
                return null;
            }
        }
        return conjuntoVales;
    }

    public <T> List<Vale> listarVales(String parametro, T valor) {
        return ManejadorBase.getInstance().listarBean(new Vale(), parametro, valor, parametro, true);
    }

    public <T, S> List<Vale> listarVales(String parametro1, T valor1, String parametro2, S valor2) {
        return ManejadorBase.getInstance().listarBean(new Vale(), parametro1, valor1, parametro2, valor2, parametro1, true);
    }

    public <T, S, U> List<Vale> listarVales(String parametro1, T valor1, String parametro2, S valor2, String parametro3, U valor3) {
        return ManejadorBase.getInstance().listarBean(new Vale(), parametro1, valor1, parametro2, valor2, parametro3, valor3, parametro1, true);
    }

    public void crearVale(Vale vale) throws Exception {
        ManejadorBase.getInstance().crearBean(vale);
    }

    public void modificarVale(Vale vale) throws Exception {
        ManejadorBase.getInstance().modificarBean(vale);
    }

    public void borrarVale(Vale vale) throws Exception {
        ManejadorBase.getInstance().borradoLogicoBean(vale);
    }

    public void restaurarVale(Vale vale) throws Exception {
        ManejadorBase.getInstance().restaurarBean(vale);
    }

    public List<Vale> listarValesDisponibles(Long compradorId) {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        Query query = session.createQuery(""
                + "FROM Vale AS v "
                + "WHERE "
                + "v.borrado = :borrado "
                + "AND "
                + "v.expira >= CURRENT_TIMESTAMP "
                + "AND "
                + "v.estado = :estado "
                + "AND "
                + "comprador.id = :compradorId "
                + "ORDER BY v.expira ASC",
                Vale.class
        );
        query.setParameter("borrado", false);
        query.setParameter("estado", EstadoVale.DISPONIBLE);
        query.setParameter("compradorId", compradorId);
        return ManejadorBase.getInstance().executeQueryList(query, session);
    }

}
