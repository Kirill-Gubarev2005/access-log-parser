import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;
    private final HashSet<String> pages = new HashSet<>();
    private final HashSet<String> missPages = new HashSet<>();
    private final HashMap<String, Integer> osStats = new HashMap<>();
    private final HashMap<String, Integer> browserStats = new HashMap<>();
    private final HashSet<String> notBotIp= new HashSet<>();
    private int nonBotVisits = 0;
    private int errorRequests = 0;

    public Statistics() {}

    public void addEntry(LogEntry entry) {
        if (entry == null) return;

        if (entry.getResponseCode() == 200) {
            pages.add(entry.getPath());
        }
        if (entry.getResponseCode() == 404) {
            missPages.add(entry.getPath());
        }

        UserAgent userAgent = new UserAgent(entry.getUserAgent());

        String os = userAgent.getOs();
        osStats.put(os, osStats.getOrDefault(os, 0) + 1);

        String browser = userAgent.getBrowser();
        browserStats.put(browser, browserStats.getOrDefault(browser, 0) + 1);

        totalTraffic += entry.getDataSize();
        LocalDateTime entryTime = entry.getTime();
        if (minTime == null || entryTime.isBefore(minTime)) {
            minTime = entryTime;
        }
        if (maxTime == null || entryTime.isAfter(maxTime)) {
            maxTime = entryTime;
        }
        if (!userAgent.isBot()) {
            nonBotVisits++;
            notBotIp.add(entry.getIpAddress());
        }

        if (entry.getResponseCode() >= 400) {
            errorRequests++;
        }
    }

    public HashSet<String> getAllPages() {
        return new HashSet<>(pages);
    }
    public HashSet<String> getAllMissedPages() {
        return new HashSet<>(missPages);
    }


    public HashMap<String, Double> getOsStatistics() {
        HashMap<String, Double> result = new HashMap<>();
        int total = 0;
        for (Integer count : osStats.values()) {
            total += count;
        }
        for (HashMap.Entry<String, Integer> entry : osStats.entrySet()) {
            String os = entry.getKey();
            int count = entry.getValue();
            double percentage = (double) count / total;
            result.put(os, percentage);
        }
        return result;
    }
    public HashMap<String, Double> getBrowserStatistics() {
        HashMap<String, Double> result = new HashMap<>();
        int total = 0;
        for (Integer count : browserStats.values()) {
            total += count;
        }
        for (HashMap.Entry<String, Integer> entry : browserStats.entrySet()) {
            String browser = entry.getKey();
            int count = entry.getValue();
            double percentage = (double) count / total;
            result.put(browser, percentage);
        }
        return result;
    }
    public double getAverageVisitsPerHour() {
        if (minTime == null || maxTime == null || nonBotVisits == 0) {
            return 0.0;
        }

        long hours = Duration.between(minTime, maxTime).toHours();
        if (hours == 0) {
            return nonBotVisits;
        }

        return (double) nonBotVisits / hours;
    }
    public double getAverageErrorsPerHour() {
        if (minTime == null || maxTime == null || errorRequests == 0) {
            return 0.0;
        }

        long hours = Duration.between(minTime, maxTime).toHours();
        if (hours == 0) {
            return errorRequests;
        }

        return (double) errorRequests / hours;
    }
    public double getAverageVisitsPerUser() {
        if (notBotIp.isEmpty() || nonBotVisits == 0) {
            return 0.0;
        }

        return (double) nonBotVisits / notBotIp.size();
    }
    public double getTrafficRate() {
        if (minTime == null || maxTime == null || minTime.equals(maxTime)) {
            return 0.0;
        }

        Duration duration = Duration.between(minTime, maxTime);
        double seconds = duration.getSeconds();
        if (seconds == 0) {
            return 0.0;
        }

        return totalTraffic / seconds * 60;
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }
}