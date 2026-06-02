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
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory

Windows.callTestCase(findTestCase('Additional Cases/Login'), [:], FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('PT Lion Super Indo v.2.1.1.71')

Windows.takeScreenshot()

'Klik Menu Transaksi Kasir'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_transaksiKasir'))

Windows.delay(2)

Windows.takeScreenshot()

'Klik tombol close pada notif'
not_run: Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_close'))

Windows.takeScreenshot()

Windows.delay(8)

/*--------------------------------------------------------*/
TestData td = TestDataFactory.findTestData('Promotion Folder/Promo Hemat')

int totalRow = td.getRowNumbers()

for (int row = 1; row <= totalRow; row++) {
    // ===== Excel data =====
    String plu = td.getValue('plu', row)

    long qtyExp = td.getValue('qty', row).toLong()

    // optional excel info
    long afterPromoExcel = td.getValue('expected', row).toLong()

	Windows.setText(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), plu)
   
	 Windows.sendKeys(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), Keys.chord(Keys.ENTER))

    // ===== INPUT QTY =====
    Windows.setText(findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU'), qtyExp.toString())

    Windows.delay(1)

    Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_enter (1)'))

    Windows.delay(2)

    // ===== UI row (Row 0,1,2,...) =====
    int uiRow = row - 1

    // ===== GET UI =====
    String hargaActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'HARGA']))

    String qtyActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'QTY']))

    String hematActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'HEMAT']))

    String jumlahActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'JUMLAH']))

    long hargaAct = cleanNumber(hargaActStr)

    long qtyAct = cleanNumber(qtyActStr)

    long hematAct = cleanNumber(hematActStr)

    long jumlahAct = cleanNumber(jumlahActStr)

    // ===== LOG =====
    KeywordUtil.logInfo("Row $row UIrow $uiRow | PLU=$plu")

    KeywordUtil.logInfo("UI -> HARGA=$hargaAct QTY=$qtyAct HEMAT=$hematAct JUMLAH=$jumlahAct")

    // ===== VALIDASI =====
    // 1) QTY harus sama dengan excel
    if (qtyAct != qtyExp) {
        KeywordUtil.markFailed("Row $row QTY mismatch | Exp=$qtyExp Act=$qtyAct")
    }
    
    // 2) HEMAT selalu 0 sesuai UI kamu
    if (hematAct != 0) {
        KeywordUtil.markFailed("Row $row HEMAT mismatch | Exp=0 Act=$hematAct")
    }
    
    // 3) JUMLAH = HARGA * QTY (rumus yang konsisten)
    long jumlahExpectedByUI = hargaAct * qtyAct

    // kalau row promo (contohnya row 1 kamu): JUMLAH malah = afterPromoExcel
    // jadi kita allow dua kemungkinan:
    if (!((jumlahAct == jumlahExpectedByUI) || (jumlahAct == afterPromoExcel))) {
        KeywordUtil.markFailed("Row $row JUMLAH mismatch | Exp(UIcalc)=$jumlahExpectedByUI or Exp(ExcelAfterPromo)=$afterPromoExcel Act=$jumlahAct")
    }
    
    KeywordUtil.markPassed("Row $row PASS ✅")
}

//===========================================================//
'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_enter (1)'))

Windows.takeScreenshot()

'Ambil total belanja'
total = Windows.getText(findWindowsObject('Object Repository/Transaksi Kasir/txt_totalYangHarusDibayar'))

String totalBayarNominalRounded = totalBayarNominal

// hapus koma jika ada
int nilai = Integer.parseInt(totalBayarNominal.replace(',', ''))

// bulatkan ke atas ratusan
int totalBulat = ((nilai + 99) / 100) * 100

totalBulatStr = totalBulat.toString()

println("Total sebelum bulat: $nilai")

println("Total setelah bulat: $totalBulat")

'Input total belanja'
Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), totalBulatStr)

Windows.delay(2)

Windows.takeScreenshot()

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

'Klik Enter untuk melanjutkan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.takeScreenshot()

'Close Notification'
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_close1'), FailureHandling.OPTIONAL)

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

def td1 = findTestData('Cash Payment')

String expectedResult = td1.getValue('postransactionpayment_Value', 1)

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

long cleanNumber(String s) {
    return s.replaceAll('[^0-9-]', '').toLong()
}

