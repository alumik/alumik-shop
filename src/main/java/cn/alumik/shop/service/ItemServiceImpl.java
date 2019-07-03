package cn.alumik.shop.service;

import cn.alumik.shop.dao.ItemRepository;
import cn.alumik.shop.dao.UserRepository;
import cn.alumik.shop.entity.Item;
import cn.alumik.shop.entity.User;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

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
    public void save(Item item) {
        String username = securityService.findLoggedInUsername();
        User user = userRepository.findByUsername(username);
        item.setSeller(user);
        item.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        item.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        item.setAvailable(true);
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
}
