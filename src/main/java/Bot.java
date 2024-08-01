import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bot {
    private WebDriver driver;
    private List<Article> articles = new ArrayList<>();

    public Bot(WebDriver driver){
        this.driver = driver;
    }

    public void sleep(int seconds){
        System.out.println("Waiting for "+ seconds+" seconds...");
        try{
            Thread.sleep(seconds*1000);
        }catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        System.out.println("Finished waiting");
    }

    public void scrollOnPage(WebDriver driver, int howMany, int scrollVolume){
        JavascriptExecutor jsEXE = (JavascriptExecutor) driver;
        for(int i = 0; i<= howMany; i++){
            jsEXE.executeScript(String.format("window.scrollBy(0, %s )", scrollVolume));
            System.out.println("Scrolling " + scrollVolume + " pixels down..");
            this.sleep(1);
        }
    }



    public void search(String query) {
        this.driver.get("https://topnotch-programmer.com/?s=" + query);
        this.sleep(7);

        this.scrollOnPage(this.driver, 4, 1500);

        List<WebElement> articlesElemets = driver.findElements(By.xpath("//*[@id=\"archives\"]/div[2]/div/div/div[1]/article"));

        for (WebElement article : articlesElemets) {
            String title = article.findElement(By.xpath(".//header/h2/a")).getText();
            String link = article.findElement(By.xpath(".//header/h2/a")).getAttribute("href");
            String description = article.findElement(By.xpath(".//div[1]/p")).getText();
            String imageUrl = article.findElement(By.xpath(".//div/div/a/span")).getAttribute("style");



            System.out.println("Title: " + title);
            System.out.println("Link: " + link);
            System.out.println("Description: " + description);
            System.out.println("Image URL: " + imageUrl);

            Article newArticle = new Article(title,description, link,imageUrl);
            this.articles.add(newArticle);
        }
    }

    public void saveAsJson() {
        List < Article> articles = this.articles;
        JSONArray jsonArray = new JSONArray();

        for (Article article : articles) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", article.getTitle());
            jsonObject.put("link", article.getLink());
            jsonObject.put("description", article.getDescription());
            jsonObject.put("imageUrl", article.getImageUrl());
            jsonArray.put(jsonObject);
        }

        try (FileWriter file = new FileWriter("articles.json")) {
            file.write(jsonArray.toString(4)); // Indent with 4 spaces
            System.out.println("Successfully saved JSON file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
