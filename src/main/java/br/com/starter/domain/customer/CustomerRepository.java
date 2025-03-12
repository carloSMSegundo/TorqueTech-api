    package br.com.starter.domain.customer;

    import br.com.starter.domain.user.User;
    import br.com.starter.domain.user.UserStatus;
    import br.com.starter.domain.work.WorkStatus;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Optional;
    import java.util.Set;
    import java.util.UUID;

    public interface CustomerRepository extends JpaRepository<Customer, UUID> {

        @Query("""
            SELECT c FROM Customer c
            WHERE c.garage.id = :garageId
            AND c.status = :status
            AND (
                :query IS NULL
                OR LOWER(c.profile.name) LIKE LOWER(CONCAT('%', :query, '%'))
            )
        """)
        Page<Customer> findPageByStatusAndProfileName(
            @Param("garageId") UUID garageId,
            @Param("query") String query,
            @Param("status") UserStatus status,
            Pageable pageable
        );

        @Query("""
            SELECT c FROM Customer c
            WHERE c.garage.id = :garageId
            AND c.status = :status
        """)
        List<Customer> findAllByGarageIdAndStatus(
            @Param("garageId") UUID garageId,
            @Param("status") UserStatus status
        );

        @Query("""
            SELECT c FROM Customer c
            WHERE c.garage.id = :garageId
            AND c.id = :customerId
        """)
        Optional<Customer> findAllByGarageId(
            @Param("garageId") UUID garageId,
            @Param("customerId") UUID customerId
        );

        int countByGarageId(UUID garageId);

    }
