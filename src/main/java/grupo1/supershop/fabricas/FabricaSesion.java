package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorSesion;
import grupo1.supershop.interfaces.controladores.IControladorSesion;

public class FabricaSesion {
    
    private FabricaSesion() {
    }
    
    public static FabricaSesion getInstance() {
        return FabricaSesionHolder.INSTANCE;
    }
    
    private static class FabricaSesionHolder {
        private static final FabricaSesion INSTANCE = new FabricaSesion();
    }
    
    public static IControladorSesion getIControladorSesion() {
            return new ControladorSesion();
    }
}
