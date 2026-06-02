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

def td2 = findTestData('PLU - DB')

String plu = td2.getValue('product_Code', 2)

'Input PLU'
Windows.setText(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), plu)

'Tekan enter pada keyboard'
Windows.sendKeys(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

Windows.delay(2)

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_enter (1)'))

Windows.delay(10)

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_enter (1)'))

Windows.takeScreenshot()

'Ambil total belanja'
total = Windows.getText(findWindowsObject('Object Repository/Transaksi Kasir/txt_totalYangHarusDibayar'))

'Input total belanja'
Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), total)

Windows.delay(2)

Windows.takeScreenshot()

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.takeScreenshot()

'Close Notification'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_close1'))

Windows.delay(1)

Windows.takeScreenshot()

'Close Notification'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_close2'))

permbayaranBerhasil = Windows.getText(findWindowsObject('Object Repository/Transaksi Kasir/txt_pembayaranBerhasil'))

if (permbayaranBerhasil.contains(permbayaranBerhasil)) {
    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_tutup'))

    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/Menu Utama'))

    Windows.callTestCase(findTestCase('ARCHIEVE/Additional Case/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
}

def td = findTestData('Cash Payment')

def td1 = findTestData('Validation')

String expectedResult = td.getValue('postransactionpayment_Value', 1)

String expectedResult_fhbkas = td1.getValue('FHBKAS', 1)

String startTime = td.getValue('postransactionpayment_StartTime', 1)

String endTime = td.getValue('postransactionpayment_EndTime', 1)

Windows.comment('Start Time: ' + startTime)

Windows.comment('End Time  : ' + endTime)

// ===== Clean data (remove comma & trim) =====
String expectedStr = expectedResult.toString().trim().replace(',', '')

String expectedStr_fhbkas = expectedResult_fhbkas.toString().trim().replace(',', '')

String actualStr = total.toString().trim().replace(',', '')

// ===== Log raw vs clean =====
Windows.comment('Expected raw (Payment Value): ' + expectedResult)

Windows.comment('Expected raw (FHBKAS)       : ' + expectedResult_fhbkas)

Windows.comment('Actual raw (total)          : ' + total)

Windows.comment('Expected clean (Payment Value): ' + expectedStr)

Windows.comment('Expected clean (FHBKAS)       : ' + expectedStr_fhbkas)

Windows.comment('Actual clean (total)          : ' + actualStr)

// ===== Convert to BigDecimal =====
BigDecimal expected = new BigDecimal(expectedStr)

BigDecimal expected_fhbkas = new BigDecimal(expectedStr_fhbkas)

BigDecimal actual = new BigDecimal(actualStr)

// ===== Validation 1: Payment Value vs Total =====
if (expected.compareTo(actual) == 0) {
    Windows.comment("✅ Payment Value sesuai | expected=$expected actual=$actual")
} else {
    Windows.comment("❌ Payment Value tidak sesuai | expected=$expected actual=$actual")
}

// ===== Validation 2: FHBKAS vs Total (optional) =====
if (expected_fhbkas.compareTo(actual) == 0) {
    Windows.comment("✅ FHBKAS sesuai | expected=$expected_fhbkas actual=$actual")
} else {
    Windows.comment("❌ FHBKAS tidak sesuai | expected=$expected_fhbkas actual=$actual")
}

