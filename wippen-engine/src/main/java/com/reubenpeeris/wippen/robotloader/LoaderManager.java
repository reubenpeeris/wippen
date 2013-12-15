package com.reubenpeeris.wippen.robotloader;

import java.util.ArrayList;
import java.util.List;

import lombok.NonNull;

public class LoaderManager<T> {
	private final List<Loader<T>> loaders = new ArrayList<>();

	protected void registerLoader(@NonNull Loader<T> loader) {
		loaders.add(loader);
	}

	protected T createInstance(@NonNull String url) throws WippenLoaderException {
		for (Loader<T> loader : loaders) {
			if (loader.acceptsUrl(url)) {
				return loader.createInstance(url);
			}
		}

		throw new WippenLoaderException("No suitable loader found for url: '" + url + "'");
	}
}
