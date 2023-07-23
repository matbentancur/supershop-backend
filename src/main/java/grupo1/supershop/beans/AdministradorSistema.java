package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtAdministradorSistema;
import grupo1.supershop.datatypes.DtBase;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
@Table(
        name = "administrador_sistema"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdministradorSistema extends Usuario {

    public AdministradorSistema(DtAdministradorSistema dtAdministradorSistema) {
        super(dtAdministradorSistema);
    }

    @Override
    public DtBase getDataType() {
        return new DtAdministradorSistema(
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
        return new DtAdministradorSistema(
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
