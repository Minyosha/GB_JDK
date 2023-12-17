package hw_4.Phoenix;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.*;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {
    private static final String DOWNLOAD_DIRECTORY = "src\\main\\java\\hw_4\\Phoenix\\Downloaded images - big";

    public static void main(String[] args) {
        String folderPath = "src\\main\\java\\hw_4\\Phoenix";
        String fileName = "Articles.txt";
        String baseUrl = "https://www.phoenixcontact.com/search/search?q=";
        String endOfUrl = "&_locale=en-PC&_realm=pc";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество потоков: ");
        int numLists = scanner.nextInt();
        scanner.close();
        System.out.println("Разбивка артиклей по " + numLists + " спискам:");


        try {
            List<String> allLines = Files.readAllLines(Paths.get(folderPath, fileName));

            // Создаем пустые списки
            List<List<String>> lists = new ArrayList<>();
            for (int i = 0; i < numLists; i++) {
                lists.add(new ArrayList<>());
            }

            // Заполняем списки
            for (int i = 0; i < allLines.size(); i++) {
                lists.get(i % numLists).add(allLines.get(i));
            }

            // Выводим списки
            for (int i = 0; i < lists.size(); i++) {
                System.out.println("List " + (i + 1) + ": " + lists.get(i));
            }

            ExecutorService executorService = Executors.newFixedThreadPool(numLists);

            for (int i = 0; i < numLists; i++) {
                final int threadId = i;
                executorService.execute(() -> {
                    try {
                        // Обрабатываем каждый артикул
                        for (String article : lists.get(threadId)) {
                            article = article.trim();
                            String url = baseUrl + article + endOfUrl;
                            String html = getHtml(url);
                            String productUrl = extractProductUrl(html);

                            if (productUrl != null) {
                                // System.out.println(productUrl);
                                String imageLink = getImageLink(productUrl);
                                if (imageLink != null) {
                                    // System.out.println(imageLink);
                                    downloadImage(imageLink, article);
                                } else {
                                    System.out.println("Image link not found for article " + article);
                                }
                            } else {
                                System.out.println("Product URL not found for article " + article);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                });
            }

            executorService.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static String getHtml(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            String html = doc.html();
            return html;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String extractProductUrl(String html) {
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("div[class=\"se-result-pos-item p-0 col-12\"] a[href^=\"/en-pc/products/\"]");

        if (!elements.isEmpty()) {
            Element element = elements.first();
            String productUrl = element.attr("href");
            productUrl = "https://www.phoenixcontact.com" + productUrl;
            return productUrl;
        }

        return null;
    }

    private static String getImageLink(String productUrl) {
        String html = getHtml(productUrl);
        int startIndex = html.indexOf("&quot;extraLargeSrc&quot;:&quot;") + 32;
//        System.out.println(startIndex);
        int endIndex = html.indexOf("?format=jpg&quot;}]\"") + 11;
//        System.out.println(endIndex);
        String imageLink = html.substring(startIndex, endIndex);
//        System.out.println(imageLink);
        if (imageLink.contains("&quot")) {
            int indexToCut = imageLink.indexOf("&quot");
            imageLink = imageLink.substring(0, indexToCut);
//            System.out.println(imageLink);
            return imageLink;
        }

//        System.out.println(imageLink);
        return imageLink;
    }

    private static void downloadImage(String imageLink, String articleNumber) throws IOException {

        URL url = new URL(imageLink);
        Path directoryPath = Path.of(DOWNLOAD_DIRECTORY);
        if (!Files.exists(directoryPath)) {
            Files.createDirectory(directoryPath);
        }
        String fileName = articleNumber + ".jpg";
        Path filePath = directoryPath.resolve(fileName);
//        Files.copy(url.openStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        boolean success = false;
        int maxAttempts = 5;
        int attempts = 0;

        while (!success && attempts < maxAttempts) {
            try {
                Files.copy(url.openStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                success = true;
            } catch (IOException e) {
                attempts++;
                System.out.println("An error occurred while copying file by thread " + Thread.currentThread().getName() + ". Retrying... Attempt: " + attempts);
            }
        }

        if (!success) {
            System.out.println("Failed to copy file for article " + articleNumber + " after " + maxAttempts + " attempts by thread " + Thread.currentThread().getName());
        }
        System.out.println("Image downloaded successfully for article " + articleNumber + " by thread " + Thread.currentThread().getName());
    }
}
