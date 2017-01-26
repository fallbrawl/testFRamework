package com.swat.example.tests;

import com.swat.TestProperties;
import com.swat.WebDriverScreenshotListener;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import java.awt.*;
import java.util.concurrent.TimeUnit;

@Listeners({ WebDriverScreenshotListener.class })
public abstract class BaseTestCase {

	private static WebDriver driver;

	@BeforeSuite(alwaysRun = true)
	public void setUpBeforeSuite() throws Exception {
	}

	@BeforeClass(alwaysRun = true)
	public void setUpBeforeClass() throws Exception {
	}

	@AfterSuite(alwaysRun = true)
	public void tearDownAfterSuite() {
		//shutDownDriver(driver);
	}

	private void shutDownDriver(WebDriver driver) {
		if (driver != null) {
			driver.quit();
		}
	}

	protected WebDriver getDriver() {

		if (driver == null) {
			String browser = TestProperties.getTestProperty("browser");
			if ("firefox".equals(browser)) {
				System.setProperty("webdriver.gecko.driver","/home/paul/selenium/selenium-2.48.2/geckodriver");
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability("marionette", false);
				driver = new FirefoxDriver(capabilities);
				maximizeWindow();
			}  
			if ("chrome".equals(browser)) {
				driver = new ChromeDriver();
			}
			driver.manage()
					.timeouts()
					.implicitlyWait(TestProperties.getWaitTime(),
							TimeUnit.SECONDS);
			driver.manage()
					.timeouts()
					.pageLoadTimeout(
							Integer.parseInt(TestProperties
									.getTestProperty("wait.page.load")),
							TimeUnit.SECONDS);
		}


		return driver;
	}

	private void maximizeWindow() {
		driver.manage().window().setPosition(new Point(0, 0));
		java.awt.Dimension screenSize = Toolkit.getDefaultToolkit()
				.getScreenSize();
		Dimension dim = new Dimension((int) screenSize.getWidth(),
				(int) screenSize.getHeight());
		driver.manage().window().setSize(dim);
	}

	public void openRelativeUrl(String relativeUrl) {
		getDriver().get(relativeUrl);
	}

}
