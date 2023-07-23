package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtCompraSucursal;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtSucursal;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.servicios.ServicioDataType;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@PrimaryKeyJoinColumn(name = "compra_id")
@Table(
        name = "compra_sucursal"
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CompraSucursal extends Compra {

    @Override
    public DtBase getDataType() {
        Set<DtVale> conjuntoDtVale = ServicioDataType.getInstance().beanSetToDataTypeSet(this.getVales());
        return new DtCompraSucursal(
                this.getEstado().toString(),
                this.getObservacionesCliente(),
                this.getObservacionesSucursal(),
                this.isPago(),
                this.getMetodoPago() != null ? this.getMetodoPago().toString() : null,
                this.getPaypalOrdenId(),
                (DtComprador) this.getComprador().getDataType(),
                (DtSucursal) this.getSucursal().getDataType(),
                conjuntoDtVale,
                this.getCostoCompra(),
                this.getDescuentoVales(),
                this.getDescuentoPromociones(),
                this.getTotal(),
                this.getId(),
                this.getCreacion()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        Set<DtVale> conjuntoDtVale = ServicioDataType.getInstance().beanSetToSimpleDataTypeSet(this.getVales());
        return new DtCompraSucursal(
                this.getEstado().toString(),
                null,
                null,
                this.isPago(),
                this.getMetodoPago() != null ? this.getMetodoPago().toString() : null,
                this.getPaypalOrdenId(),
                (DtComprador) this.getComprador().getSimpleDataType(),
                (DtSucursal) this.getSucursal().getSimpleDataType(),
                conjuntoDtVale,
                this.getCostoCompra(),
                this.getDescuentoVales(),
                this.getDescuentoPromociones(),
                this.getTotal(),
                this.getId(),
                this.getCreacion()
        );
    }

}
