package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorPromocion;
import grupo1.supershop.interfaces.controladores.IControladorPromocion;

public class FabricaPromocion {
    
    private FabricaPromocion() {
    }
    
    public static FabricaPromocion getInstance() {
        return FabricaPromocionHolder.INSTANCE;
    }
    
    private static class FabricaPromocionHolder {
        private static final FabricaPromocion INSTANCE = new FabricaPromocion();
    }
    
    public static IControladorPromocion getIControladorPromocion() {
            return new ControladorPromocion();
    }
}
