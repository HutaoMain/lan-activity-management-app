param (
    [string]$ComputerName
)

try {
    $result = Invoke-Command -ComputerName $ComputerName -ScriptBlock {
        try {
            logoff
            Write-Output "Logout successful"
        } catch {
            Write-Error "Logout failed: $_"
        }
    }
    Write-Output $result
} catch {
    Write-Error "Remote command failed: $_"
}