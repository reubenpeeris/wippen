package com.reubenpeeris.wippen.robotloader;

public interface Loader<T> {
	boolean acceptsUrl(String url) throws WippenLoaderException;

	T createInstance(String url) throws WippenLoaderException;
}
