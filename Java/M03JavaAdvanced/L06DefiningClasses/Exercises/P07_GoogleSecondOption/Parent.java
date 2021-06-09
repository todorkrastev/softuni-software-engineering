package bg.softuni.java_advanced.defining_classes.exercises.P07_GoogleSecondOption;

public class Parent {
    private final String name;
    private final String birthday;

    public Parent(String name, String birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String format() {
        return String.format("%s %s", name, birthday);
    }
}
