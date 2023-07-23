package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorStock;
import grupo1.supershop.interfaces.controladores.IControladorStock;

public class FabricaStock {
    
    private FabricaStock() {
    }
    
    public static FabricaStock getInstance() {
        return FabricaStockHolder.INSTANCE;
    }
    
    private static class FabricaStockHolder {
        private static final FabricaStock INSTANCE = new FabricaStock();
    }
    
    public static IControladorStock getIControladorStock() {
            return new ControladorStock();
    }
}
