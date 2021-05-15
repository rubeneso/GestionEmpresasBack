package com.GestionEmpresas.excepciones;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;


public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5113032088143294921L;

	public EntityNotFoundException(Class clazz, String... searchParamsMap) {
        super(EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }

    private static String generateMessage(String entity, Map<String, String> searchParams) {
        return StringUtils.capitalize(entity) + " no se encontró para los parámetros " + searchParams;
    }

    private static <K, V> Map<K, V> toMap(
            Class<K> keyType, Class<V> valueType, Object... entries) {
        if (entries.length % 2 == 1)
            throw new IllegalArgumentException("Entradas inválidas");
        return IntStream.range(0, entries.length / 2)
        					.map(i -> i * 2)
        					.collect(HashMap::new,
        							(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])),
        							Map::putAll);
    }
}
