package se.t2025041.card.entity;

/**
 * キャラクター：紳士
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public class Gentleman extends CPUsCharacter {
	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規キャラクター：紳士を作成
	 * 
	 * @param temper ：気分の変わりやすさ
	 */
	public Gentleman(double temper) {
		super(temper);
	}

	/* ---------------プレイヤとしての台詞----------------- */
	@Override
	public String makeLineWhenBetting(PlayersSeat seat) {
		int chips = seat.getChips();
		int bet = seat.getBet();
		int firstChips = PlayersSeat.getNUMBER_OF_FIRST_CHIPS();
		String message;
		if (chips + bet > firstChips) {
			message = String.format("運は私に向いている！%d枚かけよう．", bet);
		} else if (chips - bet == 0) {
			message = "かくなる上は...全ベットだ！！";
		} else if (chips > firstChips / 2) {
			message = String.format("よし，%d枚かけよう．", bet);
		} else {
			message = String.format("まだチャンスはある！%d枚かけよう．", bet);
		}

		return message;
	}

	@Override
	public String makeLineWhenSelecting(int n) {
		String message;
		if (n == 0) {
			message = "ノーチェンジだ．";
		} else if (n < 3) {
			message = String.format("%d枚チェンジしよう．", n);
		} else {
			message = String.format("思い切って%d枚チェンジだ！", n);
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
				message = "もとよりレイズしかないんだ！";
			} else {
				message = "よし，レイズだ！";
			}
		} else {
			message = "ここはフォールドしよう．";
		}
		return message;
	}

	@Override
	public String makeLineToFlattery() {
		return "いやいやそんな．お互い頑張りましょう．";
	}

	@Override
	public String makeLineToProvocation() {
		return "む，顔に出ていましたか．失敬．";
	}

	@Override
	public String makeLineToObservation(int hand) {
		String message;
		if (hand > 13) {
			message = "さて，どうしたものかな...（ブツブツ）";
		} else {
			message = "むむむ，悩ましい...（ボソボソ）";
		}
		return message;
	}

	/* ---------------ディーラーとしての台詞----------------- */
	@Override
	public String makeLineToWelcome() {
		return "ようこそポーカーテーブルへ．\n" + "　では，ゲームを始めよう．";
	}

	@Override
	public String makeLineToProceedBettingPhase(PlayersSeat seat) {
		String name = seat.getPlayer().getName();
		int chips = seat.getChips();
		String message;
		if (chips > 0) {
			message = String.format("%s くんのチップはいま%d枚だよ．\n" + "　何枚のチップをかけるのかね？", name, chips);
		} else {
			message = String.format("%s くんはもうチップを持っていないようだね．\n" + "　他の人だけで進めよう．", name, chips);
		}
		return message;
	}

	@Override
	public String makeLineToProceedWhenEveryoneCannnotBet() {
		return "おっと，全員のチップがもうないようだ．\n" + "　今回は私の勝ちだ．また会おう．";
	}

	@Override
	public String makeLineToProceedDeelingPhase() {
		return "ではカードを配るとしよう．";
	}

	@Override
	public String makeLineToProceedSelectingPhase() {
		return "交換する札を選びたまえ";
	}

	@Override
	public String makeLineToProceedDeterminingPhase() {
		return "どうだろう，塩梅は．\n" + "　さて，私と勝負しますかな？";
	}

	@Override
	public String makeLineToProceedBattlePhase() {
		return "さあ，勝負の時です．" + "　諸君，手札を公開したまえ．";
	}

	@Override
	public String makeLineWhenOpeningHand() {
		return "私の手札は...";
	}

}
