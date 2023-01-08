package io.octagram.domain.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Accessors(fluent = true)
@ToString
public abstract class BaseEntity {
    @Version
    protected Long version;

    @Id
    protected String id;

    @LastModifiedDate
    protected LocalDateTime lastModified;

    @CreatedDate
    protected LocalDateTime dateCreated;

    public BaseEntity() {
        this.lastModified = LocalDateTime.now();
        this.dateCreated = LocalDateTime.now();
    }

    public BaseEntity created() {
        this.lastModified = LocalDateTime.now();
        this.dateCreated = LocalDateTime.now();
        return this;
    }

    public BaseEntity modified() {
        this.lastModified = LocalDateTime.now();
        return this;
    }

    public abstract Object toDto();

}
