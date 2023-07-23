package grupo1.supershop.config.report.htmlreport;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import grupo1.supershop.config.report.htmlreport.brand.CustomBrand;
import grupo1.supershop.config.properties.PropertyReader;
import grupo1.supershop.config.utils.StatusTest;
import grupo1.supershop.config.utils.Utils;

/**
 * Clase encargada de manipular el framework ExtentReport.
 */
public class HtmlReportManager {

    //region [Variables]
    private final ExtentReports extentReports;
    private ExtentTest extentTest;
    //endregion

    //region [Constructores]
    public HtmlReportManager() {
        String pathReportHtml = Utils.getInstance().pathToSaveHtml();
        String imgBase64 = PropertyReader.getInstance().getEnvironment("imageReport");
        ExtentSparkReporter spark = new ExtentSparkReporter(pathReportHtml);

        spark.config().setTheme(Theme.valueOf(PropertyReader.getInstance().getEnvironment("themeReportHtml")));
        spark.config().setDocumentTitle(PropertyReader.getInstance().getEnvironment("documentTitleReportHtml"));
        spark.config().setEncoding(PropertyReader.getInstance().getEnvironment("encodingReportHtml"));
        spark.config().setReportName(PropertyReader.getInstance().getEnvironment("reportNameReportHtml"));
        spark.config().setJs(CustomBrand.customFaviconLogo(imgBase64));
        extentReports = new ExtentReports();
        extentReports.attachReporter(spark);
    }
    //endregion

    //region [Metodos de la clase]

    /**
     * Crea un nodo test para agregar al reporte HTML.
     *
     * @param name        Nombre del test
     * @param description Descripcion del test
     */
    public void createTest(String name, String description) {
        extentTest = extentReports.createTest(name, description);
    }

    /**
     * Agrega un log al test con un determinado estado.
     *
     * @param detail Mensaje para registrar en log
     * @param status Estado para asignar al log
     */
    public void addLog(StatusTest status, String detail) {
        extentTest.log(status.getStatus(), detail);
    }

    /**
     * Agrega un estado al test.
     *
     * @param detail Mensaje para el registrar en el estado
     * @param status Estado para asignar
     */
    public void addEstate(StatusTest status, String detail) {
        switch (status) {
            case INFO -> extentTest.info(detail);
            case WARN -> extentTest.warning(detail);
            case SKIP -> extentTest.skip(detail);
            case PASS -> extentTest.pass(detail);
            case FAIL -> extentTest.fail(detail);
            default -> throw new IllegalStateException(status.toString());
        }

    }

    /**
     * Agrega un estado al test.
     *
     * @param t      Valor retornado y obtenido del test
     * @param status Estado para asignar
     */
    public void addEstate(StatusTest status, Throwable t) {
        switch (status) {
            case INFO -> extentTest.info(t);
            case WARN -> extentTest.warning(t);
            case SKIP -> extentTest.skip(t);
            case PASS -> extentTest.pass(t);
            case FAIL -> extentTest.fail(t);
            default -> throw new IllegalStateException(status.toString());
        }

    }

    /**
     * Agrega una captura de pantalla al test con un determinado estado.
     *
     * @param capture Captura de pantalla en BASE64
     * @param status  Estado para asignar a la captura
     */
    public void addScreenshot(StatusTest status, String capture) {
        extentTest.log(status.getStatus(), MediaEntityBuilder.createScreenCaptureFromBase64String(capture).build());
    }

    /**
     * Registra el nodo test configurado al reporte HTML.
     */
    public void endTest() {
        extentReports.flush();
    }

    //endregion
}
