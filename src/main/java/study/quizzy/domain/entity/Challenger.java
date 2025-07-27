package study.quizzy.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import study.quizzy.domain.entity.base.BaseTimeEntity;

@Entity
@Table(name = "challenger")
@Getter
public class Challenger extends BaseTimeEntity {

	@Id
	@Column(name = "challenger_id")
	private String challengerId;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "password")
	private String password;

	@Column(name = "provider")
	private String provider;

	@Column(name = "email")
	private String email;
}
