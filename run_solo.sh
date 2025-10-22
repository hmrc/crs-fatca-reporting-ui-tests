#!/bin/bash -e
DEFAULT_BROWSER=chrome
DEFAULT_ENVIRONMENT=local
BROWSER=$1
ENVIRONMENT=$2

if [ -z "$BROWSER" ]; then
    echo "BROWSER value not set, defaulting to $DEFAULT_BROWSER..."
    echo ""
fi

if [ -z "$ENVIRONMENT" ]; then
    echo "ENVIRONMENT value not set, defaulting to $DEFAULT_ENVIRONMENT..."
    echo ""
fi

sbt clean -Dbrowser="${BROWSER_TYPE:=$DEFAULT_BROWSER}" -Denvironment="${ENV:=local}" -Dbrowser.option.headless=false -Daccessibility.assessment=false -Dsecurity.assessment=true "testOnly uk.gov.hmrc.test.ui.specs.* -- -n SoloTests"
