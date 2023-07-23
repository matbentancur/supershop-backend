package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtComprador;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
@Table(
        name = "comprador"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Comprador extends Usuario {

    public Comprador(DtComprador dtComprador) {
        super(dtComprador);
    }

    @Override
    public DtBase getDataType() {
        return new DtComprador(
                this.isBloqueado(),
                this.getEmail(),
                this.getNombres(),
                this.getApellidos(),
                this.getCelular(),
                this.getNacimiento(),
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtComprador(
                this.isBloqueado(),
                this.getEmail(),
                this.getNombres(),
                this.getApellidos(),
                null,
                null,
                this.getId(),
                null,
                this.isBorrado()
        );
    }

}
