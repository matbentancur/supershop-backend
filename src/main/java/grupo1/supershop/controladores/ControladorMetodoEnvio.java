package grupo1.supershop.controladores;

import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import grupo1.supershop.interfaces.controladores.IControladorMetodoEnvio;
import grupo1.supershop.persistencia.manejadores.ManejadorMetodoEnvio;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorMetodoEnvio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorMetodoEnvio implements IControladorMetodoEnvio {

    @Override
    public DtMetodoEnvio obtenerMetodoEnvio(Long metodoEnvioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        MetodoEnvio metodoEnvio = ManejadorMetodoEnvio.getInstance().obtenerMetodoEnvio(metodoEnvioId);
        if (metodoEnvio == null) {
            return null;
        }
        return (DtMetodoEnvio) metodoEnvio.getDataType();
    }

    @Override
    public List<DtMetodoEnvio> listarMetodosEnvio() {
        List<MetodoEnvio> lista = ManejadorMetodoEnvio.getInstance().listarMetodosEnvio("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtMetodoEnvio> listarMetodosEnvioBorrados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<MetodoEnvio> lista = ManejadorMetodoEnvio.getInstance().listarMetodosEnvio("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadMetodoEnvio(Long metodoEnvioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        //OBTIENE METODO DE ENVIO
        MetodoEnvio metodoEnvio = ManejadorMetodoEnvio.getInstance().obtenerMetodoEnvio(metodoEnvioId);
        if (metodoEnvio == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(metodoEnvio.getActividades());
    }

    @Override
    public DtMensajeSistema crearMetodoEnvio(DtMetodoEnvio dtMetodoEnvio, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE METODO ENVIO
            MetodoEnvio metodoEnvio = new MetodoEnvio(dtMetodoEnvio);

            //VALIDA METODO ENVIO
            DtMensajeSistema dtMensajeSistema = ValidadorMetodoEnvio.getInstance().validarCrear(metodoEnvio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA METODO ENVIO
            ManejadorMetodoEnvio.getInstance().crearMetodoEnvio(metodoEnvio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(metodoEnvio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("MetodoEnvio.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMetodoEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.create.error");
    }

    @Override
    public DtMensajeSistema modificarMetodoEnvio(DtMetodoEnvio dtMetodoEnvio, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE METODO ENVIO
            MetodoEnvio metodoEnvio = ManejadorMetodoEnvio.getInstance().obtenerMetodoEnvio(dtMetodoEnvio.getId());
            if (metodoEnvio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.notexists.error");
            }

            metodoEnvio.modificar(dtMetodoEnvio);

            //VALIDA METODO ENVIO
            DtMensajeSistema dtMensajeSistema = ValidadorMetodoEnvio.getInstance().validarModificar(metodoEnvio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA METODO ENVIO
            ManejadorMetodoEnvio.getInstance().modificarMetodoEnvio(metodoEnvio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(metodoEnvio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("MetodoEnvio.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMetodoEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.update.error");
    }

    @Override
    public DtMensajeSistema borrarMetodoEnvio(Long metodoEnvioId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE METODO ENVIO
            MetodoEnvio metodoEnvio = ManejadorMetodoEnvio.getInstance().obtenerMetodoEnvio(metodoEnvioId);
            if (metodoEnvio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.notexists.error");
            }

            //VALIDA METODO ENVIO
            DtMensajeSistema dtMensajeSistema = ValidadorMetodoEnvio.getInstance().validarBorrar(metodoEnvio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA METODO ENVIO
            ManejadorMetodoEnvio.getInstance().borrarMetodoEnvio(metodoEnvio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(metodoEnvio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("MetodoEnvio.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMetodoEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.delete.error");
    }

    @Override
    public DtMensajeSistema restaurarMetodoEnvio(Long metodoEnvioId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE METODO ENVIO
            MetodoEnvio metodoEnvio = ManejadorMetodoEnvio.getInstance().obtenerMetodoEnvio(metodoEnvioId);
            if (metodoEnvio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.notexists.error");
            }

            //VALIDA METODO ENVIO
            DtMensajeSistema dtMensajeSistema = ValidadorMetodoEnvio.getInstance().validarRestaurar(metodoEnvio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA METODO ENVIO
            ManejadorMetodoEnvio.getInstance().restaurarMetodoEnvio(metodoEnvio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(metodoEnvio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("MetodoEnvio.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorMetodoEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.restore.error");
    }

}
