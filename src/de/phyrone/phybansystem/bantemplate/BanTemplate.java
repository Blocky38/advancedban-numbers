package de.phyrone.phybansystem.bantemplate;

public class BanTemplate {
	public BanTemplate(String name) {
		this.Name = name;
		this.Reason = name;
	}
	
	public String getName() {
		return Name;
	}
	public String getPerm() {
		return Perm;
	}

	public BanTemplate setPerm(String perm) {
		Perm = perm;
		return this;
	}

	public BanTemplate setName(String name) {
		Name = name;
		return this;
	}
	public String getReason() {
		return Reason;
	}
	public BanTemplate setReason(String reason) {
		Reason = reason;
		return this;
	}
	public BanType getType() {
		return Type;
	}
	public BanTemplate setType(BanType type) {
		Type = type;
		return this;
	}
	public int getBanTime() {
		return BanTime;
	}
	public BanTemplate setBanTime(int banTime) {
		BanTime = banTime;
		return this;
	}

	public String Name;
	public String Reason;
	public BanType Type = BanType.BAN;
	public int BanTime = -1;
	public String Perm = null;

}
