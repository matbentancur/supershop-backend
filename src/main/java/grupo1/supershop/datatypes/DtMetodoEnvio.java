package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtMetodoEnvio extends DtBorrable {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    //gramos o mililitros
    private Integer pesoMaximo;

    @Getter
    @Setter
    private Integer cantidadProductosMaximo;

    @Getter
    @Setter
    private BigDecimal costo;

    public DtMetodoEnvio(String nombre, String descripcion, Integer pesoMaximo, Integer cantidadProductosMaximo, BigDecimal costo, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.pesoMaximo = pesoMaximo;
        this.cantidadProductosMaximo = cantidadProductosMaximo;
        this.costo = costo;
    }

}
