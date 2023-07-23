package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtUsuarioSucursal;
import grupo1.supershop.persistencia.manejadores.ManejadorSucursal;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@PrimaryKeyJoinColumn(name = "usuario_id")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(
        name = "usuario_sucursal",
        indexes = {
            @Index(columnList = "sucursal_id, usuario_id", unique = true)
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UsuarioSucursal extends Usuario {

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Sucursal sucursal;

    public UsuarioSucursal(DtUsuarioSucursal dtUsuarioSucursal) {
        super(dtUsuarioSucursal);
    }

    @Override
    public DtBase getDataType() {
        return new DtUsuarioSucursal(
                this.isBloqueado(),
                this.getEmail(),
                this.getNombres(),
                this.getApellidos(),
                this.getCelular(),
                this.getNacimiento(),
                this.getId(),
                this.getCreacion(),
                this.isBorrado(),
                (DtSucursal) this.sucursal.getDataType()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtUsuarioSucursal(
                this.isBloqueado(),
                this.getEmail(),
                this.getNombres(),
                this.getApellidos(),
                null,
                null,
                this.getId(),
                null,
                this.isBorrado(),
                (DtSucursal) this.sucursal.getSimpleDataType()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        super.modificar(dtModificable);
        DtUsuarioSucursal dtUsuarioSucursal = (DtUsuarioSucursal) dtModificable;
        this.sucursal = ManejadorSucursal.getInstance().obtenerSucursal(dtUsuarioSucursal.getDtSucursal().getId());
    }
}
