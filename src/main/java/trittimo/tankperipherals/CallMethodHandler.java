package trittimo.tankperipherals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class CallMethodHandler {
	private String[] methods;
	private Class callClass;

	public CallMethodHandler(Class callClass) {
		ArrayList<String> methodNames = new ArrayList<String>();
		for (Method m : callClass.getDeclaredMethods()) {
			if (Modifier.isStatic(m.getModifiers())) {
				methodNames.add(m.getName());
			}
		}

		this.methods = methodNames.toArray(new String[methodNames.size()]);
		this.callClass = callClass;
	}

	public String[] getMethods() {
		return this.methods;
	}

	public Object[] callMethod(int methodIndex, Class[] methodSignature, Object[] methodArguments)
			throws Throwable {
		if (methods.length > methodIndex) {
			Method method = callClass.getMethod(methods[methodIndex], methodSignature);
			try {
				return (Object[]) method.invoke(null, methodArguments);
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		} else {
			return new Object[] { "Unrecognized call method" };
		}
	}

	public static void checkArguments(Object[] example, Object[] actual) {
		if (example.length != actual.length) {
			throw new RuntimeException(
					"Incorrect argument count. Example arguments: " + Arrays.toString(example));
		}
		// } else {
		// for (int i = 0; i < example.length; i++) {
		// if (!example[i].getClass().isAssignableFrom(actual[i].getClass())) {
		// throw new RuntimeException("Argument at index " + i + " was invalid.
		// Example arguments: " + Arrays.toString(example));
		// }
		// }
		// }
	}
}
