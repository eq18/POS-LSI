import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import internal.GlobalVariable as GlobalVariable

'Login'
Windows.callTestCase(findTestCase('Additional Cases/Login'), [:], FailureHandling.STOP_ON_FAILURE)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Main Menu')

Windows.takeScreenshot()

'Klik tombol Transaksi Kasir'
Windows.click(findWindowsObject('Transaksi Kasir/btn_transaksiKasir'))

Windows.delay(3)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'('Transaksi Kasir')

Windows.takeScreenshot()

'Load Excel'
TestData td = TestDataFactory.findTestData('Promotion Folder/Promo PWP - Dengan Maksimal Quantity')

'Input PLU'
for (int row = 1; row <= 4; row++) {
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

'Klik tombil cek diskon'
Windows.click(findWindowsObject('Transaksi Kasir/btn_cekDiskon'))

Windows.delay(5)

Windows.takeScreenshot()

'Validasi Diskon'
int diskonIndex = 1

for (int x = 1; x <= 4; x++) {

	long expectedDiskon =
			td.getValue(
					'diskon',
					x
			).toLong()

	// ==========================================
	// SKIP JIKA DISKON = 0
	// ==========================================
	if (expectedDiskon == 0) {

		KeywordUtil.logInfo(
				"ROW " + x + " TIDAK ADA DISKON"
		)

		continue
	}

	String actualDiskonStr

	if (diskonIndex == 1) {

		actualDiskonStr =
				Windows.getText(
						findWindowsObject(
								'Transaksi Kasir/lbl_diskon_1'
						)
				)

	} else if (diskonIndex == 2) {

		actualDiskonStr =
				Windows.getText(
						findWindowsObject(
								'Transaksi Kasir/lbl_diskon_2'
						)
				)

	} else if (diskonIndex == 3) {

		actualDiskonStr =
				Windows.getText(
						findWindowsObject(
								'Transaksi Kasir/lbl_diskon_3'
						)
				)

	} else {

		actualDiskonStr =
				Windows.getText(
						findWindowsObject(
								'Transaksi Kasir/lbl_diskon_4'
						)
				)
	}

	long actualDiskon =
			Math.abs(
					cleanNumber(
							actualDiskonStr
					)
			)

	KeywordUtil.logInfo(
			'EXPECTED DISKON = ' +
			expectedDiskon
	)

	KeywordUtil.logInfo(
			'ACTUAL DISKON = ' +
			actualDiskon
	)

	if (expectedDiskon == actualDiskon) {

		KeywordUtil.markPassed(
				'✅ DISKON SESUAI'
		)

	} else {

		KeywordUtil.markFailed(
				'❌ DISKON TIDAK SESUAI'
		)
	}

	diskonIndex++
}

Windows.takeScreenshot()

'Klik tombol tutup'
Windows.click(findWindowsObject('Transaksi Kasir/btn_tutupCekDiskon'))

Windows.delay(2)

'Melakukan pembayaran'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter (1)'))

Windows.delay(3)

Windows.takeScreenshot()

Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

'Ambil total belanja'
String total = Windows.getText(findWindowsObject('Transaksi Kasir/txt_totalYangHarusDibayar'))

// hapus koma
int nilai = Integer.parseInt(total.replace(',', ''))

// bulatkan ke atas ke ratusan
int totalAmount = Math.ceil(nilai / 100.0) * 100

String totalBelanja = totalAmount.toString()

KeywordUtil.logInfo('TOTAL PAYMENT = ' + totalBelanja)

'Input total tunai'
Windows.setText(findWindowsObject('Transaksi Kasir/txt_tunai'), totalBelanja)

Windows.delay(2)

Windows.takeScreenshot()

'Final payment'
Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(2)

Windows.click(findWindowsObject('Transaksi Kasir/btn_enter'))

Windows.delay(3)

Windows.takeScreenshot()

Windows.delay(1)

Windows.verifyElementPresent(findWindowsObject('Transaksi Kasir/txt_pembayaranBerhasil'), 1000)

String pembayaranBerhasil = Windows.getText(findWindowsObject('Transaksi Kasir/txt_pembayaranBerhasil'))

if (pembayaranBerhasil.length() > 0) {
    KeywordUtil.markPassed('✅ PAYMENT BERHASIL')

    Windows.click(findWindowsObject('Transaksi Kasir/btn_tutup'))

    Windows.delay(2)

    Windows.click(findWindowsObject('Transaksi Kasir/Menu Utama'))

    Windows.callTestCase(findTestCase('Additional Cases/Logout'), [:], FailureHandling.STOP_ON_FAILURE)
}

'Validasi Di Database'
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

