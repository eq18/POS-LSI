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


//pake totalPembayaranStr1 dst

totalPembayaranStr1 = 'hahha'

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

def cleanNumber(String value) {
    return value.replaceAll('[^0-9-]', '').toLong()
}

