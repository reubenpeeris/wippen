package com.reubenpeeris.wippen.robotloader;

import java.io.FileNotFoundException;

import lombok.NonNull;

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

	protected SpringLoader(@NonNull Class<T> clazz, @NonNull String beanName) {
		if (beanName.isEmpty()) {
			throw new IllegalArgumentException("beanName must not be empty");
		}

		this.clazz = clazz;
		this.beanName = beanName;
	}

	@Override
	public boolean acceptsUrl(@NonNull String url) {
		return url.startsWith(PROTOCOL);
	}

	@Override
	public T createInstance(String url) {
		if (!acceptsUrl(url)) {
			throw new WippenLoaderException("Unsupported url: '" + url + "'");
		}
		String path = url.substring(PROTOCOL.length());
		ClassPathResource resource = new ClassPathResource(path);

		try (@SuppressWarnings("resource")
		AbstractApplicationContext context = resource.exists()
				? new ClassPathXmlApplicationContext(path)
				: new FileSystemXmlApplicationContext(path)) {
			try {
				return context.getBean(beanName, clazz);
			} catch (BeansException e) {
				throw new WippenLoaderException("Failed to load bean '" + beanName + "'", e);
			}
		} catch (BeanDefinitionStoreException e) {
			if (e.getCause() instanceof FileNotFoundException) {
				throw new WippenLoaderException("Failed to find file: '" + path + "'", e);
			} else {
				throw new WippenLoaderException("Failed to process file: '" + path + "'", e);
			}
		}

	}
}
