package ru.students.tpu.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import ru.students.tpu.entity.Group;

import java.util.Optional;
import java.util.UUID;

@RegisterBeanMapper(Group.class)
public interface GroupDao {
    @SqlUpdate("""
            insert into groups (id, name, department, enrollment_year)
            values (:id, :name, :department, :enrollmentYear)
            """)
    @GetGeneratedKeys
    UUID addGroup(@BindBean Group group);

    @SqlQuery("""
            select * from groups where id = :id
            """)
    Optional<Group> findByGroupId(UUID id);
}
