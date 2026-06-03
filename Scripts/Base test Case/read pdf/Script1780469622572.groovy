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

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper

Thread.sleep(5000)

'Path PDF'
String folderPath = "D:\\POS_version75\\POS_version75\\E-Receipt"

'Ambil PDF Terbaru'
File folder = new File(folderPath)

File latestPdf = folder.listFiles()
	.findAll { it.name.toLowerCase().endsWith(".pdf") }
	.max { it.lastModified() }

'Validasi PDF jika ada atau tidak'
if(latestPdf == null) {

	println("PDF tidak ditemukan")

	assert false
}

'Print PDF Terbaru'
println("PDF terbaru ditemukan")
println(latestPdf.name)


'Baca Isi PDF'
PDDocument document = PDDocument.load(latestPdf)

PDFTextStripper pdfStripper = new PDFTextStripper()

String pdfText = pdfStripper.getText(document)

document.close()

'Tampilkan isi PDF'
println("====================")
println("ISI PDF")
println("====================")

println(pdfText)

'Verifikasi PDF'
if(pdfText.contains("TOTAL")) {

	println("VALID -> Text TOTAL ditemukan")

} else {

	println("FAILED -> Text TOTAL tidak ditemukan")

	assert false
}

/*
'Contoh Verikasi Nominal'

if(pdfText.contains("10000")) {

	println("VALID -> Nominal ditemukan")

} else {

	println("FAILED -> Nominal tidak ditemukan")
}
*/