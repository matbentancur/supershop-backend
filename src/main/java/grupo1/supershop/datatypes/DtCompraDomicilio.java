package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtCompraDomicilio extends DtCompra {

    @Getter
    @Setter
    private DtDomicilio dtDomicilio;

    @Getter
    @Setter
    private BigDecimal domicilioLongitud;

    @Getter
    @Setter
    private BigDecimal domicilioLatitud;

    @Getter
    @Setter
    private String domicilioString;

    @Getter
    @Setter
    private DtMetodoEnvio dtMetodoEnvio;

    @Getter
    @Setter
    private String metodoEnvioNombre;

    @Getter
    @Setter
    private BigDecimal metodoEnvioCosto;

    public DtCompraDomicilio(DtDomicilio dtDomicilio, BigDecimal domicilioLongitud, BigDecimal domicilioLatitud, String domicilioString, DtMetodoEnvio dtMetodoEnvio, String metodoEnvioNombre, BigDecimal metodoEnvioCosto, String estado, String observacionesCliente, String observacionesSucursal, boolean pago, String metodoPago, String paypalOrdenId, DtComprador dtComprador, DtSucursal dtSucursal, Set<DtVale> vales, BigDecimal costoCompra, BigDecimal descuentoVales, BigDecimal descuentoPromociones, BigDecimal total, Long id, Date creacion) {
        super(estado, observacionesCliente, observacionesSucursal, pago, metodoPago, paypalOrdenId, dtComprador, dtSucursal, vales, costoCompra, descuentoVales, descuentoPromociones, total, id, creacion);
        this.dtDomicilio = dtDomicilio;
        this.domicilioLongitud = domicilioLongitud;
        this.domicilioLatitud = domicilioLatitud;
        this.domicilioString = domicilioString;
        this.dtMetodoEnvio = dtMetodoEnvio;
        this.metodoEnvioNombre = metodoEnvioNombre;
        this.metodoEnvioCosto = metodoEnvioCosto;
    }

}
