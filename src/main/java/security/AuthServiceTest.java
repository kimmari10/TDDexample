package security;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import org.junit.Before;
import org.junit.Test;


public class AuthServiceTest { 

	public interface UserRepository {
		User findById(String id);
	}

	public class WrongPasswordException extends RuntimeException {

	}

	private static final String NO_USER_ID = "noUserId";

	public class NonExistingUserException extends RuntimeException {

	}

	private static final String USER_ID = "userId";
	private static final String USER_PASSWORD = "userPassword";
	private AuthService authService;
	private UserRepository mockUserRepository;

	public class AuthService {

		private UserRepository userRepository;

		public void setUserRepository(UserRepository userRepository) {
			this.userRepository = userRepository;
		}

		public void authenticate(String id, String password) {
			if(id == null || id.isEmpty()) throw new IllegalArgumentException();
			if(password == null || password.isEmpty()) throw new IllegalArgumentException();
			
			User user = findUserById(id);
			if(user == null)
				throw new NonExistingUserException();
			
			throw new WrongPasswordException();
		}

		private User findUserById(String id) {
			return userRepository.findById(id);
//			if (id.equals("userId"))
//				return new User(id, "1234");
//			return null;
		}

	}
	
	@Test
	public void whenUserFoundButWrongPw_throwWrongPasswordEx() {
		givenUserExists("userId", USER_PASSWORD);
		assertExceptionThrown("userId", "worngPassword", WrongPasswordException.class);
		verifyUserFound("userId");
		
	}	
	
	private void givenUserExists(String id, String password) {
		when(mockUserRepository.findById(id)).thenReturn(new User(id, password));
	}

	private void verifyUserFound(String id) {
		verify(mockUserRepository).findById(id);
	}
	
	@Test
	public void whenUserNotFound_throwNonExistingUserEx() {
		assertExceptionThrown(NO_USER_ID, USER_PASSWORD, NonExistingUserException.class);
		for (int i = 1; i <= 100; i++)
			assertExceptionThrown(NO_USER_ID + i, USER_PASSWORD, NonExistingUserException.class);
	}

	private void assertExceptionThrown(String id, String password,
			Class<? extends Exception> type) {
		Exception throwEx = null;
		try {
			authService.authenticate(id, password);
		} catch (Exception e) {
			throwEx = e;
		}
		assertThat(throwEx, instanceOf(type));
	}
	
	@Test
	public void givenInvalidId_throwIllegalArgEx() {
		assertIllegalArgExThrown(null, USER_PASSWORD);
		assertIllegalArgExThrown("", USER_PASSWORD);
		assertIllegalArgExThrown(USER_ID, null);
		assertIllegalArgExThrown(USER_ID, "");
	}

	private void assertIllegalArgExThrown(String id, String password) {
		assertExceptionThrown(id, password, IllegalArgumentException.class);
	}
	
	@Before
	public void setUp() {
		mockUserRepository = mock(UserRepository.class);
		authService = new AuthService();
		authService.setUserRepository(mockUserRepository);
	}
	
	
}
