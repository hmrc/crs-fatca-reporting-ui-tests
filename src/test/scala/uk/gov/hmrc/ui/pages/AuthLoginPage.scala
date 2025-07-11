package uk.gov.hmrc.ui.pages

import org.openqa.selenium.By
import uk.gov.hmrc.ui.conf.TestConfiguration

object AuthLoginPage extends BasePage {
  override val pageUrl: String = TestConfiguration.url("auth-login-stub") + "/gg-sign-in"
  
  private val redirectUrl: String      = TestConfiguration.url("crs-fatca-reporting-frontend")
  
  private val redirectionUrlById: By   = By.id("redirectionUrl")
  private val affinityGroupById: By    = By.id("affinityGroupSelect")
  private val authSubmitById: By       = By.id("submit-top")
  
 
  
  
  

}
