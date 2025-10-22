package jp.co.mochisapo.user.profile.web.form;

import java.io.Serializable;
import java.util.List;

import jp.co.mochisapo.user.profile.domain.Gender;

/**
 * 初回ログイン時の入力したユーザー情報詳細を格納する
 * @author Shota.Uchiyama
 *
 */
public final class ProfileForm implements Serializable {
    private int id;
    private String loginId;
    
    // 誕生日（画面バインド用に文字列3分割）
    private String birthdayYear;
    private String birthdayMonth;
    private String birthdayDay;

    private String gender;
    private String telephoneNumber;
    private String mailAddress;
    private String postCode;
    private String prefectures;
    private String municipalities;
    private String address;
    private String apartment;
    
    private List<CodeLabel> genderList;

    /** 現在のパスワード（初回だけ省略可）*/
    private char[] currentPassword;
	/** 新しいパスワード */
    private char[] newPassword;
    /** 確認用 */
    private char[] newPasswordConfirm;
    
    /**
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id セットする id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return loginId
	 */
	public String getLoginId() {
		return loginId;
	}
	/**
	 * @param loginId セットする loginId
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	/**
	 * @return birthdayYear
	 */
	public String getBirthdayYear() {
		return birthdayYear;
	}
	/**
	 * @param birthdayYear セットする birthdayYear
	 */
	public void setBirthdayYear(String birthdayYear) {
		this.birthdayYear = birthdayYear;
	}
	/**
	 * @return birthdayMonth
	 */
	public String getBirthdayMonth() {
		return birthdayMonth;
	}
	/**
	 * @param birthdayMonth セットする birthdayMonth
	 */
	public void setBirthdayMonth(String birthdayMonth) {
		this.birthdayMonth = birthdayMonth;
	}
	/**
	 * @return birthdayDay
	 */
	public String getBirthdayDay() {
		return birthdayDay;
	}
	/**
	 * @param birthdayDay セットする birthdayDay
	 */
	public void setBirthdayDay(String birthdayDay) {
		this.birthdayDay = birthdayDay;
	}
	/**
	 * @return gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender セットする gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return telephoneNumber
	 */
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	/**
	 * @param telephoneNumber セットする telephoneNumber
	 */
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	/**
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}
	/**
	 * @param mailAddress セットする mailAddress
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	/**
	 * @return postCode
	 */
	public String getPostCode() {
		return postCode;
	}
	/**
	 * @param postCode セットする postCode
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	/**
	 * @return prefectures
	 */
	public String getPrefectures() {
		return prefectures;
	}
	/**
	 * @param prefectures セットする prefectures
	 */
	public void setPrefectures(String prefectures) {
		this.prefectures = prefectures;
	}
	/**
	 * @return municipalities
	 */
	public String getMunicipalities() {
		return municipalities;
	}
	/**
	 * @param municipalities セットする municipalities
	 */
	public void setMunicipalities(String municipalities) {
		this.municipalities = municipalities;
	}
	/**
	 * @return address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address セットする address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return apartment
	 */
	public String getApartment() {
		return apartment;
	}
	/**
	 * @param apartment セットする apartment
	 */
	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
    /**
	 * @return currentPassword
	 */
	public char[] getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * @param currentPassword セットする currentPassword
	 */
	public void setCurrentPassword(char[] currentPassword) {
		this.currentPassword = currentPassword;
	}
	/**
	 * @return newPassword
	 */
	public char[] getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword セットする newPassword
	 */
	public void setNewPassword(char[] newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return newPasswordConfirm
	 */
	public char[] getNewPasswordConfirm() {
		return newPasswordConfirm;
	}
	/**
	 * @param newPasswordConfirm セットする newPasswordConfirm
	 */
	public void setNewPasswordConfirm(char[] newPasswordConfirm) {
		this.newPasswordConfirm = newPasswordConfirm;
	}
	/**
	 * @return genderList
	 */
	public List<CodeLabel> getGenderList() {
		return genderList;
	}
	/**
	 * @param genderList セットする genderList
	 */
	public void setGenderList(List<CodeLabel> genderList) {
		this.genderList = genderList;
	}
	
    /** 画面選択肢のデフォルト */
    public static List<CodeLabel> defaultGenderList() {
        return Gender.asCodeLabelList();
    }

    /** 選択肢用の小さなVO */
    public static final class CodeLabel implements Serializable {
        private final String code;
        private final String label;
        public CodeLabel(String code, String label) { this.code = code; this.label = label; }
        public String getCode() { return code; }
        public String getLabel() { return label; }
    }
}

