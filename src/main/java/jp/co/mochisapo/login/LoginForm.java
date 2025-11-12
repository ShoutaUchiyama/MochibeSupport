package jp.co.mochisapo.login;

/**
 * ユーザのログイン情報を保持するクラス
 * @author Shota.Uchiyama
 *
 */
public class LoginForm {
	/** メールアドレス */
	private String emailAddress;
	/** パスワード */
	private String password;
	
	/**
	 * メールアドレスを取得
	 * @return emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * メールアドレスを設定
	 * @param emailAddress 設定するメールアドレス
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * パスワードを取得
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * パスワードを設定
	 * @param password 設定するパスワード
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
