package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.datatypes.DtUsuario;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtActividadTest")
class TestDtActividad extends ConfiguracionTest {

    private Long id;
    private Date momento;
    private String descripcion;
    private DtUsuario dtUsuario;

    @BeforeEach
    public void configurar() {
        id = 1L;
        momento = new Date();
        descripcion = "Descripción de actividad";
        dtUsuario = new DtUsuario();
    }

    @DisplayName("Crear DtActividad - Getter/Setter")
    @Test
    void crear() {
        dtActividad.setId(id);
        dtActividad.setMomento(momento);
        dtActividad.setDescripcion(descripcion);
        dtActividad.setDtUsuario(dtUsuario);

        ServicioVerificacion.getInstance().verificar(id, dtActividad.getId());
        ServicioVerificacion.getInstance().verificar(momento, dtActividad.getMomento());
        ServicioVerificacion.getInstance().verificar(descripcion, dtActividad.getDescripcion());
        ServicioVerificacion.getInstance().verificar(dtUsuario, dtActividad.getDtUsuario());
    }
}