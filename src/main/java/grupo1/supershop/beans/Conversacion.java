package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtConversacion;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtSucursal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "conversacion",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "finalizada"),
            @Index(columnList = "comprador_usuario_id"),
            @Index(columnList = "sucursal_id")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Conversacion extends Modificable {

    @Column(nullable = false)
    @NotNull
    @Getter
    @Setter
    private boolean finalizada;

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

    @Override
    public DtBase getDataType() {
        return new DtConversacion(
                finalizada,
                (DtComprador) comprador.getDataType(),
                (DtSucursal) sucursal.getDataType(),
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtConversacion(
                finalizada,
                (DtComprador) comprador.getSimpleDataType(),
                (DtSucursal) sucursal.getSimpleDataType(),
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
    }

    public void finalizar() {
        this.setFinalizada(true);
    }

}
