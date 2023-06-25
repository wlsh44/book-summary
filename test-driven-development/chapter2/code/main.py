from TestCaseTest import TestCaseTest
from TestResult import TestResult
from TestSuite import TestSuite

suite = TestSuite()
suite.add(TestCaseTest("testTemplateMethod"))
suite.add(TestCaseTest("testResult"))
suite.add(TestCaseTest("testFailedResult"))
suite.add(TestCaseTest("testFailedResultFormatting"))
result = TestResult()
suite.run(result)
print(result.summary())
