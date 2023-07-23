package grupo1.supershop.datatypes;

import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtPromocion extends DtBorrable {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private Date inicia;

    @Getter
    @Setter
    private Date finaliza;

    @Getter
    @Setter
    private Integer cantidad;

    @Getter
    @Setter
    private Integer descuentoPorcentual;

    @Getter
    @Setter
    private DtProducto dtProducto;

    @Getter
    @Setter
    private DtSucursal dtSucursal;

    @Getter
    @Setter
    private DtArchivo imagen;

    @Getter
    @Setter
    private String imagenRemota;

    public DtPromocion(String nombre, String descripcion, Date inicia, Date finaliza, Integer cantidad, Integer descuentoPorcentual, DtProducto dtProducto, DtSucursal dtSucursal, DtArchivo imagen, String imagenRemota, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.inicia = inicia;
        this.finaliza = finaliza;
        this.cantidad = cantidad;
        this.descuentoPorcentual = descuentoPorcentual;
        this.dtProducto = dtProducto;
        this.dtSucursal = dtSucursal;
        this.imagen = imagen;
        this.imagenRemota = imagenRemota;
    }

}
