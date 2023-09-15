package pl.edu.agh.webChess.dao;

import pl.edu.agh.webChess.entity.Role;

public interface RoleDAO {

    Role findByName(String name);


}
