package grupo1.supershop.fabricas;

import grupo1.supershop.controladores.ControladorCategoria;
import grupo1.supershop.interfaces.controladores.IControladorCategoria;

public class FabricaCategoria {
    
    private FabricaCategoria() {
    }
    
    public static FabricaCategoria getInstance() {
        return FabricaCategoriaHolder.INSTANCE;
    }
    
    private static class FabricaCategoriaHolder {
        private static final FabricaCategoria INSTANCE = new FabricaCategoria();
    }
    
    public static IControladorCategoria getIControladorCategoria() {
            return new ControladorCategoria();
    }
}
