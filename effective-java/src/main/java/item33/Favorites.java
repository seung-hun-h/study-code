package item33;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Favorites {
	private static Map<Class<?>, Object> favorites = new HashMap<>();
	public <T> void putFavorites(Class<T> type, T instance) {
		favorites.put(Objects.requireNonNull(type), type.cast(instance));
	}

	public <T> T getFavorite(Class<T> type) {
		return type.cast(favorites.get(type));
	}
}
