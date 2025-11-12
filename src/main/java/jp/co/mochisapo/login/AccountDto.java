package jp.co.mochisapo.login;

/**
 * アカウントに関連する追加情報を保持するDTOクラス
 * @author Shota.Uchiyama
 *
 */
public class AccountDto {
	/** 認証用：メールアドレス（必須）*/
	private String emailAddress;
	/** 認証用；ハッシュ済パスワード */
	private String passwordHash;
	/** アカウントID */
	private int accountId;
	/** 検索や絞り込みに利用するキーワード */
	private String keyword;
	
	/**
	 * メールアドレスを取得
	 * @return メールアドレスを取得
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * メールアドレスを設定
	 * @return mailAddress 設定するメールアドレス
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
