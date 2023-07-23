package grupo1.supershop.controladores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Domicilio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.interfaces.controladores.IControladorDomicilio;
import grupo1.supershop.persistencia.manejadores.ManejadorDomicilio;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.servicios.ServicioUsuario;
import grupo1.supershop.validadores.ValidadorDomicilio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorDomicilio implements IControladorDomicilio {

    @Override
    public DtDomicilio obtenerDomicilio(Long domicilioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        //OBTIENE DOMICILIO
        Domicilio domicilio = ManejadorDomicilio.getInstance().obtenerDomicilio(domicilioId);
        if (domicilio == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        Comprador comprador = (Comprador) usuario;
        if (!ServicioUsuario.getInstance().esPropietarioDomicilio(comprador, domicilio)) {
            return null;
        }

        return (DtDomicilio) domicilio.getDataType();
    }

    @Override
    public List<DtDomicilio> listarDomiciliosComprador(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Domicilio> lista = ManejadorDomicilio.getInstance().listarDomicilios("comprador.id", usuario.getId());
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtDomicilio> listarDomiciliosCompradorBorrados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Domicilio> lista = ManejadorDomicilio.getInstance().listarDomicilios("comprador.id", usuario.getId(), "borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public DtMensajeSistema crearDomicilio(DtDomicilio dtDomicilio, String secreto) {
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

            //OBTIENE DOMICILIO
            Domicilio domicilio = new Domicilio(dtDomicilio);
            Comprador comprador = (Comprador) usuario;
            domicilio.setComprador(comprador);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorDomicilio.getInstance().validarCrear(domicilio, comprador);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA
            ManejadorDomicilio.getInstance().crearDomicilio(domicilio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(domicilio, comprador);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Domicilio.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorDomicilio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.create.error");
    }

    @Override
    public DtMensajeSistema modificarDomicilio(DtDomicilio dtDomicilio, String secreto) {
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

            //OBTIENE DOMICILIO
            Domicilio domicilio = ManejadorDomicilio.getInstance().obtenerDomicilio(dtDomicilio.getId());
            if (domicilio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.notexists.error");
            }

            domicilio.modificar(dtDomicilio);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorDomicilio.getInstance().validarModificar(domicilio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorDomicilio.getInstance().modificarDomicilio(domicilio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(domicilio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Domicilio.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorDomicilio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.update.error");
    }

    @Override
    public DtMensajeSistema borrarDomicilio(Long domicilioId, String secreto) {
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

            //OBTIENE DOMICILIO
            Domicilio domicilio = ManejadorDomicilio.getInstance().obtenerDomicilio(domicilioId);
            if (domicilio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorDomicilio.getInstance().validarBorrar(domicilio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA
            ManejadorDomicilio.getInstance().borrarDomicilio(domicilio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(domicilio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Domicilio.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorDomicilio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.delete.error");
    }

    @Override
    public DtMensajeSistema restaurarDomicilio(Long domicilioId, String secreto) {
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

            //OBTIENE DOMICILIO
            Domicilio domicilio = ManejadorDomicilio.getInstance().obtenerDomicilio(domicilioId);
            if (domicilio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorDomicilio.getInstance().validarRestaurar(domicilio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA
            ManejadorDomicilio.getInstance().restaurarDomicilio(domicilio);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(domicilio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Domicilio.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorDomicilio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.restore.error");
    }

}
