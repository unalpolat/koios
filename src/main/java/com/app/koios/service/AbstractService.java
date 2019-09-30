package com.app.koios.service;

import com.app.koios.entity.CoreEntity;
import com.app.koios.exception.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 * @author unalpolat
 */
public abstract class AbstractService<CE extends CoreEntity, CR extends CrudRepository<CE, Long>> {

	private final CR repository;

	public AbstractService(CR repository) {
		this.repository = repository;
	}

	public CE getById(Long id) throws EntityNotFoundException {
		return repository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public CE create(CE item) {
		return repository.save(item);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public List<CE> findAll() throws EntityNotFoundException {
		List<CE> allList = new ArrayList<>();
		Iterable<CE> allIterable = repository.findAll();
		allIterable.forEach(allList::add);
		if (allList.isEmpty()) {
			throw new EntityNotFoundException();
		}
		return allList;
	}
}
