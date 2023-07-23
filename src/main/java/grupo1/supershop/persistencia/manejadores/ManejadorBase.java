package grupo1.supershop.persistencia.manejadores;

import grupo1.supershop.beans.Base;
import grupo1.supershop.beans.Borrable;
import grupo1.supershop.beans.Identificable;
import grupo1.supershop.datatypes.DtIdentificable;
import grupo1.supershop.persistencia.conexiones.ConexionDataBase;
import grupo1.supershop.servicios.ServicioBeans;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class ManejadorBase {

    private static ManejadorBase instance = null;

    private ManejadorBase() {
    }

    public static ManejadorBase getInstance() {
        if (instance == null) {
            instance = new ManejadorBase();
        }
        return instance;
    }

    public Session getSession() {
        Session session = ConexionDataBase.getSessionFactory().openSession();
        return session;
    }

    public void closeSession(Session session) {
        if (session != null || session.isOpen()) {
            session.close();
        }
    }

    private Base executeQueryUniqueResult(Query query, Session session) {
        try {
            session.beginTransaction();
            Base bean = (Base) query.uniqueResult();
            session.getTransaction().commit();
            return bean;

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public <T extends Base> List<T> executeQueryList(Query query, Session session) {
        session.beginTransaction();
        try {
            List<T> lista = query.list();
            session.getTransaction().commit();
            return lista;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    public <T> Base obtenerBean(Base bean, String parametro, T valor) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro + " = :valor",
                    bean.getClass()
            );
            query.setParameter("valor", valor);
            return this.executeQueryUniqueResult(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T, T2> Base obtenerBean(Base bean, String parametro1, T valor1, String parametro2, T2 valor2) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2",
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            return this.executeQueryUniqueResult(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T, T2, T3> Base obtenerBean(Base bean, String parametro1, T valor1, String parametro2, T2 valor2, String parametro3, T3 valor3) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2"
                    + " AND " + parametro3 + " = :valor3",
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            query.setParameter("valor3", valor3);
            return this.executeQueryUniqueResult(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T, T2, T3, T4> Base obtenerBean(Base bean, String parametro1, T valor1, String parametro2, T2 valor2, String parametro3, T3 valor3, String parametro4, T4 valor4) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2"
                    + " AND " + parametro3 + " = :valor3"
                    + " AND " + parametro4 + " = :valor4",
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            query.setParameter("valor3", valor3);
            query.setParameter("valor4", valor4);
            return this.executeQueryUniqueResult(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public Base obtenerBean(DtIdentificable dtIdentificable) {
        Identificable bean = (Identificable) ServicioBeans.getInstance().parseDtBase(dtIdentificable);
        return this.obtenerBean(bean, "id", dtIdentificable.getId());
    }

    public <T extends Base> List<T> listarBeanNotOrdered(Base bean) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName(),
                    bean.getClass()
            );
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base> List<T> listarBean(Base bean, String ordenParametro, boolean ascendente) {
        Session session = this.getSession();
        try {
            String ordenCriterio = ascendente ? "ASC" : "DESC";
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " ORDER BY " + ordenParametro + " " + ordenCriterio,
                    bean.getClass()
            );
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base, T2> List<T> listarBeanNotOrdered(Base bean, String parametro, T2 valor) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro + " = :valor",
                    bean.getClass()
            );
            query.setParameter("valor", valor);
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base, T2> List<T> listarBean(Base bean, String parametro, T2 valor, String ordenParametro, boolean ascendente) {
        Session session = this.getSession();
        try {
            String ordenCriterio = ascendente ? "ASC" : "DESC";
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro + " = :valor"
                    + " ORDER BY " + ordenParametro + " " + ordenCriterio,
                    bean.getClass()
            );
            query.setParameter("valor", valor);
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base, T2, T3> List<T> listarBeanNotOrdered(Base bean, String parametro1, T2 valor1, String parametro2, T3 valor2) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2",
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base, T2, T3> List<T> listarBean(Base bean, String parametro1, T2 valor1, String parametro2, T3 valor2, String ordenParametro, boolean ascendente) {
        Session session = this.getSession();
        try {
            String ordenCriterio = ascendente ? "ASC" : "DESC";
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2"
                    + " ORDER BY " + ordenParametro + " " + ordenCriterio,
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base, T2, T3, T4> List<T> listarBeanNotOrdered(Base bean, String parametro1, T2 valor1, String parametro2, T3 valor2, String parametro3, T4 valor3) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2"
                    + " AND " + parametro3 + " = :valor3",
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            query.setParameter("valor3", valor3);
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base, T2, T3, T4> List<T> listarBean(Base bean, String parametro1, T2 valor1, String parametro2, T3 valor2, String parametro3, T4 valor3, String ordenParametro, boolean ascendente) {
        Session session = this.getSession();
        try {
            String ordenCriterio = ascendente ? "ASC" : "DESC";
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2"
                    + " AND " + parametro3 + " = :valor3"
                    + " ORDER BY " + ordenParametro + " " + ordenCriterio,
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            query.setParameter("valor3", valor3);
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public <T extends Base, T2, T3, T4, T5> List<T> listarBeanNotOrdered(Base bean, String parametro1, T2 valor1, String parametro2, T3 valor2, String parametro3, T4 valor3, String parametro4, T5 valor4) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery(""
                    + "FROM " + bean.getClass().getName()
                    + " WHERE " + parametro1 + " = :valor1"
                    + " AND " + parametro2 + " = :valor2"
                    + " AND " + parametro3 + " = :valor3"
                    + " AND " + parametro4 + " = :valor4",
                    bean.getClass()
            );
            query.setParameter("valor1", valor1);
            query.setParameter("valor2", valor2);
            query.setParameter("valor3", valor3);
            query.setParameter("valor4", valor4);
            return this.executeQueryList(query, session);
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public void crearBean(Base bean) throws Exception {
        Session session = this.getSession();
        try {
            session.beginTransaction();
            session.persist(bean);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            Exception ex = new Exception();
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } finally {
            this.closeSession(session);
        }
    }

    public void modificarBean(Base bean) throws Exception {
        Session session = this.getSession();
        try {
            session.beginTransaction();
            session.merge(bean);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            Exception ex = new Exception();
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } finally {
            this.closeSession(session);
        }
    }

    public void borrarBean(Base bean) throws Exception {
        Session session = this.getSession();
        try {
            session.beginTransaction();
            session.remove(bean);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            Exception ex = new Exception();
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } finally {
            this.closeSession(session);
        }
    }

    public void borradoLogicoBean(Borrable bean) throws Exception {
        bean.setBorrado(true);
        this.modificarBean(bean);
    }

    public void restaurarBean(Borrable bean) throws Exception {
        bean.setBorrado(false);
        this.modificarBean(bean);
    }

    public Integer contarBean(Base bean) {
        Session session = this.getSession();
        try {
            Query query = session.createQuery("SELECT COUNT(*) FROM " + bean.getClass().getName(), bean.getClass());
            List lista = this.executeQueryList(query, session);
            if (!lista.isEmpty()) {
                return Integer.parseInt(lista.get(0).toString());
            } else {
                return 0;
            }
        } catch (HibernateException e) {
            return null;
        } finally {
            this.closeSession(session);
        }
    }

    public List<Object[]> ejecutarSQL(String sql) throws Exception {
        Session session = this.getSession();
        session.beginTransaction();
        try {
            Query query = session.createNativeQuery(sql, Object.class);
            List<Object[]> lista = query.list();
            session.getTransaction().commit();
            return lista;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            Exception ex = new Exception();
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        } finally {
            this.closeSession(session);
        }

    }

}
