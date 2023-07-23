package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtMensaje extends DtBorrable {

    @Getter
    @Setter
    private String texto;

    @Getter
    @Setter
    private DtUsuario dtUsuario;

    @Getter
    @Setter
    private DtConversacion dtConversacion;

    public DtMensaje(String texto, DtUsuario dtUsuario, DtConversacion dtConversacion, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.texto = texto;
        this.dtUsuario = dtUsuario;
        this.dtConversacion = dtConversacion;
    }

}
