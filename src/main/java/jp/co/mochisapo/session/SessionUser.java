package jp.co.mochisapo.session;

import java.io.Serializable;

import jp.co.mochisapo.login.AccountEntity.Role;

/**
 * ログイン後ユーザ情報を保持するクラス
 * @author Uchiyama.Shota
 *
 */

public final class SessionUser implements Serializable {
	private final int id;
	private final String loginId;
	private final String name;
	private final Role role;
	private final String classCode;
	private final String jobCategoryCode;
	
	public SessionUser(int id, String loginId, String name, Role role, 
			String classCode, String jobCategoryCode) {
		this.id = id;
		this.loginId = loginId;
		this.name = name;
		this.role = role;
		this.classCode = classCode;
		this.jobCategoryCode = jobCategoryCode;
	}
	public int getId() { return id; }
	public String getLoginId() { return loginId; }
	public String getName() { return name; }
	public Role getRole() { return role; }
    public String getClassCode() { return classCode; }
    public String getJobCategoryCode() { return jobCategoryCode; }
}
