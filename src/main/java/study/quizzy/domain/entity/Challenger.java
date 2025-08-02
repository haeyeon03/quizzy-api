package study.quizzy.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import study.quizzy.domain.entity.base.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "challenger")
@Getter
@Setter
public class Challenger extends BaseTimeEntity {
	@Id
	@Column(name = "challenger_id")
	private String challengerId;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	@Column(name = "provider")
	private String provider;

	@Column(name = "email")
	private String email;

	@OneToMany(mappedBy = "challenger", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rank> ranks = new ArrayList<>();
}
