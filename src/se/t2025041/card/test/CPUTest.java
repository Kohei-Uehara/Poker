package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.Gambler;
import se.t2025041.card.entity.Gentleman;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.Lady;

public class CPUTest extends TestCase {
	CPU cpu1, cpu2, cpu3;
	Card spadeA, diamond10, heartQ, clubK, spade5, diamondA, heartA, clubA, joker;
	Card spadeK, spadeQ, spadeJ, spade10, spade9, spade8, spade2;

	protected void setUp() throws Exception {
		super.setUp();
		Keyboard.inputForTest(10);
		spadeA = new Card(0, 1); // スペードA
		diamond10 = new Card(1, 10); // ダイヤ10
		heartQ = new Card(2, 12); // ハートQ
		clubK = new Card(3, 13); // クラブK
		spade5 = new Card(0, 5); // スペード5
		diamondA = new Card(1, 1); // ダイヤA
		heartA = new Card(2, 1); // ハートA
		clubA = new Card(3, 1); // クラブA
		joker = new Card(-1, 0); // ジョーカー

		spadeK = new Card(0, 13);
		spadeQ = new Card(0, 12);
		spadeJ = new Card(0, 11);
		spade10 = new Card(0, 10);
		spade9 = new Card(0, 9);
		spade8 = new Card(0, 8);
		spade2 = new Card(0, 2);
		
		cpu1 = new CPU("CPU1", new Gentleman(0.08), 0.50);
		cpu2 = new CPU("CPU2", new Lady(0.1), 0.0);
		cpu3 = new CPU("CPU3", new Gambler(0.1), 1.0);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCPU(){
		assertEquals(0.5, cpu1.getMood());
		assertEquals(0.0, cpu2.getMood());
		assertEquals(1.0, cpu3.getMood());
		
		assertTrue(cpu1.getCharacter() instanceof Gentleman);
		assertTrue(cpu2.getCharacter() instanceof Lady);
		assertTrue(cpu3.getCharacter() instanceof Gambler);
	}
	
	public void testSelect() {
		cpu1.addCard(heartA);
		cpu1.addCard(clubK);
		cpu1.addCard(heartQ);
		cpu1.addCard(spade5);
		cpu1.addCard(diamond10);
		assertEquals(8, cpu1.select());
		
		cpu2.addCard(heartA);
		cpu2.addCard(clubK);
		cpu2.addCard(heartQ);
		cpu2.addCard(spade5);
		cpu2.addCard(diamond10);
		assertEquals(0, cpu2.select());

		cpu1.clearHand();
		cpu1.addCard(spadeA);
		cpu1.addCard(spadeK);
		cpu1.addCard(spadeQ);
		cpu1.addCard(new Card(2, 5));
		cpu1.addCard(spade10);
		assertEquals(8, cpu1.select());
		
		cpu1.clearHand();
		cpu1.addCard(spadeA);
		cpu1.addCard(clubA);
		cpu1.addCard(heartA);
		cpu1.addCard(new Card(2, 5));
		cpu1.addCard(joker);
		assertEquals(8, cpu1.select());
	}

	public void testWin() {
		cpu1.win();
		cpu2.win();
		cpu3.win();
		assertEquals(0.58, cpu1.getMood());
		assertEquals(0.1, cpu2.getMood());
		assertEquals(1.0, cpu3.getMood());
	}

	public void testLose() {
		cpu1.lose();
		cpu2.lose();
		cpu3.lose();
		assertEquals(0.42, cpu1.getMood());
		assertEquals(0.0, cpu2.getMood());
		assertEquals(0.9, cpu3.getMood());
	}

	public void testReactToFlattery() {
		cpu1.reactToFlattery();
		cpu2.reactToFlattery();
		cpu3.reactToFlattery();
		assertEquals(0.55, cpu1.getMood());
		assertEquals(0.05, cpu2.getMood());
		assertEquals(1.0, cpu3.getMood());
	}

	public void testReactToProvocation() {
		cpu1.reactToProvocation();
		cpu2.reactToProvocation();
		cpu3.reactToProvocation();
		assertEquals(0.45, cpu1.getMood());
		assertEquals(0.00, cpu2.getMood());
		assertEquals(0.95, cpu3.getMood());
	}

}
