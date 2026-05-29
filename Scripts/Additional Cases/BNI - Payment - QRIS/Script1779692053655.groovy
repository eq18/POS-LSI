import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory

// Open Chrome
WebDriver driver = new ChromeDriver()

DriverFactory.changeWebDriver(driver)

// Maximize browser
WebUI.maximizeWindow()

// Go to URL
WebUI.navigateToUrl('https://uat-qris-merchant.spesandbox.com/site/payment')

// Zoom out 70%
WebUI.executeJavaScript('document.body.style.zoom=\'70%\'', null)

'input qr string, get the string from db'
WebUI.setText(findTestObject('bni-qris/Page_QR Payment/txt_qrString'), GlobalVariable.qrisStringBNI)

WebUI.delay(2)

'input amount'
WebUI.setText(findTestObject('bni-qris/Page_QR Payment/txt_amount'), GlobalVariable.total.toString())

WebUI.delay(2)

WebUI.takeScreenshot()

'klik tombol bayar'
WebUI.click(findTestObject('bni-qris/Page_QR Payment/btn_bayar'))

WebUI.verifyElementPresent(findTestObject('qr-payment/transaksiBerhasil'), 0)

WebUI.delay(5)

WebUI.closeBrowser()

