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

Windows.startApplicationWithTitle('C:\\Program Files (x86)\\Lion Super Indo\\LSI.POS.CashRegister\\LSI.POS.CashRegister.exe', 
    'Transaksi Kasir (PT Lion Super Indo v.2.1.1.75)')

Windows.click(findWindowsObject('Object Repository/Credit Card/btn_creditCard'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/btn_creditCard'), '')

Windows.click(findWindowsObject('Object Repository/Credit Card/txt_nomorKartu'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/txt_nomorKartu'), '')

Windows.click(findWindowsObject('Object Repository/Credit Card/txt_nomorKartu'))

Windows.click(findWindowsObject('Object Repository/Credit Card/txt_nomorKartu'))

Windows.click(findWindowsObject('Object Repository/Credit Card/Pane'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/Pane'), '')

Windows.click(findWindowsObject('Object Repository/Credit Card/btn_yaPilihEDC'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/cb_namaBankTipe'), '')

Windows.click(findWindowsObject('Object Repository/Credit Card/cb_namaBankTipe'))

Windows.click(findWindowsObject('Object Repository/Credit Card/pilihTipeKartu'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/pilihTipeKartu'), 'CREDIT')

Windows.click(findWindowsObject('Object Repository/Credit Card/cb_EDC'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/cb_EDC'), 'BNI')

Windows.click(findWindowsObject('Object Repository/Credit Card/cb_EDC'))

Windows.click(findWindowsObject('Object Repository/Credit Card/pilihEDC'))

Windows.click(findWindowsObject('Object Repository/Credit Card/Button'))

Windows.click(findWindowsObject('Object Repository/Credit Card/Button(1)'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/Button'), '')

Windows.click(findWindowsObject('Object Repository/Credit Card/Button'))

Windows.click(findWindowsObject('Object Repository/Credit Card/Button(2)'))

Windows.setText(findWindowsObject('Object Repository/Credit Card/Button(2)'), 'Tutup')

