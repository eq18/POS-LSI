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

Windows.startApplicationWithTitle('C:\\Users\\kasir\\AppData\\Local\\Apps\\2.0\\3KTNRRKW.CXY\\19N75WPK.ZJN\\lsi...tion_0000000000000000_0002.0001_c2e529750677f20f\\LSI.POS.CashRegister.exe', 
    'Transaksi Kasir (PT Lion Super Indo v.2.1.1.74)')

Windows.setText(findWindowsObject('Object Repository/refund/Edit'), '')

Windows.doubleClick(findWindowsObject('Object Repository/refund/Pane'))

Windows.setText(findWindowsObject('Object Repository/refund/Pane'), 'Development 2.1.1.74')

Windows.setText(findWindowsObject('Object Repository/refund/Edit'), '20')

Windows.click(findWindowsObject('Object Repository/refund/btn_yaTrx250'))

