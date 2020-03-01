package myjavapackage.controller;


import com.github.manosbatsis.scrudbeans.api.exception.NotImplementedException;
import com.github.manosbatsis.scrudbeans.api.util.Mimes;
import com.github.manosbatsis.scrudbeans.controller.AbstractPersistableModelController;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import myjavapackage.model.OrderLine;
import myjavapackage.service.OrderLineService;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller for order lines that allows search, create and delete but removes support for update and patch.
 */
@RestController
@RequestMapping(value = {"/api/rest/orderLines"},
		consumes = {MimeTypeUtils.APPLICATION_JSON_VALUE, Mimes.MIME_APPLICATIOM_HAL_PLUS_JSON_VALUE},
		produces = {MimeTypeUtils.APPLICATION_JSON_VALUE, Mimes.MIME_APPLICATIOM_HAL_PLUS_JSON_VALUE})
@ExposesResourceFor(OrderLine.class)
@OpenAPIDefinition(tags = @Tag(name = "Order Lines", description = "Search, create and delete order lines"))
public class OrderLineController extends AbstractPersistableModelController<OrderLine, String, OrderLineService> {

	/** Disable update */
	@Override
	public OrderLine update(String id, OrderLine resource) {
		throw new NotImplementedException("Method is unsupported.");
	}

	/** Disable patch */
	@Override
	public OrderLine patch(String id, OrderLine resource) {
		throw new NotImplementedException("Method is unsupported.");
	}
}
