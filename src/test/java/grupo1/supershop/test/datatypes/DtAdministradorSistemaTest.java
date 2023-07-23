package grupo1.supershop.test.datatypes;

import grupo1.supershop.config.servicios.ServicioVerificacion;
import grupo1.supershop.servicios.ServicioFechaHora;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

class DtAdministradorSistemaTest extends ConfiguracionTest {

    private String apellido;
    private boolean bloqueado;
    private boolean borrado;
    private String celular;
    private String password;
    private Date creacion;
    private String email;
    private Long id;
    private Date nacimiento;
    private String nombre;

    @BeforeEach
    public void configurar() {
        apellido = "Perez";
        bloqueado = false;
        borrado = false;
        celular = "099123456";
        password = "qwerty";
        creacion = new Date();
        email = "test@test.com.uy";
        id = 7L;
        nacimiento = ServicioFechaHora.getInstance().stringToDate("09/01/2000");
        nombre = "Leonardo";
    }

    @DisplayName("Crear DtAdministradorSistema - Getter/Setter")
    @Test
    void crear() {
        dtAdministradorSistema.setApellidos(apellido);
        dtAdministradorSistema.setBloqueado(bloqueado);
        dtAdministradorSistema.setBorrado(borrado);
        dtAdministradorSistema.setCelular(celular);
        dtAdministradorSistema.setPassword(password);
        dtAdministradorSistema.setCreacion(creacion);
        dtAdministradorSistema.setEmail(email);
        dtAdministradorSistema.setId(id);
        dtAdministradorSistema.setNacimiento(nacimiento);
        dtAdministradorSistema.setNombres(nombre);

        ServicioVerificacion.getInstance().verificar(apellido, dtAdministradorSistema.getApellidos());
        ServicioVerificacion.getInstance().verificar(bloqueado, dtAdministradorSistema.isBloqueado());
        ServicioVerificacion.getInstance().verificar(borrado, dtAdministradorSistema.isBorrado());
        ServicioVerificacion.getInstance().verificar(celular, dtAdministradorSistema.getCelular());
        ServicioVerificacion.getInstance().verificar(password, dtAdministradorSistema.getPassword());
        ServicioVerificacion.getInstance().verificar(creacion, dtAdministradorSistema.getCreacion());
        ServicioVerificacion.getInstance().verificar(email, dtAdministradorSistema.getEmail());
        ServicioVerificacion.getInstance().verificar(id, dtAdministradorSistema.getId());
        ServicioVerificacion.getInstance().verificar(nacimiento, dtAdministradorSistema.getNacimiento());
        ServicioVerificacion.getInstance().verificar(nombre, dtAdministradorSistema.getNombres());
    }
}