package grupo1.supershop.instalar.cargar;

import grupo1.supershop.beans.Actividad;
import grupo1.supershop.beans.Compra;
import grupo1.supershop.fabricas.FabricaCarrito;
import grupo1.supershop.fabricas.FabricaCompra;
import grupo1.supershop.fabricas.FabricaSesion;
import grupo1.supershop.interfaces.controladores.IControladorCarrito;
import grupo1.supershop.interfaces.controladores.IControladorCompra;
import grupo1.supershop.interfaces.controladores.IControladorSesion;
import grupo1.supershop.persistencia.manejadores.ManejadorActividad;
import grupo1.supershop.persistencia.manejadores.ManejadorBase;
import grupo1.supershop.persistencia.manejadores.ManejadorCompra;
import grupo1.supershop.servicios.ServicioFechaHora;

import java.util.Date;

public class CargarVenta {

    private static final String EMAIL_COMPRADOR = "leonardogabrielperez1993@gmail.com";
    private static final String PASSW_COMPRADOR = "leonardogabrielperez1993@gmail.com";
    private static final String EMAIL_COMPRADOR2 = "comprador@supershop.uy";
    private static final String PASSW_COMPRADOR2 = "comprador@supershop.uy";
    private static final String EMAIL_COMPRADOR3 = "comprador2@supershop.uy";
    private static final String PASSW_COMPRADOR3 = "comprador2@supershop.uy";
    private static final String EMAIL_COMPRADOR4 = "comprador3@supershop.uy";
    private static final String PASSW_COMPRADOR4 = "comprador3@supershop.uy";

    private static final String EMAIL_COMPRADOR5 = "comprador4@supershop.uy";
    private static final String PASSW_COMPRADOR5 = "comprador4@supershop.uy";
    private static final String EMAIL_COMPRADOR6 = "comprador5@supershop.uy";
    private static final String PASSW_COMPRADOR6 = "comprador5@supershop.uy";
    private static final String EMAIL_COMPRADOR7 = "comprador6@supershop.uy";
    private static final String PASSW_COMPRADOR7 = "comprador6@supershop.uy";
    private static final String EMAIL_COMPRADOR8 = "comprador7@supershop.uy";
    private static final String PASSW_COMPRADOR8 = "comprador7@supershop.uy";

    private static final String EMAIL_SUCURSAL = "adminsucursal@supershop.uy";
    private static final String PASSW_SUCURSAL = "adminsucursal@supershop.uy";
    private static final String EMAIL_SUCURSAL2 = "adminsucursal2@supershop.uy";
    private static final String PASSW_SUCURSAL2 = "adminsucursal2@supershop.uy";

    private final IControladorCarrito icCarrito;
    private final IControladorCompra icCompra;
    private final IControladorSesion icSesion;

    public CargarVenta() {
        icCarrito = FabricaCarrito.getIControladorCarrito();
        icCompra = FabricaCompra.getIControladorCompra();
        icSesion = FabricaSesion.getIControladorSesion();
    }

    public void cargarVentas() throws Exception {
        ventas1();
        ventas2();
        cambiarFechaActividad();
        cambiarFechaVentas();
    }

    private void ventas1() {
        String secretoSucursal = iniciarSesion(EMAIL_SUCURSAL, PASSW_SUCURSAL).obtenerSecreto(EMAIL_SUCURSAL, PASSW_SUCURSAL);
        String[][] datos = {
                {"1", "1", "1", EMAIL_COMPRADOR, PASSW_COMPRADOR, "si", "2"},
                {"1", "12", "1", EMAIL_COMPRADOR, PASSW_COMPRADOR, "si", "1"},
                {"1", "22", "1", EMAIL_COMPRADOR, PASSW_COMPRADOR, "si", "4"},
                {"1", "32", "1", EMAIL_COMPRADOR, PASSW_COMPRADOR, "no", "1"},
                {"1", "42", "1", EMAIL_COMPRADOR, PASSW_COMPRADOR, "no", "5"},
                {"1", "52", "1", EMAIL_COMPRADOR2, PASSW_COMPRADOR2, "si", "8"},
                {"1", "62", "1", EMAIL_COMPRADOR2, PASSW_COMPRADOR2, "si", "3"},
                {"1", "72", "1", EMAIL_COMPRADOR2, PASSW_COMPRADOR2, "si", "2"},
                {"1", "82", "1", EMAIL_COMPRADOR2, PASSW_COMPRADOR2, "no", "2"},
                {"1", "92", "1", EMAIL_COMPRADOR2, PASSW_COMPRADOR2, "no", "2"},
                {"1", "3", "1", EMAIL_COMPRADOR3, PASSW_COMPRADOR3, "si", "6"},
                {"1", "17", "1", EMAIL_COMPRADOR3, PASSW_COMPRADOR3, "si", "1"},
                {"1", "28", "1", EMAIL_COMPRADOR3, PASSW_COMPRADOR3, "si", "7"},
                {"1", "33", "1", EMAIL_COMPRADOR3, PASSW_COMPRADOR3, "no", "4"},
                {"1", "49", "1", EMAIL_COMPRADOR3, PASSW_COMPRADOR3, "no", "1"},
                {"1", "55", "1", EMAIL_COMPRADOR4, PASSW_COMPRADOR4, "si", "5"},
                {"1", "68", "1", EMAIL_COMPRADOR4, PASSW_COMPRADOR4, "si", "9"},
                {"1", "73", "1", EMAIL_COMPRADOR4, PASSW_COMPRADOR4, "si", "9"},
                {"1", "90", "1", EMAIL_COMPRADOR4, PASSW_COMPRADOR4, "no", "1"},
                {"1", "97", "1", EMAIL_COMPRADOR4, PASSW_COMPRADOR4, "no", "7"}
        };

        for (String[] val : datos) {
            Long idSucursal = Long.parseLong(val[0]);
            Long idProducto = Long.parseLong(val[1]);
            Long idMetodoEnvio = Long.parseLong(val[2]);
            String email = val[3];
            String pass = val[4];
            String secretoComprador = iniciarSesion(email, pass).obtenerSecreto(email, pass);
            String confirmar = val[5];
            int cantidad = Integer.parseInt(val[6]);

            System.out.println(icCarrito.crearCarrito(idSucursal, idProducto, cantidad, secretoComprador).getMensajes());
            System.out.println(icCarrito.agregarProducto(8L, 2, secretoComprador).getMensajes());
            System.out.println(icCarrito.agregarProducto(27L, 6, secretoComprador).getMensajes());
            System.out.println(icCompra.iniciarCompraSucursal(secretoComprador).getMensajes());
            Long idCompra = icCompra.obtenerMiCompraProceso(secretoComprador).getId();
            icCompra.aplicarMetodoPagoEfectivo(idCompra, secretoComprador);
            icCompra.aplicarMetodoEnvio(idCompra, idMetodoEnvio, secretoComprador);
            icCompra.finalizarCompra(idCompra, secretoComprador);

            if (confirmar.equals("si")) {
                icCompra.confirmarCompra(idCompra, secretoSucursal);
                icCompra.concluirCompra(idCompra, secretoSucursal);
            }

            cerrarSesion(secretoComprador);
        }

        cerrarSesion(secretoSucursal);
    }

    private void ventas2() {

        String secretoSucursal = iniciarSesion(EMAIL_SUCURSAL2, PASSW_SUCURSAL2).obtenerSecreto(EMAIL_SUCURSAL2, PASSW_SUCURSAL2);
        String[][] datos = {
                {"2", "1", "1", EMAIL_COMPRADOR5, PASSW_COMPRADOR5, "si", "2"},
                {"2", "12", "1", EMAIL_COMPRADOR5, PASSW_COMPRADOR5, "si", "1"},
                {"2", "22", "1", EMAIL_COMPRADOR5, PASSW_COMPRADOR5, "si", "4"},
                {"2", "32", "1", EMAIL_COMPRADOR5, PASSW_COMPRADOR5, "no", "1"},
                {"2", "42", "1", EMAIL_COMPRADOR5, PASSW_COMPRADOR5, "no", "5"},
                {"2", "52", "1", EMAIL_COMPRADOR6, PASSW_COMPRADOR6, "si", "8"},
                {"2", "62", "1", EMAIL_COMPRADOR6, PASSW_COMPRADOR6, "si", "3"},
                {"2", "72", "1", EMAIL_COMPRADOR6, PASSW_COMPRADOR6, "si", "2"},
                {"2", "82", "1", EMAIL_COMPRADOR6, PASSW_COMPRADOR6, "no", "2"},
                {"2", "92", "1", EMAIL_COMPRADOR6, PASSW_COMPRADOR6, "no", "2"},
                {"2", "3", "1", EMAIL_COMPRADOR7, PASSW_COMPRADOR7, "si", "6"},
                {"2", "17", "1", EMAIL_COMPRADOR7, PASSW_COMPRADOR7, "si", "1"},
                {"2", "28", "1", EMAIL_COMPRADOR7, PASSW_COMPRADOR7, "si", "7"},
                {"2", "33", "1", EMAIL_COMPRADOR7, PASSW_COMPRADOR7, "no", "4"},
                {"2", "49", "1", EMAIL_COMPRADOR7, PASSW_COMPRADOR7, "no", "1"},
                {"2", "55", "1", EMAIL_COMPRADOR8, PASSW_COMPRADOR8, "si", "5"},
                {"2", "68", "1", EMAIL_COMPRADOR8, PASSW_COMPRADOR8, "si", "9"},
                {"2", "73", "1", EMAIL_COMPRADOR8, PASSW_COMPRADOR8, "si", "9"},
                {"2", "90", "1", EMAIL_COMPRADOR8, PASSW_COMPRADOR8, "no", "1"},
                {"2", "97", "1", EMAIL_COMPRADOR8, PASSW_COMPRADOR8, "no", "7"}
        };

        for (String[] val : datos) {
            Long idSucursal = Long.parseLong(val[0]);
            Long idProducto = Long.parseLong(val[1]);
            Long idMetodoEnvio = Long.parseLong(val[2]);
            String email = val[3];
            String pass = val[4];
            String secretoComprador = iniciarSesion(email, pass).obtenerSecreto(email, pass);
            String confirmar = val[5];
            int cantidad = Integer.parseInt(val[6]);

            icCarrito.crearCarrito(idSucursal, idProducto, cantidad, secretoComprador);
            icCarrito.agregarProducto(5L, 2, secretoComprador);
            icCarrito.agregarProducto(71L, 1, secretoComprador);
            icCompra.iniciarCompraSucursal(secretoComprador);
            Long idCompra = icCompra.obtenerMiCompraProceso(secretoComprador).getId();
            icCompra.aplicarMetodoPagoEfectivo(idCompra, secretoComprador);
            icCompra.aplicarMetodoEnvio(idCompra, idMetodoEnvio, secretoComprador);
            icCompra.finalizarCompra(idCompra, secretoComprador);

            if (confirmar.equals("si")) {
                icCompra.confirmarCompra(idCompra, secretoSucursal);
                icCompra.concluirCompra(idCompra, secretoSucursal);
            }

            cerrarSesion(secretoComprador);
        }

        cerrarSesion(secretoSucursal);
    }

    private void cambiarFechaVentas() throws Exception {
        String fecha1 = "01/07/2023 12:00";
        String fecha2 = "02/07/2023 12:00";
        String fecha3 = "03/07/2023 12:00";
        String fecha4 = "04/07/2023 12:00";
        String fecha5 = "05/07/2023 12:00";
        String fecha6 = "06/07/2023 12:00";
        String fecha7 = "07/07/2023 12:00";
        String fecha8 = "08/07/2023 12:00";
        String fecha9 = "09/07/2023 12:00";
        String fecha10 = "10/07/2023 12:00";
        String fecha11 = "11/07/2023 12:00";
        String fecha12 = "12/07/2023 12:00";
        String fecha13 = "13/07/2023 12:00";

        String[][] fechas = {
                {"1", fecha1}, {"2", fecha1}, {"3", fecha1},
                {"4", fecha2}, {"5", fecha2}, {"6", fecha2},
                {"7", fecha3}, {"8", fecha3}, {"9", fecha3},
                {"10", fecha4}, {"11", fecha4}, {"12", fecha4},
                {"13", fecha5}, {"14", fecha5}, {"15", fecha5},
                {"16", fecha6}, {"17", fecha6}, {"18", fecha6},
                {"19", fecha7}, {"20", fecha7}, {"21", fecha7},
                {"22", fecha8}, {"23", fecha8}, {"24", fecha8},
                {"25", fecha9}, {"26", fecha9}, {"27", fecha9},
                {"28", fecha10}, {"29", fecha10}, {"30", fecha10},
                {"31", fecha11}, {"32", fecha11}, {"33", fecha11},
                {"34", fecha12}, {"35", fecha12}, {"36", fecha12},
                {"37", fecha13}, {"38", fecha13}, {"39", fecha13}, {"40", fecha13},
        };

        for (String[] fecha : fechas) {
            String id = fecha[0];
            String fechaNueva = fecha[1];

            Compra compra = ManejadorCompra.getInstance().obtenerCompra(Long.parseLong(id));
            compra.setCreacion(ServicioFechaHora.getInstance().stringToDate(fechaNueva));

            ManejadorBase.getInstance().modificarBean(compra);
        }

    }

    private void cambiarFechaActividad() {
        String fecha1 = "01/07/2023 10:00";
        String fecha2 = "01/07/2023 10:10";
        String fecha3 = "01/07/2023 10:25";
        String fecha4 = "01/07/2023 10:35";
        String fecha5 = "01/07/2023 12:10";
        String[][] sql = {
                {fecha1, "187"}, {fecha1, "195"}, {fecha1, "203"},
                {fecha1, "223"}, {fecha1, "231"}, {fecha1, "239"},
                {fecha1, "259"}, {fecha1, "267"}, {fecha1, "275"},
                {fecha1, "295"}, {fecha1, "303"}, {fecha1, "311"},
                {fecha2, "189"}, {fecha2, "197"}, {fecha2, "205"},
                {fecha3, "225"}, {fecha3, "233"}, {fecha3, "241"},
                {fecha4, "261"}, {fecha4, "269"}, {fecha4, "277"},
                {fecha5, "297"}, {fecha5, "305"}, {fecha5, "313"}
        };


        for (String[] val : sql) {
            Date date = ServicioFechaHora.getInstance().stringToDateTime(val[0]);
            Long id = Long.parseLong(val[1]);

            Actividad a = ManejadorActividad.getInstance().obtenerActividad(id);
            a.setMomento(date);

            try {
                ManejadorBase.getInstance().modificarBean(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private CargarVenta iniciarSesion(String email, String passw) {
        icSesion.iniciarSesion(email, passw);
        return this;
    }

    private String obtenerSecreto(String email, String passw) {
        return icSesion.obtenerSesion(email, passw).getSecreto();
    }

    private void cerrarSesion(String secreto) {
        icSesion.cerrarSesion(secreto);
    }

}
