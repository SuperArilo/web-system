package online.superarilo.myblog.service;

import online.superarilo.myblog.dto.UserDTO;
import online.superarilo.myblog.utils.Result;

public interface IRegisterService {

    Result<String> register(UserDTO userDTO);
}
