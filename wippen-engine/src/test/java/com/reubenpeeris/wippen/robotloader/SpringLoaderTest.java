package com.reubenpeeris.wippen.robotloader;

import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;
import com.reubenpeeris.wippen.robotloader.ConstructorLoaderTest.AnInterface;

public class SpringLoaderTest extends BaseTest {
	private final SpringLoader<AnInterface> loader = new SpringLoader<>(AnInterface.class, "bean-name");

	@Test
	public void constructor_throws_for_null_clazz() {
		expect(NullPointerException.class, "clazz");
		new SpringLoader<>(null, "valid-value");
	}

	@Test
	public void constructor_throws_for_null_beanName() {
		expect(NullPointerException.class, "beanName");
		new SpringLoader<>(AnInterface.class, null);
	}

	@Test
	public void constructor_throws_for_empty_beanName() {
		expect(IllegalArgumentException.class, "beanName must not be empty");
		new SpringLoader<>(AnInterface.class, "");
	}

	@Test
	public void acceptsUrl_throws_for_null_url() {
		expect(NullPointerException.class, "url");
		loader.acceptsUrl(null);
	}

	@Test
	public void acceptsUrl_returns_false_for_invalid_url() {
		assertFalse(loader.acceptsUrl("invalidURL"));
	}

	@Test
	public void acceptsUrl_returns_true_for_correct_protocol() {
		assertTrue(loader.acceptsUrl("spring:some-paths"));
	}

	@Test
	public void createInstance_throws_for_invalid_protocol() {
		String url = "invalid-path";
		expect(WippenLoaderException.class, "Unsupported url: '" + url + "'");
		loader.createInstance(url);
	}

	@Test
	public void createInstance_throws_for_missing_file() {
		String path = "SpringLoaderTest-missing.xml";
		expect(WippenLoaderException.class, "Failed to find file: '" + path + "'");
		loader.createInstance("spring:" + path);
	}

	@Test
	public void createInstance_throws_for_invalid_file() {
		String path = "SpringLoaderTest-invalid.xml";
		expect(WippenLoaderException.class, "Failed to process file: '" + path + "'");
		loader.createInstance("spring:" + path);
	}

	@Test
	public void createInstance_throws_if_the_bean_definition_is_missing() {
		String path = "SpringLoaderTest-missing-bean.xml";
		expect(WippenLoaderException.class, "Failed to load bean 'bean-name'");
		loader.createInstance("spring:" + path);
	}

	@Test
	public void createInstance_works_with_a_classpath_reference() {
		AnInterface instance = loader.createInstance("spring:SpringLoaderTest-classpath.xml");
		assertEquals(ConstructorLoaderTest.Valid.class, instance.getClass());
	}

	@Test
	public void createInstance_works_with_a_file_system_reference() {
		AnInterface instance = loader.createInstance("spring:test-resource/SpringLoaderTest-filesystem.xml");
		assertEquals(ConstructorLoaderTest.Valid.class, instance.getClass());
	}
}
