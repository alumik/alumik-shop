package cn.alumik.shop.service;

import cn.alumik.shop.dao.RoleRepository;
import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.Role;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public AdminServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<User> findAll(String username, String roleName, Pageable pageable) {
        Page<User> admins = userRepository.findAllByUsernameContainingAndRoles_Name(username, roleName, pageable);
        for (User admin : admins) {
            admin.setIsSuperAdmin(false);
            for (Role role : admin.getRoles()) {
                if (role.getName().equals("ROLE_SUPER_ADMIN")) {
                    admin.setIsSuperAdmin(true);
                    break;
                }
            }
        }
        return admins;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void toggleSuperAdmin(User user) {
        Optional<Role> roleSuperAdmin = roleRepository.findByName("ROLE_SUPER_ADMIN");
        Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
        if (roleSuperAdmin.isPresent() && roleAdmin.isPresent() && roleUser.isPresent()) {
            if (user.getRoles().contains(roleSuperAdmin.get())) {
                user.removeRole(roleSuperAdmin.get());
            } else {
                user.addRole(roleAdmin.get());
                user.addRole(roleSuperAdmin.get());
                user.removeRole(roleUser.get());
            }
            userRepository.save(user);
        }
    }

    @Override
    public void toggleAdmin(User user) {
        Optional<Role> roleSuperAdmin = roleRepository.findByName("ROLE_SUPER_ADMIN");
        Optional<Role> roleAdmin = roleRepository.findByName("ROLE_ADMIN");
        Optional<Role> roleUser = roleRepository.findByName("ROLE_USER");
        if (roleSuperAdmin.isPresent() && roleAdmin.isPresent() && roleUser.isPresent()) {
            if (user.getRoles().contains(roleAdmin.get())) {
                user.removeRole(roleSuperAdmin.get());
                user.removeRole(roleAdmin.get());
                user.addRole(roleUser.get());
            } else {
                user.addRole(roleAdmin.get());
                user.removeRole(roleUser.get());
            }
            userRepository.save(user);
        }
    }

    @Override
    public Page<User> findAllNormalUser(String username, Pageable pageable) {
        return userRepository.findAllByUsernameContainingAndRoles_Name(username, "ROLE_USER", pageable);
    }
}
