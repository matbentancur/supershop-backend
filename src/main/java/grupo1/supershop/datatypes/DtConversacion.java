package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtConversacion extends DtModificable {

    @Getter
    @Setter
    private boolean finalizada;

    @Getter
    @Setter
    private DtComprador dtComprador;

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    public DtConversacion(boolean finalizada, DtComprador dtComprador, DtSucursal dtSucursal, Long id, Date creacion) {
        super(id, creacion);
        this.finalizada = finalizada;
        this.dtComprador = dtComprador;
        this.dtSucursal = dtSucursal;
    }

}
