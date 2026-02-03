package com.ordering.mvc.repository.category;

import com.ordering.mvc.model.category.CategoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryInfo, UUID> {
}
