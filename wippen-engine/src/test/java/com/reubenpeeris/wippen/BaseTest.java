package com.reubenpeeris.wippen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class BaseTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Before
	public void buildTestData() {
		TestData.buildTestData();
	}

	public void expect(Class<? extends Exception> clazz, String message) {
		thrown.expect(clazz);
		thrown.expectMessage(message);
	}
}
