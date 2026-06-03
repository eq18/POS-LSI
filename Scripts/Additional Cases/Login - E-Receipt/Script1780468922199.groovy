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

'Open application'
Windows.startApplication(GlobalVariable.appPathER)

Windows.waitForElementPresent(findWindowsObject('new login/btn_closeX'), 0)

Windows.takeScreenshot()

'klik tombol close '
Windows.click(findWindowsObject('new login/btn_closeX'))

'input username'
Windows.setText(findWindowsObject('new login/txt_userID'), GlobalVariable.userId)

'input password'
Windows.setText(findWindowsObject('new login/txt_password'), GlobalVariable.password)

'klik tombol masuk'
Windows.click(findWindowsObject('new login/btn_masuk'))

'klik tombol x pada pop up notifikasi'
Windows.click(findWindowsObject('new login/btn_closeX'))

