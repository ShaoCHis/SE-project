package com.example.sebackend.controller;

import com.example.sebackend.error.UserNotExistedError;
import com.example.sebackend.model.Student;
import com.example.sebackend.repository.StudentRepository;
import com.example.sebackend.service.AuthenticationService;
import com.example.sebackend.utils.Result;
import com.example.sebackend.views.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api/Login")
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private StudentRepository studentRepository;
    /**
     * 返回内容中，“1”表示学生，“2”表示审阅者，“3”表示管理员
     * @param body
     * @return
     */
    @PostMapping(path = "/session")
    public Result<String> getLogin(@RequestBody Login body, HttpServletResponse response){
        String sessionId=authenticationService.createSessionId(body.getEmail(),body.getPassword(), body.getType());
        if(sessionId==null){
            response.setStatus(401);
            return Result.wrapErrorResult(new UserNotExistedError());
        }
        ResponseCookie responseCookie = ResponseCookie.from("sessionId", sessionId)
                .maxAge(3* 24* 60 * 60)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());
        return Result.wrapSuccessfulResult("Login success!");
    }

    @GetMapping(path = "/sensitize") // Map ONLY POST Requests
    public @ResponseBody
    Result<String> sensitize (@RequestBody Login body) {
        Student student=studentRepository.findByEmail(body.getEmail());
        if(student==null)
            return Result.wrapErrorResult(new UserNotExistedError());
        if(student.getStatus()==1)
            return Result.wrapErrorResult("用户已激活！");
        student.setStatus(1);
        studentRepository.save(student);
        return Result.wrapSuccessfulResult("激活成功！");
    }
}
