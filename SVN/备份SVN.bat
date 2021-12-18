@echo off
:: 关闭回显
 
:: 说明：如有命令不明白，请使用帮助命令：命令/? 。如：for/?
 
:: 设置标题
title SVNBackup
 
rem Set Variable
 
:: SVN_HOME svn程序的安装路径，指定到bin目录上一级。(如果路径有空格，请用""。)
set SVN_HOME="C:\Program Files\VisualSVN Server"
:: SVN库的路径
set SVN_ROOT=E:\Repositories
:: 备份SVN父目录的路径
set SVN_BACKUP_ROOT=E:\SVN_backup
:: 获取当前系统日期和时间用来创建目录，格式：年月日_时分秒毫秒。如：20131129_16275274
set TIME_DIR=%date:~,4%%date:~5,2%%date:~8,2%%time:~,2%%time:~3,2%%time:~6,2%%time:~9,2%
:: SVN备份子目录的路径
set BACKUP_DIRECTORY=%SVN_BACKUP_ROOT%\%TIME_DIR%
:: 日志文件路径（将日志和备份文件放在一个目录，删除时一起删除了）
set LOG=%BACKUP_DIRECTORY%\backup.log
:: 指定备份文件删除时间（单位：天）。0 - 32768 范围内的任何数字。
set NUM=365
 
rem Start backup SVN
 
:: 判断SVN程序是否存在
::if not exist %SVN_HOME% goto error
 
goto start
 
:start
:: 查询日志大小（单位：字节），超过1Mb就新建一个 （日志放入备份目录后，此语句无效。仅备份！）
:: for /r %SVN_BACKUP_ROOT% %%I in (backup.log) do if %%~zI GEQ 1048576 ren %LOG% backup_%TIME%.log
:: 判断目录是否存在，存在则不备份
if exist %BACKUP_DIRECTORY% (
rem backup is exist
@echo %BACKUP_DIRECTORY% is exist. >>%LOG%
goto end
)
:: 新建目录
mkdir %BACKUP_DIRECTORY%
:: 建立日志
@echo [info]%date:~,10% %time:~,2%:%time:~3,2%:%time:~6,2%Create a backup directory: %BACKUP_DIRECTORY% >>%LOG%
:: 备份SVN版本库并输出日志到文件
for /d %%i in (%SVN_ROOT%\*) do (
@echo Backup %%~ni repository,backup filename: %%~ni.dmp >>%LOG%
:: 完整备份（增量备份在dump后面添加参数 --incremental）
svnadmin dump %SVN_ROOT%\%%~ni >%BACKUP_DIRECTORY%\%%~ni.dmp 2>>%LOG%
)
:: 输出备份命令退出码，用来判断是否备份成功。成功的退出码为0.
@echo [info]%date:~,10% %time:~,2%:%time:~3,2%:%time:~6,2%exit_code：%errorlevel%>>%LOG%
goto end

:end
:: 输出一行空行分割日志
rem End backup SVN
@echo success backup >>%LOG%
exit
:: 调试脚本用的命令
:: at 16:06 %~f0
:: PAUSE
:: exit