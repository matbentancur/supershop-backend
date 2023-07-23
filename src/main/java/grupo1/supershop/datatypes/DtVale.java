package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtVale extends DtBorrable {

    @Getter
    @Setter
    private String estado;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private BigDecimal monto;

    @Getter
    @Setter
    private Date expira;

    @Getter
    @Setter
    private DtComprador dtComprador;

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    public DtVale(String estado, String descripcion, BigDecimal monto, Date expira, DtComprador dtComprador, DtSucursal dtSucursal, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.estado = estado;
        this.descripcion = descripcion;
        this.monto = monto;
        this.expira = expira;
        this.dtComprador = dtComprador;
        this.dtSucursal = dtSucursal;
    }

}
