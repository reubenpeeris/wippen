package com.reubenpeeris.wippen.robotloader;

import org.junit.Test;

import com.reubenpeeris.wippen.robotloader.ConstructorLoaderTest.AnInterface;

import static org.junit.Assert.*;

public class SpringLoaderTest {
	private final SpringLoader<AnInterface> loader = new SpringLoader<>(AnInterface.class, "bean-name");
		
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullClass() {
		new SpringLoader<>(null, "valid-value");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorNullBeanName() {
		new SpringLoader<>(AnInterface.class, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorEmptyBeanName() {
		new SpringLoader<>(AnInterface.class, "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAcceptsNullURL() {
		loader.acceptsURL(null);
	}
	
	@Test
	public void testAcceptsInvalidURL() {
		assertFalse(loader.acceptsURL("invalidURL"));
	}
	
	@Test
	public void testAcceptsValidURL() {
		assertTrue(loader.acceptsURL("spring:some-paths"));
	}
	
	@Test
	public void testCreateInstanceInvalidPath() {
		String url = "invalid-path";
		try {
			loader.createInstance(url);
			fail();
		} catch (LoaderException e) {
			assertEquals("Unsupported url: '" + url + "'", e.getMessage());
		}
	}
	
	@Test
	public void testCreateInstanceMissingFile() {
		String path = "SpringLoaderTest-missing.xml";
		try {
			loader.createInstance("spring:" + path);
			fail();
		} catch (LoaderException e) {
			assertEquals("Failed to find file: '" + path + "'", e.getMessage());
		}
	}
	
	@Test
	public void testCreateInstanceInvalidFile() {
		String path = "SpringLoaderTest-invalid.xml";
		try {
			loader.createInstance("spring:" + path);
			fail();
		} catch (LoaderException e) {
			assertEquals("Failed to process file: '" + path + "'", e.getMessage());
		}
	}
	
	@Test
	public void testCreateInstanceMissingBeanDefinition() {
		String path = "SpringLoaderTest-missing-bean.xml";
		try {
			loader.createInstance("spring:" + path);
			fail();
		} catch (LoaderException e) {
			assertEquals("Failed to load bean 'bean-name'", e.getMessage());
		}
	}
	
	@Test
	public void testCreateInstanceFromClasspath() {
		AnInterface instance = loader.createInstance("spring:SpringLoaderTest-classpath.xml");
		
		assertEquals(ConstructorLoaderTest.Valid.class, instance.getClass());
	}
	
	@Test
	public void testCreateInstanceFromFileSystem() {
		AnInterface instance = loader.createInstance("spring:test-resource/SpringLoaderTest-filesystem.xml");
		
		assertEquals(ConstructorLoaderTest.Valid.class, instance.getClass());
	}
}
