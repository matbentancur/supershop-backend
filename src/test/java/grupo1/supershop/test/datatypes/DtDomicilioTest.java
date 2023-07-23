package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtComprador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("DtDomicilioTest")
class DtDomicilioTest extends ConfiguracionTest {

    private String nombre;
    private String descripcion;
    private BigDecimal longitud;
    private BigDecimal latitud;
    private String calle;
    private String numeracion;
    private String esquina;
    private String referencias;
    private String block;
    private String apartamento;
    private String barrio;
    private Integer codigoPostal;
    private String departamento;
    private DtComprador dtComprador;

    @BeforeEach
    public void configurar() {
        nombre = "Nombre del lugar";
        descripcion = "Descripcion del lugar";
        longitud = new BigDecimal("12.345");
        latitud = new BigDecimal("67.890");
        calle = "Calle Principal";
        numeracion = "123";
        esquina = "Esquina 1";
        referencias = "Referencias del lugar";
        block = "Block A";
        apartamento = "Apartamento 456";
        barrio = "Barrio";
        codigoPostal = 12345;
        departamento = "Departamento ABC";
        dtComprador = new DtComprador();
    }

    @DisplayName("Crear DtDomicilioTest - Getter/Setter")
    @Test
    void crear() {
        dtDomicilio.setNombre(nombre);
        dtDomicilio.setDescripcion(descripcion);
        dtDomicilio.setLongitud(longitud);
        dtDomicilio.setLatitud(latitud);
        dtDomicilio.setCalle(calle);
        dtDomicilio.setNumeracion(numeracion);
        dtDomicilio.setEsquina(esquina);
        dtDomicilio.setReferencias(referencias);
        dtDomicilio.setBlock(block);
        dtDomicilio.setApartamento(apartamento);
        dtDomicilio.setBarrio(barrio);
        dtDomicilio.setCodigoPostal(codigoPostal);
        dtDomicilio.setDepartamento(departamento);
        dtDomicilio.setDtComprador(dtComprador);

        ServicioVerificacion.getInstance().verificar(nombre, dtDomicilio.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtDomicilio.getDescripcion());
        ServicioVerificacion.getInstance().verificar(longitud, dtDomicilio.getLongitud());
        ServicioVerificacion.getInstance().verificar(latitud, dtDomicilio.getLatitud());
        ServicioVerificacion.getInstance().verificar(calle, dtDomicilio.getCalle());
        ServicioVerificacion.getInstance().verificar(numeracion, dtDomicilio.getNumeracion());
        ServicioVerificacion.getInstance().verificar(esquina, dtDomicilio.getEsquina());
        ServicioVerificacion.getInstance().verificar(referencias, dtDomicilio.getReferencias());
        ServicioVerificacion.getInstance().verificar(block, dtDomicilio.getBlock());
        ServicioVerificacion.getInstance().verificar(apartamento, dtDomicilio.getApartamento());
        ServicioVerificacion.getInstance().verificar(barrio, dtDomicilio.getBarrio());
        ServicioVerificacion.getInstance().verificar(codigoPostal, dtDomicilio.getCodigoPostal());
        ServicioVerificacion.getInstance().verificar(departamento, dtDomicilio.getDepartamento());
        ServicioVerificacion.getInstance().verificar(dtComprador, dtDomicilio.getDtComprador());

    }
}