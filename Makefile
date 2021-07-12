clean:
	./gradlew clean

test: clean
	./gradlew check

build: clean
	./gradlew build

lint: clean
	./gradlew ktlintCheck

lint-fix: clean
	./gradlew ktlintFormat

docs: clean
	./gradlew dokkaHtml
