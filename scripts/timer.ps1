param (
    [string]$javaPID
)

$logPath = "$PSScriptRoot\WGUOASimulator_log.txt"

Add-Content -Path $logPath -Value "$(Get-Date) - Timer Started PID $javaPID"

# Start the timer (total 7200 seconds = 2 hours)
[console]::beep(500, 300)
Start-Sleep -Seconds 3600

# Halfway point
[console]::beep(500, 300)
Start-Sleep -Seconds 2700

# 15 minutes remaining
[console]::beep(500, 300)
[console]::beep(600, 300)
Start-Sleep -Seconds 900

# End of exam
[console]::beep(800, 200)
[console]::beep(800, 200)
[console]::beep(800, 200)

if (Get-Process -Id $javaPID -ErrorAction SilentlyContinue) {
    Stop-Process -Id $javaPID -Force
    Add-Content -Path $logPath -Value "$(Get-Date) - Terminated PID $javaPID"
} else {
    Add-Content -Path $logPath -Value "$(Get-Date) - PID $javaPID Already Exited."
}
