package com.blackun.util;

import java.util.Collection;
import java.util.stream.Stream;

public class Streams {

	/**
	 * null safe Stream 리턴
	 *
	 * @param collection
	 * @param <T>
	 * @return
	 */
	public static <T> Stream<T> ofNullable(Collection<T> collection) {
		return collection == null ? Stream.empty() : collection.stream();
	}

	/**
	 * null safe Stream 리턴
	 *
	 * @param values
	 * @param <T>
	 * @return
	 */
	public static <T> Stream<T> ofNullable(T... values) {
		return values == null ? Stream.empty() : Stream.of(values);
	}
}
