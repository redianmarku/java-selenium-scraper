import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Main {
    public static void main(String[] args){
        WebDriver driver = new ChromeDriver();

        Bot tnpBOT = new Bot(driver);

        tnpBOT.search("telegram");

        tnpBOT.saveAsJson();

        driver.quit();

    }
}
