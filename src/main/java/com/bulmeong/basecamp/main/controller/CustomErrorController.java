// package com.bulmeong.basecamp.main.controller;

// import org.springframework.boot.web.error.ErrorAttributeOptions;
// import org.springframework.boot.web.servlet.error.ErrorAttributes;
// import org.springframework.boot.web.servlet.error.ErrorController;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.context.request.WebRequest;

// import java.util.Map;

// @Controller
// public class CustomErrorController implements ErrorController {

//     private final ErrorAttributes errorAttributes;

//     public CustomErrorController(ErrorAttributes errorAttributes) {
//         this.errorAttributes = errorAttributes;
//     }

//     @RequestMapping("/error")
//     public String handleError(WebRequest webRequest, Model model) {
//         Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
//         int statusCode = (int) errorDetails.get("status");

//         if (statusCode == 404) {
//             return "404";  // templates/404.html
//         }else{
//             return "error";  // templates/error.html
//         }
//     }

// }