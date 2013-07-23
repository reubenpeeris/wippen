package com.reubenpeeris.wippen.robotloader;

public interface Loader<T> {
	boolean acceptsURL(String url) throws LoaderException;
	T createInstance(String url) throws LoaderException;
}
