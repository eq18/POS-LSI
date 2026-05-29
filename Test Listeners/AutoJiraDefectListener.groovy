import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext

//import com.kms.katalon.core.annotation.AfterTestCase
//import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.configuration.RunConfiguration
//import internal.GlobalVariable

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

class AutoJiraDefectListener {
	
		// ===== Jira config =====
		static final String JIRA_URL = 'https://chrishanditya.atlassian.net'
		static final String JIRA_EMAIL = 'ricarvi.rakaviko@mii.co.id'
		static final String JIRA_API_TOKEN = 'ATATT3xFfGF0MRKiSmADItWSjlKnjX5YW8AkYO7t-TFKi36kjmty3HgCVX5NIvF8TOWJyfHvkWI1a8XIwD9chSial48O3VffihGRn1jqbJJKAZZia9xXCt0uAYhEGDODT_8c2xpLS1W8xi7GlH0FwL_PAhY96CgANMTrs4AaHhR8FhmZFWqq678=E6D015BD'
		static final String PROJECT_KEY = 'DB'
		static final String ISSUE_TYPE = 'Bug'
		static final String ASSIGNEE_ACCOUNT_ID = '712020:d019aedc-841a-4f18-933c-924aef2470e4'
	
		@AfterTestCase
		def afterTestCase(TestCaseContext context) {
	
			if (context.getTestCaseStatus() != 'FAILED') {
				return
			}
	
			println "Test failed → creating Jira issue..."
	
			String issueKey = createJiraIssue(context)
	
			if (issueKey != null) {
				attachEvidence(issueKey)
			}
		}
	
		// ==========================
		// Create Jira Issue
		// ==========================
		static String createJiraIssue(TestCaseContext context) {
	
			def payload = [
				fields: [
					project: [ key: PROJECT_KEY ],
					summary: "Automation Failure: ${context.getTestCaseId()}",
					description: """
					
Test Case: ${context.getTestCaseId()}
Status: FAILED

Message:
${context.getMessage()}
                """.trim(),
					issuetype: [ name: ISSUE_TYPE ],
					assignee: [
						accountId: ASSIGNEE_ACCOUNT_ID
					]
				]
			]
	
			def connection = new URL("${JIRA_URL}/rest/api/2/issue").openConnection()
			connection.setRequestMethod("POST")
			connection.setDoOutput(true)
			connection.setRequestProperty("Content-Type", "application/json")
			connection.setRequestProperty("Authorization", getAuthHeader())
	
			connection.outputStream.withWriter("UTF-8") {
				it << JsonOutput.toJson(payload)
			}
	
			if (connection.responseCode == 201) {
				def response = new JsonSlurper().parse(connection.inputStream)
				println "Jira issue created: ${response.key}"
				return response.key
			} else {
				println "Failed to create Jira issue: ${connection.responseCode}"
				println connection.errorStream?.text
				return null
			}
		}
	
		// ==========================
		// Attach screenshots/logs
		// ==========================
		static void attachEvidence(String issueKey) {
	
			File reportDir = new File(RunConfiguration.getReportFolder())
			File[] files = reportDir.listFiles()
	
			files.each { file ->
				if (file.name.endsWith(".png") || file.name.endsWith(".log")) {
					uploadAttachment(issueKey, file)
				}
			}
		}
	
		static void uploadAttachment(String issueKey, File file) {
	
			def boundary = "----KATALON_BOUNDARY"
	
			def connection = new URL("${JIRA_URL}/rest/api/3/issue/${issueKey}/attachments").openConnection()
			connection.setRequestMethod("POST")
			connection.setDoOutput(true)
			connection.setRequestProperty("X-Atlassian-Token", "no-check")
			connection.setRequestProperty("Authorization", getAuthHeader())
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=${boundary}")
	
			def out = connection.outputStream
			out.write(("--${boundary}\r\n").bytes)
			out.write("Content-Disposition: form-data; name=\"file\"; filename=\"${file.name}\"\r\n".bytes)
			out.write("Content-Type: application/octet-stream\r\n\r\n".bytes)
			out.write(file.bytes)
			out.write("\r\n--${boundary}--\r\n".bytes)
			out.flush()
			out.close()
	
			if (connection.responseCode == 200) {
				println "Attached: ${file.name}"
			} else {
				println "Failed to attach ${file.name}: ${connection.responseCode}"
			}
		}
	
		static String getAuthHeader() {
			String auth = "${JIRA_EMAIL}:${JIRA_API_TOKEN}"
			return "Basic " + auth.bytes.encodeBase64().toString()
		}
	}