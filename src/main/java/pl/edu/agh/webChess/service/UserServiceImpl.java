package pl.edu.agh.webChess.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.webChess.dao.RoleDAO;
import pl.edu.agh.webChess.dao.UserDAO;
import pl.edu.agh.webChess.dao.UserStatisticsDAO;
import pl.edu.agh.webChess.entity.Role;
import pl.edu.agh.webChess.entity.User;
import pl.edu.agh.webChess.entity.UserStatistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@Service
public class UserServiceImpl implements UserService{

    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private UserStatisticsDAO userStatisticsDAO;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, UserStatisticsDAO userStatisticsDAO, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.userStatisticsDAO = userStatisticsDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findUserByName(String name) {
        return userDAO.findByName(name);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleDAO.findByName(name);
    }

    @Override
    @Transactional
    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setRoles(Arrays.asList(roleDAO.findByName("ROLE_USER")));
        user.setUserStatistics(new UserStatistics(0,0,0,1000));

        userDAO.save(user);
    }

    @Override
    public UserStatistics findUserStatisticsByUserName(String name) {
        return userStatisticsDAO.findUserStatisticsByUserName(name);
    }

    @Override
    public long findUserRankingByUserName(String name) {
        return userStatisticsDAO.findRowNumberByUserNameSortByPoints(name);
    }

    @Override
    public Collection<User> findTop10UsersByPoints() {
        return userDAO.find10UsersSortByPointsDesc();
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDAO.findByName(userName);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        Collection<SimpleGrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                authorities);
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getName());
            authorities.add(tempAuthority);
        }

        return authorities;
    }
}
