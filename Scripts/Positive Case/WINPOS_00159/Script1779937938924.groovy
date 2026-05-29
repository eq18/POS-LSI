import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import org.openqa.selenium.Keys as Keys
import internal.GlobalVariable as GlobalVariable

// ======================================================
// FUNCTION CLEAN NUMBER
// ======================================================
// ======================================================
// LOGIN
// ======================================================
Windows.callTestCase(findTestCase('Additional Cases/Login'), [:], FailureHandling.STOP_ON_FAILURE)

Windows.takeScreenshot()

promo = 'Package Digital Beli 3 Gratis 1'

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
// LOAD EXCEL
// ======================================================
TestData td = TestDataFactory.findTestData('Promotion Folder/Package Digital Beli 3 Gratis 1')

// ======================================================
// PAYMENT 1
// ROW 1
// ======================================================
for (int row = 1; row <= 1; row++) {
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
// CLICK CEK DISKON PAYMENT 1
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_cekDiskon'))

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// VALIDASI DISKON PAYMENT 1
// ======================================================
long expectedDiskon1 = td.getValue('diskon', 1).toLong()

String actualDiskonStr1 = Windows.getText(findWindowsObject('Transaksi Kasir/lbl_diskon_1'))

long actualDiskon1 = Math.abs(cleanNumber(actualDiskonStr1))

KeywordUtil.logInfo('EXPECTED DISKON 1 = ' + expectedDiskon1)

KeywordUtil.logInfo('ACTUAL DISKON 1 = ' + actualDiskon1)

if (actualDiskon1 == expectedDiskon1) {
    KeywordUtil.markPassed('✅ DISKON PAYMENT 1 SESUAI')
} else {
    KeywordUtil.markFailed('❌ DISKON PAYMENT 1 TIDAK SESUAI')
}

Windows.takeScreenshot()

// ======================================================
// CLICK TUTUP
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_tutupCekDiskon'))

Windows.delay(2)

// ======================================================
// PAYMENT PAGE 1
// ======================================================
Windows.click(findWindowsObject('ss/btn_enter'))

Windows.delay(2)

Windows.takeScreenshot()

Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

// ======================================================
// GET TOTAL PEMBAYARAN 1
// ======================================================
String totalPembayaranStr1 = Windows.getText(findWindowsObject('ss/txt_totalYangHarusDibayar'))

long totalPembayaranAct1 = cleanNumber(totalPembayaranStr1)

// ======================================================
// VALIDASI TOTAL PAYMENT 1
// ======================================================
long grandTotalExpected1 = td.getValue('expected', 1).toLong()

KeywordUtil.logInfo('TOTAL EXPECTED 1 = ' + grandTotalExpected1)

KeywordUtil.logInfo('TOTAL ACTUAL 1 = ' + totalPembayaranAct1)

if (grandTotalExpected1 == totalPembayaranAct1) {
    KeywordUtil.markPassed('✅ TOTAL PAYMENT 1 SESUAI')
} else {
    KeywordUtil.markFailed('❌ TOTAL PAYMENT 1 TIDAK SESUAI')
}

Windows.takeScreenshot()

// ======================================================
// INPUT PEMBAYARAN 1
// ======================================================
int nilai1 = Integer.parseInt(totalPembayaranStr1.replace(',', ''))

int totalAmount1 = Math.ceil(nilai1 / 100.0) * 100

String totalBelanja1 = totalAmount1.toString()

Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), totalBelanja1)

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// FINAL PAYMENT 1
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.takeScreenshot()

String pembayaranBerhasil1 = Windows.getText(findWindowsObject('ss/txt_pembayaranBerhasil'))

if (pembayaranBerhasil1.length() > 0) {
    KeywordUtil.markPassed('✅ PAYMENT 1 BERHASIL')

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

    String actualStr = totalPembayaranStr1.toString().trim().replace(',', '')

    // ===== Log raw vs clean =====
    Windows.comment('Expected raw (Payment Value): ' + expectedResult)

    Windows.comment('Expected raw (FHBKAS)       : ' + expectedResult_fhbkas)

    Windows.comment('Actual raw (total)          : ' + totalPembayaranStr1)

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
    
    Windows.click(findWindowsObject('ss/btn_tutup'))

    Windows.delay(2)
}

// ======================================================
// PAYMENT 2
// ROW 2
// ======================================================
for (int row = 2; row <= 2; row++) {
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
// CLICK CEK DISKON PAYMENT 2
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_cekDiskon'))

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// VALIDASI DISKON PAYMENT 2
// ======================================================
for (int x = 1; x <= 1; x++) {
    long expectedDiskon = td.getValue('diskon', x).toLong()

    String actualDiskonStr

    if (x == 1) {
        actualDiskonStr = Windows.getText(findWindowsObject('Transaksi Kasir/lbl_diskon_1'))
    } else {
        actualDiskonStr = Windows.getText(findWindowsObject('Transaksi Kasir/lbl_diskon_2'))
    }
    
    long actualDiskon = Math.abs(cleanNumber(actualDiskonStr))

    KeywordUtil.logInfo('EXPECTED DISKON = ' + expectedDiskon)

    KeywordUtil.logInfo('ACTUAL DISKON = ' + actualDiskon)

    if (expectedDiskon == actualDiskon) {
        KeywordUtil.markPassed('✅ DISKON SESUAI')
    } else {
        KeywordUtil.markFailed('❌ DISKON TIDAK SESUAI')
    }
}

Windows.takeScreenshot()

// ======================================================
// CLICK TUTUP
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_tutupCekDiskon'))

Windows.delay(2)

// ======================================================
// PAYMENT PAGE 2
// ======================================================
Windows.click(findWindowsObject('ss/btn_enter'))

Windows.delay(2)

Windows.takeScreenshot()

Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

// ======================================================
// GET TOTAL PEMBAYARAN 2
// ======================================================
String totalPembayaranStr2 = Windows.getText(findWindowsObject('ss/txt_totalYangHarusDibayar'))

long totalPembayaranAct2 = cleanNumber(totalPembayaranStr2)

// ======================================================
// VALIDASI TOTAL PAYMENT 2
// ======================================================
long expected2 = td.getValue('expected', 2).toLong()


KeywordUtil.logInfo('TOTAL EXPECTED 2 = ' + expected2)

KeywordUtil.logInfo('TOTAL ACTUAL 2 = ' + totalPembayaranAct2)

if (expected2 == totalPembayaranAct2) {
    KeywordUtil.markPassed('✅ TOTAL PAYMENT 2 SESUAI')
} else {
    KeywordUtil.markFailed('❌ TOTAL PAYMENT 2 TIDAK SESUAI')
}

Windows.takeScreenshot()

// ======================================================
// INPUT PEMBAYARAN 2
// ======================================================
int nilai2 = Integer.parseInt(totalPembayaranStr2.replace(',', ''))

int totalAmount2 = Math.ceil(nilai2 / 100.0) * 100

String totalBelanja2 = totalAmount2.toString()

Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), totalBelanja2)

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// FINAL PAYMENT 2
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.takeScreenshot()

String pembayaranBerhasil2 = Windows.getText(findWindowsObject('ss/txt_pembayaranBerhasil'))

if (pembayaranBerhasil2.length() > 0) {
    KeywordUtil.markPassed('✅ PAYMENT 2 BERHASIL')

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

    String actualStr = totalPembayaranStr2.toString().trim().replace(',', '')

    // ===== Log raw vs clean =====
    Windows.comment('Expected raw (Payment Value): ' + expectedResult)

    Windows.comment('Expected raw (FHBKAS)       : ' + expectedResult_fhbkas)

    Windows.comment('Actual raw (total)          : ' + totalPembayaranStr2)

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
    
    Windows.click(findWindowsObject('ss/btn_tutup'))

    Windows.delay(2)

    Windows.click(findWindowsObject('Transaksi Kasir/Menu Utama'))
}

// ======================================================
// LOGOUT
// ======================================================
Windows.callTestCase(findTestCase('Additional Cases/Logout'), [:], FailureHandling.STOP_ON_FAILURE)

def cleanNumber(String value) {
    return value.replaceAll('[^0-9-]', '').toLong()
}

