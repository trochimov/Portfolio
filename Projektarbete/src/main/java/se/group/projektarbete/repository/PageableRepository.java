package se.group.projektarbete.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import se.group.projektarbete.data.User;

@NoRepositoryBean
public interface PageableRepository<T> extends PagingAndSortingRepository<T, Long> {

}
