package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtConversacion;
import grupo1.supershop.datatypes.DtMensaje;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtUsuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
        name = "mensaje",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "conversacion_id"),
            @Index(columnList = "usuario_id")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Mensaje extends Borrable {

    @Column(nullable = false, length = 255)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @Getter
    @Setter
    private Conversacion conversacion;

    public Mensaje(DtMensaje dtMensaje) {
        this.texto = dtMensaje.getTexto();
    }

    @Override
    public DtBase getDataType() {
        return new DtMensaje(
                texto,
                (DtUsuario) usuario.getDataType(),
                (DtConversacion) conversacion.getDataType(),
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtMensaje(
                texto,
                (DtUsuario) usuario.getSimpleDataType(),
                (DtConversacion) conversacion.getSimpleDataType(),
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        DtMensaje dtMensaje = (DtMensaje) dtModificable;
        this.texto = dtMensaje.getTexto();
    }

}
