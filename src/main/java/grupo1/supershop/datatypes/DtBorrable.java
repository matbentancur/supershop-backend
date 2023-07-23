package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public abstract class DtBorrable extends DtModificable {

    @Getter
    @Setter
    private boolean borrado;

    public DtBorrable(Long id, Date creacion, boolean borrado) {
        super(id, creacion);
        this.borrado = borrado;
    }

}
