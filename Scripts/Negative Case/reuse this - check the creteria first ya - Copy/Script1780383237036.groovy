import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.WindowsTestObject as WindowsTestObject

'Login'
Windows.callTestCase(findTestCase('Additional Cases/Login'), [:], FailureHandling.STOP_ON_FAILURE)

Windows.takeScreenshot()

promo = 'Voucher Supplier Max Purchase 0'

Windows.comment('Promo Package = ' + promo)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Main Menu')

'Klik tombol transaksi kasir'
Windows.click(findWindowsObject('Transaksi Kasir/btn_transaksiKasir'))

Windows.delay(2)

Windows.takeScreenshot()

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Transaksi Kasir')

Windows.takeScreenshot()

Windows.delay(3)

'Load Excel'
TestData td = TestDataFactory.findTestData('Promotion Folder/Voucher Supplier - Max Purchase 0')

'Input PLU'
for (int row = 1; row <= 1; row++) {
    String plu = td.getValue('plu', row)

    String qty = td.getValue('qty', row)

    KeywordUtil.logInfo('INPUT PLU = ' + plu)

    KeywordUtil.logInfo('INPUT QTY = ' + qty)

    Windows.click(findWindowsObject('Transaksi Kasir/txt_PLU'))

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), plu)

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

    Windows.delay(2)

    Windows.setText(findWindowsObject('Transaksi Kasir/txt_PLU'), qty)

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

    Windows.delay(3)
}

Windows.takeScreenshot()

'Melakukan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter (1)'))

Windows.delay(3)

Windows.takeScreenshot()

'Klik tombol voucher supplier'
Windows.click(findWindowsObject('Transaksi Kasir/btn_voucherSupplier'))

Windows.delay(2)

'Klik checkbox kode voucher'
Windows.click(findWindowsObject('Voucher Supplier/cb_kodeVoucher'))

String voucherName = td.getValue('voucherName', 1)

KeywordUtil.logInfo('NAMA VOUCHER = ' + voucherName)

Windows.delay(2)

'Pilih list voucher'
Windows.click(findWindowsObject('Voucher Supplier/list_voucher', [('voucherName') : voucherName]))

//Windows.comment(dynamicVoucher)
//Windows.click(dynamicVoucher)
//Windows.click(findWindowsObject('Voucher Supplier/list_voucher',[('voucherName') : voucherName]))
'Input Quantity Voucher'
String qtyVoucher = td.getValue('qtyVoucher', 1)

Windows.setText(findWindowsObject('Voucher Supplier/txt_qtyVoucher'), qtyVoucher)

Windows.sendKeys(findWindowsObject('Voucher Supplier/txt_qtyVoucher'), Keys.chord(Keys.ENTER))

Windows.delay(3)

Windows.takeScreenshot()

namaVoucher = Windows.getText(findWindowsObject('Voucher Supplier/namaVoucher'))

Windows.delay(3)

Windows.takeScreenshot()

'Melakukan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter (1)'))

'Validasi pembayaran ke 1'
String totalPembayaranStr1 = Windows.getText(findWindowsObject('Transaksi Kasir/txt_totalYangHarusDibayar'))

long totalPembayaranAct1 = cleanNumber(totalPembayaranStr1)

// row1 expected + row2 expected
// ======================================================
'Expected Payment'
long expected1 = td.getValue('expected', 1).toLong()

long grandTotalExpected1 = expected1

KeywordUtil.logInfo('EXPECTED PAYMENT 1 = ' + grandTotalExpected1)

KeywordUtil.logInfo('ACTUAL PAYMENT 1 = ' + totalPembayaranAct1)

'Validasi'
if (grandTotalExpected1 == totalPembayaranAct1) {
    KeywordUtil.markPassed((((('✅ PAYMENT 1 SESUAI\n' + 'EXPECTED = ') + grandTotalExpected1) + '\n') + 'ACTUAL = ') + totalPembayaranAct1)
} else {
    KeywordUtil.markFailed((((('❌ PAYMENT 1 TIDAK SESUAI\n' + 'EXPECTED = ') + grandTotalExpected1) + '\n') + 'ACTUAL = ') + 
        totalPembayaranAct1)
}

'Validasi Voucher 1'
String actualDiskon1 = Windows.getText(findWindowsObject('Voucher Supplier/voucher'))
String actualVoucher = Windows.getText(findWindowsObject('Voucher Supplier/expectedDiskon'))

String expectedDiskon1 = td.getValue('diskon', 1)
String expectedVoucher = td.getValue('expected', 1)

// remove comma & trim
actualDiskon1 = actualDiskon1.replace(',', '').trim()
expectedDiskon1 = expectedDiskon1.replace(',', '').trim()

actualVoucher = actualVoucher.replace(',', '').trim()
expectedVoucher = expectedVoucher.replace(',', '').trim()

// VALIDASI DISKON
if (actualDiskon1 == expectedDiskon1) {
	KeywordUtil.markPassed("✅ Diskon sesuai | Actual = $actualDiskon1 | Expected = $expectedDiskon1")
} else {
	KeywordUtil.markFailed("❌ Diskon tidak sesuai | Actual = $actualDiskon1 | Expected = $expectedDiskon1")
}


// VALIDASI VOUCHER
if (actualVoucher == expectedVoucher) {
	KeywordUtil.markPassed("✅ Voucher sesuai | Actual = $actualVoucher | Expected = $expectedVoucher")
} else {
	KeywordUtil.markFailed("❌ Voucher tidak sesuai | Actual = $actualVoucher | Expected = $expectedVoucher")
}



Windows.takeScreenshot()

'Input Total Belanja'
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

Windows.delay(3)

Windows.takeScreenshot()

not_run: Windows.click(findWindowsObject('Transaksi Kasir/btn_close1'), FailureHandling.OPTIONAL)

Windows.delay(1)

Windows.verifyElementPresent(findWindowsObject('Transaksi Kasir/txt_pembayaranBerhasil'), 1000)

String pembayaranBerhasil1 = Windows.getText(findWindowsObject('Transaksi Kasir/txt_pembayaranBerhasil'))

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

    String actualStr = totalBelanja1.toString().trim().replace(',', '')

    // ===== Log raw vs clean =====
    Windows.comment('Expected raw (Payment Value): ' + expectedResult)

    Windows.comment('Expected raw (FHBKAS)       : ' + expectedResult_fhbkas)

    Windows.comment('Actual raw (total)          : ' + totalAmount1)

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
    
    Windows.click(findWindowsObject('Transaksi Kasir/btn_tutup'))

    Windows.delay(2)
}

// ======================================================
// PAYMENT 2
// ROW 3
// ======================================================
String plu2 = td.getValue('plu', 2)

String qty2 = td.getValue('qty', 2)

KeywordUtil.logInfo('INPUT PLU 3 = ' + plu2)

KeywordUtil.logInfo('INPUT QTY 3 = ' + qty2)

// ==============================================
// INPUT PLU
// ==============================================
Windows.click(findWindowsObject('Transaksi Kasir/txt_PLU'))

Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), plu2)

Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

Windows.delay(2)

// ==============================================
// INPUT QTY
// ==============================================
Windows.setText(findWindowsObject('Transaksi Kasir/txt_PLU'), qty2)

Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

Windows.delay(3)

Windows.takeScreenshot()

// ======================================================
// PAYMENT PAGE 2
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter (1)'))

'Melakukan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter (1)'))

Windows.delay(3)

Windows.takeScreenshot()

'Klik tombol voucher supplier'
Windows.click(findWindowsObject('Transaksi Kasir/btn_voucherSupplier'))

'Klik checkbox kode voucher'
Windows.click(findWindowsObject('Voucher Supplier/cb_kodeVoucher'))

'Pilih list voucher'
Windows.click(findWindowsObject('Voucher Supplier/list_voucher', [('voucherName') : voucherName]))

'Input Quantity Voucher'
String qtyVoucher1 = td.getValue('qtyVoucher', 2)

Windows.setText(findWindowsObject('Voucher Supplier/txt_qtyVoucher'), qtyVoucher1)

Windows.sendKeys(findWindowsObject('Voucher Supplier/txt_qtyVoucher'), Keys.chord(Keys.ENTER))

Windows.delay(3)

Windows.takeScreenshot()

namaVoucher = Windows.getText(findWindowsObject('Voucher Supplier/namaVoucher'))

//need action
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

'Validasi Voucher 2'
String actualDiskon2 = Windows.getText(findWindowsObject('Voucher Supplier/voucher'))
String actualVoucher2 = Windows.getText(findWindowsObject('Voucher Supplier/expectedDiskon'))

String expectedDiskon2 = td.getValue('diskon', 1)
String expectedVoucher2 = td.getValue('expected', 1)

// remove comma & trim
actualDiskon2 = actualDiskon2.replace(',', '').trim()
expectedDiskon2 = expectedDiskon2.replace(',', '').trim()

actualVoucher = actualVoucher2.replace(',', '').trim()
expectedVoucher2 = expectedVoucher2.replace(',', '').trim()

// VALIDASI DISKON
if (actualDiskon2 == expectedDiskon2) {
	KeywordUtil.markPassed("✅ Diskon sesuai | Actual = $actualDiskon2 | Expected = $expectedDiskon2")
} else {
	KeywordUtil.markFailed("❌ Diskon tidak sesuai | Actual = $actualDiskon2 | Expected = $expectedDiskon2")
}


// VALIDASI VOUCHER
if (actualVoucher2 == expectedVoucher2) {
	KeywordUtil.markPassed("✅ Voucher sesuai | Actual = $actualVoucher2 | Expected = $expectedVoucher2")
} else {
	KeywordUtil.markFailed("❌ Voucher tidak sesuai | Actual = $actualVoucher2 | Expected = $expectedVoucher2")
}


// ======================================================
// VALIDASI TOTAL PEMBAYARAN 2
// ======================================================
String totalPembayaranStr2 = Windows.getText(findWindowsObject('Transaksi Kasir/txt_totalYangHarusDibayar'))

long totalPembayaranAct2 = cleanNumber(totalPembayaranStr2)

long grandTotalExpected2 = td.getValue('expected', 2).toLong()

KeywordUtil.logInfo('EXPECTED PAYMENT 2 = ' + grandTotalExpected2)

KeywordUtil.logInfo('ACTUAL PAYMENT 2 = ' + totalPembayaranAct2)

// ======================================================
// VALIDATION
// ======================================================
if (grandTotalExpected2 == totalPembayaranAct2) {
    KeywordUtil.markPassed((((('✅ PAYMENT 2 SESUAI\n' + 'EXPECTED = ') + grandTotalExpected2) + '\n') + 'ACTUAL = ') + totalPembayaranAct2)
} else {
    KeywordUtil.markFailed((((('❌ PAYMENT 2 TIDAK SESUAI\n' + 'EXPECTED = ') + grandTotalExpected2) + '\n') + 'ACTUAL = ') + 
        totalPembayaranAct2)
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

Windows.delay(3)

Windows.takeScreenshot()

not_run: Windows.click(findWindowsObject('Transaksi Kasir/btn_close1'), FailureHandling.OPTIONAL)

Windows.delay(1)

Windows.verifyElementPresent(findWindowsObject('Transaksi Kasir/txt_pembayaranBerhasil'), 1000)

String pembayaranBerhasil2 = Windows.getText(findWindowsObject('Transaksi Kasir/txt_pembayaranBerhasil'))

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

    String actualStr = totalBelanja2.toString().trim().replace(',', '')

    // ===== Log raw vs clean =====
    Windows.comment('Expected raw (Payment Value): ' + expectedResult)

    Windows.comment('Expected raw (FHBKAS)       : ' + expectedResult_fhbkas)

    Windows.comment('Actual raw (total)          : ' + totalAmount2)

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
    
    Windows.click(findWindowsObject('Transaksi Kasir/btn_tutup'))

    Windows.delay(2)

    Windows.click(findWindowsObject('Transaksi Kasir/Menu Utama'))

    Windows.callTestCase(findTestCase('Additional Cases/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
}

def cleanNumber(String value) {
    return value.replaceAll('[^0-9-]', '').toLong()
}

