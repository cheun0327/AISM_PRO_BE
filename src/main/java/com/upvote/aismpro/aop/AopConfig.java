package com.upvote.aismpro.aop;

import com.upvote.aismpro.dto.UserDTO;
import com.upvote.aismpro.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.concurrent.Future;

@Aspect
@Component
public class AopConfig {

    @Autowired
    private UserService userService;

    //@Around("execution(* com.upvote.aismpro.controller.UserController.uploadProfileImg(..))")
    public Object uploadImg(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("!!AOP!!");

        Object[] params = joinPoint.getArgs();
        String userId = (String) params[0];
        MultipartFile file = (MultipartFile) params[1];


        String[] imgNameArr = file.getOriginalFilename().split("\\.");
        String imgFolder = userId.replaceAll("-", "");
        String imgName = imgFolder + "." + imgNameArr[imgNameArr.length - 1];
        String userDirPath = "/Users/upvote3/chaeeun/dev/react-workspace/AISM_PRO_FE/src/components/content/image/user/" + imgFolder;
//        String dirPath = "/var/lib/jenkins/workspace/AISM_PRO_REACT/src/components/content/image/user/" + imgFolder;
        File userDir = new File(userDirPath);

        try {
            if (!userDir.exists()) {
                userDir.mkdir();
            }

            String userProfileDirPath = userDirPath + "/profile";
            File userProfileDir = new File(userProfileDirPath);

            if (!userProfileDir.exists()) {
                System.out.println("프로필 디렉토리 생성");
                userProfileDir.mkdir();
            } else {
                System.out.println("프로필 디렉토리 내용물 삭제");
                File[] files = userProfileDir.listFiles(); //파일리스트 얻어오기
                for (File f : files) f.delete(); //파일 삭제
            }

            System.out.println(userProfileDir.listFiles().length);

            file.transferTo(new File(userProfileDirPath + "/" + imgName));

            userService.setProfile(userId, imgFolder + "/profile/" + imgName);

            Object returnVal = new Object();
            while(true) {
                System.out.println(userProfileDir.listFiles());
                if (userProfileDir.listFiles().length != 0){
                    System.out.println(userProfileDir.listFiles().length);
                    returnVal = joinPoint.proceed();
                    break;
                }
            }
            return returnVal;

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }
}
