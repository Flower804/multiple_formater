#!/bin/bash
#example for fat32 formating

password = [insert sudo password here]

$echo [password] | sudo -S umount /dev/[device_path]
#for format in NTFS replace .vfat for .ntfs  and for exFAT replace .vfat for .exfat
$echo [password] | sudo -S mkfs.vfat /dev/[device_path]