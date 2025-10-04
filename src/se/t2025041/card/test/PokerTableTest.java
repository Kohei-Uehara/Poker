package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Dealer;
import se.t2025041.card.entity.Gambler;
import se.t2025041.card.entity.Gentleman;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.Lady;
import se.t2025041.card.entity.PlayersSeat;
import se.t2025041.card.entity.User;
import se.t2025041.card.game.PokerTable;

/**
 * ポーカーテーブルクラスのテストクラス
 *
 */
public class PokerTableTest extends TestCase {
	PokerTable table;
	PlayersSeat user, cpu1, cpu2, cpu3;
	CPU dealer;
	
	protected void setUp() throws Exception {
		table = new PokerTable();
		Keyboard.inputForTest(10);
		super.setUp();
		user = new PlayersSeat(new User("ユーザ"));
		PokerTable.setNUMBER_OF_TURNS(5);
		PlayersSeat.setNUMBER_OF_FIRST_CHIPS(10);
		dealer = new CPU("ディーラー", new Dealer(0.05), 0.25);
		cpu1 = new PlayersSeat(new CPU("CPU1", new Gentleman(0.08), 0.30));
		cpu2 = new PlayersSeat(new CPU("CPU2", new Lady(0.1), 0.50));
		cpu3 = new PlayersSeat(new CPU("CPU3", new Gambler(0.1), 0.70));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testStartGame() {
		for(int i = 0; i < 10; i++) {
			table.addSeats(user);
			table.addSeats(cpu1);
			table.addSeats(cpu2);
			table.addSeats(cpu3);
			table.setDealer(dealer);
			table.startGame();
		}
		assertTrue(table.getSeats().isEmpty());
	}

	public void testAddSeats() {
		table.addSeats(cpu1);
		table.addSeats(cpu2);
		table.addSeats(cpu3);
		assertTrue(table.getSeats().isEmpty());
		table.addSeats(user);
		assertTrue(!table.getSeats().isEmpty());
		table.addSeats(user);
		assertEquals(1, table.getSeats().size());
	}

}
