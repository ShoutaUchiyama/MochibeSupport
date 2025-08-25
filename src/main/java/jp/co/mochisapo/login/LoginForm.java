package jp.co.mochisapo.login;

/**
 * ユーザのログイン情報を保持するクラス
 * @author Shota.Uchiyama
 *
 */
public class LoginForm {
	/** ログインID */
	private String loginId;
	/** パスワード */
	private String password;
	
	/**
	 * ログインIDを取得
	 * @return loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * ログインIDを設定
	 * @param loginId 設定するログインID
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
