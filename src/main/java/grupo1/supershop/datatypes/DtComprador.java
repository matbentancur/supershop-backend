package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DtComprador extends DtUsuario {

    public DtComprador(boolean bloqueado, String email, String nombres, String apellidos, String celular, Date nacimiento, Long id, Date creacion, boolean borrado) {
        super(bloqueado, email, nombres, apellidos, celular, nacimiento, id, creacion, borrado);
    }

}
