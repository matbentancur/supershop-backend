package grupo1.supershop.config.report.htmlreport.brand;

public class Favicon {
    private static final String QUERY_SELECTOR = "document.querySelector('%s')";
    private static final String SELECTOR = "link[rel=\"shortcut icon\"]";
    private static final String HREF = ".href='%s'";

    private Favicon() {
    }

    public static String jsFavIcon(String image) {
        String element = String.format(QUERY_SELECTOR, SELECTOR);
        String favIconWithImage = String.format(HREF, image);
        return element + favIconWithImage;
    }
}
