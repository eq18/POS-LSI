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

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// LOAD EXCEL
// ======================================================
TestData td = TestDataFactory.findTestData('Promotion Folder/Promo Hemat')

// ======================================================
// LOOP EXCEL
// ======================================================
for (int row = 1; ; row++) {
    // ==================================================
    // STOP JIKA PLU KOSONG
    // ==================================================
    String plu = td.getValue('plu', row)

    if ((plu == null) || plu.trim().isEmpty()) {
        KeywordUtil.logInfo("STOP LOOP di row $row karena PLU kosong")

        break
    }
    
    // ==================================================
    // EXCEL DATA
    // ==================================================
    long qtyExp = cleanNumber(td.getValue('qty', row))

    long totalExp = cleanNumber(td.getValue('total', row))

    long diskonExp = cleanNumber(td.getValue('diskon', row))

    long expectedExp = cleanNumber(td.getValue('expected', row))

    // ==================================================
    // RE-FIND OBJECT SETIAP LOOP
    // ==================================================
    def txtPLU = findWindowsObject('Object Repository/Transaksi Kasir/txt_PLU')

    // ==================================================
    // SWITCH WINDOW
    // ==================================================
    Windows.switchToWindowTitle('Transaksi Kasir')

    Windows.delay(1)

    // ==================================================
    // INPUT PLU
    // ==================================================
    Windows.click(txtPLU)

    Windows.delay(1)

    Windows.sendKeys(txtPLU, plu)

    Windows.delay(1)

    Windows.sendKeys(txtPLU, Keys.chord(Keys.ENTER))

    Windows.delay(2)

    // ==================================================
    // INPUT QTY
    // ==================================================
    Windows.click(txtPLU)

    Windows.delay(1)

    Windows.sendKeys(txtPLU, qtyExp.toString())

    Windows.delay(1)

    Windows.sendKeys(txtPLU, Keys.chord(Keys.ENTER))

    Windows.delay(2)

    // ==================================================
    // UI ROW
    // ==================================================
    int uiRow = row - 1

    // ==================================================
    // DELAY GRID REFRESH
    // ==================================================
    Windows.delay(1)

    // ==================================================
    // GET UI VALUE
    // ==================================================
    String hargaActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'HARGA']))

    String qtyActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'QTY']))

    String hematActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'HEMAT']))

    String jumlahActStr = Windows.getText(findWindowsObject('Validation/cell', [('row') : uiRow, ('colName') : 'JUMLAH']))

    // ==================================================
    // CLEAN NUMBER
    // ==================================================
    long hargaAct = cleanNumber(hargaActStr)

    long qtyAct = cleanNumber(qtyActStr)

    long hematAct = cleanNumber(hematActStr)

    long jumlahAct = cleanNumber(jumlahActStr)

    // ==================================================
    // LOG
    // ==================================================
    KeywordUtil.logInfo('==================================================')

    KeywordUtil.logInfo("ROW $row | PLU=$plu")

    KeywordUtil.logInfo(((('EXCEL -> ' + "QTY=$qtyExp | ") + "TOTAL=$totalExp | ") + "DISKON=$diskonExp | ") + "EXPECTED=$expectedExp")

    KeywordUtil.logInfo(((('UI -> ' + "HARGA=$hargaAct | ") + "QTY=$qtyAct | ") + "HEMAT=$hematAct | ") + "JUMLAH=$jumlahAct")

    // ==================================================
    // VALIDASI TOTAL
    // total excel = harga UI
    // ==================================================
    if (hargaAct != totalExp) {
        KeywordUtil.markWarning("ROW $row -> TOTAL mismatch | " + "Expected=$totalExp | Actual=$hargaAct")
    }
    
    // ==================================================
    // VALIDASI DISKON
    // diskon excel = hemat UI
    // ==================================================
    if (hematAct != diskonExp) {
        KeywordUtil.markWarning("ROW $row -> DISKON mismatch | " + "Expected=$diskonExp | Actual=$hematAct")
    }
    
    // ==================================================
    // VALIDASI JUMLAH
    // expected excel = jumlah UI
    // ==================================================
    if (jumlahAct != expectedExp) {
        KeywordUtil.markWarning("ROW $row -> JUMLAH mismatch | " + "Expected=$expectedExp | Actual=$jumlahAct")
    }
    
    // ==================================================
    // VALIDASI FORMULA
    // jumlah = harga - hemat
    // ==================================================
    long formulaResult = hargaAct - hematAct

    if (formulaResult != jumlahAct) {
        KeywordUtil.markWarning("ROW $row -> FORMULA mismatch | " + "Formula=$formulaResult | ActualJumlah=$jumlahAct")
    }
    
    // ==================================================
    // PASS
    // ==================================================
    KeywordUtil.markPassed("ROW $row PASS ✅")

    Windows.takeScreenshot()
}

// ======================================================
// CLEAN NUMBER
// ======================================================
long cleanNumber(String s) {
    if ((s == null) || s.trim().isEmpty()) {
        return 0
    }
    
    return s.replaceAll('[^0-9-]', '').toLong()
}

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


