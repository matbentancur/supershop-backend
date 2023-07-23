package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtAtencionCliente;
import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtSucursal;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
        name = "atencion_cliente"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AtencionCliente extends UsuarioSucursal {

    public AtencionCliente(DtAtencionCliente dtAtencionCliente) {
        super(dtAtencionCliente);
    }

    @Override
    public DtBase getDataType() {
        return new DtAtencionCliente(
                this.isBloqueado(),
                this.getEmail(),
                this.getNombres(),
                this.getApellidos(),
                this.getCelular(),
                this.getNacimiento(),
                this.getId(),
                this.getCreacion(),
                this.isBorrado(),
                (DtSucursal) this.getSucursal().getDataType()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtAtencionCliente(
                this.isBloqueado(),
                this.getEmail(),
                this.getNombres(),
                this.getApellidos(),
                null,
                null,
                this.getId(),
                null,
                this.isBorrado(),
                (DtSucursal) this.getSucursal().getSimpleDataType()
        );
    }

}
