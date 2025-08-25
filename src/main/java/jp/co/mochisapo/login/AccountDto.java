package jp.co.mochisapo.login;

/**
 * アカウントに関連する追加情報を保持するDTOクラス
 * @author Shota.Uchiyama
 *
 */
public class AccountDto extends AccountEntity {
	/** アカウントID */
	private int accountId;
	/** 検索や絞り込みに利用するキーワード */
	private String keyword;
	
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
