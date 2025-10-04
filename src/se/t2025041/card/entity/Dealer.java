package se.t2025041.card.entity;

import java.util.Random;

/**
 * キャラクター：ディーラー
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public class Dealer extends CPUsCharacter {
	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規キャラクター：ディーラーを作成
	 * 
	 * @param temper ：気分の変わりやすさ
	 */
	public Dealer(double temper) {
		super(temper);
	}

	/* ---------------プレイヤとしての台詞----------------- */
	@Override
	public String makeLineWhenBetting(PlayersSeat player) {
		int chips = player.getChips();
		int bet = player.getBet();
		int firstChips = PlayersSeat.getNUMBER_OF_FIRST_CHIPS();
		String message;
		if (chips + bet > firstChips) {
			message = String.format("乗ってきましたよ！%d枚かけます！", bet);
		} else if (chips - bet == 0) {
			message = "燃える展開ですね！全ベットします！";
		} else if (chips > firstChips / 2) {
			message = String.format("この感じ，懐かしいですね．%d枚かけます．", bet);
		} else {
			message = String.format("%d枚かけます！", bet);
		}
		return message;
	}

	@Override
	public String makeLineWhenSelecting(int n) {
		String message;
		if (n < 3) {
			message = String.format("%d枚チェンジします．", n);
		} else {
			message = String.format("よし，%d枚チェンジしましょう．", n);
		}
		return message;
	}

	@Override
	public String makeLineWhenDetarmining(PlayersSeat seat) {
		int chips = seat.getChips();
		boolean isRaise = seat.isRaise();
		String message;
		if (isRaise) {
			if (chips <= 0) {
				message = "いざ勝負！レイズします！";
			} else {
				message = "レイズします！";
			}
		} else {
			message = "フォールドします．";
		}
		return message;
	}

	@Override
	public String makeLineToFlattery() {
		return "わたしはいつも通りですよ．";
	}

	@Override
	public String makeLineToProvocation() {
		return "お気遣い感謝します．";
	}

	@Override
	public String makeLineToObservation(int hand) {
		String message;
		int n = new Random().nextInt(3);
		if (n == 1 && hand < 26) {
			message = "ちらっ（目をそらす）";
		} else {
			message = "あら，どうかされましたか？";
		}
		return message;
	}

	/* ---------------ディーラーとしての台詞----------------- */
	@Override
	public String makeLineToWelcome() {
		return "ポーカーテーブルへいらっしゃい．\n" + "　では，ゲームを始めましょう！";
	}

	@Override
	public String makeLineToProceedBettingPhase(PlayersSeat seat) {
		String name = seat.getPlayer().getName();
		int chips = seat.getChips();
		String message;
		if (chips > 0) {
			message = String.format("%s さんのチップは現在%d枚です．\n" + "　何枚のチップをかけますか？", name, chips);
		} else {
			message = String.format("%s さんのチップはもうありませんね．\n" + "　他の人だけで進めましょうか．", name, chips);
		}
		return message;
	}

	@Override
	public String makeLineToProceedWhenEveryoneCannnotBet() {
		return "あらら，全員のチップがもうないようですね．\n" + "　今回は私の勝ちです！また遊んでくださいね．";
	}

	@Override
	public String makeLineToProceedDeelingPhase() {
		return "それではカードを配ります．";
	}

	@Override
	public String makeLineToProceedSelectingPhase() {
		return "それでは，交換する札を選んでください．";
	}

	@Override
	public String makeLineToProceedDeterminingPhase() {
		return "ふふっ，強い役がそろいましたか？\n" + "　どうでしょう，私と勝負しますか？";
	}

	@Override
	public String makeLineToProceedBattlePhase() {
		return "いざ尋常に，勝負です！\n" + "　皆さん，手札を公開してください．";
	}

	@Override
	public String makeLineWhenOpeningHand() {
		return "私の手札は...";
	}

}
