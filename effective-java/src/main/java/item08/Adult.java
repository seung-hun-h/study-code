package item08;

public class Adult {
	public static void main(String[] args) {
		try (Room room = new Room(7)) {
			System.out.println("안녕");
		}
	}
}
