import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogEntry {
    private final String ipAddress;
    private final String clientIdentd;
    private final String userId;
    private final LocalDateTime time;
    private final String method;
    private final String path;
    private final String httpVersion;
    private final int responseCode;
    private final int dataSize;
    private final String referer;
    private final String userAgent;

    private static final DateTimeFormatter LOG_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .appendPattern("dd/MMM/yyyy:HH:mm:ss")
            .appendLiteral(" ")
            .appendPattern("XX")
            .toFormatter(Locale.ENGLISH);

    private static final String LOG_REGEX =
            "^([\\d.]+) (\\S+) (\\S+) \\[(.*?)\\] \"(\\S+) (.*?) (\\S+)\" (\\d+) (\\d+) \"(.*?)\" \"(.*?)\"$";
    private static final Pattern LOG_PATTERN = Pattern.compile(LOG_REGEX);

    public LogEntry(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (!matcher.find()) {
            throw new IllegalArgumentException("Invalid log format: " + logLine);
        }

        this.ipAddress = matcher.group(1);
        this.clientIdentd = parseOptionalField(matcher.group(2));
        this.userId = parseOptionalField(matcher.group(3));
        this.time = parseDateTime(matcher.group(4));
        this.method = matcher.group(5);
        this.path = matcher.group(6);
        this.httpVersion = matcher.group(7);
        this.responseCode = Integer.parseInt(matcher.group(8));
        this.dataSize = Integer.parseInt(matcher.group(9));
        this.referer = parseOptionalField(matcher.group(10));
        this.userAgent = parseOptionalField(matcher.group(11));
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        String cleanedStr = dateTimeStr.replaceAll("[\\[\\]]", "");
        ZonedDateTime zonetime = ZonedDateTime.parse(cleanedStr, LOG_DATE_FORMATTER);
        LocalDateTime dateTime = zonetime.toLocalDateTime();
        return dateTime;
    }

    private String parseOptionalField(String field) {
        return (field == null || field.equals("-")) ? null : field;
    }
    public String getIpAddress() { return ipAddress; }
    public String getClientIdentd() { return clientIdentd; }
    public String getUserId() { return userId; }
    public LocalDateTime getTime() { return time; }
    public String getMethod() { return method; }
    public String getPath() { return path; }
    public String getHttpVersion() { return httpVersion; }
    public int getResponseCode() { return responseCode; }
    public int getDataSize() { return dataSize; }
    public String getReferer() { return referer; }
    public String getUserAgent() { return userAgent; }

}