APP_NAME        ?= api-demo
PKG_FILE        ?= ./target/api.demo-0.0.1-SNAPSHOT.jar

all:
	@echo "available targets:"
	@echo "  * package     - build and package jar file"
	@echo "  * run         - run the application"

package:
	mvn clean package

run:
	java -jar $(PKG_FILE)