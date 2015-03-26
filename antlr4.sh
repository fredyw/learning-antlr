#!/bin/bash

java -jar lib/antlr-4.4-complete.jar -o src/main/java/antlr -package antlr -no-listener -visitor $@
