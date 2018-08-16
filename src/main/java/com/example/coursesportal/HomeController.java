package com.example.coursesportal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String processRegistrationPage(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()) {
            return "registration";
        } else {
            userService.saveUser(user);
            model.addAttribute("message",
                    "User Account Successfully Created");
        }
        return "index";
    }

    @RequestMapping("/")
    public String homePage(){
        return "index";
    }
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    @RequestMapping("/secure")
    public String secure(Model model) {
//        Boolean isAdmin = request.isUserInRole("ADMIN");
//        Boolean isUser = request.isUserInRole("USER");
//        UserDetails userDetails = (UserDetails)
//                authentication.getPrincipal();
//        String username = userService.getCurrentUser().getUsername();
//       // model.addAttribute("tasks", taskRepository.findByUsername(username));
        model.addAttribute("user", userService.getCurrentUser());
        return "secure";
    }
    @RequestMapping("/list")
    public String listCourse(Model model){
        String username = userService.getCurrentUser().getUsername();
        model.addAttribute("courses", courseRepository.findByUsername(username));
        return "list";
    }
    @GetMapping("/add")
    public String courseForm(Model model){
        model.addAttribute("instructors", instructorRepository.findAll());
        model.addAttribute("course", new Course());
        return "courseform";
    }
    @PostMapping("/process")
    public String processForm(@ModelAttribute @Valid Course course, @RequestParam long instructorId, BindingResult result)
    {
        if (result.hasErrors()){
            return "courseform";
        }
        Instructor ins = instructorRepository.findById(instructorId);
        course.setInstructor(ins);
        String username = userService.getCurrentUser().getUsername();
        course.setUsername(username);
        courseRepository.save(course);
        return "redirect:/list";
    }
    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("course", courseRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model){
        model.addAttribute("instructors", instructorRepository.findAll());
        model.addAttribute("course", courseRepository.findById(id).get());
        return "courseform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        courseRepository.deleteById(id);
        return "redirect:/list";
    }
    @GetMapping("/addi")
    public String instructorForm(Model model){
        model.addAttribute("instructor", new Instructor());
        return "instructorform";
    }
    @PostMapping("/process2")
    public String processForm(@ModelAttribute @Valid Instructor instructor, BindingResult result)
    {
        if (result.hasErrors()){
            return "instructorform";
        }
        instructorRepository.save(instructor);
        return "redirect:/list";
    }
}