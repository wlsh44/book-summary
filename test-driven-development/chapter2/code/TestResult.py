class TestResult:
    def __init__(self):
        self.failureCount = 0
        self.runCount = 0

    def testStarted(self):
        self.runCount += 1

    def summary(self):
        return f"{self.runCount} run, {self.failureCount} failed"

    def testFailed(self):
        self.failureCount += 1
