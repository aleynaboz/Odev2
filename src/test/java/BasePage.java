import com.thoughtworks.gauge.Step;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class BasePage extends BaseTest {


    @Step("<int> saniye kadar bekle")
    public void waitForsecond(int s) throws InterruptedException {
        Thread.sleep(1000 * s);
    }

    @Step("<id> elemetine tıkla")
    public void findByelementEndclick(String id) {
        MobileElement element = appiumDriver.findElement(By.id(id));
        if (element.isDisplayed()) {
            element.click();
        } else {
            System.out.println("Kontrol edilen element görünür olmadı");
        }
    }

    @Step("Sayfayı aşağı doğru kaydır")
    public void swipeUp() {
        final int ANIMATION_TIME = 200; // ms
        final int PRESS_TIME = 200; // ms
        int edgeBorder = 10; // better avoid edges
        PointOption pointOptionStart, pointOptionEnd;
        // init screen variables
        Dimension dims = appiumDriver.manage().window().getSize();
        System.out.println("Telefon Ekran Boyutu " + dims);
        // init start point = center of screen
        pointOptionStart = PointOption.point(dims.width / 2, dims.height / 2);
        System.out.println("Başlangıç noktası " + pointOptionStart);
        pointOptionEnd = PointOption.point(dims.width / 2, dims.height / 4);
        System.out.println("Bitiş noktası " + pointOptionEnd);
        try {
            new TouchAction(appiumDriver)
                    .press(pointOptionStart)
                    // a bit more reliable when we add small wait
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PRESS_TIME)))
                    .moveTo(pointOptionEnd)
                    .release().perform();
        } catch (Exception e) {
            System.err.println("swipeScreen(): TouchAction FAILED\n" + e.getMessage());
            return;
        }

        // always allow swipe action to complete
        try {
            Thread.sleep(ANIMATION_TIME);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    @Step("Xpath <xpath> li elementi bul ve tıkla")
    public void clickByxpath(String xpath) {
        appiumDriver.findElement(By.xpath(xpath)).click();
    }


    @Step("Id <id> li elementi bul ve tıkla")
    public void clickByid(String id) {
        appiumDriver.findElement(By.id(id)).click();
    }

    @Step("<random> ürünlerden rastgele ürün seç")
    public void randomProduct(String product) {
        List<MobileElement> elements = appiumDriver.findElements(By.xpath(" //*[@resource-id='com.ozdilek.ozdilekteyim:id/imageView']"));
        Random rnd = new Random();
        int rndInt = rnd.nextInt(elements.size());
        elements.get(rndInt).click();
    }

    @Step("<id> li alana email adresi gir")
    public void emailSend(String email) {
        appiumDriver.findElementById(email).sendKeys("aleyna.boz@testinium.com");
    }

    @Step("<id> li alana şifre gir")
    public void passwordSend(String pass) {
        appiumDriver.findElementById(pass).sendKeys("123456");
    }
}
