import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;
    private final HashSet<String> pages = new HashSet<>();
    private final HashMap<String, Integer> osStats = new HashMap<>();

    public Statistics() {}

    public void addEntry(LogEntry entry) {
        if (entry == null) return;

        if (entry.getResponseCode() == 200) {
            pages.add(entry.getPath());
        }
        UserAgent userAgent = new UserAgent(entry.getUserAgent());
        String os = userAgent.getOs();
        osStats.put(os, osStats.getOrDefault(os, 0) + 1);

        totalTraffic += entry.getDataSize();
        LocalDateTime entryTime = entry.getTime();
        if (minTime == null || entryTime.isBefore(minTime)) {
            minTime = entryTime;
        }
        if (maxTime == null || entryTime.isAfter(maxTime)) {
            maxTime = entryTime;
        }
    }

    public HashSet<String> getAllPages() {
        return new HashSet<>(pages);
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