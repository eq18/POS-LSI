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

Windows.click(findWindowsObject('Object Repository/refund/btn_tutup'))

Windows.setText(findWindowsObject('Object Repository/refund/Button'), '0')

Windows.setText(findWindowsObject('Object Repository/refund/Pane'), 'Development 2.1.1.74')

Windows.setText(findWindowsObject('Object Repository/refund/Edit'), '2130092')

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.doubleClick(findWindowsObject('Object Repository/refund/Edit'))

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.setText(findWindowsObject('Object Repository/refund/Edit'), '20')

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.setText(findWindowsObject('Object Repository/refund/Button(1)'), 'Ubah Harga')

Windows.click(findWindowsObject('Object Repository/refund/Table'))

Windows.click(findWindowsObject('Object Repository/refund/Button(2)'))

Windows.doubleClick(findWindowsObject('Object Repository/refund/Button(2)'))

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.click(findWindowsObject('Object Repository/refund/Button(3)'))

Windows.click(findWindowsObject('Object Repository/refund/Button(4)'))

Windows.click(findWindowsObject('Object Repository/refund/Button(3)'))

Windows.setText(findWindowsObject('Object Repository/refund/Edit(1)'), '30')

Windows.setText(findWindowsObject('Object Repository/refund/Button(5)'), '4')

Windows.setText(findWindowsObject('Object Repository/refund/Button'), '0')

Windows.click(findWindowsObject('Object Repository/refund/Pane(1)'))

Windows.setText(findWindowsObject('Object Repository/refund/Button(6)'), '000')

Windows.click(findWindowsObject('Object Repository/refund/btn_tutup'))

Windows.click(findWindowsObject('Object Repository/refund/lbl_penjualan'))

Windows.setText(findWindowsObject('Object Repository/refund/lbl_penjualan'), 'Trx : 00023')

Windows.setText(findWindowsObject('Object Repository/refund/Table'), 'DataGridView')

Windows.click(findWindowsObject('Object Repository/refund/Button(2)'))

Windows.setText(findWindowsObject('Object Repository/refund/Button(2)'), 'Refund')

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.click(findWindowsObject('Object Repository/refund/Button(2)'))

Windows.doubleClick(findWindowsObject('Object Repository/refund/Button(2)'))

Windows.click(findWindowsObject('Object Repository/refund/Button(2)'))

Windows.setText(findWindowsObject('Object Repository/refund/Button(2)'), 'Refund')

Windows.click(findWindowsObject('Object Repository/refund/Table'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.doubleClick(findWindowsObject('Object Repository/refund/date_refund'))

Windows.doubleClick(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/Pane(2)'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.setText(findWindowsObject('Object Repository/refund/date_refund'), '07/06/26')

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.click(findWindowsObject('Object Repository/refund/Pane(2)'))

Windows.click(findWindowsObject('Object Repository/refund/Edit(2)'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.setText(findWindowsObject('Object Repository/refund/date_refund'), '02/07/2026')

Windows.click(findWindowsObject('Object Repository/refund/Edit(2)'))

Windows.click(findWindowsObject('Object Repository/refund/date_refund'))

Windows.setText(findWindowsObject('Object Repository/refund/date_refund'), '02/07/2025')

Windows.click(findWindowsObject('Object Repository/refund/Edit(2)'))

Windows.click(findWindowsObject('Object Repository/refund/Edit(2)'))

Windows.click(findWindowsObject('Object Repository/refund/Edit(2)'))

Windows.setText(findWindowsObject('Object Repository/refund/txt_refundKasir'), '')

Windows.click(findWindowsObject('Object Repository/refund/txt_refundKasir'))

Windows.setText(findWindowsObject('Object Repository/refund/txt_refundKasir'), '')

Windows.click(findWindowsObject('Object Repository/refund/txt_refundStation'))

Windows.setText(findWindowsObject('Object Repository/refund/txt_refundStation'), '')

Windows.setText(findWindowsObject('Object Repository/refund/Pane(3)'), '')

Windows.click(findWindowsObject('Object Repository/refund/txt_refundNoTrans'))

Windows.setText(findWindowsObject('Object Repository/refund/txt_refundNoTrans'), '')

Windows.click(findWindowsObject('Object Repository/refund/txt_refundKasir'))

Windows.setText(findWindowsObject('Object Repository/refund/Pane(2)'), 'Senilai: ?')

Windows.setText(findWindowsObject('Object Repository/refund/txt_refundStation'), '')

Windows.click(findWindowsObject('Object Repository/refund/txt_refundStation'))

Windows.setText(findWindowsObject('Object Repository/refund/txt_refundStation'), '')

Windows.setText(findWindowsObject('Object Repository/refund/Pane(2)'), 'Senilai: ?')

Windows.click(findWindowsObject('Object Repository/refund/txt_refundNoTrans'))

Windows.setText(findWindowsObject('Object Repository/refund/txt_refundNoTrans'), '00022')

Windows.click(findWindowsObject('Object Repository/refund/btn_cariRefundTrx'))

Windows.click(findWindowsObject('Object Repository/refund/Table'))

Windows.click(findWindowsObject('Object Repository/refund/Table'))

Windows.click(findWindowsObject('Object Repository/refund/Button(2)'))

Windows.click(findWindowsObject('Object Repository/refund/Edit'))

Windows.setText(findWindowsObject('Object Repository/refund/Edit'), '2130092')

Windows.setText(findWindowsObject('Object Repository/refund/Pane'), 'Development 2.1.1.74')

Windows.click(findWindowsObject('Object Repository/refund/Button(3)'))

Windows.click(findWindowsObject('Object Repository/refund/btn_refundOkGatau'))

Windows.setText(findWindowsObject('Object Repository/refund/btn_tutupRfundSelesai'), '2')

Windows.click(findWindowsObject('Object Repository/refund/Table'))

Windows.setText(findWindowsObject('Object Repository/refund/Pane'), 'Development 2.1.1.74')

Windows.closeApplication()

