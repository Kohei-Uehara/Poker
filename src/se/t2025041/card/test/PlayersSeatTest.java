package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.Dealer;
import se.t2025041.card.entity.Gambler;
import se.t2025041.card.entity.Gentleman;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.Lady;
import se.t2025041.card.entity.PlayersSeat;

/**
 * 座席クラスのテストクラス
 *
 */
public class PlayersSeatTest extends TestCase {
	PlayersSeat seat1, seat2, seat3, seat4;

	protected void setUp() throws Exception {
		Keyboard.inputForTest(5);
		PlayersSeat.setNUMBER_OF_FIRST_CHIPS(10);
		seat1 = new PlayersSeat(new CPU("CPU1", new Gentleman(0.1), 0.5));
		seat2 = new PlayersSeat(new CPU("CPU2", new Lady(0.1), 0.5));

		PlayersSeat.setNUMBER_OF_FIRST_CHIPS(5);
		seat3 = new PlayersSeat(new CPU("CPU3", new Gambler(0.1), 0.5));
		seat4 = new PlayersSeat(new CPU("CPU4", new Dealer(0.1), 0.5));

		seat1.getPlayer().addCard(new Card(0, 1));
		seat1.getPlayer().addCard(new Card(1, 1));
		seat1.getPlayer().addCard(new Card(2, 1));
		seat1.getPlayer().addCard(new Card(3, 1));
		seat1.getPlayer().addCard(new Card(-1, 0));

		seat2.getPlayer().addCard(new Card(0, 1));
		seat2.getPlayer().addCard(new Card(1, 1));
		seat2.getPlayer().addCard(new Card(2, 1));
		seat2.getPlayer().addCard(new Card(3, 1));
		seat2.getPlayer().addCard(new Card(-1, 0));

		seat3.getPlayer().addCard(new Card(0, 1));
		seat3.getPlayer().addCard(new Card(1, 1));
		seat3.getPlayer().addCard(new Card(2, 1));
		seat3.getPlayer().addCard(new Card(3, 1));
		seat3.getPlayer().addCard(new Card(-1, 0));

		seat4.getPlayer().addCard(new Card(0, 1));
		seat4.getPlayer().addCard(new Card(1, 1));
		seat4.getPlayer().addCard(new Card(2, 1));
		seat4.getPlayer().addCard(new Card(3, 1));
		seat4.getPlayer().addCard(new Card(-1, 0));

		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBet() {
		seat1.bet();
		seat2.bet();
		assertEquals(4, seat1.getBet());
		assertEquals(4, seat2.getBet());
		assertEquals(6, seat1.getChips());
		assertEquals(6, seat2.getChips());

		seat3.bet();
		seat4.bet();
		assertEquals(2, seat3.getBet());
		assertEquals(2, seat4.getBet());
		assertEquals(3, seat3.getChips());
		assertEquals(3, seat4.getChips());
	}

	public void testResetIsRaise() {
		seat1.bet();
		seat1.determin();
		assertTrue(seat1.isRaise());
		seat1.resetIsRaise();
		assertTrue(!seat1.isRaise());
	}

	public void testDetermin() {
		seat1.bet();
		seat1.determin();
		assertEquals(8, seat1.getBet());
		assertEquals(2, seat1.getChips());

		seat2.bet();
		seat2.getPlayer().changeCard(0, new Card(0, 1));
		seat2.getPlayer().changeCard(1, new Card(0, 4));
		seat2.getPlayer().changeCard(2, new Card(2, 11));
		seat2.getPlayer().changeCard(3, new Card(3, 3));
		seat2.getPlayer().changeCard(4, new Card(1, 6));
		seat2.determin();
		assertEquals(0, seat2.getBet());
		assertEquals(6, seat2.getChips());
	}

	public void testWin() {
		seat1.bet();
		seat2.bet();
		seat3.bet();
		seat4.bet();

		seat1.determin();
		seat2.determin();
		seat3.determin();
		seat4.determin();
		seat1.win();
		seat2.win();
		seat3.win();
		seat4.win();

		assertEquals(0, seat1.getBet());
		assertEquals(0, seat2.getBet());
		assertEquals(18, seat1.getChips());
		assertEquals(18, seat2.getChips());

		assertEquals(0, seat3.getBet());
		assertEquals(0, seat4.getBet());
		assertEquals(9, seat3.getChips());
		assertEquals(9, seat4.getChips());
	}

	public void testLose() {
		seat1.bet();
		seat2.bet();
		seat3.bet();
		seat4.bet();

		seat1.determin();
		seat2.determin();
		seat3.determin();
		seat4.determin();
		seat1.lose();
		seat2.lose();
		seat3.lose();
		seat4.lose();

		assertEquals(0, seat1.getBet());
		assertEquals(0, seat2.getBet());
		assertEquals(2, seat1.getChips());
		assertEquals(2, seat2.getChips());

		assertEquals(0, seat3.getBet());
		assertEquals(0, seat4.getBet());
		assertEquals(1, seat3.getChips());
		assertEquals(1, seat4.getChips());
	}

	public void testDraw() {
		seat1.bet();
		seat2.bet();
		seat3.bet();
		seat4.bet();

		seat1.determin();
		seat2.determin();
		seat3.determin();
		seat4.determin();
		seat1.draw();
		seat2.draw();
		seat3.draw();
		seat4.draw();

		assertEquals(0, seat1.getBet());
		assertEquals(0, seat2.getBet());
		assertEquals(10, seat1.getChips());
		assertEquals(10, seat2.getChips());

		assertEquals(0, seat3.getBet());
		assertEquals(0, seat4.getBet());
		assertEquals(5, seat3.getChips());
		assertEquals(5, seat4.getChips());
	}

}
