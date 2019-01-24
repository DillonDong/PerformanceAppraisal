package cn.edu.fjnu.towide.entity;

import lombok.Data;

import java.util.Objects;


@Data
public class Authority {
	private String authority;
	private String description;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Authority authority1 = (Authority) o;
		return Objects.equals(authority, authority1.authority) &&
				Objects.equals(description, authority1.description);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authority, description);
	}
}
