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
import com.kms.katalon.core.util.KeywordUtil

Windows.callTestCase(findTestCase('ARCHIEVE/Additional Case/Login'), [:], FailureHandling.STOP_ON_FAILURE)


'Klik Menu Kas Awal'
Windows.click(findWindowsObject('Object Repository/Kas Awal/Kas Awal'))

'Input User ID'
Windows.setText(findWindowsObject('Object Repository/Kas Awal/txt_user'), GlobalVariable.userId)

Windows.sendKeys(findWindowsObject('Object Repository/Kas Awal/txt_user'), Keys.chord(Keys.ENTER))

awalKas = Windows.getText(findWindowsObject('Kas Awal/txt_kasAwal'))

Windows.comment('Awal Kas: '+awalKas)

'Klik tombol simpan'
Windows.click(findWindowsObject('Object Repository/Kas Awal/btn_simpan'), FailureHandling.STOP_ON_FAILURE)

'Klik tombol YA'
Windows.click(findWindowsObject('Object Repository/print/btn_YA'))

'Klik tombol close untuk menutup notification'
Windows.click(findWindowsObject('Object Repository/print/btn_close'))

validation = Windows.getText(findWindowsObject('Object Repository/print/txt_berhasil'))

if (validation.contains(validation)) /*harus diganti ini nanti yak*/ {
	
	Windows.comment(validation)
	
	KeywordUtil.markPassed('Berhasil untuk melihat Kas Awal')
	
	Windows.delay(3)

	Windows.click(findWindowsObject('Object Repository/print/btn_close2'))
	
	Windows.delay(5)
	
	Windows.callTestCase(findTestCase('ARCHIEVE/Additional Case/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
	
}


