package grupo1.supershop.controladores;

import grupo1.supershop.beans.Actividad;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.interfaces.controladores.IControladorActividad;
import grupo1.supershop.persistencia.manejadores.ManejadorActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import java.util.Date;
import java.util.List;

public class ControladorActividad implements IControladorActividad {

    @Override
    public DtActividad obtenerActividad(Long actividadId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }

        Actividad actividad = ManejadorActividad.getInstance().obtenerActividad(actividadId);
        if (actividad == null) {
            return null;
        }
        return (DtActividad) actividad.getDataType();
    }

    @Override
    public List<DtActividad> listarActividadesTemporal(Date inicio, Date fin, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<Actividad> lista = ManejadorActividad.getInstance().listarActividadesTemporal(inicio, fin);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadesUsuario(Long usuarioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }
        List<Actividad> lista = ManejadorActividad.getInstance().listarActividades("usuario.id", usuarioId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

}
