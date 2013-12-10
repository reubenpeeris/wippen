package com.reubenpeeris.wippen.expression;

import org.junit.Test;

import static com.reubenpeeris.wippen.Cards.*;

public class DivideTest extends OperatorTest {
    public DivideTest() {
        super(Divide.builder(), Divide.class);
    }

    @Override
    protected int getResult4And2() {
        return 2;
    }

    @Override
    protected String getSymbol() {
        return "/";
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructorNonDivisibleNumbers() {
        new Divide(c2, c3);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBuilderNonDivisibleNumbers() {
        Divide.builder().build(c2, c3);
    }
}
