package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtReclamo extends DtModificable {

    @Getter
    @Setter
    private String estado;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String conclusion;

    @Getter
    @Setter
    private DtComprador dtComprador;

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    public DtReclamo(String estado, String descripcion, String conclusion, DtComprador dtComprador, DtSucursal dtSucursal, Long id, Date creacion) {
        super(id, creacion);
        this.estado = estado;
        this.descripcion = descripcion;
        this.conclusion = conclusion;
        this.dtComprador = dtComprador;
        this.dtSucursal = dtSucursal;
    }

}
