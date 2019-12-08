package myjavapackage.service;


import com.github.manosbatsis.scrudbeans.common.repository.ModelRepository;
import com.github.manosbatsis.scrudbeans.common.service.PersistableModelService;
import com.github.manosbatsis.scrudbeans.jpa.service.AbstractPersistableModelServiceImpl;
import myjavapackage.model.OrderLine;
import myjavapackage.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLineService extends AbstractPersistableModelServiceImpl<OrderLine, String, ModelRepository<OrderLine, String>>
		implements PersistableModelService<OrderLine, String> {

	private ModelRepository<Product, String> productRepository;

	/**
	 * {@inheritDoc}
	 * @param resource
	 */
	@Override
	public OrderLine create(OrderLine resource) {
		return super.create(resource);
	}

	@Autowired
	public void setProductRepository(ModelRepository<Product, String> productRepository) {
		this.productRepository = productRepository;
	}
}
