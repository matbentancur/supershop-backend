package grupo1.supershop.servicios;

import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.validadores.ValidadorUsuario;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServicioAutenticacion {
    
    private static ServicioAutenticacion instance = null;
    
    private ServicioAutenticacion() {
    }
    
    public static ServicioAutenticacion getInstance() {
        if (instance == null) {
            instance = new ServicioAutenticacion();
        }
        return instance;
    }
   
    public DtMensajeSistema autenticarUsuario(String usuario, String password){
        try {
            
            DtMensajeSistema dtMensajeSistema = ValidadorUsuario.getInstance().autenticarUsuario(usuario, password);
            if(!dtMensajeSistema.isExitoso()){
                return dtMensajeSistema;
            }
            
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Usuario.autentication.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioAutenticacion.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.autentication.error");
        }
    }

}
