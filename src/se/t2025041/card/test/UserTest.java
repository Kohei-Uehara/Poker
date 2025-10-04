package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.User;

public class UserTest extends TestCase {
	User user;

	protected void setUp() throws Exception {
		Keyboard.inputForTest(5);
		user = new User("テスト");
		user.addCard(new Card(0, 1));
		user.addCard(new Card(0, 2));
		user.addCard(new Card(0, 3));
		user.addCard(new Card(0, 4));
		user.addCard(new Card(0, 5));
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testSelect() {

		for (int i = 0; i < 10; i++) {
			int select = user.select();
			assertTrue(select < 32 && select >= 0);
		}

	}

}
