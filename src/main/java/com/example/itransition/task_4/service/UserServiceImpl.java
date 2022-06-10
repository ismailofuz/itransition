package com.example.itransition.task_4.service;

import com.example.itransition.task_4.config.Generator;
import com.example.itransition.task_4.model.Role;
import com.example.itransition.task_4.model.User;
import com.example.itransition.task_4.repository.UserRepository;
import com.example.itransition.task_4.web.dto.UserRegistrationDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;

	private final Generator generator;

	public UserServiceImpl(UserRepository userRepository, Generator generator) {
		super();
		this.userRepository = userRepository;
		this.generator = generator;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(
				registrationDto.getFirstName(),
				registrationDto.getLastName(), registrationDto.getEmail(),
				generator.passwordEncoder().encode(registrationDto.getPassword()),
				Arrays.asList(new Role("ROLE_USER")),
				true);
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		user.setLastLoginTime(new Date());
		userRepository.save(user);
		return new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
	@Override
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

	@Override
	public User deleteUsers(List<Long> list){
		for (Long i:list) {
			userRepository.deleteById(i);
		}
		return null;
	}

	@Override
	public User blockUser(List<Long> list) {
		for (Long i:list) {
			Optional<User> byId = userRepository.findById(i);
			User user = byId.get();
			user.setStatus(false);
			userRepository.save(user);
		}
		return null;
	}

	@Override
	public User unblockUser(List<Long> list) {
		for (Long i:list) {
			Optional<User> byId = userRepository.findById(i);
			User user = byId.get();
			user.setStatus(true);
			userRepository.save(user);
		}
		return null;
	}
}
