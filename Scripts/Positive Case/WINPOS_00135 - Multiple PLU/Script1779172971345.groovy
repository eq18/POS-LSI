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

promo = 'Buy 1 Get 1 - Multiple PLU'

Windows.comment('Promo Package = ' + promo)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('PT Lion Super Indo v.2.1.1.71')

Windows.takeScreenshot()

// ======================================================
// OPEN TRANSAKSI KASIR
// ======================================================
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_transaksiKasir'))

Windows.delay(2)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Transaksi Kasir')

Windows.takeScreenshot()

Windows.sendKeys(findWindowsObject('Transaksi Kasir/btn_close'), Keys.chord(Keys.ENTER))

Windows.delay(3)

Windows.takeScreenshot()

// ======================================================
// LOAD EXCEL
// ======================================================
TestData td = TestDataFactory.findTestData('Promotion Folder/Package Beli 1 Gratis 1 - Multiple')

// ======================================================
// GRAND TOTAL & TOTAL ITEM
// ======================================================
long grandTotalExpected = 0

long totalItemExpected = 0

// ======================================================
// TOTAL ROW EXCEL
// ======================================================
int totalRow = td.getRowNumbers()

KeywordUtil.logInfo("TOTAL ROW EXCEL = $totalRow")

// ======================================================
// LOOP EXCEL
// ======================================================
for (int row = 1; row <= totalRow; row++) {
    String plu = td.getValue('plu', row)

    // skip kalau PLU kosong
    if ((plu == null) || plu.trim().isEmpty()) {
        KeywordUtil.logInfo("SKIP row $row karena PLU kosong")

        continue
    }
    
    // ==================================================
    // EXCEL DATA
    // ==================================================
    long qtyExp = cleanNumber(td.getValue('qty', row))

    long hargaSatuanExp = cleanNumber(td.getValue('harga satuan', row))

    long diskonExp = cleanNumber(td.getValue('diskon', row))

    long expectedExp = cleanNumber(td.getValue('expected', row))

    // ==================================================
    // ACCUMULATE
    // ==================================================
    grandTotalExpected = (grandTotalExpected + expectedExp)

    totalItemExpected = (totalItemExpected + qtyExp)

    // ==================================================
    // SWITCH WINDOW
    // ==================================================
    Windows.switchToWindowTitle('Transaksi Kasir')

    Windows.delay(1)

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
    // ==================================================
    long formulaResult = (hargaAct * qtyAct) - hematAct

    // ==================================================
    // VALIDATION MESSAGE
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
Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_enter (1)'))

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// GET TOTAL BELANJA
// ======================================================
String totalBelanjaStr = Windows.getText(findWindowsObject('Transaksi Kasir/txt_totalYangHarusDibayar'))

long totalBelanjaAct = cleanNumber(totalBelanjaStr)

// ======================================================
// GET TOTAL ITEM
// ======================================================
String totalItemStr = Windows.getText(findWindowsObject('Object Repository/ss/txt_totalItem'))

long totalItemAct = cleanNumber(totalItemStr)

// ======================================================
// PAYMENT VALIDATION
// ======================================================
boolean paymentValid = true

StringBuilder paymentValidation = new StringBuilder()

paymentValidation.append('\n')

paymentValidation.append('====================================\n')

paymentValidation.append('PAYMENT VALIDATION RESULT\n')

paymentValidation.append('====================================\n')

paymentValidation.append("TOTAL EXPECTED EXCEL : $grandTotalExpecte")

paymentValidation.append("TOTAL BELANJA POS    : $totalBelanjaAct")

paymentValidation.append("TOTAL ITEM EXCEL     : $totalItemExpected")

paymentValidation.append("TOTAL ITEM POS       : $totalItemAct")

paymentValidation.append('------------------------------------\n')

// ======================================================
// VALIDASI TOTAL BELANJA
// ======================================================
if (grandTotalExpected == totalBelanjaAct) {
    paymentValidation.append('✅ TOTAL BELANJA SESUAI\n')
} else {
    paymentValid = false

    paymentValidation.append('❌ TOTAL BELANJA TIDAK SESUAI\n')

    paymentValidation.append("   Expected : $grandTotalExpected")

    paymentValidation.append("   Actual   : $totalBelanjaAct")
}

// ======================================================
// VALIDASI TOTAL ITEM
// ======================================================
if (totalItemExpected == totalItemAct) {
    paymentValidation.append('✅ TOTAL ITEM SESUAI\n')
} else {
    paymentValid = false

    paymentValidation.append('❌ TOTAL ITEM TIDAK SESUAI\n')

    paymentValidation.append("   Expected : $totalItemExpected")

    paymentValidation.append("   Actual   : $totalItemAct")
}

paymentValidation.append('------------------------------------\n')

// ======================================================
// FINAL PAYMENT STATUS
// ======================================================
if (paymentValid) {
    paymentValidation.append('🎉 PAYMENT VALIDATION SUCCESS\n')

    KeywordUtil.markPassed(paymentValidation.toString())
} else {
    paymentValidation.append('🚨 PAYMENT VALIDATION FAILED\n')

    KeywordUtil.markWarning(paymentValidation.toString())
}

Windows.takeScreenshot()

// ======================================================
// INPUT PEMBAYARAN
// ======================================================
GlobalVariable.total = totalBelanjaStr

int nilai = Integer.parseInt(totalBelanjaStr.replace(',', ''))

int totalAmount = Math.ceil(nilai / 100.0) * 100

String totalBayar = totalAmount.toString()

Windows.comment(totalBayar)

Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), totalBayar)

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
// PEMBAYARAN BERHASIL
// ======================================================
String pembayaranBerhasil = Windows.getText(findWindowsObject('Object Repository/Transaksi Kasir/txt_pembayaranBerhasil'))

if (pembayaranBerhasil.contains(pembayaranBerhasil)) {
    Windows.delay(2)

    Windows.takeScreenshot()

    Windows.click(findWindowsObject('Object Repository/Transaksi Kasir/btn_tutup'))

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

