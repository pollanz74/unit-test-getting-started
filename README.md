# Unit Test - Getting Started

1. [Unit Testing](#unit-testing)
1. [JUnit](#junit)
1. [Hello JUnit](#hello-junit)
1. [Hello Mock](#hello-mock)
1. [JUnit e Database](#junit-e-database)
1. [JUnit e Spring](#junit-e-spring)
1. [TestContainers](#testcontainers)
1. [Test Coverage e Reportistica](#test-coverage-e-reportistica)
1. [Software Quality e Tools](#software-quality-and-tools)
1. [JMeter](#jmeter)
1. [Zed Attack Proxy](#zed-attack-proxy)
1. [Build ed esecuzione progetto](#build-ed-esecuzione-progetto)

## Unit Testing
(_01/10/2021_)
- Unit test: porzione di codice per verificare il comportamento di un singolo componente (classe) e di una singola funzionalità (metodo). Le dipendenze esterne dovrebbero essere simulate (con stub/mock)
- Integration test: porzione di codice per verificare la corretta interazione di due o più componenti (esempio repository vs database, oppure service vs cache)
- I test devono essere auto-consistenti, ripetibili, non legati ad uno specifico ambiente (database, sistemi esterni) ed eseguibili automaticamente anche off-line in un ambiente completamente isolato, senza vincoli temporali (es. date)
- TDD, Test Driven Development, metodologia per lo sviluppo del software che prevede la scrittura dei test prima dell'implementazione delle varie funzionalità. Lo sviluppo è guidato (driven) dai test 

**Costi e benefici**
- (-) Effort non trascurabile
- (-) Anche gli unit-test possono essere soggetti a bug
- (+) Incremento della qualità del codice
- (+) Protezione da regressioni
- (+) Codice da una prospettiva differente più "funzionale"
- (+) Codice difficilmente testabile con unit-test probabilmente è mal progettato/strutturato

## JUnit
(_08/10/2021_)
- Framework di riferimento per la scrittura dei test in Java
- Integrazione di JUnit con Maven (e simili)
- Riferimenti:
  + [https://junit.org/junit5/docs/current/user-guide/](https://junit.org/junit5/docs/current/user-guide/)
- Alternative a JUnit un po' meno conosciute:
  + TestNG: [https://testng.org/doc/](https://testng.org/doc/)
  + Spock: [https://spockframework.org/](https://spockframework.org/)

## Hello JUnit
(_08/10/2021_)
- Setup di un progetto maven
    + [https://github.com/junit-team/junit5-samples/tree/r5.8.1/junit5-jupiter-starter-maven](https://github.com/junit-team/junit5-samples/tree/r5.8.1/junit5-jupiter-starter-maven)
- Layout di progetto
  + [https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html)
- Convenzioni (naming metodi e classi)
- Assertions
  + [https://assertj.github.io/doc/](https://assertj.github.io/doc/)
- Test instance lifecycle
- Esecuzione di un test specifico (tutti i metodi di una classe):
```shell
cd hello-junit
mvn -Dtest=CalculatorTests test
```
- Esecuzione di un test specifico (un solo metodo):
```shell
hello-junit
mvn -Dtest=CalculatorTests#sumShouldGetCorrectValue test
```
- ArgumentAccessor\ArgumentAggregator
```java
    @ParameterizedTest
    @CsvSource({
            "Cristiano,     36",
            "Andrea,        47",
            "Mario,         31",
            "Alessandro,    14"
    })
    void csvInputAsCustomBeanShouldBeValid_withArgumentAggregator(@AggregateWith(CustomBeanArgumentAggregator.class) CustomBean customBean) {
        org.assertj.core.api.Assertions.assertThat(customBean)
        .isNotNull()
        .hasFieldOrProperty("age").isNotNull()
        .hasFieldOrProperty("name").isNotNull();
        }

    private static class CustomBeanArgumentAggregator implements ArgumentsAggregator {
    
      @Override
      public CustomBean aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) {
        return new CustomBean(accessor.getInteger(1), accessor.getString(0));
      }
    
    }
```

## Hello Mock
- Perchè si usano i mock: a supporto del test per testare solo la classe oggetto del test e non le sue dipendenze che devono essere simulate
- Mockito
  + [https://site.mockito.org/](https://site.mockito.org/)
- Powermock
  + [https://github.com/powermock/powermock](https://github.com/powermock/powermock)
- Easymock
  + [https://easymock.org/](https://easymock.org/)
- Jmockit
  + [https://jmockit.github.io/](https://jmockit.github.io/)
- Wiremock
  + [http://wiremock.org/](http://wiremock.org/)
- Java-Faker
  + [https://github.com/DiUS/java-faker](https://github.com/DiUS/java-faker)
- Jfixture
  + [https://github.com/FlexTradeUKLtd/jfixture](https://github.com/FlexTradeUKLtd/jfixture)

## JUnit e Database
TODO

## JUnit e Spring
> "The Spring team advocates test-driven development (TDD)"
> "Testing is an integral part of enterprise software development"
- Cosa mette ci offre Spring Framework
  + [https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html](https://docs.spring.io/spring-framework/docs/current/reference/html/testing.html)
- Automatismi messi a disposizione da Spring Boot
  + Annotations
  + Mocking dei bean
  + [https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.testing)
  + [https://github.com/spring-projects/spring-petclinic/](https://github.com/spring-projects/spring-petclinic/)
- DB Unit
- Rest Assured

## TestContainers
TODO

## Test Coverage e Reportistica
- Reportistica
  + [https://maven.apache.org/surefire/maven-surefire-report-plugin/index.html](https://maven.apache.org/surefire/maven-surefire-report-plugin/index.html) `mvn surefire-report:report`
- JaCoCo

## Software Quality e Tools
- Plugin per analisi statica del codice
- Plugin per l'ide (_SonarLint_)

## JMeter
TODO

## Zed Attack Proxy
TODO

## Build ed esecuzione progetto
Per poter fare build ed esecuzione del codice è necessario utilizzare JDK >= 11 e maven 3.

### Struttura progetto
Progetto maven multi modulo. Le versioni degli artifact sono presenti nel "root" `pom.xml`.

### Build ed esecuzione test

```shell
mvn clean test
```
