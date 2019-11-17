package cn.alumik.shop.service;

import cn.alumik.shop.dao.ItemRepository;
import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{
    private ItemRepository itemRepository;
    private SecurityService securityService;
    private UserRepository userRepository;

    public ItemServiceImpl(ItemRepository itemRepository, SecurityService securityService, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.securityService = securityService;
        this.userRepository = userRepository;
    }

    @Override
    public void save(Item item, String filename) {
        String username = securityService.findLoggedInUsername();
        User user = userRepository.findByUsername(username);
        item.setSeller(user);
        item.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        item.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        item.setAvailable(true);
        item.setPic(filename);
        itemRepository.save(item);
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item getById(int id) {
        return itemRepository.findById(id).get();
    }

    @Override
    public Page<Object []> findAll(String name, int pageNum, int pageSize, Sort sort){
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return itemRepository.findAllByNameContainsSell(name, pageable);
    }

    @Override
    public Page<Object[]> findAll(String name, int categoryId, int pageNum, int pageSize, Sort sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        return itemRepository.findAllByNameContainsAndCategory_IdAndSell(name, categoryId, pageable);
    }

    @Override
    public List<Object[]> findAllOrderByRand(String name, int size) {
        return itemRepository.findAllByNameContainsSellOrderByRand(name, size);
    }

    @Override
    public Page<Item> findAllBySeller(int pageNum, int pageSize, Sort sort){
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        return itemRepository.findAllBySeller(user, pageable);
    }

    @Override
    public Optional<Item> findById(Integer id) {
        return itemRepository.findById(id);
    }

    @Override
    public void toggleAvailable(Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setAvailable(!item.getAvailable());
            itemRepository.save(item);
        }
    }

    @Override
    public Page<Item> findAll(Integer categoryId, String name, String sellerName, Integer available, Pageable pageable) {
        if (categoryId != 0) {
            if (available != -1) {
                return itemRepository.findAllByCategory_IdAndNameContainsAndSeller_UsernameContainsAndAvailable(categoryId, name, sellerName, available != 0, pageable);
            }
            return itemRepository.findAllByCategory_IdAndNameContainsAndSeller_UsernameContains(categoryId, name, sellerName, pageable);
        }
        if (available != -1) {
            return itemRepository.findAllByNameContainsAndSeller_UsernameContainsAndAvailable(name, sellerName, available != 0, pageable);
        }
        return itemRepository.findAllByNameContainsAndSeller_UsernameContains(name, sellerName, pageable);
    }

    @Override
    public void save(Item item) {
        String username = securityService.findLoggedInUsername();
        User user = userRepository.findByUsername(username);
        item.setSeller(user);
        item.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        itemRepository.save(item);
    }

    @Override
    public Page<Item> findAllByFavoriteBy(int pageNum, int pageSize, Sort sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        return itemRepository.findAllByFavoriteBy(user, pageable);
    }

    @Override
    public void addFavoriteItem(Item item) {
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        user.addFavoriteItem(item);
        userRepository.save(user);
    }

    @Override
    public void delFavoriteItem(Item item) {
        User user = userRepository.findByUsername(securityService.findLoggedInUsername());
        user.removeFavoriteItem(item);
        userRepository.save(user);
    }

    @Override
    public Page<Item> findAll(User user, String name, Integer categoryId, Pageable pageable) {
        if (categoryId != 0) {
            return itemRepository.findAllByFavoriteByAndNameContainsAndCategory_Id(user, name, categoryId, pageable);
        }
        return itemRepository.findAllByFavoriteByAndNameContains(user, name, pageable);
    }

    @Override
    public Page<Item> findAll(User user, Integer categoryId, String name, Integer status, Pageable pageable) {
        if (categoryId != 0) {
            switch (status) {
                case 1:
//                    return itemRepository.findAllByNameContains
            }
        }
        return itemRepository.findAllByFavoriteByAndNameContains(user, name, pageable);
    }
}
