package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtAdministradorSucursal;
import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtSucursal;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
        name = "administrador_sucursal"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdministradorSucursal extends UsuarioSucursal {

    public AdministradorSucursal(DtAdministradorSucursal dtAdministradorSucursal) {
        super(dtAdministradorSucursal);
    }

    @Override
    public DtBase getDataType() {
        return new DtAdministradorSucursal(
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
        return new DtAdministradorSucursal(
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
