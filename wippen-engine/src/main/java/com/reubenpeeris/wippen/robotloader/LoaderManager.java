package com.reubenpeeris.wippen.robotloader;

import java.util.ArrayList;
import java.util.List;

public class LoaderManager<T> {
	private final List<Loader<T>> loaders = new ArrayList<>();
	
	protected void registerLoader(Loader<T> loader) {
		if (loader ==  null) {
			throw new IllegalArgumentException();
		}
		
		loaders.add(loader);
	}
	
	protected T createInstance(String url) throws LoaderException {
		if (url == null) {
			throw new IllegalArgumentException();
		}
		
		for (Loader<T> loader : loaders) {
			if (loader.acceptsURL(url)) {
				return loader.createInstance(url);
			}
		}
		
		throw new LoaderException("No suitable loader found for url: '" + url + "'");
	}
}
