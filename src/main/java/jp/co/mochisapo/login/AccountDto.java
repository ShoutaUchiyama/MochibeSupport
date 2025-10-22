package jp.co.mochisapo.login;

/**
 * アカウントに関連する追加情報を保持するDTOクラス
 * @author Shota.Uchiyama
 *
 */
public class AccountDto {
	/** 認証用：ログインID（必須）*/
	private String loginId;
	/** 認証用；ハッシュ済パスワード */
	private String passwordHash;
	/** アカウントID */
	private int accountId;
	/** 検索や絞り込みに利用するキーワード */
	private String keyword;
	
	/**
	 * ログインIDを取得
	 * @return ログインIDを取得
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * ログインIDを設定
	 * @return loginId 設定するログインID
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * ハッシュ済パスワードを取得
	 * @return passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	/**
	 * ハッシュ済パスワードを設定
	 * @return passwordHash 設定するハッシュ済パスワード
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	/**
	 * アカウントIDを取得
	 * @return アカウントID
	 */
	public int getAccountId() {
		return accountId;
	}
	/**
	 * アカウントIDを設定
	 * @param accountId 設定するアカウントID
	 */
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	/**
	 * キーワードを取得
	 * @return
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * キーワードを設定
	 * @param keyword 設定するキーワード
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
}
