package io.github.pollanz74.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.condition.JRE.JAVA_11;
import static org.junit.jupiter.api.condition.JRE.JAVA_8;

class CalculatorTests {

    @Test
    void sumShouldGetCorrectValue() {
        org.junit.jupiter.api.Assertions.assertEquals(5, Calculator.sum(2, 3));
    }

    @Test
    void sumShouldGetNotCorrectValue() {
        org.junit.jupiter.api.Assertions.assertNotEquals(-5, Calculator.sum(2, 3));
    }

    @Test
    void subShouldGetCorrectValue() {
        org.junit.jupiter.api.Assertions.assertEquals(-5, Calculator.sub(8, 13));
    }

    @Test
    void subShouldGetNotCorrectValue() {
        org.junit.jupiter.api.Assertions.assertNotEquals(5, Calculator.sub(8, 13));
    }

    @Test
    void percentageShouldGetCorrectValue() {
        org.junit.jupiter.api.Assertions.assertEquals(10, Calculator.percentage(1, 10));
    }

    @ParameterizedTest
    @CsvSource({"1/2,2/4", "2/3,4/6", "3/2,9/6","6,12/2","1,3/3","11/31,11/31"})

    void reduceFractionShouldGetCorrectValue(String expected, String args) {
        org.junit.jupiter.api.Assertions.assertEquals(expected, Calculator.reduceFraction(args));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 47, 128, 4, 42})
    void convertToBinaryShouldGetCorrectValue(int a) {
        org.junit.jupiter.api.Assertions.assertEquals(Integer.toBinaryString(a), Calculator.convertToBinary(a));
    }

    @ParameterizedTest
    @CsvSource({"2,2", "6,3", "24,4","120,5","1,0"})
    void factorialShouldGetCorrectValue(String expected, String args) {
        org.junit.jupiter.api.Assertions.assertEquals(Integer.parseInt(expected), Calculator.factorial(Integer.parseInt(args)));
    }

    @Test
    @Disabled // NB: da non usare se non temporaneamente!!
    void sumShouldGetCorrectValueWithIntegerMaxValue() {
        org.junit.jupiter.api.Assertions.assertEquals(2147483648L, Calculator.sum(Integer.MAX_VALUE, 1));
    }

    @Test
    void divShouldThrowsArithmeticExceptionWithDivisionByZero() {
        org.junit.jupiter.api.Assertions.assertThrows(ArithmeticException.class, () -> Calculator.div(BigDecimal.ONE, BigDecimal.ZERO));
    }

    // assertj
    @Test
    void divShouldThrowsArithmeticExceptionWithDivisionByZero_assertj() {
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> Calculator.div(BigDecimal.ONE, BigDecimal.ZERO))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("Division by zero");
    }

    // parametrized test
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8})
    void squareShouldGetCorrectValue(int a) {
        long square = (long) a * a;
        org.assertj.core.api.Assertions.assertThat(Calculator.square(a)).isEqualTo(square);
    }

    @ParameterizedTest
    @CsvSource({
            "apple,         1",
            "banana,        2",
            "lemon,         3",
            "strawberry,    4"
    })
    void csvInputShouldBeValid(String fruit, int rank) {
        org.assertj.core.api.Assertions.assertThat(fruit).isNotBlank();
        org.assertj.core.api.Assertions.assertThat(rank).isPositive();
    }

    @ParameterizedTest
    @CsvSource({
            "Cristiano,     36",
            "Andrea,        47",
            "Mario,         31",
            "Alessandro,    14"
    })
    void csvInputAsCustomBeanShouldBeValid_withArgumentAccessor(ArgumentsAccessor argumentsAccessor) {
        CustomBean customBean = new CustomBean(argumentsAccessor.getInteger(1), argumentsAccessor.getString(0));
        org.assertj.core.api.Assertions.assertThat(customBean)
                .isNotNull()
                .hasFieldOrProperty("age").isNotNull()
                .hasFieldOrProperty("name").isNotNull();
    }

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

    // repeated test
    @RepeatedTest(10)
    void repetedTestShouldNeverFail() {
        org.assertj.core.api.Assertions.assertThat(Calculator.square(2)).isEqualTo(4);
    }

    @Test
    @Timeout(value = 1, unit = TimeUnit.MINUTES)
    @EnabledOnJre(JAVA_11)
    void jre11TestShouldNotFail() {
        org.assertj.core.api.Assertions.assertThat(Calculator.square(2)).isEqualTo(4);
    }

    @Test
    @EnabledOnJre(JAVA_8)
    void jre8TestShouldNotRun() {
        org.assertj.core.api.Assertions.fail("test failed for jre8");
    }

    // lifecycle methods

    @BeforeAll
    static void initAll() {
        System.out.println("Before all test");
    }

    @BeforeEach
    void init() {
        System.out.println("Before each test");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("After all test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }

    private static class CustomBeanArgumentAggregator implements ArgumentsAggregator {

        @Override
        public CustomBean aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) {
            return new CustomBean(accessor.getInteger(1), accessor.getString(0));
        }

    }

}
