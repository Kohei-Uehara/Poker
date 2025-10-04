package se.t2025041.card.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * キーボード入出力用ユーティリティクラス
 * 
 * @author Kohei Uehara
 * @version 1.2, 2022-07-24
 * @since JDK17
 */
public class Keyboard {
	/** テスト用の変数 */
	private static Boolean IS_TEST = false;
	private static Random random;

	/**
	 * キーボード入力から整数値を取得する． フォーマット例外時は -1 を返す
	 * 
	 * @return 入力された整数
	 */
	public static int inputNumber() {
		if(IS_TEST) {
			return random.nextInt(10) - 3;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int num;
		try {
			num = Integer.parseInt(br.readLine());
		} catch (NumberFormatException e) {
			System.err.println("フォーマット例外: -1 を入力します．");
			num = -1;
		} catch (IOException e) {
			System.err.println("入出力例外: -1 を入力します．");
			num = -1;
		}
		return num;
	}

	/**
	 * キーボード入力から文字列を取得する．
	 * 
	 * @return 入力された文字列
	 */
	public static String inputString() {
		if(IS_TEST) {
			return "TEST";
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try {
			str = br.readLine();
		} catch (NumberFormatException e) {
			System.err.println("フォーマット例外: \"Error\" を入力します．");
			str = "Error";
		} catch (IOException e) {
			System.err.println("入出力例外: \"Error\" を入力します．");
			str = "Error";
		}
		return str;
	}

	/**
	 * Enterキーが押されるまで待機
	 */
	public static void sleep() {
		if (IS_TEST) {
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.print("\nEnterで進む\t");
			br.readLine();
			System.out.println("");
		} catch (IOException e) {
			System.out.println("入出力例外です．");
		}
	}

	/**
	 * テスト用入力に切り替える．<br>
	 * <br>
	 * inputNumber() : -3から6までの乱数<br>
	 * inputString() : "TEST" と入力
	 * 
	 * @param シード値
	 */
	public static void inputForTest(int seed) {
		IS_TEST = true;
		random = new Random(seed);
	}
}
