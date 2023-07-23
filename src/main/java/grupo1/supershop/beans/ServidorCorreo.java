package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtServidorCorreo;
import grupo1.supershop.servicios.ServicioPassword;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = "servidor_correo"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ServidorCorreo extends Identificable {

    @Column(unique = true, nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String nombre;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String servidor;

    @Column(nullable = false)
    @NotNull
    @Positive
    @Max(65535)
    @Getter
    @Setter
    private Integer puerto;

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String usuario;

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String password;

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String seguridad;

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String identificacion;

    @Column(nullable = false, length = 128)
    @NotNull
    @Size(min = 5, max = 128)
    @Email
    @Getter
    @Setter
    private String desdeCorreo;

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String desdeNombre;

    public void encriptarPassword() {
        this.password = ServicioPassword.getInstance().encriptarPassword(password);
    }

    public void desencriptarPassword() {
        this.password = ServicioPassword.getInstance().desencriptarPassword(password);
    }

    @Override
    public DtBase getDataType() {
        return new DtServidorCorreo();
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtServidorCorreo();
    }

}
