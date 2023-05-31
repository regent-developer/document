@echo off

set WINSCP_PATH="C:\Program Files (x86)\WinSCP\WinSCP.com"

set LOCAL_JAR_PATH="C:\path\to\local.jar"
set SERVER_ADDRESS=server_address
set SERVER_USERNAME=server_username
set SERVER_PASSWORD=server_password
set SERVER_DESTINATION=/path/to/remote/

echo Uploading JAR file to server...
%WINSCP_PATH% /command ^
    "open sftp://%SERVER_USERNAME%:%SERVER_PASSWORD%@%SERVER_ADDRESS%/" ^
    "put %LOCAL_JAR_PATH% %SERVER_DESTINATION%" ^
    "exit"

echo Starting JAR file on server...
%WINSCP_PATH% /command ^
    "open ssh://%SERVER_USERNAME%:%SERVER_PASSWORD%@%SERVER_ADDRESS%/" ^
    "call java -jar %SERVER_DESTINATION%\local.jar" ^
    "exit"
