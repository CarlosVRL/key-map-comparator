.PHONY: help test build rundev

help:
	@ echo COMMANDS
	@ echo test   - test the source code
	@ echo build  - build the application
	@ echo rundev - build and run locally

test:
	@ mvn clean test

build:
	@ mvn clean package

rundev: build
	@ java -jar `ls target/*.jar`
