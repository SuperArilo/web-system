package online.superarilo.myblog.service;

import online.superarilo.myblog.dto.RegisterUserDTO;
import online.superarilo.myblog.utils.Result;

public interface IRegisterService {

    Result<String> register(RegisterUserDTO userDTO);
}
