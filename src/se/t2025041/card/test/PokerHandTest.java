package kobeU.cs.kadaiB.test;

import java.util.ArrayList;

import junit.framework.TestCase;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.DummyCard;
import se.t2025041.card.entity.PokerHand;

/**
 * 役判定ユーティリティのテストクラス
 *
 */
public class PokerHandTest extends TestCase {
	Card spadeA, diamond10, heartQ, clubK, spade5, diamondA, heartA, clubA, joker;
	Card spadeK, spadeQ, spadeJ, spade10, spade9, spade8, spade2;
	Card clubJ, heartJ, diamondK, club10, heart10;
	Card dummy;
	ArrayList<Card> hand;

	protected void setUp() throws Exception {
		hand = new ArrayList<Card>();

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
		
		clubJ = new Card(3, 11);
		heartJ = new Card(2, 11);
		diamondK = new Card(1, 13);
		club10 = new Card(3, 10);
		heart10 = new Card(2, 10);
		
		dummy = new DummyCard(-1, 0); // ダミー

		super.setUp();

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetStrength() {
		assertEquals(13, PokerHand.getStrength(spadeA));
		assertEquals(9, PokerHand.getStrength(diamond10));
		assertEquals(11, PokerHand.getStrength(heartQ));
		assertEquals(12, PokerHand.getStrength(clubK));
		assertEquals(4, PokerHand.getStrength(spade5));
		assertEquals(13, PokerHand.getStrength(diamondA));
		assertEquals(13, PokerHand.getStrength(heartA));
		assertEquals(13, PokerHand.getStrength(clubA));
		assertEquals(1, PokerHand.getStrength(spade2));
		assertEquals(-1, PokerHand.getStrength(joker));
		assertEquals(0, PokerHand.getStrength(dummy));

	}

	public void testSort() {
		hand.add(spadeA);
		hand.add(joker);
		hand.add(clubK);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(heartQ);

		PokerHand.sort(hand);
		assertEquals(1, hand.get(0).getNumber());
		assertSame(clubK, hand.get(3));
		assertSame(heartQ, hand.get(4));
		assertSame(joker, hand.get(5));
		
		hand.clear();
		hand.add(spadeA);
		hand.add(joker);
		hand.add(clubK);
		hand.add(dummy);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(dummy);
		hand.add(heartQ);
		
		PokerHand.sort(hand);
		assertSame(clubK, hand.get(3));
		assertSame(heartQ, hand.get(4));
		assertSame(dummy, hand.get(5));
		assertSame(dummy, hand.get(6));
		assertSame(joker, hand.get(7));
		
		hand.clear();
		hand.add(clubJ);
		hand.add(heartJ);
		hand.add(diamondK);
		hand.add(club10);
		hand.add(heart10);
		PokerHand.sort(hand);
		assertSame(diamondK, hand.get(0));
		assertEquals(11, hand.get(1).getNumber());
		assertEquals(11, hand.get(2).getNumber());
		assertEquals(10, hand.get(3).getNumber());
		assertEquals(10, hand.get(4).getNumber());
	}

	public void testIsOnePair() {
		hand.add(heartA);
		hand.add(clubK);
		hand.add(heartQ);
		hand.add(spade5);
		hand.add(diamond10);
		assertEquals(0, PokerHand.isOnePair(hand));

		hand.clear();
		hand.add(clubK);
		hand.add(heartA);
		hand.add(joker);
		hand.add(diamond10);
		hand.add(heartQ);
		assertEquals(13, PokerHand.isOnePair(hand));

		hand.clear();
		hand.add(clubK);
		hand.add(joker);
		hand.add(joker);
		hand.add(diamond10);
		hand.add(heartQ);
		assertEquals(12, PokerHand.isOnePair(hand));

		hand.clear();
		hand.add(heartA);
		hand.add(heartA);
		hand.add(diamond10);
		assertEquals(13, PokerHand.isOnePair(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(spade5);
		hand.add(spade5);
		hand.add(diamond10);
		assertEquals(4, PokerHand.isOnePair(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(joker);
		hand.add(joker);
		hand.add(joker);
		assertEquals(13, PokerHand.isOnePair(hand));
		
		hand.clear();
		hand.add(diamondK);
		hand.add(club10);
		hand.add(heart10);
		assertEquals(9, PokerHand.isOnePair(hand));
	}

	public void testIsTwoPair() {
		hand.add(heartA);
		hand.add(clubK);
		hand.add(heartQ);
		hand.add(spade5);
		hand.add(diamond10);
		assertEquals(0, PokerHand.isTwoPair(hand));

		hand.clear();
		hand.add(heartQ);
		hand.add(heartQ);
		hand.add(spade5);
		hand.add(spade5);
		hand.add(diamondK);
		assertEquals(11, PokerHand.isTwoPair(hand));
		
		hand.clear();
		hand.add(clubJ);
		hand.add(heartJ);
		hand.add(diamondK);
		hand.add(club10);
		hand.add(heart10);
		assertEquals(10, PokerHand.isTwoPair(hand));
	}

	public void testIsThreeCards() {
		hand.add(heartA);
		hand.add(clubK);
		hand.add(heartQ);
		hand.add(spade5);
		hand.add(diamond10);
		assertEquals(0, PokerHand.isThreeCards(hand));

		hand.clear();
		hand.add(heartQ);
		hand.add(heartQ);
		hand.add(spade5);
		hand.add(spade5);
		hand.add(heartQ);
		assertEquals(11, PokerHand.isThreeCards(hand));

		hand.clear();
		hand.add(heartQ);
		hand.add(heartQ);
		hand.add(spade5);
		hand.add(spade5);
		hand.add(joker);
		assertEquals(11, PokerHand.isThreeCards(hand));

		hand.clear();
		hand.add(heartQ);
		hand.add(joker);
		hand.add(spade5);
		hand.add(spade5);
		hand.add(joker);
		assertEquals(11, PokerHand.isThreeCards(hand));

		hand.clear();
		hand.add(heartQ);
		hand.add(joker);
		hand.add(clubK);
		hand.add(spade5);
		hand.add(joker);
		assertEquals(12, PokerHand.isThreeCards(hand));
	}

	public void testIsStraight() {
		hand.add(heartA);
		hand.add(clubK);
		hand.add(heartQ);
		hand.add(spadeJ);
		hand.add(diamond10);
		assertEquals(13, PokerHand.isStraight(hand));

		hand.clear();
		hand.add(heartA);
		hand.add(clubK);
		hand.add(joker);
		hand.add(spadeJ);
		hand.add(diamond10);
		assertEquals(13, PokerHand.isStraight(hand));

		hand.clear();
		hand.add(heartA);
		hand.add(clubK);
		hand.add(joker);
		hand.add(spadeJ);
		hand.add(joker);
		assertEquals(13, PokerHand.isStraight(hand));

		hand.clear();
		hand.add(clubK);
		hand.add(joker);
		hand.add(joker);
		hand.add(diamond10);
		hand.add(spade5);
		assertEquals(0, PokerHand.isStraight(hand));

		hand.clear();
		hand.add(joker);
		hand.add(joker);
		assertEquals(13, PokerHand.isStraight(hand));
	}

	public void testIsFlush() {
		hand.add(spadeA);
		hand.add(spade5);
		hand.add(spadeJ);
		hand.add(spadeK);
		hand.add(spade10);
		assertEquals(13, PokerHand.isFlush(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(spade5);
		hand.add(spadeJ);
		hand.add(joker);
		hand.add(spade10);
		assertEquals(13, PokerHand.isFlush(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(spade5);
		hand.add(spadeJ);
		hand.add(joker);
		hand.add(spade10);
		assertEquals(13, PokerHand.isFlush(hand));

		hand.clear();
		hand.add(joker);
		hand.add(spade5);
		hand.add(spadeJ);
		hand.add(joker);
		hand.add(spade10);
		assertEquals(10, PokerHand.isFlush(hand));

		hand.clear();
		hand.add(joker);
		hand.add(heartA);
		hand.add(spadeJ);
		hand.add(joker);
		hand.add(spade10);
		assertEquals(0, PokerHand.isFlush(hand));

		hand.clear();
		hand.add(joker);
		hand.add(joker);
		assertEquals(13, PokerHand.isFlush(hand));
		
		hand.clear();
		hand.add(joker);
		hand.add(dummy);
		hand.add(spadeJ);
		hand.add(dummy);
		hand.add(spade10);
		assertEquals(0, PokerHand.isFlush(hand));
	}

	public void testIsFullHouse() {
		hand.add(spadeA);
		hand.add(spade5);
		hand.add(spadeJ);
		hand.add(spadeK);
		hand.add(spade10);
		assertEquals(0, PokerHand.isFullHouse(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(heartA);
		hand.add(clubA);
		hand.add(spadeK);
		hand.add(spade10);
		assertEquals(0, PokerHand.isFullHouse(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(heartA);
		hand.add(clubA);
		hand.add(spadeK);
		hand.add(clubK);
		assertEquals(13, PokerHand.isFullHouse(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(joker);
		hand.add(clubA);
		hand.add(spadeK);
		hand.add(clubK);
		assertEquals(13, PokerHand.isFullHouse(hand));
	}

	public void testIsFourCards() {
		hand.add(spadeA);
		hand.add(spade5);
		hand.add(spadeJ);
		hand.add(spadeK);
		hand.add(spade10);
		assertEquals(0, PokerHand.isFourCards(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(heartA);
		hand.add(spade10);
		assertEquals(13, PokerHand.isFourCards(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(joker);
		hand.add(heartA);
		hand.add(spade10);
		assertEquals(13, PokerHand.isFourCards(hand));

		hand.clear();
		hand.add(joker);
		hand.add(joker);
		hand.add(diamondA);
		hand.add(heartA);
		hand.add(spade10);
		assertEquals(13, PokerHand.isFourCards(hand));
		
		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(dummy);
		hand.add(heartA);
		hand.add(joker);
		assertEquals(13, PokerHand.isFourCards(hand));
	}

	public void testIsStraightFlush() {
		hand.add(spadeA);
		hand.add(spade5);
		hand.add(spadeJ);
		hand.add(spadeK);
		hand.add(spade10);
		assertEquals(0, PokerHand.isStraightFlush(hand));

		hand.clear();
		hand.add(heartA);
		hand.add(clubK);
		hand.add(heartQ);
		hand.add(spadeJ);
		hand.add(diamond10);
		assertEquals(0, PokerHand.isStraightFlush(hand));

		hand.clear();
		hand.add(spadeK);
		hand.add(spade10);
		hand.add(spadeQ);
		hand.add(spadeJ);
		hand.add(spade9);
		assertEquals(12, PokerHand.isStraightFlush(hand));

		hand.clear();
		hand.add(joker);
		hand.add(spade10);
		hand.add(spadeQ);
		hand.add(spadeJ);
		hand.add(spade9);
		assertEquals(11, PokerHand.isStraightFlush(hand));
	}

	public void testIsFiveCards() {
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(heartA);
		hand.add(spade10);
		assertEquals(0, PokerHand.isFiveCards(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(heartA);
		hand.add(joker);
		assertEquals(13, PokerHand.isFiveCards(hand));

		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(joker);
		hand.add(heartA);
		hand.add(joker);
		assertEquals(13, PokerHand.isFiveCards(hand));
		
		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(dummy);
		hand.add(heartA);
		hand.add(joker);
		assertEquals(0, PokerHand.isFiveCards(hand));
	}

	public void testMatch() {
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(heartA);
		hand.add(joker);
		assertEquals(13 * 8 + 13, PokerHand.match(hand));

		hand.clear();
		hand.add(joker);
		hand.add(spade10);
		hand.add(spadeQ);
		hand.add(spadeJ);
		hand.add(spade9);
		assertEquals(13 * 7 + 11, PokerHand.match(hand));

		hand.clear();
		hand.add(clubK);
		hand.add(heartA);
		hand.add(joker);
		hand.add(spade5);
		hand.add(heartQ);
		assertEquals(13, PokerHand.match(hand));
	}

	public void testGetString() {
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(heartA);
		hand.add(joker);
		assertEquals("ファイブカード (A)", PokerHand.getString(hand));
		assertEquals("ファイブカード (A)", PokerHand.getString(PokerHand.match(hand)));

		hand.clear();
		hand.add(joker);
		hand.add(spade10);
		hand.add(spadeQ);
		hand.add(spadeJ);
		hand.add(spade9);
		assertEquals("ストレートフラッシュ (Q)", PokerHand.getString(hand));
		assertEquals("ストレートフラッシュ (Q)", PokerHand.getString(PokerHand.match(hand)));

		hand.clear();
		hand.add(spadeA);
		hand.add(spadeK);
		hand.add(spadeQ);
		hand.add(spadeJ);
		hand.add(spade10);
		assertEquals("ロイヤルストレートフラッシュ", PokerHand.getString(hand));
		assertEquals("ロイヤルストレートフラッシュ", PokerHand.getString(PokerHand.match(hand)));

		hand.clear();
		hand.add(clubK);
		hand.add(heartA);
		hand.add(joker);
		hand.add(spade5);
		hand.add(heartQ);
		assertEquals("ワンペア (A)", PokerHand.getString(hand));
		assertEquals("ワンペア (A)", PokerHand.getString(PokerHand.match(hand)));
		
		assertEquals("ワンペア (A)", PokerHand.getString(13));
		assertEquals("ノーペア", PokerHand.getString(0));
	}
	
	public void testEvaluate() {
		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(heartA);
		hand.add(new Card(2, 5));
		hand.add(joker);
		assertEquals(13.0*7, PokerHand.evaluate(hand, 0, 0.5));
		assertEquals((13*8+13-13*7)*0.5+13*7, PokerHand.evaluate(hand, 8, 0.5));
		
		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(diamondA);
		hand.add(heartA);
		hand.add(joker);
		assertTrue(PokerHand.evaluate(hand, 0, 0.5) == 13.0*8+13);
		assertTrue(PokerHand.evaluate(hand, 1, 0.5) <= 13.0*8+13);
		assertTrue(PokerHand.evaluate(hand, 2, 0.5) <= 13.0*8+13);
		
	}

	public void testSolve() {
		hand.add(heartA);
		hand.add(clubK);
		hand.add(heartQ);
		hand.add(spade5);
		hand.add(diamond10);
		assertEquals(0, PokerHand.solve(hand, 0));
		assertEquals(8, PokerHand.solve(hand, 0.5));
		

		hand.clear();
		hand.add(spadeA);
		hand.add(spadeK);
		hand.add(spadeQ);
		hand.add(new Card(2, 5));
		hand.add(spade10);
		assertEquals(8, PokerHand.solve(hand, 0.5));
		
		hand.clear();
		hand.add(spadeA);
		hand.add(clubA);
		hand.add(heartA);
		hand.add(new Card(2, 5));
		hand.add(joker);
		assertEquals(8, PokerHand.solve(hand, 0.5));
	}

}
