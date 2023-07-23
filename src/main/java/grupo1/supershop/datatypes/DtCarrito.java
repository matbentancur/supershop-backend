package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtCarrito extends DtBase {

    @Getter
    @Setter
    private DtComprador dtComprador;

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    @Getter
    @Setter
    private BigDecimal total;

    public DtCarrito(DtComprador dtComprador, DtSucursal dtSucursal, BigDecimal total) {
        this.dtComprador = dtComprador;
        this.dtSucursal = dtSucursal;
        this.total = total;
    }

}
