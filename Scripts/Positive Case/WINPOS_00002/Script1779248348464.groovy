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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory

Windows.startApplication(GlobalVariable.appPath)

Windows.waitForElementPresent(findWindowsObject('new login/btn_closeX'), 0)

Windows.takeScreenshot()

'klik tombol close '
Windows.click(findWindowsObject('new login/btn_closeX'))

Windows.delay(5)

Windows.click(findWindowsObject('Object Repository/Hardware POS/btn_config'))

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('frmConfig')

Windows.click(findWindowsObject('Object Repository/Hardware POS/btn_addEDC'))

Windows.click(findWindowsObject('Object Repository/Hardware POS/cb_edc'))

Windows.click(findWindowsObject('Object Repository/Hardware POS/namaEDC'))

Windows.click(findWindowsObject('Object Repository/Hardware POS/cb_tipeConfig'))

Windows.delay(3)

Windows.click(findWindowsObject('Object Repository/Hardware POS/tipeConfig'))

Windows.delay(3)

Windows.click(findWindowsObject('Object Repository/Hardware POS/cb_port'))

Windows.click(findWindowsObject('Object Repository/Hardware POS/port'))

Windows.click(findWindowsObject('Object Repository/Hardware POS/btn_save'))

Windows.click(findWindowsObject('Object Repository/Hardware POS/txt_berhasilDisimpan'))

Windows.click(findWindowsObject('Object Repository/Hardware POS/btn_closeSetelahSimpan'))

