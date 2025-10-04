package se.t2025041.card.entity;

/**
 * トランプカードクラス．
 * 
 * @author Kohei Uehara
 * @version 1.1, 2022-07-12
 * @since JDK17
 */
public class Card {
	/* ---------------フィールド群---------------- */
	/** 絵柄 */
	private int suit;
	/** 数字 */
	private int number;

	/* ---------------コンストラクタ----------------- */
	/**
	 * カードを生成する．<br>
	 * 絵柄：0-スペード，1-ダイヤ，2-ハート，3-クラブ<br>
	 * 数字：1~13，1-A，11-J，12-Q，13-K<br>
	 * ジョーカーは 絵柄-1，数字 0 とする．
	 * 
	 * @param suit   絵柄
	 * @param number 数字
	 */
	public Card(int suit, int number) {
		if (suit < -1 || suit > 3 || number < 0 || number > 13) {
			System.err.println("無効な値です．ジョーカーとして扱います．");
			this.suit = -1;
			this.number = 0;
		} else {
			this.suit = suit;
			this.number = number;
		}
	}

	/* ---------------メソッド群----------------- */
	/**
	 * カードの整数表現を取得する．<br>
	 * 整数表現:<br>
	 * [スペード1~13, ダイヤ1~13, ハート1~13, クラブ1~13] を<br>
	 * この順番に 0~51 の通し番号をつけたもの．<br>
	 * ジョーカーは -1．
	 * 
	 * @return 整数表現
	 */
	public int toIndex() {
		return suit == -1 ? -1 : suit * 13 + number - 1;
	}

	/**
	 * カード情報を文字列表現に変換する．<br>
	 * 
	 * @return 文字列表現．
	 */
	public String toString() {
		String s;
		switch (suit) {
		case 0:
			s = "スペード";
			break;
		case 1:
			s = "ダイヤ";
			break;
		case 2:
			s = "ハート";
			break;
		case 3:
			s = "クラブ";
			break;
		default:
			return "ジョーカー";
		}
		
		String n;
		switch (number) {
		case 1:
			n = "A";
			break;
		case 11:
			n = "J";
			break;
		case 12:
			n = "Q";
			break;
		case 13:
			n = "K";
			break;
		default:
			n = String.valueOf(number);
			break;
		}
		return s + n;

	}

	/**
	 * カード情報を文字列として画面に出力する．
	 */
	public void show() {
		System.out.print(this);
	}

	/**
	 * 入力された絵柄，数字に対応するカードの整数表現を返すメソッド．
	 * 
	 * @param suit   絵柄
	 * @param number 数字
	 * @return       整数表現
	 */
	public static int getIndex(int suit, int number) {
		if (suit < -1 || suit > 3 || number < 0 || number > 13) {
			System.err.println("無効な値です．ジョーカーとして扱います．");
			return -1;
		} else {
			return suit == -1 ? -1 : suit * 13 + number - 1;
		}
	}

	/**
	 * 入力された絵柄，数字に対応するカードの文字列表現を返すメソッド．
	 * 
	 * @param suit   絵柄
	 * @param number 数字
	 * @return       文字列表現
	 */
	public static String getString(int suit, int number) {
		if (suit < -1 || suit > 3 || number < 0 || number > 13) {
			System.err.println("無効な値です．ジョーカーとして扱います．");
			return "ジョーカー";
		} else {
			String s;
			switch (suit) {
			case 0:
				s = "スペード";
				break;
			case 1:
				s = "ダイヤ";
				break;
			case 2:
				s = "ハート";
				break;
			case 3:
				s = "クラブ";
				break;
			default:
				return "ジョーカー";
			}

			String n;
			switch (number) {
			case 1:
				n = "A";
				break;
			case 11:
				n = "J";
				break;
			case 12:
				n = "Q";
				break;
			case 13:
				n = "K";
				break;
			default:
				n = String.valueOf(number);
				break;
			}
			return s + n;

		}
	}

	/* ---------------アクセサ----------------- */
	/**
	 * 絵柄を取得する．
	 * 
	 * @return 絵柄
	 */
	public int getSuit() {
		return suit;
	}

	/**
	 * 数字を取得する．
	 * 
	 * @return 数字
	 */
	public int getNumber() {
		return number;
	}

}
