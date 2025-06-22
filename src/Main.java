import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int correctFile = 0;
        while (true) {
            System.out.println("������� ���� � �����: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (fileExists == false || !isDirectory == false) {
                System.out.println("��������� ���� ���� �� ����������, ���� �������� ������, � �� ������.");

            } else {
                correctFile++;
                System.out.println("��� ���� �����: " + correctFile);
            }

            try (FileReader fileReader = new FileReader(path);
                 BufferedReader reader = new BufferedReader(fileReader)) {

                int lineCount = 0;
                int googlebotRequests = 0;
                int yandexbotRequests = 0;
                String line;

                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    if (line.length() > 1024) {
                        throw new LongLine("������ #" + lineCount + " ��������� ����������� ���������� ����� 1024 �������");
                    }

                    String userAgent = extractUserAgent(line);
                    if (userAgent != null) {
                        String botName = extractBotName(userAgent);
                        if ("Googlebot".equals(botName)) {
                            googlebotRequests++;
                        } else if ("YandexBot".equals(botName)) {
                            yandexbotRequests++;
                        }
                    }
                }
                if (lineCount > 0) {
                    double googlebotPercent = (double) googlebotRequests / lineCount * 100;
                    double yandexbotPercent = (double) yandexbotRequests / lineCount * 100;

                    System.out.println("����� ���������� �����: " + lineCount);
                    System.out.println("������ �� Googlebot: "+ googlebotRequests);
                    System.out.println("���� ����� �� Googlebot: "+ googlebotPercent);

                    System.out.println("������ �� YandexBot: "+ yandexbotRequests);
                    System.out.println("���� ����� �� �� YandexBot: "+ yandexbotPercent);

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static String extractUserAgent(String logLine) {
        int lastQuoteInd = logLine.lastIndexOf('"');
        if (lastQuoteInd == -1) return null;

        int secondLastQuoteInd = logLine.lastIndexOf('"', lastQuoteInd - 1);
        if (secondLastQuoteInd == -1) return null;

        return logLine.substring(secondLastQuoteInd + 1, lastQuoteInd);
    }

    private static String extractBotName(String userAgent) {
        int openBracket = userAgent.indexOf('(');
        if (openBracket == -1) return null;

        int closeBracket = userAgent.indexOf(')', openBracket);
        if (closeBracket == -1) return null;

        String firstBrackets = userAgent.substring(openBracket + 1, closeBracket);
        String[] parts = firstBrackets.split(";");
        if (parts.length >= 2) {
            String fragment = parts[1].trim();
            int slashIndex = fragment.indexOf('/');
            if (slashIndex != -1) {
                return fragment.substring(0, slashIndex).trim();
            }
        }
        return null;
    }

    static class LongLine extends RuntimeException {
        public LongLine(String message) {
            super(message);
        }
    }
}
