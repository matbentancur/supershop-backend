package grupo1.supershop.webservices.rest;

import grupo1.supershop.fabricas.FabricaPayPal;
import grupo1.supershop.interfaces.controladores.IControladorPayPal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/paypal")
public class RestPayPal {

    private IControladorPayPal iControladorPayPal;

    public RestPayPal() {
        this.iControladorPayPal = FabricaPayPal.getIControladorPayPal();
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/crearOrden/{compraId}/{secreto}")
    public String crearOrden(@PathVariable Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorPayPal.crearOrden(compraId, secreto);
    }

    @GetMapping("/capturarPago/{compraId}/{secreto}")
    public String capturarPago(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorPayPal.capturarPago(compraId, secreto);
    }

}
