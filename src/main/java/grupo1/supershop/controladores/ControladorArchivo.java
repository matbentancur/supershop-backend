package grupo1.supershop.controladores;

import grupo1.supershop.beans.Archivo;
import grupo1.supershop.beans.Usuario;
import grupo1.supershop.datatypes.DtArchivo;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.interfaces.controladores.IControladorArchivo;
import grupo1.supershop.persistencia.manejadores.ManejadorArchivo;
import grupo1.supershop.servicios.ServicioArchivo;
import grupo1.supershop.servicios.ServicioMensajeSistema;
import grupo1.supershop.servicios.ServicioPermiso;
import grupo1.supershop.servicios.ServicioSesion;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class ControladorArchivo implements IControladorArchivo {

    @Override
    public DtArchivo obtenerArchivo(Long archivoId) {
        //DEBERIA VERIFICAR LA SESION

        //DEBERIA VERIFICAR EL PERMISO DEL USUARIO
        Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(archivoId);
        if (archivo == null) {
            return null;
        }
        return (DtArchivo) archivo.getDataType();
    }

    @Override
    public DtMensajeSistema crearArchivo(MultipartFile archivoSubir, String archivoNombre, String secreto) {
        try {

            //OBTIENE USUARIO
            Usuario usuario = ServicioSesion.getInstance().obtenerUsuario(secreto);
            if (usuario == null) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Sesion.exists.error");
            }

            //VERIFICA PERMISO
            if (!ServicioPermiso.getInstance().permisoAdministradores(usuario)) {
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Usuario.denied.error");
            }

            DtMensajeSistema dtMensajeSistema = ServicioArchivo.getInstance().subirArchivoTemporal(archivoSubir, archivoNombre);
            if (!dtMensajeSistema.isExitoso()) {
                return dtMensajeSistema;
            }

            Archivo archivo = new Archivo();
            archivo.setNombre(archivoNombre);
            archivo = ServicioArchivo.getInstance().obtenerMetadatos(archivo);
            if (archivo == null) {
                ServicioArchivo.getInstance().borrarArchivoTemporal(archivoNombre);
                return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.create.error");
            }

            dtMensajeSistema = ServicioArchivo.getInstance().moverArchivoTemporal(archivo.getNombre());
            if (!dtMensajeSistema.isExitoso()) {
                ServicioArchivo.getInstance().borrarArchivoTemporal(archivoNombre);
                return dtMensajeSistema;
            }

            ManejadorArchivo.getInstance().crearArchivo(archivo);

            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Archivo.create.success");
        } catch (Exception ex) {
            Logger.getLogger(ControladorArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.create.error");
        }
    }

    @Override
    public Resource descargarArchivo(Long archivoId) {
        Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(archivoId);
        if (archivo == null) {
            return null;
        }

        return ServicioArchivo.getInstance().descargarArchivoResource(archivo);
    }

}
