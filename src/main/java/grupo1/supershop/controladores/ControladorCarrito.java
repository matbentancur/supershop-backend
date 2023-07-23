package grupo1.supershop.controladores;

import grupo1.supershop.beans.Carrito;
import grupo1.supershop.beans.CarritoProducto;
import grupo1.supershop.beans.Comprador;
import grupo1.supershop.beans.Producto;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtCarrito;
import grupo1.supershop.datatypes.DtCarritoProducto;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;
import grupo1.supershop.persistencia.manejadores.ManejadorCarrito;
import grupo1.supershop.persistencia.manejadores.ManejadorCarritoProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import grupo1.supershop.servicios.ServicioActividad;
import grupo1.supershop.servicios.ServicioDataType;
import grupo1.supershop.servicios.ServicioElementoProducto;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioProperties;
import grupo1.supershop.servicios.ServicioSesion;
import grupo1.supershop.validadores.ValidadorCarrito;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControladorCarrito implements IControladorCarrito {

    @Override
    public DtCarrito obtenerCarrito(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        Carrito carrito = ManejadorCarrito.getInstance().obtenerCarrito(usuario.getId());
        if (carrito == null) {
            return null;
        }
        return (DtCarrito) carrito.getDataType();
    }

    @Override
    public List<DtCarritoProducto> listarCarritoProductos(String secreto) {
        //OBTIENE USUARIO
        Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
        if (usuario == null) {
            return null;
        }

        //VERIFICA PERMISO
        if (!ServicioPermiso.getInstance().permisoComprador(usuario)) {
            return null;
        }

        List<CarritoProducto> lista = ManejadorCarritoProducto.getInstance().listarCarritoProductos("carrito.comprador.id", usuario.getId());
        return ServicioDataType.getInstance().beanToDataType(lista);
    }

    @Override
    public DtMensajeSistema crearCarrito(Long sucursalId, Long productoId, Integer cantidad, String secreto) {
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

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //OBTIENE CARRITO
            Carrito carrito = ManejadorCarrito.getInstance().obtenerCarrito(usuario.getId());
            if (carrito != null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.exists.error");
            }

            //OBTIENE CARRITO
            carrito = new Carrito();
            Comprador comprador = (Comprador) usuario;
            carrito.setComprador(comprador);
            carrito.setSucursal(sucursal);
            carrito.setTotal(producto.getPrecio().multiply(BigDecimal.valueOf(cantidad)));

            //VALIDA CARRITO
            DtMensajeSistema dtMensajeSistema = ValidadorCarrito.getInstance().validarCrear(carrito, producto, cantidad, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA CARRITO PRODUCTO
            CarritoProducto carritoProducto = new CarritoProducto();
            carritoProducto.setCarrito(carrito);
            carritoProducto.setProducto(producto);
            carritoProducto.setProductoNombre(producto.getNombre());
            carritoProducto.setPrecioUnitario(producto.getPrecio());
            carritoProducto.setCantidad(0);
            carritoProducto.agregarCantidad(cantidad);

            //APLICAR PROMOCION
            ServicioElementoProducto.getInstance().aplicarPromocionVigente(sucursal, carritoProducto);

            //VALIDA CARRITO PRODUCTO
            dtMensajeSistema = ValidadorCarrito.getInstance().validarCarritoProducto(carritoProducto);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //CREA
            ManejadorCarrito.getInstance().crearCarrito(carrito);
            ManejadorCarritoProducto.getInstance().crearCarritoProducto(carritoProducto);
            carrito.calcularTotal();
            ManejadorCarrito.getInstance().modificarCarrito(carrito);

            //REGISTRA ACTIVIDAD
            ServicioActividad.getInstance().registrarActvidadUsuario(usuario, ServicioProperties.getInstance().getMensajeProperties("Carrito.create.success"));

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Carrito.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCarrito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.create.error");
    }

    @Override
    public DtMensajeSistema borrarCarrito(String secreto) {
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

            // OBTIENE PRODUCTOS DEL CARRITO
            List<CarritoProducto> listaCarritoProductos = ManejadorCarritoProducto.getInstance().listarCarritoProductos("carrito.comprador.id", usuario.getId());

            //BORRA PRODUCTOS DEL CARRITO
            ManejadorCarrito.getInstance().borrarProductosCarrito(listaCarritoProductos);

            //BORRA CATEGORIA
            ManejadorCarrito.getInstance().borrarCarrito(carrito);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Carrito.delete.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCategoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.delete.error");
    }

    @Override
    public DtMensajeSistema agregarProducto(Long productoId, Integer cantidad, String secreto) {
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

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA CARRITO
            DtMensajeSistema dtMensajeSistema = ValidadorCarrito.getInstance().validarAgregarProducto(carrito, producto, cantidad, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //AGREGA PRODUCTO
            CarritoProducto carritoProducto = ManejadorCarritoProducto.getInstance().obtenerCarritoProducto(carrito.getComprador().getId(), productoId);
            if (carritoProducto == null) {
                carritoProducto = new CarritoProducto();
                carritoProducto.setCarrito(carrito);
                carritoProducto.setProducto(producto);
                carritoProducto.setProductoNombre(producto.getNombre());
                carritoProducto.setPrecioUnitario(producto.getPrecio());
                carritoProducto.setCantidad(0);
                carritoProducto.agregarCantidad(cantidad);

                //APLICAR PROMOCION
                ServicioElementoProducto.getInstance().aplicarPromocionVigente(carrito.getSucursal(), carritoProducto);

                //VALIDA CARRITO PRODUCTO
                dtMensajeSistema = ValidadorCarrito.getInstance().validarCarritoProducto(carritoProducto);
                if (!dtMensajeSistema.isExitoso()) {
                    return dtMensajeSistema;
                }
                ManejadorCarritoProducto.getInstance().crearCarritoProducto(carritoProducto);
            } else {
                carritoProducto.agregarCantidad(cantidad);

                //APLICAR PROMOCION
                ServicioElementoProducto.getInstance().aplicarPromocionVigente(carrito.getSucursal(), carritoProducto);

                //VALIDA CARRITO PRODUCTO
                dtMensajeSistema = ValidadorCarrito.getInstance().validarCarritoProducto(carritoProducto);
                if (!dtMensajeSistema.isExitoso()) {
                    return dtMensajeSistema;
                }
                ManejadorCarritoProducto.getInstance().modificarCarritoProducto(carritoProducto);
            }

            //MODIFICA CARRITO TOTAL
            carrito.calcularTotal();
            ManejadorCarrito.getInstance().modificarCarrito(carrito);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Carrito.add.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCarrito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.add.error");
    }

    @Override
    public DtMensajeSistema quitarProducto(Long productoId, Integer cantidad, String secreto) {
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

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA CARRITO
            DtMensajeSistema dtMensajeSistema = ValidadorCarrito.getInstance().validarQuitarProducto(carrito, producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //QUITA PRODUCTO
            CarritoProducto carritoProducto = ManejadorCarritoProducto.getInstance().obtenerCarritoProducto(carrito.getComprador().getId(), productoId);
            if (carritoProducto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            carritoProducto.quitarCantidad(cantidad);
            if (carritoProducto.getCantidad() == 0) {
                ManejadorCarritoProducto.getInstance().borrarCarritoProducto(carritoProducto);
            } else {
                //APLICAR PROMOCION
                ServicioElementoProducto.getInstance().aplicarPromocionVigente(carrito.getSucursal(), carritoProducto);

                //VALIDA CARRITO PRODUCTO
                dtMensajeSistema = ValidadorCarrito.getInstance().validarCarritoProducto(carritoProducto);
                if (!dtMensajeSistema.isExitoso()) {
                    return dtMensajeSistema;
                }
                ManejadorCarritoProducto.getInstance().modificarCarritoProducto(carritoProducto);
            }

            //MODIFICA CARRITO TOTAL
            carrito.calcularTotal();
            if (carrito.getTotal().compareTo(BigDecimal.ZERO) == 0) {
                ManejadorCarrito.getInstance().borrarCarrito(carrito);
            } else {
                ManejadorCarrito.getInstance().modificarCarrito(carrito);
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Carrito.remove.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCarrito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.remove.error");
    }

    @Override
    public DtMensajeSistema modificarCantidadProducto(Long productoId, Integer cantidad, String secreto) {
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

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA CARRITO
            DtMensajeSistema dtMensajeSistema = ValidadorCarrito.getInstance().validarModificarCantidadProducto(carrito, producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //MODIFICA CANTIDAD PRODUCTO
            CarritoProducto carritoProducto = ManejadorCarritoProducto.getInstance().obtenerCarritoProducto(carrito.getComprador().getId(), productoId);
            if (carritoProducto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            carritoProducto.modificarCantidad(cantidad);
            if (carritoProducto.getCantidad() == 0) {
                ManejadorCarritoProducto.getInstance().borrarCarritoProducto(carritoProducto);
            } else {
                //APLICAR PROMOCION
                ServicioElementoProducto.getInstance().aplicarPromocionVigente(carrito.getSucursal(), carritoProducto);

                //VALIDA CARRITO PRODUCTO
                dtMensajeSistema = ValidadorCarrito.getInstance().validarCarritoProducto(carritoProducto);
                if (!dtMensajeSistema.isExitoso()) {
                    return dtMensajeSistema;
                }
                ManejadorCarritoProducto.getInstance().modificarCarritoProducto(carritoProducto);
            }

            //MODIFICA CARRITO TOTAL
            carrito.calcularTotal();
            if (carritoProducto.getCantidad() == 0) {
                ManejadorCarrito.getInstance().borrarCarrito(carrito);
            } else {
                ManejadorCarrito.getInstance().modificarCarrito(carrito);
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Carrito.update.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCarrito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.update.error");
    }

    @Override
    public DtMensajeSistema borrarCarritoProducto(Long productoId, String secreto) {
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

            //OBTIENE PRODUCTO
            Producto producto = ManejadorProducto.getInstance().obtenerProducto(productoId);
            if (producto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //VALIDA CARRITO
            DtMensajeSistema dtMensajeSistema = ValidadorCarrito.getInstance().validarQuitarProducto(carrito, producto, usuario);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            //QUITA PRODUCTO
            CarritoProducto carritoProducto = ManejadorCarritoProducto.getInstance().obtenerCarritoProducto(carrito.getComprador().getId(), productoId);
            if (carritoProducto == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Producto.notexists.error");
            }

            //BORRA PRODUCTO DEL CARRITO
            ManejadorCarritoProducto.getInstance().borrarCarritoProducto(carritoProducto);

            //MODIFICA CARRITO TOTAL
            carrito.calcularTotal();
            if (carrito.getTotal().compareTo(BigDecimal.ZERO) == 0) {
                ManejadorCarrito.getInstance().borrarCarrito(carrito);
            } else {
                ManejadorCarrito.getInstance().modificarCarrito(carrito);
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Carrito.remove.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorCarrito.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Carrito.remove.error");
    }

}
