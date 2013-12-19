package com.reubenpeeris.wippen.engine;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.StandardErrorStreamLog;
import org.junit.contrib.java.lang.system.StandardOutputStreamLog;

import com.reubenpeeris.wippen.BaseTest;

public class BaseCommandLineTest extends BaseTest {
	@Rule
	public final StandardOutputStreamLog stdOut = new StandardOutputStreamLog();

	@Rule
	public final StandardErrorStreamLog stdErr = new StandardErrorStreamLog();

	protected String expectedStdOut;
	protected String expectedStdErr;

	@Before
	public void initialiseExpectedOutAndErr() {
		expectedStdOut = null;
		expectedStdErr = "";
	}

	@After
	public void assertStdOutIsAsExpected() {
		if (expectedStdOut != null) {
			assertThat(stdOut.getLog(), is(equalTo(expectedStdOut)));
		}
	}

	@After
	public void assertStdErrIsAsExpected() {
		if (expectedStdErr != null) {
			assertThat(stdErr.getLog(), is(equalTo(expectedStdErr)));
		}
	}
}
