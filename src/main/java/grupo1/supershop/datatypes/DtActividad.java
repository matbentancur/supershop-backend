package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtActividad extends DtBase {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Date momento;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private DtUsuario dtUsuario;

    public DtActividad(Long id, Date momento, String descripcion, DtUsuario dtUsuario) {
        this.id = id;
        this.momento = momento;
        this.descripcion = descripcion;
        this.dtUsuario = dtUsuario;
    }

}
