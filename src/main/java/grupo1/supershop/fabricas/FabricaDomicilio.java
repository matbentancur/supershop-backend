package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorDomicilio;
import grupo1.supershop.interfaces.controladores.IControladorDomicilio;

public class FabricaDomicilio {
    
    private FabricaDomicilio() {
    }
    
    public static FabricaDomicilio getInstance() {
        return FabricaDomicilioHolder.INSTANCE;
    }
    
    private static class FabricaDomicilioHolder {
        private static final FabricaDomicilio INSTANCE = new FabricaDomicilio();
    }
    
    public static IControladorDomicilio getIControladorDomicilio() {
            return new ControladorDomicilio();
    }
}
