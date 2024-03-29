package Item79;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import item18.composition.ForwardingSet;

public class ObservableSet<E> extends ForwardingSet<E> {
	public ObservableSet(Set<E> s) {
		super(s);
	}

	private final List<SetObserver<E>> observers = new ArrayList<>();

	public void addObserver(SetObserver<E> observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	public void removeObserver(SetObserver<E> observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	private void notifyElementAdded(E element) {

		// synchronized (observers) {
		// 	for (SetObserver<E> observer : observers) {
		// 		observer.added(this, element);
		// 	}
		// }

		List<SetObserver<E>> snapShot = null;
		synchronized (observers) {
			snapShot = new ArrayList<>(observers);
		}

		for (SetObserver<E> observer : snapShot) {
			observer.added(this, element);
		}
	}

	@Override
	public boolean add(E e) {
		boolean added = super.add(e);
		if (added) {
			notifyElementAdded(e);
		}
		return added;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean result = false;
		for (E e : c) {
			result |= add(e);
		}
		return result;
	}
}
