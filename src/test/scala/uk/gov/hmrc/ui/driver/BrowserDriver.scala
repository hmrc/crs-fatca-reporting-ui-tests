package uk.gov.hmrc.ui.driver

import org.openqa.selenium.WebDriver
import uk.gov.hmrc.selenium.webdriver.Driver

trait BrowserDriver {
  implicit def driver: WebDriver = Driver.instance

}
