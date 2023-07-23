package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtProducto extends DtBorrable {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private String barcode;

    @Getter
    @Setter
    //gramos o mililitros
    private Integer peso;

    @Getter
    @Setter
    private BigDecimal precio;

    @Getter
    @Setter
    private DtCategoria dtCategoria;

    @Getter
    @Setter
    private Set<DtArchivo> imagenes = new HashSet<DtArchivo>();

    @Getter
    @Setter
    private Set<String> imagenesRemotas = new HashSet<String>();

    public DtProducto(String nombre, String descripcion, String barcode, Integer peso, BigDecimal precio, DtCategoria dtCategoria, Set<DtArchivo> imagenes, Set<String> imagenesRemotas, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.barcode = barcode;
        this.peso = peso;
        this.precio = precio;
        this.dtCategoria = dtCategoria;
        this.imagenes = imagenes;
        this.imagenesRemotas = imagenesRemotas;
    }

}
