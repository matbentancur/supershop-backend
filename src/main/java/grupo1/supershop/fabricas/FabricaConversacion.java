package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorConversacion;
import grupo1.supershop.interfaces.controladores.IControladorConversacion;

public class FabricaConversacion {
    
    private FabricaConversacion() {
    }
    
    public static FabricaConversacion getInstance() {
        return FabricaConversacionHolder.INSTANCE;
    }
    
    private static class FabricaConversacionHolder {
        private static final FabricaConversacion INSTANCE = new FabricaConversacion();
    }
    
    public static IControladorConversacion getIControladorConversacion() {
            return new ControladorConversacion();
    }
}
