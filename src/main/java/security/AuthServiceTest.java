package security;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;


public class AuthServiceTest {

	private AuthService authService;

	public class AuthService {

		public void authenticate(String id, String password) {
			if(id == null) throw new IllegalArgumentException();
		}

	}
	
	@Test
	public void givenInvalidId_throwIllegalArgEx() {
		Exception throwEx = null;
		
		//alt + shift + z
		try {
			authService.authenticate(null, "userPassword");
		} catch (Exception e) {
			throwEx = e;
		}
		assertThat(throwEx, instanceOf(IllegalArgumentException.class));
	}
	
	@Before
	public void setUp() {
		//변수명 alt + shift + t -> Convert local variable to field
		authService = new AuthService();
		
	}
	
	
}
