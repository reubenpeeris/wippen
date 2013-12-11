package com.reubenpeeris.wippen.expression;

import static com.reubenpeeris.wippen.ObjectMother.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
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

    @Test
    public void testConstructorNonDivisibleNumbers() {
    	thrown.expect(IllegalArgumentException.class);
        new Divide(c2, c3);
    }

    @Test
    public void testBuilderNonDivisibleNumbers() {
        assertThat(Divide.builder().left(c2).right(c3).build(), is(nullValue()));
    }
}
