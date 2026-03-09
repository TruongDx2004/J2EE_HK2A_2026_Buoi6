package restaurant.project.order_table.reponsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import restaurant.project.order_table.model.Account;

@Repository
public interface AccountReponsitory extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE a.username = ?1")
    Optional<Account> findByUsername(String username);

}
