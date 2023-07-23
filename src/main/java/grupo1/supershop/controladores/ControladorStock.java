package grupo1.supershop.controladores;

import grupo1.supershop.beans.Stock;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtStock;
import grupo1.supershop.interfaces.controladores.IControladorStock;
import grupo1.supershop.persistencia.manejadores.ManejadorStock;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorStock;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorStock implements IControladorStock {

    @Override
    public DtStock obtenerStock(Long sucursalId, Long productoId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }
        
        Stock stock = ManejadorStock.getInstance().obtenerStock(sucursalId, productoId);
        if (stock == null) {
            return null;
        }
        return (DtStock) stock.getDataType();
    }

    @Override
    public List<DtStock> listarStockSucursal(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }
        
        List<Stock> lista = ManejadorStock.getInstance().listarStock("sucursal.id", sucursalId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtStock> listarStockProducto(Long productoId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }
        
        List<Stock> lista = ManejadorStock.getInstance().listarStock("producto.id", productoId);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtStock> listarSinStock(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }
        
        List<Stock> lista = ManejadorStock.getInstance().listarStock("cantidad", 0);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtStock> listarSinStockSucursal(Long sucursalId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }
        
        List<Stock> lista = ManejadorStock.getInstance().listarStock("sucursal.id", sucursalId, "cantidad", 0);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public List<DtStock> listarSinStockProducto(Long productoId, String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
            return null;
        }
        
        List<Stock> lista = ManejadorStock.getInstance().listarStock("producto.id", productoId, "cantidad", 0);
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public DtMensajeSistema agregarStock(Long sucursalId, Long productoId, Integer cantidad, String secreto) {
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

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //OBTIENE STOCK
            boolean crear = false;
            Stock stock = ManejadorStock.getInstance().obtenerStock(sucursalId, productoId);
            if (stock == null) {
                crear = true;
                stock = new Stock();
                stock.setSucursal(sucursal);
                stock.setProducto(producto);
                stock.setCantidad(0);
            }

            stock.agregarCantidad(cantidad);

            //VALIDA STOCK
            DtMensajeSistema dtMensajeSistema = ValidadorStock.getInstance().validarAgregarStock(stock, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA STOCK O LO CREA SI NO EXISTE
            if (crear) {
                ManejadorStock.getInstance().crearStock(stock);
            } else {
                ManejadorStock.getInstance().modificarStock(stock);
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Stock.add.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Stock.add.error");
    }

    @Override
    public DtMensajeSistema quitarStock(Long sucursalId, Long productoId, Integer cantidad, String secreto) {
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

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //OBTIENE STOCK
            Stock stock = ManejadorStock.getInstance().obtenerStock(sucursalId, productoId);
            if (stock == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Stock.notexists.error");
            }
            if (stock.getCantidad() == 0) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Stock.remove.error");
            }

            stock.quitarCantidad(cantidad);

            //VALIDA STOCK
            DtMensajeSistema dtMensajeSistema = ValidadorStock.getInstance().validarQuitarStock(stock, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA STOCK
            ManejadorStock.getInstance().modificarStock(stock);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Stock.remove.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Stock.remove.error");
    }

    @Override
    public DtMensajeSistema modificarStock(Long sucursalId, Long productoId, Integer cantidad, String secreto) {
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

            //OBTIENE SUCURSAL
            Sucursal sucursal = ManejadorSucursal.getInstance().obtenerSucursal(sucursalId);
            if (sucursal == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sucursal.notexists.error");
            }

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //OBTIENE STOCK
            Stock stock = ManejadorStock.getInstance().obtenerStock(sucursalId, productoId);
            if (stock == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Stock.notexists.error");
            }

            stock.modificarCantidad(cantidad);

            //VALIDA STOCK
            DtMensajeSistema dtMensajeSistema = ValidadorStock.getInstance().validarModificarStock(stock, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA STOCK
            ManejadorStock.getInstance().modificarStock(stock);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Stock.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorStock.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Stock.update.error");
    }

}
