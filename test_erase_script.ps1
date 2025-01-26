# ---------------------------------
# Script: test_erase_script.ps1
# Author: Flower :3
# Date: 26/01/2025 12:38
# --------------------------------
#working

ipmo storage
$source = "C:\Data\PowerShellWorkShop"
$destination = "F:" #insert the volume leter here
Format-Volume -DriveLetter $destination -NewFileSystemLabel powershell -FileSystem exfat -Confirm:$false
robocopy $source $destination /S
[media.SystemSounds]::("Hand").play()