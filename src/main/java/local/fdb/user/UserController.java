package local.fdb.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return userService.getAllUsers();
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public Optional<User> getUserById(@PathVariable String id) {
		return userService.getUserById(id);
	}
	
	@RequestMapping(value="/users", method = RequestMethod.POST)
	public void addUser(@RequestBody User u) {
		userService.addUser(u);
	}
	
	@RequestMapping(value="/users/{id}", method = RequestMethod.PUT)
	public void updateUser(@RequestBody User u, @PathVariable String id) {
		userService.updateUser(u, id);
	}
	
	@RequestMapping(value="/users/{id}", method = RequestMethod.DELETE)
	public void deleteUserById(@PathVariable String id) {
		userService.deleteUserById(id);
	}

}
