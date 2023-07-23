package grupo1.supershop.controladores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Conversacion;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtConversacion;
import grupo1.supershop.interfaces.controladores.IControladorConversacion;
import grupo1.supershop.persistencia.manejadores.ManejadorConversacion;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.servicios.ServicioUsuario;
import grupo1.supershop.validadores.ValidadorConversacion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorConversacion implements IControladorConversacion {

    @Override
    public DtConversacion obtenerConversacion(Long conversacionId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursalComprador(usuario)) {
            return null;
        }

        Conversacion conversacion = ManejadorConversacion.getInstance().obtenerConversacion(conversacionId);
        if (conversacion == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        if (!ServicioUsuario.getInstance().esPropietarioConversacion(usuario, conversacion)) {
            return null;
        }

        return (DtConversacion) conversacion.getDataType();
    }

    @Override
    public List<DtConversacion> listarConversacionesComprador(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Conversacion> lista = ManejadorConversacion.getInstance().listarConversaciones("comprador.id", usuario.getId(), "finalizada", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtConversacion> listarConversacionesCompradorFinalizadas(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Conversacion> lista = ManejadorConversacion.getInstance().listarConversaciones("comprador.id", usuario.getId(), "finalizada", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtConversacion> listarConversacionesSucursal(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //OBTIENE SUCURSAL
        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
        if (sucursal == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursal(usuario)) {
            return null;
        }

        //VERIFICA PROPIETARIO
        UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
        if (!ServicioUsuario.getInstance().perteneceSusursal(usuarioSucursal, sucursal)) {
            return null;
        }

        List<Conversacion> lista = ManejadorConversacion.getInstance().listarConversaciones("sucursal.id", sucursalId, "finalizada", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtConversacion> listarConversacionesSucursalFinalizadas(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //OBTIENE SUCURSAL
        Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
        if (sucursal == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursal(usuario)) {
            return null;
        }

        //VERIFICA PROPIETARIO
        UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
        if (!ServicioUsuario.getInstance().perteneceSusursal(usuarioSucursal, sucursal)) {
            return null;
        }

        List<Conversacion> lista = ManejadorConversacion.getInstance().listarConversaciones("sucursal.id", sucursalId, "finalizada", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadConversacion(Long conversacionId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }

        //OBTIENE CONVERSACION
        Conversacion conversacion = ManejadorConversacion.getInstance().obtenerConversacion(conversacionId);
        if (conversacion == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(conversacion.getActividades());
    }

    @Override
    public DtMensajeSistema crearConversacion(Long sucursalId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE CONVERSACION
            Conversacion conversacion = new Conversacion();

            //VALIDA CONVERSACION
            DtMensajeSistema dtMensajeSistema = ValidadorConversacion.getInstance().validarCrear(conversacion, sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA CONVERSACION
            conversacion.setFinalizada(false);
            conversacion.setComprador((Comprador) usuario);
            conversacion.setSucursal(sucursal);
            ManejadorConversacion.getInstance().crearConversacion(conversacion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(conversacion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Conversacion.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Conversacion.create.error");
    }

    @Override
    public DtMensajeSistema finalizarConversacion(Long conversacionId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoSucursal(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE CONVERSACION
            Conversacion conversacion = ManejadorConversacion.getInstance().obtenerConversacion(conversacionId);
            if (conversacion == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Conversacion.notexists.error");
            }

            //VERIFICA PROPIETARIO
            UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;
            if (!ServicioUsuario.getInstance().perteneceSusursal(usuarioSucursal, conversacion.getSucursal())) {
                return null;
            }

            //VALIDA CONVERSACION
            DtMensajeSistema dtMensajeSistema = ValidadorConversacion.getInstance().validarFinalizar(conversacion, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA CONVERSACION
            conversacion.finalizar();
            ManejadorConversacion.getInstance().modificarConversacion(conversacion);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarConclusion(conversacion, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Conversacion.conclude.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorConversacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Conversacion.conclude.error");
    }

}
