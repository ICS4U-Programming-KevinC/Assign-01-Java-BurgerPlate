import java.util.Scanner;

/**
 * Calculates the amount of burgers you fit on your triangle shaped plate.
 *
 * <p>Then the cost of those burgers and type of triangle the plate is.
 *
 * @author Kevin Csiffary
 * @version 1.0
 * @since 2024-03-22
 */

// BurgerPlate class
public final class BurgerPlate {
  // Initialize variables
  /** Initialize the diameter for a size 1 burger. */
  static final double BURGER1SIZE = 8.7;

  /** Initialize the diameter for a size 2 burger. */
  static final double BURGER2SIZE = 10.3;

  /** Initialize the cost for a size 1 burger. */
  static final double BURGER1COST = 5.99;

  /** Initialize the cost for a size 2 burger. */
  static final double BURGER2COST = 7.25;

  /** Initialize the first plate side length. */
  static final double PLATE_SIDE_LENGTH = 35.0;

  /** Initialize sales tax. */
  static final double HST = 1.13;

  /** Fix magic number error. */
  static final int TOTAL_INNER_TRIANGLE_ANGLE = 180;

  /** Private constructor to prevent instantiation. */
  private BurgerPlate() {
    throw new UnsupportedOperationException("Cannot instantiate");
  }

  /**
   * This is the main method.
   *
   * @param args Unused
   */
  public static void main(final String[] args) {

    // Tell the user what the program is about.
    System.out.print("This program asks you two angles of your plate, ");
    System.out.print("and the size of your burger, then tells you the ");
    System.out.print("type of triangle your plate is and how many ");
    System.out.print("burgers can fit (based on area) and how how much ");
    System.out.println("they will cost.");
    System.out.println("*Assumed side length between angles is 35cm\n");

    // Create the scanner.
    Scanner sc = new Scanner(System.in);

    // Initialize quit condition.
    boolean quit = false;

    do {
      try {
        // Get user input.
        System.out.println("What is the first angle of your plate?");
        double angle1 = sc.nextDouble();
        System.out.println("What is the second angle of your plate?");
        double angle2 = sc.nextDouble();
        System.out.println("Please enter the size of your burger, 1 or 2.");
        int burgerSize = sc.nextInt();

        // Calculate third angle.
        double angle3 = getThirdAngle(angle1, angle2);

        if (angle3 > 0) {
          // Initialize variables to be accessed in output.
          int burgerCount = 0;
          double subtotal = 0.0;
          double total = 0.0;
          String triangleType = "";

          // Calculate burger data based on input.
          if (burgerSize == 1) {
            double burgerArea = getBurgerArea(BURGER1SIZE);
            double triangleArea = calculateTriArea(angle1, angle2, angle3);
            burgerCount = getBurgerCount(burgerArea, triangleArea);
            subtotal = burgerCount * BURGER1COST;
            total = subtotal * HST;
            triangleType = calculateTriangleType(angle1, angle2, angle3);
          } else if (burgerSize == 2) {
            double burgerArea = getBurgerArea(BURGER2SIZE);
            double triangleArea = calculateTriArea(angle1, angle2, angle3);
            burgerCount = getBurgerCount(burgerArea, triangleArea);
            subtotal = burgerCount * BURGER2COST;
            total = subtotal * HST;
            triangleType = calculateTriangleType(angle1, angle2, angle3);
          } else {
            System.out.println("That is not an available size!");
            continue;
          }

          // Display result to user
          System.out.print("You can fit " + burgerCount + " size ");
          System.out.print(burgerSize + " burger(s) on your ");
          System.out.println(triangleType + " shaped plate.");
          System.out.print("Your subtotal will be $");
          System.out.println(String.format("%.2f", subtotal));
          System.out.print("and your total will be $");
          System.out.println(String.format("%.2f", total));

        } else {
          System.out.println("Your triangle is not real!");
          continue;
        }

        sc.nextLine();

        System.out.println("\nWould you like to try again?");
        System.out.println("Enter q to quit");
        String userQuit = sc.nextLine();

        if (userQuit.equals("q")) {
          quit = true;
        }

      } catch (Exception e) {
        sc.nextLine();
        System.out.println("Please enter a proper number!\n");
        continue;
      }
    } while (!quit);
    sc.close();
  }

  static double getThirdAngle(final double angle1, final double angle2) {
    double angle3 = TOTAL_INNER_TRIANGLE_ANGLE - angle1 - angle2;
    return angle3;
  }

  static String calculateTriangleType(
      final double angle1, final double angle2, final double angle3) {
    if (angle1 == angle2 && angle2 == angle3) {
      return "Equilateral";
    } else if (angle1 == angle2 || angle2 == angle3 || angle1 == angle3) {
      return "Isosceles";
    } else {
      return "Scalene";
    }
  }

  static double calculateTriArea(// Here is some text to make lines <80.
      final double angle1, final double angle2, final double angle3) {
    double sinLaw = Math.sin(angle3) / PLATE_SIDE_LENGTH;
    double secondSide = Math.sin(angle1) / sinLaw;
    double height = Math.sin(angle2) * secondSide;
    return (PLATE_SIDE_LENGTH * height) / 2;
  }

  static double getBurgerArea(final double burgerDiameter) {
    return Math.PI * Math.pow((burgerDiameter / 2), 2);
  }

  static int getBurgerCount(final double burgerArea, final double plateArea) {
    int burgerCount = (int) (plateArea / burgerArea);
    return burgerCount;
  }
}
