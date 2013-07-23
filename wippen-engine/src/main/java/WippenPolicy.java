import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;

//Need to be careful - don't want to give libraries (e.g. spring) the ability to do reflection on robots behalf.
public class WippenPolicy extends Policy {
	private final PermissionCollection robotPermissions = new Permissions();
	private CodeSource engineSource;
	
	@SuppressWarnings("serial")
	private static Permission INITIALIZATION_PERMISSION = new Permission("") {
		@Override
		public boolean implies(Permission permission) {
			return false;
		}

		@Override
		public boolean equals(Object that) {
			return this == that;
		}

		@Override
		public int hashCode() {
			return 0;
		}

		@Override
		public String getActions() {
			return null;
		}
	};
	
	WippenPolicy() {
	}

	public static WippenPolicy installPolicy() {
		WippenPolicy policy = new WippenPolicy();
		Policy.setPolicy(policy);
		try {
			SecurityManager sm = new SecurityManager();
			System.setSecurityManager(sm);
			
			sm.checkPermission(INITIALIZATION_PERMISSION);
		} catch (SecurityException e) {
			System.err.println("Error: could not set security manager: " + e);
		}
		
		return policy;
	}
	
	public boolean implies(ProtectionDomain domain, Permission permission) {
		if (INITIALIZATION_PERMISSION == permission) {
			engineSource = domain.getCodeSource();
			return true;
		} else if (engineSource == null) {
			throw new IllegalStateException();
		}
		
		try {
			URL url = domain.getCodeSource().getLocation();
			URI uri = url.toURI();
			System.out.println(url.getFile());
			System.out.println(url);
			System.out.println(uri);
			System.out.println(domain.getCodeSource().implies(new CodeSource(new URL("file:wippen-engine.jar"), (Certificate[])null)));
		
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return robotPermissions.implies(permission);
	}

	public void addPermission(Permission permission) {
		robotPermissions.add(permission);
	}
}