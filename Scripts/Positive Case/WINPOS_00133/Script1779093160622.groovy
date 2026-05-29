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
not_run: Windows.click(findWindowsObject('Transaksi Kasir/btn_close'), FailureHandling.OPTIONAL)

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// LOAD EXCEL
// ======================================================
TestData td = TestDataFactory.findTestData('Promotion Folder/Promo Percent')

// ======================================================
// GRAND TOTAL
// ======================================================
long grandTotalExpected = 0

// ======================================================
// LOOP EXCEL
// ======================================================
for (int row = 1; ; row++) {
    // ==================================================
    // STOP LOOP JIKA PLU KOSONG
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

    long hargaSatuanExp = cleanNumber(td.getValue('harga satuan', row))

    long diskonExp = cleanNumber(td.getValue('diskon', row))

    long expectedExp = cleanNumber(td.getValue('expected', row))

    // ==================================================
    // ACCUMULATE GRAND TOTAL
    // ==================================================
    grandTotalExpected = (grandTotalExpected + expectedExp)

    // ==================================================
    // RE-FIND OBJECT
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
    // FORMULA
    // (harga x qty) - hemat = jumlah
    // ==================================================
    long formulaResult = (hargaAct * qtyAct) - hematAct

    // ==================================================
    // HUMAN READABLE VALIDATION
    // ==================================================
    boolean isValid = true

    StringBuilder validationMsg = new StringBuilder()

    validationMsg.append('\n')

    validationMsg.append('====================================\n')

    validationMsg.append("ROW $row VALIDATION RESULT")

    validationMsg.append('====================================\n')

    validationMsg.append("PLU            : $plu")

    validationMsg.append("QTY            : $qtyAct")

    validationMsg.append("HARGA          : $hargaAct")

    validationMsg.append("HEMAT          : $hematAct")

    validationMsg.append("JUMLAH         : $jumlahAct")

    validationMsg.append('------------------------------------\n')

    // ==================================================
    // VALIDASI HARGA
    // ==================================================
    if (hargaAct == hargaSatuanExp) {
        validationMsg.append('✅ HARGA SESUAI\n')
    } else {
        isValid = false

        validationMsg.append('❌ HARGA TIDAK SESUAI\n')

        validationMsg.append("   Expected : $hargaSatuanExp")

        validationMsg.append("   Actual   : $hargaAct")
    }
    
    // ==================================================
    // VALIDASI DISKON
    // ==================================================
    if (hematAct == diskonExp) {
        validationMsg.append('✅ DISKON SESUAI\n')
    } else {
        isValid = false

        validationMsg.append('❌ DISKON TIDAK SESUAI\n')

        validationMsg.append("   Expected : $diskonExp")

        validationMsg.append("   Actual   : $hematAct")
    }
    
    // ==================================================
    // VALIDASI JUMLAH
    // ==================================================
    if (jumlahAct == expectedExp) {
        validationMsg.append('✅ JUMLAH SESUAI\n')
    } else {
        isValid = false

        validationMsg.append('❌ JUMLAH TIDAK SESUAI\n')

        validationMsg.append("   Expected : $expectedExp")

        validationMsg.append("   Actual   : $jumlahAct")
    }
    
    // ==================================================
    // VALIDASI FORMULA
    // ==================================================
    if (formulaResult == expectedExp) {
        validationMsg.append('✅ FORMULA VALID\n')
    } else {
        isValid = false

        validationMsg.append('❌ FORMULA TIDAK VALID\n')

        validationMsg.append("   Formula Result : $formulaResult")

        validationMsg.append("   Expected       : $expectedExp")
    }
    
    validationMsg.append('------------------------------------\n')

    // ==================================================
    // FINAL STATUS
    // ==================================================
    if (isValid) {
        validationMsg.append('🎉 STATUS : VALID\n')

        KeywordUtil.markPassed(validationMsg.toString())
    } else {
        validationMsg.append('🚨 STATUS : TIDAK VALID\n')

        KeywordUtil.markWarning(validationMsg.toString())
    }
    
    Windows.takeScreenshot()
}

// ======================================================
// PAYMENT PAGE
// ======================================================
Windows.click(findWindowsObject('Object Repository/ss/btn_enter'))

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// GET TOTAL PEMBAYARAN
// ======================================================
String totalPembayaranStr = Windows.getText(findWindowsObject('Object Repository/ss/txt_totalYangHarusDibayar'))

long totalPembayaranAct = cleanNumber(totalPembayaranStr)

// ======================================================
// PAYMENT VALIDATION
// ======================================================
StringBuilder paymentValidation = new StringBuilder()

paymentValidation.append('\n')

paymentValidation.append('====================================\n')

paymentValidation.append('PAYMENT VALIDATION RESULT\n')

paymentValidation.append('====================================\n')

paymentValidation.append("TOTAL EXPECTED EXCEL : $grandTotalExpected")

paymentValidation.append("TOTAL PEMBAYARAN POS : $totalPembayaranAct")

paymentValidation.append('------------------------------------\n')

if (grandTotalExpected == totalPembayaranAct) {
    paymentValidation.append('✅ TOTAL PEMBAYARAN SESUAI\n')

    paymentValidation.append('🎉 STATUS : VALID\n')

    KeywordUtil.markPassed(paymentValidation.toString())
} else {
    paymentValidation.append('❌ TOTAL PEMBAYARAN TIDAK SESUAI\n')

    paymentValidation.append("Expected : $grandTotalExpected")

    paymentValidation.append("Actual   : $totalPembayaranAct")

    paymentValidation.append('🚨 STATUS : TIDAK VALID\n')

    KeywordUtil.markWarning(paymentValidation.toString())
}

Windows.takeScreenshot()

// ======================================================
// INPUT PEMBAYARAN
// ======================================================
GlobalVariable.total = totalPembayaranStr

int nilai = Integer.parseInt(totalPembayaranStr.replace(',', ''))

int totalAmount = Math.ceil(nilai / 100.0) * 100

String totalBelanja = totalAmount.toString()

Windows.comment(totalBelanja)

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

Windows.click(findWindowsObject('Object Repository/ss/btn_close1'), FailureHandling.OPTIONAL)

Windows.delay(1)

Windows.takeScreenshot()

Windows.click(findWindowsObject('Object Repository/ss/btn_close2'))

String pembayaranBerhasil = Windows.getText(findWindowsObject('Object Repository/ss/txt_pembayaranBerhasil'))

if (pembayaranBerhasil.contains(pembayaranBerhasil)) {
    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/ss/btn_tutup'))

    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/Menu Utama'))

    Windows.callTestCase(findTestCase('ARCHIEVE/Additional Case/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
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

