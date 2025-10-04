package se.t2025041.card.entity;

/**
 * キャラクター：淑女
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public class Lady extends CPUsCharacter {
	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規キャラクター：淑女を作成
	 * 
	 * @param temper ：気分の変わりやすさ
	 */
	public Lady(double temper) {
		super(temper);
	}

	/* ---------------プレイヤとしての台詞---------------- */
	@Override
	public String makeLineWhenBetting(PlayersSeat player) {
		int chips = player.getChips();
		int bet = player.getBet();
		int firstChips = PlayersSeat.getNUMBER_OF_FIRST_CHIPS();
		String message;
		if (chips + bet > firstChips) {
			message = String.format("この調子でいきますよ！%d枚かけます！", bet);
		} else if (chips - bet == 0) {
			message = "こうなったら...！全ベットします！";
		} else if (chips > firstChips / 2) {
			message = String.format("まだ勝負の時ではないですね．%d枚かけます．", bet);
		} else {
			message = String.format("ちょっとまずいかも？%d枚かけます．", bet);
		}

		return message;
	}

	@Override
	public String makeLineWhenSelecting(int n) {
		String message;
		if (n == 0) {
			message = "ノーチェンジです．";
		} else if (n < 3) {
			message = String.format("%d枚チェンジです！", n);
		} else {
			message = String.format("ふーっ...%d枚チェンジ！", n);
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
				message = "よし...レイズ！！";
			} else {
				message = "レイズします！";
			}
		} else {
			message = "ここは降りましょう．";
		}
		return message;
	}

	@Override
	public String makeLineToFlattery() {
		return "余裕のつもりですか？負けませんよ．";
	}

	@Override
	public String makeLineToProvocation() {
		return "そうですね．どうやってあなたを負かすか\n" + "　考えていたところです．";
	}

	@Override
	public String makeLineToObservation(int hand) {
		String message;
		if (hand > 13) {
			message = "じーっ（ディーラーを観察している）";
		} else {
			message = "な，なにをみているんですか？";
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
			message = String.format("%s さんのチップはいま%d枚です．\n" + "　何枚のチップをかけますか？", name, chips);
		} else {
			message = String.format("%s さんのチップはもうありません！\n" + "　他の人だけでやりましょう．", name, chips);
		}
		return message;
	}

	@Override
	public String makeLineToProceedWhenEveryoneCannnotBet() {
		return "あちゃー．誰もチップを持ってないみたいですね．\n" + "　今回は私の勝ちです！さようならー";
	}

	@Override
	public String makeLineToProceedDeelingPhase() {
		return "カードを配ります．";
	}

	@Override
	public String makeLineToProceedSelectingPhase() {
		return "それでは，交換する札をえらんでください．";
	}

	@Override
	public String makeLineToProceedDeterminingPhase() {
		return "さてさて，みなさんどうですか？\n" + "　私と勝負しますか？";
	}

	@Override
	public String makeLineToProceedBattlePhase() {
		return "みさなんの命運やいかに？！\n" + "　手札を公開してください！";
	}

	@Override
	public String makeLineWhenOpeningHand() {
		return "私の手札は...";
	}
}
