package local.fdb.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public UserService() {
		super();
	}
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	public Optional<User> getUserById(String id) {
		return userRepository.findById(id);
	}
	
	public void addUser(User u) {
		userRepository.save(u);
	} 
	
	public void updateUser(User u, String id) {
		User uSaved = this.getUserById(id)
						  .orElseThrow(() -> { 
								throw new ResponseStatusException(
									  HttpStatus.NOT_FOUND,
									  "user not found");
						  });
		uSaved.setName(u.getName());
		uSaved.setEmail(u.getEmail());
		uSaved.setAddress(u.getAddress());
		
		userRepository.save(uSaved);
	}
	
	public void deleteUserById(String id) {
		userRepository.deleteById(id);
	}

}
