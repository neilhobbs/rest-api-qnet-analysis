@echo on
REM Setlocal EnableDelayedExpansion

call %~dp0\env.bat

REM set LOG_OPTS=-Dlog4j.configuration="file:/%INSTALL_DIR%/conf/log4j.properties"

set LOG_OPTS=-Dlogback.configurationFile="file:/%INSTALL_DIR%/conf/rcmslogback.xml"
set command="%JAVA_HOME%\bin\java.exe" -Xmx4G %LOG_OPTS% -classpath %RCMS_CLASSPATH% %*
cd /d %~dp0\..
echo %command%
call %command%
cd /d %~dp0