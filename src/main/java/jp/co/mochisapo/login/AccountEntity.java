package jp.co.mochisapo.login;

import java.time.LocalDate;
import java.util.StringJoiner;

/**
 * アカウント情報を表すエンティティクラス
 * @author Shota.Uchiyama
 *
 */
public class AccountEntity {
	
	public enum Role { STUDENT, STAFF, ADMIN }
	
	/** role */
	private Role role;
	/** id */
	private int id;
	/** ログインに使用するID */
	private String loginId;
	/** クラス（組織や学級など）を表すコード値 */
	private String classCode;
	/** 氏名 */
	private String name;
	/** 職種区分を表すコード値 */
	private String jobCategoryCode;
	/** 生年月日 */
	private LocalDate birthday;
	
	/**
	 * Roleを取得
	 * @return role
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * Roleを設定
	 * @return role 設定するrole
	 */
	public void setRole(Role role) {
		this.role = role;
	}
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
	 * ログインIDを取得
	 * @return ログインID
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
		String maskedLogin = loginId == null ? null :
			(loginId.length() <= 3 ? "***" : loginId.substring(0, 3) + "***");
		
		return new StringJoiner(", ", getClass().getName() + "{", "}")
				.add("id=" + id)
				.add("loginId=" + maskedLogin)
				.add("name=" + name)
				.add("role=" + role)
				.toString();
	}
}
