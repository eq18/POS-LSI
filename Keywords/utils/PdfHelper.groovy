package utils

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import com.kms.katalon.core.annotation.Keyword

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pdfbox.Loader

class PdfHelper {

	@Keyword
	static File getLatestPdf(String folderPath) {

		File folder = new File(folderPath)

		File latestPdf = folder.listFiles()
			.findAll { it.name.toLowerCase().endsWith(".pdf") }
			.max { it.lastModified() }

		return latestPdf
	}

	@Keyword
	static String readPdf(File pdfFile) {

		PDDocument document = Loader.loadPDF(pdfFile)

		PDFTextStripper pdfStripper = new PDFTextStripper()

		String pdfText = pdfStripper.getText(document)

		document.close()

		return pdfText
	}

	@Keyword
	static String readLatestPdf(String folderPath) {

		File latestPdf = getLatestPdf(folderPath)

		if(latestPdf == null) {

			throw new Exception("PDF tidak ditemukan di folder: " + folderPath)
		}

		return readPdf(latestPdf)
	}
}