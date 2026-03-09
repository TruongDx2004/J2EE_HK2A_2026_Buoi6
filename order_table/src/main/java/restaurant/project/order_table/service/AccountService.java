package restaurant.project.order_table.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import restaurant.project.order_table.model.Account;
import restaurant.project.order_table.reponsitory.AccountReponsitory;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountReponsitory accountReponsitory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("=== LOGIN DEBUG ===");
        System.out.println("Username nhập: " + username);

        Account account = accountReponsitory.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ Không tìm thấy user trong database");
                    return new UsernameNotFoundException("User not found: " + username);
                });

        System.out.println("✔ User tìm thấy: " + account.getUsername());
        System.out.println("Password DB: " + account.getPassword());

        System.out.println("Roles:");
        account.getRoles().forEach(role -> System.out.println(" - " + role.getName()));

        Set<SimpleGrantedAuthority> authorities = account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        System.out.println("Authorities tạo cho Spring Security:");
        authorities.forEach(a -> System.out.println(" - " + a.getAuthority()));

        System.out.println("===================");

        return new User(account.getUsername(), account.getPassword(), authorities);
    }

}
