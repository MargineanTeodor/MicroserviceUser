package UserBackend.controller;

import UserBackend.DTO.UserDTO;
import UserBackend.Security.TokenProvider;
import UserBackend.model.Token;
import UserBackend.model.User;
import UserBackend.service.ServiceToken;
import UserBackend.service.ServiceUser;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ServiceUser serviceUser;
    @Autowired
    private ServiceToken serviceToken;
    private final TokenProvider tokenProvider = new TokenProvider() ;
    public UserController(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void deleteUser(@RequestParam Long Id, @RequestHeader ("Authorization") String token ) {
        System.out.println(token);
        if(tokenProvider.validateToken(token) && !tokenProvider.getRoleFromToken(token).equals( "normal" )) {
            String uri = "http://localhost:8081/device/deleteUser?Id=" + Id;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.delete(uri);
            serviceUser.deleteUser(Id);
        }
        else
        {
            throw new RuntimeException("invalid token");
        }
    }
    @RequestMapping(value = "/addUser", method = RequestMethod.PUT)
    public void addUser(@Valid @RequestParam String username, String password, String name ) {
            String uri = "http://localhost:8081/device/addUser?username=" + username;
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put(uri, null);
            serviceUser.addUser(name, username, password, "normal");
    }
    @RequestMapping(value = "/normalUsers", method = RequestMethod.GET)
    public List<UserDTO> getNormalUsers (@RequestHeader ("Authorization") String token ){
        if(tokenProvider.validateToken(token) && !tokenProvider.getRoleFromToken(token).equals( "normal" )) {
        return serviceUser.findAllNormalUsers();
        }
        else return new ArrayList<>();
    }
    @RequestMapping(value="/swapPassword",method = RequestMethod.PUT)
    public void changePassword(@RequestParam Long id, String password, @RequestHeader ("Authorization") String token )
    {
        if(tokenProvider.validateToken(token)) {
            serviceUser.updatePassword(password,id);}
    }
    @RequestMapping(value="/swapUsername",method = RequestMethod.PUT)
    public void changeUsername(@RequestParam Long id, String name, @RequestHeader ("Authorization") String token )
    {
        if(tokenProvider.validateToken(token)) {
        serviceUser.updateName(name,id);}
    }
    @RequestMapping(value ="/login")
    public Token login(@RequestParam String username, String password)
    {
        Boolean val = serviceUser.loginCheck(username, password);
        UserDTO user = serviceUser.findUserByUsername(username);
        String token = "Fail";
        if(val == Boolean.TRUE)
            token = tokenProvider.generateToken(user.getId(), user.getRole());
        Token tokenObj = serviceToken.addToken(token);
        return tokenObj;
    }
    @RequestMapping(value = "/findUserById", method = RequestMethod.GET)
    public UserDTO getUserId (@RequestHeader ("Authorization") String token){
        Long id =   parseLong(tokenProvider.getIdFromToken(token));
        return serviceUser.findUserById(id);
    }

    @RequestMapping(value = "/findUserById2", method = RequestMethod.GET)
    public UserDTO getUserId2 (@RequestParam Long Id,@RequestHeader ("Authorization") String token){
        return serviceUser.findUserById(Id);
    }

    @RequestMapping(value = "/listOfUsers", method = RequestMethod.GET)
    public List<UserDTO> getNormalUsers (){
        return serviceUser.findAllUsers();
    }
    @RequestMapping(value = "/findUserByUsername", method = RequestMethod.GET)
    public UserDTO getUser (@RequestParam String username){
        return serviceUser.findUserByUsername(username);
    }
}
