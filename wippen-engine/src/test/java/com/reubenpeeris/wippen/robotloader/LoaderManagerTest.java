package com.reubenpeeris.wippen.robotloader;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoaderManagerTest {
	private LoaderManager<ConstructorLoaderTest.AnInterface> loaderManager;
	
	@Before
	public void setUp() {
		loaderManager = new LoaderManager<>();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testRegisterLoaderNull() {
		loaderManager.registerLoader(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateInstanceNull() {
		loaderManager.createInstance(null);
	}
	
	@Test
	public void testCreateInstanceNoRegisteredLoaders() {
		String url = "unsupported://protocol";
		try {
			loaderManager.createInstance(url);
		} catch (LoaderException e) {
			assertEquals("No suitable loader found for url: '" + url + "'", e.getMessage());
		}
	}
	
	@Test
	public void testCreateInstanceValidURL() {
		loaderManager.registerLoader(ConstructorLoaderTest.loader);
		String url = "class:" + ConstructorLoaderTest.Valid.class.getName();
		ConstructorLoaderTest.AnInterface instance = loaderManager.createInstance(url);
		assertEquals(ConstructorLoaderTest.Valid.class, instance.getClass());
	}
	
	@Test
	public void testCreateInstanceInvalidURL() {
		String url = "unsupported://protocol";
		try {
			loaderManager.createInstance(url);
		} catch (LoaderException e) {
			assertEquals("No suitable loader found for url: '" + url + "'", e.getMessage());
		}
	}
}
