package pr2.pr2;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public class Student {
    private String id;

    @NotBlank(message = "Ім'я не може бути порожнім")
    private String name;

    @Min(value = 17, message = "Вік повинен бути більшим за 16")
    private int age;

    @NotBlank(message = "Email не може бути порожнім")
    @Email(message = "Некоректний формат email")
    private String email;

    private String details;

    public Student() {
        this.id = UUID.randomUUID().toString(); // Автоматична генерація ID
    }

    public Student(String name, int age, String email, String details) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
        this.email = email;
        this.details = details;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}