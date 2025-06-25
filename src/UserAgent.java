public class UserAgent {
    private final String os;
    private final String browser;

    public UserAgent(String userAgentString) {
        this.os = parseOperatingSystem(userAgentString);
        this.browser = parseBrowser(userAgentString);
    }

    private String parseOperatingSystem(String userAgent) {
        if (userAgent == null || userAgent.isEmpty() || userAgent.equals("-")) {
            return "Other";
        }

        String userAgentLower = userAgent.toLowerCase();

        if (userAgentLower.contains("windows")) {
            return "Windows";
        } else if (userAgentLower.contains("mac os") || userAgentLower.contains("macos")) {
            return "macOS";
        } else if (userAgentLower.contains("linux")) {
            return "Linux";
        } else if (userAgentLower.contains("android")) {
            return "Android";
        } else if (userAgentLower.contains("iphone") || userAgentLower.contains("ipad")) {
            return "iOS";
        } else {
            return "Other";
        }
    }

    private String parseBrowser(String userAgent) {
        if (userAgent == null || userAgent.isEmpty() || userAgent.equals("-")) {
            return "Other";
        }

        String userAgentLower = userAgent.toLowerCase();

        if (userAgentLower.contains("edge")) {
            return "Edge";
        } else if (userAgentLower.contains("firefox")) {
            return "Firefox";
        } else if (userAgentLower.contains("chrome") && !userAgentLower.contains("chromium")) {
            return "Chrome";
        } else if (userAgentLower.contains("safari") && !userAgentLower.contains("chrome")) {
            return "Safari";
        } else if (userAgentLower.contains("opera")) {
            return "Opera";
        } else {
            return "Other";
        }
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }
}