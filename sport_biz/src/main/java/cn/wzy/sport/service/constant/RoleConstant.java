package cn.wzy.sport.service.constant;

/**
 * Create by Wzy
 * on 2018/7/14 11:33
 * 不短不长八字刚好
 */
public enum RoleConstant {
	VISITOR(3),
	ORDINARY(2),
	ADMIN(1);

	private int role;

	RoleConstant(int role) {
		this.role = role;
	}

	public int val() {
		return this.role;
	}
	@Override
	public String toString() {
		return this.role + "";
	}
}
