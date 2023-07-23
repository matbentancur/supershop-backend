package grupo1.supershop.controladores;

import grupo1.supershop.beans.AdministradorSistema;
import grupo1.supershop.beans.AdministradorSucursal;
import grupo1.supershop.beans.AtencionCliente;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.UsuarioSucursal;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtAdministradorSistema;
import grupo1.supershop.datatypes.DtAdministradorSucursal;
import grupo1.supershop.datatypes.DtAtencionCliente;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtMensajePush;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtUsuario;
import grupo1.supershop.datatypes.DtUsuarioSucursal;
import grupo1.supershop.interfaces.controladores.IControladorUsuario;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorUsuario;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioNotificacionPush;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioServidorCorreo;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.servicios.ServicioUsuario;
import grupo1.supershop.validadores.ValidadorUsuario;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorUsuario implements IControladorUsuario {

    @Override
    public DtUsuario obtenerUsuario(Long usuarioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        Usuario usuarioBuscado = ManejadorUsuario.getInstance().obtenerUsuario(usuarioId);
        if (usuarioBuscado == null) {
            return null;
        }
        return (DtUsuario) usuarioBuscado.getDataType();
    }

    @Override
    public String obtenerPerfilUsuario(Long usuarioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        Usuario usuarioBuscado = ManejadorUsuario.getInstance().obtenerUsuario(usuarioId);
        if (usuarioBuscado == null) {
            return null;
        }
        return ServicioUsuario.getInstance().obtenerPerfil(usuarioBuscado);
    }

    @Override
    public DtUsuarioSucursal obtenerUsuarioSucursal(Long usuarioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        UsuarioSucursal usuarioSucursal = ManejadorUsuario.getInstance().obtenerUsuarioSucursal(usuarioId);
        if (usuarioSucursal == null) {
            return null;
        }
        return (DtUsuarioSucursal) usuarioSucursal.getDataType();
    }

    @Override
    public DtUsuario obtenerMisDatos(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        return (DtUsuario) usuario.getMisDatos();
    }

    @Override
    public DtSucursal obtenerMiSucursal(String secreto) {
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
        if (usuarioSucursal == null) {
            return null;
        }

        return (DtSucursal) usuarioSucursal.getSucursal().getDataType();
    }

    @Override
    public List<DtAdministradorSistema> listarAdministradoresSistema(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<AdministradorSistema> lista = ManejadorUsuario.getInstance().listarAdministradoresSistema("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtAdministradorSistema> listarAdministradoresSistemaBorrados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<AdministradorSistema> lista = ManejadorUsuario.getInstance().listarAdministradoresSistema("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtAdministradorSucursal> listarAdministradoresSucursal(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<AdministradorSucursal> lista = ManejadorUsuario.getInstance().listarAdministradoresSucursal("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtAdministradorSucursal> listarAdministradoresSucursalBorrados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<AdministradorSucursal> lista = ManejadorUsuario.getInstance().listarAdministradoresSucursal("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtAtencionCliente> listarAtencionCliente(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<AtencionCliente> lista = ManejadorUsuario.getInstance().listarAtencionCliente("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtAtencionCliente> listarAtencionClienteBorrados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        List<AtencionCliente> lista = ManejadorUsuario.getInstance().listarAtencionCliente("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtComprador> listarCompradores(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Comprador> lista = ManejadorUsuario.getInstance().listarCompradores("borrado", false);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtComprador> listarCompradoresBorrados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Comprador> lista = ManejadorUsuario.getInstance().listarCompradores("borrado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtComprador> listarCompradoresBloqueados(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Comprador> lista = ManejadorUsuario.getInstance().listarCompradores("bloqueado", true);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtActividad> listarActividadUsuario(Long usuarioId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradorSistema(usuario)) {
            return null;
        }

        //OBTIENE USUARIO
        Usuario u = ManejadorUsuario.getInstance().obtenerUsuario(usuarioId);
        if (u == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(u.getActividades());
    }

    @Override
    public DtMensajeSistema registrarComprador(DtComprador dtComprador) {
        try {
            //OBTIENE COMPRADOR
            Comprador comprador = new Comprador(dtComprador);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarRegistrarComprador(comprador);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA
            comprador.cifrarPassword();
            ManejadorUsuario.getInstance().crearUsuario(comprador);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(comprador, comprador);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.create.error");
        }
    }

    //TODO
    @Override
    public DtMensajeSistema crearAdministradorSistema(DtAdministradorSistema dtAdministradorSistema, String secreto) {
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

            //OBTIENE ADMINISTRADOR SISTEMA
            AdministradorSistema administradorSistema = new AdministradorSistema(dtAdministradorSistema);
            administradorSistema.setRandomPassword();

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarCrear(administradorSistema, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA
            String password = administradorSistema.getPassword();
            administradorSistema.cifrarPassword();
            ManejadorUsuario.getInstance().crearUsuario(administradorSistema);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(administradorSistema, usuario);

            //ENVIA CORREO ELECTRONICO CON PASSWORD
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoNuevoUsuario(administradorSistema.getEmail(), password);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.create.error");
        }

    }

    @Override
    public DtMensajeSistema crearAdministradorSucursal(DtAdministradorSucursal dtAdministradorSucursal, Long sucursalId, String secreto) {
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

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE ADMINISTRADOR SUCURSAL
            AdministradorSucursal administradorSucursal = new AdministradorSucursal(dtAdministradorSucursal);
            administradorSucursal.setRandomPassword();
            administradorSucursal.setSucursal(sucursal);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarCrearUsuarioSucursal(administradorSucursal, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA
            String password = administradorSucursal.getPassword();
            administradorSucursal.cifrarPassword();
            ManejadorUsuario.getInstance().crearUsuario(administradorSucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(administradorSucursal, usuario);

            //ENVIA CORREO ELECTRONICO CON PASSWORD
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoNuevoUsuario(administradorSucursal.getEmail(), password);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.create.error");
        }

    }

    @Override
    public DtMensajeSistema crearAtencionCliente(DtAtencionCliente dtAtencionCliente, Long sucursalId, String secreto) {
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

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE ATENCION CLIENTE
            AtencionCliente atencionCliente = new AtencionCliente(dtAtencionCliente);
            atencionCliente.setRandomPassword();
            atencionCliente.setSucursal(sucursal);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarCrearUsuarioSucursal(atencionCliente, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA
            String password = atencionCliente.getPassword();
            atencionCliente.cifrarPassword();
            ManejadorUsuario.getInstance().crearUsuario(atencionCliente);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(atencionCliente, usuario);

            //ENVIA CORREO ELECTRONICO CON PASSWORD
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoNuevoUsuario(atencionCliente.getEmail(), password);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.create.error");
        }

    }

    @Override
    public DtMensajeSistema modificarUsuario(DtUsuario dtUsuario, String secreto) {
        try {
            //OBTIENE ADMINISTRADOR DEL SISTEMA
            AdministradorSistema administradorSistema = (AdministradorSistema) ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (administradorSistema == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(administradorSistema)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE USUARIO
            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario(dtUsuario.getId());
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            usuario.modificar(dtUsuario);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarModificar(usuario, administradorSistema);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorUsuario.getInstance().modificarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(usuario, administradorSistema);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.update.error");
        }

    }

    @Override
    public DtMensajeSistema modificarUsuarioSucursal(DtUsuarioSucursal dtUsuarioSucursal, String secreto) {
        try {
            //OBTIENE ADMINISTRADOR DEL SISTEMA
            AdministradorSistema administradorSistema = (AdministradorSistema) ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (administradorSistema == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(administradorSistema)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE USUARIO SUCURSAL
            UsuarioSucursal usuarioSucursal = ManejadorUsuario.getInstance().obtenerUsuarioSucursal(dtUsuarioSucursal.getId());
            if (usuarioSucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            usuarioSucursal.modificar(dtUsuarioSucursal);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarModificarUsuarioSucursal(usuarioSucursal, administradorSistema);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorUsuario.getInstance().modificarUsuario(usuarioSucursal);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(usuarioSucursal, administradorSistema);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.update.error");
        }

    }

    @Override
    public DtMensajeSistema borrarMiUsuario(String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarBorrarMiUsuario(usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA
            ManejadorUsuario.getInstance().borrarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(usuario, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.delete.error");
        }

    }

    @Override
    public DtMensajeSistema borrarUsuario(Long usuarioId, String secreto) {
        try {
            //OBTIENE ADMINISTRADOR DEL SISTEMA
            AdministradorSistema administradorSistema = (AdministradorSistema) ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (administradorSistema == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(administradorSistema)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE USUARIO
            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario(usuarioId);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarBorrar(usuario, administradorSistema);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BORRA
            ManejadorUsuario.getInstance().borrarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(usuario, administradorSistema);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.delete.error");
        }

    }

    @Override
    public DtMensajeSistema restaurarUsuario(Long usuarioId, String secreto) {
        try {
            //OBTIENE ADMINISTRADOR DEL SISTEMA
            AdministradorSistema administradorSistema = (AdministradorSistema) ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (administradorSistema == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(administradorSistema)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE USUARIO
            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario(usuarioId);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarRestaurar(usuario, administradorSistema);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //RESTAURA
            ManejadorUsuario.getInstance().restaurarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRestauracion(usuario, administradorSistema);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.restore.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.restore.error");
        }

    }

    @Override
    public DtMensajeSistema bloquearUsuario(Long usuarioId, String secreto) {
        try {
            //OBTIENE ADMINISTRADOR DEL SISTEMA
            AdministradorSistema administradorSistema = (AdministradorSistema) ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (administradorSistema == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(administradorSistema)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE USUARIO
            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario(usuarioId);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarBloquear(usuario, administradorSistema);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BLOQUEA
            usuario.setBloqueado(true);
            ManejadorUsuario.getInstance().modificarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarBloqueo(usuario, administradorSistema);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.disable.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.disable.error");
        }

    }

    @Override
    public DtMensajeSistema desbloquearUsuario(Long usuarioId, String secreto) {
        try {
            //OBTIENE ADMINISTRADOR DEL SISTEMA
            AdministradorSistema administradorSistema = (AdministradorSistema) ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (administradorSistema == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSistema(administradorSistema)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE USUARIO
            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario(usuarioId);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarDesbloquear(usuario, administradorSistema);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //BLOQUEA
            usuario.setBloqueado(false);
            ManejadorUsuario.getInstance().modificarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarDesbloqueo(usuario, administradorSistema);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.enable.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.enable.error");
        }

    }

    @Override
    public DtMensajeSistema modificarMisDatos(DtUsuario dtUsuario, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            usuario.modificar(dtUsuario);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarModificarMisDatos(usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorUsuario.getInstance().modificarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(usuario, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.update.error");
        }

    }

    @Override
    public DtMensajeSistema modificarMiClave(String passwordActual, String passwordNuevo, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarModificarMiClave(passwordActual, passwordNuevo, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            usuario.setPassword(passwordNuevo);
            usuario.cifrarPassword();
            ManejadorUsuario.getInstance().modificarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(usuario, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.update.error");
        }

    }

    @Override
    public DtMensajeSistema recuperarPassword(String email) {
        try {
            Usuario usuario = ManejadorUsuario.getInstance().obtenerUsuario("email", email);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.notexists.error");
            }

            //MODIFICA
            usuario.setRandomPassword();
            String password = usuario.getPassword();
            usuario.cifrarPassword();
            ManejadorUsuario.getInstance().modificarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarRecuperacionContraseña(usuario);

            //ENVIA CORREO ELECTRONICO CON PASSWORD
            DtMensajeSistema dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoRecuperacionContraseña(usuario.getEmail(), password);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.rememberPassword.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.rememberPassword.error");
        }

    }

    @Override
    public DtMensajeSistema modificarPushToken(String token, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return null;
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().validarModificarPushToken(usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            usuario.setPushToken(token);
            ManejadorUsuario.getInstance().modificarUsuario(usuario);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarEliminacion(usuario, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.update.pushToken.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.update.pushToken.error");
        }
    }

    @Override
    public DtMensajeSistema enviarNotificacionPush(DtMensajePush dtMensajePush, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("PushNotification.send.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("PushNotification.send.error");
            }

            //VERIFICA QUE TENGA UN TOKEN
            if (usuario.getPushToken() == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("PushNotification.send.error");
            }

            //ENVIA NOTIFICACION PUSH
            DtMensajeSistema dtMensajeSistema = ServicioNotificacionPush.getInstance().notificar(dtMensajePush, usuario.getPushToken());
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("PushNotification.send.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorUsuario.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("PushNotification.send.error");
        }

    }

}
