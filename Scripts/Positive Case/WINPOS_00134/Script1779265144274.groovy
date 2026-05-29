import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint

// ======================================================
// FUNCTION CLEAN NUMBER
// ======================================================
Windows.callTestCase(findTestCase('Additional Cases/Login'), [:], FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Main Menu')

Windows.takeScreenshot()

// ======================================================
// OPEN TRANSAKSI KASIR
// ======================================================
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_transaksiKasir'))

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// CLOSE NOTIFICATION
// ======================================================
not_run: Windows.click(findWindowsObject('Transaksi Kasir/btn_close'), FailureHandling.STOP_ON_FAILURE)

Windows.delay(5)

Windows.takeScreenshot()

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Transaksi Kasir')

// ======================================================
// LOAD EXCEL
// ======================================================
TestData td = TestDataFactory.findTestData('Promotion Folder/Promo PWP')

// ======================================================
// INPUT 3 PLU
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

    Windows.setText(findWindowsObject('Transaksi Kasir/txt_PLU'), plu)

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

    Windows.delay(2)

    // ==============================================
    // INPUT QTY
    // ==============================================
    Windows.setText(findWindowsObject('Transaksi Kasir/txt_PLU'), qty)

    Windows.delay(1)

    Windows.sendKeys(findWindowsObject('Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

    Windows.delay(3)
}

// ======================================================
// VALIDASI ITEM PWP MUNCUL
// ======================================================
//boolean isItemPwpDisplayed = Windows.verifyElementPresent(findWindowsObject('Transaksi Kasir/txt_statusProduk'), 5)
//if (isItemPwpDisplayed) {
//    KeywordUtil.markPassed('✅ TEXT ITEM PWP MUNCUL')
//} else {
//    KeywordUtil.markFailed('❌ TEXT ITEM PWP TIDAK MUNCUL')
//}
Windows.takeScreenshot()

// ======================================================
// CLICK BUTTON CEK DISKON
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_cekDiskon'))

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// VALIDASI DISKON PLU KE 3
// ======================================================
String actualDiskon = Windows.getText(findWindowsObject('Transaksi Kasir/hargaHemat'))

KeywordUtil.logInfo('ACTUAL DISKON = ' + actualDiskon)

long actualDiskonClean = Math.abs(cleanNumber(actualDiskon))

// expected dari excel row ke 3
long expectedDiskon = td.getValue('diskon', 3).toLong()

KeywordUtil.logInfo('EXPECTED DISKON = ' + expectedDiskon)

// ======================================================
// VALIDATION
// ======================================================
if (actualDiskonClean == expectedDiskon) {
    KeywordUtil.markPassed((((('✅ DISKON PLU KE 3 SESUAI\n' + 'EXPECTED = ') + expectedDiskon) + '\n') + 'ACTUAL   = ') + 
        actualDiskonClean)
} else {
    KeywordUtil.markFailed((((('❌ DISKON PLU KE 3 TIDAK SESUAI\n' + 'EXPECTED = ') + expectedDiskon) + '\n') + 'ACTUAL   = ') + 
        actualDiskonClean)
}

Windows.takeScreenshot()

// ======================================================
// CLICK TUTUP
// ======================================================
Windows.click(findWindowsObject('Transaksi Kasir/btn_tutupCekDiskon'))

Windows.delay(5)

Windows.takeScreenshot()

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

total = Windows.getText(findWindowsObject('Object Repository/ss/txt_totalYangHarusDibayar'))

GlobalVariable.total = total

// hapus koma
int nilai = Integer.parseInt(total.replace(',', ''))

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
not_run: Windows.click(findWindowsObject('Object Repository/ss/btn_close2'))

permbayaranBerhasil = Windows.getText(findWindowsObject('Object Repository/ss/txt_pembayaranBerhasil'))

if (permbayaranBerhasil.contains(permbayaranBerhasil)) {
    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/ss/btn_tutup'))

    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/Menu Utama'))

    Windows.callTestCase(findTestCase('Additional Cases/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
}

def cleanNumber(String value) {
    return value.replaceAll('[^0-9-]', '').toLong()
}

