package jp.co.mochisapo.user.profile.domain;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Gender {
    NONE("N", "選択してください"),
    MALE("M", "男性"),
    FEMALE("W", "女性"),
    UNKNOWN("U", "不明");

    private final String code;
    private final String label;

    Gender(String code, String label) { this.code = code; this.label = label; }
    public String getCode() { return code; }
    public String getLabel() { return label; }

    public static Gender fromCode(String code) {
    	if (code == null) return NONE;
    	for (Gender g : values()) {
    		if (g.code.equals(code)) return g;
    	}
    	return NONE;
    }

    /** 画面選択肢用 */
    public static List<jp.co.mochisapo.user.profile.web.form.ProfileForm.CodeLabel> asCodeLabelList() {
    	return Arrays.stream(values())
    			.map(g -> new jp.co.mochisapo.user.profile.web.form.ProfileForm.CodeLabel(g.code, g.label))
    			.collect(Collectors.toList());
    }
}
