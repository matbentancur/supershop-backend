package grupo1.supershop.controladores;

import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Reclamo;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtReclamo;
import grupo1.supershop.enums.EstadoReclamo;
import grupo1.supershop.interfaces.controladores.IControladorReclamo;
import grupo1.supershop.persistencia.manejadores.ManejadorReclamo;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.servicios.ServicioUsuario;
import grupo1.supershop.validadores.ValidadorReclamo;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorReclamo implements IControladorReclamo {

    @Override
    public DtReclamo obtenerReclamo(Long reclamoId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursalComprador(usuario)) {
            return null;
        }

        Reclamo reclamo = ManejadorReclamo.getInstance().obtenerReclamo(reclamoId);
        if (reclamo == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        if (!ServicioUsuario.getInstance().esPropietarioReclamo(usuario, reclamo)) {
            return null;
        }

        return (DtReclamo) reclamo.getDataType();
    }

    @Override
    public List<DtReclamo> listarReclamos(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<Reclamo> lista = ManejadorReclamo.getInstance().listarReclamos();
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtReclamo> listarReclamosComprador(EstadoReclamo estadoReclamo, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Reclamo> lista = ManejadorReclamo.getInstance().listarReclamos("comprador.id", usuario.getId(), "estado", estadoReclamo);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtReclamo> listarReclamosCompradorTodos(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Reclamo> lista = ManejadorReclamo.getInstance().listarReclamos("comprador.id", usuario.getId());
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtReclamo> listarReclamosSucursal(EstadoReclamo estadoReclamo, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoSucursal(usuario)) {
            return null;
        }

        UsuarioSucursal usuarioSucursal = (UsuarioSucursal) usuario;

        List<Reclamo> lista = ManejadorReclamo.getInstance().listarReclamos("sucursal.id", usuarioSucursal.getSucursal().getId(), "estado", estadoReclamo);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadReclamo(Long reclamoId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        //OBTIENE RECLAMO
        Reclamo reclamo = ManejadorReclamo.getInstance().obtenerReclamo(reclamoId);
        if (reclamo == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(reclamo.getActividades());
    }

    @Override
    public DtMensajeSistema crearReclamo(DtReclamo dtReclamo, Long sucursalId, String secreto) {
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

            //OBTIENE RECLAMO
            Reclamo reclamo = new Reclamo(dtReclamo);
            reclamo.setEstado(EstadoReclamo.PENDIENTE);
            reclamo.setComprador((Comprador) usuario);
            reclamo.setSucursal(sucursal);

            //VALIDA RECLAMO
            DtMensajeSistema dtMensajeSistema = ValidadorReclamo.getInstance().validarCrear(reclamo, sucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA RECLAMO
            ManejadorReclamo.getInstance().crearReclamo(reclamo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(reclamo, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Reclamo.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorReclamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Reclamo.create.error");
    }

    @Override
    public DtMensajeSistema confirmarReclamo(Long reclamoId, String secreto) {
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

            //OBTIENE RECLAMO
            Reclamo reclamo = ManejadorReclamo.getInstance().obtenerReclamo(reclamoId);
            if (reclamo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Reclamo.notexists.error");
            }

            //VALIDA RECLAMO
            DtMensajeSistema dtMensajeSistema = ValidadorReclamo.getInstance().validarConfirmar(reclamo, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA RECLAMO
            reclamo.confirmar();
            ManejadorReclamo.getInstance().modificarReclamo(reclamo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarConfirmacion(reclamo, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Reclamo.confirm.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorReclamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Reclamo.confirm.error");
    }

    @Override
    public DtMensajeSistema concluirReclamo(DtReclamo dtReclamo, String secreto) {
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

            //OBTIENE RECLAMO
            Reclamo reclamo = ManejadorReclamo.getInstance().obtenerReclamo(dtReclamo.getId());
            if (reclamo == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Reclamo.notexists.error");
            }

            //VALIDA RECLAMO
            DtMensajeSistema dtMensajeSistema = ValidadorReclamo.getInstance().validarConcluir(reclamo, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA RECLAMO
            reclamo.concluir(dtReclamo);
            ManejadorReclamo.getInstance().modificarReclamo(reclamo);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarConclusion(reclamo, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Reclamo.conclude.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorReclamo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Reclamo.conclude.error");
    }

}
