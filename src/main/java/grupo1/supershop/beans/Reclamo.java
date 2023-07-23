package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtReclamo;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.enums.EstadoReclamo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "reclamo",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "comprador_usuario_id"),
            @Index(columnList = "sucursal_id"),}
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Reclamo extends Modificable {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 128)
    @NotNull
    @Getter
    @Setter
    private EstadoReclamo estado;

    @Column(nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String descripcion;

    @Column(nullable = true, length = 255)
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String conclusion;

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

    public Reclamo(DtReclamo dtReclamo) {
        this.descripcion = dtReclamo.getDescripcion();
    }

    @Override
    public DtBase getDataType() {
        return new DtReclamo(
                estado.toString(),
                descripcion,
                conclusion,
                (DtComprador) comprador.getDataType(),
                (DtSucursal) sucursal.getDataType(),
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtReclamo(
                estado.toString(),
                descripcion,
                conclusion,
                (DtComprador) comprador.getSimpleDataType(),
                (DtSucursal) sucursal.getSimpleDataType(),
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {

    }

    public void concluir(DtReclamo dtReclamo) {
        this.setEstado(EstadoReclamo.CONCLUIDO);
        this.setConclusion(dtReclamo.getConclusion());
    }

    public void confirmar() {
        this.setEstado(EstadoReclamo.CONFIRMADO);
    }

}
