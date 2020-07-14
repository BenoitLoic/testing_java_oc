package com.openclassrooms.testing.calcul.service;

import com.openclassrooms.testing.calcul.domain.Calculator;
import com.openclassrooms.testing.calcul.domain.model.CalculationModel;
import com.openclassrooms.testing.calcul.domain.model.CalculationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

    @Mock
    Calculator calculator;


    @Mock
    CalculatorService classUnderTest;

    SolutionFormatter solutionFormatter;

    @BeforeEach
    public void init() {
        classUnderTest = new CalculatorServiceImpl(calculator, solutionFormatter);
    }

    @Test
    public void calculate_ShouldUseCalculator_ForAddition() {
//		GIVEN
        when(calculator.add(1, 2)).thenReturn(3);
//		WHEN
        final int result = classUnderTest.calculate(
                new CalculationModel(CalculationType.ADDITION, 1, 2)).getSolution();
//		THEN
        verify(calculator).add(1, 2);
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void calculate_ShouldFormatSolution_ForAddition() {
//		GIVEN
        when(calculator.add(13000, 5000)).thenReturn(18000);
        when(solutionFormatter.format(18000)).thenReturn("18 000");
//		WHEN
        final String formattedResult = classUnderTest
                .calculate(new CalculationModel(CalculationType.ADDITION, 13000, 5000))
                .getFormattedSolution();
//		THEN
        assertThat(formattedResult).isEqualTo("18 000");


    }

    @Test
    public void calculate_ShouldUseCalculator_ForSubstraction() {

//		GIVEN
        when(calculator.sub(1, 2)).thenReturn(-1);
//		WHEN
        final int result = classUnderTest.calculate(
                new CalculationModel(CalculationType.SUBTRACTION, 1, 2)).getSolution();

//		THEN
        verify(calculator).sub(1, 2);
        assertThat(result).isEqualTo(-1);

    }

    @Test
    public void calculate_ShouldUseCalculator_ForMultiplication() {
//	GIVEN
        when(calculator.multiply(5, 9)).thenReturn(45);
//		WHEN
        final int result = classUnderTest.calculate(
                new CalculationModel(CalculationType.MULTIPLICATION, 5, 9)).getSolution();

//		THEN
        verify(calculator).multiply(5, 9);
        assertThat(result).isEqualTo(45);
    }

    @Test
    public void calculate_ShouldUseCalculator_ForDivision() {
//	GIVEN
        when(calculator.divide(50, 5)).thenReturn(10);
//		WHEN
        final int result = classUnderTest.calculate(
                new CalculationModel(CalculationType.DIVISION, 50, 5)).getSolution();

//		THEN
        verify(calculator).divide(50, 5);
        assertThat(result).isEqualTo(10);
    }

    @Test
    public void calculate_ShouldThrowIllegalArgumentException_ForDivisionBy0() {
//	GIVEN
        when(calculator.divide(50, 0)).thenThrow(new ArithmeticException());
//		WHEN
        assertThrows(IllegalArgumentException.class, () -> classUnderTest.calculate(new CalculationModel(CalculationType.DIVISION, 50, 0)));

//		THEN
        verify(calculator).divide(50, 0);

    }
}
