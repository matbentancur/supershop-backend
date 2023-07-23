package grupo1.supershop.servicios;

import grupo1.supershop.beans.Carrito;
import grupo1.supershop.beans.CarritoProducto;
import grupo1.supershop.beans.Compra;
import grupo1.supershop.beans.CompraDomicilio;
import grupo1.supershop.beans.CompraProducto;
import grupo1.supershop.beans.CompraSucursal;
import grupo1.supershop.beans.MetodoEnvio;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.beans.Vale;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.enums.EstadoCompra;
import grupo1.supershop.enums.EstadoVale;
import grupo1.supershop.persistencia.manejadores.ManejadorCarrito;
import grupo1.supershop.persistencia.manejadores.ManejadorCarritoProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorCompra;
import grupo1.supershop.persistencia.manejadores.ManejadorCompraProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorVale;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.DateUtils;

public class ServicioCompra {

    private static ServicioCompra instance = null;

    private ServicioCompra() {
    }

    public static ServicioCompra getInstance() {
        if (instance == null) {
            instance = new ServicioCompra();
        }
        return instance;
    }

    public DtMensajeSistema aplicarVales(Compra compra, Set<Vale> vales) {
        try {
            for (Vale vale : vales) {
                compra.aplicarVale(vale);
                vale.setEstado(EstadoVale.PENDIENTE);
                ManejadorVale.getInstance().modificarVale(vale);
            }
            compra.calcularTotal();
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.vouchers.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.vouchers.error");
        }
    }

    public Vale emitirValeDevolucion(Compra compra) {
        Vale vale = new Vale();
        vale.setBorrado(false);
        vale.setEstado(EstadoVale.DISPONIBLE);
        vale.setDescripcion("Vale por devolución de compra ID: " + compra.getId());
        vale.setMonto(compra.getMontoDevolucion());

        Date fechaActual = new Date();
        Date fechaExpira = DateUtils.addDays(fechaActual, 30);

        vale.setExpira(fechaExpira);
        vale.setComprador(compra.getComprador());
        vale.setSucursal(compra.getSucursal());

        return vale;
    }

    public DtMensajeSistema iniciarCompraSucursal(CompraSucursal compraSucursal, Carrito carrito) {
        DtMensajeSistema dtMensajeSistema = this.iniciarCompra(compraSucursal, carrito);
        return dtMensajeSistema;
    }

    public DtMensajeSistema iniciarCompraDomicilio(CompraDomicilio compraDomicilio, Carrito carrito) {
        DtMensajeSistema dtMensajeSistema = this.iniciarCompra(compraDomicilio, carrito);
        return dtMensajeSistema;
    }

    public DtMensajeSistema cancelarCompra(Compra compra) {
        try {
            if (compra.getEstado().equals(EstadoCompra.PENDIENTE)) {
                //REPONE EL STOCK
                ServicioStock.getInstance().reponerStock(compra);
            }

            if (compra.isPago()) {
                //EMITE UN VALE DE DEVOLUCION
                Vale vale = ServicioCompra.getInstance().emitirValeDevolucion(compra);
                ManejadorVale.getInstance().crearVale(vale);
            } else {
                //CAMBIA EL ESTADO DE LOS VALES
                for (Vale vale : compra.getVales()) {
                    vale.setEstado(EstadoVale.DISPONIBLE);
                    ManejadorVale.getInstance().modificarVale(vale);
                }
            }

            //MODIFICA
            compra.cancelar();
            ManejadorCompra.getInstance().modificarCompra(compra);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.cancel.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.cancel.error");
        }

    }

    public DtMensajeSistema finalizarCompra(Compra compra, Carrito carrito) {
        try {
            //BORRA EL CARRITO
            ManejadorCarrito.getInstance().vaciarCarrito(carrito);
            ManejadorCarrito.getInstance().borrarCarrito(carrito);

            //DESCONTAR EL STOCK
            ServicioStock.getInstance().descontarStock(compra);

            //MARCAR VALES COMO USADOS
            for (Vale vale : compra.getVales()) {
                vale.setEstado(EstadoVale.USADO);
                ManejadorVale.getInstance().modificarVale(vale);
            }

            //MODIFICA
            compra.finalizar();
            ManejadorCompra.getInstance().modificarCompra(compra);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.finalize.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioCompra.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.finalize.error");
        }
    }

    private DtMensajeSistema iniciarCompra(Compra compra, Carrito carrito) {
        try {
            compra.setEstado(EstadoCompra.PROCESO);
            compra.setPago(false);
            compra.setComprador(carrito.getComprador());
            compra.setSucursal(carrito.getSucursal());
            compra.setCostoCompra(carrito.getTotal());
            compra.setDescuentoVales(BigDecimal.ZERO);
            compra.setDescuentoPromociones(BigDecimal.ZERO);
            compra.setTotal(BigDecimal.ZERO);

            if (compra instanceof CompraDomicilio) {
                CompraDomicilio compraDomicilio = (CompraDomicilio) compra;
                compraDomicilio.setMetodoEnvioCosto(BigDecimal.ZERO);
            }

            compra.calcularTotal();
            ManejadorCompra.getInstance().crearCompra(compra);

            DtMensajeSistema dtMensajeSistema = this.cargarProductos(compra, carrito);
            if (!dtMensajeSistema.isExitoso()) {
                ManejadorCompra.getInstance().vaciarCompra(compra);
                ManejadorCompra.getInstance().borrarCompra(compra);
                return dtMensajeSistema;
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.create.success");

        } catch (Exception ex) {
            Logger.getLogger(ServicioCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Compra.create.error");
    }

    private void ajustarProductosCarrito(Carrito carrito) {
        List<CarritoProducto> listaCarritoProducto = ManejadorCarritoProducto.getInstance().listarCarritoProductos("carrito.comprador.id", carrito.getComprador().getId());

        if (listaCarritoProducto != null) {
            ServicioElementoProducto.getInstance().ajustarListaElementosProducto(carrito.getSucursal(), listaCarritoProducto);
        }
    }

    private DtMensajeSistema cargarProductos(Compra compra, Carrito carrito) {
        try {
            this.ajustarProductosCarrito(carrito);
            List<CarritoProducto> listaCarritoProducto = ManejadorCarritoProducto.getInstance().listarCarritoProductos("carrito.comprador.id", carrito.getComprador().getId());

            for (CarritoProducto carritoProducto : listaCarritoProducto) {
                CompraProducto compraProducto = new CompraProducto(compra, carritoProducto);
                ManejadorCompraProducto.getInstance().crearCompraProducto(compraProducto);
                if (compraProducto.getDescuentoPromocion() != null) {
                    compra.setDescuentoPromociones(compra.getDescuentoPromociones().add(compraProducto.getDescuentoPromocion()));
                }
            }

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.addProducts.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Compra.addProducts.error");

    }

    public boolean verificarMontoValesCompra(Compra compra, Set<Vale> conjuntoVales) {
        BigDecimal montoVales = BigDecimal.ZERO;

        for (Vale vale : conjuntoVales) {
            montoVales = montoVales.add(vale.getMonto());
        }

        if (montoVales.compareTo(compra.getCostoCompra()) < 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean verificarPesoCompra(Compra compra, MetodoEnvio metodoEnvio) {
        List<CompraProducto> listaCompraProducto = ManejadorCompraProducto.getInstance().listarCompraProductos("compra.id", compra.getId());

        Integer pesoCompra = 0;

        for (CompraProducto compraProducto : listaCompraProducto) {
            pesoCompra = pesoCompra + (compraProducto.getProducto().getPeso() * compraProducto.getCantidad());
        }

        if (pesoCompra <= metodoEnvio.getPesoMaximo()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean verificarCantidadProductos(Compra compra, MetodoEnvio metodoEnvio) {
        List<CompraProducto> listaCompraProducto = ManejadorCompraProducto.getInstance().listarCompraProductos("compra.id", compra.getId());

        Integer cantidadProductosCompra = 0;

        for (CompraProducto compraProducto : listaCompraProducto) {
            cantidadProductosCompra = cantidadProductosCompra + compraProducto.getCantidad();
        }

        if (cantidadProductosCompra <= metodoEnvio.getCantidadProductosMaximo()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarMinimoCompra(Carrito carrito) {
        List<CarritoProducto> listaCarritoProducto = ManejadorCarritoProducto.getInstance().listarCarritoProductos("carrito.comprador.id", carrito.getComprador().getId());

        BigDecimal costoCompra = BigDecimal.ZERO;

        for (CarritoProducto carritoProducto : listaCarritoProducto) {
            costoCompra = costoCompra.add(carritoProducto.getSubtotal());
        }

        if (costoCompra.compareTo(BigDecimal.valueOf(500L)) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarComprasPendientes(Usuario usuario) {
        List<Compra> listaCompras = ManejadorCompra.getInstance().listarCompras("comprador.id", usuario.getId(), "estado", EstadoCompra.PENDIENTE);

        if (listaCompras.size() < 5) {
            return true;
        } else {
            return false;
        }
    }

}
