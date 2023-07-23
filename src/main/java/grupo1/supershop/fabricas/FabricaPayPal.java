package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorPayPal;
import grupo1.supershop.interfaces.controladores.IControladorPayPal;

public class FabricaPayPal {
    
    private FabricaPayPal() {
    }
    
    public static FabricaPayPal getInstance() {
        return FabricaPayPalHolder.INSTANCE;
    }
    
    private static class FabricaPayPalHolder {
        private static final FabricaPayPal INSTANCE = new FabricaPayPal();
    }
    
    public static IControladorPayPal getIControladorPayPal() {
            return new ControladorPayPal();
    }
}
