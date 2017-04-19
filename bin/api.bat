@echo off
REM Setlocal EnableDelayedExpansion
call %~dp0\rcms_env.bat

set MAIN_CLASS=com.qnet.statistic.api.App
set args=%*
call %~dp0\rcms_class.bat %MAIN_CLASS% %args%