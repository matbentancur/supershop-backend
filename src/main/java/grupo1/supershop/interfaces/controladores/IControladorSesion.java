package grupo1.supershop.interfaces.controladores;

import grupo1.supershop.datatypes.DtInicioSesion;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtSesion;

public interface IControladorSesion {

    public DtSesion obtenerSesion(String email, String password);
    
    public DtSesion obtenerSesionSeguro(DtInicioSesion dtInicioSesion);

    public DtMensajeSistema iniciarSesion(String email, String password);
    
    public DtMensajeSistema iniciarSesionSeguro(DtInicioSesion dtInicioSesion);

    public DtMensajeSistema cerrarSesion(String secret);
    
    public DtMensajeSistema validarSesion(String secret);

}
