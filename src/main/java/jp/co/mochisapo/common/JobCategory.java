package jp.co.mochisapo.common;

public enum JobCategory {
	STUDENT("st"),
	TEACHER("te"),
	CAREER("cr"),
	ADMIN("ad");
	
	private String jobCode;
	
	private JobCategory(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getJobCode() {
		return jobCode;
	}

	public static JobCategory fromCode(String code) {
		for (JobCategory c : values()) {
			if (c.jobCode.equals(code)) {
				return c;
			}
		}
		throw new IllegalArgumentException("Unknown JobCategory code: " + code);
	}
}
