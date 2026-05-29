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
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.WebDriver as WebDriver
import com.kms.katalon.core.configuration.RunConfiguration as RunConfiguration
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import org.openqa.selenium.WebDriver
import org.openqa.selenium.JavascriptExecutor

import com.kms.katalon.core.webui.driver.DriverFactory
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

ChromeOptions options = new ChromeOptions()
options.addArguments("--start-maximized")

ChromeDriver driver = new ChromeDriver(options)
DriverFactory.changeWebDriver(driver)

'Pergi ke URL QRIS Merchant'
WebUI.navigateToUrl('https://uat-qris-merchant.spesandbox.com/site/payment')

WebUI.executeJavaScript("document.body.style.zoom='75%'", null)



'Input QR'
WebUI.setText(findTestObject('qr-payment/txt_paymentQr'), findTestData('Generate QR').getValue(1, 1))

'Input total belanja'
WebUI.setText(findTestObject('qr-payment/txt_amountQR'), GlobalVariable.total)

'Klik bayar'
WebUI.click(findTestObject('qr-payment/btn_bayar'))

'Verifikasi negative case'
not_run: WebUI.verifyElementPresent(findTestObject('qr-payment/txt_transaksiQrFailed'), 0)

not_run: transakasiGagal = WebUI.getText(findTestObject('qr-payment/txt_transaksiQrFailed'), 0)

not_run: if (transakasiGagal.contains(transakasiGagal)) {
//    WebUI.comment(transakasiGagal)
//
//    WebUI.delay(3)
//
//    WebUI.takeFullPageScreenshot()
//
//    WebUI.closeBrowser()
}

