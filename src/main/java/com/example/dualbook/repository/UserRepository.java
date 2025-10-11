package com.example.dualbook.repository;

import com.example.dualbook.entity.User;
import com.example.dualbook.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // پیدا کردن کاربر فعال با شماره موبایل
    @Query("SELECT u FROM User u WHERE u.mobileNumber = :mobileNumber AND u.disableDate IS NULL")
    Optional<User> findByMobileNumberAndDisabledDateIsNull(@Param("mobileNumber") String mobileNumber);

    // پیدا کردن کاربر (حتی غیرفعال) با شماره موبایل
    Optional<User> findByMobileNumber(String mobileNumber);

    // بررسی وجود کاربر فعال با شماره موبایل
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.mobileNumber = :mobileNumber AND u.disableDate IS NULL")
    boolean existsByMobileNumberAndDisabledDateIsNull(@Param("mobileNumber") String mobileNumber);

    // پیدا کردن تمام کاربران فعال
    @Query("SELECT u FROM User u WHERE u.disableDate IS NULL")
    List<User> findAllActiveUsers();

    // پیدا کردن کاربران غیرفعال
    @Query("SELECT u FROM User u WHERE u.disableDate IS NOT NULL")
    List<User> findAllInactiveUsers();

    // پیدا کردن کاربران بر اساس نقش
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.disableDate IS NULL")
    List<User> findByRoleAndDisableDateIsNull(@Param("role") RoleName role);

    // پیدا کردن کاربران فعال با نام
    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:name% AND u.disableDate IS NULL")
    List<User> findByFullNameContainingAndDisableDateIsNull(@Param("name") String name);
}