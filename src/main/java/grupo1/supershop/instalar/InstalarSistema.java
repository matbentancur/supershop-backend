package grupo1.supershop.instalar;

import grupo1.supershop.instalar.cargar.CargarDatos;

public class InstalarSistema {

    public void instalar() throws Exception {
        //CARGAR DATOS
        CargarDatos cargarDatos = new CargarDatos();

        String entorno = "produccion";
//        String entorno = "desarrollo";

        if (entorno.equals("produccion")) {
            cargarDatos.cargarDatosProduccion();
        } else {
            cargarDatos.cargarDatosDesarrolo();
        }

    }

}
