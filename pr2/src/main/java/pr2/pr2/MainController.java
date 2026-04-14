package pr2.pr2;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/students")
public class MainController {
    
    private List<Student> students = new ArrayList<>();

    // Показати список студентів з можливістю пошуку
    @GetMapping
    public String showStudents(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Student> filteredStudents;

        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerCaseKeyword = keyword.toLowerCase();
            // Фільтруємо список за ім'ям, email або ID
            filteredStudents = students.stream()
                    .filter(s -> s.getName().toLowerCase().contains(lowerCaseKeyword) ||
                                 s.getEmail().toLowerCase().contains(lowerCaseKeyword) ||
                                 s.getId().toLowerCase().contains(lowerCaseKeyword))
                    .toList();
        } else {
            // Якщо пошукового запиту немає, показуємо всіх
            filteredStudents = students;
        }

        model.addAttribute("students", filteredStudents);
        model.addAttribute("keyword", keyword); // Зберігаємо запит, щоб показати його у формі
        return "students";
    }

    // Показати форму додавання
    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("student", new Student());
        return "add-student";
    }

    // Обробка збереження нового студента
    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("student") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Якщо є помилки, повертаємо сторінку з формою, не виконуючи redirect
            return "add-student";
        }
        students.add(student);
        return "redirect:/students";
    }
    
    // Показати форму редагування для конкретного студента
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Student studentToEdit = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (studentToEdit != null) {
            model.addAttribute("student", studentToEdit);
            return "edit-student"; // Повертає новий шаблон
        }
        return "redirect:/students";
    }

    // Обробка збереження змін після редагування
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable String id, @Valid @ModelAttribute("student") Student updatedStudent, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "edit-student";
        }

        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                updatedStudent.setId(id);
                students.set(i, updatedStudent);
                break;
            }
        }
        return "redirect:/students";
    }

    // Видалення студента
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id) {
        students.removeIf(student -> student.getId().equals(id));
        return "redirect:/students";
    }

    // Показати деталі студента
    @GetMapping("/details/{id}")
    public String showDetails(@PathVariable String id, Model model) {
        Student studentDetails = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (studentDetails != null) {
            model.addAttribute("student", studentDetails);
            return "student-details"; // Повертає новий шаблон
        }
        return "redirect:/students";
    }
}