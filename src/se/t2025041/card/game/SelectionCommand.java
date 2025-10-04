package se.t2025041.card.game;

import java.util.ArrayList;

import se.t2025041.card.entity.CPU;
import se.t2025041.card.entity.CardDeck;
import se.t2025041.card.entity.Keyboard;
import se.t2025041.card.entity.Player;
import se.t2025041.card.entity.PokerHand;
import se.t2025041.card.entity.User;

/**
 * 交換フェイズの処理を分担するサブクラス．
 * 
 * @author Kohei Uehara
 * @version 1.0, 2022-07-24
 * @since JDK17
 *
 */
public class SelectionCommand {
	/** 山札 */
	private CardDeck deck;
	/** ベットしたプレイヤ（0:ユーザ） */
	private ArrayList<Player> players = new ArrayList<Player>();

	/**
	 * カードの交換を行う．
	 */
	public void select() {
		for (Player player : players) {
			int a = player.select();
			for (int j = 0; a != 0; j++) {
				if ((a & 1) == 1) {
					player.changeCard(j, deck.takeCard());
				}
				a = a >>> 1;
			}
		}
	}

	/**
	 * 会話コマンドの中身の実装
	 */
	private void talk() {
		System.out.println("相手を選んでください．");
		System.out.println("----------------");
		for (int i = 1; i < players.size(); i++) {
			System.out.println(i + ". " + players.get(i).getName());
		}
		System.out.println("----------------");
		System.out.print("数字を入力：");
		int n;
		do {
			n = Keyboard.inputNumber();
			if (n < 1 || n >= players.size()) {
				System.err.println("無効なコマンド：再入力してください．");
			}
		} while (n < 1 || n >= players.size());
		System.out.println("");
		System.out.println("内容を選んでください．");
		System.out.println("----------------");
		System.out.println("1. おだてる");
		System.out.println("2. 挑発する");
		System.out.println("----------------");

		int c;
		do {
			System.out.print("数字を入力：");
			c = Keyboard.inputNumber();
			if (c < 1 || c > 2) {
				System.err.println("無効なコマンド：再入力してください．");
			}
		} while (c < 1 || c > 2);
		System.out.println("");
		if (c == 1) {
			players.get(0).createMessageBox("今日は調子がいいですね．");
			CPU cpu = (CPU) players.get(n);
			cpu.reactToFlattery();
		} else {
			players.get(0).createMessageBox("お悩みのようですね．相談に乗りましょうか？");
			CPU cpu = (CPU) players.get(n);
			cpu.reactToProvocation();
		}
	}

	/**
	 * 役一覧を表示する．
	 */
	private void showHandList() {
		System.out.println("役の一覧です．上に行くほど強くなります．");
		PokerHand.showHandsList();
		Keyboard.sleep();
	}

	/**
	 * ユーザの手札を表示する用
	 */
	private void showHand() {
		System.out.println("手札を表示します．");
		players.get(0).showHand();
		Keyboard.sleep();
	}

	/**
	 * メニューを表示し，コマンドを受け付ける
	 */
	public void showMenu() {
		int command;
		boolean continueFrag = true;
		do {
			System.out.println("交換するカードを決めましょう．コマンドを入力してください．");
			System.out.print("[1:手札，2:会話，3:役一覧, 0:交換]：");
			command = Keyboard.inputNumber();
			System.out.println("");
			switch (command) {
			case 0:
				System.out.println("交換するカードを決めると，次のフェイズに進みます．");
				System.out.println("交換するカードを決めますか？");
				System.out.println("----------------");
				System.out.println("1.決める");
				System.out.println("2.考え直す");
				System.out.println("----------------");

				int c;
				do {
					System.out.print("数字を入力：");
					c = Keyboard.inputNumber();
					if (c < 1 || c > 2) {
						System.err.println("無効なコマンド：再入力してください．");
					}
				} while (c < 1 || c > 2);
				System.out.println("");
				if (c == 1) {
					select();
					continueFrag = false;
				} else {
					System.out.println("よく考えて選んでください．");
				}
				break;
			case 1:
				showHand();
				break;
			case 2:
				talk();
				break;
			case 3:
				showHandList();
				break;
			default:
				System.out.println("無効なコマンド：再入力してください．");
				break;
			}
			System.out.println("");
		} while (continueFrag);
		players.clear();
	}

	/**
	 * デッキのセット
	 * 
	 * @param deck ：デッキ
	 */
	public void setDeck(CardDeck deck) {
		this.deck = deck;
	}

	/**
	 * 配列の 0 番目にユーザを設定するために，アクセス制限
	 * 
	 * @param player ：プレイヤ
	 */
	public void addPlayer(Player player) {
		if (players.isEmpty()) {
			if (player instanceof User) {
				players.add(player);
			} else {
				System.out.println("最初にユーザを設定してください．");
			}
		} else {
			if (player instanceof User) {
				System.out.println("ユーザは設定済みです．");
			} else {
				players.add(player);
			}
		}
	}
	
	/**
	 * セットされているプレイヤを取得する．
	 * 
	 * @return セットされたプレイヤリスト
	 */
	public ArrayList<Player> getPlayers(){
		return players;
	}
}
