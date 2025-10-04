package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.CardDeck;
import se.t2025041.card.entity.Dealer;
import se.t2025041.card.entity.Gambler;
import se.t2025041.card.entity.Gentleman;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.Lady;
import se.t2025041.card.entity.Player;
import se.t2025041.card.entity.PlayersSeat;
import se.t2025041.card.entity.User;
import se.t2025041.card.game.PokerTable;
import se.t2025041.card.game.SelectionCommand;


/**
 * 交換フェイズの処理を分担するサブクラスのテストクラス
 *
 */
public class SelectionCommandTest extends TestCase {
	SelectionCommand sc;
	Player user, cpu1, cpu2, cpu3;
	CPU dealer;

	protected void setUp() throws Exception {
		super.setUp();
		sc = new SelectionCommand();
		Keyboard.inputForTest(10);
		user = new User("ユーザ");
		PokerTable.setNUMBER_OF_TURNS(5);
		PlayersSeat.setNUMBER_OF_FIRST_CHIPS(10);
		dealer = new CPU("ディーラー", new Dealer(0.05), 0.25);
		cpu1 = new CPU("CPU1", new Gentleman(0.08), 0.30);
		cpu2 = new CPU("CPU2", new Lady(0.1), 0.50);
		cpu3 = new CPU("CPU3", new Gambler(0.1), 0.70);
		
		user.addCard(new Card(-1,0));
		user.addCard(new Card(-1,0));
		
		cpu1.addCard(new Card(-1,0));
		cpu1.addCard(new Card(-1,0));
		
		cpu2.addCard(new Card(-1,0));
		cpu2.addCard(new Card(-1,0));
		
		cpu3.addCard(new Card(-1,0));
		cpu3.addCard(new Card(-1,0));
		
		dealer.addCard(new Card(-1,0));
		dealer.addCard(new Card(-1,0));
		
		CardDeck deck = new CardDeck();
		
		deck.createFullDeck();
		sc.setDeck(deck);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testShowMenu() {
		for (int i = 0; i < 10; i++) {
			sc.addPlayer(user);
			sc.addPlayer(cpu1);
			sc.addPlayer(cpu2);
			sc.addPlayer(cpu3);
			sc.addPlayer(dealer);
			sc.showMenu();
		}
		assertTrue(sc.getPlayers().isEmpty());
	}

	public void testAddPlayer() {
		sc.addPlayer(cpu1);
		sc.addPlayer(cpu2);
		sc.addPlayer(cpu3);
		assertTrue(sc.getPlayers().isEmpty());
		sc.addPlayer(user);
		assertTrue(!sc.getPlayers().isEmpty());
		sc.addPlayer(user);
		assertEquals(1, sc.getPlayers().size());
	}

}
