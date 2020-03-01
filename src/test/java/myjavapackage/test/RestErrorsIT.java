package myjavapackage.test;

import com.github.manosbatsis.scrudbeans.api.error.ConstraintViolationEntry;
import com.github.manosbatsis.scrudbeans.test.AbstractRestAssuredIT;
import lombok.extern.slf4j.Slf4j;
import myjavapackage.ScrudBeansSampleApplication;
import myjavapackage.model.DiscountCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ScrudBeansSampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestErrorsIT extends AbstractRestAssuredIT {

	@Test
	public void testNotFound() {
        SimpleErrorResponse error = given()
                .spec(defaultSpec())
                .get("api/rest/products/invalid")
                .then()
                .statusCode(404).extract().as(SimpleErrorResponse.class);

        assertEquals("Not Found", error.getMessage());
        assertEquals(404, error.getHttpStatusCode().intValue());
    }

	@Test
	public void testBadRequestNotNullConstraint() {

        DiscountCode discountCode = new DiscountCode();
        discountCode.setCode("DISCOUNT_1");

        SimpleErrorResponse error = given()
                .spec(defaultSpec())
                .body(discountCode)
                .post("api/rest/discountCodes")
                .then()
                .statusCode(400).extract().as(SimpleErrorResponse.class);

        assertEquals("Validation failed", error.getMessage());
        assertEquals(400, error.getHttpStatusCode().intValue());
        Set<ConstraintViolationEntry> violations = error.getValidationErrors();
        assertEquals(1, violations.size());
        ConstraintViolationEntry violation = violations.iterator().next();
        assertEquals("must not be null", violation.getMessage());
        assertEquals("percentage", violation.getPropertyPath());
    }

	/**
	 * Test meaningful messages in constraints validation
	 */
	@Test
	public void testBadRequestUniqueConstraint() {

		DiscountCode discountCode1 = new DiscountCode();
		discountCode1.setCode("DISCOUNT_1");
		discountCode1.setPercentage(10);

		discountCode1 = given()
				.spec(defaultSpec())
				.body(discountCode1)
				.post("/api/rest/discountCodes")
				.then()
				.statusCode(201).extract().as(DiscountCode.class);

        DiscountCode discountCode2 = new DiscountCode();
        discountCode2.setCode("DISCOUNT_1");
        discountCode2.setPercentage(10);
        SimpleErrorResponse error = given()
                .spec(defaultSpec())
                .body(discountCode2)
                .post("/api/rest/discountCodes")
                .then()
                .statusCode(400).extract().as(SimpleErrorResponse.class);

        assertEquals("Validation failed", error.getMessage());
        assertEquals(400, error.getHttpStatusCode().intValue());
        Set<ConstraintViolationEntry> violations = error.getValidationErrors();
        assertEquals(1, violations.size());
        ConstraintViolationEntry violation = violations.iterator().next();
        assertEquals("Unique value not available for property: code", violation.getMessage());
        assertEquals("code", violation.getPropertyPath());
    }

}
