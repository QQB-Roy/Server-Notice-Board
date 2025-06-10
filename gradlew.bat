@echo off
set GRADLE_WRAPPER_PROPERTIES=gradle\wrapper\gradle-wrapper.properties
if not exist %GRADLE_WRAPPER_PROPERTIES% (
    echo Wrapper properties not found. Please make sure gradle-wrapper.properties is present.
    exit /b 1
)
java -jar gradle\wrapper\gradle-wrapper.jar %*
