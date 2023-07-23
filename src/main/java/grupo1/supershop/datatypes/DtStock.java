package grupo1.supershop.datatypes;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtStock extends DtBase {

    @Getter
    @Setter
    private DtProducto dtProducto;

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    @Getter
    @Setter
    private Integer cantidad;

    public DtStock(DtProducto dtProducto, DtSucursal dtSucursal, Integer cantidad) {
        this.dtProducto = dtProducto;
        this.dtSucursal = dtSucursal;
        this.cantidad = cantidad;
    }

}
