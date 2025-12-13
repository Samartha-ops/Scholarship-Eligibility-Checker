import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;

// CO 6: Custom Exception Handling
class InvalidInputException extends Exception {
    public InvalidInputException(String msg) {
        super(msg);
    }
}

// CO 4: Class, Objects, Encapsulation
class Student {
    private String name;
    private int age;
    private String gender;
    private String reservation;
    private double gpa;
    private double income;
    private String field;
    private String specialCategory;
    private final String nationality = "Indian";

    public Student(String name, int age, String gender, String reservation,
                   double gpa, double income, String field, String specialCategory) {

        this.name = name;
        this.age = age;
        this.gender = gender;
        this.reservation = reservation;
        this.gpa = gpa;
        this.income = income;
        this.field = field;
        this.specialCategory = specialCategory;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getGender() { return gender; }
    public String getReservation() { return reservation; }
    public double getGpa() { return gpa; }
    public double getIncome() { return income; }
    public String getField() { return field; }
    public String getSpecialCategory() { return specialCategory; }
    public String getNationality() { return nationality; }
}

interface EligibilityChecker {
    boolean check(Student s);
}

class Scholarship implements EligibilityChecker {

    private String id;
    private String title;
    private double minGpa;
    private String field;
    private String nationalityRequired;
    private LocalDate deadline;

    public Scholarship(String id, String title, double minGpa, String field,
                       String nationalityRequired, LocalDate deadline) {

        this.id = id;
        this.title = title;
        this.minGpa = minGpa;
        this.field = field;
        this.nationalityRequired = nationalityRequired;
        this.deadline = deadline;
    }

    @Override
    public boolean check(Student s) {
        boolean gpaOk = s.getGpa() >= minGpa;
        boolean fieldOk = field.equalsIgnoreCase("Any") ||
                field.equalsIgnoreCase(s.getField());
        boolean natOk = nationalityRequired.equalsIgnoreCase("Any") ||
                nationalityRequired.equalsIgnoreCase(s.getNationality());
        boolean dateOk = LocalDate.now().isBefore(deadline.plusDays(1));

        return gpaOk && fieldOk && natOk && dateOk;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public double getMinGpa() { return minGpa; }
    public String getField() { return field; }
    public LocalDate getDeadline() { return deadline; }
}

class ScholarshipService {

    HashMap<String, Scholarship> map = new HashMap<>();

    public ScholarshipService() {
        loadScholarships();
    }

    private void loadScholarships() {

        map.put("SCH01", new Scholarship("SCH01", "Engineering Merit Scholarship",
                7.0, "Engineering", "Indian", LocalDate.of(2026, 1, 30)));

        map.put("SCH02", new Scholarship("SCH02", "Arts Talent Grant",
                6.0, "Arts", "Any", LocalDate.of(2025, 12, 31)));

        map.put("SCH03", new Scholarship("SCH03", "Health & Wellness Award",
                7.5, "Health", "Indian", LocalDate.of(2026, 3, 15)));

        map.put("SCH04", new Scholarship("SCH04", "Vocational Skills Scholarship",
                6.0, "Vocational", "Any", LocalDate.of(2026, 6, 10)));

        map.put("SCH05", new Scholarship("SCH05", "Women in Engineering Award",
                8.0, "Engineering", "Any", LocalDate.of(2026, 4, 30)));
    }

    public ArrayList<Scholarship> findEligibleScholarships(Student s) {

        ArrayList<Scholarship> list = new ArrayList<>();

        for (Scholarship sch : map.values()) {

            if (sch.getId().equals("SCH05") &&
                    !s.getGender().equalsIgnoreCase("female")) {
                continue;
            }

            if (sch.check(s)) {
                list.add(sch);
            }
        }
        return list;
    }

    public int[] calculateAmount(Student s) {

        int min, max;

        if (s.getIncome() < 300000) {
            min = 40000; max = 60000;
        } else {
            min = 30000; max = 50000;
        }

        double gpa = s.getGpa();

        if (gpa >= 9) { min += 10000; max += 10000; }
        else if (gpa >= 8) { min += 5000; max += 5000; }
        else if (gpa >= 7) { min += 2500; max += 2500; }

        if (s.getGender().equalsIgnoreCase("female")) {
            min += 5000; max += 5000;
        }

        switch (s.getReservation().toUpperCase()) {
            case "SC" -> { min += 8000; max += 8000; }
            case "ST" -> { min += 7000; max += 7000; }
            case "OBC" -> { min += 5000; max += 5000; }
            case "EWS" -> { min += 3000; max += 3000; }
        }

        switch (s.getSpecialCategory().toLowerCase()) {
            case "athlete" -> { min += 7000; max += 7000; }
            case "minority" -> { min += 8000; max += 8000; }
            case "ex-service-man" -> { min += 10000; max += 10000; }
            case "none" -> {}
        }

        return new int[]{min, max};
    }
}

class FileManager {
    public void save(String summary, Scholarship awarded, int min, int max) {
        try (FileWriter fw = new FileWriter("Scholarship_Result.txt")) {

            fw.write(summary + "\n\n");
            fw.write("Awarded Scholarship:\n");
            fw.write(awarded.getId() + " - " + awarded.getTitle() + "\n");
            fw.write("Amount: ₹" + min + " - ₹" + max + "\n");

            System.out.println("\n✔ Results saved to Scholarship_Result.txt");
        } catch (Exception e) {
            System.out.println("File write error: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ScholarshipService service = new ScholarshipService();
        FileManager fileManager = new FileManager();

        try {

            System.out.println("=== Scholarship Eligibility System ===\n");

            System.out.print("Enter Student Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Age: ");
            int age = Integer.parseInt(sc.nextLine());

            if (age < 17) {
                System.out.println("❌ Sorry, you are not eligible (Age must be ≥ 17)");
                return;
            }

            System.out.print("Enter Gender (Male/Female): ");
            String gender = sc.nextLine();

            System.out.print("Enter Reservation Category (SC/ST/OBC/EWS/General): ");
            String reservation = sc.nextLine();

            System.out.print("Enter GPA (or Percentage): ");
            double val = Double.parseDouble(sc.nextLine());

            double gpa = (val > 10) ? val / 10.0 : val;

            if (gpa < 0 || gpa > 10)
                throw new InvalidInputException("GPA must be 0 - 10");

            System.out.print("Enter Family Income (<500000): ");
            double income = Double.parseDouble(sc.nextLine());

            if (income >= 500000)
                throw new InvalidInputException("Income must be less than ₹5 lakh");

            System.out.println("\nChoose Field:");
            System.out.println("1. Engineering");
            System.out.println("2. Humanities");
            System.out.println("3. Arts");
            System.out.println("4. Health");
            System.out.println("5. Vocational");
            System.out.println("6. Any");

            int f = Integer.parseInt(sc.nextLine());

            String field = switch (f) {
                case 1 -> "Engineering";
                case 2 -> "Humanities";
                case 3 -> "Arts";
                case 4 -> "Health";
                case 5 -> "Vocational";
                default -> "Any";
            };

            System.out.print("Special Category (athlete/minority/ex-service-man/none): ");
            String special = sc.nextLine().toLowerCase();

            Student s = new Student(name, age, gender, reservation, gpa, income, field, special);

            ArrayList<Scholarship> eligible = service.findEligibleScholarships(s);

            if (eligible.isEmpty()) {
                System.out.println("❌ No scholarships available for your profile");
                return;
            }

            eligible.sort((a, b) -> Double.compare(b.getMinGpa(), a.getMinGpa()));

            Scholarship awarded = eligible.get(0);

            int[] amount = service.calculateAmount(s);

            System.out.println("\n🎉 Congratulations, " + s.getName());
            System.out.println("You have been awarded:");
            System.out.println(awarded.getId() + " - " + awarded.getTitle());
            System.out.println("Scholarship Amount: ₹" + amount[0] + " - " + amount[1]);

            fileManager.save(buildSummary(s), awarded, amount[0], amount[1]);

        } catch (InvalidInputException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Unexpected Error: " + e.getMessage());
        }
    }

    public static String buildSummary(Student s) {

        return "STUDENT SUMMARY\n" +
                "Name        : " + s.getName() + "\n" +
                "Age         : " + s.getAge() + "\n" +
                "Gender      : " + s.getGender() + "\n" +
                "Reservation : " + s.getReservation() + "\n" +
                "GPA         : " + s.getGpa() + "\n" +
                "Income      : " + s.getIncome() + "\n" +
                "Field       : " + s.getField() + "\n" +
                "Category    : " + s.getSpecialCategory() + "\n";
    }
}

