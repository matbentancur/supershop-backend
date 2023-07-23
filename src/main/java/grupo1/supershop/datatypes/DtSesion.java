package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtSesion extends DtBase {

    @Getter
    @Setter
    private DtUsuario dtUsuario;

    @Getter
    @Setter
    private String secreto;

    @Getter
    @Setter
    private Date validez;

    public DtSesion(DtUsuario dtUsuario, String secreto, Date validez) {
        this.dtUsuario = dtUsuario;
        this.secreto = secreto;
        this.validez = validez;
    }

}
