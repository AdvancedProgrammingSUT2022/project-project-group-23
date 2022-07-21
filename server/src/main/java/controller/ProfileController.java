package controller;

import com.google.gson.Gson;
import model.Request;
import model.User;

import java.io.FileWriter;
import java.io.IOException;

public class ProfileController {
    private static ProfileController instance;

    private ProfileController(){

    }

    public static ProfileController getInstance() {
        if(instance==null) {
            instance=new ProfileController();
        }
        return instance;
    }

    public String changeNickname(Request request, User currentUser) {
        String nickname = request.getInfo().get("nickname");
        for (User user : User.getUsers()) {
            if(user.getNickname().equals(nickname) && !user.equals(currentUser)) {
                return "user with nickname "+nickname+" already exists";
            }
        }
        currentUser.setNickname(nickname);
        User.updateUsersInfo();
        return "nickname changed successfully!";
    }


    public String changePassword(Request request, User currentUser) {
        String currentPassword = request.getInfo().get("currentPassword");
        String newPassword = request.getInfo().get("newPassword");
        if(!currentUser.getPassword().equals(currentPassword))
            return "current password is invalid";
        if(currentUser.getPassword().equals(newPassword))
            return "please enter a new password";
        currentUser.setPassword(newPassword);
        User.updateUsersInfo();
        return "password changed successfully!";
    }
    public String changeProfilePicture(Request request, User currentUser){
        currentUser.setProfilePictureURL(request.getInfo().get("URL"));
        return "Profile picture changed successfully";
    }
}
