package se.t2025041.card.entity;

import java.util.ArrayList;
import java.util.Collections;

/**
 * トランプの山札クラス
 * 
 * @author Kohei Uehara
 * @version 1.1, 2022-07-13
 * @since JDK17
 *
 */
public class CardDeck {
	/* ---------------フィールド群---------------- */
	/** 山札 */
	private ArrayList<Card> cards;

	/* ---------------コンストラクタ----------------- */
	/**
	 * 空の山札を生成する．
	 */
	public CardDeck() {
		this.cards = new ArrayList<Card>();
	}

	/* ---------------メソッド群----------------- */
	/**
	 * フルデッキにする．
	 */
	public void createFullDeck() {
		cards.clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j <= 13; j++) {
				Card card = new Card(i, j);
				this.cards.add(card);
			}
		}
	}

	/**
	 * 空デッキにする．
	 */
	public void clear() {
		cards.clear();
	}

	/**
	 * シャッフルする．
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * 山札の一番下にカードを加える．
	 * 
	 * @param card カード
	 */
	public void addCard(Card card) {
		cards.add(card);
	}

	/**
	 * 山札の i 番目にカードを加える．
	 * 
	 * @param i    カードを加える位置
	 * @param card カード
	 */
	public void addCard(int i, Card card) {
		if (i > cards.size() + 1) {
			System.err.println("デッキのカードは" + cards.size() + "枚です．");
		} else if (i <= 0) {
			System.err.println("正の値を入力してください．");
		} else {
			cards.add(i - 1, card);
		}
	}

	/**
	 * 山札の一番上のカードを取り出す．
	 * 
	 * @return
	 */
	public Card takeCard() {
		if (cards.isEmpty()) {
			System.err.println("デッキにカードがありません．");
			return null;
		}
		return cards.remove(0);
	}

	/**
	 * 山札の i 番目のカードを取り出す．
	 * 
	 * @param i 取り出す位置
	 * @return 取り出したカード
	 */
	public Card takeCard(int i) {
		if (i > cards.size()) {
			System.err.println("デッキのカードは" + cards.size() + "枚です．");
			return null;
		} else if ( i <= 0 ) {
			System.err.println("正の値を入力してください．");
			return null;
		}
		return cards.remove(i - 1);
	}

	/**
	 * 山札の i 番目のカードを見る．
	 * 
	 * @param i 見るカードの位置
	 * @return カード
	 */
	public Card seeCard(int i) {
		if (i > cards.size()) {
			System.err.println("デッキのカードは" + cards.size() + "枚です．");
			return null;
		}
		return cards.get(i - 1);
	}

	/**
	 * 指定したカードが山札の何番目にあるか調べる．<br>
	 * カードがない場合は 0 を返す．
	 * 
	 * @param suit   絵柄
	 * @param number 数字
	 * @return カードがある位置
	 */
	public int searchCard(int suit, int number) {
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getNumber() == number && cards.get(i).getSuit() == suit) {
				return i+1;
			}
		}
		return 0;
	}

	/**
	 * 山札が空かどうかを調べる．
	 * 
	 * @return 山札が空かどうか
	 */
	public boolean isEmpty() {
		return cards.isEmpty();
	}

	/**
	 * 山札の枚数を取得する．
	 * 
	 * @return 山札の枚数
	 */
	public int size() {
		return cards.size();
	}

	/**
	 * 山札のすべてのカードを表示する．
	 */
	public void showAllCards() {
		System.out.println("------------現在の山を表示します．-----------");
		for (int i = 0; i < cards.size(); i++) {
			System.out.printf("%2d番目のカード:", (i + 1));
			System.out.println(cards.get(i));
		}
		System.out.println("------------ここまで-----------");
	}

	/* ---------------アクセサ----------------- */
	/**
	 * 山札のすべてのカードを取得する．
	 * 
	 * @return 山札のすべてのカード
	 */
	public ArrayList<Card> getAllCards() {
		return cards;
	}

}
