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

'Open Application'
Windows.startApplication(GlobalVariable.appPath)

Windows.waitForElementPresent(findWindowsObject('new login/btn_closeX'), 10)

Windows.takeScreenshot()

'Close Pop Up'
Windows.click(findWindowsObject('new login/btn_closeX'))

Windows.delay(3)

'Klik tombol configuration'
Windows.click(findWindowsObject('Hardware POS/btn_config'))

Windows.delay(3)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('frmConfig')

Windows.delay(3)

'Klik tombol Add EDC'
Windows.click(findWindowsObject('Hardware POS/btn_addEDC'))

Windows.delay(2)

'Pilih EDC'
Windows.click(findWindowsObject('Hardware POS/cb_edc'))

Windows.delay(1)

Windows.click(findWindowsObject('Hardware POS/namaEDC'))

Windows.delay(2)

'Get Nama EDC'
String expectedEdc = Windows.getText(findWindowsObject('Hardware POS/cb_edc'))

KeywordUtil.logInfo('EXPECTED EDC = ' + expectedEdc)

'Pilih Tipe Config'
Windows.click(findWindowsObject('Hardware POS/cb_tipeConfig'))

Windows.delay(2)

Windows.click(findWindowsObject('Hardware POS/ipAddress'))

Windows.delay(2)

'Input IP Address'
String ipAddress = '19.11.1.1.1.1'

Windows.setText(findWindowsObject('Hardware POS/txt_ipAddress'), ipAddress)

Windows.delay(2)

KeywordUtil.logInfo('IP ADDRESS = ' + ipAddress)

'Klik tombol Save'
Windows.click(findWindowsObject('Hardware POS/btn_save'))

Windows.delay(3)

'Get Text dari BerhasilDisimpan'
berhasil = Windows.getText(findWindowsObject('Hardware POS/txt_berhasilDisimpan'))

Windows.delay(3)

'Klik tombol close setelah menyimpan'
Windows.click(findWindowsObject('Hardware POS/btn_closeSetelahSimpan'))

Windows.takeScreenshot()

'Validasi data di table'
boolean isFound = false

for (int row = 0; row <= 10; row++) {
    try {
        String actualRow = Windows.getText(findWindowsObject('Hardware POS/row_validation', [('row') : row]))

        KeywordUtil.logInfo((('ROW ' + row) + ' = ') + actualRow)

        'validation'
        if (actualRow.contains(expectedEdc)) {
            isFound = true

            KeywordUtil.markPassed((((('✅ DATA EDC BERHASIL DITEMUKAN DI TABLE\n' + 'EXPECTED EDC = ') + expectedEdc) + 
                '\n') + 'FOUND AT ROW = ') + row)

            break
        }
    }
    catch (Exception e) {
        KeywordUtil.logInfo(('ROW ' + row) + ' TIDAK ADA')
    } 
}

'Final Validation'
if (!(isFound)) {
    KeywordUtil.markWarning(('❌ DATA EDC TIDAK DITEMUKAN DI TABLE\n' + 'EXPECTED EDC = ') + expectedEdc)
}

Windows.takeScreenshot()

Windows.click(findWindowsObject('Hardware POS/btn_closeSetting'))

'Logout / Close Application'
CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Login')

Windows.click(findWindowsObject('new login/btn_closeApplication'))

