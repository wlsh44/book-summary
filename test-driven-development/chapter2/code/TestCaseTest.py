from TestCase import TestCase
from WasRun import WasRun


class TestCaseTest(TestCase):
    def testTemplateMethod(self):
        self.test = WasRun("testMethod")
        self.test.run()
        assert ("setUp testMethod tearDown " == self.test.log)
