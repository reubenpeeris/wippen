package com.reubenpeeris.wippen.robotloader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class ConstructorLoaderTest extends BaseTest {
	static final ConstructorLoader<AnInterface> loader = new ConstructorLoader<>(AnInterface.class);

	@Test
	public void acceptsUrl_throws_for_null_input() {
		expect(NullPointerException.class, "url");
		loader.acceptsUrl(null);
	}

	@Test
	public void acceptUrl_returns_false_for_invalid_format() {
		assertThat(loader.acceptsUrl("invalid://url"), is(false));
	}

	@Test
	public void acceptUrl_returns_true_for_valid_protocol() {
		assertThat(loader.acceptsUrl("class:some.random.class"), is(true));
	}

	@Test
	public void createInstance_returns_instance_of_expected_class() {
		assertThat(loader.createInstance("class:" + Valid.class.getName()).getClass() == Valid.class, is(true));
	}

	@Test
	public void createInstance_throws_for_missing_class() {
		String className = "FakeClassName";
		expect(WippenLoaderException.class, "Class not found: '" + className + "'");
		loader.createInstance("class:" + className);
	}

	@Test
	public void createInstance_throws_for_private_class() {
		String className = Private.class.getName();
		expect(WippenLoaderException.class, "Class is not accessible: '" + className + "'");
		loader.createInstance("class:" + className);
	}

	@Test
	public void createInstance_throws_for_wrong_interface() {
		String className = WrongInterface.class.getName();
		expect(WippenLoaderException.class, "Class '" + className + "' does not implement interface '" + AnInterface.class.getName() + "'");
		loader.createInstance("class:" + className);
	}

	@Test
	public void createInstance_throws_if_class_does_not_have_a_public_no_arg_constructor() {
		String className = NoNpArgConstructor.class.getName();
		expect(WippenLoaderException.class, "No public no-argument constructor found on class '" + className + "'");
		loader.createInstance("class:" + className);
	}

	@Test
	public void createInstance_throws_for_invalid_url() {
		String url = "inavlid url";
		expect(WippenLoaderException.class, "Unsupported url: '" + url + "'");
		loader.createInstance(url);
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
