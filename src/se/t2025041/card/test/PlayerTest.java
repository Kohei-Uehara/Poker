package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.Gambler;
import se.t2025041.card.entity.Gentleman;
import se.t2025041.card.entity.Lady;
import se.t2025041.card.entity.Player;
import se.t2025041.card.entity.User;

/**
 * プレイヤクラスのテストクラス
 *
 */
public class PlayerTest extends TestCase {
	Player user, cpu1, cpu2, cpu3;
	Card spadeA, diamond10, heartQ, clubK, spade5, diamondA, heartA, clubA, joker;
	
	protected void setUp() throws Exception {
		super.setUp();
		user = new User("ユーザ");
		cpu1 = new CPU("CPU1", new Gentleman(0.08), 0.50);
		cpu2 = new CPU("CPU2", new Lady(0.1), 0.0);
		cpu3 = new CPU("CPU3", new Gambler(0.1), 1.0);
		
		spadeA = new Card(0, 1); // スペードA
		diamond10 = new Card(1, 10); // ダイヤ10
		heartQ = new Card(2, 12); // ハートQ
		clubK = new Card(3, 13); // クラブK
		spade5 = new Card(0, 5); // スペード5
		diamondA = new Card(1, 1); // ダイヤA
		heartA = new Card(2, 1); // ハートA
		clubA = new Card(3, 1); // クラブA
		joker = new Card(-1, 0); // ジョーカー

	}

	protected void tearDown() throws Exception {
		super.tearDown();

	}

	public void testAddCard() {
		cpu1.addCard(spadeA);
		cpu1.addCard(diamond10);
		cpu1.addCard(heartQ);
		assertSame(spadeA, cpu1.getHand().get(0));
		assertSame(diamond10, cpu1.getHand().get(1));
		assertSame(heartQ, cpu1.getHand().get(2));
		
		cpu2.addCard(clubK);
		cpu2.addCard(spade5);
		cpu2.addCard(diamondA);
		assertSame(clubK, cpu2.getHand().get(0));
		assertSame(spade5, cpu2.getHand().get(1));
		assertSame(diamondA, cpu2.getHand().get(2));
		
		cpu3.addCard(heartA);
		cpu3.addCard(clubA);
		cpu3.addCard(joker);
		assertSame(heartA, cpu3.getHand().get(0));
		assertSame(clubA, cpu3.getHand().get(1));
		assertSame(joker, cpu3.getHand().get(2));
	}

	public void testChangeCard() {
		cpu1.addCard(spadeA);
		cpu1.addCard(diamond10);
		cpu1.addCard(heartQ);
		assertSame(spadeA, cpu1.getHand().get(0));
		assertSame(diamond10, cpu1.getHand().get(1));
		assertSame(heartQ, cpu1.getHand().get(2));
		
		cpu1.changeCard(0, clubK);
		assertSame(clubK, cpu1.getHand().get(0));
		
		cpu1.changeCard(1, spadeA);
		assertSame(spadeA, cpu1.getHand().get(1));
		
		cpu1.changeCard(2, clubK);
		assertSame(clubK, cpu1.getHand().get(2));
	}

	public void testClearHand() {
		cpu1.addCard(spadeA);
		cpu1.addCard(diamond10);
		cpu1.addCard(heartQ);
		
		cpu2.addCard(clubK);
		cpu2.addCard(spade5);
		cpu2.addCard(diamondA);
		
		cpu3.addCard(heartA);
		cpu3.addCard(clubA);
		cpu3.addCard(joker);
		
		cpu1.clearHand();
		assertTrue(cpu1.getHand().isEmpty());
		
		cpu2.clearHand();
		assertTrue(cpu2.getHand().isEmpty());
		
		cpu3.clearHand();
		assertTrue(cpu3.getHand().isEmpty());
	}

}
