package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;

import grupo1.supershop.datatypes.DtArchivo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("DtSucursalTest")
class DtSucursalTest extends ConfiguracionTest {

    private String nombre;
    private String email;
    private String telefono;
    private String horario;
    private DtArchivo imagen;

    @BeforeEach
    public void configurar() {
        nombre = "Nombre de la sucursal";
        email = "sucursal@example.com";
        telefono = "123456789";
        horario = "9:00 - 18:00";
        imagen = new DtArchivo();
    }

    @DisplayName("Crear DtSucursalTest - Getter/Setter")
    @Test
    void crear() {
        dtSucursal.setNombre(nombre);
        dtSucursal.setEmail(email);
        dtSucursal.setTelefono(telefono);
        dtSucursal.setHorario(horario);
        dtSucursal.setImagen(imagen);

        ServicioVerificacion.getInstance().verificar(nombre, dtSucursal.getNombre());
        ServicioVerificacion.getInstance().verificar(email, dtSucursal.getEmail());
        ServicioVerificacion.getInstance().verificar(telefono, dtSucursal.getTelefono());
        ServicioVerificacion.getInstance().verificar(horario, dtSucursal.getHorario());
        ServicioVerificacion.getInstance().verificar(imagen, dtSucursal.getImagen());
    }
}