package com.reubenpeeris.wippen.robotloader;

import java.io.FileNotFoundException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * Format: spring://classpath-path spring://file-path
 */
public class SpringLoader<T> implements Loader<T> {
	private static final String PROTOCOL = "spring:";

	private final Class<T> clazz;
	private final String beanName;

	protected SpringLoader(Class<T> clazz, String beanName) {
		if (clazz == null || beanName == null || beanName.isEmpty()) {
			throw new IllegalArgumentException();
		}

		this.clazz = clazz;
		this.beanName = beanName;
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
		String path = url.substring(PROTOCOL.length());
		ClassPathResource resource = new ClassPathResource(path);

		try (@SuppressWarnings("resource")
		AbstractApplicationContext context = resource.exists() ? new ClassPathXmlApplicationContext(
				path) : new FileSystemXmlApplicationContext(path)) {
			try {
				return context.getBean(beanName, clazz);
			} catch (BeansException e) {
				throw new LoaderException("Failed to load bean '" + beanName
						+ "'", e);
			}
		} catch (BeanDefinitionStoreException e) {
			if (e.getCause() instanceof FileNotFoundException) {
				throw new LoaderException(
						"Failed to find file: '" + path + "'", e);
			} else {
				throw new LoaderException("Failed to process file: '" + path
						+ "'", e);
			}
		}

	}
}
