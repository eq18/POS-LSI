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
import com.kms.katalon.core.keyword.BuiltinKeywords as KeywordUtil
import com.kms.katalon.core.testdata.TestDataFactory

Windows.callTestCase(findTestCase('ARCHIEVE/Additional Case/Login'), [:], FailureHandling.STOP_ON_FAILURE)

Windows.takeScreenshot()

'Klik Menu Transaksi Kasir'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_transaksiKasir'))

Windows.delay(2)

Windows.switchToWindowTitle('Transaksi Kasir')

Windows.takeScreenshot()

'Klik tombol close pada notif'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_close'))

Windows.takeScreenshot()

Windows.delay(2)

TestData dataPLU = TestDataFactory.findTestData('PLU - Excel')
int totalRow = dataPLU.getRowNumbers()

for (int row = 1; row <= totalRow; row++) {

	'Input PLU'
	Windows.setText(
		findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'),
		dataPLU.getValue(1, row)
	)

	'Tekan Enter'
	Windows.sendKeys(
		findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'),
		Keys.chord(Keys.ENTER)
	)

	'Input Quantity'
	Windows.setText(
		findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'),
		dataPLU.getValue(2, row)
	)

	Windows.delay(1)

	'Klik Enter / lanjut'
	Windows.click(findWindowsObject('Object Repository/ss/btn_enter'))
}



//sadkhadihavfhaohoiahfhafjhihguagfoaa//
/*
'Input PLU'
Windows.setText(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), findTestData('PLU - excel').getValue(1, 1))

'Tekan enter pada keyboard'
Windows.sendKeys(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

'Input Quantity'
Windows.setText(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), findTestData('PLU').getValue(2, 1))

Windows.delay(2)

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Object Repository/ss/btn_enter'))
*/
Windows.delay(2)

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Object Repository/ss/btn_enter'))

Windows.takeScreenshot()

'Ambil total belanja'
total = Windows.getText(findWindowsObject('Object Repository/ss/txt_totalYangHarusDibayar'))

GlobalVariable.total = total

// hapus koma
int nilai = Integer.parseInt(total.replace(",", ""))

// bulatkan ke atas ke ratusan
int totalAmount = Math.ceil(nilai / 100.0) * 100
Windows.comment(totalAmount.toString())
totalBelanja = totalAmount.toString()

'Input total belanja'
Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), totalBelanja)

Windows.delay(2)

Windows.takeScreenshot()

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.takeScreenshot()

'Close Notification'
Windows.click(findWindowsObject('Object Repository/ss/btn_close1'))

Windows.delay(1)

Windows.takeScreenshot()

'Close Notification'
Windows.click(findWindowsObject('Object Repository/ss/btn_close2'))

permbayaranBerhasil = Windows.getText(findWindowsObject('Object Repository/ss/txt_pembayaranBerhasil'))

if (permbayaranBerhasil.contains(permbayaranBerhasil)) {
    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/ss/btn_tutup'))

    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/Menu Utama'))

    Windows.callTestCase(findTestCase('ARCHIEVE/Additional Case/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
}

def td = findTestData('Cash Payment')

String expectedResult = td.getValue('postransactionpayment_Value', 1)

String expectedStr = expectedResult.toString().trim().replace(',', '')

String actualStr = total.toString().trim().replace(',', '')

Windows.comment('Expected raw : ' + expectedResult)

Windows.comment('Actual raw   : ' + total)

Windows.comment('Expected clean: ' + expectedStr)

Windows.comment('Actual clean  : ' + actualStr)

BigDecimal expected = new BigDecimal(expectedStr)

BigDecimal actual = new BigDecimal(actualStr)

if (expected.compareTo(actual) == 0) {
    Windows.comment("Data Sesuai ✅ expected=$expected actual=$actual")
} else {
    Windows.comment("Data Tidak Sesuai ❌ expected=$expected actual=$actual")
}

