package grupo1.supershop.datatypes;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class DtDomicilio extends DtBorrable {

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String descripcion;

    @Getter
    @Setter
    private BigDecimal longitud;

    @Getter
    @Setter
    private BigDecimal latitud;

    @Getter
    @Setter
    private String calle;

    @Getter
    @Setter
    private String numeracion;

    @Getter
    @Setter
    private String esquina;

    @Getter
    @Setter
    private String referencias;

    @Getter
    @Setter
    private String block;

    @Getter
    @Setter
    private String apartamento;

    @Getter
    @Setter
    private String barrio;

    @Getter
    @Setter
    private Integer codigoPostal;

    @Getter
    @Setter
    private String departamento;

    @Getter
    @Setter
    private DtComprador dtComprador;

    public DtDomicilio(String nombre, String descripcion, BigDecimal longitud, BigDecimal latitud, String calle, String numeracion, String esquina, String referencias, String block, String apartamento, String barrio, Integer codigoPostal, String departamento, DtComprador dtComprador, Long id, Date creacion, boolean borrado) {
        super(id, creacion, borrado);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.calle = calle;
        this.numeracion = numeracion;
        this.esquina = esquina;
        this.referencias = referencias;
        this.block = block;
        this.apartamento = apartamento;
        this.barrio = barrio;
        this.codigoPostal = codigoPostal;
        this.departamento = departamento;
        this.dtComprador = dtComprador;
    }

}
