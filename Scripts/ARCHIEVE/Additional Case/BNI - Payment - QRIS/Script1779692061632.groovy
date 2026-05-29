import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable as GlobalVariable

WebUI.openBrowser('')

'Open Navigasi ke URL'
WebUI.navigateToUrl('https://uat-qris-merchant.spesandbox.com/site/payment')

'Input QRIS String yang di generate di Database'
WebUI.setText(findTestObject('bni-qris/Page_QR Payment/txt_qrString'), GlobalVariable.qrisStringBNI)

'Input Total yang harus dibayar dari POS System'
WebUI.setText(findTestObject('bni-qris/Page_QR Payment/txt_amount'), GlobalVariable.amount)

'Klik tombol bayar'
WebUI.click(findTestObject('bni-qris/Page_QR Payment/btn_bayar'), FailureHandling.STOP_ON_FAILURE)

WebUI.closeBrowser()

