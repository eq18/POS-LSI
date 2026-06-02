import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

// ======================================================
// LOGIN
// ======================================================
Windows.callTestCase(findTestCase('Additional Cases/Login'), [:], FailureHandling.STOP_ON_FAILURE)

Windows.takeScreenshot()

promo = 'Package Digital Buy 5 Get 1'

Windows.comment('Promo Package = ' + promo)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Main Menu')

// ======================================================
// OPEN TRANSAKSI KASIR
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_transaksiKasir'))

Windows.delay(2)

Windows.takeScreenshot()

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Transaksi Kasir')

Windows.takeScreenshot()

Windows.delay(3)

// ======================================================
// INPUT MEMBER
// ======================================================
Windows.setText(findWindowsObject('Transaksi Kasir/txt_member'), GlobalVariable.member)

Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_member'), Keys.chord(Keys.ENTER))

Windows.delay(2)

String namaMember = Windows.getText(findWindowsObject('Transaksi Kasir/namaMember'))

KeywordUtil.logInfo('NAMA MEMBER = ' + namaMember)

Windows.takeScreenshot()

// ======================================================
// LOAD EXCEL
// ======================================================
TestData td = TestDataFactory.findTestData('Promotion Folder/Package Digital Beli 5 Lebih Hemat')

// ======================================================
// INPUT ALL PLU
// ======================================================
for (int row = 1; row <= 3; row++) {
    String plu = td.getValue('plu', row)

    String qty = td.getValue('qty', row)

    KeywordUtil.logInfo('INPUT PLU = ' + plu)

    KeywordUtil.logInfo('INPUT QTY = ' + qty)

    // ==============================================
    // INPUT PLU
    // ==============================================
    Windows.click(findWindowsObject('Transaksi Kasir/txt_PLU'))

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), plu)

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

    Windows.delay(2)

    // ==============================================
    // INPUT QTY
    // ==============================================
    Windows.setText(findWindowsObject('Transaksi Kasir/txt_PLU'), qty)

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

    Windows.delay(3)
}

Windows.takeScreenshot()

// ======================================================
// CLICK CEK DISKON
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_cekDiskon'))

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// VALIDASI DISKON
// ======================================================
long expectedDiskon = td.getValue('diskon', 1).toLong()

String actualDiskonStr = Windows.getText(findWindowsObject('Transaksi Kasir/lbl_diskon_1'))

long actualDiskon = Math.abs(cleanNumber(actualDiskonStr))

KeywordUtil.logInfo('EXPECTED DISKON = ' + expectedDiskon)

KeywordUtil.logInfo('ACTUAL DISKON = ' + actualDiskon)

// ======================================================
// VALIDATION
// ======================================================
if (actualDiskon == expectedDiskon) {
    KeywordUtil.markPassed((((('✅ DISKON SESUAI\n' + 'EXPECTED = ') + expectedDiskon) + '\n') + 'ACTUAL = ') + actualDiskon)
} else {
    KeywordUtil.markFailed((((('❌ DISKON TIDAK SESUAI\n' + 'EXPECTED = ') + expectedDiskon) + '\n') + 'ACTUAL = ') + actualDiskon)
}

Windows.takeScreenshot()

// ======================================================
// CLICK TUTUP
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_tutupCekDiskon'))

Windows.delay(2)

// ======================================================
// PAYMENT
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter (1)'))

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// CLICK ENTER PAYMENT
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

// ======================================================
// GET TOTAL PEMBAYARAN
// ======================================================
String totalPembayaranStr = Windows.getText(findWindowsObject('Transaksi Kasir/txt_totalYangHarusDibayar'))

long totalPembayaranAct = cleanNumber(totalPembayaranStr)

// ======================================================
// VALIDASI TOTAL PEMBAYARAN
// ======================================================
long grandTotalExpected = td.getValue('expected', 1).toLong()

KeywordUtil.logInfo('TOTAL EXPECTED = ' + grandTotalExpected)

KeywordUtil.logInfo('TOTAL ACTUAL = ' + totalPembayaranAct)

if (grandTotalExpected == totalPembayaranAct) {
    KeywordUtil.markPassed((((('✅ TOTAL PEMBAYARAN SESUAI\n' + 'EXPECTED = ') + grandTotalExpected) + '\n') + 'ACTUAL = ') + 
        totalPembayaranAct)
} else {
    KeywordUtil.markFailed((((('❌ TOTAL PEMBAYARAN TIDAK SESUAI\n' + 'EXPECTED = ') + grandTotalExpected) + '\n') + 'ACTUAL = ') + 
        totalPembayaranAct)
}

Windows.takeScreenshot()

// ======================================================
// INPUT PEMBAYARAN
// ======================================================
int nilai = Integer.parseInt(totalPembayaranStr.replace(',', ''))

int totalAmount = Math.ceil(nilai / 100.0) * 100

String totalBelanja = totalAmount.toString()

KeywordUtil.logInfo('TOTAL BAYAR = ' + totalBelanja)

Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), totalBelanja)

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// FINAL PAYMENT
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.takeScreenshot()

// ======================================================
// CLOSE PAYMENT
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_close1'), FailureHandling.OPTIONAL)

Windows.delay(1)

Windows.takeScreenshot()

Windows.click(findWindowsObject('Transaksi Kasir/btn_close2'))

String pembayaranBerhasil = Windows.getText(findWindowsObject('Transaksi Kasir/txt_pembayaranBerhasil'))

if (pembayaranBerhasil.length() > 0) {
    KeywordUtil.markPassed('✅ PAYMENT BERHASIL')

    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Transaksi Kasir/btn_tutup'))

    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Transaksi Kasir/Menu Utama'))
}

// ======================================================
// LOGOUT
// ======================================================
Windows.callTestCase(findTestCase('Additional Cases/Logout'), [:], FailureHandling.STOP_ON_FAILURE)

def td2 = findTestData('Query DB/Cash Payment')

def td1 = findTestData('Query DB/Validation')

String expectedResult = td2.getValue('postransactionpayment_Value', 1)

String expectedResult_fhbkas = td1.getValue('FHBKAS', 1)

String startTime = td2.getValue('postransactionpayment_StartTime', 1)

String endTime = td2.getValue('postransactionpayment_EndTime', 1)

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

def cleanNumber(String value) {
    return value.replaceAll('[^0-9-]', '').toLong()
}

