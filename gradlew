#!/usr/bin/env sh
GRADLE_WRAPPER_PROPERTIES=gradle/wrapper/gradle-wrapper.properties
if [ ! -f "$GRADLE_WRAPPER_PROPERTIES" ]; then
  echo "Wrapper properties not found. Please make sure gradle-wrapper.properties is present."
  exit 1
fi
exec java -jar gradle/wrapper/gradle-wrapper.jar "$@"
