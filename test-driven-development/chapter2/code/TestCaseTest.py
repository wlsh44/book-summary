from TestCase import TestCase
from TestResult import TestResult
from WasRun import WasRun


class TestCaseTest(TestCase):
    def testTemplateMethod(self):
        self.test = WasRun("testMethod")
        self.test.run()
        assert ("setUp testMethod tearDown " == self.test.log)

    def testResult(self):
        self.test = WasRun("testMethod")
        result = self.test.run()
        assert("1 run, 0 failed" == result.summary())

    def testFailedResult(self):
        self.test = WasRun("testMethod")
        result = self.test.run()
        assert("1 run, 1 failed" == result.summary())

    def testFailedResultFormatting(self):
        result = TestResult()
        result.testStarted()
        result.testFailed()
        assert("1 run, 1 failed" == result.summary())

