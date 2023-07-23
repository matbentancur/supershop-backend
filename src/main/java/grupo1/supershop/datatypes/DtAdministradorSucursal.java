package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DtAdministradorSucursal extends DtUsuarioSucursal {

    public DtAdministradorSucursal(boolean bloqueado, String email, String nombres, String apellidos, String celular, Date nacimiento, Long id, Date creacion, boolean borrado, DtSucursal dtSucursal) {
        super(bloqueado, email, nombres, apellidos, celular, nacimiento, id, creacion, borrado, dtSucursal);
    }

}
