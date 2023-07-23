package grupo1.supershop.config.report.htmlreport.brand;

public class CustomBrand {

    private CustomBrand() {
    }

    public static String customFavicon(String image) {
        return Favicon.jsFavIcon(image);
    }

    public static String customLogo(String image) {
        return Logo.jsLogo(image);
    }

    public static String customFaviconLogo(String image) {
        return customFavicon(image) + "," + customLogo(image);
    }
}
