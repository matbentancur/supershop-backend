package grupo1.supershop.config.report.htmlreport.brand;

public class Logo {
    private static final String QUERY_SELECTOR = "document.querySelector('%s')";
    private static final String SELECTOR = "body > div > div > div.header.navbar > div > div > a > div";
    private static final String BACK_GROUND = ".style.background=\"url('%s')\"";
    private static final String BACK_GROUND_SIZE = ".style.backgroundSize= \"100% 100%\"";

    private Logo() {
    }

    public static String jsLogo(String image) {
        String element = String.format(QUERY_SELECTOR, SELECTOR);
        String logoWithImage = String.format(BACK_GROUND, image);
        return element + logoWithImage + "," + element + BACK_GROUND_SIZE;
    }
}
