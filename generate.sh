#! /bin/bash

mkdir build
printf "cd ../\njavac -d build src/cardgame/* src/blackjack/* src/main/*\ncd build/" > build/build.sh
chmod u+x build/build.sh
printf "java main.Main\n" > build/run.sh
chmod u+x build/run.sh
