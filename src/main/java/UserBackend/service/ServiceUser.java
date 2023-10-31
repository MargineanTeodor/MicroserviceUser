package UserBackend.service;

import UserBackend.DTO.UserDTO;
import UserBackend.mapper.UserMapper;
import UserBackend.model.User;
import UserBackend.repository.UserRepository;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.List;
@Service
public class ServiceUser {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public ServiceUser(UserRepository userRepository, UserMapper userMapper)
    {
        this.userRepository= userRepository;
        this.userMapper = userMapper;
    }
    public UserDTO findUserByUsername(String username)
    {
        User userFounded = userRepository.findByUsername(username);
        if(userFounded.getId()!=null)
            return userMapper.mapModelToDto(userFounded);
        return null;
    }
    public List<UserDTO> findAllNormalUsers()
    {
        List<User> listNormalUser = userRepository.findAllByRole("normal");
        List<UserDTO> listNormalUserDTO = new ArrayList<UserDTO>();
        for(User user: listNormalUser)
        {
            listNormalUserDTO.add(UserMapper.mapModelToDto(user));
        }
        return listNormalUserDTO;
    }
    public void addUser(String name,String username, String password, String role) {

        User user =new User();
        user.setName(name);
        user.setRole(role);
        user.setPassword(password);
        user.setUsername(username);
        userRepository.save(user);
    }
    public void deleteUser(Long id) {
        try {
            User x = userRepository.findUserById(id);
            userRepository.delete(x);
        } catch (Exception e) {
            System.out.println("User not found");
        }
    }
    public void updatePassword(String password, Long id) {
        User x= userRepository.findUserById(id);
        x.setPassword(password);
        userRepository.save(x);
    }
    public void updateName(String name, Long id) {
        User x= userRepository.findUserById(id);
        x.setName(name);
        userRepository.save(x);
    }
    private static byte[] getKey() {
        return new byte[]{0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f};
    }
    private static String encript(String password)
    {
        try {
            // Get the key to use for encrypting the data
            byte[] key = getKey();
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(password.getBytes());
            return new String(encryptedBytes);
        }   catch (Exception e) {
            e.printStackTrace();
            return "Fail";
        }
    }
    public Boolean loginCheck(String username, String password)
    {
        UserDTO user = this.findUserByUsername(username);
        System.out.println(user);
        if(user.getPassword().equals(password))
            return Boolean.TRUE;
            System.out.println("True");
        return Boolean.FALSE;
    }
    public List<UserDTO> findAllUsers()
    {
        List<User> listOfUsers = userRepository.findAll();
        List<UserDTO> DTOList = new ArrayList<UserDTO>();
        for (User user: listOfUsers) {
            DTOList.add(userMapper.mapModelToDto(user));
        }
        return DTOList;
    }

    public UserDTO findUserById(Long id) {
        return userMapper.mapModelToDto(userRepository.findUserById(id));
    }
}
