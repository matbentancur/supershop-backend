package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtModificable;
import grupo1.supershop.datatypes.DtUsuario;
import grupo1.supershop.servicios.ServicioPassword;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
        name = "usuario",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "email", unique = true),
            @Index(columnList = "bloqueado"),
            @Index(columnList = "password")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Usuario extends Borrable {

    @Column(nullable = false)
    @NotNull
    @Getter
    @Setter
    private boolean bloqueado;

    @Column(unique = true, nullable = false, length = 128)
    @NotNull
    @Size(min = 3, max = 255)
    @Email
    @Getter
    @Setter
    private String email;

    @Column(nullable = false, length = 128)
    @Getter
    @Setter
    private String password;

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String nombres;

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String apellidos;

    @Column(nullable = false, length = 32)
    @NotNull
    @NotBlank
    @Size(min = 8, max = 32)
    @Getter
    @Setter
    private String celular;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    @Getter
    @Setter
    private Date nacimiento;
    
    @Column(nullable = true, length = 255)
    @Getter
    @Setter
    private String pushToken;

    public Usuario(DtUsuario dtUsuario) {
        this.email = dtUsuario.getEmail();
        this.password = dtUsuario.getPassword();
        this.nombres = dtUsuario.getNombres();
        this.apellidos = dtUsuario.getApellidos();
        this.celular = dtUsuario.getCelular();
        this.nacimiento = dtUsuario.getNacimiento();
    }

    public void cifrarPassword() {
        this.password = ServicioPassword.getInstance().hashPassword(this.password);
    }

    public void setRandomPassword() {
        this.password = ServicioPassword.getInstance().getRandomPassword(20);
    }

    @Override
    public DtBase getDataType() {
        return new DtUsuario(
                bloqueado,
                email,
                nombres,
                apellidos,
                celular,
                nacimiento,
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtUsuario(
                bloqueado,
                email,
                nombres,
                apellidos,
                null,
                null,
                this.getId(),
                null,
                this.isBorrado()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        DtUsuario dtUsuario = (DtUsuario) dtModificable;
        this.email = dtUsuario.getEmail();
        this.nombres = dtUsuario.getNombres();
        this.apellidos = dtUsuario.getApellidos();
        this.celular = dtUsuario.getCelular();
        this.nacimiento = dtUsuario.getNacimiento();
    }

    public DtUsuario getMisDatos() {
        return new DtUsuario(
                email,
                nombres,
                apellidos,
                celular,
                nacimiento
        );
    }

}
