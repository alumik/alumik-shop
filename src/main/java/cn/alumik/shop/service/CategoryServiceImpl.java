package cn.alumik.shop.service;

import cn.alumik.shop.dao.CategoryRepository;
import cn.alumik.shop.dao.ItemRepository;
import cn.alumik.shop.entity.Category;
import cn.alumik.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ItemRepository itemRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(String name, Pageable pageable) {
        return categoryRepository.findAllByNameContains(name, pageable);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public void deleteById(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        categoryOptional.ifPresent(category -> {
            Set<Item> items = category.getItems();
            for (Item item : items) {
                item.setCategory(null);
                itemRepository.save(item);
            }
        });
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }
}
