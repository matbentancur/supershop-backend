package grupo1.supershop.test.datatypes;

import grupo1.supershop.datatypes.*;
import org.junit.jupiter.api.BeforeAll;

public class ConfiguracionTest {

    protected static DtActividad dtActividad;
    protected static DtAdministradorSistema dtAdministradorSistema;
    protected static DtAdministradorSucursal dtAdministradorSucursal;
    protected static DtArchivo dtArchivo;
    protected static DtAtencionCliente dtAtencionCliente;
    protected static DtCarrito dtCarrito;
    protected static DtCarritoProducto dtCarritoProducto;
    protected static DtCategoria dtCategoria;
    protected static DtCompraDomicilio dtCompraDomicilio;
    protected static DtCompraProducto dtCompraProducto;
    protected static DtCompraSucursal dtCompraSucursal;
    protected static DtConversacion dtConversacion;
    protected static DtDomicilio dtDomicilio;
    protected static DtMensaje dtMensaje;
    protected static DtMensajeSistema dtMensajeSistema;
    protected static DtMetodoEnvio dtMetodoEnvio;
    protected static DtProducto dtProducto;
    protected static DtPromocion dtPromocion;
    protected static DtVale dtVale;
    protected static DtStock dtStock;
    protected static DtReclamo dtReclamo;
    protected static DtSucursal dtSucursal;

    @BeforeAll
    public static void configurarPruebas() {
        dtActividad = new DtActividad();
        dtAdministradorSistema = new DtAdministradorSistema();
        dtAdministradorSucursal = new DtAdministradorSucursal();
        dtArchivo = new DtArchivo();
        dtAtencionCliente = new DtAtencionCliente();
        dtCarrito = new DtCarrito();
        dtCarritoProducto = new DtCarritoProducto();
        dtCategoria = new DtCategoria();
        dtCompraDomicilio = new DtCompraDomicilio();
        dtCompraProducto = new DtCompraProducto();
        dtCompraSucursal = new DtCompraSucursal();
        dtConversacion = new DtConversacion();
        dtDomicilio = new DtDomicilio();
        dtMensaje = new DtMensaje();
        dtMensajeSistema = new DtMensajeSistema();
        dtMetodoEnvio = new DtMetodoEnvio();
        dtProducto = new DtProducto();
        dtPromocion = new DtPromocion();
        dtVale = new DtVale();
        dtStock = new DtStock();
        dtReclamo = new DtReclamo();
        dtSucursal = new DtSucursal();
    }
}
