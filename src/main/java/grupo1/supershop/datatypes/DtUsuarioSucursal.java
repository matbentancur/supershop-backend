package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtUsuarioSucursal extends DtUsuario {

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    public DtUsuarioSucursal(boolean bloqueado, String email, String nombres, String apellidos, String celular, Date nacimiento, Long id, Date creacion, boolean borrado, DtSucursal dtSucursal) {
        super(bloqueado, email, nombres, apellidos, celular, nacimiento, id, creacion, borrado);
        this.dtSucursal = dtSucursal;
    }

}
