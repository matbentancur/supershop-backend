package grupo1.supershop.config.report.listenertest;

import grupo1.supershop.config.report.htmlreport.HtmlReportManager;
import grupo1.supershop.config.utils.StatusTest;
import org.junit.jupiter.api.extension.*;

public class ListenerTest implements BeforeAllCallback, BeforeEachCallback, AfterAllCallback, TestWatcher {
    private HtmlReportManager reporteHtml;

    @Override
    public void beforeAll(ExtensionContext context) {
        reporteHtml = new HtmlReportManager();
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        if (context.getTestMethod().isPresent()) {
            reporteHtml.createTest(context.getDisplayName(), context.getDisplayName());
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        reporteHtml.endTest();
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        reporteHtml.addEstate(StatusTest.PASS, String.format("Test <b>'%s'</b> ha sido exitoso", context.getDisplayName()));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        reporteHtml.addEstate(StatusTest.FAIL, cause);
    }
}
