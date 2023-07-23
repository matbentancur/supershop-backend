package grupo1.supershop.config.utils;


import grupo1.supershop.config.properties.PropertyReader;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

@NoArgsConstructor
public class Utils {

    private static Utils instance = null;

    //region Metodos de la clase

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    /**
     * Devuelve la fecha como un string.
     * Se podría parametrizar para que utilice diferentes formatos.
     *
     * @return Fecha con formato yyyy-mm-dd hh.mm.ss
     */
    public String getDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH_mm_ss"));
    }

    /**
     * Configura un path para el reporte HTML.
     *
     * @return Retorna un path configurado.
     */
    public String pathToSaveHtml() {
        String pathReportHtml = getPathReportHtml();
        String userDir = getUserDir();
        String rootFolder = getRootFolder();
        String dateTime = "reporte " + getDateTime() + ".html";

        if (pathReportHtml.equals(""))
            return userDir + "\\src\\reportHTML\\" + dateTime;

        return pathReportHtml + rootFolder + dateTime;
    }

    //endregion

    //region Metodos privados

    /**
     * Obtiene el path desde archivo environment.properties
     *
     * @return Path para reporte html.
     */
    private static String getPathReportHtml() {
        return PropertyReader.getInstance().getEnvironment("pathReportHtml");
    }

    /**
     * Obtiene directorio del proyecto.
     *
     * @return Directorio de proyecto.
     */
    private static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * Obtienen carpeta raiz del proyecto.
     *
     * @return Carpeta raiz del proyecto.
     */
    private static String getRootFolder() {
        String userDir = getUserDir();
        String[] listDir = userDir.split(Pattern.quote("\\"));
        int lastPosition = listDir.length - 1;
        return "\\" + listDir[lastPosition];
    }
    //endregion
}
