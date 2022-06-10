package com.example.itransition.task_4.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name =  "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private String email;
	
	private String password;

	private boolean status;/*if status is true user active else blocked*/

	@CreationTimestamp
	private Date createdDate;

	private Date lastLoginTime;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(
		            name = "user_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(
				            name = "role_id", referencedColumnName = "id"))
	
	private Collection<Role> roles;
	
	public User(String firstName, String lastName, String email, String password, Collection<Role> roles,Boolean status) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.status = status;
	}

	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

}
