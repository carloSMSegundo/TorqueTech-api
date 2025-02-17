    package br.com.starter.domain.customer;

    import br.com.starter.domain.user.User;
    import br.com.starter.domain.user.UserStatus;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;

    import java.util.UUID;

    public interface CustomerRepository extends JpaRepository<Customer, UUID> {
        Page<Customer> findByOwner(User owner, Pageable pageable);

        Page<Customer> findByProfile_Name(String profileName, Pageable pageable);

        Page<Customer> findByStatus(UserStatus status, Pageable pageable);
    }
