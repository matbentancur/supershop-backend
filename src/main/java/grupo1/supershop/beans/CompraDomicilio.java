package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtCompraDomicilio;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.servicios.ServicioDataType;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@PrimaryKeyJoinColumn(name = "compra_id")
@Table(
        name = "compra_domicilio"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompraDomicilio extends Compra {

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Domicilio domicilio;

    @Column(nullable = true, precision = 9, scale = 6, name = "domicilio_longitud")
    @Min(-180)
    @Max(180)
    @Getter
    @Setter
    private BigDecimal domicilioLongitud;

    @Column(nullable = true, precision = 9, scale = 6, name = "domicilio_latitud")
    @Min(-90)
    @Max(90)
    @Getter
    @Setter
    private BigDecimal domicilioLatitud;

    @Column(nullable = true, length = 255, name = "domicilio_string")
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String domicilioString;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metodo_envio_id")
    @Getter
    @Setter
    private MetodoEnvio metodoEnvio;

    @Column(nullable = true, length = 128, name = "metodo_envio_nombre")
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String metodoEnvioNombre;

    @Column(nullable = true, name = "metodo_envio_costo")
    @Min(0)
    @Getter
    @Setter
    private BigDecimal metodoEnvioCosto;

    @Override
    public DtBase getDataType() {
        Set<DtVale> conjuntoDtVale = ServicioDataType.getInstance().beanSetToDataTypeSet(this.getVales());
        return new DtCompraDomicilio(
                domicilio != null ? (DtDomicilio) domicilio.getDataType() : null,
                domicilioLongitud,
                domicilioLatitud,
                domicilioString,
                metodoEnvio != null ? (DtMetodoEnvio) metodoEnvio.getDataType() : null,
                metodoEnvioNombre,
                metodoEnvioCosto,
                this.getEstado().toString(),
                this.getObservacionesCliente(),
                this.getObservacionesSucursal(),
                this.isPago(),
                this.getMetodoPago() != null ? this.getMetodoPago().toString() : null,
                this.getPaypalOrdenId(),
                (DtComprador) this.getComprador().getDataType(),
                (DtSucursal) this.getSucursal().getDataType(),
                conjuntoDtVale,
                this.getCostoCompra(),
                this.getDescuentoVales(),
                this.getDescuentoPromociones(),
                this.getTotal(),
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        Set<DtVale> conjuntoDtVale = ServicioDataType.getInstance().beanSetToSimpleDataTypeSet(this.getVales());
        return new DtCompraDomicilio(
                null,
                domicilioLongitud,
                domicilioLatitud,
                domicilioString,
                metodoEnvio != null ? (DtMetodoEnvio) metodoEnvio.getSimpleDataType() : null,
                metodoEnvioNombre,
                metodoEnvioCosto,
                this.getEstado().toString(),
                null,
                null,
                this.isPago(),
                this.getMetodoPago() != null ? this.getMetodoPago().toString() : null,
                this.getPaypalOrdenId(),
                (DtComprador) this.getComprador().getSimpleDataType(),
                (DtSucursal) this.getSucursal().getSimpleDataType(),
                conjuntoDtVale,
                this.getCostoCompra(),
                this.getDescuentoVales(),
                this.getDescuentoPromociones(),
                this.getTotal(),
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public void calcularTotal() {
        this.setTotal(this.getCostoCompra());
        this.setTotal(this.getTotal().add(this.getMetodoEnvioCosto()));
        this.setTotal(this.getTotal().subtract(this.getDescuentoVales()));
    }

}
