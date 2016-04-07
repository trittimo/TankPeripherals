package trittimo.tankperipherals;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class CallMethodHandler {
	private String[] methods;
	private Class callClass;

	/**
	 * Acts as a go-between for the ComputerCraft API and the Java methods
	 * without any clunky handling on the part of the integrating mod
	 * 
	 * @param callClass
	 *           The class containing the methods which will be provided to the
	 *           computer. Will only act on the static methods in the class.
	 */
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

	/**
	 * Used in classes implementing IPeripheral, can be returned directly from
	 * the getMethodNames in that class.
	 * 
	 * @return A string array of the methods in the callClass.
	 */
	public String[] getMethodNames() {
		return this.methods;
	}

	/**
	 * Automatically calls the correct method provided the methodIndex,
	 * methodSignature, and methodArguments. Should be called from a method in a
	 * class implementing IPeripheral, and should be returned directly (i.e. do
	 * not handle errors yourself).
	 * 
	 * @param methodIndex
	 *           The int method provided to the callMethod function
	 * @param methodSignature
	 *           The signature of the method to be called. Many methods will use
	 *           the same signature, so it may be practical to create a default
	 *           method signature definition and pass it to this function no
	 *           matter what method is being called
	 * @param methodArguments
	 *           The actual arguments you want to provide to the function
	 * @return The object array to be returned to the calling computer (i.e. the
	 *         result of the function call)
	 */
	public Object[] callMethod(int methodIndex, Class[] methodSignature, Object[] methodArguments) {
		try {
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
		} catch (Throwable e) {
			return new Object[] { e.getMessage() };
		}
	}

	public static void checkArguments(Object[] example, Object[] actual) {
		if (example.length != actual.length) {
			throw new RuntimeException(
					"Incorrect argument count. Example arguments: " + Arrays.toString(example));
		}
		// TODO fix the argument checker: for now only checks that the lengths are
		// the same
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
