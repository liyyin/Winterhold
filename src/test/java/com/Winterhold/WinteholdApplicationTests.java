package com.Winterhold;

import com.Winterhold.dto.category.CreateCategoryDTO;
import com.Winterhold.entity.Category;
import com.Winterhold.service.absract.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WinteholdApplicationTests {

	@Autowired
	CategoryService categoryService;

	@Test
	void testAddCategory(){
		var category = new CreateCategoryDTO("scientific",3,"A","Z");
		Category categorySave = null;
		try {
			categoryService.save(category);
			categorySave = new Category(category.getCategoryName(), category.getFloor(), category.getIsle(), category.getBay());
		}catch (Exception ex){
			categorySave = null;
		}
		assertNotNull(categorySave);
		assertEquals("scientific",categorySave.getName());
		assertEquals(3,categorySave.getFloor());
		assertEquals("A",categorySave.getIsle());
		assertEquals("Z",categorySave.getBay());
	}

	@Test
	void contextLoads() {

	}

}
