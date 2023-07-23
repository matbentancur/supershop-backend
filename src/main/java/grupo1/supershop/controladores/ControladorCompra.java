package grupo1.supershop.controladores;

import grupo1.supershop.beans.Actividad;
import grupo1.supershop.beans.Carrito;
import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.CompraDomicilio;
import grupo1.supershop.beans.CompraProducto;
import grupo1.supershop.beans.CompraSucursal;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Domicilio;
import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtCompra;
import grupo1.supershop.datatypes.DtCompraProducto;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.enums.EstadoCompra;
import grupo1.supershop.enums.MetodoPago;
import grupo1.supershop.interfaces.controladores.IControladorCompra;
import grupo1.supershop.persistencia.manejadores.ManejadorCarrito;
import grupo1.supershop.persistencia.manejadores.ManejadorCompra;
import grupo1.supershop.persistencia.manejadores.ManejadorCompraProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorDomicilio;
import grupo1.supershop.persistencia.manejadores.ManejadorMetodoEnvio;
import grupo1.supershop.persistencia.manejadores.ManejadorVale;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioStock;
import grupo1.supershop.servicios.ServicioCompra;
import grupo1.supershop.servicios.ServicioNotificacionPush;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioServidorCorreo;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.servicios.ServicioUsuario;
import grupo1.supershop.validadores.ValidadorCompra;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorCompra implements IControladorCompra {

    @Override
    public DtCompra obtenerCompra(Long compraId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
        if (compra == null) {
            return null;
        }
        return (DtCompra) compra.getDataType();
    }

    @Override
    public DtCompra obtenerMiCompra(Long compraId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
        if (compra == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        Comprador comprador = (Comprador) usuario;
        if (!ServicioUsuario.getInstance().esPropietarioCompra(comprador, compra)) {
            return null;
        }

        return (DtCompra) compra.getDataType();
    }

    @Override
    public DtCompra obtenerMiCompraProceso(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        Compra compra = ManejadorCompra.getInstance().obtenerCompra("comprador.id", usuario.getId(), "estado", EstadoCompra.PROCESO);
        if (compra == null) {
            return null;
        }
        return (DtCompra) compra.getDataType();
    }

    @Override
    public List<DtCompraProducto> listarMiCompraProductos(Long compraId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
        if (compra == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        Comprador comprador = (Comprador) usuario;
        if (!ServicioUsuario.getInstance().esPropietarioCompra(comprador, compra)) {
            return null;
        }

        List<CompraProducto> lista = ManejadorCompraProducto.getInstance().listarCompraProductos("compra.id", compraId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompraProducto> listarCompraProductos(Long compraId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<CompraProducto> lista = ManejadorCompraProducto.getInstance().listarCompraProductos("compra.id", compraId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarMisCompras(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", usuario.getId());
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarMisComprasPendientes(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", usuario.getId(), "estado", EstadoCompra.PENDIENTE);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarMisComprasConfirmadas(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", usuario.getId(), "estado", EstadoCompra.CONFIRMADA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarMisComprasConcluidas(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", usuario.getId(), "estado", EstadoCompra.CONCLUIDA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarMisComprasDevueltas(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", usuario.getId(), "estado", EstadoCompra.DEVUELTA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasUsuario(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", compradorId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasUsuarioProceso(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", compradorId, "estado", EstadoCompra.PROCESO);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasUsuarioPendientes(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", compradorId, "estado", EstadoCompra.PENDIENTE);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasUsuarioConfirmadas(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", compradorId, "estado", EstadoCompra.CONFIRMADA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasUsuarioConcluidas(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", compradorId, "estado", EstadoCompra.CONCLUIDA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasUsuarioDevueltas(Long compradorId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("comprador.id", compradorId, "estado", EstadoCompra.DEVUELTA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasSucursal(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("sucursal.id", sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasSucursalProceso(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("sucursal.id", sucursalId, "estado", EstadoCompra.PROCESO);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasSucursalPendientes(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("sucursal.id", sucursalId, "estado", EstadoCompra.PENDIENTE);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasSucursalConfirmadas(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("sucursal.id", sucursalId, "estado", EstadoCompra.CONFIRMADA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasSucursalConcluidas(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("sucursal.id", sucursalId, "estado", EstadoCompra.CONCLUIDA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtCompra> listarComprasSucursalDevueltas(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Compra> lista = ManejadorCompra.getInstance().listarCompras("sucursal.id", sucursalId, "estado", EstadoCompra.DEVUELTA);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtVale> listarValesAplicados(Long compraId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
        if (compra == null) {
            return null;
        }
        return ServicioDataType.getInstance().beanSetToDataTypeList(compra.getVales());
    }

    @Override
    public List<DtVale> listarMisValesAplicados(Long compraId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
        if (compra == null) {
            return null;
        }

        //VERIFICA PROPIETARIO
        Comprador comprador = (Comprador) usuario;
        if (!ServicioUsuario.getInstance().esPropietarioCompra(comprador, compra)) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(compra.getVales());
    }

    @Override
    public List<DtActividad> listarActividadCompra(Long compraId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }

        //OBTIENE COMPRA
        Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
        if (compra == null) {
            return null;
        }

        return ServicioDataType.getInstance().beanSetToDataTypeList(compra.getActividades());
    }

    @Override
    public DtMensajeSistema iniciarCompraDomicilio(String secreto) {
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

            //OBTIENE CARRITO
            Carrito carrito = ManejadorCarrito.getInstance().obtenerCarrito(usuario.getId());
            if (carrito == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.notexists.error");
            }

            //OBTIENE COMPRA EN PROCESO
            Compra compra = ManejadorCompra.getInstance().obtenerCompra("comprador.id", usuario.getId(), "estado", EstadoCompra.PROCESO);
            if (compra != null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.inProgress.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarIniciarCompra(carrito, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //INICIA LA COMPRA
            CompraDomicilio compraDomicilio = new CompraDomicilio();
            dtMensajeSistema = ServicioCompra.getInstance().iniciarCompraDomicilio(compraDomicilio, carrito);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(compraDomicilio, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.start.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.start.error");
        }
    }

    @Override
    public DtMensajeSistema iniciarCompraSucursal(String secreto) {
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

            //OBTIENE CARRITO
            Carrito carrito = ManejadorCarrito.getInstance().obtenerCarrito(usuario.getId());
            if (carrito == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.notexists.error");
            }

            //OBTIENE COMPRA EN PROCESO
            Compra compra = ManejadorCompra.getInstance().obtenerCompra("comprador.id", usuario.getId(), "estado", EstadoCompra.PROCESO);
            if (compra != null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.inProgress.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarIniciarCompra(carrito, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //INICIA LA COMPRA
            CompraSucursal compraSucursal = new CompraSucursal();
            dtMensajeSistema = ServicioCompra.getInstance().iniciarCompraSucursal(compraSucursal, carrito);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarCreacion(compraSucursal, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.start.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.start.error");
        }
    }

    @Override
    public DtMensajeSistema cancelarCompra(Long compraId, String secreto) {
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

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarCancelar(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CANCELA LA COMPRA
            dtMensajeSistema = ServicioCompra.getInstance().cancelarCompra(compra);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarConfirmacion(compra, usuario);

            //ENVIA CORREO ELECTRONICO
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoCancelacionCompra(usuario.getEmail(), compra);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.cancel.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.cancel.error");
        }
    }

    @Override
    public DtMensajeSistema confirmarCompra(Long compraId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarConfirmar(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            compra.confirmar();
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarConfirmacion(compra, usuario);

            //OBTIENE COMPRADOR
            Comprador comprador = compra.getComprador();

            //ENVIA CORREO ELECTRONICO
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoConfirmacionCompra(comprador.getEmail(), compra);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //ENVIA NOTIFICACION PUSH SI TIENE UN TOKEN
            if (comprador.getPushToken() != null) {
                dtMensajeSistema = ServicioNotificacionPush.getInstance().notificarConfirmacionCompra(comprador.getPushToken(), compra);
                if (!dtMensajeSistema.isExitoso()) {
                    return dtMensajeSistema;
                }
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.confirm.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.confirm.error");
        }
    }

    @Override
    public DtMensajeSistema concluirCompra(Long compraId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarConcluir(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            compra.concluir();
            if (compra instanceof CompraSucursal) {
                compra.setPago(true);
            }
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarConclusion(compra, usuario);

            //OBTIENE COMPRADOR
            Comprador comprador = compra.getComprador();

            //ENVIA CORREO ELECTRONICO
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoConclusionCompra(comprador.getEmail(), compra);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //ENVIA NOTIFICACION PUSH SI TIENE UN TOKEN
            if (comprador.getPushToken() != null) {
                dtMensajeSistema = ServicioNotificacionPush.getInstance().notificarConclusionCompra(comprador.getPushToken(), compra);
                if (!dtMensajeSistema.isExitoso()) {
                    return dtMensajeSistema;
                }
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.conclude.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.conclude.error");
        }
    }

    @Override
    public DtMensajeSistema devolverCompra(Long compraId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradorSucursal(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarDevolver(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA COMPRA
            compra.devolver();
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REPONE EL STOCK
            ServicioStock.getInstance().reponerStock(compra);

            //EMITE UN VALE DE DEVOLUCION
            Vale vale = ServicioCompra.getInstance().emitirValeDevolucion(compra);
            ManejadorVale.getInstance().crearVale(vale);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarConfirmacion(compra, usuario);

            //OBTIENE COMPRADOR
            Comprador comprador = compra.getComprador();

            //ENVIA CORREO ELECTRONICO
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoDevoluciónCompra(comprador.getEmail(), compra, vale);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //ENVIA NOTIFICACION PUSH SI TIENE UN TOKEN
            if (comprador.getPushToken() != null) {
                dtMensajeSistema = ServicioNotificacionPush.getInstance().notificarDevoluciónCompra(comprador.getPushToken(), compra, vale);
                if (!dtMensajeSistema.isExitoso()) {
                    return dtMensajeSistema;
                }
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.return.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.return.error");
        }
    }

    @Override
    public DtMensajeSistema aplicarMisVales(List<DtVale> listaDtVales, Long compraId, String secreto) {
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

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            //OBTIENE LISTA VALES
            Set<Vale> vales = ManejadorVale.getInstance().obtenerListaVales(listaDtVales);
            if (vales == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.vouchers.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarAplicarVales(compra, vales, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //APLICA LOS VALES
            dtMensajeSistema = ServicioCompra.getInstance().aplicarVales(compra, vales);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA COMPRA
            ManejadorCompra.getInstance().modificarCompra(compra);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.vouchers.success");

        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.vouchers.error");
        }
    }

    @Override
    public DtMensajeSistema aplicarMetodoPagoEfectivo(Long compraId, String secreto) {
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

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarAplicarMetodoPago(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            compra.setMetodoPago(MetodoPago.EFECTIVO);
            compra.setPago(false);
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(compra, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.applyPaymentMethod.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.applyPaymentMethod.error");
        }
    }

    @Override
    public DtMensajeSistema aplicarMetodoPagoPayPal(Long compraId, String secreto) {
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

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            compra.setMetodoPago(MetodoPago.PAYPAL);
            compra.setPago(false);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarAplicarMetodoPago(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(compra, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.applyPaymentMethod.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.applyPaymentMethod.error");
        }
    }

    @Override
    public DtMensajeSistema aplicarMetodoEnvio(Long compraId, Long metodoEnvioId, String secreto) {
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

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            if (!(compra instanceof CompraDomicilio)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.home.error");
            }

            //OBTIENE METODO DE ENVIO
            MetodoEnvio metodoEnvio = ManejadorMetodoEnvio.getInstance().obtenerMetodoEnvio(metodoEnvioId);
            if (metodoEnvio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("MetodoEnvio.notexists.error");
            }

            CompraDomicilio compraDomicilio = (CompraDomicilio) compra;
            compraDomicilio.setMetodoEnvio(metodoEnvio);
            compraDomicilio.setMetodoEnvioNombre(metodoEnvio.getNombre());
            compraDomicilio.setMetodoEnvioCosto(metodoEnvio.getCosto());

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarAplicarMetodoEnvio(compra, metodoEnvio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            compraDomicilio.calcularTotal();
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(compra, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.applyShipping.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.applyShipping.error");
        }
    }

    @Override
    public DtMensajeSistema modificarDomicilio(Long compraId, Long domicilioId, String secreto) {
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

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            if (!(compra instanceof CompraDomicilio)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.home.error");
            }

            //OBTIENE DOMICILIO
            Domicilio domicilio = ManejadorDomicilio.getInstance().obtenerDomicilio(domicilioId);
            if (domicilio == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Domicilio.notexists.error");
            }

            CompraDomicilio compraDomicilio = (CompraDomicilio) compra;
            compraDomicilio.setDomicilio(domicilio);
            compraDomicilio.setDomicilioLongitud(domicilio.getLongitud());
            compraDomicilio.setDomicilioLatitud(domicilio.getLatitud());
            compraDomicilio.setDomicilioString(domicilio.describir());

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarModificarDomicilio(compra, domicilio, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(compra, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.update.error");
        }
    }

    @Override
    public DtMensajeSistema aplicarObservacionesComprador(String observaciones, Long compraId, String secreto) {
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

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            compra.setObservacionesCliente(observaciones);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarAplicarObservacionesComprador(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(compra, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.update.error");
        }
    }

    @Override
    public DtMensajeSistema modificarObservacionesSucursal(String observaciones, Long compraId, String secreto) {
        try {
            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            compra.setObservacionesSucursal(observaciones);

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarModificarObservacionesSucursal(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA
            ManejadorCompra.getInstance().modificarCompra(compra);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarModificacion(compra, usuario);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.update.error");
        }
    }

    @Override
    public DtMensajeSistema finalizarCompra(Long compraId, String secreto) {
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

            //OBTIENE CARRITO
            Carrito carrito = ManejadorCarrito.getInstance().obtenerCarrito(usuario.getId());
            if (carrito == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.notexists.error");
            }

            //OBTIENE COMPRA
            Compra compra = ManejadorCompra.getInstance().obtenerCompra(compraId);
            if (compra == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.notexists.error");
            }

            //VALIDA
            DtMensajeSistema dtMensajeSistema = ValidadorCompra.getInstance().validarFinalizar(compra, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //FINALIZA LA COMPRA
            dtMensajeSistema = ServicioCompra.getInstance().finalizarCompra(compra, carrito);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarFinalizacion(compra, usuario);

            //ENVIA CORREO ELECTRONICO
            dtMensajeSistema = ServicioServidorCorreo.getInstance().enviarCorreoFinalizacionCompra(usuario.getEmail(), compra);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.finalize.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.finalize.error");
        }
    }

    @Override
    public List<DtActividad> listarActividadesComprasConcluidasSucursal(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoEmpresa(usuario)) {
            return null;
        }

        List<Actividad> listaActividades = new ArrayList<Actividad>();

        List<Compra> listaCompras = ManejadorCompra.getInstance().listarCompras("sucursal.id", sucursalId, "estado", EstadoCompra.CONCLUIDA);

        if (listaCompras != null) {
            if (!listaCompras.isEmpty()) {
                for (Compra compra : listaCompras) {
                    listaActividades.addAll(compra.getActividades());
                }
            }
        }

        return ServicioDataType.getInstance().beanToDataType(listaActividades);
    }

}
