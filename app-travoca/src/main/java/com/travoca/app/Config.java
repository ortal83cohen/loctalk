package com.travoca.app;

/**
 * @author ortal
 * @date 2015-04-07
 */
public class Config {

    public static final String CORE_INTERFACE_ENDPOINT = "http://www.travoca.com";
    public static final String CORE_INTERFACE_SECURE_ENDPOINT = "https://secure.travoca.com";

    public static final String POI_IMAGE_ENDPOINT = "http://d1pa4et5htdsls.cloudfront.net/images/";

    public static final String TRAVOCA_API_KEY = "Ec95jbYA1iuAt";

    private static String sCoreInterfaceEndpoint = CORE_INTERFACE_ENDPOINT;
    private static String sCoreInterfaceSecureEndpoint = CORE_INTERFACE_SECURE_ENDPOINT;

    private static boolean sIsProduction;

    public static String getCoreInterfaceEndpoint() {
        return sCoreInterfaceEndpoint;
    }

    public static void setCoreInterfaceEndpoint(String endpoint) {
        sCoreInterfaceEndpoint = endpoint;
    }

    public static String getCoreInterfaceSecureEndpoint() {
        return sCoreInterfaceSecureEndpoint;
    }

    public static void setCoreInterfaceSecureEndpoint(String secureEndpoint) {
        sCoreInterfaceSecureEndpoint = secureEndpoint;
    }

    public static void setProductionEnv(boolean isProduction) {
        sIsProduction = isProduction;
    }

    public static boolean isProduction() {
        return sIsProduction;
    }
}
