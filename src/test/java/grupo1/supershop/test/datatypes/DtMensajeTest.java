package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtConversacion;
import grupo1.supershop.datatypes.DtUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtMensajeTest")
class DtMensajeTest extends ConfiguracionTest {

    private String texto;
    private DtUsuario dtUsuario;
    private DtConversacion dtConversacion;

    @BeforeEach
    public void configurar() {
        texto = "Texto del mensaje";
        dtUsuario = new DtUsuario();
        dtConversacion = new DtConversacion();
    }

    @DisplayName("Crear DtMensaje - Getter/Setter")
    @Test
    void crear() {
        dtMensaje.setTexto(texto);
        dtMensaje.setDtUsuario(dtUsuario);
        dtMensaje.setDtConversacion(dtConversacion);

        ServicioVerificacion.getInstance().verificar(texto, dtMensaje.getTexto());
        ServicioVerificacion.getInstance().verificar(dtUsuario, dtMensaje.getDtUsuario());
        ServicioVerificacion.getInstance().verificar(dtConversacion, dtMensaje.getDtConversacion());
    }
}