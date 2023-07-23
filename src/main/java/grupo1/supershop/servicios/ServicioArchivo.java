package grupo1.supershop.servicios;

import grupo1.supershop.beans.Archivo;
import grupo1.supershop.datatypes.DtMensajeSistema;
import grupo1.supershop.persistencia.manejadores.ManejadorArchivo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class ServicioArchivo {

    private static ServicioArchivo instance = null;
//    private static String filesDirectory = "src/main/resources/static/";
//    private static String tempDirectory = "src/main/resources/tmp/";
//    private static String fileUrl = "http://localhost:8080/supershop/";
    private static String filesDirectory = "/tmp/static/";
    private static String tempDirectory = "/tmp/tmp/";
    private static String fileUrl = "http://localhost:8080/supershop/";

    private ServicioArchivo() {
    }

    public static ServicioArchivo getInstance() {
        if (instance == null) {
            instance = new ServicioArchivo();
        }
        return instance;
    }

    public DtMensajeSistema subirArchivoTemporal(MultipartFile archivoSubir, String archivoSubirNombre) {
        try {
            this.crearDirectorios();

            Path path = Paths.get(tempDirectory + archivoSubirNombre);

            InputStream input = archivoSubir.getInputStream();
            File nuevoArchivo = new File(path.toString());

            OutputStream outputStream = new FileOutputStream(nuevoArchivo);

            byte[] b = new byte[100000];
            int bytesRead = 0;
            while ((bytesRead = input.read(b)) != -1) {
                outputStream.write(b, 0, bytesRead);
            }
            outputStream.close();
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Archivo.upload.success");

        } catch (IOException ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.upload.error");
        }
    }

    public DtMensajeSistema borrarArchivoTemporal(String archivoNombre) {
        try {
            Path path = Paths.get(tempDirectory + archivoNombre);
            Files.deleteIfExists(path);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Archivo.delete.success");
        } catch (IOException ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.delete.error");
        }
    }

    public DtMensajeSistema moverArchivoTemporal(String archivoNombre) {
        try {
            this.crearDirectorios();

            Path tempPath = Paths.get(tempDirectory + archivoNombre);
            Path filePath = Paths.get(filesDirectory + archivoNombre);

            Files.move(tempPath, filePath, StandardCopyOption.REPLACE_EXISTING);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Archivo.moved.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.moved.error");
        }
    }

    public DtMensajeSistema borrarArchivo(Long id) {
        try {
            Archivo archivo = ManejadorArchivo.getInstance().obtenerArchivo(id);
            Path path = Paths.get(archivo.getUbicacion());
            Files.deleteIfExists(path);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Archivo.delete.success");
        } catch (IOException ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.delete.error");
        }
    }

    public Resource descargarArchivoResource(Archivo archivo) {
        try {
            Path path = Paths.get(filesDirectory + archivo.getNombre());
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Archivo obtenerMetadatos(Archivo archivo) {
        try {
            Path path = Paths.get(tempDirectory + archivo.getNombre());

            File archivoSubido = new File(path.toString());

            String mimeType = Files.probeContentType(path);
            if (mimeType == null) {
                return null;
            }
            archivo.setMime(mimeType);

            String extension = this.obtenerExtension(archivo.getMime());
            if (extension == null) {
                return null;
            }
            archivo.setExtension(extension);

            archivo.setPeso(archivoSubido.length());
            archivo.setUbicacion(filesDirectory + archivo.getNombre());
            archivo.setUrl(fileUrl + archivo.getNombre());

            return archivo;

        } catch (Exception ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String obtenerExtension(String mimeType) {
        switch (mimeType) {
            case "image/png":
                return "png";
            case "image/gif":
                return "gif";
            case "image/jpeg":
                return "jpg";
            case "image/svg+xml":
                return "svg";
            default:
                return null;
        }

    }

    public String agregarExtension(String mimeType) {
        switch (mimeType) {
            case "image/png":
                return ".png";
            case "image/gif":
                return ".gif";
            case "image/jpeg":
                return ".jpg";
            case "image/svg+xml":
                return ".svg";
            default:
                return null;
        }

    }

    private DtMensajeSistema crearDirectorios() {
        try {
            File directorioTemporal = new File(tempDirectory);
            if (!directorioTemporal.exists()) {
                directorioTemporal.mkdirs();
            }

            File directorioArchivos = new File(filesDirectory);
            if (!directorioArchivos.exists()) {
                directorioArchivos.mkdirs();
            }
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaOK("Archivo.directory.success");
        } catch (Exception ex) {
            Logger.getLogger(ServicioArchivo.class.getName()).log(Level.SEVERE, null, ex);
            return ServicioMensajeSistema.getInstance().getPropertiesMensajeSistemaERROR("Archivo.directory.error");
        }
    }

}
