# Dictionary Project

This is an Automation Test project using the Dictionary Service to search for English words.

The main features of this project has the following:

* The project has a dictionary service that is used to search for words. 
* A mock object to simulate and to get the dictionary list.
* Some Tests that exercise the dictionary service.
* A simple String list based on the Dictionary file (EnglishWords in this case) to mock the dictionary service.

## Frameworks
This project using the following languages and frameworks:

* [Java 11](https://openjdk.java.net/projects/jdk/11/) as the programming framework language
* [JUnit](https://junit.org/junit5/) as the Unit Test framework to support the test creation
* [Mockito](https://site.mockito.org) as the mocking framework for unit tests in Java
* [AssertJ](https://joel-costigliola.github.io/assertj/) as the fluent assertion library
* [SLF4J](https://www.slf4j.org) as the logging management strategy

### Automation Test Strategy

The main Test Strategy has the following steps:

1. A mock object has been created for the Dictionary list.
2. String list with the dictionary content has been created.
3. Validate if given word exists in the dictionary (EnglishWords in this case) to mock the isEnglishWord function.
4. A static String constants has been created to test the words from the dictionary list.
5. Test has the following scenarios:
* Test the dictionary service with the mocked dictionary list.
* Test the dictionary service with the non-English word: AMIGO.
* Test the dictionary service finding possible words of a given string
* Test the dictionary service with the real English word: WORKING. Besides, print all possible words of the given string.
1. After the test, assertions will be validated to each test.
2. Annotation @Test will be used to group the tests.
3. Each tests will finish automatically and it is independent of one and the other.

## Tester details
* Name: `Douglas Urrea Ocampo`