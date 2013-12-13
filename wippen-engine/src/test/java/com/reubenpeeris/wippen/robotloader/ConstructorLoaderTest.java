package com.reubenpeeris.wippen.robotloader;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConstructorLoaderTest {
	static final ConstructorLoader<AnInterface> loader = new ConstructorLoader<>(AnInterface.class);

	@Test(expected = IllegalArgumentException.class)
	public void testAcceptsNullURL() {
		loader.acceptsURL(null);
	}

	@Test
	public void testAcceptURLInvalid() {
		assertFalse(loader.acceptsURL("invalid://url"));
	}

	@Test
	public void testAcceptURLValidProtocol() {
		assertTrue(loader.acceptsURL("class:some.random.class"));
	}

	@Test
	public void testCreateInstanceValid() {
		assertEquals(Valid.class, loader.createInstance("class:" + Valid.class.getName()).getClass());
	}

	@Test
	public void testCreateInstanceMissing() {
		String className = "FakeClassName";
		try {
			loader.createInstance("class:" + className);
			fail();
		} catch (LoaderException e) {
			assertEquals("Class not found: '" + className + "'", e.getMessage());
		}
	}

	@Test
	public void testCreateInstancePrivate() {
		String className = Private.class.getName();
		try {
			loader.createInstance("class:" + className);
			fail();
		} catch (LoaderException e) {
			assertEquals("Class is not accessible: '" + className + "'", e.getMessage());
		}
	}

	@Test
	public void testCreateInstanceWrongInterface() {
		String className = WrongInterface.class.getName();
		try {
			loader.createInstance("class:" + className);
			fail();
		} catch (LoaderException e) {
			assertEquals("Class '" + className + "' does not implement interface '" + AnInterface.class.getName() + "'", e.getMessage());
		}
	}

	@Test
	public void testCreateInstanceNoNpArgConstructor() {
		String className = NoNpArgConstructor.class.getName();
		try {
			loader.createInstance("class:" + className);
			fail();
		} catch (LoaderException e) {
			assertEquals("No public no-argument constructor found on class '" + className + "'", e.getMessage());
		}
	}

	@Test
	public void testCreateInstanceInvalidURL() {
		String url = "inavlid url";
		try {
			loader.createInstance(url);
			fail();
		} catch (LoaderException e) {
			assertEquals("Unsupported url: '" + url + "'", e.getMessage());
		}
	}

	static interface AnInterface {
	}

	static class Private implements AnInterface {
	}

	public static class WrongInterface {
	}

	public static class Valid implements AnInterface {
	}

	public static class NoNpArgConstructor implements AnInterface {
		public NoNpArgConstructor(String string) {
		}
	}
}
