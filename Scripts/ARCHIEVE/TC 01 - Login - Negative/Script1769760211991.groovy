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
import groovy.console.ui.view.WindowsDefaults as WindowsDefaults
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

Windows.startApplicationWithTitle(GlobalVariable.appPath, null)

Windows.waitForElementPresent(findWindowsObject('new login/btn_closeX'), 100)

'Klik tombol X pada pemberitahuan untuk melanjutkan proses login'
Windows.doubleClick(findWindowsObject('new login/btn_closeX'))

Windows.doubleClick(findWindowsObject('Object Repository/new login/txt_userID'))

'Input User ID'
Windows.setText(findWindowsObject('Object Repository/new login/txt_userID'), GlobalVariable.userId)

Windows.sendKeys(findWindowsObject('new login/txt_userID'), Keys.chord(Keys.TAB))

'Input Password yang Salah'
Windows.setText(findWindowsObject('Object Repository/new login/txt_password'), '1231231')

'Klik Tombol Masuk '
Windows.click(findWindowsObject('Object Repository/new login/btn_masuk'))

loginFailed = Windows.getText(findWindowsObject('Object Repository/new login/txt_loginFailed'))

if (loginFailed.contains(loginFailed)) {
    Windows.comment(loginFailed)

    'Klik tombol tutup untuk menutup notification'
    Windows.click(findWindowsObject('Object Repository/new login/btn_closeNotif'))

    'Tutup Aplikasi'
    Windows.closeApplication()
}

