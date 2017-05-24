import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TypeChecker {
  /* Controls whether stack traces are printed for missing
   * methods and fields.
   */
  private static boolean VERBOSE = false;

  /**
   * Creates a List out of items.
   * @param items the elements that will constitute the return List
   * @return List of items
   */
  @SuppressWarnings("unchecked")
  public static <T> List<T> listify(T ... items) {
    List<T> list = new ArrayList<T>();
    for (T item : items) {
      list.add(item);
    }
    return list;
  }

  /**
   * Trigger some compiler errors if necessary.
   * More accurate than using reflection.
   */
  @SuppressWarnings("unused")
  public static void generics() throws java.io.IOException {
    game.ArrayGrid<String> grid = new game.ArrayGrid<String>(2, 2);
    game.Grid<sprites.Sprite> gme = new game.VacuumGame("").getGrid();
  }

  /**
   * Checks types used in the solution of A1.
   * @param args the usual
   * @throws ClassNotFoundException if a class that should be part of the
   *     solution is not found
   */
  public static void main(String[] args) throws ClassNotFoundException {
    printMissing("Could not find abstract class %s.",
        verifyClasses(false, true,
            "sprites.Sprite"
            ));

    printMissing("Could not find class %s.",
        verifyClasses(false, false,
            "game.VacuumGame",
            "game.ArrayGrid",
            "sprites.Wall",
            "sprites.Dumpster",
            "sprites.CleanHallway",
            "sprites.Dirt",
            "sprites.DustBall",
            "sprites.Vacuum",
            "ui.TextUi",
            "ui.Gui",
            "ui.GuiListener"
            ));

    printMissing("Could not find interface %s.",
        verifyClasses(true, false,
            "game.Grid",
            "sprites.Moveable",
            "ui.Ui"
            ));

    printMissing("Could not find %s with correct signature.",
        verifySignatures(
            verifySignature("game.Grid", "public", false,
                void.class, "setCell", int.class, int.class, Object.class),
                verifySignature("game.Grid", "public", false,
                    Object.class, "getCell", int.class, int.class),
                    verifySignature("game.Grid", "public", false,
                        int.class, "getNumRows"),
                        verifySignature("game.Grid", "public", false,
                            int.class, "getNumColumns"),
                            verifySignature("game.Grid", "public", false,
                                boolean.class, "equals", Object.class),
                                verifySignature("game.ArrayGrid", "public", false,
                                    null, null, int.class, int.class),
                                    verifySignature("game.ArrayGrid", "private", false,
                                        null, "grid", Object[][].class),
                                        verifySignature("game.VacuumGame", "private", false,
                                            null, "vacuum1", sprites.Vacuum.class),
                                            verifySignature("game.VacuumGame", "private", false,
                                                null, "vacuum2", sprites.Vacuum.class),
                                                verifySignature("game.VacuumGame", "private", false,
                                                    null, "dirts", java.util.List.class),
                                                    verifySignature("game.VacuumGame", "private", false,
                                                        null, "random", java.util.Random.class),
                                                        verifySignature("game.VacuumGame", "public", false,
                                                            null, null, String.class),
                                                            verifySignature("game.VacuumGame", "public", false,
                                                                game.Grid.class, "getGrid"),
                                                                verifySignature("game.VacuumGame", "public", false,
                                                                    sprites.Vacuum.class, "getVacuumOne"),
                                                                    verifySignature("game.VacuumGame", "public", false,
                                                                        sprites.Vacuum.class, "getVacuumTwo"),
                                                                        verifySignature("game.VacuumGame", "public", false,
                                                                            int.class, "getNumRows"),
                                                                            verifySignature("game.VacuumGame", "public", false,
                                                                                int.class, "getNumColumns"),
                                                                                verifySignature("game.VacuumGame", "public", false,
                                                                                    sprites.Sprite.class, "getSprite", int.class, int.class),
                                                                                    verifySignature("game.VacuumGame", "public", false,
                                                                                        boolean.class, "move", char.class),
                                                                                        verifySignature("game.VacuumGame", "public", false,
                                                                                            boolean.class, "gameOver"),
                                                                                            verifySignature("game.VacuumGame", "public", false,
                                                                                                int.class, "getWinner"),
                                                                                                verifySignature("sprites.Sprite", "protected", false,
                                                                                                    null, "symbol", char.class),
                                                                                                    verifySignature("sprites.Sprite", "protected", false,
                                                                                                        null, "row", int.class),
                                                                                                        verifySignature("sprites.Sprite", "protected", false,
                                                                                                            null, "column", int.class),
                                                                                                            verifySignature("sprites.Sprite", "public", false,
                                                                                                                null, null, char.class, int.class, int.class),
                                                                                                                verifySignature("sprites.Sprite", "public", false,
                                                                                                                    char.class, "getSymbol"),
                                                                                                                    verifySignature("sprites.Sprite", "public", false,
                                                                                                                        int.class, "getRow"),
                                                                                                                        verifySignature("sprites.Sprite", "public", false,
                                                                                                                            int.class, "getColumn"),
                                                                                                                            verifySignature("sprites.Sprite", "public", false,
                                                                                                                                String.class, "toString"),
                                                                                                                                verifySignature("sprites.Wall", "public", false,
                                                                                                                                    null, null, char.class, int.class, int.class),
                                                                                                                                    verifySignature("sprites.Dumpster", "public", false,
                                                                                                                                        null, null, char.class, int.class, int.class),
                                                                                                                                        verifySignature("sprites.CleanHallway", "public", false,
                                                                                                                                            null, null, char.class, int.class, int.class),
                                                                                                                                            verifySignature("sprites.Dirt", "protected", false,
                                                                                                                                                null, "value", int.class),
                                                                                                                                                verifySignature("sprites.Dirt", "public", false,
                                                                                                                                                    null, null, char.class, int.class, int.class, int.class),
                                                                                                                                                    verifySignature("sprites.Dirt", "public", false,
                                                                                                                                                        int.class, "getValue"),
                                                                                                                                                        verifySignature("sprites.DustBall", "public", false,
                                                                                                                                                            null, null, char.class, int.class, int.class, int.class),
                                                                                                                                                            verifySignature("sprites.Moveable", "public", false,
                                                                                                                                                                void.class, "moveTo", int.class, int.class),
                                                                                                                                                                verifySignature("sprites.Vacuum", "private", false,
                                                                                                                                                                    null, "score", int.class),
                                                                                                                                                                    verifySignature("sprites.Vacuum", "private", false,
                                                                                                                                                                        null, "capacity", int.class),
                                                                                                                                                                        verifySignature("sprites.Vacuum", "private", false,
                                                                                                                                                                            null, "fullness", int.class),
                                                                                                                                                                            verifySignature("sprites.Vacuum", "private", false,
                                                                                                                                                                                null, "under", sprites.Sprite.class),
                                                                                                                                                                                verifySignature("sprites.Vacuum", "public", false,
                                                                                                                                                                                    null, null, char.class, int.class, int.class, int.class),
                                                                                                                                                                                    verifySignature("sprites.Vacuum", "public", false,
                                                                                                                                                                                        boolean.class, "clean", int.class),
                                                                                                                                                                                        verifySignature("sprites.Vacuum", "public", false,
                                                                                                                                                                                            void.class, "empty"),
                                                                                                                                                                                            verifySignature("sprites.Vacuum", "public", false,
                                                                                                                                                                                                int.class, "getScore"),
                                                                                                                                                                                                verifySignature("sprites.Vacuum", "public", false,
                                                                                                                                                                                                    void.class, "setUnder", sprites.Sprite.class),
                                                                                                                                                                                                    verifySignature("sprites.Vacuum", "public", false,
                                                                                                                                                                                                        sprites.Sprite.class, "getUnder"),
                                                                                                                                                                                                        verifySignature("ui.Ui", "public", false,
                                                                                                                                                                                                            void.class, "launchGame"),
                                                                                                                                                                                                            verifySignature("ui.Ui", "public", false,
                                                                                                                                                                                                                void.class, "displayWinner"),
                                                                                                                                                                                                                verifySignature("ui.TextUi", "private", false,
                                                                                                                                                                                                                    null, "game", game.VacuumGame.class),
                                                                                                                                                                                                                    verifySignature("ui.TextUi", "public", false,
                                                                                                                                                                                                                        null, null, game.VacuumGame.class),
                                                                                                                                                                                                                        verifySignature("ui.Gui", "private", false,
                                                                                                                                                                                                                            null, "game", game.VacuumGame.class),
                                                                                                                                                                                                                            verifySignature("ui.Gui", "private", false,
                                                                                                                                                                                                                                null, "tiles", javax.swing.JLabel[][].class),
                                                                                                                                                                                                                                verifySignature("ui.Gui", "public", false,
                                                                                                                                                                                                                                    null, null, game.VacuumGame.class),
                                                                                                                                                                                                                                    verifySignature("ui.Gui", "public", false,
                                                                                                                                                                                                                                        game.VacuumGame.class, "getGame"),
                                                                                                                                                                                                                                        verifySignature("ui.Gui", "public", false,
                                                                                                                                                                                                                                            void.class, "updateLabels"),
                                                                                                                                                                                                                                            verifySignature("ui.GuiListener", "private", false,
                                                                                                                                                                                                                                                null, "window", ui.Gui.class)
            )
    );

    printMissing("%s does not extend or implement the correct classes/interfaces.",
        verifySignatures(
            checkHierarchy("game.ArrayGrid", "java.lang.Object", "game.Grid"),
            checkHierarchy("game.VacuumGame", "java.lang.Object"),
            checkHierarchy("sprites.Sprite", "java.lang.Object"),
            checkHierarchy("sprites.Wall", "sprites.Sprite"),
            checkHierarchy("sprites.Dumpster", "sprites.Sprite"),
            checkHierarchy("sprites.CleanHallway", "sprites.Sprite"),
            checkHierarchy("sprites.Dirt", "sprites.Sprite"),
            checkHierarchy("sprites.Vacuum", "sprites.Sprite", "sprites.Moveable"),
            checkHierarchy("sprites.DustBall", "sprites.Dirt", "sprites.Moveable"),
            checkHierarchy("ui.TextUi", "java.lang.Object", "ui.Ui")
            )
    );

    System.out.println("All type checks passed!");
    System.out.println("Note that this does NOT mean your code works perfectly.");
  }

  /**
   * Checks if className implements from and extends the expected interfaces and classes.
   * @param className name of the class to check
   * @param supercls class the class should extend
   * @param interfaces interfaces the class should implement
   * @return the name of the violating class or null
   * @throws ClassNotFoundException if a class that should be part of the solution 
   *     does not exist
   */
  private static String checkHierarchy(String className, String supercls, String ... interfaces)
      throws ClassNotFoundException {
    Set<String> set = new HashSet<String>(Arrays.asList(interfaces));

    Class<?> cls = Class.forName(className);

    if (cls.getSuperclass() != null) {
      if (!supercls.equals(cls.getSuperclass().getName())) {
        return className;
      }
    }
    for (Class<?> c : cls.getInterfaces()) {
      if (!set.remove(c.getName())) {
        return className;
      }
    }

    if (set.size() > 0) {
      return className;
    }

    return null;
  }

  /**
   * Returns the name of any signature that doesn't match and null
   * otherwise.
   * @param className name of the class that is checked
   * @param accessModifier expected access modifier
   * @param staticWanted whether or not static is expected
   * @param returnType Return type of method; null for constructors
   *     and fields.
   * @param methodName Null for constructors.
   * @param argClasses the classes
   * @return name of a violating signature or null
   */
  private static String verifySignature(String className, String accessModifier,
      boolean staticWanted, Object returnType, String methodName,
      Object ... argClasses) {
    Class<?> cls = null;

    try {
      cls = Class.forName(className);
    } catch (ClassNotFoundException e) { }

    List<Class<?>> argTypes = argClassesToList(argClasses);
    Class<?>[] args = argTypes == null ? null : argTypes.toArray(new Class<?>[0]);

    try {
      int modifiers = getModifiers(returnType, cls, methodName, args);
      // Check access modifiers
      checkModifiers(accessModifier, staticWanted, modifiers);
    } catch (NoSuchMethodException e) {
      if (VERBOSE) {
        e.printStackTrace();
      }
      return className + "." + methodName;
    } catch (SecurityException e) {
      return className + "." + methodName;
    } catch (NoSuchFieldException e) {
      return className + "." + methodName;
    } catch (ClassNotFoundException e) {
      // Problem with type checker inputs
      e.printStackTrace();
    }

    return null;
  }


  /**
   * Raises an exception if the expected access modifier does not match
   * the declared modifier.
   * @param accessModifier One of the Java access modifiers.
   * @param staticWanted True iff a static method/field is sought.
   * @param modifiers Modifiers as found by reflection.
   * @throws NoSuchMethodException if an expected method does not exist
   */
  private static void checkModifiers(String accessModifier,
      boolean staticWanted, int modifiers)
          throws NoSuchMethodException {

    // Check static-ness
    if (Modifier.isStatic(modifiers) != staticWanted) {
      throw new NoSuchMethodException();
    }

    // Check access modifiers
    if (accessModifier.equals("public")
        && !Modifier.isPublic(modifiers)) {
      throw new NoSuchMethodException();
    } else if (accessModifier.equals("protected")
        && !Modifier.isProtected(modifiers)) {
      throw new NoSuchMethodException();
    } else if (accessModifier.equals("default")
        && !Modifier.isPublic(modifiers)
        && !Modifier.isProtected(modifiers)
        && !Modifier.isPrivate(modifiers)) {
      throw new NoSuchMethodException();
    } else if (accessModifier.equals("private")
        && !Modifier.isPrivate(modifiers)) {
      throw new NoSuchMethodException();
    }
  }


  /**
   * Gets the modifiers for the specified constructor/method/field with
   * the matching signature.
   * @param returnType Return type of the method signature.
   * @param checkedClass The class containing the method.
   * @param name The method/field name to be checked (null for constructors)
   * @param argTypes Types of arguments for signature.
   * @return the modifiers for the specified constructor/method/field with
   *     the matching signature
   * @throws NoSuchMethodException if an expected method does not exist
   * @throws NoSuchFieldException if an expected field does not exist
   * @throws ClassNotFoundException if an expected class does not exist
   */
  private static int getModifiers(Object returnType, Class<?> checkedClass,
      String name, Class<?>[] args)
          throws NoSuchMethodException, NoSuchFieldException,
          ClassNotFoundException {
    int modifiers;
    if (name == null) {
      // Check constructors
      Constructor<?> con = checkedClass.getDeclaredConstructor(args);
      modifiers = con.getModifiers();
    } else if (returnType == null) {
      // Check fields
      Field fld = checkedClass.getDeclaredField(name);
      modifiers = fld.getModifiers();

      if (fld.getType() != toClassName(args[0])) {
        throw new NoSuchMethodException();
      }
    } else {
      // Check methods
      Method meth = checkedClass.getDeclaredMethod(name, args);
      modifiers = meth.getModifiers();

      if (meth.getReturnType() != toClassName(returnType)) {
        throw new NoSuchMethodException();
      }
    }
    return modifiers;
  }


  /**
   * Takes a list of arguments with classes or class names, turn
   * them into a list, and return that list.
   * @param argClasses classes to be put into the return List
   * @return a List of classes made up of the arguments
   */
  private static List<Class<?>> argClassesToList(Object... argClasses) {
    // Add each argument type to a list
    List<Class<?>> argTypes = new ArrayList<Class<?>>();

    if (argClasses.length > 0 && argClasses[0].equals("")) {
      argTypes = null;
    } else {
      for (Object argClass : argClasses) {
        try {
          if (argClass.getClass() == String.class) {
            argTypes.add(toClassName((String) argClass));
          } else {
            argTypes.add((Class<?>) argClass);
          }
        } catch (ClassNotFoundException e) {
          // Error with type checker input
          e.printStackTrace();
        }
      }
    }
    return argTypes;
  }

  /**
   * Resolves a class from argClassRaw if a String is provided or
   * else returns the original argument.
   * @param argClassRaw class to find
   * @return the corresponding class or null
   * @throws ClassNotFoundException if the expected class does not exist
   */
  protected static Class<?> toClassName(Object argClassRaw)
      throws ClassNotFoundException {
    Class<?> cls;
    if (argClassRaw.getClass() != String.class) {
      return (Class<?>) argClassRaw;
    }

    String argClass = (String) argClassRaw;

    if (argClass.equals("int")) {
      cls = int.class;
    } else if (argClass.equals("void")) {
      cls = void.class;
    } else if (argClass.equals("boolean")) {
      cls = boolean.class;
    } else if (argClass.equals("String")) {
      cls = java.lang.String.class;  // Convenience
    } else {
      cls = Class.forName(argClass);
    }
    return cls;
  }


  /**
   * Returns a list of Strings from methodNames with nulls filtered out.
   * @param methodNames names of methods to filter
   * @return a list of Strings from methodNames with nulls filtered out.
   */
  private static List<String> verifySignatures(String ... methodNames) {
    List<String> missing = new ArrayList<String>();
    for (String methodName : methodNames) {
      if (methodName != null) {
        missing.add(methodName);
      }
    }
    return missing;
  }

  /**
   * Prints missing items. Exits if anything is missing.
   * @param string Message for missing items. Uses "%s" for missing item name.
   * @param missingItems Items that were missing.
   */
  private static void printMissing(String string, List<String> missingItems) {
    if (missingItems.size() > 0) {
      for (String itemName : missingItems) {
        itemName = itemName.replaceAll("\\.null$", " constructor");
        System.out.println(string.replaceAll("%s", itemName));
      }
      System.exit(1);
    }
  }

  /**
   * Generates a list of classes/interfaces that are missing.
   * @param classNames classes to check
   * @param wantInterface Set to true iff looking for an interface.
   * @param wantAbstract Set to true iff looking for abstract class.
   * @return List of classes that are missing.
   */
  private static List<String> verifyClasses(boolean wantInterface,
      boolean wantAbstract,
      String ... classNames) {
    List<String> missing = new ArrayList<String>();
    for (String className : classNames) {
      try {
        Class<?> cls = Class.forName(className);
        if (cls.isInterface() != wantInterface) {
          throw new ClassNotFoundException();
        }

        // Interfaces are also abstract, so need both
        if (Modifier.isAbstract(cls.getModifiers())
            != (wantAbstract | wantInterface)) {
          throw new ClassNotFoundException();
        }
      } catch (ClassNotFoundException e) {
        missing.add(className);
      } catch (NoClassDefFoundError e) {
        missing.add(className);
      }
    }
    return missing;
  }
}
