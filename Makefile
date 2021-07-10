clean:
	./gradlew clean

build: clean
	./gradlew build

lint: clean
	./gradlew ktlintCheck

lint-fix: clean
	./gradlew ktlintFormat

docs: clean
	./gradlew dokkaHtml
