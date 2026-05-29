package custom

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import org.openqa.selenium.remote.DesiredCapabilities
import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.windows.driver.WindowsDesiredCapsBuilder
import com.kms.katalon.core.windows.driver.WindowsDriverFactory
import com.kms.katalon.core.windows.driver.WindowsSession
import io.appium.java_client.windows.WindowsDriver
public class WindowUtils {
@Keyword
public static WindowsDriver switchToWindowTitle(String windowName) {
WindowsSession windowsSession = WindowsDriverFactory.getWindowsSession()
String titleMatchRegex = ".*" + windowName + ".*"
DesiredCapabilities caps = new WindowsDesiredCapsBuilder()
.merge(windowsSession.getInitCapabilities())
.withAppTopLevelWindowTitleMatch(titleMatchRegex)
.build()
WindowsDriver windowsDriver = WindowsDriverFactory.newWindowsDriver(
windowsSession.getRemoteAddressURL(),
caps, windowsSession.getProxyInfo())
Set<String> handles = windowsDriver.getWindowHandles()
for (String handle : handles) {
windowsDriver.switchTo().window(handle)
String title = windowsDriver.getTitle()
if (title != null && title.contains(windowName)) {
windowsSession.setApplicationDriver(windowsDriver)
windowsSession.setTargetAppType(WindowsSession.TargetAppType.SPECIFIC_APP)
windowsSession.getRunningDriver().switchTo().window(handle);
return windowsDriver
}
}
windowsSession.setApplicationDriver(windowsDriver)
windowsSession.setTargetAppType(WindowsSession.TargetAppType.SPECIFIC_APP)
return windowsDriver
}
}