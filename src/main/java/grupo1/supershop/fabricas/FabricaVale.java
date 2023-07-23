package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorVale;
import grupo1.supershop.interfaces.controladores.IControladorVale;

public class FabricaVale {
    
    private FabricaVale() {
    }
    
    public static FabricaVale getInstance() {
        return FabricaValeHolder.INSTANCE;
    }
    
    private static class FabricaValeHolder {
        private static final FabricaVale INSTANCE = new FabricaVale();
    }
    
    public static IControladorVale getIControladorVale() {
            return new ControladorVale();
    }
}
