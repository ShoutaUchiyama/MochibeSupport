package jp.co.mochisapo.login;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * アカウント情報を表すエンティティクラス
 * @author Shota.Uchiyama
 *
 */
public class AccountEntity {
	
	/** id */
	private int id;
	/** ログインに使用するメールアドレス */
	private String emailAddress;
	/** クラス（組織や学級など）を表すコード値 */
	private String classCode;
	/** 氏名 */
	private String name;
	/** 職種区分を表すコード値 */
	private String jobCategoryCode;
	/** 生年月日 */
	private LocalDate birthday;
	
	/** 
	 * IDを取得
	 * @return ID
	 */
	public int getId() {
		return id;
	}
	/**
	 * IDを設定
	 * @param id 設定するID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * メールアドレスを取得
	 * @return メールアドレス
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * メールアドレスを設定
	 * @param loginId 設定するメールアドレス
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * クラスコードを取得
	 * @return クラスコード
	 */
	public String getClassCode() {
		return classCode;
	}
	/**
	 * クラスコードを設定
	 * @param classCode 設定するクラスコード
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	/**
	 * 氏名を取得
	 * @return 氏名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 氏名を設定
	 * @param name 設定する氏名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 職業区分コードを取得
	 * @return 職業区分コード
	 */
	public String getJobCategoryCode() {
		return jobCategoryCode;
	}
	/**
	 * 職業区分コードを設定
	 * @param jobCategoryCode 設定する職業区分コード
	 */
	public void setJobCategoryCode(String jobCategoryCode) {
		this.jobCategoryCode = jobCategoryCode;
	}
	/**
	 * 生年月日を取得
	 * @return
	 */
	public LocalDate getBirthday() {
		return birthday;
	}
	/**
	 * 生年月日を設定
	 * @param birthday 設定する生年月日
	 */
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	@Override
	public String toString() {
		// 機微情報は含めない。loginIdは一部マスク
		String maskedEmailAddress = emailAddress == null ? null :
			(emailAddress.length() <= 3 ? "***" : emailAddress.substring(0, 3) + "***");
		
		return new StringJoiner(", ", getClass().getName() + "{", "}")
				.add("id=" + id)
				.add("emailAddress=" + maskedEmailAddress)
				.add("name=" + name)
				.toString();
	}
}
