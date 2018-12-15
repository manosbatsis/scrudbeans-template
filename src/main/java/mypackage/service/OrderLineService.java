package mypackage.service;


import com.github.manosbatsis.scrudbeans.api.mdd.repository.ModelRepository;
import com.github.manosbatsis.scrudbeans.api.mdd.service.PersistableModelService;
import com.github.manosbatsis.scrudbeans.jpa.mdd.service.AbstractPersistableModelServiceImpl;
import mypackage.model.OrderLine;
import mypackage.model.Product;

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
		// Load the target product
		Product lineProduct = productRepository.getOne(resource.getProduct().getId());
		resource.setProduct(lineProduct);
		// Init product-related properties
		resource.setName(lineProduct.getName());
		resource.setDescription(lineProduct.getDescription());
		resource.setPrice(lineProduct.getPrice());
		return super.create(resource);
	}

	@Autowired
	public void setProductRepository(ModelRepository<Product, String> productRepository) {
		this.productRepository = productRepository;
	}
}
