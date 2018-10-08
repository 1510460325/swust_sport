package cn.wzy.sport.service.constant;

/**
 * Create by Wzy
 * on 2018/7/21 15:03
 * 不短不长八字刚好
 */
public enum SportStatus {
	READY (1),
	RUNNING(2),
	END(3);

	private int status;

	public int val() {
		return this.status;
	}

	SportStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return this.status + "";
	}
}
