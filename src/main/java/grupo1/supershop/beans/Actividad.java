package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtUsuario;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(
        name = "actividad",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "usuario_id")
        }
)
@NoArgsConstructor
@ToString(callSuper = true)
public class Actividad extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Getter
    @Setter
    private Date momento;

    @Column(nullable = false, columnDefinition = "varchar")
    @NotNull
    @NotBlank
    @Size(min = 1)
    @Getter
    @Setter
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Usuario usuario;

    public Actividad(String descripcion, Usuario usuario) {
        this.descripcion = descripcion;
        this.usuario = usuario;
    }

    @Override
    public DtBase getDataType() {
        return new DtActividad(
                id,
                momento,
                descripcion,
                (DtUsuario) usuario.getDataType()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtActividad(
                id,
                momento,
                descripcion,
                (DtUsuario) usuario.getSimpleDataType()
        );
    }

}
