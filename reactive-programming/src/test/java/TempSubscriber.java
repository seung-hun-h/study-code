import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class TempSubscriber implements Subscriber<TempInfo> {
	private Subscription subscription;

	@Override
	public void onSubscribe(Subscription subscription) {
		this.subscription = subscription;
		subscription.request(1);
	}

	@Override
	public void onNext(TempInfo item) {
		System.out.println("item = " + item);
		subscription.request(1);
	}

	@Override
	public void onError(Throwable throwable) {
		System.err.println("throwable.getMessage() = " + throwable.getMessage());
	}

	@Override
	public void onComplete() {
		System.out.println("Done!");
	}
}
