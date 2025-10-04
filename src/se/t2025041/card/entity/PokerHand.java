package se.t2025041.card.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * ポーカーの役とカードに関するユーティリティクラス
 * 
 * @author Kohei Uehara
 * @version 1.1, 2022-07-23
 * @since JDK17
 *
 */
public class PokerHand {
	/**
	 * ポーカーにおけるカードの強さを返す．
	 * 
	 * @param card ：カード
	 * @return カードの強さ
	 */
	public static int getStrength(Card card) {
		if (card.getSuit() == -2) {
			return 0;
		} else if (card.getSuit() == -1) {
			return -1;
		} else {
			return (card.getNumber() + 11) % 13 + 1;
		}
	}

	/**
	 * 手札をソートする
	 * 
	 * @param hand ：手札
	 */
	public static void sort(ArrayList<Card> hand) {
		Comparator<Card> comparator = new Comparator<Card>() {
			@Override
			public int compare(Card O1, Card O2) {
				Integer o1 = getStrength(O1);
				Integer o2 = getStrength(O2);
				Integer judgement = Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
				return -judgement;
			}
		};
		Collections.sort(hand, comparator);
	}

	/**
	 * 数字が同じカードが n 枚以上あるとき，そのカードの強さを返す．<br>
	 * (ワンペア，スリーカード等の判定に利用)
	 * 
	 * @param hand 手札
	 * @param n    重複数
	 * @return 重複するカードの強さ
	 */
	private static int hasSameCards(ArrayList<Card> hand, int n) {
		if (n > hand.size() + 1 || n < 2) {
			return -1;
		}
		ArrayList<Card> copy = new ArrayList<Card>(hand);
		sort(copy);
		if (copy.get(0).getSuit() == -1) {
			return 13;
		}
		int tail = copy.size() - 1;
		int strength_tmp = getStrength(copy.get(0));
		int count = 1;
		int head = 1;
		while (head < copy.size()) {
			if (strength_tmp == getStrength(copy.get(head))) {
				count++;
				if (count == n) {
					return strength_tmp;
				}
			} else {
				while (copy.get(tail).getSuit() == -1) {
					count++;
					tail--;
					if (count == n) {
						return strength_tmp;
					}
				}
				strength_tmp = getStrength(copy.get(head));
				count = 1;
				tail = copy.size() - 1;
			}
			head++;
		}
		return 0;
	}

	/**
	 * 数字が同じカードが n 枚以上あり， さらに異なる数字のカードのペアが存在する時， そのカードの強さを返す．<br>
	 * (ツーペア，フルハウスの判定に利用)
	 * 
	 * @param hand 手札
	 * @param n    重複数（大きい方）
	 * @return 重複するカード（重複数が大きい方）の強さ
	 */
	private static int hasPairSet(ArrayList<Card> hand, int n) {
		if (n > 3 || n < 2) {
			return -1;
		}
		int largerPairsStrength = hasSameCards(hand, n);
		if (largerPairsStrength == -1) {
			return -1;
		}
		if (largerPairsStrength == 0) {
			return 0;
		}
		ArrayList<Card> copy = new ArrayList<Card>(hand);
		sort(copy);
		if (copy.get(0).getSuit() == -1) {
			return 13;
		}
		int head = 0;
		int count = 0;
		while (count < n) {
			if (largerPairsStrength == getStrength(copy.get(head))) {
				copy.remove(head);
				count++;
				head--;
			} else if(copy.get(copy.size() - 1).getSuit() == -1) {
				copy.remove(copy.size() - 1);
				count++;
			}
			head++;
		}
		if (hasSameCards(copy, 2) > 0) {
			return largerPairsStrength;
		} else {
			return 0;
		}
	}

	/**
	 * ワンペアの判定<br>
	 * ワンペアなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isOnePair(ArrayList<Card> hand) {
		return hasSameCards(hand, 2);
	}

	/**
	 * ツーペアの判定<br>
	 * ツーペアなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isTwoPair(ArrayList<Card> hand) {
		return hasPairSet(hand, 2);
	}

	/**
	 * スリーカードの判定<br>
	 * スリーカードなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isThreeCards(ArrayList<Card> hand) {
		return hasSameCards(hand, 3);
	}

	/**
	 * ストレートの判定<br>
	 * ストレートなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isStraight(ArrayList<Card> hand) {
		ArrayList<Card> copy = new ArrayList<Card>(hand);
		sort(copy);
		if (copy.get(0).getSuit() == -1) {
			return 13;
		}
		int head;
		int tail = copy.size() - 1;
		int strength = getStrength(copy.get(0));
		head = 1;
		for (int i = 1; head <= tail && copy.get(head).getSuit() != -1; i++) {
			if (getStrength(copy.get(head)) != strength - i) {
				if (copy.get(tail).getSuit() == -1) {
					tail--;
				} else {
					return 0;
				}
			} else {
				head++;
			}
		}
		return strength;
	}

	/**
	 * フラッシュの判定<br>
	 * フラッシュなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isFlush(ArrayList<Card> hand) {
		ArrayList<Card> copy = new ArrayList<Card>(hand);
		sort(copy);
		int suit = copy.get(0).getSuit();
		if (suit == -1) {
			return 13;
		}
		for (int i = 1; i < copy.size(); i++) {
			if (copy.get(i).getSuit() != suit && copy.get(i).getSuit() != -1) {
				return 0;
			}
		}
		return getStrength(copy.get(0));
	}

	/**
	 * フルハウスの判定<br>
	 * フルハウスなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isFullHouse(ArrayList<Card> hand) {
		return hasPairSet(hand, 3);
	}

	/**
	 * フォーカードの判定<br>
	 * フォーカードなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isFourCards(ArrayList<Card> hand) {
		return hasSameCards(hand, 4);
	}

	/**
	 * ストレートフラッシュの判定<br>
	 * ストレートフラッシュなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isStraightFlush(ArrayList<Card> hand) {
		if (isFlush(hand) == 0) {
			return 0;
		} else {
			return isStraight(hand);
		}
	}

	/**
	 * ファイブカードの判定<br>
	 * ファイブカードなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return 役の強さ
	 */
	public static int isFiveCards(ArrayList<Card> hand) {
		return PokerHand.hasSameCards(hand, 5);
	}

	/**
	 * 役判定
	 * 
	 * @param hand ：手札
	 * @return 役の番号
	 */
	public static int match(ArrayList<Card> hand) {
		int strength;
		strength = isFiveCards(hand);
		if (strength > 0) {
			return strength + 104;
		}
		strength = isStraightFlush(hand);
		if (strength > 0) {
			return strength + 91;
		}
		strength = isFourCards(hand);
		if (strength > 0) {
			return strength + 78;
		}
		strength = isFullHouse(hand);
		if (strength > 0) {
			return strength + 65;
		}
		strength = isFlush(hand);
		if (strength > 0) {
			return strength + 52;
		}
		strength = isStraight(hand);
		if (strength > 0) {
			return strength + 39;
		}
		strength = isThreeCards(hand);
		if (strength > 0) {
			return strength + 26;
		}
		strength = isTwoPair(hand);
		if (strength > 0) {
			return strength + 13;
		}
		strength = isOnePair(hand);
		if (strength > 0) {
			return strength;
		}
		return 0;
	}

	/**
	 * カードの強さを文字に変換する．
	 * 
	 * @param strength カードの数字の強さ
	 * @return 対応する数字の文字表現
	 */
	private static String convert(int strength) {
		switch (strength) {
		case 10:
			return "J";
		case 11:
			return "Q";
		case 12:
			return "K";
		case 13:
			return "A";
		default:
			return "" + (strength + 1);
		}
	}

	/**
	 * 役番号から役の文字列表現を取得する．
	 * 
	 * @param handNumber ：役番号
	 * @return 役(文字列)
	 */
	public static String getString(int handNumber) {
		int p = (handNumber - 1) / 13;
		if(handNumber < 1) {
			p = -1;
		}
		int r = handNumber - p * 13;
		switch (p) {
		case 0:
			return "ワンペア (" + PokerHand.convert(r) + ")";
		case 1:
			return "ツーペア (" + PokerHand.convert(r) + ")";
		case 2:
			return "スリーカード(" + PokerHand.convert(r) + ")";
		case 3:
			return "ストレート (" + PokerHand.convert(r) + ")";
		case 4:
			return "フラッシュ (" + PokerHand.convert(r) + ")";
		case 5:
			return "フルハウス (" + PokerHand.convert(r) + ")";
		case 6:
			return "フォーカード (" + PokerHand.convert(r) + ")";
		case 7:
			if (r == 13) {
				return "ロイヤルストレートフラッシュ";
			} else {
				return "ストレートフラッシュ (" + PokerHand.convert(r) + ")";
			}
		case 8:
			return "ファイブカード (" + PokerHand.convert(r) + ")";

		default:
			return "ノーペア";
		}

	}

	/**
	 * 手札に適合する役の文字列表現を取得する．
	 * 
	 * @param hand ：手札
	 * @return 役（文字列）
	 */
	public static String getString(ArrayList<Card> hand) {
		int handNumber = match(hand);
		int p = (handNumber - 1) / 13;
		int r = handNumber - p * 13;

		switch (p) {
		case 0:
			return "ワンペア (" + PokerHand.convert(r) + ")";
		case 1:
			return "ツーペア (" + PokerHand.convert(r) + ")";
		case 2:
			return "スリーカード(" + PokerHand.convert(r) + ")";
		case 3:
			return "ストレート (" + PokerHand.convert(r) + ")";
		case 4:
			return "フラッシュ (" + PokerHand.convert(r) + ")";
		case 5:
			return "フルハウス (" + PokerHand.convert(r) + ")";
		case 6:
			return "フォーカード (" + PokerHand.convert(r) + ")";
		case 7:
			if (r == 13) {
				return "ロイヤルストレートフラッシュ";
			} else {
				return "ストレートフラッシュ (" + PokerHand.convert(r) + ")";
			}
		case 8:
			return "ファイブカード (" + PokerHand.convert(r) + ")";

		default:
			return "ノーペア";
		}

	}

	/**
	 * ワンペアの判定<br>
	 * ワンペアなら役を構成する最も強いカードの強さを返し，そうでないなら0を返す．
	 * 
	 * @param hand ：手札
	 * @return ：役の強さ
	 */
	public static void showHandsList() {
		System.out.println("--------------------------------");
		System.out.println("役");
		System.out.println("--------------------------------");
		System.out.println("ファイブカード");
		System.out.println("ロイヤルストレートフラッシュ");
		System.out.println("ストレートフラッシュ");
		System.out.println("フォーカード");
		System.out.println("フルハウス");
		System.out.println("フラッシュ");
		System.out.println("ストレート");
		System.out.println("スリーカード");
		System.out.println("ツーペア");
		System.out.println("ワンペア");
		System.out.println("ノーペア");
		System.out.println("--------------------------------");
	}
	
	/**
	 * カード交換に際する評価値を求める．
	 * 
	 * @param hand ：手札
	 * @param select ：交換するカード（桁数=手札の枚数となる二進数）
	 * @param p ：望むカードを引く主観的確率
	 * @return 評価値
	 */
	public static double evaluate(ArrayList<Card> hand, int select, double p) {
		Card joker = new Card(-1, 0);
		Card dummy = new DummyCard(-1, 0);
		int a = select;
		int count = 0;
		ArrayList<Card> copy1 = new ArrayList<Card>(hand);
		ArrayList<Card> copy2 = new ArrayList<Card>(hand);
		for (int j = 0; a != 0; j++) {
			if ((a & 1) == 1) {
				copy1.set(j, joker);
				copy2.set(j, dummy);
				count++;
			}
			a = a >>> 1;
		}
		double maxValue = (double) match(copy1);
		double minValue = (double) match(copy2);
		return minValue + (maxValue - minValue) * Math.pow(p, count);
	}

	/**
	 * 手札をみて，どのカードを交換すべきかを返す．<br>
	 * 返り値は二進数になっており，例えば<br>
	 * <br>
	 * 00011<br>
	 * <br>
	 * なら，1枚目と2枚目を交換する．
	 * 
	 * @param hand ：手札
	 * @param p    ：望むカードを引く主観的確率
	 * @return 交換すべきカード
	 */
	public static int solve(ArrayList<Card> hand, double p) {
		Card joker = new Card(-1, 0);
		Card dummy = new DummyCard(-1, 0);
		double evaluationValue;
		double maxEvaluationValue = 0;
		int maxId = 0;
		for (int i = 0; i < (int) Math.pow(2, hand.size()); i++) {
			int a = i;
			int count = 0;
			ArrayList<Card> copy1 = new ArrayList<Card>(hand);
			ArrayList<Card> copy2 = new ArrayList<Card>(hand);
			for (int j = 0; a != 0; j++) {
				if ((a & 1) == 1) {
					copy1.set(j, joker);
					copy2.set(j, dummy);
					count++;
				}
				a = a >>> 1;
			}
			double maxValue = (double) match(copy1);
			double minValue = (double) match(copy2);
			evaluationValue = minValue + (maxValue - minValue) * Math.pow(p, count);
			if (evaluationValue > maxEvaluationValue) {
				maxEvaluationValue = evaluationValue;
				maxId = i;
			}
		}
		return maxId;
	}
}
