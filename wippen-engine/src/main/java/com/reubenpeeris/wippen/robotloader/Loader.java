package com.reubenpeeris.wippen.robotloader;

public interface Loader<T> {
	boolean acceptsUrl(String url);

	T createInstance(String url);
}
