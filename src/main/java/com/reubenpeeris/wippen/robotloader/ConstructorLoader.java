package com.reubenpeeris.wippen.robotloader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Format: class://dotted.class.name
 */
public class ConstructorLoader<T> implements Loader<T> {
	private static final String PROTOCOL = "class:";
	private final Class<T> tClazz;

	public ConstructorLoader(Class<T> clazz) {
		this.tClazz = clazz;
	}

	@Override
	public boolean acceptsURL(String url) throws LoaderException {
		if (url == null) {
			throw new IllegalArgumentException();
		}

		return url.startsWith(PROTOCOL);
	}

	@Override
	public T createInstance(String url) throws LoaderException {
		if (!acceptsURL(url)) {
			throw new LoaderException("Unsupported url: '" + url + "'");
		}

		String className = url.substring(PROTOCOL.length());
		try {
			Class<?> clazz = Class.forName(className);
			if (!Modifier.isPublic(clazz.getModifiers())){
				throw new LoaderException("Class is not accessible: '" + className + "'");
			}

			if (!tClazz.isAssignableFrom(clazz)) {
				throw new LoaderException("Class '" + className + "' does not implement interface '" + tClazz.getName() + "'");
			}
			@SuppressWarnings("unchecked")
			Class<T> c = (Class<T>) clazz;
			Constructor<T> noArgConstructor = c.getConstructor();

			return noArgConstructor.newInstance();
		} catch (ClassNotFoundException e) {
			throw new LoaderException("Class not found: '" + className + "'", e);
		} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
			throw new LoaderException("No public no-argument constructor found on class '" + className + "'", e);
		}
	}

}
