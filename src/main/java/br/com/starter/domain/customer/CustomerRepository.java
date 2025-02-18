    package br.com.starter.domain.customer;

    import br.com.starter.domain.user.User;
    import br.com.starter.domain.user.UserStatus;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.Optional;
    import java.util.UUID;

    public interface CustomerRepository extends JpaRepository<Customer, UUID> {
        Page<Customer> findByOwner(User owner, Pageable pageable);

        @Query("""
        SELECT c FROM Customer c
        WHERE c.owner.status = :status
        AND (
            :query IS NULL
            OR LOWER(c.profile.name) LIKE %:query%
        )
    """)
        Page<Customer> findPageByStatusAndProfileName(
                @Param("query") String query,
                @Param("status") UserStatus status,
                Pageable pageable
        );
    }
