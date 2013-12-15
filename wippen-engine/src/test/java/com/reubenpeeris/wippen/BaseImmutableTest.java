package com.reubenpeeris.wippen;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Test;

import com.reubenpeeris.wippen.engine.Player;
import com.reubenpeeris.wippen.expression.Expression;
import com.reubenpeeris.wippen.expression.Rank;

public abstract class BaseImmutableTest<T> extends BaseTest {
	protected abstract T validInstance();

	protected List<MethodSignature> methodsExemptFromImmutabilityCheck() {
		List<MethodSignature> exemptMethods = new ArrayList<>();

		exemptMethods.add(new MethodSignature(Boolean.TYPE, "equals", Object.class));
		exemptMethods.add(new MethodSignature(Boolean.TYPE, "canEqual", Object.class));

		return exemptMethods;
	}

	@Test
	public void public_methods_do_not_return_or_accept_mutable_objects() throws Exception {
		Class<?> clazz = validInstance().getClass();

		for (Method method : clazz.getMethods()) {
			if (isMethodOfInterest(method)) {
				assertThat("returned object may not be immutable: " + method.getName(), isReturnTypeImmutable(method), is(true));
				assertThat("parameter object may not be immutable: " + method.getName(), areParameterTypesImmutable(method), is(true));
			}
		}
	}

	@Test
	public void class_is_final() {
		assertThat(Modifier.isFinal(validInstance().getClass().getModifiers()), is(true));
	}

	@Test
	public void all_super_classes_only_have_package_private_constructors() {
		Class<?> superClazz = validInstance().getClass().getSuperclass();
		while (superClazz != Object.class) {
			for (Constructor<?> constructor : superClazz.getDeclaredConstructors()) {
				assertThat(String.format("%s.%s does not have default visibility", superClazz.getSimpleName(), constructor),
						isDefault(constructor.getModifiers()), is(true));
			}
			superClazz = superClazz.getSuperclass();
		}
	}

	private boolean isDefault(int mod) {
		return !Modifier.isPrivate(mod) && !Modifier.isProtected(mod) && !Modifier.isPublic(mod);
	}

	private boolean isMethodOfInterest(Method method) {
		for (MethodSignature exemptMethod : methodsExemptFromImmutabilityCheck()) {
			if (exemptMethod.equivalentTo(method)) {
				return false;
			}
		}

		return !Modifier.isStatic(method.getModifiers()) && !method.getDeclaringClass().equals(Object.class);
	}

	private boolean areParameterTypesImmutable(Method method) {
		for (Class<?> parameter : method.getParameterTypes()) {
			if (!isTypeImmutable(parameter)) {
				return false;
			}
		}

		return true;
	}

	private boolean isTypeImmutable(Class<?> type) {
		if (type.isPrimitive() || type == Void.class || type.isEnum()) {
			return true;
		}

		for (Class<?> immutabeClass : new Class<?>[] { Expression.class, Rank.class, Player.class, String.class }) {
			if (immutabeClass.isAssignableFrom(type)) {
				return true;
			}
		}

		return false;
	}

	private boolean isReturnTypeImmutable(Method method) throws Exception {
		Class<?> returnType = method.getReturnType();

		if (isTypeImmutable(returnType)) {
			return true;
		}

		if (Collection.class.isAssignableFrom(returnType) && method.getParameterTypes().length == 0) {
			method.setAccessible(true);
			Object returnValue = method.invoke(validInstance());
			return unmodifiable().matches(returnValue);
		}

		return false;
	}

	private <C extends Collection<?>> Matcher<C> unmodifiable() {
		return new Unmodifiable<>();
	}

	public static class MethodSignature {
		private final Class<?> returnType;
		private final String methodName;
		private final Class<?>[] parameterTypes;

		public MethodSignature(Class<?> returnType, String methodName, Class<?>... parameterTypes) {
			this.returnType = returnType;
			this.methodName = methodName;
			this.parameterTypes = parameterTypes;
		}

		public boolean equivalentTo(Method method) {
			return method.getReturnType() == returnType && method.getName().equals(methodName)
					&& Arrays.equals(method.getParameterTypes(), parameterTypes);
		}
	}

	private static class Unmodifiable<T extends Collection<?>> extends BaseMatcher<T> {
		private final String[] UNMODIFIABLE_COLLECTION_CLASS_PREFIXES = { com.reubenpeeris.wippen.util.CollectionPair.class.getCanonicalName(),
				"java.util.Collections.Unmodifiable", "java.util.Collections.SingletonSet", "java.util.Collections.EmptySet" };

		@Override
		public boolean matches(Object item) {
			if (item != null) {
				String className = item.getClass().getCanonicalName();
				for (String unmodifiableCollectionPrefix : UNMODIFIABLE_COLLECTION_CLASS_PREFIXES) {
					if (className.startsWith(unmodifiableCollectionPrefix)) {
						return true;
					}
				}
			}

			return false;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("class should start with one of the following prefixes: "
					+ Arrays.toString(UNMODIFIABLE_COLLECTION_CLASS_PREFIXES));
		}
	}
}
