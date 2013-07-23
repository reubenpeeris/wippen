import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ReflectPermission;
import java.security.AccessControlException;

public class Tester {

	private static final Class<?> victimClass = VictimClass.class;

	public static void main(String[] args) {
		makeConstructorAccessible("with no SecurityManager");
		
		WippenPolicy policy = WippenPolicy.installPolicy();
//		Policy.setPolicy(securityPolicy);

		
		
		makeConstructorAccessible("with SecurityManager");

		policy.addPermission(
				new ReflectPermission("suppressAccessChecks"));
		
		makeConstructorAccessible("with SecurityManager and ReflectPermission");
	}

	private static void makeConstructorAccessible(String message) {
		System.out.println("Trying to make constructor accessible: " + message);

		try {
			Constructor<?>[] constructors = victimClass.getDeclaredConstructors();
			constructors[0].setAccessible(true);
			constructors[0].newInstance(new Object[] {});
		} catch (InstantiationException e) {
			System.out.println(" Error: could not instantiate: " + e);
		} catch (IllegalAccessException e) {
			System.out.println(" Error: could not access: " + e);
		} catch (InvocationTargetException e) {
			System.out.println(" Error: could not invoke the target: " + e);
		} catch (AccessControlException e) {
			System.out.println(" Could not made constructor accessible: "
					+ e.getMessage());
		}
	}
}