package se.t2025041.card.game;

import java.util.ArrayList;

import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.Card;
import se.t2025041.card.entity.CardDeck;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.PlayersSeat;
import se.t2025041.card.entity.PokerHand;
import se.t2025041.card.entity.User;

/**
 * ポーカーテーブルクラス
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-24
 * @since JDK17
 */
public class PokerTable {
	/* ---------------フィールド群---------------- */
	/** 山札 */
	private CardDeck deck;
	/** 席（0:ユーザ） */
	private ArrayList<PlayersSeat> seats;
	/** ディーラ */
	private CPU dealer;
	/** 手札の枚数 */
	private static int HAND_SIZE = 5;
	/** ターン数 */
	private static int NUMBER_OF_TURNS = 5;
	/** 交換カード選択コマンド */
	private SelectionCommand selectionCommand;
	/** レイズ・フォールド選択コマンド */
	private DeterminationCommand determinationCommand;

	/* ---------------コンストラクタ----------------- */
	/**
	 * 新規ポーカーテーブルを生成する．
	 */
	public PokerTable() {
		deck = new CardDeck();
		seats = new ArrayList<PlayersSeat>();
		selectionCommand = new SelectionCommand();
		determinationCommand = new DeterminationCommand();
	}

	/* ---------------メソッド群----------------- */
	/**
	 * ユーザのチップが 0 かどうかを判定する．
	 * 
	 * @return ユーザのチップが 0 かどうか
	 */
	private boolean isUserPlayable() {
		return seats.get(0).getBet() > 0 ? true : false;
	}

	/**
	 * ベットフェイズを開始する．
	 * 
	 * @return 全員のチップが 0 になっていないか
	 */
	private boolean startBettingPhase() {
		System.out.println("*ベットフェイズ");
		boolean noPlayableSeats = true;
		for (PlayersSeat seat : seats) {
			if (seat.getChips() > 0) {
				if (seat.getPlayer() instanceof User) {
					dealer.proceedBettingPhase(seat);
				}
				seat.bet();
				noPlayableSeats = false;
			} else {
				dealer.proceedBettingPhase(seat);
			}
		}
		if (noPlayableSeats) {
			dealer.proceedWhenEveryoneCannotBet();
		} else {
			System.out.println("--------------------------------");
			System.out.println("かけられたチップ");
			System.out.println("--------------------------------");
			for (PlayersSeat seat : seats) {
				String bet = seat.getBet() > 0 ? seat.getBet() + "" : "--";
				System.out.printf("%s\t\t : %2s 枚\n", seat.getPlayer().getName(), bet);
			}
			System.out.println("--------------------------------");
			Keyboard.sleep();
		}
		return noPlayableSeats;
	}

	/**
	 * ディールフェイズを開始する<br>
	 * ディーラはカードを配る．
	 */
	private void startDealingPhase() {
		System.out.println("*ディールフェイズ");
		dealer.proceedDealingPhase();
		deck.createFullDeck();
		deck.addCard(new Card(-1, 0));
		deck.shuffle();

		for (PlayersSeat seat : seats) {
			if (seat.getBet() > 0) {
				seat.getPlayer().clearHand();
				for (int i = 0; i < HAND_SIZE; i++) {
					seat.getPlayer().addCard(deck.takeCard());
				}
			}
		}

		dealer.clearHand();
		for (int i = 0; i < HAND_SIZE; i++) {
			dealer.addCard(deck.takeCard());
		}

		System.out.println("全員に手札が " + HAND_SIZE + " 枚配られました．");
		Keyboard.sleep();

		if (isUserPlayable()) {
			seats.get(0).getPlayer().showHand();
		}
	}

	/**
	 * 交換フェイズを開始する．<br>
	 * カードを選んで交換する．
	 */
	private void startSelectingPhase() {
		System.out.println("*交換フェイズ");
		dealer.proceedSelectingPhase();
		if (isUserPlayable()) {
			for (PlayersSeat seat : seats) {
				if (seat.getBet() > 0) {
					selectionCommand.addPlayer(seat.getPlayer());
				}
			}
			selectionCommand.addPlayer(dealer);
			selectionCommand.setDeck(deck);
			selectionCommand.showMenu();

			System.out.println("交換後の手札");
			seats.get(0).getPlayer().showHand();

		} else {
			for (int i = 1; i < seats.size(); i++) {
				int a = seats.get(i).getPlayer().select();
				for (int j = 0; a != 0; j++) {
					if ((a & 1) == 1) {
						seats.get(i).getPlayer().changeCard(j, deck.takeCard());
					}
					a = a >>> 1;
				}
			}
			int a = dealer.select();
			for (int j = 0; a != 0; j++) {
				if ((a & 1) == 1) {
					dealer.changeCard(j, deck.takeCard());
				}
				a = a >>> 1;
			}
		}
	}

	/**
	 * 決断フェイズを開始する．<br>
	 * レイズ・フォールドを選択する．
	 */
	private void startDetarminingPhase() {
		System.out.println("*決断フェイズ");
		dealer.proceedDeterminingPhase();
		for (PlayersSeat seat : seats) {
			seat.resetIsRaise();
		}
		if (isUserPlayable()) {
			for (PlayersSeat seat : seats) {
				if (seat.getBet() > 0) {
					determinationCommand.addPlayer(seat.getPlayer());
				}
			}
			determinationCommand.addPlayer(dealer);
			determinationCommand.showMenu(seats);
		} else {
			determinationCommand.determin(seats);
		}
	}

	/**
	 * 勝負フェイズを開始する．
	 */
	private void startBattlePhase() {
		System.out.println("*勝負フェイズ");
		dealer.proceedBattlePhase();
		boolean noPlayersAreRaise = true;
		for (PlayersSeat seat : seats) {
			if (seat.isRaise()) {
				noPlayersAreRaise = false;
				seat.getPlayer().showHand();
				int hand = seat.getPlayer().getHandStrength();
				System.out.print(PokerHand.getString(hand));
				if (hand > 65) {
					System.out.println("！！");
				} else if (hand > 39) {
					System.out.println("！");
				} else {
					System.out.println("");
				}
				Keyboard.sleep();
			}
		}
		if (noPlayersAreRaise) {
			System.out.println("なんと，全員がフォールドしたようです．");
			System.out.println("一応，" + dealer.getName() + "さんの手も見ておきましょう．");
		}
		dealer.speakWhenOpeningHand();
		dealer.showHand();
		int hand = dealer.getHandStrength();
		System.out.print(PokerHand.getString(hand));
		if (hand > 65) {
			System.out.println("！！");
		} else if (hand > 39) {
			System.out.println("！");
		} else {
			System.out.println("");
		}
		for (PlayersSeat seat : seats) {
			System.out.println("");
			if (seat.isRaise()) {
				if (seat.getPlayer().getHandStrength() > hand) {
					seat.win();
				} else if (seat.getPlayer().getHandStrength() < hand) {
					seat.lose();
				} else {
					seat.draw();
				}
			}
		}
	}

	/**
	 * 全員のスコアを表示する．
	 */
	private void showScore() {
		System.out.println("*スコア");
		for (PlayersSeat seat : seats) {
			seat.getScore().showScore();
		}
	}

	/**
	 * 最終的な勝者を表示し，後処理を行う．
	 */
	private void showWinner() {
		int maxChips = 0;
		ArrayList<PlayersSeat> winner = new ArrayList<PlayersSeat>();
		for (int i = 0; i < seats.size(); i++) {
			if (seats.get(i).getChips() > maxChips) {
				winner.clear();
				winner.add(seats.get(i));
				maxChips = seats.get(i).getChips();
			} else if (seats.get(i).getChips() == maxChips) {
				winner.add(seats.get(i));
			}
		}
		System.out.print("勝者：");
		for (PlayersSeat seat : winner) {
			System.out.print(seat.getPlayer().getName() + " ");
			seat.getScore().updateScore(true);
		}
		System.out.println("");
	}

	/**
	 * ゲームを始める．
	 */
	public void startGame() {
		System.out.println("□ポーカーを始めます");
		System.out.println("全員にチップが " + PlayersSeat.getNUMBER_OF_FIRST_CHIPS() + " 枚配られました．");
		System.out.println("最後に持っているチップが最も多い人が勝者になります．");
		Keyboard.sleep();
		dealer.welcome();
		for (int i = 1; i <= NUMBER_OF_TURNS; i++) {
			System.out.println("----------------ターン " + i + "/" + NUMBER_OF_TURNS + " ----------------");
			if (startBettingPhase()) {
				showScore();
				seats.clear();
				return;
			}
			startDealingPhase();
			startSelectingPhase();
			startDetarminingPhase();
			startBattlePhase();
			Keyboard.sleep();
			showScore();
		}
		System.out.println("すべてのターンが終了しました．");
		showWinner();
		seats.clear();
		Keyboard.sleep();
	}

	/**
	 * ディーラーをセットする．
	 * 
	 * @param dealer ：ディーラー（CPU）
	 */
	public void setDealer(CPU dealer) {
		this.dealer = dealer;
	}

	/**
	 * 席を追加する．
	 * 
	 * @param seat ：席
	 */
	public void addSeats(PlayersSeat seat) {
		if (seats.isEmpty()) {
			if (seat.getPlayer() instanceof User) {
				this.seats.add(seat);
			} else {
				System.out.println("最初にユーザの席を設定してください．");
			}
		} else {
			if (seat.getPlayer() instanceof User) {
				System.out.println("ユーザの席は設定済みです．");
			} else {
				seats.add(seat);
			}
		}
	}

	/**
	 * ゲームにおける手札の枚数を取得する．
	 * 
	 * @return 手札の枚数
	 */
	public static int getHAND_SIZE() {
		return HAND_SIZE;
	}

	/**
	 * ゲームでの手札の枚数を設定する．デフォルトでは 5 に設定．<br>
	 * それ以外の値でのデバッグは行っていないので，変更は非推奨
	 * 
	 * @param hAND_SIZE ：手札の枚数
	 */
	public static void setHAND_SIZE(int hAND_SIZE) {
		HAND_SIZE = hAND_SIZE;
	}

	/**
	 * ゲームのターン数を取得する．
	 * 
	 * @return nUMBER_OF_TURNS
	 */
	public static int getNUMBER_OF_TURNS() {
		return NUMBER_OF_TURNS;
	}

	/**
	 * ゲームのターン数を設定する．デフォルトでは 5 に設定
	 * 
	 * @param nUMBER_OF_TURNS ：ターン数
	 */
	public static void setNUMBER_OF_TURNS(int nUMBER_OF_TURNS) {
		NUMBER_OF_TURNS = nUMBER_OF_TURNS;
	}
	
	/**
	 * ゲームの座席のパラメータを取得する．
	 * 
	 * @return 座席
	 */
	public ArrayList<PlayersSeat> getSeats(){
		return seats;
	}

}
