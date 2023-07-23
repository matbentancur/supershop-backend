package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtDomicilio;
import grupo1.supershop.datatypes.DtMetodoEnvio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("DtCompraDomicilioTest")
class DtCompraDomicilioTest extends ConfiguracionTest {

    private DtDomicilio dtDomicilio;
    private BigDecimal domicilioLongitud;
    private BigDecimal domicilioLatitud;
    private String domicilioString;
    private DtMetodoEnvio dtMetodoEnvio;
    private String metodoEnvioNombre;
    private BigDecimal metodoEnvioCosto;

    @BeforeEach
    public void configurar() {
        dtDomicilio = new DtDomicilio();
        domicilioLongitud = new BigDecimal("180.00");
        domicilioLatitud = new BigDecimal("90.00");
        domicilioString = "Valor de domicilioString";
        dtMetodoEnvio = new DtMetodoEnvio();
        metodoEnvioNombre = "Nombre del metodo de envio";
        metodoEnvioCosto = new BigDecimal("10.00");
    }

    @DisplayName("Crear DtCompraDomicilio - Getter/Setter")
    @Test
    void crear() {
        dtCompraDomicilio.setDtDomicilio(dtDomicilio);
        dtCompraDomicilio.setDomicilioLongitud(domicilioLongitud);
        dtCompraDomicilio.setDomicilioLatitud(domicilioLatitud);
        dtCompraDomicilio.setDomicilioString(domicilioString);
        dtCompraDomicilio.setDtMetodoEnvio(dtMetodoEnvio);
        dtCompraDomicilio.setMetodoEnvioNombre(metodoEnvioNombre);
        dtCompraDomicilio.setMetodoEnvioCosto(metodoEnvioCosto);

        ServicioVerificacion.getInstance().verificar(dtDomicilio, dtCompraDomicilio.getDtDomicilio());
        ServicioVerificacion.getInstance().verificar(domicilioLongitud, dtCompraDomicilio.getDomicilioLongitud());
        ServicioVerificacion.getInstance().verificar(domicilioLatitud, dtCompraDomicilio.getDomicilioLatitud());
        ServicioVerificacion.getInstance().verificar(domicilioString, dtCompraDomicilio.getDomicilioString());
        ServicioVerificacion.getInstance().verificar(dtMetodoEnvio, dtCompraDomicilio.getDtMetodoEnvio());
        ServicioVerificacion.getInstance().verificar(metodoEnvioNombre, dtCompraDomicilio.getMetodoEnvioNombre());
        ServicioVerificacion.getInstance().verificar(metodoEnvioCosto, dtCompraDomicilio.getMetodoEnvioCosto());
    }
}