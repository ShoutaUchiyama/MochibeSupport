package jp.co.mochisapo.session;

import java.io.Serializable;

/**
 * ログイン後ユーザ情報を保持するクラス
 * @author Uchiyama.Shota
 *
 */

public final class SessionUser implements Serializable {
	private final int id;
	private final String emailAddress;
	private final String name;
	private final String classCode;
	private final String jobCategoryCode;
	
	public SessionUser(int id, String emailAddress, String name, 
			String classCode, String jobCategoryCode) {
		this.id = id;
		this.emailAddress = emailAddress;
		this.name = name;
		this.classCode = classCode;
		this.jobCategoryCode = jobCategoryCode;
	}
	public int getId() { return id; }
	public String getEmailAddress() { return emailAddress; }
	public String getName() { return name; }
    public String getClassCode() { return classCode; }
    public String getJobCategoryCode() { return jobCategoryCode; }
}
