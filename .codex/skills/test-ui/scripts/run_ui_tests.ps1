param([string]$PlanPath = "test/ui-test-plan.md")
$ErrorActionPreference = "Stop"
$markdown = Get-Content -Raw (Resolve-Path $PlanPath)
$match = [regex]::Match($markdown, '(?s)```json\s*(.*?)\s*```')
if (-not $match.Success) { throw "No fenced JSON test plan found in $PlanPath" }
$plan = $match.Groups[1].Value | ConvertFrom-Json
function Normalize([string]$value) { return ($value -replace "`r`n", "`n" -replace "`r", "`n").TrimEnd("`n") }
foreach ($test in $plan.tests) {
    Write-Host "=== $($test.name) ==="; Write-Host "Aim: $($test.aim)"; Write-Host "[INPUT]"
    $inputText = (($test.inputs | ForEach-Object { [string]$_ }) -join "`n") + "`n"
    if ($test.inputs.Count -gt 0) { $test.inputs | ForEach-Object { Write-Host $_ } }
    $psi = [Diagnostics.ProcessStartInfo]::new(); $psi.FileName = "powershell.exe"
    $psi.Arguments = "-NoProfile -Command `"$($plan.program)`""; $psi.WorkingDirectory = (Get-Location).Path
    $psi.RedirectStandardInput = $true; $psi.RedirectStandardOutput = $true; $psi.RedirectStandardError = $true; $psi.UseShellExecute = $false
    $process = [Diagnostics.Process]::Start($psi); $process.StandardInput.Write($inputText); $process.StandardInput.Close()
    $stdout = $process.StandardOutput.ReadToEnd(); $stderr = $process.StandardError.ReadToEnd(); $process.WaitForExit()
    $actual = Normalize ($stdout + $stderr); $expected = Normalize (($test.expectedOutput | ForEach-Object { ([string]$_) -replace '\\u005c', '\' }) -join "`n")
    Write-Host "[OUTPUT]"; if ($actual) { Write-Host $actual }
    if ($actual -ne $expected) { Write-Host "[FAIL] $($test.name)"; Write-Host "--- Actual ---"; Write-Host $actual; Write-Host "--- Expected ---"; Write-Host $expected; exit 1 }
    Write-Host "[PASS] $($test.name)"
}
Write-Host "All UI tests passed."
