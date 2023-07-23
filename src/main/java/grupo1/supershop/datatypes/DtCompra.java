package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtCompra extends DtModificable {

    @Getter
    @Setter
    private String estado;

    @Getter
    @Setter
    private String observacionesCliente;

    @Getter
    @Setter
    private String observacionesSucursal;

    @Getter
    @Setter
    private boolean pago;

    @Getter
    @Setter
    private String metodoPago;

    @Getter
    @Setter
    private String paypalOrdenId;

    @Getter
    @Setter
    private DtComprador dtComprador;

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    @Getter
    @Setter
    private Set<DtVale> vales = new HashSet<DtVale>();

    @Getter
    @Setter
    private BigDecimal costoCompra;

    @Getter
    @Setter
    private BigDecimal descuentoVales;

    @Getter
    @Setter
    private BigDecimal descuentoPromociones;

    @Getter
    @Setter
    private BigDecimal total;

    public DtCompra(String estado, String observacionesCliente, String observacionesSucursal, boolean pago, String metodoPago, String paypalOrdenId, DtComprador dtComprador, DtSucursal dtSucursal, Set<DtVale> vales, BigDecimal costoCompra, BigDecimal descuentoVales, BigDecimal descuentoPromociones, BigDecimal total, Long id, Date creacion) {
        super(id, creacion);
        this.estado = estado;
        this.observacionesCliente = observacionesCliente;
        this.observacionesSucursal = observacionesSucursal;
        this.pago = pago;
        this.metodoPago = metodoPago;
        this.paypalOrdenId = paypalOrdenId;
        this.dtComprador = dtComprador;
        this.dtSucursal = dtSucursal;
        this.vales = vales;
        this.costoCompra = costoCompra;
        this.descuentoVales = descuentoVales;
        this.descuentoPromociones = descuentoPromociones;
        this.total = total;
    }

}
