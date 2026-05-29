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

'Ambil Data dari Excel'
TestData td = TestDataFactory.findTestData('Data Files/Hardware/EDC Status')

'Get ROW yang ada di excel'
int totalRow = td.getRowNumbers()

KeywordUtil.logInfo("TOTAL ROW = $totalRow")

'Looping EDC'
for (int row = 1; row <= totalRow; row++) {
    'Excel Data'
    String edcName = td.getValue('edc', row)

    String expectedStatus = td.getValue('expectedStatus', row)

    KeywordUtil.logInfo('====================================')

    KeywordUtil.logInfo("TEST EDC = $edcName")

    'Klik ROW'
    Windows.click(findWindowsObject('Hardware POS/row_dynamic', [('row') : row - 1]))

    Windows.delay(2)

    Windows.takeScreenshot()

    'Klik tombol check status'
    Windows.click(findWindowsObject('Hardware POS/btn_checkStatus'))

    Windows.delay(5)

    Windows.takeScreenshot()

    'Get Text Status'
    String actualStatus = Windows.getText(findWindowsObject('Hardware POS/lbl_status_dynamic', [('row') : row - 1]))

    KeywordUtil.logInfo("EXPECTED STATUS = $expectedStatus")

    KeywordUtil.logInfo("ACTUAL STATUS   = $actualStatus")

    'Validasi'
    if (actualStatus.contains(expectedStatus)) {
        KeywordUtil.markPassed((('✅ STATUS VALID\n' + "EDC      : $edcName") + "Expected : $expectedStatus") + "Actual   : $actualStatus")
    } else {
        KeywordUtil.markWarning((('❌ STATUS INVALID\n' + "EDC      : $edcName") + "Expected : $expectedStatus") + "Actual   : $actualStatus")
    }
    
    Windows.takeScreenshot()
}

Windows.sendKeys(findWindowsObject('Hardware POS/btn_closeSetting'), Keys.chord(Keys.ENTER))

'Logout / Close Application'
CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Login')

Windows.click(findWindowsObject('new login/btn_closeApplication'))

