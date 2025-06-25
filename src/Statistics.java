import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private long totalTraffic = 0;
    private LocalDateTime minTime = null;
    private LocalDateTime maxTime = null;

    public Statistics() {}

    public void addEntry(LogEntry entry) {
        if (entry == null) return;

        totalTraffic += entry.getDataSize();

        LocalDateTime entryTime = entry.getTime();
        if (minTime == null || entryTime.isBefore(minTime)) {
            minTime = entryTime;
        }
        if (maxTime == null || entryTime.isAfter(maxTime)) {
            maxTime = entryTime;
        }
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