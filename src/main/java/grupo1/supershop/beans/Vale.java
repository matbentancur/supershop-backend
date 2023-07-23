package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.enums.EstadoVale;
import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "vale",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "estado"),
            @Index(columnList = "sucursal_id")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Vale extends Borrable {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 128)
    @NotNull
    @Getter
    @Setter
    private EstadoVale estado;

    @Column(nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String descripcion;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Min(1)
    @Getter
    @Setter
    private BigDecimal monto;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    @Future
    @Getter
    @Setter
    private Date expira;

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

    public Vale(DtVale dtVale) {
        this.descripcion = dtVale.getDescripcion();
        this.monto = dtVale.getMonto();
        this.expira = dtVale.getExpira();
    }

    @Override
    public DtBase getDataType() {
        return new DtVale(
                estado.toString(),
                descripcion,
                monto,
                expira,
                (DtComprador) comprador.getDataType(),
                (DtSucursal) sucursal.getDataType(),
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtVale(
                estado.toString(),
                null,
                monto,
                expira,
                (DtComprador) comprador.getSimpleDataType(),
                (DtSucursal) sucursal.getSimpleDataType(),
                this.getId(),
                null,
                this.isBorrado()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        DtVale dtVale = (DtVale) dtModificable;
        this.descripcion = dtVale.getDescripcion();
        this.monto = dtVale.getMonto();
        this.expira = dtVale.getExpira();
    }

}
