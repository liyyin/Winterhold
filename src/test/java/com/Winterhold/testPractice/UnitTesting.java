    package com.Winterhold.testPractice;

    import com.Winterhold.dao.CategoryRepository;
    import com.Winterhold.dto.category.CategoryDTO;
    import com.Winterhold.dto.category.CreateCategoryDTO;
    import com.Winterhold.entity.Category;
    import com.Winterhold.service.implementation.CategoryImplementation;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.junit.jupiter.api.extension.ExtendWith;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.Mockito;
    import org.mockito.junit.jupiter.MockitoExtension;

    import java.util.ArrayList;
    import java.util.List;

    import static org.assertj.core.api.Assertions.assertThat;
    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    public class UnitTesting {

        @Mock
        private CategoryRepository categoryRepository;

        @InjectMocks
        private CategoryImplementation categoryImplementation;
        private Category category;
        private List<Category> categoryList;

        @BeforeEach
        void createObj(){
            category = new Category("scientific",3,"A","Z");
            categoryList = new ArrayList<>();
        }

        @Test
        void getDataText(){
            Mockito.lenient().when(categoryRepository.findAll()).thenReturn(categoryList);
        }

        @Test
        public void testAddDataCategory(){
            Mockito.lenient().when(categoryRepository.save(any(Category.class))).thenReturn(category);
//            assertThat(category).isNotNull();
            CreateCategoryDTO dto = new CreateCategoryDTO("scientific",3,"A","Z");
            categoryImplementation.save(dto);

            assertThat(category).isNotNull();
            assertThat(category.getName()).isEqualTo(dto.getCategoryName());

        }
    }
