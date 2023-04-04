package kr.onthelive.basic.ws.repository.mapper;

import kr.onthelive.basic.ws.model.User;
import kr.onthelive.basic.ws.model.support.UserType;

import java.util.List;

public interface UserMapper {
    User selectUser(String id);
    List<User> selectUsersWhereType(UserType type);
    int insertUser(User account);
}
