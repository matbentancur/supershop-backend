package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtCompra;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.enums.EstadoCompra;
import grupo1.supershop.enums.MetodoPago;
import grupo1.supershop.servicios.ServicioDataType;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
        name = "compra",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "estado"),
            @Index(columnList = "comprador_usuario_id"),
            @Index(columnList = "sucursal_id")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Compra extends Modificable {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 128)
    @NotNull
    @Getter
    @Setter
    private EstadoCompra estado;

    @Column(nullable = true, length = 255, name = "observaciones_cliente")
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String observacionesCliente;

    @Column(nullable = true, length = 255, name = "observaciones_sucursal")
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String observacionesSucursal;

    @Column(nullable = false)
    @NotNull
    @Getter
    @Setter
    private boolean pago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, length = 128, name = "metodo_pago")
    @Getter
    @Setter
    private MetodoPago metodoPago;
    
    @Column(nullable = true, length = 36, name = "paypal_orden_id")
    @Size(min = 1, max = 36)
    @Getter
    @Setter
    private String paypalOrdenId;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Comprador comprador;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Sucursal sucursal;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            joinColumns = @JoinColumn(name = "compra_id"),
            inverseJoinColumns = @JoinColumn(name = "vale_id")
    )
    @Getter
    @Setter
    private Set<Vale> vales = new HashSet<Vale>();

    @Column(nullable = false, name = "costo_compra")
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private BigDecimal costoCompra;

    @Column(nullable = false, name = "descuento_vales")
    @NotNull
    @Min(0)
    @Getter
    @Setter
    private BigDecimal descuentoVales;

    @Column(nullable = false, name = "descuento_promociones")
    @NotNull
    @Min(0)
    @Getter
    @Setter
    private BigDecimal descuentoPromociones;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private BigDecimal total;

    @Override
    public DtBase getDataType() {
        Set<DtVale> conjuntoDtVale = ServicioDataType.getInstance().beanSetToDataTypeSet(this.getVales());
        return new DtCompra(
                estado.toString(),
                observacionesCliente,
                observacionesSucursal,
                pago,
                metodoPago != null ? metodoPago.toString() : null,
                paypalOrdenId,
                (DtComprador) comprador.getDataType(),
                (DtSucursal) sucursal.getDataType(),
                conjuntoDtVale,
                costoCompra,
                descuentoVales,
                descuentoPromociones,
                total,
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        Set<DtVale> conjuntoDtVale = ServicioDataType.getInstance().beanSetToSimpleDataTypeSet(this.getVales());
        return new DtCompra(
                estado.toString(),
                null,
                null,
                pago,
                metodoPago != null ? metodoPago.toString() : null,
                paypalOrdenId,
                (DtComprador) comprador.getSimpleDataType(),
                (DtSucursal) sucursal.getSimpleDataType(),
                conjuntoDtVale,
                costoCompra,
                descuentoVales,
                descuentoPromociones,
                total,
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
    }

    public void confirmar() {
        this.setEstado(EstadoCompra.CONFIRMADA);
    }

    public void cancelar() {
        this.setEstado(EstadoCompra.CANCELADA);
    }

    public void devolver() {
        this.setEstado(EstadoCompra.DEVUELTA);
    }

    public void concluir() {
        this.setEstado(EstadoCompra.CONCLUIDA);
    }

    public void finalizar() {
        this.setEstado(EstadoCompra.PENDIENTE);
    }

    public void errorPago() {
        this.setEstado(EstadoCompra.ERROR_PAGO);
    }

    public void aplicarVale(Vale vale) {
        this.vales.add(vale);
        this.descuentoVales = this.descuentoVales.add(vale.getMonto());
    }

    public BigDecimal getMontoDevolucion() {
        return this.total.add(this.descuentoVales);
    }

    public void calcularTotal() {
        this.setTotal(this.getCostoCompra());
        this.setTotal(this.getTotal().subtract(this.getDescuentoVales()));
    }

}
