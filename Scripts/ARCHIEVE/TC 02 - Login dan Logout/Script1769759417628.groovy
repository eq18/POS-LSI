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

Windows.startApplicationWithTitle('C:\\Users\\kasir\\AppData\\Local\\Apps\\2.0\\3KTNRRKW.CXY\\19N75WPK.ZJN\\lsi...tion_0000000000000000_0002.0001_2e33c1d3d05ede76\\LSI.POS.CashRegister.exe', 
    'Login (PT Lion Super Indo v.2.1.1.71)')

Windows.waitForElementPresent(findWindowsObject('new login/btn_closeX'), 100)

Windows.takeScreenshot()

'Klik tombol X pada pemberitahuan untuk melanjutkan proses login'
Windows.doubleClick(findWindowsObject('new login/btn_closeX'))

Windows.takeScreenshot()

Windows.doubleClick(findWindowsObject('Object Repository/new login/txt_userID'))

'Input User ID'
Windows.setText(findWindowsObject('Object Repository/new login/txt_userID'), GlobalVariable.userId)

Windows.sendKeys(findWindowsObject('new login/txt_userID'), Keys.chord(Keys.TAB))

'Input Password'
Windows.setText(findWindowsObject('Object Repository/new login/txt_password'), GlobalVariable.password)

Windows.takeScreenshot()

'Klik tombol masuk'
Windows.click(findWindowsObject('Object Repository/new login/btn_masuk'))

Windows.takeScreenshot()

'Klik tombol close untuk menutup notification'
Windows.click(findWindowsObject('Object Repository/new login/btn_close2'))

Windows.takeScreenshot()

'Ganti window ke Main Menu'
Windows.switchToWindowTitle('Main Menu')

Windows.waitForElementPresent(findWindowsObject('new login/btn_keluar'), 0)

Windows.takeScreenshot()

'Klik tombil keluar'
Windows.click(findWindowsObject('new login/btn_keluar'))

Windows.delay(5)

'Ganti ke window Login'
Windows.switchToWindowTitle('Login')

Windows.takeScreenshot()

'Klik tombol untuk keluar dari aplikasi'
Windows.click(findWindowsObject('Object Repository/new login/btn_closeApplication'))

