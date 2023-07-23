package grupo1.supershop.webservices.rest;

import grupo1.supershop.datatypes.DtActividad;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.datatypes.DtCompra;
import grupo1.supershop.datatypes.DtCompraProducto;
import grupo1.supershop.datatypes.DtVale;
import grupo1.supershop.fabricas.FabricaCompra;
import grupo1.supershop.interfaces.controladores.IControladorCompra;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/rest/compra")
public class RestCompra {

    private IControladorCompra iControladorCompra;

    public RestCompra() {
        this.iControladorCompra = FabricaCompra.getIControladorCompra();;
    }

    @GetMapping("/test")
    public String test() {
        return "TEST OK";
    }

    @GetMapping("/obtenerCompra/{compraId}/{secreto}")
    public DtCompra obtenerCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.obtenerCompra(compraId, secreto);
    }

    @GetMapping("/obtenerMiCompra/{compraId}/{secreto}")
    public DtCompra obtenerMiCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.obtenerMiCompra(compraId, secreto);
    }

    @GetMapping("/obtenerMiCompraProceso/{secreto}")
    public DtCompra obtenerMiCompraProceso(@PathVariable("secreto") String secreto) {
        return iControladorCompra.obtenerMiCompraProceso(secreto);
    }

    @GetMapping("/listarMiCompraProductos/{compraId}/{secreto}")
    public List<DtCompraProducto> listarMiCompraProductos(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarMiCompraProductos(compraId, secreto);
    }

    @GetMapping("/listarCompraProductos/{compraId}/{secreto}")
    public List<DtCompraProducto> listarCompraProductos(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarCompraProductos(compraId, secreto);
    }

    @GetMapping("/listarMisCompras/{secreto}")
    public List<DtCompra> listarMisCompras(@PathVariable("secreto") String secreto) {
        return iControladorCompra.listarMisCompras(secreto);
    }

    @GetMapping("/listarMisComprasPendientes/{secreto}")
    public List<DtCompra> listarMisComprasPendientes(@PathVariable("secreto") String secreto) {
        return iControladorCompra.listarMisComprasPendientes(secreto);
    }

    @GetMapping("/listarMisComprasConfirmadas/{secreto}")
    public List<DtCompra> listarMisComprasConfirmadas(@PathVariable("secreto") String secreto) {
        return iControladorCompra.listarMisComprasConfirmadas(secreto);
    }

    @GetMapping("/listarMisComprasConcluidas/{secreto}")
    public List<DtCompra> listarMisComprasConcluidas(@PathVariable("secreto") String secreto) {
        return iControladorCompra.listarMisComprasConcluidas(secreto);
    }

    @GetMapping("/listarMisComprasDevueltas/{secreto}")
    public List<DtCompra> listarMisComprasDevueltas(@PathVariable("secreto") String secreto) {
        return iControladorCompra.listarMisComprasDevueltas(secreto);
    }

    @GetMapping("/listarComprasUsuario/{compradorId}/{secreto}")
    public List<DtCompra> listarComprasUsuario(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasUsuario(compradorId, secreto);
    }

    @GetMapping("/listarComprasUsuarioProceso/{compradorId}/{secreto}")
    public List<DtCompra> listarComprasUsuarioProceso(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasUsuarioProceso(compradorId, secreto);
    }

    @GetMapping("/listarComprasUsuarioPendientes/{compradorId}/{secreto}")
    public List<DtCompra> listarComprasUsuarioPendientes(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasUsuarioPendientes(compradorId, secreto);
    }

    @GetMapping("/listarComprasUsuarioConfirmadas/{compradorId}/{secreto}")
    public List<DtCompra> listarComprasUsuarioConfirmadas(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasUsuarioConfirmadas(compradorId, secreto);
    }

    @GetMapping("/listarComprasUsuarioConcluidas/{compradorId}/{secreto}")
    public List<DtCompra> listarComprasUsuarioConcluidas(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasUsuarioConcluidas(compradorId, secreto);
    }

    @GetMapping("/listarComprasUsuarioDevueltas/{compradorId}/{secreto}")
    public List<DtCompra> listarComprasUsuarioDevueltas(@PathVariable("compradorId") Long compradorId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasUsuarioDevueltas(compradorId, secreto);
    }

    @GetMapping("/listarComprasSucursal/{sucursalId}/{secreto}")
    public List<DtCompra> listarComprasSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasSucursal(sucursalId, secreto);
    }

    @GetMapping("/listarComprasSucursalPendientes/{sucursalId}/{secreto}")
    public List<DtCompra> listarComprasSucursalPendientes(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasSucursalPendientes(sucursalId, secreto);
    }

    @GetMapping("/listarComprasSucursalConfirmadas/{sucursalId}/{secreto}")
    public List<DtCompra> listarComprasSucursalConfirmadas(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasSucursalConfirmadas(sucursalId, secreto);
    }

    @GetMapping("/listarComprasSucursalConcluidas/{sucursalId}/{secreto}")
    public List<DtCompra> listarComprasSucursalConcluidas(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasSucursalConcluidas(sucursalId, secreto);
    }

    @GetMapping("/listarComprasSucursalDevueltas/{sucursalId}/{secreto}")
    public List<DtCompra> listarComprasSucursalDevueltas(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarComprasSucursalDevueltas(sucursalId, secreto);
    }

    @GetMapping("/listarValesAplicados/{compraId}/{secreto}")
    public List<DtVale> listarValesAplicados(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarValesAplicados(compraId, secreto);
    }

    @GetMapping("/listarMisValesAplicados/{compraId}/{secreto}")
    public List<DtVale> listarMisValesAplicados(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarMisValesAplicados(compraId, secreto);
    }

    @GetMapping("/listarActividadCompra/{compraId}/{secreto}")
    public List<DtActividad> listarActividadCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarActividadCompra(compraId, secreto);
    }

    @PutMapping("/iniciarCompraDomicilio/{secreto}")
    public DtMensajeSistema iniciarCompraDomicilio(@PathVariable("secreto") String secreto) {
        return iControladorCompra.iniciarCompraDomicilio(secreto);
    }

    @PutMapping("/iniciarCompraSucursal/{secreto}")
    public DtMensajeSistema iniciarCompraSucursal(@PathVariable("secreto") String secreto) {
        return iControladorCompra.iniciarCompraSucursal(secreto);
    }

    @PutMapping("/cancelarCompra/{compraId}/{secreto}")
    public DtMensajeSistema cancelarCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.cancelarCompra(compraId, secreto);
    }

    @PutMapping("/confirmarCompra/{compraId}/{secreto}")
    public DtMensajeSistema confirmarCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.confirmarCompra(compraId, secreto);
    }

    @PutMapping("/concluirCompra/{compraId}/{secreto}")
    public DtMensajeSistema concluirCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.concluirCompra(compraId, secreto);
    }

    @PutMapping("/devolverCompra/{compraId}/{secreto}")
    public DtMensajeSistema devolverCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.devolverCompra(compraId, secreto);
    }

    @PostMapping("/aplicarMisVales/{compraId}/{secreto}")
    public DtMensajeSistema aplicarMisVales(@RequestBody List<DtVale> listaDtVales, @PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.aplicarMisVales(listaDtVales, compraId, secreto);
    }

    @PutMapping("/aplicarMetodoPagoEfectivo/{compraId}/{secreto}")
    public DtMensajeSistema aplicarMetodoPagoEfectivo(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.aplicarMetodoPagoEfectivo(compraId, secreto);
    }

    @PutMapping("/aplicarMetodoPagoPayPal/{compraId}/{secreto}")
    public DtMensajeSistema aplicarMetodoPagoPayPal(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.aplicarMetodoPagoPayPal(compraId, secreto);
    }

    @PutMapping("/aplicarMetodoEnvio/{compraId}/{metodoEnvioId}/{secreto}")
    public DtMensajeSistema aplicarMetodoEnvio(@PathVariable("compraId") Long compraId, @PathVariable("metodoEnvioId") Long metodoEnvioId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.aplicarMetodoEnvio(compraId, metodoEnvioId, secreto);
    }

    @PutMapping("/modificarDomicilio/{compraId}/{domicilioId}/{secreto}")
    public DtMensajeSistema modificarDomicilio(@PathVariable("compraId") Long compraId, @PathVariable("domicilioId") Long domicilioId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.modificarDomicilio(compraId, domicilioId, secreto);
    }

    @PostMapping("/aplicarObservacionesComprador/{compraId}/{secreto}")
    public DtMensajeSistema aplicarObservacionesComprador(@RequestBody String observaciones, @PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.aplicarObservacionesComprador(observaciones, compraId, secreto);
    }

    @PostMapping("/modificarObservacionesSucursal/{compraId}/{secreto}")
    public DtMensajeSistema modificarObservacionesSucursal(@RequestBody String observaciones, @PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.modificarObservacionesSucursal(observaciones, compraId, secreto);
    }

    @PutMapping("/finalizarCompra/{compraId}/{secreto}")
    public DtMensajeSistema finalizarCompra(@PathVariable("compraId") Long compraId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.finalizarCompra(compraId, secreto);
    }

    @GetMapping("/listarActividadesComprasConcluidasSucursal/{sucursalId}/{secreto}")
    public List<DtActividad> listarActividadesComprasConcluidasSucursal(@PathVariable("sucursalId") Long sucursalId, @PathVariable("secreto") String secreto) {
        return iControladorCompra.listarActividadesComprasConcluidasSucursal(sucursalId, secreto);
    }

}
