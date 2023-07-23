package grupo1.supershop.servicios;

import grupo1.supershop.beans.ElementoProducto;
import grupo1.supershop.beans.Promocion;
import grupo1.supershop.beans.Stock;
import grupo1.supershop.beans.Sucursal;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.persistencia.manejadores.ManejadorElementoProducto;
import grupo1.supershop.persistencia.manejadores.ManejadorPromocion;
import grupo1.supershop.persistencia.manejadores.ManejadorStock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioElementoProducto {

    private static ServicioElementoProducto instance = null;

    private ServicioElementoProducto() {
    }

    public static ServicioElementoProducto getInstance() {
        if (instance == null) {
            instance = new ServicioElementoProducto();
        }
        return instance;
    }

    private boolean ajustarProductosBorrados(ElementoProducto elementoProducto) {
        try {
            if (elementoProducto.getProducto().isBorrado()) {
                ManejadorElementoProducto.getInstance().borrarElementoProducto(elementoProducto);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioElementoProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    private boolean ajustarPrecio(ElementoProducto elementoProducto) {
        try {
            if (!elementoProducto.getProducto().getPrecio().equals(elementoProducto.getPrecioUnitario())) {
                elementoProducto.setPrecioUnitario(elementoProducto.getProducto().getPrecio());
                elementoProducto.calcularSubtotal();
                ManejadorElementoProducto.getInstance().modificarElementoProducto(elementoProducto);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioElementoProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean ajustarStock(Sucursal sucursal, ElementoProducto elementoProducto) {
        try {
            Stock stock = ManejadorStock.getInstance().obtenerStock(sucursal.getId(), elementoProducto.getProducto().getId());
            if (stock != null) {
                if (stock.getCantidad() < elementoProducto.getCantidad()) {
                    elementoProducto.setCantidad(stock.getCantidad());
                    ManejadorElementoProducto.getInstance().modificarElementoProducto(elementoProducto);
                    return true;
                }
            } else {
                ManejadorElementoProducto.getInstance().borrarElementoProducto(elementoProducto);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioElementoProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean ajustarPromocion(Sucursal sucursal, ElementoProducto elementoProducto) {
        try {
            if (this.aplicarPromocionVigente(sucursal, elementoProducto)) {
                ManejadorElementoProducto.getInstance().modificarElementoProducto(elementoProducto);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioElementoProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean aplicarPromocionVigente(Sucursal sucursal, ElementoProducto elementoProducto) {
        try {
            List<Promocion> listaPromociones = ManejadorPromocion.getInstance().listarPromocionesVigentesProducto(sucursal.getId(), elementoProducto.getProducto().getId());

            Promocion promocionAplicar = this.closestPromocion(elementoProducto.getCantidad(), listaPromociones);

            if (promocionAplicar != null) {
                elementoProducto.setPromocion(promocionAplicar);
                elementoProducto.setPromocionNombre(promocionAplicar.getNombre());
                elementoProducto.setDescuentoPorcentual(promocionAplicar.getDescuentoPorcentual());
                elementoProducto.calcularDescuentoPromocion(promocionAplicar.getCantidad());
                elementoProducto.calcularSubtotal();
                return true;
            }
            if (elementoProducto.getDescuentoPromocion() != null) {
                if (elementoProducto.getDescuentoPromocion().compareTo(BigDecimal.ZERO) == 0) {
                    elementoProducto.setPromocion(null);
                    elementoProducto.setPromocionNombre(null);
                    elementoProducto.setDescuentoPorcentual(null);
                    elementoProducto.setDescuentoPromocion(null);
                    return true;
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(ServicioElementoProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private Promocion closestPromocion(Integer cantidadProducto, List<Promocion> listaPromociones) {
        Integer minimo = Integer.MAX_VALUE;
        Promocion closest = null;

        for (Promocion promocion : listaPromociones) {
            final int diff = Math.abs(promocion.getCantidad() - cantidadProducto);

            if (diff < minimo) {
                minimo = diff;
                closest = promocion;
            }
        }
        return closest;
    }

    public <T extends ElementoProducto> DtMensajeSistema ajustarListaElementosProducto(Sucursal sucursal, List<T> listaElementoProducto) {
        try {
            List<String> listaAlertas = new ArrayList<>();
            boolean productosBorrados = false;
            boolean precios = false;
            boolean stock = false;
            boolean promocion = false;

            for (ElementoProducto elementoProducto : listaElementoProducto) {
                productosBorrados = this.ajustarProductosBorrados(elementoProducto);
                precios = this.ajustarPrecio(elementoProducto);
                stock = this.ajustarStock(sucursal, elementoProducto);
                promocion = this.ajustarPromocion(sucursal, elementoProducto);
            }

            if (productosBorrados) {
                listaAlertas.add(ServicioMensajeSistema.getInstance().getMensajeProperties("ElementoProducto.product.warn"));
            }
            if (precios) {
                listaAlertas.add(ServicioMensajeSistema.getInstance().getMensajeProperties("ElementoProducto.cost.warn"));
            }
            if (stock) {
                listaAlertas.add(ServicioMensajeSistema.getInstance().getMensajeProperties("ElementoProducto.stock.warn"));
            }
            if (promocion) {
                listaAlertas.add(ServicioMensajeSistema.getInstance().getMensajeProperties("ElementoProducto.promotion.warn"));
            }

            if (listaAlertas.isEmpty()) {
                return ServicioMensajeSistema.getInstance().getMensajeSistemaOK();
            } else {
                return ServicioMensajeSistema.getInstance().getMensajeSistemaALERT(listaAlertas);
            }
        } catch (Exception ex) {
            Logger.getLogger(ServicioElementoProducto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ServicioMensajeSistema.getInstance().getMensajeSistemaERROR("ElementoProducto.adjust.error");
    }
}
