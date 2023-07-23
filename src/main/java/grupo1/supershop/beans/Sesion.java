package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtSesion;
import grupo1.supershop.datatypes.DtUsuario;
import grupo1.supershop.servicios.ServicioSesion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "sesion",
        indexes = {
            @Index(columnList = "usuario_id", unique = true),
            @Index(columnList = "secreto", unique = true)
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class Sesion extends Base {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Usuario usuario;

    @Column(unique = true, nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 128, max = 128)
    @Getter
    @Setter
    private String secreto;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @NotNull
    @Getter
    @Setter
    private Date validez;

    public Sesion(Usuario usuario) {
        this.usuario = usuario;
        this.secreto = ServicioSesion.getInstance().getSecret();
        this.validez = ServicioSesion.getInstance().getValidez();
    }

    @Override
    public DtBase getDataType() {
        return new DtSesion(
                (DtUsuario) usuario.getDataType(),
                secreto,
                validez
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtSesion(
                (DtUsuario) usuario.getSimpleDataType(),
                secreto,
                validez
        );
    }

}
