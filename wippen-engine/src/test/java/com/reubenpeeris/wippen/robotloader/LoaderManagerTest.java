package com.reubenpeeris.wippen.robotloader;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.reubenpeeris.wippen.BaseTest;

public class LoaderManagerTest extends BaseTest {
	private LoaderManager<ConstructorLoaderTest.AnInterface> loaderManager;

	@Before
	public void createLoaderManager() {
		loaderManager = new LoaderManager<>();
	}

	@Test
	public void registerLoader_throws_for_null_loader() {
		expect(NullPointerException.class, "loader");
		loaderManager.registerLoader(null);
	}

	@Test
	public void createInstance_throws_for_null_url() {
		expect(NullPointerException.class, "url");
		loaderManager.createInstance(null);
	}

	@Test
	public void createInstance_throws_when_there_are_no_suitable_loaders_registered() {
		String url = "unsupported://protocol";
		expect(WippenLoaderException.class, "No suitable loader found for url: '" + url + "'");
		loaderManager.createInstance(url);
	}

	@Test
	public void createInstance_creates_expected_class_for_valid_url() {
		loaderManager.registerLoader(ConstructorLoaderTest.loader);
		String url = "class:" + ConstructorLoaderTest.Valid.class.getName();
		ConstructorLoaderTest.AnInterface instance = loaderManager.createInstance(url);
		assertThat(instance.getClass() == ConstructorLoaderTest.Valid.class, is(true));
	}

	@Test
	public void createInstance_throws_for_unsupported_protocol() {
		String url = "unsupported://protocol";
		expect(WippenLoaderException.class, "No suitable loader found for url: '" + url + "'");
		loaderManager.createInstance(url);
	}
}
