package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class Group_javaninjas {

        @Test
        public void test() throws InterruptedException {
            WebDriver driver = new ChromeDriver();
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            WebElement webForm = driver.findElement(By.xpath("//a[text() = 'Web form']"));
            webForm.click();
            String titleWebBrowser = driver.getTitle();
            assertEquals(titleWebBrowser, "Hands-On Selenium WebDriver with Java");
            WebElement title  = driver.findElement(By.xpath("//h1[@class='display-6']"));
            String titleText = title.getText();
            assertEquals(titleText, "Web form");
            WebElement textInput  = driver.findElement(By.name("my-text"));
            textInput.sendKeys("Vasya");
            WebElement password  = driver.findElement(By.name("my-password"));
            password.sendKeys("123456");
            WebElement textArea = driver.findElement(By.name("my-textarea"));
            textArea.sendKeys("Hello");
            // элемент недоступный для редактирования
            WebElement disabledInput = driver.findElement(By.name("my-disabled"));
            assertFalse(disabledInput.isEnabled());
            //элемент readonly
            WebElement readonlyInput = driver.findElement(By.name("my-readonly"));
            String readonlyText = readonlyInput.getAttribute("readonly");
            Assert.assertTrue(readonlyText.equals("true"));
            //перетаскиваем слайдер в начальную позицию
            WebElement rangeInput = driver.findElement(By.xpath("//input[@type='range']"));
            int initialX = rangeInput.getLocation().getX();
            Actions actions = new Actions(driver);
            actions.clickAndHold(rangeInput)
                    .moveByOffset(-initialX,0)
                    .release()
                    .perform();
            //выбор цвета
            WebElement colorInput = driver.findElement(By.name("my-colors"));
            String colorText = colorInput.getAttribute("value");
            colorInput.click();

            //Date picker
            WebElement dataInput = driver.findElement(By.name("my-date"));
            dataInput.click();
            Thread.sleep(1000);
            WebElement selectDate =  driver.findElement(By.xpath("//td[text() = '15']"));
            selectDate.click();
            String data = dataInput.getAttribute("value");
            String selectDateText = "03/15/2025";
            assertEquals(data,selectDateText);
            driver.quit();
        }
//Alert
        @Test
        public void test2() {
            WebDriver driver = new ChromeDriver();
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            WebElement dialogBoxes = driver.findElement(By.xpath("//a[text() = 'Dialog boxes']"));
            dialogBoxes.click();
            WebElement dialogTitle = driver.findElement(By.className("display-6"));
            String dialogTitleText = dialogTitle.getText();
            assertEquals(dialogTitleText, "Dialog boxes");
            WebElement launchAlert = driver.findElement(By.id("my-alert"));
            launchAlert.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            String alertText = alert.getText();
            System.out.println("Текст алерта: " + alertText);
            alert.accept();
            driver.quit();
        }
//Launch confirm
            @Test
                    public void test3(){

            WebDriver driver = new ChromeDriver();
            driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
                WebElement dialogBoxes = driver.findElement(By.xpath("//a[text() = 'Dialog boxes']"));
                dialogBoxes.click();
                WebElement dialogTitle = driver.findElement(By.className("display-6"));
                String dialogTitleText = dialogTitle.getText();
                assertEquals(dialogTitleText, "Dialog boxes");
                WebElement launchAlert = driver.findElement(By.id("my-confirm"));
                launchAlert.click();
                // Ожидаем появления алерта
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                String alertText = alert.getText();
                // Получаем текст из алерта (опционально)
                System.out.println("Текст алерта: " + alertText);
                // Выбор действия: нажать "OK" или "Отмена"
                String action = "OK"; // Можете изменить на "Отмена" для другого действия
                if (action.equals("OK")) {
                    // Нажимаем "OK"
                    alert.accept();
                    System.out.println("Нажата кнопка 'OK'");
                } else {
                    // Нажимаем "Отмена"
                    alert.dismiss();
                    System.out.println("Нажата кнопка 'Отмена'");
                }
            }

            @Test
            public void testSearchLamoda() throws InterruptedException {
                WebDriver driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
                driver.get("https://www.lamoda.ru/");
                String titleWebBrowser = driver.getTitle();
                assertEquals(titleWebBrowser, "Интернет магазин одежды и обуви. Купить обувь, купить одежду, аксессуары в онлайн магазине Lamoda.ru");
                WebElement search = driver.findElement(By.className("_input_mh0i8_19"));
                search.click();
                search.sendKeys("куртка");
                search.sendKeys(Keys.ENTER);
                Thread.sleep(10000);
                WebElement result = driver.findElement(By.className("_titleText_1s7by_15"));
                String resultText = result.getText();
                assertEquals(resultText, "Товары по запросу «куртка»");
            }
            @Test
            public void testShoesLamoda() throws InterruptedException {
                WebDriver driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.manage().deleteAllCookies();
                driver.get("https://www.lamoda.ru/");
                Thread.sleep(1000);
                WebElement shoes = driver.findElement(By.xpath("//a[@href='/c/15/shoes-women/?sitelink=topmenuW&l=4']"));
                shoes.click();
                Thread.sleep(3000);
            }
        }

