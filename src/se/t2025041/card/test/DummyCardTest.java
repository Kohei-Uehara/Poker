package se.t2025041.card.test;

import junit.framework.TestCase;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.DummyCard;

/**
 * ダミーカードのテストクラス
 *
 */
public class DummyCardTest extends TestCase {
	private Card spadeA, diamond10, heartQ, clubK, spade5, diamondA, heartA, clubA, joker;
	
	protected void setUp() throws Exception {
		super.setUp();
		spadeA = new DummyCard(0, 1); // スペードA
		diamond10 = new DummyCard(1, 10); // ダイヤ10
		heartQ = new DummyCard(2, 12); // ハートQ
		clubK = new DummyCard(3, 13); // クラブK

		// 他にも作っておく．
		spade5 = new DummyCard(0, 5); // スペード5
		diamondA = new DummyCard(1, 1); // ダイヤA
		heartA = new DummyCard(2, 1); // ハートA
		clubA = new DummyCard(3, 1); // クラブA
		joker = new DummyCard(-1, 0); // ジョーカー
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testToString() {
		assertEquals("ダミー", spadeA.toString());
		assertEquals("ダミー", diamond10.toString());
		assertEquals("ダミー", heartQ.toString());
		assertEquals("ダミー", clubK.toString());
		assertEquals("ダミー", spade5.toString());
		assertEquals("ダミー", diamondA.toString());
		assertEquals("ダミー", heartA.toString());
		assertEquals("ダミー", clubA.toString());
		assertEquals("ダミー", joker.toString());
	}

	public void testGetSuit() {
		assertEquals(-2, spadeA.getSuit()); 
		assertEquals(-2, diamond10.getSuit()); 
		assertEquals(-2, heartQ.getSuit()); 
		assertEquals(-2, clubK.getSuit());
		assertEquals(-2, joker.getSuit()); 

	}

	public void testGetNumber() {
		assertEquals(0, spadeA.getNumber());
		assertEquals(0, diamond10.getNumber()); 
		assertEquals(0, heartQ.getNumber());
		assertEquals(0, clubK.getNumber());
	}

}
