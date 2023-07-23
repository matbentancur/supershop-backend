package grupo1.supershop.instalar.cargar;

public class CargarDatos {

    private final CargarServidorCorreo cargarServidorCorreo;
    private final CargarSucursal cargarSucursales;
    private final CargarAdminSistema cargarAdminSistema;
    private final CargarAdminSucursal cargarAdminSucursal;
    private final CargarAtencionCliente cargarAtencionCliente;
    private final CargarComprador cargarComprador;
    private final CargarDomicilio cargarDomicilio;
    private final CargarCategoria cargarCategoria;
    private final CargarProducto cargarProducto;
    private final CargarStock cargarStock;
    private final CargarMetodoEnvio cargarMetodoEnvio;
    private final CargarPromocion cargarPromocion;
    private final CargarVale cargarVale;
    private final CargarVenta cargarVenta;
    private final CargarConversacion cargarConversacion;
    private final CargarReclamo cargarReclamo;

    public CargarDatos() {
        this.cargarServidorCorreo = new CargarServidorCorreo();
        this.cargarAdminSistema = new CargarAdminSistema();
        this.cargarSucursales = new CargarSucursal();
        this.cargarAdminSucursal = new CargarAdminSucursal();
        this.cargarAtencionCliente = new CargarAtencionCliente();
        this.cargarComprador = new CargarComprador();
        this.cargarDomicilio = new CargarDomicilio();
        this.cargarCategoria = new CargarCategoria();
        this.cargarProducto = new CargarProducto();
        this.cargarStock = new CargarStock();
        this.cargarMetodoEnvio = new CargarMetodoEnvio();
        this.cargarPromocion = new CargarPromocion();
        this.cargarVale = new CargarVale();
        this.cargarVenta = new CargarVenta();
        this.cargarConversacion = new CargarConversacion();
        this.cargarReclamo = new CargarReclamo();
    }

    public void cargarDatosProduccion() throws Exception {
        this.crearDatosProduccion();
    }

    public void cargarDatosDesarrolo() throws Exception {
        this.crearDatosDesarrollo();
    }

    private void crearDatosProduccion() throws Exception {
        this.cargarServidorCorreo.crearServidorCorreo();
        this.cargarAdminSistema.cargarUsuarios();
        this.cargarSucursales.cargarSucursales();
        this.cargarAdminSucursal.cargarUsuarios();
        this.cargarAtencionCliente.cargarUsuarios();
        this.cargarComprador.cargarUsuarios();
        this.cargarDomicilio.cargarDomicilios();
        this.cargarCategoria.cargarCategorias();
        this.cargarProducto.cargarProductos();
        this.cargarStock.cargarStock();
        this.cargarMetodoEnvio.cargarMetodosDeEnvios();
        this.cargarPromocion.cargarPromociones();
        this.cargarVale.cargarVales();
        this.cargarVenta.cargarVentas();
        this.cargarConversacion.cargarConversaciones();
        this.cargarReclamo.cargarReclamos();
    }

    private void crearDatosDesarrollo() throws Exception {
        this.cargarServidorCorreo.crearServidorCorreo();
        this.cargarAdminSistema.cargarUsuarios();
        this.cargarSucursales.cargarSucursales();
        this.cargarAdminSucursal.cargarUsuarios();
        this.cargarAtencionCliente.cargarUsuarios();
        this.cargarComprador.cargarUsuarios();
        this.cargarDomicilio.cargarDomicilios();
        this.cargarCategoria.cargarCategorias();
        this.cargarProducto.cargarProductos();
        this.cargarStock.cargarStock();
        this.cargarMetodoEnvio.cargarMetodosDeEnvios();
        this.cargarPromocion.cargarPromociones();
        this.cargarVale.cargarVales();
        this.cargarConversacion.cargarConversaciones();
        this.cargarReclamo.cargarReclamos();
    }
}
