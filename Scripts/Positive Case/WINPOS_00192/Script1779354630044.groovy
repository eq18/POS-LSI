import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import org.openqa.selenium.Keys as Keys
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.util.KeywordUtil
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import org.openqa.selenium.Keys

// ======================================================
// FUNCTION CLEAN NUMBER
// ======================================================
def cleanNumber(String value) {

	return value.replaceAll('[^0-9-]', '').toLong()
}

// ======================================================
// LOGIN
// ======================================================
Windows.callTestCase(
		findTestCase(
				'Additional Cases/Login'
		),
		[:],
		FailureHandling.STOP_ON_FAILURE
)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'(
		'Main Menu'
)

Windows.takeScreenshot()

// ======================================================
// OPEN TRANSAKSI KASIR
// ======================================================
Windows.click(
		findWindowsObject(
				'Transaksi Kasir/btn_transaksiKasir'
		)
)

Windows.delay(3)

CustomKeywords.'custom.WindowUtils.switchToWindowTitle'(
		'Transaksi Kasir'
)

Windows.takeScreenshot()

// ======================================================
// LOAD EXCEL
// ======================================================
TestData td =
		TestDataFactory.findTestData(
				'Promo Multilevel - Promo Price and Promo Price'
		)

// ======================================================
// INPUT PLU ROW 1 & 2
// ======================================================
for (int row = 1; row <= 2; row++) {

	String plu =
			td.getValue(
					'plu',
					row
			)

	String qty =
			td.getValue(
					'qty',
					row
			)

	KeywordUtil.logInfo(
			"INPUT PLU = " + plu
	)

	KeywordUtil.logInfo(
			"INPUT QTY = " + qty
	)

	// ==============================================
	// INPUT PLU
	// ==============================================
	Windows.click(
			findWindowsObject(
					'Transaksi Kasir/txt_PLU'
			)
	)

	Windows.sendKeys(
			findWindowsObject(
					'Transaksi Kasir/txt_PLU'
			),
			plu
	)

	Windows.sendKeys(
			findWindowsObject(
					'Transaksi Kasir/txt_PLU'
			),
			Keys.chord(Keys.ENTER)
	)

	Windows.delay(2)

	// ==============================================
	// INPUT QTY
	// ==============================================
	Windows.setText(
			findWindowsObject(
					'Transaksi Kasir/txt_PLU'
			),
			qty
	)

	Windows.sendKeys(
			findWindowsObject(
					'Transaksi Kasir/txt_PLU'
			),
			Keys.chord(Keys.ENTER)
	)

	Windows.delay(3)
}

Windows.takeScreenshot()

// ======================================================
// CLICK CEK DISKON
// ======================================================
Windows.click(
		findWindowsObject(
				'bole/btn_cekDiskon'
		)
)

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// VALIDASI DISKON ROW 1 & 2
// ======================================================
for (int row = 1; row <= 2; row++) {

	long expectedDiskon =
			td.getValue(
					'diskon',
					row
			).toLong()

	String actualDiskonStr

	// ==============================================
	// GET UI DISKON
	// ==============================================
	if (row == 1) {

		actualDiskonStr =
				Windows.getText(
						findWindowsObject(
								'bole/lbl_diskon_1'
						)
				)

	} else {

		actualDiskonStr =
				Windows.getText(
						findWindowsObject(
								'bole/lbl_diskon_2'
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
			"ROW = " + row
	)

	KeywordUtil.logInfo(
			"EXPECTED DISKON = " + expectedDiskon
	)

	KeywordUtil.logInfo(
			"ACTUAL DISKON = " + actualDiskon
	)

	// ==============================================
	// VALIDATION
	// ==============================================
	if (actualDiskon == expectedDiskon) {

		KeywordUtil.markPassed(
				"✅ DISKON SESUAI\n" +
				"ROW = " + row + "\n" +
				"EXPECTED = " + expectedDiskon + "\n" +
				"ACTUAL = " + actualDiskon
		)

	} else {

		KeywordUtil.markFailed(
				"❌ DISKON TIDAK SESUAI\n" +
				"ROW = " + row + "\n" +
				"EXPECTED = " + expectedDiskon + "\n" +
				"ACTUAL = " + actualDiskon
		)
	}
}

Windows.takeScreenshot()

// ======================================================
// CLICK TUTUP
// ======================================================
Windows.click(
		findWindowsObject(
				'bole/btn_tutupCekDiskon'
		)
)

Windows.delay(2)

// ======================================================
// PAYMENT 1
// ======================================================
Windows.click(
		findWindowsObject(
				'ss/btn_enter'
		)
)

Windows.delay(3)

Windows.takeScreenshot()

// ======================================================
// CLICK ENTER PAYMENT
// ======================================================
Windows.click(
		findWindowsObject(
				'Transaksi Kasir/btn_enter'
		)
)

Windows.delay(2)

// ======================================================
// GET TOTAL
// ======================================================
String total =
		Windows.getText(
				findWindowsObject(
						'ss/txt_totalYangHarusDibayar'
				)
		)

// hapus koma
int nilai =
		Integer.parseInt(
				total.replace(',', '')
		)

// bulatkan ke atas ke ratusan
int totalAmount =
		Math.ceil(
				nilai / 100.0
		) * 100

String totalBelanja =
		totalAmount.toString()

KeywordUtil.logInfo(
		"TOTAL PAYMENT 1 = " +
		totalBelanja
)

// ======================================================
// INPUT TUNAI
// ======================================================
Windows.setText(
		findWindowsObject(
				'Transaksi Kasir/txt_tunai'
		),
		totalBelanja
)

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// FINAL PAYMENT
// ======================================================
Windows.click(
		findWindowsObject(
				'Transaksi Kasir/btn_enter'
		)
)

Windows.delay(2)

Windows.click(
		findWindowsObject(
				'Transaksi Kasir/btn_enter'
		)
)

Windows.delay(3)

Windows.takeScreenshot()

// ======================================================
// CLOSE PAYMENT
// ======================================================
Windows.click(
		findWindowsObject(
				'ss/btn_close1'
		),
		FailureHandling.OPTIONAL
)

Windows.delay(1)

String pembayaranBerhasil =
		Windows.getText(
				findWindowsObject(
						'ss/txt_pembayaranBerhasil'
				)
		)

if (pembayaranBerhasil.length() > 0) {

	KeywordUtil.markPassed(
			"✅ PAYMENT 1 BERHASIL"
	)

	Windows.click(
			findWindowsObject(
					'ss/btn_tutup'
			)
	)

	Windows.delay(2)
}

// ======================================================
// INPUT PLU ROW 3
// ======================================================
String plu3 =
		td.getValue(
				'plu',
				3
		)

String qty3 =
		td.getValue(
				'qty',
				3
		)

KeywordUtil.logInfo(
		"INPUT PLU 3 = " + plu3
)

KeywordUtil.logInfo(
		"INPUT QTY 3 = " + qty3
)

// ==============================================
// INPUT PLU
// ==============================================
Windows.click(
		findWindowsObject(
				'Transaksi Kasir/txt_PLU'
		)
)

Windows.sendKeys(
		findWindowsObject(
				'Transaksi Kasir/txt_PLU'
		),
		plu3
)

Windows.sendKeys(
		findWindowsObject(
				'Transaksi Kasir/txt_PLU'
		),
		Keys.chord(Keys.ENTER)
)

Windows.delay(2)

// ==============================================
// INPUT QTY
// ==============================================
Windows.setText(
		findWindowsObject(
				'Transaksi Kasir/txt_PLU'
		),
		qty3
)

Windows.sendKeys(
		findWindowsObject(
				'Transaksi Kasir/txt_PLU'
		),
		Keys.chord(Keys.ENTER)
)

Windows.delay(3)

Windows.takeScreenshot()

// ======================================================
// CLICK CEK DISKON
// ======================================================
Windows.click(
		findWindowsObject(
				'bole/btn_cekDiskon'
		)
)

Windows.delay(5)

Windows.takeScreenshot()

// ======================================================
// VALIDASI DISKON ROW 3
// ======================================================
long expectedDiskon3 =
		td.getValue(
				'diskon',
				3
		).toLong()

String actualDiskonStr3 =
		Windows.getText(
				findWindowsObject(
						'bole/lbl_diskon_1'
				)
		)

long actualDiskon3 =
		Math.abs(
				cleanNumber(
						actualDiskonStr3
				)
		)

KeywordUtil.logInfo(
		"EXPECTED DISKON 3 = " +
		expectedDiskon3
)

KeywordUtil.logInfo(
		"ACTUAL DISKON 3 = " +
		actualDiskon3
)

// ======================================================
// VALIDATION
// ======================================================
if (actualDiskon3 == expectedDiskon3) {

	KeywordUtil.markPassed(
			"✅ DISKON ROW 3 SESUAI\n" +
			"EXPECTED = " + expectedDiskon3 + "\n" +
			"ACTUAL = " + actualDiskon3
	)

} else {

	KeywordUtil.markFailed(
			"❌ DISKON ROW 3 TIDAK SESUAI\n" +
			"EXPECTED = " + expectedDiskon3 + "\n" +
			"ACTUAL = " + actualDiskon3
	)
}

Windows.takeScreenshot()

// ======================================================
// CLICK TUTUP
// ======================================================
Windows.click(
		findWindowsObject(
				'bole/btn_tutupCekDiskon'
		)
)

Windows.delay(2)

// ======================================================
// PAYMENT 2
// ======================================================
Windows.click(
		findWindowsObject(
				'Transaksi Kasir/btn_enter'
		)
)

Windows.delay(2)

// ======================================================
// GET TOTAL PAYMENT 2
// ======================================================
String total1 =
		Windows.getText(
				findWindowsObject(
						'ss/txt_totalYangHarusDibayar'
				)
		)

int nilai1 =
		Integer.parseInt(
				total1.replace(',', '')
		)

int totalAmount1 =
		Math.ceil(
				nilai1 / 100.0
		) * 100

String totalBelanja1 =
		totalAmount1.toString()

KeywordUtil.logInfo(
		"TOTAL PAYMENT 2 = " +
		totalBelanja1
)

// ======================================================
// INPUT TUNAI
// ======================================================
Windows.setText(
		findWindowsObject(
				'Transaksi Kasir/txt_tunai'
		),
		totalBelanja1
)

Windows.delay(2)

Windows.takeScreenshot()

// ======================================================
// FINAL PAYMENT
// ======================================================
Windows.click(
		findWindowsObject(
				'Transaksi Kasir/btn_enter'
		)
)

Windows.delay(2)

Windows.click(
		findWindowsObject(
				'Transaksi Kasir/btn_enter'
		)
)

Windows.delay(3)

Windows.takeScreenshot()

// ======================================================
// CLOSE PAYMENT
// ======================================================
Windows.click(
		findWindowsObject(
				'ss/btn_close1'
		),
		FailureHandling.OPTIONAL
)

Windows.delay(1)

String pembayaranBerhasil2 =
		Windows.getText(
				findWindowsObject(
						'ss/txt_pembayaranBerhasil'
				)
		)

if (pembayaranBerhasil2.length() > 0) {

	KeywordUtil.markPassed(
			"✅ PAYMENT 2 BERHASIL"
	)

	Windows.click(
			findWindowsObject(
					'ss/btn_tutup'
			)
	)

	Windows.delay(2)

	Windows.click(
		findWindowsObject(
			'Transaksi Kasir/Menu Utama'
		)
	)

	Windows.callTestCase(
		findTestCase(
			'Additional Cases/Logout'
		),
		[:],
		FailureHandling.STOP_ON_FAILURE
	)
}