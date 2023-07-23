package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtUsuario extends DtBorrable {

    @Getter
    @Setter
    private boolean bloqueado;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String nombres;

    @Getter
    @Setter
    private String apellidos;

    @Getter
    @Setter
    private String celular;

    @Getter
    @Setter
    private Date nacimiento;

    public DtUsuario(String email, String nombres, String apellidos, String celular, Date nacimiento) {
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.nacimiento = nacimiento;
    }

    public DtUsuario(boolean bloqueado, String email, String nombres, String apellidos, String celular, Date nacimiento, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.bloqueado = bloqueado;
        this.email = email;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.nacimiento = nacimiento;
    }

}
