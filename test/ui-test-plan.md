# Nova UI test plan

The runner reads the fenced JSON below. `program` is started once per test case. Each `inputs` item is sent as one line, and each `expectedOutput` item is one exact output line.

```json
{
  "program": "java -cp out\\production\\ip2 Nova",
  "tests": [
    {
      "name": "Application starts and displays the greeting",
      "aim": "Verify that the application starts successfully and prints the expected Nova greeting.",
      "inputs": [],
      "expectedOutput": ["____________________________________________________________", " _   _  _____   ____   ", "| \\u005c | || ____| / ___|  ", "|  \\u005c| ||  _|   \\u005c___ \\u005c  ", "| |\\u005c  || |___   ___) | ", "|_| \\u005c_||_____| |____/  ", "Hello! I'm Nova.", "What can I do for you?", "____________________________________________________________", "____________________________________________________________", " Got it. I've added this task:", "   [T][ ] ", " Now you have 1 tasks in the list.", "____________________________________________________________"]
    }
  ]
}
```
