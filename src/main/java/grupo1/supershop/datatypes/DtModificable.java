package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public abstract class DtModificable extends DtIdentificable {

//    @Getter
//    @Setter
//    private Set<DtActividad> actividad = new HashSet<DtActividad>();

    public DtModificable(Long id, Date creacion) {
        super(id, creacion);
    }

}
