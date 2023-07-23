package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@DisplayName("DtMetodoEnvioTest")
class DtMetodoEnvioTest extends ConfiguracionTest {

    private String nombre;
    private String descripcion;
    private BigDecimal costo;

    @BeforeEach
    public void configurar() {
        nombre = "Nombre del producto";
        descripcion = "Descripcion del producto";
        costo = new BigDecimal("100.00");
    }

    @DisplayName("Crear DtMetodoEnvio - Getter/Setter")
    @Test
    void crear() {
        dtMetodoEnvio.setNombre(nombre);
        dtMetodoEnvio.setDescripcion(descripcion);
        dtMetodoEnvio.setCosto(costo);

        ServicioVerificacion.getInstance().verificar(nombre, dtMetodoEnvio.getNombre());
        ServicioVerificacion.getInstance().verificar(descripcion, dtMetodoEnvio.getDescripcion());
        ServicioVerificacion.getInstance().verificar(costo, dtMetodoEnvio.getCosto());
    }
}