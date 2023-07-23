package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DtCompraSucursal extends DtCompra {

    public DtCompraSucursal(String estado, String observacionesCliente, String observacionesSucursal, boolean pago, String metodoPago, String paypalOrdenId, DtComprador dtComprador, DtSucursal dtSucursal, Set<DtVale> vales, BigDecimal costoCompra, BigDecimal descuentoVales, BigDecimal descuentoPromociones, BigDecimal total, Long id, Date creacion) {
        super(estado, observacionesCliente, observacionesSucursal, pago, metodoPago, paypalOrdenId, dtComprador, dtSucursal, vales, costoCompra, descuentoVales, descuentoPromociones, total, id, creacion);
    }

}
