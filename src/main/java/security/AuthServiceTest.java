package security;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;


public class AuthServiceTest {

	private static final String USER_PASSWORD = "userPassword";
	private AuthService authService;

	public class AuthService {

		public void authenticate(String id, String password) {
			if(id == null || id.isEmpty()) throw new IllegalArgumentException();
			if(password == null || password.isEmpty()) throw new IllegalArgumentException();
		}

	}
	
	@Test
	public void givenInvalidId_throwIllegalArgEx() {
		assertIllegalArgExThrown(null, USER_PASSWORD);
		assertIllegalArgExThrown("", USER_PASSWORD);
		assertIllegalArgExThrown("userId", null);
		assertIllegalArgExThrown("userId", "");
	}

	private void assertIllegalArgExThrown(String id, String password) {
		Exception throwEx = null;
		try {
			authService.authenticate(id, password);
		} catch (Exception e) {
			throwEx = e;
		}
		assertThat(throwEx, instanceOf(IllegalArgumentException.class));
	}
	
	@Before
	public void setUp() {
		authService = new AuthService();
		
	}
	
	
}
