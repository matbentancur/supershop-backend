package grupo1.supershop.beans;

import grupo1.supershop.datatypes.DtBase;
import grupo1.supershop.datatypes.DtComprador;
import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.datatypes.DtModificable;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(
        name = "domicilio",
        indexes = {
            @Index(columnList = "id", unique = true),
            @Index(columnList = "borrado"),
            @Index(columnList = "comprador_usuario_id")
        }
)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Domicilio extends Borrable {

    @Column(nullable = false, length = 128)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String nombre;

    @Column(nullable = true, length = 255)
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String descripcion;

    @Column(nullable = false, precision = 9, scale = 6)
    @NotNull
    @Min(-180)
    @Max(180)
    @Getter
    @Setter
    private BigDecimal longitud;

    @Column(nullable = false, precision = 9, scale = 6)
    @NotNull
    @Min(-90)
    @Max(90)
    @Getter
    @Setter
    private BigDecimal latitud;

    @Column(nullable = true, length = 128)
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String calle;

    @Column(nullable = true, length = 8)
    @Size(min = 1, max = 8)
    @Getter
    @Setter
    private String numeracion;

    @Column(nullable = true, length = 128)
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String esquina;

    @Column(nullable = true, length = 255)
    @Size(min = 1, max = 255)
    @Getter
    @Setter
    private String referencias;

    @Column(nullable = true, length = 8)
    @Size(min = 1, max = 8)
    @Getter
    @Setter
    private String block;

    @Column(nullable = true, length = 8)
    @Size(min = 1, max = 8)
    @Getter
    @Setter
    private String apartamento;

    @Column(nullable = true, length = 128)
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String barrio;

    @Column(nullable = true, name = "codigo_postal")
    @Positive
    @Min(1)
    @Getter
    @Setter
    private Integer codigoPostal;

    @Column(nullable = true, length = 128)
    @Size(min = 1, max = 128)
    @Getter
    @Setter
    private String departamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Comprador comprador;

    public Domicilio(DtDomicilio dtDomicilio) {
        this.nombre = dtDomicilio.getNombre();
        this.descripcion = dtDomicilio.getDescripcion();
        this.longitud = dtDomicilio.getLongitud();
        this.latitud = dtDomicilio.getLatitud();
        this.calle = dtDomicilio.getCalle();
        this.numeracion = dtDomicilio.getNumeracion();
        this.esquina = dtDomicilio.getEsquina();
        this.referencias = dtDomicilio.getReferencias();
        this.block = dtDomicilio.getBlock();
        this.apartamento = dtDomicilio.getApartamento();
        this.barrio = dtDomicilio.getBarrio();
        this.codigoPostal = dtDomicilio.getCodigoPostal();
        this.departamento = dtDomicilio.getDepartamento();
    }

    @Override
    public DtBase getDataType() {
        return new DtDomicilio(
                nombre,
                descripcion,
                longitud,
                latitud,
                calle,
                numeracion,
                esquina,
                referencias,
                block,
                apartamento,
                barrio,
                codigoPostal,
                departamento,
                (DtComprador) comprador.getDataType(),
                this.getId(),
                this.getCreacion(),
                this.isBorrado()
        );
    }

    @Override
    public DtBase getSimpleDataType() {
        return new DtDomicilio(
                nombre,
                null,
                longitud,
                latitud,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                this.getId(),
                null,
                this.isBorrado()
        );
    }

    @Override
    public void modificar(DtModificable dtModificable) {
        DtDomicilio dtDomicilio = (DtDomicilio) dtModificable;
        this.nombre = dtDomicilio.getNombre();
        this.descripcion = dtDomicilio.getDescripcion();
        this.longitud = dtDomicilio.getLongitud();
        this.latitud = dtDomicilio.getLatitud();
        this.calle = dtDomicilio.getCalle();
        this.numeracion = dtDomicilio.getNumeracion();
        this.esquina = dtDomicilio.getEsquina();
        this.referencias = dtDomicilio.getReferencias();
        this.block = dtDomicilio.getBlock();
        this.apartamento = dtDomicilio.getApartamento();
        this.barrio = dtDomicilio.getBarrio();
        this.codigoPostal = dtDomicilio.getCodigoPostal();
        this.departamento = dtDomicilio.getDepartamento();
    }

    private String getCalleString() {
        return this.getCalle() != null ? "Calle: " + this.getCalle() + ", " : null;
    }

    private String getNumeracionString() {
        return this.getNumeracion() != null ? "N°: " + this.getNumeracion() + ", " : null;
    }

    private String getEsquinaString() {
        return this.getEsquina() != null ? "Esq.: " + this.getEsquina() + ", " : null;
    }

    private String getReferenciasString() {
        return this.getReferencias() != null ? "Ref.: " + this.getReferencias() + ", " : null;
    }

    private String getBlockString() {
        return this.getBlock() != null ? "Block: " + this.getBlock() + ", " : null;
    }

    private String getApartamentoString() {
        return this.getApartamento() != null ? "Apto.: " + this.getApartamento() + ", " : null;
    }

    private String getBarrioString() {
        return this.getBarrio() != null ? "Barrio: " + this.getBarrio() + ", " : null;
    }

    private String getCodigoPostalString() {
        return this.getCodigoPostal() != null ? "CP: " + this.getCodigoPostal().toString() + ", " : null;
    }

    private String getDepartamentoString() {
        return this.getDepartamento() != null ? "Dpto: " + this.getDepartamento() + ", " : null;
    }

    public String describir() {
        String domicilioString = this.getCalleString()
                + this.getNumeracionString()
                + this.getEsquinaString()
                + this.getReferenciasString()
                + this.getBlockString()
                + this.getApartamentoString()
                + this.getBarrioString()
                + this.getCodigoPostalString()
                + this.getDepartamentoString();
        return StringUtils.left(domicilioString, 255);
    }

}
