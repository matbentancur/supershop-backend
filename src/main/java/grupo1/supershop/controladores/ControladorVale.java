package grupo1.supershop.controladores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.interfaces.controladores.IControladorVale;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorVale;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.servicios.ServicioUsuario;
import grupo1.supershop.validadores.ValidadorVale;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorVale implements IControladorVale {

    @Override
    public DtVale obtenerVale(Long valeId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursalComprador(usuario)) {
            return null;
        }

        Vale vale = ManejadorVale.getInstance().obtenerVale(valeId);
        if (vale == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        if (!ServicioUsuario.getInstance().esPropietarioVale(usuario, vale)) {
            return null;
        }

        return (DtVale) vale.getDataType();
    }

    @Override
    public List<DtVale> listarValesDisponibles(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Vale> lista = ManejadorVale.getInstance().listarValesDisponibles(usuario.getId());
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtVale> listarVales(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursal(usuario)) {
            return null;
        }

        List<Vale> lista = ManejadorVale.getInstance().listarVales("comprador.id", compradorId, "borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtVale> listarValesBorrados(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursal(usuario)) {
            return null;
        }

        List<Vale> lista = ManejadorVale.getInstance().listarVales("comprador.id", compradorId, "borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadVale(Long valeId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        //OBTIENE VALE
        Vale vale = ManejadorVale.getInstance().obtenerVale(valeId);
        if (vale == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(vale.getActividades());
    }

    @Override
    public DtMensajeSistema crearVale(DtVale dtVale, Long compradorId, Long sucursalId, String secreto) {
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

            //OBTIENE COMPRADOR
            Comprador comprador = (Comprador) ManejadorUsuario.getInstance().obtenerUsuario(compradorId);
            if (comprador == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE VALE
            Vale vale = new Vale(dtVale);
            vale.setComprador(comprador);
            vale.setSucursal(sucursal);
            vale.setEstado(EstadoVale.DISPONIBLE);

            //VALIDA VALE
            DtMensajeSistema dtMensajeSistema = ValidadorVale.getInstance().validarCrear(vale, comprador, sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA VALE
            ManejadorVale.getInstance().crearVale(vale);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(vale, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Vale.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorVale.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Vale.create.error");
    }

    @Override
    public DtMensajeSistema modificarVale(DtVale dtVale, String secreto) {
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

            //OBTIENE VALE
            Vale vale = ManejadorVale.getInstance().obtenerVale(dtVale.getId());
            if (vale == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Vale.notexists.error");
            }

            vale.modificar(dtVale);

            //VALIDA VALE
            DtMensajeSistema dtMensajeSistema = ValidadorVale.getInstance().validarModificar(vale, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA VALE
            ManejadorVale.getInstance().modificarVale(vale);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(vale, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Vale.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorVale.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Vale.update.error");
    }

    @Override
    public DtMensajeSistema borrarVale(Long valeId, String secreto) {
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

            //OBTIENE VALE
            Vale vale = ManejadorVale.getInstance().obtenerVale(valeId);
            if (vale == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Vale.notexists.error");
            }

            //VALIDA VALE
            DtMensajeSistema dtMensajeSistema = ValidadorVale.getInstance().validarBorrar(vale, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA VALE
            ManejadorVale.getInstance().borrarVale(vale);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(vale, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Vale.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorVale.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Vale.delete.error");
    }

    @Override
    public DtMensajeSistema restaurarVale(Long valeId, String secreto) {
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

            //OBTIENE VALE
            Vale vale = ManejadorVale.getInstance().obtenerVale(valeId);
            if (vale == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Vale.notexists.error");
            }

            //VALIDA VALE
            DtMensajeSistema dtMensajeSistema = ValidadorVale.getInstance().validarRestaurar(vale, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA VALE
            ManejadorVale.getInstance().restaurarVale(vale);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(vale, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Vale.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorVale.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Vale.restore.error");
    }

}
