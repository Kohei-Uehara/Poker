package se.t2025041.card.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 成績記録用スコアボードクラス．
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-23
 * @since JDK17
 *
 */
public class ScoreBoard {
	/* ---------------フィールド群---------------- */
	/** 名前 */
	private String name;
	/** スコア */
	private double score;
	/** 所有チップ */
	private int chips;
	/** 勝率 */
	private double winRate;
	/** 出した中で最も強い役 */
	private int strongestHand;
	/** 勝った回数 */
	private int numberOfWins;
	/** 対戦回数 */
	private int numberOfBattles;

	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規スコアボードを作成する．
	 * 
	 * @param name ：名前
	 */
	public ScoreBoard(String name) {
		this.name = name;
		this.numberOfBattles = 0;
		this.numberOfWins = 0;
	}

	/* ---------------メソッド群----------------- */
	/**
	 * スコアを更新する．
	 * 
	 * @param isFinallyWinner ：優勝したかどうか
	 */
	public void updateScore(boolean isFinallyWinner) {
		score = winRate * (chips - PlayersSeat.getNUMBER_OF_FIRST_CHIPS()) * 100 * strongestHand;
		if (isFinallyWinner) {
			score *= 2;
		}
	}

	/**
	 * チップの枚数を更新する．
	 * 
	 * @param chips ：チップの枚数
	 */
	public void updateChips(int chips) {
		this.chips = chips;
	}

	/**
	 * 勝率を更新する．
	 * 
	 * @param isWinner ：勝ったかどうか
	 */
	public void updateWinRate(boolean isWinner) {
		numberOfBattles++;
		if (isWinner) {
			numberOfWins++;
		}
		this.winRate = (double)this.numberOfWins / this.numberOfBattles;
	}

	/**
	 * 出した中で最も強い役を更新する．
	 * 
	 * @param hand ：出した役
	 */
	public void updateStrongestHand(int hand) {
		if (this.strongestHand < hand) {
			this.strongestHand = hand;
		}
	}

	/**
	 * すべての記録を一括で更新する．
	 * 
	 * @param chips ：チップの枚数
	 * @param hand ：出した役
	 * @param isWinner ：勝者かどうか
	 * @param isFinallyWinner ：優勝者かどうか
	 */
	public void update(int chips, int hand, boolean isWinner, boolean isFinallyWinner) {
		this.updateChips(chips);
		this.updateStrongestHand(hand);
		this.updateWinRate(isWinner);
		this.updateScore(isFinallyWinner);
	}

	/**
	 * スコアを表示する．
	 */
	public void showScore() {
		System.out.println("--------------------------------");
		System.out.println(name + " のスコア: " + score);
		System.out.println("--------------------------------");
		System.out.println("チップ: " + chips);
		System.out.println("勝率: " + winRate);
		System.out.println("出した一番強い役: " + PokerHand.getString(strongestHand));
		System.out.println("--------------------------------");
		Keyboard.sleep();
	}

	@Override
	/**
	 * スコアの文字表現（*.csv 出力用）
	 */
	public String toString() {
		Date d = new Date();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return String.format("%s,%s,%f,%d,%f,%d", s.format(d), name, score, chips, winRate, strongestHand);
	}

	/* ---------------アクセサ----------------- */
	/**
	 * 名前を取得する
	 * 
	 * @return 名前
	 */
	public String getName() {
		return name;
	}

	/**
	 * スコアを取得する．
	 * 
	 * @return スコア
	 */
	public double getScore() {
		return score;
	}

	/**
	 * 所有するチップを取得する．
	 * 
	 * @return 所有チップ
	 */
	public int getChips() {
		return chips;
	}

	/**
	 * 所有するチップをセットする．<br>
	 * updateChips(int chips) と中身は同じ
	 * 
	 * @param chips ：所有チップ
	 */
	public void setChips(int chips) {
		this.chips = chips;
	}

	/**
	 * 勝率を取得する．
	 * 
	 * @return 勝率
	 */
	public double getWinRate() {
		return winRate;
	}

	/**
	 * 出した一番強い役を取得する．
	 * 
	 * @return 出した一番強い役
	 */
	public int getStrongestHand() {
		return strongestHand;
	}

	/**
	 * 出した一番強い役をセットする（テスト用）．
	 * 
	 * @param strongestHand 出した一番強い役
	 */
	public void setStrongestHand(int strongestHand) {
		this.strongestHand = strongestHand;
	}

	/**
	 * 勝った回数を取得する．
	 * 
	 * @return 勝った回数
	 */
	public int getNumberOfWins() {
		return numberOfWins;
	}

	/**
	 * 勝った回数をセットする．
	 * 
	 * @param numberOfWins ：勝った回数
	 */
	public void setNumberOfWins(int numberOfWins) {
		this.numberOfWins = numberOfWins;
	}

	/**
	 * 戦った回数を取得する．
	 * 
	 * @return 戦った回数
	 */
	public int getNumberOfBattles() {
		return numberOfBattles;
	}

	/**
	 * 戦った回数をセットする．
	 * 
	 * @param numberOfBattles ：戦った回数
	 */
	public void setNumberOfBattles(int numberOfBattles) {
		this.numberOfBattles = numberOfBattles;
	}
}
