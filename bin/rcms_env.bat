@echo off
REM Setlocal EnableDelayedExpansion

set RCMS_VERSION=2.1

REM if not defined INSTALL_DIR goto setupInstallDir
if not defined RCMS_CLASSPATH goto :setupInstallDir
goto :endEnv

:setupInstallDir
pushd %~dp0
cd /D %~dp0\..
set INSTALL_DIR=%CD%
if not defined RCMS_HOME set RCMS_HOME=%INSTALL_DIR%
cd /D %~dp0
goto :setupJava


:setupJava
if defined JAVA_HOME goto setupCassandra
pushd %~dp0
cd /D %~dp0\..\java
set JAVA_HOME=%~dp0\..\java
set PATH=%JAVA_HOME%\bin;%PATH%
cd /D %~dp0
goto :setupCassandra


:setupCassandra
if defined CASSANDRA_HOME goto :endEnv
pushd %~dp0
cd /D %~dp0\..\cassandra
set CASSANDRA_HOME=%CD%
set PATH=!PATH:%CASSANDRA_HOME%\bin;=!
set PATH=%CASSANDRA_HOME%\bin;%PATH%
cd /D %~dp0
goto :endEnv

:endEnv
set RCMS_CLASSPATH=%INSTALL_DIR%\lib\*;%INSTALL_DIR%\plugins\*
echo %RCMS_CLASSPATH%
goto :eof
